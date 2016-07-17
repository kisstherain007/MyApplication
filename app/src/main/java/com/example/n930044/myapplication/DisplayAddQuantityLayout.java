package com.example.n930044.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by n930044 on 2016/7/16.
 */
public class DisplayAddQuantityLayout extends LinearLayout implements View.OnClickListener{

    TextView quantityTextView;

    public DisplayAddQuantityLayout(Context context) {
        super(context);
    }

    public DisplayAddQuantityLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.reduce_view).setOnClickListener(this);
        findViewById(R.id.add_view).setOnClickListener(this);
        quantityTextView = (TextView) findViewById(R.id.quantity_view);
        quantityTextView.setText(String.valueOf(quantity));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.reduce_view:
                quantity = setQuantity(false);
                break;
            case R.id.add_view:
                quantity = setQuantity(true);
                break;
        }
    }

    private int setQuantity(boolean isAdd){

        int quantity = Integer.parseInt(quantityTextView.getText().toString());

        if (!isAdd && quantity <= 1) return 1;

        quantity = isAdd ? ++quantity : --quantity;

        quantityTextView.setText(String.valueOf(quantity));

        return quantity;
    }

    private int quantity = 1;

    /**
     * 获取选择数量
     * @return
     */
    public int getQuantity(){

        return quantity;
    }
}
