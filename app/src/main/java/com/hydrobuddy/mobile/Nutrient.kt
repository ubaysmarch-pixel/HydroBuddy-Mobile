package com.hydrobuddy.mobile

data class NutrientTarget(
    val n: Double = 0.0,
    val p: Double = 0.0,
    val k: Double = 0.0,
    val ca: Double = 0.0,
    val mg: Double = 0.0,
    val s: Double = 0.0,
    val fe: Double = 0.0,
    val mn: Double = 0.0,
    val zn: Double = 0.0,
    val cu: Double = 0.0,
    val b: Double = 0.0,
    val mo: Double = 0.0
)

data class Fertilizer(
    val name: String,
    val n: Double = 0.0,
    val p: Double = 0.0,
    val k: Double = 0.0,
    val ca: Double = 0.0,
    val mg: Double = 0.0,
    val s: Double = 0.0,
    val fe: Double = 0.0,
    val mn: Double = 0.0,
    val zn: Double = 0.0,
    val cu: Double = 0.0,
    val b: Double = 0.0,
    val mo: Double = 0.0
)

data class Contribution(
    val name: String,
    val grams: Double,
    val ppm: NutrientTarget
)

object FertilizerDatabase {
    // Percentages are elemental nutrient percentages, matching the user's
    // previously supplied fertilizer analysis. Adjust/add products as needed.
    val defaults = listOf(
        Fertilizer("Calnit", n = 14.4, ca = 18.6),
        Fertilizer("KNO3", n = 13.0, k = 38.1846),
        Fertilizer("Mag-S", mg = 9.63, s = 13.0),
        Fertilizer("MAP", n = 12.0, p = 26.6204),
        Fertilizer("MKP", p = 22.6928, k = 28.2234),
        Fertilizer("SOP", k = 43.1652, s = 18.0),
        Fertilizer("Fe EDTA 13%", fe = 13.0),
        Fertilizer("Mn 13%", mn = 13.0),
        Fertilizer("Zn 15%", zn = 15.0),
        Fertilizer("Cu 15%", cu = 15.0),
        Fertilizer("B", b = 56.31),
        Fertilizer("Ammonium Molybdate", mo = 39.65)
    )
}
