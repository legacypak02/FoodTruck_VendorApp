package com.stepbystep.bossapp.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.stepbystep.bossapp.databinding.FragmentCompleteListBinding;

public class CompleteListFragment extends Fragment {
    private View view;
    private FragmentCompleteListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCompleteListBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        return view;
    }
}
