package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.StoreSelectionAdapter;
import com.liner_exe.smartcart.databinding.FragmentStoreSelectionBinding;
import com.liner_exe.smartcart.viewmodel.ListItemsViewModel;
import com.liner_exe.smartcart.viewmodel.StoresViewModel;

public class StoreSelectionFragment extends Fragment {
    private FragmentStoreSelectionBinding binding;
    private StoreSelectionAdapter adapter;
    private StoresViewModel storesViewModel;
    private ListItemsViewModel listItemsViewModel;
    private ListItem listItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStoreSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storesViewModel = new ViewModelProvider(requireActivity()).get(StoresViewModel.class);
        listItemsViewModel = new ViewModelProvider(requireActivity()).get(ListItemsViewModel.class);

        handleArguments();

        setupToolbar();
        setupRecyclerView();
        observeViewModel();
    }

    private void handleArguments() {
        if (getArguments() != null) {
            StoreSelectionFragmentArgs args = StoreSelectionFragmentArgs.fromBundle(getArguments());
            listItem = args.getListItem();
        }
    }

    private void setupToolbar() {
        binding.toolbar.inflateMenu(R.menu.store_selection_menu);

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_reset_store) {
                resetStore();
                return true;
            }
            return false;
        });

        binding.toolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void setupRecyclerView() {
        adapter = new StoreSelectionAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((store, position) -> {
            storesViewModel.setSelectedStore(store);
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void resetStore() {
        storesViewModel.setSelectedStore(null);
        NavHostFragment.findNavController(this).popBackStack();
    }

    private void observeViewModel() {
        storesViewModel.filteredStores.observe(getViewLifecycleOwner(), store -> {
            adapter.setItems(store);
        });
    }
}