package floatcustomlayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.n930044.myapplication.R;

/**
 * 
 * @author pythoner
 * @since 2015-10-23
 *
 */
public class CSDNDragLayout extends LinearLayout {

	private ViewDragHelper mDragHelper;
	private View headView, handleView, footView;// �����ؼ�
	private int headViewHeight, handleViewHeight, footViewHeight;// �����ؼ��߶�
	private boolean isOpen = false;

	public CSDNDragLayout(Context context) {
		this(context, null);
	}

	public CSDNDragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CSDNDragLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
		setOrientation(LinearLayout.VERTICAL);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		headView = findViewById(R.id.headView);
		handleView = findViewById(R.id.handleView);
		footView = findViewById(R.id.footView);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		headViewHeight = headView.getMeasuredHeight();
		handleViewHeight = handleView.getMeasuredHeight();
		footViewHeight = footView.getMeasuredHeight();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = MotionEventCompat.getActionMasked(ev);
		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			mDragHelper.cancel();
			return false;
		}
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			mDragHelper.processTouchEvent(ev);
		} catch (Exception e) {
			// TODO: handle exception
		}
		final float x = ev.getX();  
	    final float y = ev.getY();
		boolean isViewUnder = mDragHelper.isViewUnder(handleView, (int) x, (int) y); 
		return isViewUnder;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	private boolean isViewHit(View view, int x, int y) {  
	    int[] viewLocation = new int[2];  
	    view.getLocationOnScreen(viewLocation);  
	    int[] parentLocation = new int[2];  
	    this.getLocationOnScreen(parentLocation);  
	    int screenX = parentLocation[0] + x;  
	    int screenY = parentLocation[1] + y;  
	    return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() &&  
	            screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();  
	}
	
//	@Override  
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
//	    measureChildren(widthMeasureSpec, heightMeasureSpec);  
//	    int maxWidth = MeasureSpec.getSize(widthMeasureSpec);  
//	    int maxHeight = MeasureSpec.getSize(heightMeasureSpec);  
//	    setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),  
//	            resolveSizeAndState(maxHeight, heightMeasureSpec, 0));  
//	}
	class DragHelperCallback extends ViewDragHelper.Callback {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return child == handleView;// ֻ�а��ֿ�����ק
		}

		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			// final int topBound = getPaddingTop();
			// ֻ��Y�����Ͽ����϶������϶���Χ����HeadView֮��
			final int topBound = getPaddingTop() + headViewHeight;
			final int bottomBound = getHeight() - handleViewHeight;
			final int newTop = Math.min(Math.max(top, topBound), bottomBound);
			return newTop;
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			// ���¿��ٻ����������»�������һ���ContentViewʱ�������϶��������
			if (yvel > 0 || releasedChild.getY() > headViewHeight + footViewHeight / 2) {
				mDragHelper.settleCapturedViewAt((int) handleView.getX(), getHeight() - handleViewHeight);
				if (onScrollListener != null) {
					onScrollListener.onOpen(releasedChild);
				}
				isOpen = true;
			} else {
				mDragHelper.settleCapturedViewAt((int) handleView.getX(), headViewHeight);
				if (onScrollListener != null) {
					onScrollListener.onClose(releasedChild);
				}
				isOpen = false;
			}
			// ��Ҫinvalidate()�Լ����computeScroll����һ��
			invalidate();
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);
			headView.setY(headView.getY() - dy);
			// �˴�����ʹ��setY()�������϶�����ȥ�ˣ�why?���Բ����˱�ͨ��layout()�ı�λ��
//			 contentView.setY(contentView.getY() + dy);
			footView.layout(footView.getLeft(), (int) (footView.getY() + dy), footView.getRight(),
					footView.getBottom());
			float fraction = (top - headViewHeight) * 1.00f / footViewHeight;// ����λ�Ʊ���
			getBackground().setAlpha((int) (255 * (1 - fraction)));// �������ݻ������ʲ�������Ч��
			if (onScrollListener != null) {
				onScrollListener.onScroll(changedView, fraction);
			}
		}

	}

	public void toggleSlide() {
		int y = 0;
		if (isOpen) {// ��
			y = getPaddingTop() + headViewHeight;
		} else {// �ر�
			y = getHeight() - handleViewHeight;
		}
		isOpen = !isOpen;

		if (mDragHelper.smoothSlideViewTo(handleView, handleView.getLeft(), y)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	public View getHandleView(){
		return handleView;
	}
	public View getHeadView(){
		return headView;
	}
	public View getFootView(){
		return footView;
	}
	
	private OnScrollListener onScrollListener;

	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	interface OnScrollListener {
		void onScroll(View view, float fraction);

		void onOpen(View view);

		void onClose(View view);
	}
}
