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

        main.addView(title("ZERIVA", 42));

        TextView subtitle = new TextView(this);
        subtitle.setText("انگور ممتاز مریوان\nدریاچه زریوار • مریوان");
        subtitle.setTextColor(WHITE);
        subtitle.setTextSize(19);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, dp(10), 0, dp(25));
        main.addView(subtitle);

        button(main, "مشتریان", v -> customers());
        button(main, "حساب‌ها و معاملات", v -> accounts());
        button(main, "ثبت سفارش", v -> addOrder());
        button(main, "سفارش‌ها", v -> orderList());
        button(main, "قیمت روز", v -> dailyPrice());
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

            ContentValues x = new ContentValues();
            x.put("name", n);
            x.put("phone", p);

            db.getWritableDatabase()
                    .insert("customers", null, x);

            toast("مشتری ذخیره شد");
            customers();
        });

        button(l, "بازگشت", v -> customers());

        setContentView(l);
    }

    private void customerList() {

        LinearLayout l = layout();

        l.addView(title("لیست مشتریان", 30));

        Cursor c = db.getReadableDatabase().rawQuery(
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

    // ================= قیمت روز =================

    private void dailyPrice() {

        LinearLayout l = layout();

        l.addView(title("قیمت روز انگور", 30));

        double current = getDailyPrice();

        l.addView(card(
                "قیمت فعلی هر کیلو:\n" +
                money(current) +
                " تومان"
        ));

        EditText price = input("قیمت جدید هر کیلو - تومان");

        l.addView(price);

        button(l, "ثبت قیمت روز", v -> {

            double p = number(
                    price.getText().toString()
            );

            if (p <= 0) {
                toast("قیمت صحیح وارد کنید");
                return;
            }

            ContentValues x = new ContentValues();
            x.put("value", p);

            SQLiteDatabase d =
                    db.getWritableDatabase();

            d.delete(
                    "settings",
                    "key=?",
                    new String[]{"daily_price"}
            );

            d.insert("settings", null, xWithKey(x));

            toast("قیمت روز ذخیره شد");
            dailyPrice();
        });

        button(l, "بازگشت", v -> showHome());

        setContentView(l);
    }

    private ContentValues xWithKey(ContentValues x) {
        x.put("key", "daily_price");
        return x;
    }

    private double getDailyPrice() {

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT value FROM settings " +
                "WHERE key='daily_price' LIMIT 1",
                null
        );

        double result = 0;

        if (c.moveToFirst()) {
            result = c.getDouble(0);
        }

        c.close();

        return result;
    }

    // ================= سفارش جدید =================

    private void addOrder() {

        LinearLayout l = layout();

        l.addView(title("ثبت سفارش جدید", 30));

        EditText name = input("نام و نام خانوادگی");
        EditText phone = input("شماره موبایل");
        EditText province = input("استان");
        EditText city = input("شهر");
        EditText address = input("آدرس");
        EditText grape = input("نوع انگور");
        EditText kg = input("وزن دلخواه - کیلو");
        EditText deposit = input("مبلغ بیعانه");
        EditText description = input("توضیحات");

        l.addView(name);
        l.addView(phone);
        l.addView(province);
        l.addView(city);
        l.addView(address);
        l.addView(grape);
        l.addView(kg);

        l.addView(card(
                "قیمت روز هر کیلو: " +
                money(getDailyPrice()) +
                " تومان"
        ));

        l.addView(deposit);
        l.addView(description);

        button(l, "ثبت سفارش", v -> {

            String n = name.getText().toString().trim();
            String p = phone.getText().toString().trim();
            String pr = province.getText().toString().trim();
            String c = city.getText().toString().trim();
            String a = address.getText().toString().trim();
            String g = grape.getText().toString().trim();

            double weight =
                    number(kg.getText().toString());

            double dep =
                    number(deposit.getText().toString());

            String desc =
                    description.getText().toString().trim();

            double daily = getDailyPrice();

            if (n.isEmpty()) {
                toast("نام و نام خانوادگی را وارد کنید");
                return;
            }

            if (p.isEmpty()) {
                toast("شماره موبایل را وارد کنید");
                return;
            }

            if (weight <= 0) {
                toast("وزن سفارش را وارد کنید");
                return;
            }

            if (daily <= 0) {
                toast("ابتدا قیمت روز را ثبت کنید");
                return;
            }

            double total = weight * daily;
            double balance = total - dep;

            if (dep > total) {
                toast("بیعانه نمی‌تواند بیشتر از مبلغ کل باشد");
                return;
            }

            int customerNo = createCustomerIfNeeded(
                    n,
                    p
            );

            ContentValues x = new ContentValues();

            x.put("customer_no", customerNo);
            x.put("full_name", n);
            x.put("phone", p);
            x.put("province", pr);
            x.put("city", c);
            x.put("address", a);
            x.put("grape_type", g);
            x.put("kg", weight);
            x.put("daily_price", daily);
            x.put("total_amount", total);
            x.put("deposit", dep);
            x.put("balance", balance);
            x.put("description", desc);
            x.put("date", System.currentTimeMillis());

            db.getWritableDatabase()
                    .insert("orders", null, x);

            toast(
                    "سفارش ثبت شد\n" +
                    "شماره مشتری: " +
                    customerNo
            );

            showHome();
        });

        button(l, "بازگشت", v -> showHome());

        setContentView(l);
    }

    // ================= ساخت مشتری خودکار =================

    private int createCustomerIfNeeded(
            String name,
            String phone) {

        SQLiteDatabase d =
                db.getWritableDatabase();

        Cursor c = d.rawQuery(
                "SELECT id FROM customers " +
                "WHERE phone=? LIMIT 1",
                new String[]{phone}
        );

        if (c.moveToFirst()) {

            int id = c.getInt(0);
            c.close();

            return id;
        }

        c.close();

        ContentValues x = new ContentValues();

        x.put("name", name);
        x.put("phone", phone);

        long id =
                d.insert("customers", null, x);

        return (int) id;
    }

    // ================= لیست سفارش‌ها =================

    private void orderList() {

        LinearLayout l = layout();

        l.addView(title("سفارش‌های ZERIVA", 30));

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT customer_no,full_name,phone," +
                "province,city,grape_type,kg," +
                "daily_price,total_amount,deposit,balance," +
                "description FROM orders " +
                "ORDER BY id DESC",
                null
        );

        if (c.getCount() == 0) {

            l.addView(
                    text("هنوز سفارشی ثبت نشده است.")
            );
        }

        while (c.moveToNext()) {

            int customerNo = c.getInt(0);
            String name = c.getString(1);
            String phone = c.getString(2);
            String province = c.getString(3);
            String city = c.getString(4);
            String grape = c.getString(5);

            double kg = c.getDouble(6);
            double price = c.getDouble(7);
            double total = c.getDouble(8);
            double deposit = c.getDouble(9);
            double balance = c.getDouble(10);

            String description = c.getString(11);

            l.addView(card(
                    "شماره مشتری: " + customerNo +
                    "\nنام: " + name +
                    "\nموبایل: " + phone +
                    "\nاستان: " + province +
                    "\nشهر: " + city +
                    "\nنوع انگور: " + grape +
                    "\nوزن: " + money(kg) + " کیلو" +
                    "\nقیمت روز: " + money(price) + " تومان" +
                    "\nمبلغ کل: " + money(total) + " تومان" +
                    "\nبیعانه: " + money(deposit) + " تومان" +
                    "\nمانده: " + money(balance) + " تومان" +
                    "\nتوضیحات: " +
                    (description == null ||
                     description.isEmpty()
                            ? "-"
                            : description)
            ));
        }

        c.close();

        button(l, "بازگشت", v -> showHome());

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

            double dValue =
                    number(debt.getText().toString());

            double pValue =
                    number(payment.getText().toString());

            if (customerNo.isEmpty()) {
                toast("شماره مشتری را وارد کنید");
                return;
            }

            ContentValues x = new ContentValues();

            x.put("customer", customerNo);
            x.put("description", desc);
            x.put("debt", dValue);
            x.put("payment", pValue);
            x.put("date", System.currentTimeMillis());

            db.getWritableDatabase()
                    .insert("transactions", null, x);

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
            l.addView(
                    text("هنوز معامله‌ای ثبت نشده است.")
            );
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

    // ================= خرید =================

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

            String f =
                    farmer.getText().toString().trim();

            double k =
                    number(kg.getText().toString());

            double p =
                    number(price.getText().toString());

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

            String c =
                    customer.getText().toString().trim();

            String cityName =
                    city.getText().toString().trim();

            double k =
                    number(kg.getText().toString());

            double p =
                    number(price.getText().to
