package concioscompany.leitordelibras

import android.app.AlertDialog
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import concioscompany.leitordelibras.ui.theme.LeitorDeLibrasTheme
import java.security.Permission

class MainActivity : ComponentActivity() {

    private var permissionRequest : PermissionRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            permissionRequest?.apply {
                if (granted) {
                    grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                } else {
                    deny()
                }
            }
        }

        val webView = findViewById<WebView>(R.id.webView) as WebView
        webView.loadUrl("https://concioscompany.com/leitordelibras.html")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                if (PermissionRequest.RESOURCE_VIDEO_CAPTURE in request.resources) {
                    permissionRequest = request
                    launcher.launch(android.Manifest.permission.CAMERA)
                }
            }
        }
    }
}
