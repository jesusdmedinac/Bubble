# Keep all public classes and methods
-keep public class * {
    public *;
}

-keep class io.kamel.image.** { *; }
-dontwarn io.kamel.image.**

-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean