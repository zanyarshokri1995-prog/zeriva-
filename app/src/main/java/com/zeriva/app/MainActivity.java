package com.zeriva.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int GREEN = Color.rgb(12, 45, 31);
    private final int GOLD = Color.rgb(212, 175, 55);
    private final int WHITE = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    private void showHome() {

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

        addButton(main, "مشتریان", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage("مشتریان");
            }
        });

        addButton(main, "حساب‌ها و معاملات", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage("حساب‌ها و معاملات");
            }
        });

        addButton(main, "خرید از باغدار", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage("خرید از باغدار");
            }
        });

        addButton(main, "فروش و ارسال", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage("فروش و ارسال");
            }
        });

        addButton(main, "گزارش‌ها", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage("گزارش‌ها");
            }
        });

        setContentView(main);
    }

    private void showPage(String pageTitle) {

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setPadding(dp(25), dp(45), dp(25), dp(25));
        page.setBackgroundColor(GREEN);

        TextView title = new TextView(this);
        title.setText(pageTitle);
        title.setTextColor(GOLD);
        title.setTextSize(30);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        page.addView(title);

        TextView message = new TextView(this);
        message.setText(
                "بخش " + pageTitle + "\n\n" +
                "این قسمت آماده توسعه است."
        );
        message.setTextColor(WHITE);
        message.setTextSize(20);
        message.setGravity(Gravity.CENTER);
        message.setPadding(0, dp(30), 0, dp(30));

        page.addView(message);

        addButton(page, "بازگشت به صفحه اصلی",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showHome();
                    }
                });

        setContentView(page);
    }

    private void addButton(
            LinearLayout parent,
            String text,
            View.OnClickListener listener) {

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

        button.setOnClickListener(listener);

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

    @Override
    public void onBackPressed() {
        showHome();
    }
}
