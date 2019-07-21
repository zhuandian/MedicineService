package com.zhuandian.medicineserver;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhuandian.medicineserver.db.DBHelper;

public class AddMedicineActivity extends AppCompatActivity {

    private EditText name;
    private EditText count;
    private Button btnAdd;
    private DBHelper helper;
    private SQLiteDatabase dbWrite;
    private String dateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        dateStr = getIntent().getStringExtra("date");
        //创建数据
        helper = new DBHelper(this);
        //调用数据操作对象
        dbWrite = helper.getWritableDatabase();
        dbWrite.execSQL("insert into medicine values(null,?,?,?,?)"
                , new Object[]{dateStr, "9:00", "标题", "内容"});
        name = (EditText) findViewById(R.id.et_name);
        count = (EditText) findViewById(R.id.et_count);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbWrite.execSQL("insert into medicine values(null,?,?,?,?)"
                        , new Object[]{dateStr, "9:00", "标题", "内容"});
            }
        });
    }
}
