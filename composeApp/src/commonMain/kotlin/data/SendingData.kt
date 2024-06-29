package data

interface SendingData {
    fun sendPlainText(data: String)

    companion object {
        val Default: SendingData = object : SendingData {
            override fun sendPlainText(data: String) {
                TODO("sendPlainText on SendingData is not implemented yet")
            }
        }
    }
}