package data

interface UsageAPI {
    fun getUsageStats(): List<UsageStats>
    fun packagesToFilter(): List<String>

    companion object {
        val Default = object : UsageAPI {
            override fun getUsageStats(): List<UsageStats> {
                TODO("Not yet implemented")
            }

            override fun packagesToFilter(): List<String> {
                TODO("Not yet implemented")
            }
        }
    }
}
