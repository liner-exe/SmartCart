package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.smartcart.adapters.ListItemAddAdapter;
import com.liner_exe.smartcart.databinding.FragmentListItemAddBinding;
import com.liner_exe.smartcart.viewmodel.ProductViewModel;
import com.liner_exe.smartcart.viewmodel.ListItemsViewModel;


public class ListItemAddFragment extends Fragment {
    private FragmentListItemAddBinding binding;
    private ListItemAddAdapter adapter;
    private ProductViewModel productViewModel;
    private ListItemsViewModel listItemsViewModel;
    private int currentListId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListItemAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        listItemsViewModel = new ViewModelProvider(requireActivity()).get(ListItemsViewModel.class);

        handleArguments();

        setupToolbar();
        setupRecyclerView();
        observeViewModel();
    }

    private void handleArguments() {
        if (getArguments() != null) {
            ListItemAddFragmentArgs args = ListItemAddFragmentArgs.fromBundle(getArguments());
            currentListId = args.getListId();
        }
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

        adapter.setOnItemClickListener((product, position) -> {
            listItemsViewModel.addProductAsListItem(product);
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void observeViewModel() {
        productViewModel.products.observe(getViewLifecycleOwner(), products -> {
            adapter.setItems(products);
        });
    }
}