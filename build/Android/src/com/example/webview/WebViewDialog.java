package com.example.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.unity3d.player.UnityPlayer;

public class WebViewDialog {

    private static String GameObjectName;

    public static void showDialog(String name) {
        GameObjectName = name;

        final Activity current = UnityPlayer.currentActivity;
        current.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WebView webView = new CustomWebView(current);
                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setFocusable(false);
                webView.setFocusableInTouchMode(false);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView target, String url) {
                        target.setFocusable(true);
                        target.setFocusableInTouchMode(true);
                        if (!url.startsWith("http://www.google.co.jp/") &&
                                !url.startsWith("https://www.google.co.jp/")) {
                            UnityPlayer.UnitySendMessage(GameObjectName, "OnPageLoaded", url);
                        }
                    }
                });
                webView.loadUrl("http://www.google.co.jp/");

                AlertDialog.Builder builder = new AlertDialog.Builder(current);
                builder.setView(webView);
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show().setCanceledOnTouchOutside(false);
            }
        });
    }
}
