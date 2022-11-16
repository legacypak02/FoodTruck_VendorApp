package com.stepbystep.bossapp.home;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stepbystep.bossapp.AnimActivity;
import com.stepbystep.bossapp.R;
import com.stepbystep.bossapp.databinding.ActivityTruckNoticeBinding;
import com.stepbystep.bossapp.MainActivity;

import lombok.SneakyThrows;

public class TruckNoticeActivity extends AnimActivity {
    private ActivityTruckNoticeBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();


    String savedNoticeInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = ActivityTruckNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar abar = getSupportActionBar();
        abar.hide();

        initButtonListener();
        initAddTextChangeListener();
    }
    private void initAddTextChangeListener(){
        binding.etNotice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() == 255){
                    Toast.makeText(TruckNoticeActivity.this,"255자 까지만 입력할 수 있습니다.",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                binding.tvInputCounter.setText(String.format("(%d/255자)",s.length()));   //글자수 TextView에 보여주기.
                if(s.length() >= 255){
                    binding.tvInputCounter.setTextColor(getColor(R.color.error));
                }else{
                    binding.tvInputCounter.setTextColor(getColor(R.color.grey));
                }
            }
        });
    }

    private void initButtonListener(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinishWithAnim();
            }
        });

        binding.btnSaveNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNotice();
            }
        });
    }

    private void saveNotice(){
        try {
            binding.progressBarNotice.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(TruckNoticeActivity.this, "공지사항을 저장하는 과정에서 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
            Log.e("e = ", e.getMessage());
            binding.progressBarNotice.setVisibility(View.GONE);
        }
    }


}
