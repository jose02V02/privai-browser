package com.privai.browser

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings
import org.mozilla.geckoview.GeckoView

class MainActivity : Activity() {
    private lateinit var session: GeckoSession
    private lateinit var geckoView: GeckoView
    private lateinit var urlInput: EditText
    private lateinit var aiPanel: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.WHITE

        session = GeckoSession(createPrivateSessionSettings())
        session.open((application as PrivAIApplication).runtime)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(245, 247, 250))
        }

        val toolbar = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(10, 10, 10, 10)
        }

        urlInput = EditText(this).apply {
            setSingleLine(true)
            hint = "Cerca o inserisci URL"
            imeOptions = EditorInfo.IME_ACTION_GO
            setTextColor(Color.rgb(15, 23, 42))
            setHintTextColor(Color.rgb(100, 116, 139))
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    loadAddress(text.toString())
                    true
                } else {
                    false
                }
            }
        }

        val goButton = Button(this).apply {
            text = "Vai"
            setOnClickListener { loadAddress(urlInput.text.toString()) }
        }

        val aiButton = Button(this).apply {
            text = "AI"
            setOnClickListener { summarizeCurrentPage() }
        }

        toolbar.addView(urlInput)
        toolbar.addView(goButton)
        toolbar.addView(aiButton)

        geckoView = GeckoView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }
        geckoView.setSession(session)

        aiPanel = TextView(this).apply {
            text = "PrivAI pronto. Apri una pagina e premi AI."
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(11, 16, 32))
            setPadding(18, 14, 18, 14)
        }

        val browserFrame = FrameLayout(this).apply {
            addView(geckoView)
        }

        root.addView(toolbar)
        root.addView(browserFrame)
        root.addView(aiPanel)
        setContentView(root)

        val firstUrl = intent?.data?.toString() ?: "https://duckduckgo.com"
        loadAddress(firstUrl)
    }

    override fun onDestroy() {
        session.close()
        super.onDestroy()
    }

    private fun loadAddress(rawAddress: String) {
        val address = rawAddress.trim()
        if (address.isEmpty()) return

        val url = when {
            address.startsWith("https://", ignoreCase = true) -> address
            address.startsWith("http://", ignoreCase = true) ->
                address.replaceFirst("http://", "https://")
            "." in address && " " !in address -> "https://$address"
            else -> "https://duckduckgo.com/?q=${Uri.encode(address)}"
        }

        urlInput.setText(url)
        session.loadUri(url)
    }

    @SuppressLint("SetTextI18n")
    private fun summarizeCurrentPage() {
        aiPanel.text = """
            PrivAI 0.1
            
            Browser attivo.
            
            In questa versione iniziale:
            - navigazione web con GeckoView
            - modalita privata
            - protezione anti-tracking
            - pannello AI pronto
            
            Prossimo passo:
            collegare l'estrazione del testo della pagina e un vero motore AI.
        """.trimIndent()
    }
}

private fun createPrivateSessionSettings(): GeckoSessionSettings {
    return GeckoSessionSettings.Builder()
        .usePrivateMode(true)
        .useTrackingProtection(true)
        .build()
}
