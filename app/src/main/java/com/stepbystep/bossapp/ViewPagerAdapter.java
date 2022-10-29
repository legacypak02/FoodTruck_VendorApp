package com.stepbystep.bossapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.stepbystep.bossapp.home.MenuManageFragment;
import com.stepbystep.bossapp.home.TruckReviewFragment;
import com.stepbystep.bossapp.order.CompleteListFragment;
import com.stepbystep.bossapp.order.OrderListFragment;

public class  ViewPagerAdapter extends FragmentStateAdapter {
    int num, count;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,int num, int count) {
        super(fragmentActivity);
        this.num = num;
        this.count = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (num == 0) {
            switch (position) {
                case 0:
                    return new MenuManageFragment();

                default:
                    return new TruckReviewFragment();
            }
        }
        else {
            switch (position) {
                case 0:
                    return new OrderListFragment();

                default:
                    return new CompleteListFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }


}