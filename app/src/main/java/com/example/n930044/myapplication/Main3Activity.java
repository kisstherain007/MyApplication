package com.example.n930044.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import floatcustomlayout.CSDNDragLayout;
import floatcustomlayout.DragLayout;

public class Main3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.csdn_drager_layout);
        TextView tv_behind = (TextView) findViewById(R.id.tv_behind);
        tv_behind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(Main3Activity.this, "tv_behind clicked", Toast.LENGTH_SHORT).show();
            }
        });

        final CSDNDragLayout dragLayout = (CSDNDragLayout) findViewById(R.id.dragLayout);

//		dragLayout.getHandleView().setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				dragLayout.toggleSlide();
//			}
//		});

//		dragLayout.setOnScrollListener(new DragLayout.OnScrollListener() {
//
//			@Override
//			public void onScroll(View view, float fraction) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onOpen(View view) {
//				// TODO Auto-generated method stub
//				Log.i("tag", "============onOpen============");
//			}
//
//			@Override
//			public void onClose(View view) {
//				// TODO Auto-generated method stub
//				Log.i("tag", "============onClose============");
//			}
//		});

        TextView tv_head = (TextView) findViewById(R.id.tv_head);
        TextView tv_foot = (TextView) findViewById(R.id.tv_foot);
        tv_head.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(Main3Activity.this, "tv_head clicked", Toast.LENGTH_SHORT).show();
            }
        });
        tv_foot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(Main3Activity.this, "tv_content clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
