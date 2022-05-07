package com.konghuan.skipads.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.konghuan.skipads.R;
import com.konghuan.skipads.activities.AppInformation;
import com.konghuan.skipads.bean.APP;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<APP> mAppList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MyAdapter(Context context,List<APP> mAppList){
        this.mAppList = mAppList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_layout,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        APP app = mAppList.get(position);

        holder.mytitle.setText(app.getName());
        holder.edition.setText(app.getEdition());
        holder.myimg.setImageDrawable(app.getImg());

        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AppInformation.class);

                intent.putExtra("img", (Parcelable) app.getImg());
                intent.putExtra("AppName", app.getName());
                intent.putExtra("AppEdition", app.getEdition());

                mContext.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mytitle;
        TextView edition;
        ImageView myimg;
        ViewGroup rlContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.mytitle = itemView.findViewById(R.id.tv_title);
            this.edition = itemView.findViewById(R.id.tv_edition);
            this.myimg = itemView.findViewById(R.id.iv_img);
            this.rlContainer = itemView.findViewById(R.id.list_layout);

        }
    }
}