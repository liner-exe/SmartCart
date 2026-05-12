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
import com.liner_exe.smartcart.viewmodel.ProductViewModel;


public class ListItemAddFragment extends Fragment {
    private FragmentListItemAddBinding binding;
    private ListItemAddAdapter adapter;
    private ProductViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_item_add,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();
    }

    private void setupToolbar() {
        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void setupRecyclerView() {
        adapter = new ListItemAddAdapter();

        binding.rvListItemsToAdd.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvListItemsToAdd.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.products.observe(getViewLifecycleOwner(), products -> {
            adapter.setItems(products);
        });
    }
}