package com.zeriva.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int GREEN = Color.rgb(12, 45, 31);
    private final int GOLD = Color.rgb(212, 175, 55);
    private final int WHITE = Color.WHITE;

    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB();
        showHome();
    }

    // ================= صفحه اصلی =================

    private void showHome() {

        LinearLayout main = layout();

        TextView title = title("ZERIVA", 42);
        main.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("انگور ممتاز مریوان\nدریاچه زریوار • مریوان");
        subtitle.setTextColor(WHITE);
        subtitle.setTextSize(19);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, dp(10), 0, dp(25));
        main.addView(subtitle);

        button(main, "مشتریان", v -> customers());
        button(main, "حساب‌ها و معاملات", v -> accounts());
        button(main, "خرید از باغدار", v -> purchase());
        button(main, "فروش و ارسال", v -> sale());
        button(main, "گزارش‌ها", v -> reports());

        setContentView(main);
    }

    // ================= مشتریان =================

    private void customers() {

        LinearLayout l = layout();

        l.addView(title("مشتریان ZERIVA", 30));

        button(l, "➕ ثبت مشتری جدید", v -> addCustomer());
        button(l, "📋 لیست مشتریان", v -> customerList());
        button(l, "بازگشت", v -> showHome());

        setContentView(l);
    }

    private void addCustomer() {

        LinearLayout l = layout();
        l.addView(title("ثبت مشتری جدید", 30));

        EditText name = input("نام مشتری");
        EditText phone = input("شماره تماس");
        l.addView(name);
        l.addView(phone);

        button(l, "ذخیره مشتری", v -> {

            String n = name.getText().toString().trim();
            String p = phone.getText().toString().trim();

            if (n.isEmpty()) {
                toast("نام مشتری را وارد کنید");
                return;
            }

            SQLiteDatabase d = db.getWritableDatabase();

            ContentValues x = new ContentValues();
            x.put("name", n);
            x.put("phone", p);

            d.insert("customers", null, x);

            toast("مشتری ذخیره شد");
            customers();
        });

        button(l, "بازگشت", v -> customers());

        setContentView(l);
    }

    private void customerList() {

        LinearLayout l = layout();
        l.addView(title("لیست مشتریان", 30));

        SQLiteDatabase d = db.getReadableDatabase();

        Cursor c = d.rawQuery(
                "SELECT id,name,phone FROM customers ORDER BY id",
                null
        );

        if (c.getCount() == 0) {
            l.addView(text("هنوز مشتری ثبت نشده است."));
        }

        while (c.moveToNext()) {

            int id = c.getInt(0);
            String name = c.getString(1);
            String phone = c.getString(2);

            l.addView(card(
                    "شماره مشتری: " + id +
                    "\nنام: " + name +
                    "\nتلفن: " +
                    (phone == null || phone.isEmpty()
                            ? "ثبت نشده"
                            : phone)
            ));
        }

        c.close();

        button(l, "بازگشت", v -> customers());

        setContentView(l);
    }

    // ================= حساب‌ها =================

    private void accounts() {

        LinearLayout l = layout();

        l.addView(title("حساب‌ها و معاملات", 30));

        button(l, "➕ ثبت معامله", v -> addTransaction());
        button(l, "📋 دفتر معاملات", v -> transactionList());
        button(l, "بازگشت", v -> showHome());

        setContentView(l);
    }

    private void addTransaction() {

        LinearLayout l = layout();

        l.addView(title("ثبت معامله", 30));

        EditText customer = input("شماره مشتری");
        EditText description = input("شرح معامله");
        EditText debt = input("بدهکار");
        EditText payment = input("پرداخت");

        l.addView(customer);
        l.addView(description);
        l.addView(debt);
        l.addView(payment);

        button(l, "ذخیره معامله", v -> {

            String customerNo =
                    customer.getText().toString().trim();

            String desc =
                    description.getText().toString().trim();

            String debtValue =
                    debt.getText().toString().trim();

            String paymentValue =
                    payment.getText().toString().trim();

            if (customerNo.isEmpty()) {
                toast("شماره مشتری را وارد کنید");
                return;
            }

            double dValue = number(debtValue);
            double pValue = number(paymentValue);

            SQLiteDatabase d =
                    db.getWritableDatabase();

            ContentValues x = new ContentValues();

            x.put("customer", customerNo);
            x.put("description", desc);
            x.put("debt", dValue);
            x.put("payment", pValue);
            x.put("date", System.currentTimeMillis());

            d.insert("transactions", null, x);

            toast("معامله ذخیره شد");
            accounts();
        });

        button(l, "بازگشت", v -> accounts());

        setContentView(l);
    }

    private void transactionList() {

        LinearLayout l = layout();

        l.addView(title("دفتر معاملات", 30));

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT customer,description,debt,payment " +
                "FROM transactions ORDER BY id DESC",
                null
        );

        if (c.getCount() == 0) {
            l.addView(text("هنوز معامله‌ای ثبت نشده است."));
        }

        while (c.moveToNext()) {

            String customer = c.getString(0);
            String desc = c.getString(1);
            double debt = c.getDouble(2);
            double payment = c.getDouble(3);

            double balance = debt - payment;

            l.addView(card(
                    "مشتری: " + customer +
                    "\nشرح: " + desc +
                    "\nبدهکار: " + money(debt) +
                    "\nپرداخت: " + money(payment) +
                    "\nمانده: " + money(balance)
            ));
        }

        c.close();

        button(l, "بازگشت", v -> accounts());

        setContentView(l);
    }

    // ================= خرید از باغدار =================

    private void purchase() {

        LinearLayout l = layout();

        l.addView(title("خرید از باغدار", 30));

        EditText farmer = input("نام باغدار");
        EditText kg = input("مقدار انگور - کیلو");
        EditText price = input("قیمت کل");

        l.addView(farmer);
        l.addView(kg);
        l.addView(price);

        button(l, "ذخیره خرید", v -> {

            String f = farmer.getText().toString().trim();
            double k = number(kg.getText().toString());
            double p = number(price.getText().toString());

            if (f.isEmpty()) {
                toast("نام باغدار را وارد کنید");
                return;
            }

            ContentValues x = new ContentValues();

            x.put("farmer", f);
            x.put("kg", k);
            x.put("price", p);
            x.put("date", System.currentTimeMillis());

            db.getWritableDatabase()
                    .insert("purchases", null, x);

            toast("خرید ذخیره شد");
            showHome();
        });

        button(l, "بازگشت", v -> showHome());

        setContentView(l);
    }

    // ================= فروش =================

    private void sale() {

        LinearLayout l = layout();

        l.addView(title("فروش و ارسال", 30));

        EditText customer = input("نام مشتری");
        EditText city = input("شهر مقصد");
        EditText kg = input("مقدار انگور - کیلو");
        EditText price = input("مبلغ فروش");

        l.addView(customer);
        l.addView(city);
        l.addView(kg);
        l.addView(price);

        button(l, "ذخیره فروش", v -> {

            String c = customer.getText().toString().trim();
            String cityName = city.getText().toString().trim();
            double k = number(kg.getText().toString());
            double p = number(price.getText().toString());

            if (c.isEmpty()) {
                toast("نام مشتری را وارد کنید");
                return;
            }

            ContentValues x = new ContentValues();

            x.put("customer", c);
            x.put("city", cityName);
            x.put("kg", k);
            x.put("price", p);
            x.put("date", System.currentTimeMillis());

            db.getWritableDatabase()
                    .insert("sales", null, x);

            toast("فروش ذخیره شد");
            showHome();
        });

        button(l, "بازگشت", v -> showHome());

        setContentView(l);
    }

    // ================= گزارش‌ها =================

    private void reports() {

        LinearLayout l = layout();

        l.addView(title("گزارش‌های ZERIVA", 30));

        SQLiteDatabase d = db.getReadableDatabase();

        double purchases = sum(d, "purchases", "price");
        double sales = sum(d, "sales", "price");
        double purchasedKg = sum(d, "purchases", "kg");
        double soldKg = sum(d, "sales", "kg");

        l.addView(card(
                "📦 خرید\n" +
                "مقدار: " + money(purchasedKg) + " کیلو\n" +
                "مبلغ: " + money(purchases)
        ));

        l.addView(card(
                "🚚 فروش\n" +
                "مقدار: " + money(soldKg) + " کیلو\n" +
                "مبلغ: " + money(sales)
        ));

        l.addView(card(
                "💰 فروش منهای خرید\n" +
                money(sales - purchases)
        ));

        button(l, "بازگشت", v -> showHome());

        setContentView(l);
    }

    // ================= ابزارهای ظاهری =================

    private LinearLayout layout() {

        LinearLayout l = new LinearLayout(this);

        l.setOrientation(LinearLayout.VERTICAL);
        l.setGravity(Gravity.CENTER_HORIZONTAL);

        l.setPadding(
                dp(25),
                dp(40),
                dp(25),
                dp(25)
        );

        l.setBackgroundColor(GREEN);

        return l;
    }

    private TextView title(String s, int size) {

        TextView t = new TextView(this);

        t.setText(s);
        t.setTextColor(GOLD);
        t.setTextSize(size);
        t.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        t.setGravity(Gravity.CENTER);

        t.setPadding(
                0,
                0,
                0,
                dp(25)
        );

        return t;
    }

    private TextView text(String s) {

        TextView t = new TextView(this);

        t.setText(s);
        t.setTextColor(WHITE);
        t.setTextSize(19);
        t.setGravity(Gravity.CENTER);

        t.setPadding(
                dp(10),
                dp(25),
                dp(10),
                dp(25)
        );

        return t;
    }

    private TextView card(String s) {

        TextView t = new TextView(this);

        t.setText(s);
        t.setTextColor(WHITE);
        t.setTextSize(17);
        t.setGravity(Gravity.CENTER_VERTICAL);

        t.setPadding(
                dp(15),
                dp(15),
                dp(15),
                dp(15)
        );

        GradientDrawable g =
                new GradientDrawable();

        g.setColor(GREEN);
        g.setStroke(dp(1), GOLD);
        g.setCornerRadius(dp(15));

        t.setBackground(g);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        p.setMargins(
                0,
                dp(5),
                0,
                dp(5)
        );

        t.setLayoutParams(p);

        return t;
    }

    private EditText input(String hint) {

        EditText e = new EditText(this);

        e.setHint(hint);
        e.setHintTextColor(Color.LTGRAY);
        e.setTextColor(WHITE);
        e.setTextSize(18);
        e.setSingleLine(true);

        e.setPadding(
                dp(15),
                dp(8),
                dp(15),
                dp(8)
        );

        GradientDrawable g =
                new GradientDrawable();

        g.setColor(GREEN);
        g.setStroke(dp(1), GOLD);
        g.setCornerRadius(dp(15));

        e.setBackground(g);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(60)
                );

        p.setMargins(
                0,
                dp(5),
                0,
                dp(5)
        );

        e.setLayoutParams(p);

        return e;
    }

    private void button(
            LinearLayout parent,
            String text,
            View.OnClickListener click) {

        TextView b = new TextView(this);

        b.setText(text);
        b.setTextColor(GOLD);
        b.setTextSize(18);
        b.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        b.setGravity(Gravity.CENTER);
        b.setIncludeFontPadding(true);

        GradientDrawable g =
                new GradientDrawable();

        g.setColor(GREEN);
        g.setStroke(dp(2), GOLD);
        g.setCornerRadius(dp(18));

        b.setBackground(g);

        b.setOnClickListener(click);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(60)
                );

        p.setMargins(
                0,
                dp(5),
                0,
                dp(5)
        );

        parent.addView(b, p);
    }

    // ================= ابزارهای داده =================

    private double number(String s) {

        try {
            if (s == null || s.trim().isEmpty())
                return 0;

            return Double.parseDouble(
                    s.replace(",", "")
            );

        } catch (Exception e) {
            return 0;
        }
    }

    private String money(double n) {

        return String.format(
                java.util.Locale.US,
                "%.0f",
                n
        );
    }

    private double sum(
            SQLiteDatabase d,
            String table,
            String column) {

        Cursor c = d.rawQuery(
                "SELECT COALESCE(SUM(" +
                column +
                "),0) FROM " +
                table,
                null
        );

        double result = 0;

        if (c.moveToFirst())
            result = c.getDouble(0);

        c.close();

        return result;
    }

    private void toast(String s) {

        Toast.makeText(
                this,
                s,
                Toast.LENGTH_SHORT
        ).show();
    }

    private int dp(int value) {

        return Math.round(
                value *
                getResources()
                        .getDisplayMetrics()
                        .density
        );
    }

    @Override
    public void onBackPressed() {
        showHome();
    }

    // ================= دیتابیس =================

    private class DB extends SQLiteOpenHelper {

        DB() {
            super(
                    MainActivity.this,
                    "ZERIVA.db",
                    null,
                    1
            );
        }

        @Override
        public void onCreate(SQLiteDatabase d) {

            d.execSQL(
                    "CREATE TABLE customers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "phone TEXT)"
            );

            d.execSQL(
                    "CREATE TABLE transactions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "customer TEXT," +
                    "description TEXT," +
                    "debt REAL DEFAULT 0," +
                    "payment REAL DEFAULT 0," +
                    "date INTEGER)"
            );

            d.execSQL(
                    "CREATE TABLE purchases (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "farmer TEXT," +
                    "kg REAL DEFAULT 0," +
                    "price REAL DEFAULT 0," +
                    "date INTEGER)"
            );

            d.execSQL(
                    "CREATE TABLE sales (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "customer TEXT," +
                    "city TEXT," +
                    "kg REAL DEFAULT 0," +
                    "price REAL DEFAULT 0," +
                    "date INTEGER)"
            );
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase d,
                int oldVersion,
                int newVersion) {

        }
    }
            }
