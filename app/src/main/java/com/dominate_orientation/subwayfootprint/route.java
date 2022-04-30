package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class route extends AppCompatActivity {
    String msg="";
    Info[] infos = null;
    Intent it = null;

    TextView ls =null;
    TextView ps = null;
    TextView ns = null;

    Boolean no_next_passed_station = false;
    Boolean last_station_used = false;
    Boolean no_station_left=false;
    Integer infos_index = 0;
    Integer passed_station_index = 0;

    Button go_to_next=null;
    Button dig = null;
    Button ensconce = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        it = getIntent();
        msg = it.getStringExtra("msg");
        ls = (TextView) findViewById(R.id.last_station);
        ps = (TextView) findViewById(R.id.pre_station);
        ns = (TextView) findViewById(R.id.next_station);
        go_to_next=(Button)findViewById(R.id.go_to_next_station);
        dig =(Button)findViewById(R.id.dig);
        ensconce =(Button)findViewById(R.id.ensconce);
        ensconce.setEnabled(false);

        /*new Thread()
        {
            @Override
            public void run() {
                while(!no_station_left);
                go_to_next.setEnabled(false);
            }
        }.start();*/

        Calculate();
        init_texts();
    }

    public void ls(String s){ ls.setText(s);}
    public void ps(String s){ ps.setText(s);}
    public void ns(String s){ ns.setText(s);}
    public void ls(CharSequence s){ ls.setText(s);}
    public void ps(CharSequence s){ ps.setText(s);}
    public void ns(CharSequence s){ ns.setText(s);}

    public void init_texts()
    {
        ps(infos[infos_index].start);
        if(infos[infos_index].passed.length>0)
        {
            ns(infos[infos_index].passed[passed_station_index]);
            passed_station_index++;
        }else
        {
            ns(infos[infos_index].end);
            last_station_used = true;
        }
    }

    public void go_to_next_station(View view)
    {
        ls(ps.getText());
        ps(ns.getText());

        if(no_next_passed_station)
        {
            if(ns.getText().toString().indexOf("广")!=-1)
            {
                System.out.println("lmao");
            }
            if(last_station_used)
            {//infos_index add 1, clear two false status
                if(infos_index<infos.length-1){
                    ns(infos[++infos_index].start);
                    last_station_used = false;
                    no_next_passed_station = false;
                }
                else
                {
                    go_to_next.setEnabled(false);
                    ensconce.setEnabled(true);
                    ps(ns.getText());
                    ns("已到达！可以挖宝啦");
                }
            }else
            {
                ns(infos[infos_index].end);
                last_station_used = true;
            }
        }else{
            if(passed_station_index>=infos[infos_index].passed.length)
            {
                ns(infos[infos_index].end);
                last_station_used = true;
                no_next_passed_station = true;
            }else{
                ns(infos[infos_index].passed[passed_station_index++]);
            }
        }
    }

    public void Calculate()
    {
        String[] set = msg.split(".line.");
        infos=new Info[set.length-1];
        int index=0;
        for(int i=1;i<set.length;i++)
        {
            infos[index] = new Info();
            if(set[i].length()>0) {
                String[] tmp1 = set[i].split(".sn.");
                infos[index].line = tmp1[0];
                if(tmp1[1].indexOf(".pn.")!=-1) {
                    String[] tmp2 = tmp1[1].split(".pn.");
                    infos[index].start = tmp2[0];
                    String[] tmp3 = tmp2[1].split(".en.");
                    infos[index].passed = tmp3[0].split(" ");// 可能是空
                    infos[index].end = tmp3[1];
                }else
                {
                    String[] tmp2 = tmp1[1].split(".en.");
                    infos[index].start = tmp2[0];// 可能是空
                    infos[index].end = tmp2[1];
                }
            }
            index++;
        }
    }

    public void go_back(View view)
    {
        finish();
    }

    //挖宝
    public void dig(View view){

    }

    //藏宝
    //我已设定只有到终点此按钮才能互动
    public void ensconce(View view){

    }
}


class Info{
    String line="";
    String start="";
    String end="";
    String[] passed={};
}
