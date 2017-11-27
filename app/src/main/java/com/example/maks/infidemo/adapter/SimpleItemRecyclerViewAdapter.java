package com.example.maks.infidemo.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maks.infidemo.R;
import com.example.maks.infidemo.model.AboutItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maks on 27/11/17.
 */
public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<AboutItem> mValues =new ArrayList<AboutItem>();

    public SimpleItemRecyclerViewAdapter(List<AboutItem> items) {
        mValues.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content, parent, false);
        return new SimpleItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder holder, final int position) {
        AboutItem mItem = mValues.get(position);
        holder.mIdView.setText(mItem.getTitle());
        holder.mContentView.setText(mItem.getDescription());
        Glide.with(holder.mImageView.getContext())
                .load(mItem.getImageHref())
                .centerCrop()
                .into(holder.mImageView);

    }

    public void addItem(AboutItem item){
        mValues.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public AboutItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.desc);
            mImageView = (ImageView) view.findViewById(R.id.list_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

