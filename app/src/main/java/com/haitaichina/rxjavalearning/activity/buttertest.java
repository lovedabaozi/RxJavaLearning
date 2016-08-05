package com.haitaichina.rxjavalearning.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.haitaichina.rxjavalearning.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张峰林
 * Created on 2016/8/5.
 * Description：
 */
public class Buttertest extends AppCompatActivity {

    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.bt_base0)
    Button btBase0;
    @BindView(R.id.bt_base)
    Button btBase;
    @BindView(R.id.bt_base2)
    Button btBase2;
    @BindView(R.id.bt_base3)
    Button btBase3;
    @BindView(R.id.bt_base4)
    Button btBase4;
    @BindView(R.id.bt_base5)
    Button btBase5;
    @BindView(R.id.bt_base6)
    Button btBase6;
    @OnClick(R.id.bt_base0) void  yy(){
        Toast.makeText(Buttertest.this,"---",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
