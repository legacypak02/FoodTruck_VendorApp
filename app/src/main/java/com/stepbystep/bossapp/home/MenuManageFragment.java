package com.stepbystep.bossapp.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepbystep.bossapp.DO.Food;
import com.stepbystep.bossapp.R;

import java.util.ArrayList;


public class MenuManageFragment extends Fragment {
    private static FrameLayout menuEditLayout, menuAddLayout;
    Button menuAddButton, refreshButton;

    String truckId;
    static RecyclerView menuRecyclerView;
    static MenuAdapter menuAdapter;
    static Context context;

    DatabaseReference food_database;
    DatabaseReference truck_database;
    FirebaseAuth mAuth;
    FirebaseUser user;

    ArrayList<Food> foods;

    static int lastClickedMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_manage,container,false);
        lastClickedMenu=-1;

        context = view.getContext();

        truckId = getArguments().getString("truck");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        food_database =  FirebaseDatabase.getInstance().getReference("FoodTruck").child("Food");
        truck_database =  FirebaseDatabase.getInstance().getReference("FoodTruck").child("Truck");

        menuEditLayout = view.findViewById(R.id.M_menuEditLayout);
        menuAddLayout = view.findViewById(R.id.M_menuAddLayout);
        menuAddButton = view.findViewById(R.id.menuAddButton);
        menuAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAddShow(true);
            }
        });

        menuRecyclerView = (RecyclerView)view.findViewById(R.id.menuRecyclerView);
        menuAdapter = new MenuAdapter(getContext());
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshButton = view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadView();
            }
        });

        return view;
    }

    public void getMenuDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("FoodTruck").child("Food").child(truckId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void menuEditShow(boolean show){
        int duration = 200;

        Animation pageUpAnim = AnimationUtils.loadAnimation(context, R.anim.page_slide_up);
        pageUpAnim.setDuration(duration);
        Animation pageDownAnim = AnimationUtils.loadAnimation(context, R.anim.page_slide_down);
        pageDownAnim.setDuration(duration);

        if(show) {
            menuEditLayout.setVisibility(View.VISIBLE);
            menuEditLayout.setAnimation(pageUpAnim);
        }else{
            menuEditLayout.setVisibility(View.GONE);
            menuEditLayout.setAnimation(pageDownAnim);
        }

    }

    public static void menuAddShow(boolean show){
        int duration = 200;

        Animation pageUpAnim = AnimationUtils.loadAnimation(context, R.anim.page_slide_up);
        pageUpAnim.setDuration(duration);
        Animation pageDownAnim = AnimationUtils.loadAnimation(context, R.anim.page_slide_down);
        pageDownAnim.setDuration(duration);

        if(show) {
            menuAddLayout.setVisibility(View.VISIBLE);
            menuAddLayout.setAnimation(pageUpAnim);
        }else{
            menuAddLayout.setVisibility(View.GONE);
            menuAddLayout.setAnimation(pageDownAnim);
        }

    }


    public void reloadView(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(MenuManageFragment.this).attach(MenuManageFragment.this).commitAllowingStateLoss();

    }


}
