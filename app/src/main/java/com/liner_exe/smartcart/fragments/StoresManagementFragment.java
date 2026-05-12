package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.Store;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.StoreAdapter;
import com.liner_exe.smartcart.databinding.FragmentStoresManagementBinding;
import com.liner_exe.smartcart.dialogs.ProductDialogFragment;
import com.liner_exe.smartcart.viewmodel.StoresViewModel;

public class StoresManagementFragment extends Fragment {
    private FragmentStoresManagementBinding binding;
    private StoreAdapter adapter;
    private StoresViewModel storesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStoresManagementBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storesViewModel = new ViewModelProvider(requireActivity()).get(StoresViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();

        bindDialog();
    }

    private void setupToolbar() {
        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void setupRecyclerView() {
        adapter = new StoreAdapter();

        binding.rvShops.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvShops.setAdapter(adapter);
    }

    private void observeViewModel() {
        storesViewModel.stores.observe(getViewLifecycleOwner(), stores -> {
            if (stores != null) {
                adapter.setItems(stores);
            }
        });
    }

    private void bindDialog() {
        binding.fabAddStore.setOnClickListener(v -> {
            ProductDialogFragment.newInstance(null, name -> {
                storesViewModel.addStore(new Store(name));
            }).show(getChildFragmentManager(), "AddProductDialog");
        });
    }
}