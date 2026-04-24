package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.ProductsManagementAdapter;
import com.liner_exe.smartcart.databinding.FragmentProductsManagementBinding;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductsManagementFragment extends Fragment {
    private FragmentProductsManagementBinding binding;
    private ProductsManagementAdapter adapter;
    private ShoppingViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);

        RecyclerView recyclerView = binding.recyclerViewProductsManagement;
        adapter = new ProductsManagementAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.products.observe(getViewLifecycleOwner(), newProducts -> {
            if (newProducts != null) {
                adapter.setProducts(newProducts);
            }
        });

        viewModel.loadProducts();
    }
}