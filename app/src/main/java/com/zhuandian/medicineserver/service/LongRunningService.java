package com.zhuandian.medicineserver.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.zhuandian.medicineserver.AlarmActivity;
import com.zhuandian.medicineserver.db.DBHelper;
import com.zhuandian.medicineserver.entity.DbEntity;
import com.zhuandian.medicineserver.entity.ItemEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LongRunningService extends Service {
    private DBHelper helper;
    private SQLiteDatabase dbWrite;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("LongRunningService", "executed at " + new Date().toString());
            }
        }).start();

        helper = new DBHelper(this);
        dbWrite = helper.getWritableDatabase();
        List<DbEntity> dbEntityList = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.YEAR) + "." + (now.get(Calendar.MONTH) + 1) + "." + (now.get(Calendar.DATE));
        Cursor cursor = dbWrite.query(DBHelper.name, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String count = cursor.getString(cursor.getColumnIndex("count"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            if (date.equals(today)){
                DbEntity dbEntity = new DbEntity();
                dbEntity.setCount(count);
                dbEntity.setDate(date);
                dbEntity.setTime(time);
                dbEntity.setTitle(title);
                dbEntityList.add(dbEntity);
            }
        }


        System.out.println(now.get(Calendar.HOUR_OF_DAY)+"---------------------");
        for (int i = 0; i < dbEntityList.size(); i++) {
            if (dbEntityList.get(i).getTime().equals("9:00AM-11:00AM")) {

            } else if (dbEntityList.get(i).getTime().equals("11:00AM-1:00PM")) {

            } else if (dbEntityList.get(i).getTime().equals("1:00PM OR Later")) {

            }
        }

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 10 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AlarmActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);//1min后返回执行
        return super.onStartCommand(intent, flags, startId);
    }
}
