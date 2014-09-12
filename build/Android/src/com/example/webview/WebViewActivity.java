package com.example.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.unity3d.player.UnityPlayer;

public class WebViewActivity extends Activity {

	private static String GameObjectName;

	public static void startActivity(String name) {
		GameObjectName = name;

		Activity current = UnityPlayer.currentActivity;
        Intent intent = new Intent(current.getApplicationContext(),
                WebViewActivity.class);
        current.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(
            Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        this.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

        WebView webView = new WebView(this);
        layout.addView(webView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String url) {
            	if (!url.startsWith("http://www.google.co.jp/") &&
            			!url.startsWith("https://www.google.co.jp/")) {
            		UnityPlayer.UnitySendMessage(GameObjectName, "OnPageLoaded", url);
            		finish();
            	}
            }
        });
        webView.loadUrl("http://www.google.co.jp/");
    }
}
