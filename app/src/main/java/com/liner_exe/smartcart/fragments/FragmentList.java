package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.ListItemAdapter;
import com.liner_exe.smartcart.databinding.FragmentListBinding;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

import java.util.Collections;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentList extends Fragment {
    private FragmentListBinding binding;
    private ListItemAdapter adapter;
    private ShoppingViewModel viewModel;
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

        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);

        if (getArguments() != null) {
            FragmentListArgs args = FragmentListArgs.fromBundle(getArguments());

            listName = args.getListName();
            listId = args.getListId();

            viewModel.setCurrentListId(listId);
        }

        binding.listAppBar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        RecyclerView recyclerView = binding.recyclerViewListItems;
        adapter = new ListItemAdapter(new ListItemAdapter.OnListItemActionListener() {
            @Override
            public void onCheckbox(ListItem listItem) {
                viewModel.toggleItemStatus(listItem);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.listItems.observe(getViewLifecycleOwner(), newListItems -> {
            if (newListItems != null) {
                adapter.setItems(newListItems);
            }
        });

        binding.listAppBar.setTitle(listName);
    }
}