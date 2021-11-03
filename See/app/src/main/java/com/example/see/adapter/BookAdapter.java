package com.example.see.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import com.example.see.MainActivity;
import com.example.see.R;
import com.example.see.ShowActivity;
import com.example.see.domain.Book;
import com.example.see.domain.MiniBook;
import com.example.see.util.Consts;

import java.io.*;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookAdapter extends ArrayAdapter<Book> {

    private int resourceId;
    private URL murl;
    public int m_var; //需要在消息处理中访问的成员变量，一定要声明成public
    List<Book> dowBooks=new ArrayList<>();
    List<Book> books=null;

    public BookAdapter(Context context, int textViewResourceId,
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
            viewHolder.bookPhoto = (ImageView) view.findViewById (R.id.bookPhoto);
            viewHolder.bookName = (TextView) view.findViewById (R.id.bookName);
            viewHolder.booklinearlayout=(LinearLayout)view.findViewById(R.id.booklinearlayout);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.booklinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "我是linealayout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), ShowActivity.class);
                MiniBook miniBook=new MiniBook();
                miniBook.setBookId(book.getBookId());
                miniBook.setBookAuthor(book.getBookAuthor());
                miniBook.setBookName(book.getBookName());
                miniBook.setBookPhoto(book.getBookPhoto());
                miniBook.setDowPhoto(book.getDowPhoto());
                intent.putExtra("book",miniBook);
                v.getContext().startActivity(intent);
            }
        });
        try {
//            URL url = new URL(Consts.PHOTO_BASE_URI+book.getBookPhoto());
//            System.out.println(url.openStream().read());
//            Bitmap bm = BitmapFactory.decodeStream(url.openStream());
            //Bitmap bm=decodeBitmap(null,url.openStream());

//            if(book.getDowPhoto()!=null){
//                final Bitmap bitmap = BitmapFactory.decodeFile(book.getDowPhoto());
//                viewHolder.bookPhoto.setImageBitmap(bitmap);
//            }else {

                viewHolder.bookPhoto.setImageBitmap(getBitmap(book.getBitmap()));
//            }
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


    public static Bitmap getBitmap(byte[] data){
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }
    class ViewHolder {

        ImageView bookPhoto;

        TextView bookName;
        LinearLayout booklinearlayout;

    }






}
