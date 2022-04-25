package com.example.zhuyihaotest1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class HistoryAdapter extends BaseAdapter {
    private LinkedList<History> mData;
    private Context mContext;
    private LayoutInflater mlayoutInflater;

    public HistoryAdapter(LinkedList<History> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
        mlayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
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
        public TextView txt_operation,txt_num,txt_balance,txt_time,txt_way ;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mlayoutInflater.inflate(R.layout.item_list_history,null);
            holder=new ViewHolder();
            holder.txt_operation = convertView.findViewById(R.id.History_1);
            holder.txt_num =  convertView.findViewById(R.id.History_2);
            holder.txt_balance =  convertView.findViewById(R.id.History_3);
            holder.txt_way=convertView.findViewById(R.id.History_4);
            holder.txt_time = convertView.findViewById(R.id.History_5);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        if(mData.get(position).isOperation()==1){
            holder.txt_operation.setText("增加");

        }else{
            holder.txt_operation.setText("减少");
        }
        String s1=String.valueOf(mData.get(position).getNum());
        holder.txt_num.setText(s1);
        String s2=String.valueOf(mData.get(position).getBalance());
        holder.txt_balance.setText(s2);
        holder.txt_way.setText(mData.get(position).getWay());
        holder.txt_time.setText(mData.get(position).getTime());
     // Glide.with(this).load("url").into(holder.imageView);
        return convertView;


    }
}
