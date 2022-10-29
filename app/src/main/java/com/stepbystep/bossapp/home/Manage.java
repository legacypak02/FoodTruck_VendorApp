package com.stepbystep.bossapp.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.stepbystep.bossapp.DO.Truck;
import com.stepbystep.bossapp.MainActivity;
import com.stepbystep.bossapp.account.NoticeActivity;
import com.stepbystep.bossapp.databinding.FragmentManageBinding;

import java.util.ArrayList;

public class Manage extends Fragment {
    public static final String EMPTY_NOTICE = "EMPTYNOTICE";
    FragmentManageBinding binding;
    private Uri imageUri;
    String myUrl = "";
    private StorageTask uploadTask;
    private static final int GALLERYPICK = 1;
    ArrayList<Truck> items = new ArrayList<>();
    DatabaseReference truckData;
    StorageReference storageReference;
    FirebaseAuth auth;
    FirebaseUser user;
    Truck truck;

    public Manage() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentManageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initButtonClickListener();

        return view;
    }

    private void initButtonClickListener() {
        binding.btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NoticeActivity.class));
                //getActivity().finish();
            }
        });


        binding.btnMenuManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TruckManageActivity.class);
                startActivity(intent);
                // 저장 후 화면을 갱신하기 위해 startActivityForResult 로 호출
                // 이 호출함수는 나중에 돌아오면 MainActivity 의 onActivityResult 함수 에서 받는다.
                //startActivityForResult(intent,MainActivity.MANAGEFRAGMENT);
            }
        });//

        binding.btnReviewManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TruckManageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}