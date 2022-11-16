package com.stepbystep.bossapp.account;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.stepbystep.bossapp.DO.Festival;
import com.stepbystep.bossapp.MainActivity;
import com.stepbystep.bossapp.R;

import java.util.Calendar;

public class FestivalContentActivity extends AppCompatActivity {
    private Button btn_push;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_content);

        Intent intenti = new Intent(FestivalContentActivity.this, AlarmActivity.class);
        btn_push = findViewById(R.id.noti_set);
        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intenti);
            }
        });

        //액션바 지우기
        ActionBar abar = getSupportActionBar();
        abar.hide();

        Intent intent = getIntent();
        Festival festival = (Festival) intent.getSerializableExtra("Festival");
        ((TextView) findViewById(R.id.activity_festival_content_title2)).setText(festival.getTitle().replace("\\n", System.getProperty("line.separator")));
        ((TextView) findViewById(R.id.activity_festival_content_content)).setText(festival.getContent().replace("\\n", System.getProperty("line.separator")));
    }

}
