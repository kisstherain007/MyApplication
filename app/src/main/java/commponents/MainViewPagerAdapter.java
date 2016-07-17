package commponents;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.n930044.myapplication.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 滚动的viewpager的适配器
 * Created by archermind on 16-1-18.
 */
public class MainViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    public static final String TAG = "MainViewPagerAdapter";
    private List<String> mAdvtList = new ArrayList<String>();

    private Context mContext;

    private PagerNavCallback mPagerNavCallback;

    int[] arrBannerRes = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

    public MainViewPagerAdapter(List<String> mImgList, Context mContext, PagerNavCallback mPagerNavCallback) {
        this.mAdvtList = mImgList;
        this.mContext = mContext;
        this.mPagerNavCallback = mPagerNavCallback;
        if (this.mPagerNavCallback != null) {
            this.mPagerNavCallback.notfiyInit(mImgList != null ? mImgList.size() : 0);
        }
    }

    public void setData(List<String> advtDetailsList) {
        Log.d(TAG, "setData========");
        if (advtDetailsList != null) {
            mAdvtList.clear();
            mAdvtList.addAll(advtDetailsList);
            this.notifyDataSetChanged();
            if (this.mPagerNavCallback != null) {
                this.mPagerNavCallback.notfiyInit(mAdvtList != null ? mAdvtList.size() : 0);
            }
        }
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(arrBannerRes[position % arrBannerRes.length]);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mPagerNavCallback != null) {
            mPagerNavCallback.notifyPagerChanged(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
