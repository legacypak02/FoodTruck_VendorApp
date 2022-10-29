package com.stepbystep.bossapp.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.stepbystep.bossapp.databinding.FragmentOrderListBinding;

public class OrderListFragment extends Fragment {
    private FragmentOrderListBinding binding;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderListBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        return view;
    }
}
