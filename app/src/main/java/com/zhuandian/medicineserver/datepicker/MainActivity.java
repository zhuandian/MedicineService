package com.zhuandian.medicineserver.datepicker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.zhuandian.medicineserver.AddMedicineActivity;
import com.zhuandian.medicineserver.R;
import com.zhuandian.medicineserver.datepicker.calendar.cons.DPMode;
import com.zhuandian.medicineserver.datepicker.calendar.views.MonthView;
import com.zhuandian.medicineserver.datepicker.calendar.views.WeekView;
import com.zhuandian.medicineserver.datepicker.view.MyListView;
import com.zhuandian.medicineserver.db.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MonthView.OnDateChangeListener, MonthView.OnDatePickedListener {

    private MonthView monthView;
    private WeekView weekView;
    private LinearLayout contentLayout;
    private TextView weekTxt;
    private Calendar now;
    private LinearLayout llAddContainer;
    private Button btnAdd;
    private SQLiteDatabase dbWrite;
    private DBHelper helper;
    private String dateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_datapicker);
        now = Calendar.getInstance();

        monthView = (MonthView) findViewById(R.id.month_calendar);
        weekView = (WeekView) findViewById(R.id.week_calendar);
        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
        weekTxt = (TextView) findViewById(R.id.week_text);
        llAddContainer = (LinearLayout) findViewById(R.id.ll_add_container);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMedicineActivity.class);
                intent.putExtra("date", dateStr);
                startActivity(intent);
            }
        });

        monthView.setDPMode(DPMode.SINGLE);
        monthView.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1);
        monthView.setFestivalDisplay(true);
        monthView.setTodayDisplay(true);
        monthView.setOnDateChangeListener(this);
        monthView.setOnDatePickedListener(this);

        weekView.setDPMode(DPMode.SINGLE);
        weekView.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1);
        weekView.setFestivalDisplay(true);
        weekView.setTodayDisplay(true);
        weekView.setOnDatePickedListener(this);

        initData();


    }

    private void initData() {
        //创建数据
        helper = new DBHelper(this);
        //调用数据操作对象
        dbWrite = helper.getWritableDatabase();

        Cursor cursor = dbWrite.query(DBHelper.name, new String[]{"time"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("time"));
            System.out.println(title + "----------------------------");
        }

        cursor.close();
//        for (int i = 0; i < 20; i++) {
//            MyListView cia = new MyListView(this);
//            contentLayout.addView(cia);
//        }
    }

    @Override
    public void onDateChange(int year, int month) {
        ActionBar toolbar = getSupportActionBar();
        if (null != toolbar)
            toolbar.setTitle(year + "." + month);
    }

    @Override
    public void onDatePicked(String date) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
            Date choosedate = format1.parse(date);
            weekTxt.setText(format2.format(choosedate));
            if (date.equals(now.get(Calendar.YEAR) + "." + (now.get(Calendar.MONTH) + 1) + "." + now.get(Calendar.DAY_OF_MONTH))) {
                weekTxt.setVisibility(View.INVISIBLE);
            } else {
                weekTxt.setVisibility(View.VISIBLE);
            }
            contentLayout.removeAllViews();
//            for (int i = 0; i < 2; i++) {
//                MyListView cia = new MyListView(this);
//                contentLayout.addView(cia);
//            }
            dateStr = "" + date;
            Cursor cursor = dbWrite.query(DBHelper.name, new String[]{"date"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String dateStr = cursor.getString(cursor.getColumnIndex("date"));
                if (dateStr.contains("" + date)) {
                    llAddContainer.setVisibility(View.GONE);
                    contentLayout.addView(new MyListView(this));
                } else {
                    llAddContainer.setVisibility(View.VISIBLE);
                }
                System.out.println(dateStr + "----------------------------");
            }
            cursor.close();
            Toast.makeText(this, "" + date, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
