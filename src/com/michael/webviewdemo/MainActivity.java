package com.michael.webviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * 更改精彩应用推荐
 * 
 * 
 * */
public class MainActivity extends Activity {

	private static final String URL = "http://demo.91yong.com/mobile/moreApp.jsp?type=2";

	private WebView wvShow;
	private ProgressBar pbLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wvShow = (WebView) findViewById(R.id.web_view);
		pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

		wvShow.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				// 监听下载功能，当用户点击下载链接的时候，直接调用系统的浏览器来下载

				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		wvShow.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				//页面加载完毕
				super.onPageFinished(view, url);
				if (pbLoading != null && pbLoading.getVisibility() == View.VISIBLE) {
					pbLoading.setVisibility(View.GONE);//进度条不可见
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				//页面开始加载
				super.onPageStarted(view, url, favicon);
				if (pbLoading != null && pbLoading.getVisibility() == View.INVISIBLE) {
					pbLoading.setVisibility(View.VISIBLE);//进度条可见
				}
			}

		});

		wvShow.getSettings().setBuiltInZoomControls(true);// 设置支持缩放
		wvShow.getSettings().setDefaultZoom(ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
		wvShow.loadUrl(URL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.more_app, menu);
		return true;

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.more_app, menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.refresh: {// 刷新
				
				wvShow.loadUrl(URL);
				break;
			}
			case R.id.open_in_brower: {// 在浏览器中打开
				
				Uri uri = Uri.parse(URL);
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
				break;
			}
		}
		return true;
	}
}
