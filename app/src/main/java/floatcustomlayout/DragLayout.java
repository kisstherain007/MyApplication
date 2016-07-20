package floatcustomlayout;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.n930044.myapplication.R;

/**
 * Created by n930044 on 2016/7/16.
 */
public class DragLayout extends LinearLayout {

    public static final String TAG = DragLayout.class.getSimpleName();

    private ViewDragHelper mDragHelper = null;
    private ViewDragHelper.Callback callback = null;
    LinearLayout rootLayout;
    LinearLayout containerView;
    LinearLayout headerView;
    LinearLayout floatView;
    ViewPager mContent;

    Context mContext;
    private Point targerPointPosition = new Point();
    private Point initPointPosition = new Point();
    private Point nextPointPosition = new Point();
    WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);

    public DragLayout(Context context) {
        super(context);
        this.mContext = context;
        callback = new DraggerCallBack();
        mDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        callback = new DraggerCallBack();
        mDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        containerView = (LinearLayout) findViewById(R.id.main_container);
        headerView = (LinearLayout) findViewById(R.id.header_layout);
        floatView = (LinearLayout) findViewById(R.id.float_layout);
        mContent = (ViewPager) findViewById(R.id.content_layout);
        getCurrentScrollView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged");
        mHeaderHeight = headerView.getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");

        // reset container height
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels / 2;  //得到高度

        // header
        ViewGroup.LayoutParams layoutParamsHeader = headerView.getLayoutParams();
        layoutParamsHeader.height = height;

        // container
        ViewGroup.LayoutParams layoutParamsContainer = containerView.getLayoutParams();
        layoutParamsContainer.height = getMeasuredHeight() + floatView.getMeasuredHeight();

        // reset content height
        ViewGroup.LayoutParams layoutParamsCotnent = mContent.getLayoutParams();
        layoutParamsCotnent.height = getMeasuredHeight() - floatView.getMeasuredHeight();

        // root
        ViewGroup.LayoutParams layoutParamsRoot = rootLayout.getLayoutParams();
        layoutParamsRoot.height = getMeasuredHeight() + floatView.getMeasuredHeight();
        layoutParamsRoot.height = layoutParamsCotnent.height + floatView.getMeasuredHeight() + layoutParamsHeader.height;
    }

    boolean isHidenHeader = false; // 默认header显示
    boolean isCanScrollcontainerView = true;

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    class DraggerCallBack extends ViewDragHelper.Callback{

        //这个地方实际上函数返回值为true就代表可以滑动 为false 则不能滑动
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return isCanScrollcontainerView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            Log.d("DraggerCallBack", "top:" + top);

            if (Math.abs(top) >= mHeaderHeight){

                isHidenHeader = true;
            }else {

                isHidenHeader = false;
            }

            final int topBound = getPaddingTop() - mHeaderHeight;
            final int bottomBound = 0;

            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            //松手的时候 判断如果是这个view 就让他回到起始位置
            if (releasedChild == containerView) {

            Log.d("xxxx", "xvel" + xvel);
            Log.d("xxxx", "yvel" + yvel);
            Log.d("xxxx", "releasedChild.getY()" + releasedChild.getY());
            Log.d("xxxx", "headerView.getHeight()" + headerView.getHeight());

            if ((releasedChild.getY() < -(headerView.getHeight()/3)) && (targerPointPosition == initPointPosition)){ // 下滑
                Log.d("xxxx", "下滑");
                targerPointPosition = nextPointPosition;
            }else if((releasedChild.getY() > (500 - (headerView.getHeight()))) && (targerPointPosition == nextPointPosition)){
                targerPointPosition = initPointPosition;
                Log.d("xxxx", "上滑");
            }
                mDragHelper.settleCapturedViewAt(targerPointPosition.x, targerPointPosition.y);
                ViewCompat.postInvalidateOnAnimation(DragLayout.this);
            }
        }

        /**
         * 当拖拽到状态改变时回调
         * @params 新的状态
         */
        @Override
        public void onViewDragStateChanged(int state) {
            switch (state) {
                case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动

                    break;
                case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者 正在进行fling/snap

                    break;
                case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置

                    break;
            }
            super.onViewDragStateChanged(state);
        }
}

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //布局完成的时候就记录一下位置
        initPointPosition.x = containerView.getLeft();
        initPointPosition.y = containerView.getTop();

        nextPointPosition.x = floatView.getLeft();
        nextPointPosition.y = -floatView.getTop();

        targerPointPosition = initPointPosition;

        Log.d("ktr", nextPointPosition.toString());
    }

    float mInitialMotionY;
    float moveY;
    // header 显示的时候,ListView不能滑动,header完全隐藏的时候ListView才可以滑动
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("DragLayout", "onInterceptTouchEvent");

        //决定是否拦截当前事件
        final int action = MotionEventCompat.getActionMasked(ev);

        final float x = ev.getX();
        final float y = ev.getY();

        boolean temp = false;

        switch (action){

            case MotionEvent.ACTION_DOWN:
                mInitialMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DragLayout", "ACTION_MOVE");
                getCurrentScrollView();
                moveY = y - mInitialMotionY;
                // 拦截 下滑/header没有显示/listView 第一行在顶端
                if (mInnerScrollview instanceof ListView) {
                    ListView listView = (ListView) mInnerScrollview;
                    View viewItem = listView.getChildAt(listView.getFirstVisiblePosition());
                    Log.d("DragLayout", " moveY:"+ moveY + " isHidenHeader:" + isHidenHeader + " viewItem.getTop():" + (viewItem == null ? "null" : viewItem.getTop()));

                    if (moveY > 0 && isHidenHeader && ((viewItem != null && viewItem.getTop() == 0))){
                        isHidenHeader = false; //拦截
                    }

//                    if (moveY <0 && !isHidenHeader && ((viewItem != null && viewItem.getTop() == 0))){
//                        return (temp = true); //拦截
//                    }
                }
                break;
        }

        // movey > 0 下划
        if (isHidenHeader){

            return false; // 不拦截 ListView 滑动
        }else {
            return true; //拦截
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = MotionEventCompat.getActionMasked(event);
        float y = event.getY();
        switch (action){

            case MotionEvent.ACTION_DOWN:
                Log.d("DragLayout", "onTouchEvent: ACTION_DOWN");
                mInitialMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DragLayout", "onTouchEvent: ACTION_MOVE");
                float moveY = y - mInitialMotionY;
                // 拦截 下滑/header没有显示/listView 第一行在顶端
                if (mInnerScrollview instanceof ListView) {
                    ListView listView = (ListView) mInnerScrollview;
                    View viewItem = listView.getChildAt(listView.getFirstVisiblePosition());
                    Log.d("DragLayout", " moveY:"+ moveY + " isHidenHeader:" + isHidenHeader + " viewItem.getTop():" + (viewItem == null ? "null" : viewItem.getTop()));

                    if (moveY > 0 && isHidenHeader && ((viewItem != null && viewItem.getTop() == 0))){

                        isHidenHeader = false; //拦截
                    }
                }

                break;
        }

        try {
            if(!isHidenHeader){

                mDragHelper.processTouchEvent(event);
            }else {
//
                return false;
            }
        } catch (Exception e) {
        }

        return true;
    }

    int mHeaderHeight = 0;

    private ViewGroup mInnerScrollview;

    private void getCurrentScrollView(){
        int cuttentItem = mContent.getCurrentItem();
        PagerAdapter pagerAdapter = mContent.getAdapter();
        if (pagerAdapter instanceof FragmentPagerAdapter) {
            FragmentPagerAdapter adapter = (FragmentPagerAdapter) pagerAdapter;
            Fragment fragment = adapter.getItem(cuttentItem);
            mInnerScrollview = (ViewGroup) fragment.getView().findViewById(R.id.float_layout_inner_view);
        } else if (pagerAdapter instanceof FragmentStatePagerAdapter) {
            FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) pagerAdapter;
            Fragment fragment = adapter.getItem(cuttentItem);
            mInnerScrollview = (ViewGroup) fragment.getView().findViewById(R.id.float_layout_inner_view);
        }
    }
}
