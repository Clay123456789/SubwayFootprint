package com.dominate_orientation.subwayfootprint;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class TreasureUserAdapter extends BaseAdapter {
    private LinkedList<User_treasure> mData;
    private Context mContext;
    private LayoutInflater mlayoutInflater;

    public TreasureUserAdapter(LinkedList<User_treasure> mData, Context context) {
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

        public TextView tre_content,tre_item,tre_credit;
        public ImageView tre_content_img;
        public Button tre_button;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TreasureUserAdapter.ViewHolder holder=null;
        if(convertView==null){
            convertView=mlayoutInflater.inflate(R.layout.item_list_treasure_user,null);
            holder=new TreasureUserAdapter.ViewHolder();
            holder.tre_item = convertView.findViewById(R.id.T_U_1);
            holder.tre_content_img =  convertView.findViewById(R.id.T_U_3);
            holder.tre_content =  convertView.findViewById(R.id.T_U_4);
            holder.tre_button=convertView.findViewById(R.id.buttonT);
            holder.tre_credit=convertView.findViewById(R.id.T_U_5);
            convertView.setTag(holder);
        }else{
            holder=(TreasureUserAdapter.ViewHolder) convertView.getTag();
        }
        String s1=String.valueOf(mData.get(position).getVariety());
        holder.tre_item.setText(s1);
        String s2=String.valueOf(mData.get(position).getContent());

        holder.tre_content.setText(s2);

        String s3=mData.get(position).getVariety();
        if(s3.equals("优惠券")){

            holder.tre_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.coupon));
         //   imageview.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_2));
        }else if(s3.equals("系统称号")){
            holder.tre_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.title));

        }else if(s3.equals("碳积分")){
            holder.tre_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.co2));

        }else if(s3.equals("实体物品")){
            holder.tre_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.things));
        }

        if(mData.get(position).getCredit().equals("1")){
            holder.tre_credit.setText("已打开");

        }else{
            holder.tre_credit.setText("未打开"); holder.tre_button.setText("打开宝箱");
        }

        holder.tre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent(mContext, ShowAllMessage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不是在Activity中进行跳转，需要添加这个方法
                mContext.startActivity(intent);
                User_treasure user_treasure=mData.get(position);

                intent.putExtra("PERSON_INFO", user_treasure);
                mContext.startActivity(intent);
            }
        });

        return convertView;


    }
}
