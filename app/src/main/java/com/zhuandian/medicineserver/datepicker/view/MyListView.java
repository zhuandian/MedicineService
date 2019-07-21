package com.zhuandian.medicineserver.datepicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.zhuandian.medicineserver.R;

/**
 * desc :
 */
public class MyListView extends LinearLayout {
    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.view_my_list_view, null);
        LinearLayout itemContainer = view.findViewById(R.id.ll_item_container);
        ContentItemViewAbs contentItemViewAbs = new ContentItemViewAbs(context);
        itemContainer.addView(contentItemViewAbs);
        addView(view);
    }
}
