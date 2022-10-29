package com.stepbystep.bossapp.order;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.stepbystep.bossapp.R;
import com.stepbystep.bossapp.ViewPagerAdapter;
import com.stepbystep.bossapp.databinding.FragmentOrderBinding;

public class Order extends Fragment {
    private View view;
    private FragmentOrderBinding binding;

    Bundle extra;

    Boolean storeInitInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        extra = this.getArguments();
        if(extra != null) {
            extra = getArguments();

            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(), 2,2);
            binding.orderViewPager.setAdapter(adapter);

            new TabLayoutMediator(binding.tabLayoutOrder, binding.orderViewPager,
                    new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            switch (position){
                                case 0:
                                    tab.setText("주문현황");
                                    break;
                                default:
                                    tab.setText("완료현황");
                                    break;
                            }
                        }
                    }).attach();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
