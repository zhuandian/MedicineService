package com.zhuandian.medicineserver.datepicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuandian.medicineserver.R;
import com.zhuandian.medicineserver.entity.ItemEntity;

import org.w3c.dom.Text;


public class ContentItemViewAbs extends LinearLayout {

    private TextView tvTitle;
    private TextView tvContent;

    public ContentItemViewAbs(Context context) {
        this(context, null);
    }

    public ContentItemViewAbs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContentItemViewAbs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.content_list_item_abs, null);
        tvTitle = view.findViewById(R.id.tv_title);
        tvContent = view.findViewById(R.id.tv_content);
        addView(view);
    }

    public void initView(ItemEntity.SubItem subItem){
        tvTitle.setText(subItem.getTitle());
        tvContent.setText(subItem.getContent());
    }
}
