package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.smartcart.adapters.ListItemAdapter;
import com.liner_exe.smartcart.databinding.BottomSheetEditListItemBinding;
import com.liner_exe.smartcart.databinding.FragmentListBinding;
import com.liner_exe.smartcart.modal.ListItemEditSheet;
import com.liner_exe.smartcart.viewmodel.ListItemsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentList extends Fragment {
    private FragmentListBinding binding;
    private ListItemAdapter adapter;
    private ListItemsViewModel viewModel;
    private String listName;
    private int listId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ListItemsViewModel.class);

        handleArguments();

        setupToolbar();
        setupRecyclerView();
        setupFab();

        observeViewModel();
    }

    private void handleArguments() {
        if (getArguments() != null) {
            FragmentListArgs args = FragmentListArgs.fromBundle(getArguments());

            listName = args.getListName();
            listId = args.getListId();

            viewModel.setCurrentListId(listId);
        }
    }

    private void setupToolbar() {
        binding.listAppBar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        binding.listAppBar.setTitle(listName);
    }

    private void setupRecyclerView() {
        adapter = new ListItemAdapter(new ListItemAdapter.OnListItemActionListener() {
            @Override
            public void onCheckbox(ListItem listItem) {
                viewModel.toggleItemStatus(listItem);
            }

            @Override
            public void onEdit(ListItem listItem) {
                ListItemEditSheet.newInstance(listItem)
                        .show(getChildFragmentManager(),
                        "ListItemEdit");
            }
        });

        binding.rvListItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvListItems.setAdapter(adapter);

        adapter.setOnItemClickListener((listItem, position) -> {
            ListItemEditSheet.newInstance(listItem)
                    .show(getChildFragmentManager(),
                            "ListItemEdit");
        });
    }

    private void setupFab() {
        binding.fabAddListItem.setOnClickListener(v -> {
            NavDirections action = FragmentListDirections
                    .actionFragmentListToProductAddFragment(listId);
            NavHostFragment.findNavController(this).navigate(action);
        });
    }

    private void observeViewModel() {
        viewModel.listItems.observe(getViewLifecycleOwner(), newListItems -> {
            adapter.setItems(newListItems);
        });
    }
}