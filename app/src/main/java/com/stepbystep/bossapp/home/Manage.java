package com.stepbystep.bossapp.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.stepbystep.bossapp.MainActivity;
import com.stepbystep.bossapp.R;
import com.stepbystep.bossapp.databinding.FragmentManageBinding;

import java.util.ArrayList;

public class Manage extends Fragment {
    private DatabaseReference databaseReference;
    private DatabaseReference locationRef;
    private FirebaseUser user;

    private LocationListener mLocListener = null;
    FusedLocationProviderClient client;
    private final int permissionRequestCode = 12345;
    private android.location.Location loc;
    private String lat;
    private String lon;

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

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("BossApp").child("StoreAccount").child(user.getUid());
        locationRef = FirebaseDatabase.getInstance().getReference("FoodTruck").child("Truck");

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
                                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                            ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION},100);
                                    } else {
                                        getCurrentLocation();
                                        Log.d("CheckCurrentLocation", "현재 위치 값: ${lat}, ${lon}");
                                    }
                                }
                            });
                        }
                        if(vendor_status.equals("1"))
                        {
                            binding.toggleButton.setChecked(false);
                            binding.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    databaseReference.child("vendor_status").setValue("0");
                                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                            ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION},100);
                                    } else {
                                        getCurrentLocation();
                                        Log.d("CheckCurrentLocation", "현재 위치 값: " +lat+","+ lon);
                                    }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && (grantResults.length > 0) &&
                (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }else {
            Toast.makeText(getActivity(), "위치정보 사용여부 거부됨", Toast.LENGTH_SHORT).show();
        }
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

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        lat = Double.toString(location.getLatitude());
                        lon = Double.toString(location.getLongitude());
                    }else {
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setFastestInterval(1000)
                                .setInterval(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setFastestInterval(500);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();
                                lat = Double.toString(location1.getLatitude());
                                lon = Double.toString(location1.getLongitude());
                            }
                        };
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        }else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}