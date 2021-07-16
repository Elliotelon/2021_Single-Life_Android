package study.singlelife.contentslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import study.singlelife.R

class ContentShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_show)

        val getUrl = intent.getStringExtra("url")
        //Toast.makeText(this, getUrl, Toast.LENGTH_SHORT).show()

        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl(getUrl.toString())
    }
}