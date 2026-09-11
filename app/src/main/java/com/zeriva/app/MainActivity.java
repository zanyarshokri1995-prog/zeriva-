package com.zeriva.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private final int GREEN = Color.rgb(12, 45, 31);
    private final int GOLD = Color.rgb(212, 175, 55);
    private final int WHITE = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setGravity(Gravity.CENTER_HORIZONTAL);
        main.setPadding(35, 55, 35, 35);
        main.setBackgroundColor(GREEN);

        TextView title = new TextView(this);
        title.setText("ZERIVA");
        title.setTextColor(GOLD);
        title.setTextSize(42);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        main.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("انگور ممتاز مریوان\nدریاچه زریوار • مریوان");
        subtitle.setTextColor(WHITE);
        subtitle.setTextSize(19);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 15, 0, 35);

        main.addView(subtitle);

        addButton(main, "مشتریان");
        addButton(main, "حساب‌ها و معاملات");
        addButton(main, "خرید از باغدار");
        addButton(main, "فروش و ارسال");
        addButton(main, "گزارش‌ها");

        setContentView(main);
    }

    private void addButton(LinearLayout parent, String text) {

        TextView button = new TextView(this);

        button.setText(text);
        button.setTextColor(GOLD);
        button.setTextSize(18);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setGravity(Gravity.CENTER);

        GradientDrawable background = new GradientDrawable();
        background.setColor(GREEN);
        background.setStroke(2, GOLD);
        background.setCornerRadius(18);

        button.setBackground(background);
        button.setPadding(10, 10, 10, 10);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        65);

        params.setMargins(0, 7, 0, 7);

        parent.addView(button, params);
    }
}
