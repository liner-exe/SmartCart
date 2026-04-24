package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.FragmentProductsManagementBinding;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

public class ProductsManagementFragment extends Fragment {
    private FragmentProductsManagementBinding binding;

    private ShoppingViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        viewModel = new ViewModelProvider(this).get(viewModel.getClass());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products_management,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}