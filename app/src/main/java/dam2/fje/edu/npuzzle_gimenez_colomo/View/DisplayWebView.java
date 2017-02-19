package dam2.fje.edu.npuzzle_gimenez_colomo.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import dam2.fje.edu.npuzzle_gimenez_colomo.R;

/**
 * Classe que mostra una pàgina web local
 * Created by David on 20/02/2011.
 */

public class DisplayWebView extends AppCompatActivity {

    WebView webview;

    /**
     * Sobreescriu el mètode "OnCreate" per mostrar la pàgina web local.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_web_view);
        webview = (WebView)findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/HTMLAjuda.html");
    }
}
