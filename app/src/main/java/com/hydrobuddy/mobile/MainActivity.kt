package com.hydrobuddy.mobile

import android.os.Bundle
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val rows = mutableListOf<Pair<Fertilizer, EditText>>()
    private lateinit var litersInput: EditText
    private lateinit var result: TextView

    private val targetFields = mutableMapOf<String, EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildUi()
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    private fun text(value: String, size: Float = 14f, bold: Boolean = false): TextView =
        TextView(this).apply {
            this.text = value
            textSize = size
            setTextColor(0xFF18352B.toInt())
            if (bold) typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(4), dp(4), dp(4), dp(4))
        }

    private fun input(value: String = "0"): EditText =
        EditText(this).apply {
            setText(value)
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                    android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            setPadding(dp(10), 0, dp(10), 0)
            layoutParams = LinearLayout.LayoutParams(dp(90), dp(48))
        }

    private fun buildUi() {
        val scroll = ScrollView(this)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(24))
        }

        root.addView(text("HydroBuddy Mobile", 26f, true))
        root.addView(text("Nutrient Calculator • Version 1", 14f))

        val info = text(
            "Kalkulator mobile untuk formulasi nutrisi. Masukkan volume tandon dan gram pupuk. " +
            "Hasil dihitung sebagai ppm unsur berdasarkan analisis pupuk elemental.",
            14f
        )
        root.addView(info)

        root.addView(text("Volume tandon (L)", 16f, true))
        litersInput = input("100")
        root.addView(litersInput)

        root.addView(text("Target unsur (ppm)", 18f, true))
        listOf(
            "N" to 210.0, "P" to 70.0, "K" to 400.0, "Ca" to 230.0,
            "Mg" to 80.0, "S" to 140.0, "Fe" to 5.0, "Mn" to 1.2,
            "Zn" to 0.4, "Cu" to 0.1, "B" to 0.6, "Mo" to 0.05
        ).forEach { (key, value) ->
            val line = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
            line.addView(text(key, 15f, true), LinearLayout.LayoutParams(0, dp(48), 1f))
            val e = input(value.toString())
            targetFields[key] = e
            line.addView(e)
            root.addView(line)
        }

        root.addView(text("Pupuk dan dosis (gram)", 18f, true))

        FertilizerDatabase.defaults.forEach { f ->
            val line = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
            }
            line.addView(text(f.name, 14f), LinearLayout.LayoutParams(0, dp(56), 1f))
            val e = input("0")
            rows.add(f to e)
            line.addView(e)
            root.addView(line)
        }

        val button = Button(this).apply {
            text = "HITUNG PPM"
            setOnClickListener { calculate() }
        }
        root.addView(button, LinearLayout.LayoutParams(-1, dp(54)))

        result = text("Hasil akan tampil di sini.", 14f)
        root.addView(result)

        scroll.addView(root)
        setContentView(scroll)
    }

    private fun d(e: EditText): Double =
        e.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0

    private fun calculate() {
        val liters = d(litersInput)
        if (liters <= 0) {
            result.text = "Volume harus lebih besar dari 0 L."
            return
        }

        val contributions = rows.mapNotNull { (f, e) ->
            val grams = d(e)
            if (grams > 0) Contribution(f.name, grams, NutrientCalculator.contribution(f, grams, liters))
            else null
        }

        val total = NutrientCalculator.sum(contributions)
        val target = NutrientTarget(
            n = d(targetFields["N"]!!), p = d(targetFields["P"]!!), k = d(targetFields["K"]!!),
            ca = d(targetFields["Ca"]!!), mg = d(targetFields["Mg"]!!), s = d(targetFields["S"]!!),
            fe = d(targetFields["Fe"]!!), mn = d(targetFields["Mn"]!!), zn = d(targetFields["Zn"]!!),
            cu = d(targetFields["Cu"]!!), b = d(targetFields["B"]!!), mo = d(targetFields["Mo"]!!)
        )

        fun line(name: String, actual: Double, wanted: Double): String {
            val diff = actual - wanted
            return String.format(Locale.US, "%-3s  %8.3f  target %8.3f  Δ %+8.3f", name, actual, wanted, diff)
        }

        val sb = StringBuilder()
        sb.append("HASIL PPM\n")
        sb.append(line("N", total.n, target.n)).append("\n")
        sb.append(line("P", total.p, target.p)).append("\n")
        sb.append(line("K", total.k, target.k)).append("\n")
        sb.append(line("Ca", total.ca, target.ca)).append("\n")
        sb.append(line("Mg", total.mg, target.mg)).append("\n")
        sb.append(line("S", total.s, target.s)).append("\n")
        sb.append(line("Fe", total.fe, target.fe)).append("\n")
        sb.append(line("Mn", total.mn, target.mn)).append("\n")
        sb.append(line("Zn", total.zn, target.zn)).append("\n")
        sb.append(line("Cu", total.cu, target.cu)).append("\n")
        sb.append(line("B", total.b, target.b)).append("\n")
        sb.append(line("Mo", total.mo, target.mo)).append("\n\n")

        if (contributions.isNotEmpty()) {
            sb.append("KONTRIBUSI PUPUK\n")
            contributions.forEach { c ->
                sb.append(String.format(Locale.US, "%s: %.3f g\n", c.name, c.grams))
            }
        } else {
            sb.append("Belum ada pupuk yang diberi dosis.")
        }

        result.text = sb.toString()
    }
}
