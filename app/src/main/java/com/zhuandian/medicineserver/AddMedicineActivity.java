package com.zhuandian.medicineserver;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhuandian.medicineserver.db.DBHelper;
import com.zhuandian.medicineserver.entity.DbEntity;
import com.zhuandian.medicineserver.entity.DbEntityList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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

        final String[] timeArray = new String[24];
        for (int i = 0; i < 24; i++) {
            timeArray[i] = i + "";
        }
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
                        .setSingleChoiceItems(timeArray, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tvTime.setText("Time is " + timeArray[which]);
                                time = timeArray[which];
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

        findViewById(R.id.btn_import_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }


    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please open the relevant permissions, otherwise the application cannot be normally used！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {
            importFile();
        }
    }

    private void importFile() {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "config.txt";
        String content = "";
        File file = new File(path);
        if (file.exists() && file.getName().endsWith(".txt")) {
            try {
                InputStream in = new FileInputStream(file);
                if (in != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(in, "utf-8");
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        content += line;
                    }
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            formatJson(content);
        } else {
            new AlertDialog.Builder(AddMedicineActivity.this)
                    .setTitle("Fail !！")
                    .setMessage("Make sure there is a file config.tex in the root directory")
                    .create()
                    .show();
        }


    }

    private void formatJson(String content) {
        DbEntity[] array = new Gson().fromJson(content.toString(), DbEntity[].class);
        for (int i = 0; i < array.length; i++) {
            dbWrite.execSQL("insert into medicine values(null,?,?,?,?)"
                    , new Object[]{array[i].getDate(),
                            array[i].getTime(),
                            array[i].getTitle(),
                            array[i].getCount() + "Pills"});
        }

        new AlertDialog.Builder(AddMedicineActivity.this)
                .setTitle("Import file success ！")
                .setMessage("Import file success ")
                .create()
                .show();
    }
}
