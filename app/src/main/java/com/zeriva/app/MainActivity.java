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
        main.setPadding(dp(25), dp(40), dp(25), dp(25));
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
        subtitle.setPadding(0, dp(12), 0, dp(25));

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
        button.setIncludeFontPadding(true);
        button.setMinHeight(dp(60));

        GradientDrawable background = new GradientDrawable();
        background.setColor(GREEN);
        background.setStroke(dp(2), GOLD);
        background.setCornerRadius(dp(18));

        button.setBackground(background);

        button.setPadding(
                dp(10),
                dp(8),
                dp(10),
                dp(8)
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(60)
                );

        params.setMargins(
                0,
                dp(5),
                0,
                dp(5)
        );

        parent.addView(button, params);
    }

    private int dp(int value) {
        return Math.round(
                value * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }
}
