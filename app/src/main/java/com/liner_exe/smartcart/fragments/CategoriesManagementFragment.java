package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.Category;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.CategoriesManagementAdapter;
import com.liner_exe.smartcart.databinding.FragmentCategoriesManagementBinding;
import com.liner_exe.smartcart.viewmodel.CategoryViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesManagementFragment extends Fragment {
    private FragmentCategoriesManagementBinding binding;
    private CategoriesManagementAdapter adapter;
    private CategoryViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories_management,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        RecyclerView recyclerView = binding.rvCategories;
        adapter = new CategoriesManagementAdapter(new CategoriesManagementAdapter.OnCategoryActionListener() {
            @Override
            public void onDelete(Category category) {
                viewModel.deleteCategoryById(category.getId());
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.categories.observe(getViewLifecycleOwner(), newCategories -> {
            if (newCategories != null) {
                adapter.setItems(newCategories);
            }
        });

        adapter.setOnItemClickListener((category, position) -> {
            NavDirections action = CategoriesManagementFragmentDirections
                    .actionCategoryManagementFragmentToCategoryEditFragment()
                    .setCategory(category);
            Navigation.findNavController(view).navigate(action);
        });

        binding.fabAddCategory.setOnClickListener(v -> {
            NavDirections action = CategoriesManagementFragmentDirections
                    .actionCategoryManagementFragmentToCategoryEditFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }
}