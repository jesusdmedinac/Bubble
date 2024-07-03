package presentation.model

sealed interface UIBenefit {
    val title: String
    val description: String
}

sealed interface UIFreeBenefits : UIBenefit

data object UIFreeChallenges : UIFreeBenefits {
    override val title: String =
        "Acceso Básico a Retos de Reducción de Uso"
    override val description: String =
        "Participa en retos semanales para reducir el tiempo de uso del smartphone y ganar puntos."
}

data object UIFreeStatistics : UIFreeBenefits {
    override val title: String = "Estadísticas de Uso del Dispositivo"
    override val description: String =
        "Visualiza tu tiempo de uso diario y semanal, con gráficos y tendencias."
}

data object UIFreeChat : UIFreeBenefits {
    override val title: String = "Chat con Bubble limitado"
    override val description: String =
        "Interactúa con un chat de IA que ofrece consejos y apoyo para reducir la adicción al smartphone."
}

data object UIFreeRewards : UIFreeBenefits {
    override val title: String = "Puntos y Recompensas Básicas"
    override val description: String =
        "Gana puntos canjeables por recompensas dentro de la app por completar retos."
}

sealed interface UIPremiumBenefits : UIBenefit

data object UIPremiumChallenges : UIPremiumBenefits {
    override val title: String = "Acceso a Retos Exclusivos"
    override val description: String =
        "Participa en retos avanzados y personalizados, diseñados para un mayor impacto en la reducción del uso del smartphone."
}

data object UIPremiumStatistics : UIPremiumBenefits {
    override val title: String = "Estadísticas Avanzadas y Personalizadas"
    override val description: String =
        "Accede a análisis detallados y recomendaciones personalizadas basadas en tus patrones de uso."
}

data object UIPremiumChat : UIPremiumBenefits {
    override val title: String = "Chat con Bubble Ilimitado"
    override val description: String =
        "Disfruta de una versión mejorada del chat con IA, con respuestas más precisas y personalizadas."
}

data object UIPremiumRewards : UIPremiumBenefits {
    override val title: String = "Puntos y Recompensas Exclusivas"
    override val description: String =
        "Obtén acceso a recompensas exclusivas y beneficios adicionales al canjear tus puntos."
}