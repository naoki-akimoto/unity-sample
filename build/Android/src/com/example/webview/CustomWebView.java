package com.example.webview;

import android.content.Context;
import android.webkit.WebView;

public class CustomWebView extends WebView {

	public CustomWebView(Context context) {
		super(context);
	}

	@Override
	public boolean onCheckIsTextEditor() {
		return true;
	}
}
