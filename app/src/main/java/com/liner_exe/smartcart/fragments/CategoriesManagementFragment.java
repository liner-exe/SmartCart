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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liner_exe.domain.models.Category;
import com.liner_exe.smartcart.adapters.CategoriesManagementAdapter;
import com.liner_exe.smartcart.databinding.FragmentCategoriesManagementBinding;
import com.liner_exe.smartcart.viewmodel.CategoryViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesManagementFragment extends Fragment {
    private FragmentCategoriesManagementBinding binding;
    private CategoriesManagementAdapter adapter;
    private CategoryViewModel categoryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoriesManagementBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();
        setupFab();
    }

    private void setupToolbar() {
        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void setupRecyclerView() {
        adapter = new CategoriesManagementAdapter(new CategoriesManagementAdapter.OnCategoryActionListener() {
            @Override
            public void onDelete(Category category) {
                categoryViewModel.deleteCategoryById(category.getId());
            }
        });
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCategories.setAdapter(adapter);

        adapter.setOnItemClickListener((category, position) -> navigateToEdit(category));
    }

    private void observeViewModel() {
        categoryViewModel.categories.observe(getViewLifecycleOwner(), newCategories -> {
            boolean isEmpty = newCategories == null || newCategories.isEmpty();

            adapter.setItems(newCategories);

            binding.rvCategories.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            binding.emptyStateView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        });
    }

    private void setupFab() {
        binding.fabAddCategory.setOnClickListener(v -> navigateToEdit(null));
    }

    private void navigateToEdit(@Nullable Category category) {
        NavDirections action = CategoriesManagementFragmentDirections
                .actionCategoryManagementFragmentToCategoryEditFragment()
                .setCategory(category);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }
}