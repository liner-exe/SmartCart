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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.Product;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.CategorySelectionAdapter;
import com.liner_exe.smartcart.databinding.FragmentCategorySelectionBinding;
import com.liner_exe.smartcart.viewmodel.CategoryViewModel;
import com.liner_exe.smartcart.viewmodel.ProductViewModel;

public class CategorySelectionFragment extends Fragment {
    private FragmentCategorySelectionBinding binding;
    private CategorySelectionAdapter adapter;
    private ProductViewModel productViewModel;
    private CategoryViewModel categoryViewModel;
    private Product product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategorySelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        handleArguments();

        setupToolbar();
        setupRecyclerView();
        observeViewModel();
    }

    private void handleArguments() {
        if (getArguments() != null) {
            CategorySelectionFragmentArgs args = CategorySelectionFragmentArgs.fromBundle(getArguments());
            product = args.getProduct();
        }
    }

    private void setupToolbar() {
        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void setupRecyclerView() {
        adapter = new CategorySelectionAdapter();

        binding.rvCategoriesToSelect.setAdapter(adapter);
        binding.rvCategoriesToSelect.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener((category, position) -> {
            productViewModel.updateProduct(new Product(
                    product.getId(),
                    product.getName(),
                    category.getId()
            ));
            categoryViewModel.setSelectedCategory(category);
            NavHostFragment.findNavController(this).popBackStack();
        });
    }

    private void observeViewModel() {
        categoryViewModel.filteredCategories.observe(getViewLifecycleOwner(), categories -> {
            adapter.setItems(categories);
        });
    }
}