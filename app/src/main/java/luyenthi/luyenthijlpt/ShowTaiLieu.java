package luyenthi.luyenthijlpt;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShowTaiLieu extends AppCompatActivity {
    private WebView webView;
    private String link;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tai_lieu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView=(WebView)findViewById(R.id.wv);

        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("link");
        String ten=bundle.getString("ten");
        setTitle(ten);
        webView.getSettings().setSupportZoom(true);
        //setLoadsImagesAutomatically
        // để load ảnh với kích thước hợp lý
        //
        //Bộ đồ cho dù các trang tải WebView trong chế độ tổng quan, đó là, phóng ra các nội dung để phù hợp trên màn hình bằng chiều rộng.
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setUseWideViewPort(true);
        //Thiết lập chế độ qua cuộn
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setWebViewClient(new NeroWebView());    //doc ND k ra khoi webview
        //"Sử dụng setJavaScriptEnabled có thể tạo ra lỗ hổng XSS vào bạn xem xét áp dụng một cách cẩn thận".
        // cho phép client-side script vào WebView
        webView.getSettings().setJavaScriptEnabled(true);

        dialog = ProgressDialog.show(this, "", "Loading...");
        webView.loadUrl(link);

    }
    class NeroWebView extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if (dialog != null) {
                dialog.dismiss();
            }
            super.onPageFinished(view, url);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
