package com.example.zhuyihaotest1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class MyListAdapter extends BaseAdapter {
    private Context mcontext;
    private LayoutInflater mlayoutInflater;
    private LinkedList<User> mData;//user队列
    public MyListAdapter(Context context){
        this.mcontext=context;
        mlayoutInflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder{
        public ImageView imageView;
        public TextView textView1 ,textView2,textView3 ;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view=mlayoutInflater.inflate(R.layout.layout_list_item,null);
            holder=new ViewHolder();
            holder.imageView=view.findViewById(R.id.iv_user);
            holder.textView1=view.findViewById(R.id.User_rank);
            holder.textView2=view.findViewById(R.id.User_name);
            holder.textView3=view.findViewById(R.id.User_number);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }

            holder.textView1.setText("1");
            holder.textView2.setText("2");
            holder.textView3.setText("3");


        // Glide.with(this).load("url").into(holder.imageView);
        return view;
    }
}
