package com.example.see.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.see.R;
import com.example.see.dao.DBOpenHelper;
import com.example.see.domain.Content;
import com.example.see.service.impl.BookServiceImpl;
import com.example.see.util.Consts;

import java.net.URL;
import java.util.List;
import java.util.Locale;

public class ContentPathAdapter extends ArrayAdapter<Content> {

    private int resourceId;


    public ContentPathAdapter(Context context, int textViewResourceId,
                       List<Content> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Content content = getItem(position); // 获取当前项的Content实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contentPath = (ImageView) view.findViewById (R.id.contentPath);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        try {
            URL url = new URL(Consts.PHOTO_BASE_URI +"cc"+content.getContentDesri()+"/"+content.getContentNo()+"/"+content.getContentPath());
            Bitmap bm = BitmapFactory.decodeStream(url.openStream());
            viewHolder.contentPath.setImageBitmap(bm);
            return view;
        } catch (Exception e){
            e.printStackTrace();
        }finally {
        }
        return null;
    }

    class ViewHolder {

        ImageView contentPath;


    }

}
