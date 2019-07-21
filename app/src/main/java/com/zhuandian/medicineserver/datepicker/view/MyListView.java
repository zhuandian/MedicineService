package com.zhuandian.medicineserver.datepicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.zhuandian.medicineserver.R;
import com.zhuandian.medicineserver.entity.DbEntity;
import com.zhuandian.medicineserver.entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * desc :
 */
public class MyListView extends LinearLayout {
    private final LinearLayout itemContainer3;
    private final LinearLayout itemContainer2;
    private final LinearLayout itemContainer1;
    private View view;
    private Context context;


    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        view = inflate(context, R.layout.view_my_list_view, null);
        itemContainer1 = view.findViewById(R.id.ll_item_container_1);
        itemContainer2 = view.findViewById(R.id.ll_item_container_2);
        itemContainer3 = view.findViewById(R.id.ll_item_container_3);
        addView(view);
    }

    public void initView(List<DbEntity> dbEntityList) {

        List<ItemEntity.SubItem> subItemList1 = new ArrayList<>();
        List<ItemEntity.SubItem> subItemList2 = new ArrayList<>();
        List<ItemEntity.SubItem> subItemList3 = new ArrayList<>();


        for (DbEntity dbEntity : dbEntityList) {
            if (dbEntity.getTime().equals("9:00AM-11:00AM")) {
                ItemEntity.SubItem subItem = new ItemEntity.SubItem();
                subItem.setTitle(dbEntity.getTitle());
                subItem.setContent(dbEntity.getCount());
                subItemList1.add(subItem);
            } else if (dbEntity.getTime().equals("11:00AM-1:00PM")) {
                ItemEntity.SubItem subItem = new ItemEntity.SubItem();
                subItem.setTitle(dbEntity.getTitle());
                subItem.setContent(dbEntity.getCount());
                subItemList2.add(subItem);
            } else if (dbEntity.getTime().equals("1:00PM OR Later")) {
                ItemEntity.SubItem subItem = new ItemEntity.SubItem();
                subItem.setTitle(dbEntity.getTitle());
                subItem.setContent(dbEntity.getCount());
                subItemList3.add(subItem);
            }
        }

        for (ItemEntity.SubItem subItem : subItemList1) {
            ContentItemViewAbs child = new ContentItemViewAbs(context);
            child.initView(subItem);
            itemContainer1.addView(child);
        }

        for (ItemEntity.SubItem subItem : subItemList2) {
            ContentItemViewAbs child = new ContentItemViewAbs(context);
            child.initView(subItem);
            itemContainer2.addView(child);
        }

        for (ItemEntity.SubItem subItem : subItemList3) {
            ContentItemViewAbs child = new ContentItemViewAbs(context);
            child.initView(subItem);
            itemContainer2.addView(child);
        }


    }
}
