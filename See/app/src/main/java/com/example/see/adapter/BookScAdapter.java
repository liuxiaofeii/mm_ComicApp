package com.example.see.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.see.ChapterActivity;
import com.example.see.R;
import com.example.see.ShowActivity;
import com.example.see.domain.Book;
import com.example.see.domain.Content;
import com.example.see.domain.History;
import com.example.see.service.impl.HistoryServiceImpl;
import com.example.see.util.Consts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class BookScAdapter extends ArrayAdapter<Book> {

    private int resourceId;
    private List<Book> books;


    public BookScAdapter(Context context, int textViewResourceId,
                         List<Book> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        books=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position); // 获取当前项的Book实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.bookPhoto = (ImageView) view.findViewById (R.id.bookphoto1);
            viewHolder.bookName = (TextView) view.findViewById (R.id.bookname1);
            viewHolder.startread = (Button) view.findViewById (R.id.startread);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.startread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChapterActivity.class);
                Book book=books.get(position);
                Content content=new Content();
                content.setContentBookId(book.getBookId());
                content.setContentNo(1);
                intent.putExtra("content", content);
                getContext().startActivity(intent);
            }
        });
        try {
//            URL url = new URL(Consts.PHOTO_BASE_URI+book.getBookPhoto());
//            System.out.println(url.openStream().read());
//            Bitmap bm = BitmapFactory.decodeStream(url.openStream());
//            //Bitmap bm=decodeBitmap(null,url.openStream());
            if(book.getDowPhoto()!=null){
                final Bitmap bitmap = BitmapFactory.decodeFile(book.getDowPhoto());
                viewHolder.bookPhoto.setImageBitmap(bitmap);
            }else {
                viewHolder.bookPhoto.setImageBitmap(getBitmap(book.getBitmap()));
            }
            viewHolder.bookName.setText(book.getBookName());

        } catch (Exception e){
            e.printStackTrace();
        }finally {
        }
        return view;
    }


    private byte[] inputStream2ByteArr(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ( (len = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, len);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

    private Bitmap decodeBitmap(String url, InputStream is) throws IOException {
        if (is == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置该属性可以不占用内存，并且能够得到bitmap的宽高等属性，此时得到的bitmap是空
        options.inJustDecodeBounds = true;
        byte[] data = inputStream2ByteArr(is);//将InputStream转为byte数组，可以多次读取
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        //设置计算得到的压缩比例
        options.inSampleSize = 4;
        //设置为false，确保可以得到bitmap != null
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return bitmap;
    }

    class ViewHolder {

        ImageView bookPhoto;

        TextView bookName;
        Button startread;

    }

    public static Bitmap getBitmap(byte[] data){
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }

}
