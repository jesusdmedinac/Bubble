package data

fun String.formatNumber(): String {
    val parts = split(".")
    val integerPart = parts[0]
    val decimalPart = if (parts.size > 1) "." + parts[1] else ""

    val formattedIntegerPart = integerPart.reversed().chunked(2).joinToString(",").reversed()

    return formattedIntegerPart + decimalPart
}