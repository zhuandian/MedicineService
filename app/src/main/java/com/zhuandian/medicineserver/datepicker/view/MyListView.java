package com.zhuandian.medicineserver.datepicker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuandian.medicineserver.R;
import com.zhuandian.medicineserver.clock.AlarmManagerUtil;
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
    private final TextView tvIndex1;
    private final TextView tvIndex2;
    private final TextView tvIndex3;
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
        tvIndex1 = view.findViewById(R.id.tv_index_1);
        tvIndex2 = view.findViewById(R.id.tv_index_2);
        tvIndex3 = view.findViewById(R.id.tv_index_3);
        addView(view);
    }

    @SuppressLint("ResourceAsColor")
    public void initView(List<DbEntity> dbEntityList) {

        List<ItemEntity.SubItem> subItemList1 = new ArrayList<>();
        List<ItemEntity.SubItem> subItemList2 = new ArrayList<>();
        List<ItemEntity.SubItem> subItemList3 = new ArrayList<>();


        for (DbEntity dbEntity : dbEntityList) {

            AlarmManagerUtil.setAlarm(context, 0, Integer.parseInt(dbEntity.getTime()),
                    00, (int)(1+Math.random()*(10000-1+1)), 0, "Medication time", 2);

            if (Integer.parseInt(dbEntity.getTime()) < 11) {
                ItemEntity.SubItem subItem = new ItemEntity.SubItem();
                subItem.setTitle(dbEntity.getTitle());
                subItem.setContent(dbEntity.getCount());
                subItemList1.add(subItem);
            } else if (Integer.parseInt(dbEntity.getTime()) > 11 && Integer.parseInt(dbEntity.getTime()) < 13) {
                ItemEntity.SubItem subItem = new ItemEntity.SubItem();
                subItem.setTitle(dbEntity.getTitle());
                subItem.setContent(dbEntity.getCount());
                subItemList2.add(subItem);
            } else if (Integer.parseInt(dbEntity.getTime()) > 13) {
                ItemEntity.SubItem subItem = new ItemEntity.SubItem();
                subItem.setTitle(dbEntity.getTitle());
                subItem.setContent(dbEntity.getCount());
                subItemList3.add(subItem);
            }
        }


        tvIndex1.setBackgroundColor(getResources().getColor(subItemList1.size() > 0 ? R.color.colorAccent : R.color.colorPrimary));
        tvIndex2.setBackgroundColor(getResources().getColor(subItemList2.size() > 0 ? R.color.colorAccent : R.color.colorPrimary));
        tvIndex3.setBackgroundColor(getResources().getColor(subItemList3.size() > 0 ? R.color.colorAccent : R.color.colorPrimary));


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
            itemContainer3.addView(child);
        }


    }
}
