package com.apkcore.layoutmanagerdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by apkcore on 2017/12/14. 22:13
 * mail:apkcore@gmail.com
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private Context mContext;
    private List<Bitmap> mBitmaps;

    public MyAdapter(Context context, List<Bitmap> bitmap) {
        mContext = context;
        mBitmaps = bitmap;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        position = getRawItemCount() == 0 ? 0 : position % getRawItemCount();
        holder.imageView.setImageBitmap(mBitmaps.get(position));
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return (mBitmaps == null || mBitmaps.size() == 0) ? 0 : Integer.MAX_VALUE;
    }

    public int getRawItemCount() {
        return (mBitmaps == null || mBitmaps.size() == 0) ? 0 : mBitmaps.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicked:" + v.getTag(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
