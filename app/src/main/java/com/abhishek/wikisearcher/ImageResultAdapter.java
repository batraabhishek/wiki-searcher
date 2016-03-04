package com.abhishek.wikisearcher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.wikisearcher.models.Image;
import com.abhishek.wikisearcher.utils.NetworkUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by abhishek on 03/03/16 at 10:36 PM.
 */
public class ImageResultAdapter extends RecyclerView.Adapter<ImageResultAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "ImageResultAdapter";
    ArrayList<Image> mImages;
    Context mContext;

    public ImageResultAdapter(Context context, ArrayList<Image> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Image image = mImages.get(position);
        holder.title.setText(image.getTitle());


        Glide.with(mContext)
                .load(image.getThumbUrl())
                .placeholder(R.drawable.progress_bar) // can also be a drawable
                .error(R.drawable.progress_bar) // will be displayed if the image cannot be loaded
                .crossFade()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
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

                mImages = object;
                notifyDataSetChanged();
            }
        };

        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public ArrayList<Image> getImages() {
        return mImages;
    }
}
