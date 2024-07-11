package data.local

interface BuildConfig {
    val versionName: String

    companion object {
        val Default = object : BuildConfig {
            override val versionName: String
                get() = TODO("versionName on BuildConfig not yet implemented")
        }
    }
}