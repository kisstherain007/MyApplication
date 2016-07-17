package commponents;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by archermind on 16-1-18.
 */
public class AutoPlayViewPager extends ViewPager {
    public static final String TAG = "AutoPlayViewPager";
    private boolean isAutoPlay;
    /**
     * 请求更新显示的View。
     */
    protected static final int MSG_UPDATE_IMAGE = 1;

    //轮播间隔时间
    protected static final long MSG_DELAY = 3000;

    private AutoPlayHandler mAutoPlayHandler;

    public void setAutoPlay(boolean isAutoPlay){
        this.isAutoPlay =isAutoPlay;
        if (isAutoPlay){
            mAutoPlayHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
        }else{
            mAutoPlayHandler.removeMessages(MSG_UPDATE_IMAGE);
        }
    };


    public AutoPlayViewPager(Context context) {
        super(context);
        mAutoPlayHandler = new AutoPlayHandler();
    }

    public AutoPlayViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAutoPlayHandler = new AutoPlayHandler();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mAutoPlayHandler.removeMessages(MSG_UPDATE_IMAGE);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mAutoPlayHandler != null) {
                    mAutoPlayHandler.removeMessages(MSG_UPDATE_IMAGE);
                    mAutoPlayHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    class AutoPlayHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_UPDATE_IMAGE&&isAutoPlay) {
//                Log.i(TAG, "isAutoPlay  ==" + isAutoPlay);
                    AutoPlayViewPager.this.setCurrentItem(AutoPlayViewPager.this.getCurrentItem() + 1,true);
                    this.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                }
            }
        }
    }

