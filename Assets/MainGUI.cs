using UnityEngine;
using System.Collections;

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
			WebViewPlugins.LaunchWebView(this.name);
#endif
		}
		if (this.loadedUrl != null)
		{
			GUI.Label(new Rect(10, 10, Screen.width - 10, 100), this.loadedUrl);
		}
	}

#if UNITY_ANDROID
	public void OnPageLoaded(string url)
	{
		this.loadedUrl = url;
	}
#endif
}
