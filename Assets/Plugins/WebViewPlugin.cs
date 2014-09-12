using UnityEngine;

public class WebViewPlugins
{
    public static void LaunchWebView(string name)
    {
#if UNITY_ANDROID
        Debug.LogError("call LaunchWebView.");
        AndroidJavaClass c =
            new AndroidJavaClass("com.example.webview.WebViewActivity");
        c.CallStatic("startActivity", name);
#endif
    }
}
