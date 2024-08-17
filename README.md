This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

# Bubble Server

Bubble Server is powered by Ktor Server. The easiest way to test it in local is by running
[Docker Compose](https://ktor.io/docs/docker-compose.html).

## Local Deploy

First of all clean your local environment:

```shell
./gradlew clean
```

Then run next command to build a combined JAR of project and runtime dependencies:

```shell
./gradlew buildFatJar
```

Before deploy locally you may need to stops all the services, removes their containers, deletes the 
images and volumes, and cleans up any orphaned containers related to the Docker Compose project.

> [!CAUTION]
> This command may affect your Docker local environment. Run next command with caution.

```shell
docker compose down --rmi all -v --remove-orphans
```

Now, we need to add some env variables.

> [!NOTE]  
> Next instructions are based on Z Shell 

Open `~/.zshrc` file or create it if it does not exists:

```shell
# Open ~/.zshrc in edit mode
open -e ~/.zshrc

# Create it
nano ~/.zshrc
```

Edit the file with next variables:

```plaintext
# Bubble
export PORT="[REPLACE_WITH_PORT]"
export GEMINI_KEY="[REPLACE_WITH_GEMINI_KEY]"
export FIREBASE_DATABASE_NAME="[REPLACE_WITH_FIREBASE_DATABASE_NAME]"
```

Save the changes on your file and run next command:

```shell
source ~/.zshrc
```

Finally, run `docker compose up` and open the `localhost` on the `port` defined previously to test
the Bubble Ktor Server running on your local environment.

```shell
docker compose up
# add --force-recreate parameter if needed
```