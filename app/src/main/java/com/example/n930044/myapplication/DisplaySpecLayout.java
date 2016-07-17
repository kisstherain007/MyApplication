package com.example.n930044.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n930044 on 2016/7/15.
 */
public class DisplaySpecLayout extends LinearLayout implements View.OnTouchListener{

    public static final int DEFAULT_COLUMNCOUNT_1 = 1; // 默认3列
    public static final int DEFAULT_COLUMNCOUNT_3 = 3; // 默认3列

    private List<View> contentViewList = new ArrayList<>();

    Context mContext;
    private LayoutInflater mInflater;
    GridLayout mGridLayout;
    private List<String> mDatas = null;

    public DisplaySpecLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    public DisplaySpecLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mInflater = LayoutInflater.from(mContext);
            mGridLayout = (GridLayout) findViewById(R.id.display_grid);
        }
    }

    /**
     * 设置尺寸类型的数据
     * @param datas
     */
    public void setGoodsSizeDataOnDisplaySpecLayout(List<String> datas){
        setDataOnDisplaySpecLayout(datas, DEFAULT_COLUMNCOUNT_3);
    }

    /**
     * 设置服务类型的数据
     * @param datas
     */
    public void setGoodsServiceSizeDataOnDisplaySpecLayout(List<String> datas){
        setDataOnDisplaySpecLayout(datas, DEFAULT_COLUMNCOUNT_1);
    }


    public void setDataOnDisplaySpecLayout(List<String> datas, int columnCount){
        this.resetGridLayout(columnCount);

        mDatas = datas;

        for (int i = 0; i < datas.size(); i++) {
            TextView item = (TextView) mInflater.inflate( R.layout.display_item, null);
            TextView textView = (TextView) item.findViewById(R.id.textView);
            textView.setText(datas.get(i));
            item.setOnTouchListener(this);
            item.setTag(datas.get(i));
            contentViewList.add(item);
            mGridLayout.addView(item);
            // reset TextView style
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) item.getLayoutParams();
            params.rightMargin = (int) mContext.getResources().getDimension(R.dimen.grid_item_right_margin);
            params.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.grid_item_bottom_margin);
            params.width = (int) mContext.getResources().getDimension(R.dimen.grid_item_width);
            params.height = (int) mContext.getResources().getDimension(R.dimen.grid_item_height);
            item.setLayoutParams(params);
        }

        resetGridSelectedData();
    }

    private void resetGridLayout(int columnCount){
        mGridLayout.removeAllViews();
        mGridLayout.setColumnCount(columnCount);
    }
    
    private void resetGridSelectedData(){

        if (contentViewList == null) return;

        for (View view : contentViewList) {

            TextView item = (TextView) view;

            if (item.getTag().toString().equals(selectItem)){

                item.setPressed(true);
            }else {
                item.setPressed(false);
            }
        }
    }

    private String selectItem = "";

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN) return true;
        if(event.getAction()!=MotionEvent.ACTION_UP) return false;

        Object tag = v.getTag();

        selectItem = tag.toString();

        resetGridSelectedData();

        Toast.makeText(mContext, tag.toString(), Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        if (visibility == View.VISIBLE){
            resetGridSelectedData();
        }
    }
}
