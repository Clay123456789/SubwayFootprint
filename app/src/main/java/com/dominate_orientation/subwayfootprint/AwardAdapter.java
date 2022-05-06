package com.dominate_orientation.subwayfootprint;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.LinkedList;

public class AwardAdapter extends BaseAdapter {
    LinkedList<Award> awardLinkedList;
    Context mContext;
    LayoutInflater mlayoutInflater;



    public AwardAdapter(LinkedList<Award> awardLinkedList, buryTreasure mContext) {
        this.awardLinkedList = awardLinkedList;
        this.mContext = mContext;
        mlayoutInflater=LayoutInflater.from(mContext);
    }

    static class ViewHolder{
        public TextView award_variety,award_name;
        public ImageView award_content_img;
        public Button award_button;
        public RadioButton award_choice_button;
    }


    @Override
    public int getCount() {
        return awardLinkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AwardAdapter.ViewHolder holder=null;
        if(convertView==null){
            convertView=mlayoutInflater.inflate(R.layout.item_list_award,null);
            holder=new AwardAdapter.ViewHolder();
            holder.award_variety = convertView.findViewById(R.id.award_variety);
            holder.award_name =  convertView.findViewById(R.id.award_name);
            holder.award_content_img =  convertView.findViewById(R.id.award_img);
            holder.award_button=convertView.findViewById(R.id.check_award);
            holder.award_choice_button=convertView.findViewById(R.id.award_radio_button);
            convertView.setTag(holder);
        }else{
            holder=(AwardAdapter.ViewHolder) convertView.getTag();
        }
        String s1=String.valueOf(awardLinkedList.get(position).getVariety());
        holder.award_variety.setText(s1);
        String s2=String.valueOf(awardLinkedList.get(position).getName());
        holder.award_name.setText(s2);
        String s3=String.valueOf(awardLinkedList.get(position).getVariety());
        if(s3.equals("优惠券")){

            holder.award_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.coupon));
            //   imageview.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_2));
        }else if(s3.equals("系统称号")){
            holder.award_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.title));

        }else if(s3.equals("碳积分")){
            holder.award_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.co2));

        }else if(s3.equals("实体物品")){
            holder.award_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.things));
        }

        holder.award_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent(mContext, main_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不是在Activity中进行跳转，需要添加这个方法
                mContext.startActivity(intent);
//                User_treasure user_treasure=mData.get(position);
//
//                intent.putExtra("PERSON_INFO", user_treasure);
//                mContext.startActivity(intent);
            }
        });


        return convertView;
    }
}
