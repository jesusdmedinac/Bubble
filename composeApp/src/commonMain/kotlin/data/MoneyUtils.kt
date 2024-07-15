package data

/**
 * Format a string as money assuming that the last two characters are the cents.
 *
 * @receiver The string to format as money.
 */
fun String.asMoney(): String {
    val units = substring(0, length - 2)
    val cents = substring(length - 2, length)
    return "$units.$cents"
}