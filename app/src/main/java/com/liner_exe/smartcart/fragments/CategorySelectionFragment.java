package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.FragmentCategorySelectionBinding;

public class CategorySelectionFragment extends Fragment {
    private FragmentCategorySelectionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_category_selection, container, false
        );
        return binding.getRoot();
    }
}