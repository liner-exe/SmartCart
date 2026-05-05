package com.liner_exe.smartcart.modal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.liner_exe.domain.models.Product;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.BottomSheetEditProductBinding;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

import java.io.Serializable;

public class ProductEditSheet extends BottomSheetDialogFragment {
    private BottomSheetEditProductBinding binding;
    private ShoppingViewModel viewModel;
    private Product product;

    public static ProductEditSheet newInstance(Product product) {
        ProductEditSheet sheet = new ProductEditSheet();
        Bundle args = new Bundle();
        args.putSerializable("arg_product", product);
        sheet.setArguments(args);
        return sheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable("arg_product");
        }

        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_edit_product, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setProduct(product);

        binding.productEditButtonDelete.setOnClickListener(v -> {
            viewModel.deleteProductById(product.getId());
            dismiss();
        });

        binding.productEditButtonDone.setOnClickListener(v -> {
            viewModel.updateProduct(product);
            dismiss();
        });
    }
}
