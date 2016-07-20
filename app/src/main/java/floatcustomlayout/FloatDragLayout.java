package floatcustomlayout;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.n930044.myapplication.R;

/**
 * Created by archermind on 16-7-20.
 *
 * getWidth(): View在设定好布局后整个View的宽度。
 * getMeasuredWidth(): 对View上的内容进行测量后得到的View内容佔据的宽度，
 * 前提是你必须在父布局的onLayout()方法或者此View的onDraw()方法裡调 用measure(0,0);
 * (measure 参数的值你可以自己定义)，否则你得到的结果和getWidth()得到的结果一样。
 *
 * http://blog.csdn.net/easyer2012/article/details/37900583
 * http://blog.csdn.net/mr_dsw/article/details/50510674 详解
 */
public class FloatDragLayout extends ViewGroup {

    public static final String TAG = FloatDragLayout.class.getSimpleName();

    ViewDragHelper mDragHelper = null;
    private ViewDragHelper.Callback callback = null;

    private int mHeaderHeight = 0;
    View headerLayout;

    public FloatDragLayout(Context context) {
        super(context);
        initViewDragHelper();
    }

    public FloatDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewDragHelper();
    }

    void initViewDragHelper(){
        callback = new DraggerCallBack();
        mDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        headerLayout = findViewById(R.id.header_layout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int height = 0;
        int width = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++){

            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            height += childHeight;
            width += childWidth;
            Log.d("aaaa", childHeight+"");
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width,
                (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
        measure(MeasureSpec.EXACTLY + getMeasuredWidth(), MeasureSpec.UNSPECIFIED);
        int currentHeight = 0;
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            Log.d(TAG, "getMeasureHeight" + getMeasuredHeight());

            childView.layout(0, currentHeight, childWidth, currentHeight + childHeight);

            currentHeight += childHeight;
        }

        mHeaderHeight = headerLayout.getMeasuredHeight();
        Log.d(TAG, "mHeaderHeight:" + mHeaderHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    class DraggerCallBack extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            Log.d(TAG, "top:" + top);

            if (top <= -mHeaderHeight){
                return -mHeaderHeight;
            }

            if (top >= 0){
                return 0;
            }

            return top;
        }
    }
}
