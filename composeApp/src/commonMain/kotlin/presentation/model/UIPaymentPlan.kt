package presentation.model

sealed interface UIPaymentPlan {
    val title: String
    val description: String
    val price: String
}

data object UIAnnualPaymentPlan : UIPaymentPlan {
    override val title: String = "Anual"
    override val description: String = "($83.25/mes)"
    override val price: String = "999.00 MXN"
}

data object UIMonthlyPaymentPlan : UIPaymentPlan {
    override val title: String = "Mensual"
    override val description: String = ""
    override val price: String = "99.00 MXN"
}
