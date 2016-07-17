package com.example.n930044.myapplication;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import commponents.MainViewPagerAdapter;
import commponents.PagerNavigationView;
import floatcustomlayout.ListViewFragment;
import floatcustomlayout.ScrollViewFragment;

public class FloatMainActivity extends FragmentActivity {

	DisplaySpecLayout mDisplaySpecLayout;
	private ViewPager mFloatContent;
	PagerNavigationView pagerNavigationView;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private MainViewPagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float_activity_main);
		initView();
	}

	private void initData() {
		List<String> imgList = new ArrayList<String>();
		imgList.add("1");
		imgList.add("1");
		imgList.add("1");
		imgList.add("1");
		imgList.add("1");
		imgList.add("1");
		mPagerAdapter.setData(imgList);
	}

	private void initView() {

		pagerNavigationView = (PagerNavigationView) findViewById(R.id.pagerNavigationView);
		mPagerAdapter = new MainViewPagerAdapter(new ArrayList<String>(), this, pagerNavigationView);
		pagerNavigationView.getmViewPager().setAdapter(mPagerAdapter);
		pagerNavigationView.getmViewPager().addOnPageChangeListener(mPagerAdapter);
		pagerNavigationView.setAutoPlay(true);
		pagerNavigationView.getmViewPager().setCurrentItem(Integer.MAX_VALUE / 2);
		mFloatContent = (ViewPager) findViewById(R.id.float_layout_content);
		mFragments.add(ListViewFragment.getInstain());
		mFragments.add(ScrollViewFragment.getInstain());
		mFloatContent.setAdapter(new MyAdapter(getSupportFragmentManager(), mFragments));

		mDisplaySpecLayout = (DisplaySpecLayout) findViewById(R.id.custom_view);
		List<String> datas = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			datas.add("index:" + i);
		}

		mDisplaySpecLayout.setGoodsSizeDataOnDisplaySpecLayout(datas);

		initData();
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
