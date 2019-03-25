package com.coderpig.family.Fragment.Home.data;



import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.coderpig.family.Activity.MainActivity;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Fragment.SafeFragment;
import com.coderpig.family.R;

public class HomeDataViewFragment extends BaseFragement {
    private static final String mTag = HomeDataViewFragment.class.getSimpleName();
    private View homedataviewFragmentView;
    private String cookie="";
    private WebView dataWebView;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();
    }
    @Override
    protected View initView() {
        Log.e(mTag, "数据大屏Fragment页面被初始化了...");
      homedataviewFragmentView = View.inflate(mContext, R.layout.fg_home_dataview, null);
      dataWebView=(WebView)homedataviewFragmentView.findViewById(R.id.data_webview);



 //  CookieSyncManager.createInstance(getActivity());
  //   CookieManager cookieManager = CookieManager.getInstance();
 //  cookieManager.setAcceptCookie(true);
 //    cookieManager.setCookie("http://148.70.56.247:8999/datav", cookie);  //cookies是要设置的cookie字符串
//  CookieSyncManager.getInstance().sync();
   //  dataWebView.setWebViewClient(new WebViewClient());
 // WebSettings settings = dataWebView.getSettings();
 // settings.setJavaScriptCanOpenWindowsAutomatically(true);
 // settings.setJavaScriptEnabled(true);
      dataWebView.loadUrl("http://148.70.56.247:8999/admin/login");

        return homedataviewFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "数据大屏Fragment页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");
    }
    public String getmTag()
    {
        return mTag;
    }

}
