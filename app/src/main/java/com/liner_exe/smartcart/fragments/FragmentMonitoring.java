package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.FragmentMonitoringBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentMonitoring extends Fragment {
    private FragmentMonitoringBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMonitoringBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}