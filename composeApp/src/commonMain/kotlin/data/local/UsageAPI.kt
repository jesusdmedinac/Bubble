package data.local

interface UsageAPI {
    fun requestUsageSettings()
    fun hasPermission(): Boolean
    fun getUsageStats(): List<UsageStats>
    fun packagesToFilter(): List<String>

    companion object {
        val Default = object : UsageAPI {
            override fun requestUsageSettings() {
                TODO("requestUsageSettings on UsageAPI is not yet implemented")
            }

            override fun hasPermission(): Boolean {
                TODO("hasPermission on UsageAPI is not yet implemented")
            }

            override fun getUsageStats(): List<UsageStats> {
                TODO("getUsageStats on UsageAPI is not yet implemented")
            }

            override fun packagesToFilter(): List<String> {
                TODO("packagesToFilter on UsageAPI is not yet implemented")
            }
        }
    }
}
