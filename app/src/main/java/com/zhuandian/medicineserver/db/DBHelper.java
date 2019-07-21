package com.zhuandian.medicineserver.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String name="medicine";//数据库名
    private static final int version=1;//版本号

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table medicine(" +
                "_id integer not null primary key autoincrement," +
                "date varchar(20)," +
                "time varchar(20)," +
                "title varchar(20)," +
                "content varchar(20))");
        //添加一条测试数据
        db.execSQL("insert into medicine values(null,?,?,?,?)"
                ,new Object[]{"2019.7.21","8:00","标题","内容"});

        db.execSQL("insert into medicine values(null,?,?,?,?)"
                ,new Object[]{"2019.7.22","9:00","标题","内容"});

        db.execSQL("insert into medicine values(null,?,?,?,?)"
                ,new Object[]{"2019.7.24","10:00","标题","内容"});
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}