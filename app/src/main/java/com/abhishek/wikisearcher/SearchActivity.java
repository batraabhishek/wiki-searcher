package com.abhishek.wikisearcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abhishek.wikisearcher.models.Image;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {


    private EditText mEditTextSearch;
    private RecyclerView mRecyclerView;
    private ImageResultAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new ImageResultAdapter(this, new ArrayList<Image>());
        mRecyclerView.setAdapter(mAdapter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Image image = mAdapter.getImages().get(position);

                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);

                intent.putExtra(Image.TAG, image);

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(SearchActivity.this, v, "title");

                startActivity(intent, options.toBundle());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        handleMenuSearch();
        return true;
    }

    protected void handleMenuSearch() {

        ActionBar action = getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.view_action_bar_search);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        action.setCustomView(action.getCustomView(), layoutParams);
        action.setDisplayShowTitleEnabled(false);
        mEditTextSearch = (EditText) action.getCustomView().findViewById(R.id.edtSearch);
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            private Timer timer = new Timer();
            private final long DELAY = 500; // milliseconds

            @Override
            public void afterTextChanged(final Editable editable) {

                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {

                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SearchActivity.this.mAdapter.getFilter().filter(editable.toString());
                                    }
                                });
                            }
                        }, DELAY);
            }
        });
    }
}
