package com.abhishek.wikisearcher;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.abhishek.wikisearcher.models.Image;
import com.abhishek.wikisearcher.utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by abhishek on 03/03/16 at 3:39 PM.
 */
public class ImageAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = "ImageAdapter";
    ArrayList<String> mStrings;
    Context mContext;

    public ImageAdapter(ArrayList<String> strings, Context context) {
        mStrings = strings;
        mContext = context;
    }


    @Override
    public int getCount() {
        return mStrings.size();
    }

    @Override
    public String getItem(int i) {
        return mStrings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.item_image, viewGroup, false);

        ((TextView) view.findViewById(R.id.text)).setText(getItem(i));
        return view;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                Log.d(TAG, String.valueOf(charSequence));


                FilterResults filterResults = new FilterResults();
                ArrayList<Image> object = NetworkUtil.fetchImageJson(String.valueOf(charSequence));

                filterResults.values = object;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ArrayList<Image> object = (ArrayList<Image>) filterResults.values;
                Log.d(TAG, "No of Result: " + object.size());
            }
        };
        return filter;
    }
}
