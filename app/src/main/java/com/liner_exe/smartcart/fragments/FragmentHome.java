package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.ShoppingListsAdapter;
import com.liner_exe.smartcart.databinding.FragmentHomeBinding;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentHome extends Fragment {
    private FragmentHomeBinding binding;
    private ShoppingListsAdapter adapter;
    private ShoppingViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);

        RecyclerView recyclerView = binding.recyclerView;
        adapter = new ShoppingListsAdapter(Collections.emptyList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((shoppingList, position) -> {
            NavController navController = Navigation.findNavController(requireActivity(),
                    R.id.main_nav_host);

            Bundle bundle = new Bundle();
            bundle.putString("listName", shoppingList.getName());
            navController.navigate(R.id.action_mainFragment_to_fragmentList, bundle);
        });

        viewModel.shoppingLists.observe(getViewLifecycleOwner(), newLists -> {
            if (newLists != null) {
                adapter.setShoppingLists(newLists);
            }
        });
    }
}