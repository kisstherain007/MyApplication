package commponents;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.n930044.myapplication.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by archermind on 16-1-19.
 */
public class PagerNavigationView extends LinearLayout implements PagerNavCallback {
    public static final String TAG = "PagerNavigationView";
    /**
     * 导航的点
     */
    private List<ImageView> mImgPoints;

    private Context mContext;

    /**
     * 导航点的间距
     */
    private final static int NAV_POINT_MARGIN_DEFAULT = 14;

    private final static int NAV_POINT_SELECT_RESID = R.drawable.red_point;

    private final static int NAV_POINT_UNSELECT_RESID = R.drawable.white_point;

    private int mDotSelectImgResId = NAV_POINT_SELECT_RESID;

    private int mDotUnSelectImgResId = NAV_POINT_UNSELECT_RESID;

    private AutoPlayViewPager mViewPager;

    private LinearLayout mLayoutContainer;

    public PagerNavigationView(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public PagerNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    public PagerNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure   =====");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG, "changed   =====");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged   =====");
    }

    public void init(Context context) {
        mImgPoints = new ArrayList<ImageView>();
        View view = LayoutInflater.from(context).inflate(R.layout.view_nav_viewpager, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        mViewPager = (AutoPlayViewPager) view.findViewById(R.id.viewpager);
        mLayoutContainer = (LinearLayout) view.findViewById(R.id.ll_nav_container);
        this.addView(view);
    }

    public void initNav(int size) {
        Log.i(TAG, "initNav  size==" + size);
        mLayoutContainer.removeAllViews();
        mImgPoints.clear();
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            int dip = 7;//设备像素

            LayoutParams params = new LayoutParams(DensityUtil.dip2px
                    (mContext, dip),
                    DensityUtil.dip2px(mContext, dip));
            if (i != 0) {
                params.setMargins(DensityUtil.dip2px(mContext, dip), 0, 0, 0);
                setDotIamgeRes(imageView, mDotUnSelectImgResId);
            } else {
                setDotIamgeRes(imageView, mDotSelectImgResId);
            }
            imageView.setLayoutParams(params);
            mImgPoints.add(imageView);
            mLayoutContainer.addView(imageView);
        }
        this.invalidate();
    }

    /**
     * 设置导航点
     *
     * @param ResId
     */
    public void setDotIamgeRes(ImageView img, int ResId) {
        if (img != null) {
            try {
                img.setImageResource(ResId);
            } catch (Resources.NotFoundException e) {
                Log.e(TAG, "setDotIamgeRes", e);
            }
        }
    }

    public void pagerChange(int curPager) {
//        Log.i(TAG, "pagerChange  curPager==" + curPager);
        if (mImgPoints.size() > 0 && curPager > 0) {
            int position = curPager % mImgPoints.size();
            for (int i = 0; i < mImgPoints.size(); i++) {
                ImageView imageView = mImgPoints.get(i);
                if (i == position) {
                    setDotIamgeRes(imageView, mDotSelectImgResId);
                } else {
                    setDotIamgeRes(imageView, mDotUnSelectImgResId);
                }
            }
        }
        invalidate();
    }

    public void setmDotSelectImgResId(int mDotSelectImgResId) {
        this.mDotSelectImgResId = mDotSelectImgResId;
    }

    public void setmDotUnSelectImgResId(int mDotUnSelectImgResId) {
        this.mDotUnSelectImgResId = mDotUnSelectImgResId;
    }

    /**
     * @param isAutoPlay
     */
    public void setAutoPlay(boolean isAutoPlay) {
        mViewPager.setAutoPlay(isAutoPlay);
    }


    public AutoPlayViewPager getmViewPager() {
        return mViewPager;
    }

    @Override
    public void notfiyInit(int size) {
        initNav(size);
    }

    @Override
    public void notifyPagerChanged(int position) {
        pagerChange(position);
    }
}



