package presentation.model

import data.asMoney

sealed interface UIPaymentPlan {
    val title: String
    val priceAsMXN: String
    val priceWithDiscount: String
    val hasDiscount: Boolean
        get() = true

    fun formattedPrice(): String = "$${priceAsMXN.asMoney()} MXN"

    fun formattedPriceWithDiscount(): String = priceWithDiscount
        .let { "$${it.asMoney()} MXN" }
}

data object UIAnnualPaymentPlan : UIPaymentPlan {
    override val title: String = "Anual"
    override val priceAsMXN: String = "99900"
    override val priceWithDiscount: String = "49950"
    fun monthlyPriceAsMXN(): String {
        val units = priceAsMXN.substring(0, priceAsMXN.length - 2).toDouble()
        val cents = priceAsMXN.substring(priceAsMXN.length - 2, priceAsMXN.length).toDouble() / 100.0
        return ((units + cents) * 100).toInt().toString()
    }
}

data object UIMonthlyPaymentPlan : UIPaymentPlan {
    override val title: String = "Mensual"
    override val priceAsMXN: String = "9900"
    override val priceWithDiscount: String = "4950"
}
