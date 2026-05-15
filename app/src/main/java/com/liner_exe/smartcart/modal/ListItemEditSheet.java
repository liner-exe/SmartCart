package com.liner_exe.smartcart.modal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.utils.validators.PriceValidator;
import com.liner_exe.smartcart.databinding.BottomSheetEditListItemBinding;
import com.liner_exe.smartcart.viewmodel.CategoryViewModel;
import com.liner_exe.smartcart.viewmodel.ListItemsViewModel;
import com.liner_exe.smartcart.viewmodel.ProductViewModel;

public class ListItemEditSheet extends BottomSheetDialogFragment {
    private BottomSheetEditListItemBinding binding;
    private ListItemsViewModel listItemsViewModel;
    private CategoryViewModel categoryViewModel;
    private ProductViewModel productViewModel;
    private ListItem listItem;

    public static ListItemEditSheet newInstance(ListItem listItem) {
        ListItemEditSheet sheet = new ListItemEditSheet();
        Bundle args = new Bundle();
        args.putSerializable("arg_listItem", listItem);
        sheet.setArguments(args);
        return sheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            listItem = (ListItem) getArguments().getSerializable("arg_listItem");
        }

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        listItemsViewModel = new ViewModelProvider(requireActivity()).get(ListItemsViewModel.class);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BottomSheetEditListItemBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.liEditNameEditText.setText(listItem.getProduct().getName());
        binding.liEditQuantityEditText.setText(String.valueOf(listItem.getQuantity()));
        binding.liEditPriceEditText.setText(PriceValidator.format(listItem.getPrice()));

        binding.liEditButtonDelete.setOnClickListener(v -> {
            listItemsViewModel.deleteListItemById(listItem.getId(), listItem.getListId(),
                    listItem.getProduct().getId());
            dismiss();
        });

        binding.liEditPriceInputLayout.setError(null);

        binding.liEditButtonDone.setOnClickListener(v -> {
            if (!updateListItemFromUI()) return;

            Product updatedProduct = new Product(
                    listItem.getProduct().getId(),
                    listItem.getProduct().getName(),
                    listItem.getProduct().getCategoryId()
            );

            productViewModel.updateProduct(updatedProduct);
            listItemsViewModel.updateListItem(listItem);

            dismiss();
        });
    }

    private boolean updateListItemFromUI() {
        String currentName = binding.liEditNameEditText.getText().toString().trim();
        String quantityText = binding.liEditQuantityEditText.getText().toString();
        String priceText = binding.liEditPriceEditText.getText().toString();

        if (!PriceValidator.isValid(priceText)) {
            binding.liEditPriceInputLayout.setError("Формат 0.00");
            return false;
        }

        int quantity = quantityText.isEmpty() ? 1 : Integer.parseInt(quantityText);
        double price = PriceValidator.parse(priceText);

        listItem = new ListItem(
                listItem.getId(),
                new Product(
                        listItem.getProduct().getId(),
                        currentName,
                        listItem.getProduct().getCategoryId()
                ),
                quantity,
                price,
                listItem.isBought(),
                listItem.getListId(),
                null
        );

        return true;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        categoryViewModel.resetSelectedCategory();
    }
}
