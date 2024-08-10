package di

import data.remote.GeminiAPI

actual fun chatAIAPI(): GeminiAPI = ChatAIAPI.Default