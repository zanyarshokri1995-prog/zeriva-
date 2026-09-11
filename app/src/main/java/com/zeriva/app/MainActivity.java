package com.zeriva.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(32, 32, 32, 32);
        layout.setBackgroundColor(Color.rgb(12, 45, 31));

        TextView title = new TextView(this);
        title.setText("ZERIVA");
        title.setTextColor(Color.rgb(212, 175, 55));
        title.setTextSize(38);
        title.setGravity(Gravity.CENTER);

        TextView subtitle = new TextView(this);
        subtitle.setText("انگور ممتاز مریوان\nدریاچه زریوار • مریوان");
        subtitle.setTextColor(Color.WHITE);
        subtitle.setTextSize(18);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 24, 0, 0);

        layout.addView(title);
        layout.addView(subtitle);

        setContentView(layout);
    }
}
