package com.privai.browser

import android.app.Application
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings

class PrivAIApplication : Application() {
    lateinit var runtime: GeckoRuntime
        private set

    override fun onCreate() {
        super.onCreate()

        val settings = GeckoRuntimeSettings.Builder()
            .aboutConfigEnabled(false)
            .consoleOutput(false)
            .javaScriptEnabled(true)
            .remoteDebuggingEnabled(false)
            .webFontsEnabled(true)
            .build()

        runtime = GeckoRuntime.create(this, settings)
    }
}
