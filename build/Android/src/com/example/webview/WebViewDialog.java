package com.example.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.unity3d.player.UnityPlayer;

public class WebViewDialog {

    private static final String AuthURL = "https://api.smt.docomo.ne.jp/cgi11d/authorization";
    private static final String RedirectURL = "https://127.0.0.1/";

    private static String GameObjectName;
    private static AlertDialog CurrentDialog;

    public static void showDialog(String name, final String clientId) {
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

                        if (isRedirectUri(RedirectURL, url)) {
                            UnityPlayer.UnitySendMessage(GameObjectName, "OnPageLoaded", url);
                            CookieManager.getInstance().removeSessionCookie();
                            if (CurrentDialog != null) {
                                CurrentDialog.cancel();
                                CurrentDialog = null;
                            }
                        }
                    }

                    private boolean isRedirectUri(String redirectUrl, String url) {
                    	Uri redirectUri = Uri.parse(redirectUrl);
                    	Uri uri = Uri.parse(url);
                        if (!redirectUri.getScheme().equals(uri.getScheme())) {
                            return false;
                        } else if (!redirectUri.getHost().equals(uri.getHost())) {
                            return false;
                        } else if (!redirectUri.getPath().equals(uri.getPath())) {
                            return false;
                        }
                        return true;
                    }
                });
                webView.loadUrl(generateUrl(AuthURL, clientId, RedirectURL));

                AlertDialog.Builder builder = new AlertDialog.Builder(current);
                builder.setView(webView);
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                CurrentDialog = builder.show();
                CurrentDialog.setCanceledOnTouchOutside(false);
            }
        });
    }

    private static String generateUrl(
            String authURL,
            String clientId,
            String redirectUri)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(authURL).append("?");
        builder.append("client_id=").append(clientId).append("&");
        builder.append("response_type=code&");
        builder.append("redirect_uri=").append(redirectUri).append("&");
        builder.append("scope=").append("PhotoGetContent").append("&");
        builder.append("state=").append(Long.toString(System.currentTimeMillis()));
        return builder.toString();
    }
}
