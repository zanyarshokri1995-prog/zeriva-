package com.zeriva.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {

    private final int GREEN = Color.rgb(12, 45, 31);
    private final int GOLD = Color.rgb(212, 175, 55);
    private final int WHITE = Color.WHITE;

    private DB db;
    private TextView orderPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB();
        showHome();
    }

    // =========================
    // صفحه اصلی
    // =========================

    private void showHome() {

        LinearLayout main = layout();

        main.addView(
                new ZerivaLogoView(this),
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(220)
                )
        );

        main.addView(title("ZERIVA", 42));

        TextView subtitle = new TextView(this);
        subtitle.setText(
                "انگور ممتاز مریوان\n" +
                "دریاچه زریوار • مریوان"
        );
        subtitle.setTextColor(WHITE);
        subtitle.setTextSize(19);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, dp(5), 0, dp(25));
        main.addView(subtitle);

        button(main, "مشتریان", v -> customers());
        button(main, "حساب‌ها و معاملات", v -> accounts());
        button(main, "ثبت سفارش", v -> addOrder());
        button(main, "سفارش‌ها", v -> orderList());
        button(main, "قیمت روز", v -> dailyPrice());
        button(main, "خرید از باغدار", v -> purchase());
        button(main, "فروش و ارسال", v -> sale());
        button(main, "گزارش‌ها", v -> reports());

        // پشتیبانی
        supportBox(main);

        setPage(main);
    }

    // =========================
    // مشتریان
    // =========================

    private void customers() {

        LinearLayout l = layout();

        l.addView(title("مشتریان ZERIVA", 30));

        button(l, "➕ ثبت مشتری جدید", v -> addCustomer());
        button(l, "📋 مشتریان بر اساس شهر", v -> customerByCity());
        button(l, "📋 لیست همه مشتریان", v -> customerList());
        button(l, "بازگشت", v -> showHome());

        setPage(l);
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

        setPage(l);
    }

    private void customerList() {

        LinearLayout l = layout();

        l.addView(title("لیست مشتریان", 30));

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT id,name,phone " +
                "FROM customers " +
                "ORDER BY name COLLATE NOCASE",
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

        setPage(l);
    }

    private void customerByCity() {

        LinearLayout l = layout();

        l.addView(title("مشتریان بر اساس شهر", 30));

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT o.city, o.customer_no, " +
                "o.full_name, o.phone " +
                "FROM orders o " +
                "INNER JOIN (" +
                " SELECT customer_no, MAX(id) AS max_id " +
                " FROM orders " +
                " GROUP BY customer_no" +
                ") latest " +
                "ON o.customer_no=latest.customer_no " +
                "AND o.id=latest.max_id " +
                "ORDER BY COALESCE(NULLIF(o.city,''),'بدون شهر'), " +
                "o.full_name",
                null
        );

        String lastCity = null;
        int count = 0;

        while (c.moveToNext()) {

            String city = c.getString(0);

            String cityName =
                    (city == null || city.trim().isEmpty())
                            ? "بدون شهر"
                            : city;

            if (lastCity == null ||
                    !lastCity.equals(cityName)) {

                l.addView(cityHeader(cityName));
                lastCity = cityName;
            }

            l.addView(card(
                    "شماره مشتری: " + c.getInt(1) +
                    "\nنام: " + c.getString(2) +
                    "\nموبایل: " + c.getString(3)
            ));

            count++;
        }

        c.close();

        if (count == 0) {
            l.addView(text("هنوز سفارشی ثبت نشده است."));
        }

        button(l, "بازگشت", v -> customers());

        setPage(l);
    }

    private TextView cityHeader(String city) {

        TextView t = title("🏙️ " + city, 24);

        t.setPadding(
                0,
                dp(18),
                0,
                dp(10)
        );

        return t;
    }

    // =========================
    // قیمت روز
    // =========================

    private void dailyPrice() {

        LinearLayout l = layout();

        l.addView(title("قیمت روز انگور", 30));

        double current = getDailyPrice();

        l.addView(card(
                "قیمت فعلی هر کیلو:\n" +
                money(current) +
                " تومان"
        ));

        EditText price =
                input("قیمت جدید هر کیلو - تومان");

        l.addView(price);

        button(l, "ثبت قیمت روز", v -> {

            double p =
                    number(price.getText().toString());

            if (p <= 0) {
                toast("قیمت صحیح وارد کنید");
                return;
            }

            ContentValues x = new ContentValues();

            x.put("key", "daily_price");
            x.put("value", p);

            SQLiteDatabase d =
                    db.getWritableDatabase();

            d.delete(
                    "settings",
                    "key=?",
                    new String[]{"daily_price"}
            );

            d.insert("settings", null, x);

            // فقط سفارش‌های تحویل‌نشده
            updateUndeliveredOrdersPrice(p);

            toast(
                    "قیمت روز ذخیره شد\n" +
                    "سفارش‌های تحویل‌نشده بروزرسانی شدند"
            );

            dailyPrice();
        });

        button(l, "بازگشت", v -> showHome());

        setPage(l);
    }

    private double getDailyPrice() {

        Cursor c =
                db.getReadableDatabase().rawQuery(
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

    private void updateUndeliveredOrdersPrice(
            double price) {

        SQLiteDatabase d =
                db.getWritableDatabase();

        /*
         * فقط سفارش‌هایی که delivered=0 هستند
         * با قیمت جدید محاسبه می‌شوند.
         */

        d.execSQL(
                "UPDATE orders SET " +
                "daily_price=?, " +
                "total_amount=kg*?, " +
                "balance=MAX((kg*?)-deposit,0) " +
                "WHERE delivered=0",
                new Object[]{
                        price,
                        price,
                        price
                }
        );
    }

    // =========================
    // ثبت سفارش
    // =========================

    private void addOrder() {

        LinearLayout l = layout();

        l.addView(title("ثبت سفارش جدید", 30));

        EditText name =
                input("نام و نام خانوادگی");

        EditText phone =
                input("شماره موبایل");

        EditText province =
                input("استان");

        EditText city =
                input("شهر");

        EditText address =
                input("آدرس");

        EditText grape =
                input("نوع انگور");

        EditText kg =
                input("وزن دلخواه - کیلو");

        EditText deposit =
                input("مبلغ بیعانه");

        EditText description =
                input("توضیحات");

        l.addView(name);
        l.addView(phone);
        l.addView(province);
        l.addView(city);
        l.addView(address);
        l.addView(grape);
        l.addView(kg);

        orderPriceView = card(
                "قیمت روز هر کیلو: " +
                money(getDailyPrice()) +
                " تومان"
        );

        l.addView(orderPriceView);

        button(
                l,
                "🔄 بروزرسانی قیمت روز",
                v -> refreshOrderPrice()
        );

        l.addView(deposit);
        l.addView(description);

        button(l, "ثبت سفارش", v -> {

            String n =
                    name.getText().toString().trim();

            String p =
                    phone.getText().toString().trim();

            String pr =
                    province.getText().toString().trim();

            String c =
                    city.getText().toString().trim();

            String a =
                    address.getText().toString().trim();

            String g =
                    grape.getText().toString().trim();

            double weight =
                    number(kg.getText().toString());

            double dep =
                    number(deposit.getText().toString());

            String desc =
                    description.getText()
                            .toString()
                            .trim();

            double daily =
                    getDailyPrice();

            if (n.isEmpty()) {
                toast("نام و نام خانوادگی را وارد کنید");
                return;
            }

            if (p.isEmpty()) {
                toast("شماره موبایل را وارد کنید");
                return;
            }

            if (c.isEmpty()) {
                toast("شهر را وارد کنید");
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

            if (dep < 0 ||
                    dep > weight * daily) {

                toast("مبلغ بیعانه صحیح نیست");
                return;
            }

            double total =
                    weight * daily;

            double balance =
                    total - dep;

            int customerNo =
                    createCustomerIfNeeded(n, p);

            ContentValues x =
                    new ContentValues();

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
            x.put("delivered", 0);
            x.put(
                    "date",
                    System.currentTimeMillis()
            );

            db.getWritableDatabase()
                    .insert(
                            "orders",
                            null,
                            x
                    );

            toast(
                    "سفارش ثبت شد\n" +
                    "شماره مشتری: " +
                    customerNo
            );

            showHome();
        });

        button(l, "بازگشت", v -> showHome());

        setPage(l);
    }

    private void refreshOrderPrice() {

        if (orderPriceView == null) {
            return;
        }

        double currentPrice =
                getDailyPrice();

        orderPriceView.setText(
                "قیمت روز هر کیلو: " +
                money(currentPrice) +
                " تومان"
        );

        toast(
                "قیمت روز: " +
                money(currentPrice) +
                " تومان"
        );
    }

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

        ContentValues x =
                new ContentValues();

        x.put("name", name);
        x.put("phone", phone);

        long id =
                d.insert(
                        "customers",
                        null,
                        x
                );

        return (int) id;
    }

    // =========================
    // لیست سفارش‌ها
    // =========================

    private void orderList() {

        LinearLayout l = layout();

        l.addView(title("سفارش‌های ZERIVA", 30));

        Cursor c =
                db.getReadableDatabase().rawQuery(
                        "SELECT id,customer_no,full_name," +
                        "phone,province,city,address," +
                        "grape_type,kg,daily_price," +
                        "total_amount,deposit,balance," +
                        "description,delivered " +
                        "FROM orders " +
                        "ORDER BY id DESC",
                        null
                );

        if (c.getCount() == 0) {

            l.addView(
                    text("هنوز سفارشی ثبت نشده است.")
            );
        }

        while (c.moveToNext()) {

            final int orderId =
                    c.getInt(0);

            int customerNo =
                    c.getInt(1);

            String name =
                    c.getString(2);

            String phone =
                    c.getString(3);

            String province =
                    c.getString(4);

            String city =
                    c.getString(5);

            String address =
                    c.getString(6);

            String grape =
                    c.getString(7);

            double kg =
                    c.getDouble(8);

            double price =
                    c.getDouble(9);

            double total =
                    c.getDouble(10);

            double deposit =
                    c.getDouble(11);

            double balance =
                    c.getDouble(12);

            String description =
                    c.getString(13);

            boolean delivered =
                    c.getInt(14) == 1;

            l.addView(card(
                    "شماره مشتری: " +
                    customerNo +

                    "\nنام: " +
                    name +

                    "\nموبایل: " +
                    phone +

                    "\nاستان: " +
                    province +

                    "\nشهر: " +
                    city +

                    "\nآدرس: " +
                    address +

                    "\nنوع انگور: " +
                    grape +

                    "\nوزن: " +
                    money(kg) +
                    " کیلو" +

                    "\nقیمت ثبت‌شده: " +
                    money(price) +
                    " تومان" +

                    "\nمبلغ کل: " +
                    money(total) +
                    " تومان" +

                    "\nبیعانه: " +
                    money(deposit) +
                    " تومان" +

                    "\nمانده: " +
                    money(balance) +
                    " تومان" +

                    "\nوضعیت: " +
                    (
                            delivered
                                    ? "تحویل شده"
                                    : "در انتظار تحویل"
                    ) +

                    "\nتوضیحات: " +
                    (
                            description == null ||
                            description.isEmpty()
                                    ? "-"
                                    : description
                    )
            ));

            if (!delivered) {

                button(
                        l,
                        "✅ تحویل شد - سفارش " +
                        customerNo,
                        v -> markDelivered(orderId)
                );
            }
        }

        c.close();

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    private void markDelivered(int orderId) {

        ContentValues x =
                new ContentValues();

        x.put("delivered", 1);

        db.getWritableDatabase()
                .update(
                        "orders",
                        x,
                        "id=?",
                        new String[]{
                                String.valueOf(orderId)
                        }
                );

        toast(
                "سفارش تحویل‌شده ثبت شد"
        );

        orderList();
    }

    // =========================
    // حساب‌ها
    // =========================

    private void accounts() {

        LinearLayout l = layout();

        l.addView(
                title("حساب‌ها و معاملات", 30)
        );

        button(
                l,
                "➕ ثبت معامله",
                v -> addTransaction()
        );

        button(
                l,
                "📋 دفتر معاملات",
                v -> transactionList()
        );

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    private void addTransaction() {

        LinearLayout l = layout();

        l.addView(title("ثبت معامله", 30));

        EditText customer =
                input("شماره مشتری");

        EditText description =
                input("شرح معامله");

        EditText debt =
                input("بدهکار");

        EditText payment =
                input("پرداخت");

        l.addView(customer);
        l.addView(description);
        l.addView(debt);
        l.addView(payment);

        button(l, "ذخیره معامله", v -> {

            String customerNo =
                    customer.getText()
                            .toString()
                            .trim();

            String desc =
                    description.getText()
                            .toString()
                            .trim();

            double dValue =
                    number(
                            debt.getText()
                                    .toString()
                    );

            double pValue =
                    number(
                            payment.getText()
                                    .toString()
                    );

            if (customerNo.isEmpty()) {

                toast(
                        "شماره مشتری را وارد کنید"
                );

                return;
            }

            ContentValues x =
                    new ContentValues();

            x.put(
                    "customer",
                    customerNo
            );

            x.put(
                    "description",
                    desc
            );

            x.put(
                    "debt",
                    dValue
            );

            x.put(
                    "payment",
                    pValue
            );

            x.put(
                    "date",
                    System.currentTimeMillis()
            );

            db.getWritableDatabase()
                    .insert(
                            "transactions",
                            null,
                            x
                    );

            toast(
                    "معامله ذخیره شد"
            );

            accounts();
        });

        button(
                l,
                "بازگشت",
                v -> accounts()
        );

        setPage(l);
    }

    private void transactionList() {

        LinearLayout l = layout();

        l.addView(
                title("دفتر معاملات", 30)
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT customer," +
                                "description,debt,payment " +
                                "FROM transactions " +
                                "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            l.addView(
                    text(
                            "هنوز معامله‌ای ثبت نشده است."
                    )
            );
        }

        while (c.moveToNext()) {

            double debt =
                    c.getDouble(2);

            double payment =
                    c.getDouble(3);

            l.addView(card(
                    "مشتری: " +
                    c.getString(0) +

                    "\nشرح: " +
                    c.getString(1) +

                    "\nبدهکار: " +
                    money(debt) +

                    "\nپرداخت: " +
                    money(payment) +

                    "\nمانده: " +
                    money(debt - payment)
            ));
        }

        c.close();

        button(
                l,
                "بازگشت",
                v -> accounts()
        );

        setPage(l);
    }

    // =========================
    // خرید از باغدار
    // =========================

    private void purchase() {

        LinearLayout l = layout();

        l.addView(
                title("خرید از باغدار", 30)
        );

        EditText farmer =
                input("نام باغدار");

        EditText kg =
                input("مقدار انگور - کیلو");

        EditText price =
                input("قیمت کل");

        l.addView(farmer);
        l.addView(kg);
        l.addView(price);

        button(l, "ذخیره خرید", v -> {

            String f =
                    farmer.getText()
                            .toString()
                            .trim();

            double k =
                    number(
                            kg.getText()
                                    .toString()
                    );

            double p =
                    number(
                            price.getText()
                                    .toString()
                    );

            if (f.isEmpty()) {

                toast(
                        "نام باغدار را وارد کنید"
                );

                return;
            }

            ContentValues x =
                    new ContentValues();

            x.put("farmer", f);
            x.put("kg", k);
            x.put("price", p);
            x.put(
                    "date",
                    System.currentTimeMillis()
            );

            db.getWritableDatabase()
                    .insert(
                            "purchases",
                            null,
                            x
                    );

            toast(
                    "خرید ذخیره شد"
            );

            showHome();
        });

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    // =========================
    // فروش و ارسال
    // =========================

    private void sale() {

        LinearLayout l = layout();

        l.addView(
                title("فروش و ارسال", 30)
        );

        EditText customer =
                input("نام مشتری");

        EditText city =
                input("شهر مقصد");

        EditText kg =
                input("مقدار انگور - کیلو");

        EditText price =
                input("مبلغ فروش");

        l.addView(customer);
        l.addView(city);
        l.addView(kg);
        l.addView(price);

        button(l, "ذخیره فروش", v -> {

            String c =
                    customer.getText()
                            .toString()
                            .trim();

            String cityName =
                    city.getText()
                            .toString()
                            .trim();

            double k =
                    number(
                            kg.getText()
                                    .toString()
                    );

            double p =
                    number(
                            price.getText()
                                    .toString()
                    );

            if (c.isEmpty()) {

                toast(
                        "نام مشتری را وارد کنید"
                );

                return;
            }

            ContentValues x =
                    new ContentValues();

            x.put(
                    "customer",
                    c
            );

            x.put(
                    "city",
                    cityName
            );

            x.put(
                    "kg",
                    k
            );

            x.put(
                    "price",
                    p
            );

            x.put(
                    "date",
                    System.currentTimeMillis()
            );

            db.getWritableDatabase()
                    .insert(
                            "sales",
                            null,
                            x
                    );

            toast(
                    "فروش ذخیره شد"
            );

            showHome();
        });

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    // =========================
    // گزارش‌ها
    // =========================

    private void reports() {

        LinearLayout l = layout();

        l.addView(
                title("گزارش‌های ZERIVA", 30)
        );

        SQLiteDatabase d =
                db.getReadableDatabase();

        double purchases =
                sum(
                        d,
                        "purchases",
                        "price"
                );

        double sales =
                sum(
                        d,
                        "sales",
                        "price"
                );

        double purchasedKg =
                sum(
                        d,
                        "purchases",
                        "kg"
                );

        double soldKg =
                sum(
                        d,
                        "sales",
                        "kg"
                );

        double deposits =
                sum(
                        d,
                        "orders",
                        "deposit"
                );

        double orderBalance =
                sum(
                        d,
                        "orders",
                        "balance"
                );

        l.addView(card(
                "📦 خرید\n" +
                "مقدار: " +
                money(purchasedKg) +
                " کیلو\n" +
                "مبلغ: " +
                money(purchases) +
                " تومان"
        ));

        l.addView(card(
                "🚚 فروش\n" +
                "مقدار: " +
                money(soldKg) +
                " کیلو\n" +
                "مبلغ: " +
                money(sales) +
                " تومان"
        ));

        l.addView(card(
                "🛒 سفارش‌ها\n" +
                "بیعانه دریافت‌شده: " +
                money(deposits) +
                " تومان\n" +
                "مانده سفارش‌ها: " +
                money(orderBalance) +
                " تومان"
        ));

        l.addView(card(
                "💰 فروش منهای خرید\n" +
                money(sales - purchases) +
                " تومان"
        ));

        button(
                l,
                "📍 میزان سفارش هر شهر",
                v -> cityOrderReport()
        );

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    // =========================
    // گزارش سفارش هر شهر
    // =========================

    private void cityOrderReport() {

        LinearLayout l = layout();

        l.addView(
                title("📍 سفارش‌های هر شهر", 30)
        );

        /*
         * تمام سفارش‌های یک شهر با هم جمع می‌شوند.
         *
         * مثال:
         * تهران 2520 کیلو
         * اصفهان 500 کیلو
         * سنندج 1720 کیلو
         *
         * و هر شهر دیگری که در سفارش‌ها
         * ثبت شود، به صورت جداگانه نمایش داده می‌شود.
         */

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT " +
                                "COALESCE(" +
                                "NULLIF(TRIM(city),'')," +
                                "'بدون شهر'" +
                                ") AS city_name, " +

                                "COALESCE(" +
                                "SUM(kg),0" +
                                ") AS total_kg, " +

                                "COUNT(*) AS order_count " +

                                "FROM orders " +

                                "GROUP BY city_name " +

                                "ORDER BY total_kg DESC",
                                null
                        );

        double allKg = 0;
        int allOrders = 0;
        int rows = 0;

        while (c.moveToNext()) {

            String city =
                    c.getString(0);

            double kg =
                    c.getDouble(1);

            int orders =
                    c.getInt(2);

            allKg += kg;
            allOrders += orders;
            rows++;

            l.addView(
                    title(
                            "🏙️ " + city,
                            23
                    )
            );

            l.addView(
                    card(
                            "📦 مجموع سفارش: " +
                            money(kg) +
                            " کیلو\n" +

                            "🧾 تعداد سفارش: " +
                            orders
                    )
            );
        }

        c.close();

        if (rows == 0) {

            l.addView(
                    text(
                            "هنوز سفارشی ثبت نشده است."
                    )
            );

        } else {

            l.addView(
                    card(
                            "📊 مجموع کل سفارش‌ها\n" +

                            "مقدار: " +
                            money(allKg) +
                            " کیلو\n" +

                            "تعداد سفارش: " +
                            allOrders
                    )
            );
        }

        button(
                l,
                "بازگشت",
                v -> reports()
        );

        setPage(l);
    }

    // =========================
    // پشتیبانی ZERIVA
    // =========================

    private void supportBox(
            LinearLayout parent) {

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        box.setGravity(
                Gravity.CENTER
        );

        box.setPadding(
                dp(15),
                dp(18),
                dp(15),
                dp(18)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(GREEN);
        bg.setStroke(dp(1), GOLD);
        bg.setCornerRadius(dp(15));

        box.setBackground(bg);

        TextView supportTitle =
                new TextView(this);

        supportTitle.setText(
                "☎ پشتیبانی ZERIVA"
        );

        supportTitle.setTextColor(GOLD);
        supportTitle.setTextSize(20);

        supportTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        supportTitle.setGravity(
                Gravity.CENTER
        );

        box.addView(supportTitle);

        // شماره اصلی پشتیبانی
        TextView phone1 =
                new TextView(this);

        phone1.setText(
                "📞 09172172402"
        );

        phone1.setTextColor(WHITE);
        phone1.setTextSize(19);
        phone1.setGravity(Gravity.CENTER);

        phone1.setPadding(
                0,
                dp(12),
                0,
                dp(8)
        );

        phone1.setOnClickListener(v -> {

            try {

                Intent intent =
                        new Intent(
                                Intent.ACTION_DIAL,
                                Uri.parse(
                                        "tel:09172172402"
                                )
                        );

                startActivity(intent);

            } catch (Exception e) {

                toast(
                        "امکان باز کردن تماس وجود ندارد"
                );
            }
        });

        box.addView(phone1);

        // جای شماره دوم
        TextView phone2 =
                new TextView(this);

        phone2.setText(
                "📞 شماره پشتیبانی دوم: __________"
        );

        phone2.setTextColor(
                Color.LTGRAY
        );

        phone2.setTextSize(17);

        phone2.setGravity(
                Gravity.CENTER
        );

        phone2.setPadding(
                0,
                dp(8),
                0,
                dp(5)
        );

        box.addView(phone2);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        p.setMargins(
                0,
                dp(15),
                0,
                dp(10)
        );

        parent.addView(box, p);
    }

    // =========================
    // ظاهر برنامه
    // =========================

    private LinearLayout layout() {

        LinearLayout l =
                new LinearLayout(this);

        l.setOrientation(
                LinearLayout.VERTICAL
        );

        l.setGravity(
                Gravity.CENTER_HORIZONTAL
        );

        l.setPadding(
                dp(25),
                dp(30),
                dp(25),
                dp(25)
        );

        l.setBackgroundColor(GREEN);

        return l;
    }

    private void setPage(
            LinearLayout content) {

        ScrollView scroll =
                new ScrollView(this);

        scroll.setFillViewport(true);

        scroll.addView(content);

        setContentView(scroll);
    }

    private TextView title(
            String s,
            int size) {

        TextView t =
                new TextView(this);

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
                dp(20)
        );

        return t;
    }

    private TextView text(String s) {

        TextView t =
                new TextView(this);

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

        TextView t =
                new TextView(this);

        t.setText(s);
        t.setTextColor(WHITE);
        t.setTextSize(17);

        t.setGravity(
                Gravity.CENTER_VERTICAL
        );

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

    private EditText input(
            String hint) {

        EditText e =
                new EditText(this);

        e.setHint(hint);
        e.setHintTextColor(
                Color.LTGRAY
        );

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

        TextView b =
                new TextView(this);

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

    // =========================
    // ابزارها
    // =========================

    private double number(String s) {

        try {

            if (s == null ||
                    s.trim().isEmpty()) {

                return 0;
            }

            return Double.parseDouble(
                    s.replace(",", "")
            );

        } catch (Exception e) {

            return 0;
        }
    }

    private String money(double n) {

        return String.format(
                Locale.US,
                "%.0f",
                n
        );
    }

    private double sum(
            SQLiteDatabase d,
            String table,
            String column) {

        Cursor c =
                d.rawQuery(
                        "SELECT COALESCE(SUM(" +
                        column +
                        "),0) FROM " +
                        table,
                        null
                );

        double result = 0;

        if (c.moveToFirst()) {
            result = c.getDouble(0);
        }

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

    // =========================
    // لوگوی وکتوری ZERIVA
    // =========================

    private class ZerivaLogoView
            extends View {

        private final Paint paint =
                new Paint(
                        Paint.ANTI_ALIAS_FLAG
                );

        ZerivaLogoView(Context context) {

            super(context);

            setLayerType(
                    View.LAYER_TYPE_SOFTWARE,
                    null
            );
        }

        @Override
        protected void onDraw(
                Canvas canvas) {

            super.onDraw(canvas);

            float cx =
                    getWidth() / 2f;

            float cy =
                    dp(95);

            // حلقه طلایی
            paint.setStyle(
                    Paint.Style.STROKE
            );

            paint.setStrokeWidth(
                    dp(2)
            );

            paint.setColor(GOLD);

            canvas.drawCircle(
                    cx,
                    cy,
                    dp(78),
                    paint
            );

            paint.setStyle(
                    Paint.Style.FILL
            );

            // ساقه
            paint.setColor(GOLD);

            canvas.drawRoundRect(
                    cx - dp(3),
                    cy - dp(62),
                    cx + dp(3),
                    cy - dp(25),
                    dp(3),
                    dp(3),
                    paint
            );

            // برگ بزرگ
            Path leaf = new Path();

            leaf.moveTo(
                    cx,
                    cy - dp(48)
            );

            leaf.cubicTo(
                    cx + dp(20),
                    cy - dp(68),
                    cx + dp(48),
                    cy - dp(62),
                    cx + dp(52),
                    cy - dp(42)
            );

            leaf.cubicTo(
                    cx + dp(32),
                    cy - dp(38),
                    cx + dp(12),
                    cy - dp(38),
                    cx,
                    cy - dp(48)
            );

            leaf.close();

            paint.setColor(GOLD);

            canvas.drawPath(
                    leaf,
                    paint
            );

            // برگ کوچک
            Path leaf2 = new Path();

            leaf2.moveTo(
                    cx - dp(3),
                    cy - dp(45)
            );

            leaf2.cubicTo(
                    cx - dp(27),
                    cy - dp(58),
                    cx - dp(44),
                    cy - dp(48),
                    cx - dp(46),
                    cy - dp(31)
            );

            leaf2.cubicTo(
                    cx - dp(28),
                    cy - dp(30),
                    cx - dp(12),
                    cy - dp(34),
                    cx - dp(3),
                    cy - dp(45)
            );

            leaf2.close();

            canvas.drawPath(
                    leaf2,
                    paint
            );

            // خوشه انگور
            float r = dp(8);
            float gap = dp(15);

            for (int row = 0;
                 row < 6;
                 row++) {

                int count =
                        row + 1;

                float startX =
                        cx -
                        ((count - 1) * gap)
                        / 2f;

                for (int col = 0;
                     col < count;
                     col++) {

                    float x =
                            startX +
                            col * gap;

                    float y =
                            cy -
                            dp(20) +
                            row * dp(12);

                    // سایه کوچک
                    paint.setColor(
                            Color.rgb(
                                    130,
                                    100,
                                    25
                            )
                    );

                    canvas.drawCircle(
                            x + dp(1),
                            y + dp(1),
                            r,
                            paint
                    );

                    // دانه اصلی
                    paint.setColor(GOLD);

                    canvas.drawCircle(
                            x,
                            y,
                            r,
                            paint
                    );
                }
            }

            // نوشته پایین لوگو
            paint.setColor(WHITE);

            paint.setTextSize(
                    dp(15)
            );

            paint.setTypeface(
                    Typeface.DEFAULT_BOLD
            );

            paint.setTextAlign(
                    Paint.Align.CENTER
            );

            canvas.drawText(
                    "MARIWAN • ZARIVAR",
                    cx,
                    cy + dp(70),
                    paint
            );
        }
    }

    // =========================
    // دیتابیس
    // =========================

    private class DB
            extends SQLiteOpenHelper {

        DB() {

            super(
                    MainActivity.this,
                    "ZERIVA.db",
                    null,
                    3
            );
        }

        @Override
        public void onCreate(
                SQLiteDatabase d) {

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

            createNewTables(d);
        }

        private void createNewTables(
                SQLiteDatabase d) {

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS settings (" +
                    "key TEXT PRIMARY KEY," +
                    "value REAL DEFAULT 0)"
            );

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "customer_no INTEGER," +
                    "full_name TEXT," +
                    "phone TEXT," +
                    "province TEXT," +
                    "city TEXT," +
                    "address TEXT," +
                    "grape_type TEXT," +
                    "kg REAL DEFAULT 0," +
                    "daily_price REAL DEFAULT 0," +
                    "total_amount REAL DEFAULT 0," +
                    "deposit REAL DEFAULT 0," +
                    "balance REAL DEFAULT 0," +
                    "description TEXT," +
                    "date INTEGER," +
                    "delivered INTEGER DEFAULT 0)"
            );
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase d,
                int oldVersion,
                int newVersion) {

            if (oldVersion < 2) {

                createNewTables(d);
            }

            if (oldVersion < 3) {

                try {

                    d.execSQL(
                            "ALTER TABLE orders " +
                            "ADD COLUMN delivered " +
                            "INTEGER DEFAULT 0"
                    );

                } catch (Exception ignored) {
                    // ستون قبلاً وجود داشته است.
                }
            }
        }
    }
}
