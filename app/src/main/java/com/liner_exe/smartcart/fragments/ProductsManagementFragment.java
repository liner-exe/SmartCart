package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.domain.models.Product;
import com.liner_exe.smartcart.adapters.ProductsManagementAdapter;
import com.liner_exe.smartcart.databinding.FragmentProductsManagementBinding;
import com.liner_exe.smartcart.dialogs.ProductDialogFragment;
import com.liner_exe.smartcart.modal.ProductEditSheet;
import com.liner_exe.smartcart.viewmodel.ProductViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductsManagementFragment extends Fragment {
    private FragmentProductsManagementBinding binding;
    private ProductsManagementAdapter adapter;
    private ProductViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductsManagementBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();
        bindDialog();
    }

    private void setupToolbar() {
        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void setupRecyclerView() {
        adapter = new ProductsManagementAdapter(
            new ProductsManagementAdapter.OnProductActionListener() {
                @Override
                public void onEdit(Product product) {
                    ProductEditSheet.newInstance(product).show(
                            getChildFragmentManager(), "ProductEditSheet"
                    );
                }

                @Override
                public void onRename(Product product) {
                    ProductDialogFragment.newInstance(product.getName(), newName -> {
                        Product updatedProduct = new Product(product.getId(), newName, product.getCategoryId());
                        viewModel.updateProduct(updatedProduct);
                    }).show(getChildFragmentManager(), "RenameProductDialog");
                }

                @Override
                public void onDelete(Product product) {
                    viewModel.deleteProductById(product.getId());
                }
        });

        binding.rvProductsManagement.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvProductsManagement.setAdapter(adapter);

        adapter.setOnItemClickListener(((product, position) -> {
            ProductEditSheet.newInstance(product).show(
                    getChildFragmentManager(), "ProductEditSheet"
            );
        }));
    }

    private void observeViewModel() {
        viewModel.products.observe(getViewLifecycleOwner(), newProducts -> {
            boolean isEmpty = newProducts == null || newProducts.isEmpty();

            adapter.setItems(newProducts);

            binding.rvProductsManagement.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            binding.emptyStateView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        });
    }

    private void bindDialog() {
        binding.fabAddProduct.setOnClickListener(v -> {
            ProductDialogFragment.newInstance(null, name -> {
                viewModel.addProduct(new Product(name));
            }).show(getChildFragmentManager(), "AddProductDialog");
        });
    }
}