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
import com.liner_exe.smartcart.viewmodel.ProductViewModel;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

public class CategorySelectionFragment extends Fragment {
    private FragmentCategorySelectionBinding binding;
    private CategorySelectionAdapter adapter;
    private ProductViewModel productViewModel;
    private ShoppingViewModel shoppingViewModel;
    private Product product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_category_selection, container, false
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        if (getArguments() != null) {
            CategorySelectionFragmentArgs args = CategorySelectionFragmentArgs.fromBundle(getArguments());
            product = args.getProduct();
        }

        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        adapter = new CategorySelectionAdapter();
        RecyclerView recyclerView = binding.rvCategoriesToSelect;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener((category, position) -> {
            productViewModel.updateProduct(new Product(
                    product.getId(),
                    product.getName(),
                    category.getId()
            ));
            shoppingViewModel.setSelectedCategory(category);
            NavHostFragment.findNavController(this).popBackStack();
        });

        shoppingViewModel.categories.observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                adapter.setItems(categories);
            }
        });
    }
}