package com.hydrobuddy.mobile

object NutrientCalculator {
    // ppm = grams fertilizer × nutrient% × 10,000 / liters
    fun contribution(f: Fertilizer, grams: Double, liters: Double): NutrientTarget {
        if (liters <= 0) return NutrientTarget()
        val factor = grams * 10000.0 / liters / 100.0
        return NutrientTarget(
            n = f.n * factor, p = f.p * factor, k = f.k * factor,
            ca = f.ca * factor, mg = f.mg * factor, s = f.s * factor,
            fe = f.fe * factor, mn = f.mn * factor, zn = f.zn * factor,
            cu = f.cu * factor, b = f.b * factor, mo = f.mo * factor
        )
    }

    fun sum(items: List<Contribution>): NutrientTarget = NutrientTarget(
        n = items.sumOf { it.ppm.n }, p = items.sumOf { it.ppm.p },
        k = items.sumOf { it.ppm.k }, ca = items.sumOf { it.ppm.ca },
        mg = items.sumOf { it.ppm.mg }, s = items.sumOf { it.ppm.s },
        fe = items.sumOf { it.ppm.fe }, mn = items.sumOf { it.ppm.mn },
        zn = items.sumOf { it.ppm.zn }, cu = items.sumOf { it.ppm.cu },
        b = items.sumOf { it.ppm.b }, mo = items.sumOf { it.ppm.mo }
    )
}
