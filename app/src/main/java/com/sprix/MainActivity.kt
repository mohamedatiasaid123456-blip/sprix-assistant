package com.sprix.assistant

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

private val programmingUrl =
    "https://me-portal.qureo.education/login"

private val financeUrl =
    "https://egy.app.spx-learning-square.com/dashboard"

private lateinit var root: LinearLayout
private var webView: WebView? = null

private val backgroundColor = Color.rgb(245, 247, 252)
private val primaryColor = Color.rgb(25, 45, 85)

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    showHome()
}

private fun setupScreen(title: String, subtitle: String) {
    root = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        setPadding(28, 28, 28, 28)
        setBackgroundColor(backgroundColor)
    }

    val heading = TextView(this).apply {
        text = title
        textSize = 27f
        setTextColor(primaryColor)
        typeface = Typeface.DEFAULT_BOLD
        gravity = Gravity.CENTER
    }

    val description = TextView(this).apply {
        text = subtitle
        textSize = 17f
        setTextColor(Color.DKGRAY)
        gravity = Gravity.CENTER
        setPadding(0, 16, 0, 24)
    }

    root.addView(
        heading,
        LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    )

    root.addView(
        description,
        LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    )

    setContentView(root)
}

private fun makeButton(
    label: String,
    action: () -> Unit
): Button {
    return Button(this).apply {
        text = label
        textSize = 18f
        isAllCaps = false

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(0, 10, 0, 10)
        layoutParams = params

        setOnClickListener { action() }
    }
}

private fun showHome() {
    webView?.destroy()
    webView = null

    setupScreen(
        "SPRIX Assistant",
        "اختار الصف الدراسي"
    )

    root.addView(makeButton("الصف الأول الثانوي") {
        showFirstYear()
    })

    root.addView(makeButton("الصف الثاني الثانوي") {
        showSecondYear()
    })
}

private fun showFirstYear() {
    setupScreen(
        "الصف الأول الثانوي",
        "اختار القسم المطلوب"
    )

    root.addView(makeButton("💻 البرمجة") {
        openWebsite(programmingUrl)
    })

    root.addView(makeButton("⬅️ رجوع") {
        showHome()
    })
}

private fun showSecondYear() {
    setupScreen(
        "الصف الثاني الثانوي",
        "اختار القسم المطلوب"
    )

    root.addView(makeButton("💻 البرمجة") {
        openWebsite(programmingUrl)
    })

    root.addView(makeButton("💰 الثقافة المالية") {
        openWebsite(financeUrl)
    })

    root.addView(makeButton("⬅️ رجوع") {
        showHome()
    })
}

private fun openWebsite(url: String) {
    webView?.destroy()

    setupScreen(
        "SPRIX Assistant",
        "جارٍ فتح المنصة التعليمية..."
    )

    root.gravity = Gravity.TOP

    root.addView(makeButton("⬅️ العودة للأقسام") {
        webView?.destroy()
        webView = null
        showHome()
    })

    val browser = WebView(this).apply {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.loadsImagesAutomatically = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        webViewClient = WebViewClient()
    }

    webView = browser

    root.addView(
        browser,
        LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            0,
            1f
        )
    )

    browser.loadUrl(url)
}

@Deprecated("Deprecated in Android API")
override fun onBackPressed() {
    if (webView != null && webView!!.canGoBack()) {
        webView!!.goBack()
    } else {
        showHome()
    }
}

override fun onDestroy() {
    webView?.destroy()
    webView = null
    super.onDestroy()
}

}
