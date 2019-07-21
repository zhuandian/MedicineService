package com.zhuandian.medicineserver;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.medicineserver.db.DBHelper;

public class AddMedicineActivity extends AppCompatActivity {

    private EditText name;
    private EditText count;
    private Button btnAdd;
    private DBHelper helper;
    private SQLiteDatabase dbWrite;
    private String dateStr;
    private LinearLayout llTime;
    private TextView tvTime;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        dateStr = getIntent().getStringExtra("date");
        //创建数据
        helper = new DBHelper(this);
        //调用数据操作对象
        dbWrite = helper.getWritableDatabase();

        name = (EditText) findViewById(R.id.et_name);
        count = (EditText) findViewById(R.id.et_count);
        btnAdd = (Button) findViewById(R.id.btn_add);
        llTime = (LinearLayout) findViewById(R.id.ll_time);
        tvTime = (TextView) findViewById(R.id.tv_time);
        llTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddMedicineActivity.this)
                        .setTitle("Select Time")
                        .setSingleChoiceItems(new String[]{"9:00 AM - 11:00 AM", "11:00 AM - 1:00 PM", "1:00 PM OR Later"}, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        tvTime.setText("9:00 AM - 11:00 AM");
                                        time = "9:00";
                                        break;
                                    case 1:
                                        tvTime.setText("11:00 AM - 1:00 PM");
                                        time = "11:00";
                                        break;
                                    case 2:
                                        tvTime.setText("1:00 PM OR Later");
                                        time = "13:00";
                                        break;
                                }
                                dialog.cancel();
                            }
                        }).create()
                        .show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(count.getText().toString())) {
                    Toast.makeText(AddMedicineActivity.this, "Please complete the information...", Toast.LENGTH_SHORT).show();
                } else {
                    dbWrite.execSQL("insert into medicine values(null,?,?,?,?)"
                            , new Object[]{dateStr, time, name.getText().toString(), count.getText().toString() + "Pills"});
                    new AlertDialog.Builder(AddMedicineActivity.this)
                            .setTitle("add successfully !")
                            .setMessage("add the medicine successfull")
                            .setPositiveButton("add new medicine", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    name.setText("");
                                    count.setText("");
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("go home page", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AddMedicineActivity.this.finish();
                                }
                            }).create().show();
                }

            }
        });
    }
}
