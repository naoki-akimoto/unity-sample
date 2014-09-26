using UnityEngine;
using System.Collections;
using System;

public class MainGUI : MonoBehaviour {
	string loadedUrl;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void OnGUI() {
		float xx = Screen.width * 0.5f - 100;
		float yy = Screen.height * 0.5f - 100;
		float ww = 200;
		float hh = 200;
		
		if(GUI.Button(new Rect(xx, yy, ww, hh), "Open URL"))
		{
#if UNITY_EDITOR
			Application.OpenURL("http://www.google.co.jp/");
#elif UNITY_ANDROID
			AndroidJavaClass c =
				new AndroidJavaClass("com.example.webview.WebViewActivity");
			c.CallStatic("startActivity", name);
#elif UNITY_WEBPLAYER
			string url = "https://api.smt.docomo.ne.jp/cgi11d/authorization?"
				+ "client_id=Your_Client_ID&"
				+ "response_type=code&"
				+ "redirect_uri=https://127.0.0.1/&"
				+ "scope=PhotoGetContent&"
				+ "state=" + (DateTime.Now.Ticks / 10000);
			string js = "window.open('" + url + "', null, 'width=640,height=480,scrollbars=yes');"
				+ "function onFromSub( event ) {"
				+ "    alert ( 'onFromSub: ' + event.data );"
				+ "    u.getUnity().SendMessage('GameObject', 'OnPageLoaded', event.data);"
				+ "    window.removeEventListener('message', onFromSub, false);"
				+ "}"
				+ "window.addEventListener('message', onFromSub, false);";
			Application.ExternalEval(js);
#endif
		}

		if (this.loadedUrl != null)
		{
			GUI.Label(new Rect(10, 10, Screen.width - 10, 100), this.loadedUrl);
		}
	}

	public void OnPageLoaded(string url)
	{
		this.loadedUrl = url;
	}
}
