package com.liner_exe.smartcart.modal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.liner_exe.data.storage.SettingsManager;
import com.liner_exe.domain.enums.Currency;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.utils.QuantityCalculator;
import com.liner_exe.domain.utils.formatters.QuantityFormatter;
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

    private SettingsManager settingsManager;
    private Currency currentCurrency;
    private boolean decimalMode = false;

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

        settingsManager = new SettingsManager(requireContext());
        currentCurrency = settingsManager.getCurrency();

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
        binding.liEditQuantityEditText.setText(QuantityFormatter.format(listItem.getQuantity()));
        binding.liEditPriceEditText.setText(PriceValidator.format(listItem.getPrice()));
        binding.liEditMeasureUnitEditText.setText(listItem.getUnit());

        categoryViewModel.selectedCategory.observe(getViewLifecycleOwner(), category -> {
            if (category != null) {
                binding.liEditCategoryName.setText(category.getEmoji() + " " + category.getName());
            } else {
                binding.liEditCategoryName.setText("Не указана");
            }
        });

        categoryViewModel.loadCategoryById(listItem.getProduct().getId()) ;

        updateTotalDisplay();

        TextWatcher totalPriceUpdateTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String priceInput = binding.liEditPriceEditText.getText().toString();

                if (!priceInput.isEmpty() && !PriceValidator.isValid(priceInput)) {
                    binding.liEditPriceInputLayout.setError("Формат 99.99 или 9.9");
                } else {
                    binding.liEditPriceInputLayout.setError(null);
                }

                updateTotalDisplay();
            }
        };

        binding.liEditQuantityEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                String text = binding.liEditQuantityEditText.getText().toString();

                decimalMode = text.contains(".") || text.contains(",");
            }
        });

        binding.liEditQuantityEditText.addTextChangedListener(totalPriceUpdateTextWatcher);
        binding.liEditPriceEditText.addTextChangedListener(totalPriceUpdateTextWatcher);

        binding.liEditButtonMinus.setOnClickListener(v -> {
            double current = QuantityFormatter.parse(binding.liEditQuantityEditText.getText().toString());
            updateQuantityField(QuantityCalculator.decrement(current, decimalMode));
        });

        binding.liEditButtonPlus.setOnClickListener(v -> {
            double current = QuantityFormatter.parse(binding.liEditQuantityEditText.getText().toString());
            updateQuantityField(QuantityCalculator.increment(current, decimalMode));
        });

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

    private void updateTotalDisplay() {
        String quantityString = binding.liEditQuantityEditText.getText().toString();
        String priceString = binding.liEditPriceEditText.getText().toString();

        double quantity = QuantityFormatter.parse(quantityString);
        double price = (priceString.isEmpty() || !PriceValidator.isValid(priceString)) ?
                0 : PriceValidator.parse(priceString);

        binding.liEditTotalPriceText.setText(currentCurrency.format(price * quantity));
    }

    private void updateQuantityField(double newValue) {
        String formatted = QuantityFormatter.format(newValue);
        binding.liEditQuantityEditText.setText(formatted);
        binding.liEditQuantityEditText.setSelection(formatted.length());
    }

    private boolean updateListItemFromUI() {
        String currentName = binding.liEditNameEditText.getText().toString().trim();
        String quantityText = binding.liEditQuantityEditText.getText().toString();
        String priceText = binding.liEditPriceEditText.getText().toString();
        String unit = binding.liEditMeasureUnitEditText.getText().toString().trim();

        double quantity = quantityText.isEmpty() ? 1 : Double.parseDouble(quantityText);
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
                unit,
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