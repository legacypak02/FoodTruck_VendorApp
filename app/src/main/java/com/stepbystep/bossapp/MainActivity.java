package com.stepbystep.bossapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.stepbystep.bossapp.account.Account;
import com.stepbystep.bossapp.chart.Chart;
import com.stepbystep.bossapp.home.Manage;
import com.stepbystep.bossapp.login.LoginActivity;
import com.stepbystep.bossapp.order.Order;


public class MainActivity extends AppCompatActivity {

    Account account;
    Manage manage;
    Order order;
    Chart chart;
    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;

    //뒤로가기 로직을 위한 변수
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //액션바 지우기
        ActionBar abar = getSupportActionBar();
        abar.hide();
        //로그인 인증을 위한 객체 생성
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //intent 받기
        Intent intent = getIntent();
        int flag = intent.getIntExtra("startFragment",0);
        //fragment 객체 생성
        manage = new Manage();
        chart = new Chart();
        order = new Order();
        account = new Account();
        //main fragment 선택(홈, map fragment)
        fragmentManager = getSupportFragmentManager();
        //하단 탭 리스너 설정 메서드 호출
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentSelect(auth);
        if(flag == 0){
            bottomNavigationView.setSelectedItemId(R.id.manage_tab);
        }
        else if(flag == 1){
            bottomNavigationView.setSelectedItemId(R.id.chart_tab);
        }
        else if(flag == 2){
            bottomNavigationView.setSelectedItemId(R.id.order_tab);
        }
        else if(flag == 3){
            bottomNavigationView.setSelectedItemId(R.id.account_tab);
        }
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르면 앱이 종료 됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fragmentSelect(FirebaseAuth auth){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.manage_tab){
                    if(auth.getCurrentUser() == null){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    fragmentManager.beginTransaction().replace(R.id.container, manage).commit();
                    return true;
                }
                else if(id == R.id.chart_tab){
                    if(auth.getCurrentUser() == null){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    fragmentManager.beginTransaction().replace(R.id.container,chart).commit();
                    return true;
                }
                else if(id == R.id.order_tab){
                    if(auth.getCurrentUser() == null){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    fragmentManager.beginTransaction().replace(R.id.container,order).commit();
                    return true;
                }

                else if(id == R.id.account_tab){
                    if(auth.getCurrentUser() == null){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    else{
                        fragmentManager.beginTransaction().replace(R.id.container, account).commit();
                        return true;
                    }
                }
                return false;
            }
        });
    }

}