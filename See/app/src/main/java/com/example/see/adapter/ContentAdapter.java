package com.example.see.adapter;


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.see.ChapterActivity;
import com.example.see.MyApplication;
import com.example.see.R;
import com.example.see.ShowActivity;
import com.example.see.domain.Content;
import com.example.see.domain.History;
import com.example.see.service.impl.HistoryServiceImpl;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder>{

    private List<Content> mContentList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View contentView;
        TextView contentNO;
        TextView contentId;

        public ViewHolder(View view) {
            super(view);
            contentView = view;
            contentNO=(TextView) view.findViewById(R.id.contentNO);
            //contentId = (TextView) view.findViewById(R.id.contentId);
        }
    }

    public ContentAdapter(List<Content> ContentList) {
        mContentList = ContentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contents_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Content content = mContentList.get(position);
                System.out.println("我的id是"+content.getContentBookId());
                //Toast.makeText(v.getContext(), "you clicked view " + content.getContentId(), Toast.LENGTH_SHORT).show();


                Intent intent=new Intent(v.getContext(), ChapterActivity.class);
                intent.putExtra("content",content);
                v.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Content content = mContentList.get(position);
        holder.contentNO.setText("第"+content.getContentNo()+"话");
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

}