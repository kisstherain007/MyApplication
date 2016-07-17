package com.example.n930044.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import floatcustomlayout.ListViewFragment;
import floatcustomlayout.ScrollViewFragment;

public class Main2Activity extends AppCompatActivity {

    DrawerLayout d;
    private ViewPager mFloatContent;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drager_layout);

        mFloatContent = (ViewPager) findViewById(R.id.content_layout);
        mFragments.add(ListViewFragment.getInstain());
        mFragments.add(ScrollViewFragment.getInstain());
        mFloatContent.setAdapter(new MyAdapter(getSupportFragmentManager(), mFragments));
    }

    class MyAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            mList = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return mList.get(arg0);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
