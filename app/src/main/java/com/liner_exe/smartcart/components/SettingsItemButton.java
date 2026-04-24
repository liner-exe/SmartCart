package com.liner_exe.smartcart.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liner_exe.smartcart.R;

public class SettingsItemButton extends LinearLayout {
    private TextView title;
    private ImageView icon;

    public SettingsItemButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_settings_item_button, this);

        title = findViewById(R.id.settings_item_title);
        icon = findViewById(R.id.settings_item_icon);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingsItemButton);

            String text = a.getString(R.styleable.SettingsItemButton_itemText);
            title.setText(text);

            int img = a.getResourceId(R.styleable.SettingsItemButton_itemIcon, R.drawable.info_filled);
            icon.setImageResource(img);

            int defaultColor = Color.parseColor("#b3e5fc");
            int color = a.getColor(R.styleable.SettingsItemButton_itemColor, defaultColor);
            this.setBackgroundColor(color);

            a.recycle();
        }
    }
}
