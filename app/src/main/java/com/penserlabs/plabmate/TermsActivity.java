package com.penserlabs.plabmate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class TermsActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        webView = findViewById(R.id.terms_WV);
        progressBar = findViewById(R.id.termsprogress_PB);

        if ( isNetworkAvailable() )     //check if internet available or not
        {
            WebView webview = (WebView)findViewById(R.id.terms_WV);
            webView.setWebViewClient(new myWebClient());
            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl(getString(R.string.terms_URL));
        }
        else    //Not connected
        {
            Toast.makeText(
                    TermsActivity.this,
                    "Please check your internet connection",
                    Toast.LENGTH_LONG
            ).show();
        }

    }

    public boolean isNetworkAvailable()
    {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) getApplicationContext().getSystemService(TermsActivity.this.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
    }

}
