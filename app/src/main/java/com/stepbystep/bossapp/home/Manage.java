package com.stepbystep.bossapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stepbystep.bossapp.DO.StoreAccount;
import com.stepbystep.bossapp.DO.Truck;
import com.stepbystep.bossapp.R;
import com.stepbystep.bossapp.databinding.FragmentManageBinding;

import java.util.ArrayList;

public class Manage extends Fragment {
    private DatabaseReference databaseReference;
    private DatabaseReference toggleReferences;
    private FirebaseUser user;
    String truckId;
    String vendor_status;
    StoreAccount storeAccount;

    FragmentManageBinding binding;
    ArrayList<Truck> items = new ArrayList<>();

    public Manage() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentManageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initButtonClickListener();

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("BossApp").child("StoreAccount").child(user.getUid());

        if(user != null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        storeAccount = snapshot.getValue(StoreAccount.class);
                        truckId = storeAccount.getTruckId();
                        vendor_status = storeAccount.getVendor_status();
                        if(vendor_status.equals("0")){
                            binding.toggleButton.setChecked(true);
                            binding.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    databaseReference.child("vendor_status").setValue("1");
                                }
                            });
                        } else {
                            binding.toggleButton.setChecked(false);
                            binding.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    databaseReference.child("vendor_status").setValue("0");
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        return view;
    }

    private void initButtonClickListener() {
        binding.btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TruckNoticeActivity.class));
                //getActivity().finish();
            }
        });


        binding.btnMenuManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MenuManageActivity.class);
                startActivity(intent);
            }
        });//

        binding.btnReviewManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewManageActivity.class);
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