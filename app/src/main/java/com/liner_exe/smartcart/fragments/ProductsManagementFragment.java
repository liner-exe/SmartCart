package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.domain.models.Product;
import com.liner_exe.smartcart.R;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products_management,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        RecyclerView recyclerView = binding.recyclerViewProductsManagement;
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

        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        adapter.setOnItemClickListener(((product, position) -> {
            ProductEditSheet.newInstance(product).show(
                    getChildFragmentManager(), "ProductEditSheet"
            );
        }));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.products.observe(getViewLifecycleOwner(), newProducts -> {
            if (newProducts != null) {
                adapter.setItems(new ArrayList<>(newProducts));
            }
        });

        bindDialog();
    }

    private void bindDialog() {
        binding.fabAddProduct.setOnClickListener(v -> {
            ProductDialogFragment.newInstance(null, name -> {
                viewModel.addProduct(new Product(name));
            }).show(getChildFragmentManager(), "AddProductDialog");
        });
    }
}