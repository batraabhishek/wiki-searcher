package com.abhishek.wikisearcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.wikisearcher.models.Image;
import com.abhishek.wikisearcher.utils.NetworkUtil;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private Image mImage;
    private TextView mTextView;
    private ImageView mImageView;
    private WebView mBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImage = (Image) getIntent().getSerializableExtra(Image.TAG);
        setContentView(R.layout.activity_detail);

        mTextView = (TextView) findViewById(R.id.title);
        mImageView = (ImageView) findViewById(R.id.image);

        mTextView.setText(mImage.getTitle());
        Glide.with(this)
                .load(mImage.getThumbUrl())
                .placeholder(R.drawable.progress_bar) // can also be a drawable
                .error(R.drawable.progress_bar) // will be displayed if the image cannot be loaded
                .crossFade()
                .into(mImageView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBrowser = (WebView) findViewById(R.id.webview);

        mBrowser.setWebViewClient(new WebViewClient());

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String url = NetworkUtil.getUrlFromPageId(mImage.getPageId());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBrowser.loadUrl(url);
                    }
                });

            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
