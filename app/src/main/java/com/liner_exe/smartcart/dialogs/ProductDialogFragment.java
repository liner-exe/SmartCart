package com.liner_exe.smartcart.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.liner_exe.smartcart.R;

public class ProductDialogFragment extends DialogFragment {
    public interface OnProductAddedListener {
        void onAdd(String name);
    }

    private OnProductAddedListener listener;
    private String currentName;

    public static ProductDialogFragment newInstance(String currentName, OnProductAddedListener listener) {
        ProductDialogFragment fragment = new ProductDialogFragment();
        fragment.listener = listener;
        fragment.currentName = currentName;

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.dialog_add_product, null);
        TextInputEditText editText = view.findViewById(R.id.edit_product_name);
        TextInputLayout inputLayout = view.findViewById(R.id.product_add_input_layout);

        if (currentName != null) {
            editText.setText(currentName);
            editText.setSelection(currentName.length());
        }

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(currentName == null ? R.string.dialog_add_product_title : R.string.action_rename)
                .setMessage(R.string.dialog_add_product_message)
                .setView(view)
                .setPositiveButton(currentName == null ? R.string.action_add : R.string.action_rename, null)
                .setNegativeButton(R.string.action_cancel, null)
                .create();

        editText.setOnEditorActionListener((textView, actionID, event) -> {
            if (actionID == EditorInfo.IME_ACTION_DONE) {
                attemptSave(editText, inputLayout, dialog);
                return true;
            }
            return false;
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            editText.requestFocus();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputLayout.setError(null);
                inputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        });

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

            if (button != null) {
                button.setOnClickListener(v -> {
                    attemptSave(editText, inputLayout, dialog);
                });
            }
        });

        return dialog;
    }

    private void attemptSave(TextInputEditText editText, TextInputLayout inputLayout,
                             AlertDialog dialog) {
        String name = editText.getText() != null ? editText.getText().toString().trim() : "";

        if (name.isEmpty()) {
            inputLayout.setError(getString(R.string.dialog_add_product_error_empty));
            inputLayout.setErrorEnabled(true);
        } else {
            if (listener != null) {
                listener.onAdd(name);
            }
            dialog.dismiss();
        }
    }
}
