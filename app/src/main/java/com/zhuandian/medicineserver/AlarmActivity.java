package com.zhuandian.medicineserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhuandian.medicineserver.clock.AlarmManagerUtil;

public class AlarmActivity extends AppCompatActivity {

    private EditText etHour;
    private EditText etMinutes;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        etHour = (EditText) findViewById(R.id.et_hour);
        etMinutes = (EditText) findViewById(R.id.et_minutes);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etHour.getText().toString())||TextUtils.isEmpty(etMinutes.getText().toString())){
                    Toast.makeText(AlarmActivity.this, "please select time", Toast.LENGTH_SHORT).show();
                }else {
                    AlarmManagerUtil.setAlarm(AlarmActivity.this, 0, Integer.parseInt(etHour.getText().toString()),
                            Integer.parseInt(etMinutes.getText().toString()), (int)(1+Math.random()*(50000-1+1)), 0, "Medication time", 2);
                    AlarmActivity.this.finish();

                }
            }
        });
    }
}
