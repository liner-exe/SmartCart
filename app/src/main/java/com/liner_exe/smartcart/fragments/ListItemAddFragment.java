package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.ListItemAddAdapter;
import com.liner_exe.smartcart.databinding.FragmentListItemAddBinding;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;


public class ListItemAddFragment extends Fragment {
    FragmentListItemAddBinding binding;
    ListItemAddAdapter adapter;
    ShoppingViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_item_add,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);

        RecyclerView recyclerView = binding.rvListItemsToAdd;
        adapter = new ListItemAddAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.products.observe(getViewLifecycleOwner(), products -> {
            adapter.setItems(products);
        });

        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
}