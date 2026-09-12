package com.zeriva.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends Activity {

    // =========================
    // رنگ‌های ZERIVA
    // =========================
    private static final int DARK_GREEN = Color.rgb(15, 48, 30);
    private static final int GREEN = Color.rgb(23, 74, 42);
    private static final int GOLD = Color.rgb(212, 175, 55);
    private static final int WHITE = Color.WHITE;
    private static final int LIGHT = Color.rgb(235, 235, 230);
    private static final int DARK = Color.rgb(30, 30, 30);

    private LinearLayout root;
    private DB db;

    // =========================
    // استان‌ها و شهرها
    // =========================
    private final HashMap<String, String[]> provinces = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DB(this);
        loadCities();

        showHome();
    }

    // =========================================================
    // شهرها
    // =========================================================
    private void loadCities() {

        provinces.put("آذربایجان شرقی",
                new String[]{"تبریز", "مراغه", "مرند", "میانه", "اهر", "شبستر"});

        provinces.put("آذربایجان غربی",
                new String[]{"ارومیه", "خوی", "مهاباد", "میاندوآب", "بوکان", "نقده"});

        provinces.put("اردبیل",
                new String[]{"اردبیل", "مشگین‌شهر", "پارس‌آباد", "خلخال"});

        provinces.put("اصفهان",
                new String[]{"اصفهان", "کاشان", "خمینی‌شهر", "نجف‌آباد", "شاهین‌شهر"});

        provinces.put("البرز",
                new String[]{"کرج", "نظرآباد", "هشتگرد", "طالقان"});

        provinces.put("ایلام",
                new String[]{"ایلام", "دهلران", "مهران", "آبدانان"});

        provinces.put("بوشهر",
                new String[]{"بوشهر", "برازجان", "گناوه", "کنگان"});

        provinces.put("تهران",
                new String[]{"تهران", "ری", "شهریار", "اسلامشهر", "ورامین"});

        provinces.put("چهارمحال و بختیاری",
                new String[]{"شهرکرد", "بروجن", "فارسان", "لردگان"});

        provinces.put("خراسان جنوبی",
                new String[]{"بیرجند", "قائن", "فردوس", "طبس"});

        provinces.put("خراسان رضوی",
                new String[]{"مشهد", "نیشابور", "سبزوار", "تربت حیدریه", "قوچان"});

        provinces.put("خراسان شمالی",
                new String[]{"بجنورد", "شیروان", "اسفراین", "جاجرم"});

        provinces.put("خوزستان",
                new String[]{"اهواز", "آبادان", "خرمشهر", "دزفول", "اندیمشک", "شوش"});

        provinces.put("زنجان",
                new String[]{"زنجان", "ابهر", "خرمدره", "قیدار"});

        provinces.put("سمنان",
                new String[]{"سمنان", "شاهرود", "دامغان", "گرمسار"});

        provinces.put("سیستان و بلوچستان",
                new String[]{"زاهدان", "چابهار", "ایرانشهر", "خاش", "سراوان"});

        provinces.put("فارس",
                new String[]{"شیراز", "مرودشت", "جهرم", "فسا", "لار"});

        provinces.put("قزوین",
                new String[]{"قزوین", "تاکستان", "آبیک", "الوند"});

        provinces.put("قم",
                new String[]{"قم"});

        provinces.put("کردستان",
                new String[]{"سنندج", "مریوان", "سقز", "بانه", "کامیاران", "بیجار"});

        provinces.put("کرمان",
                new String[]{"کرمان", "رفسنجان", "سیرجان", "جیرفت", "بم"});

        provinces.put("کرمانشاه",
                new String[]{"کرمانشاه", "اسلام‌آباد غرب", "جوانرود", "پاوه", "سرپل ذهاب"});

        provinces.put("کهگیلویه و بویراحمد",
                new String[]{"یاسوج", "گچساران", "دهدشت"});

        provinces.put("گلستان",
                new String[]{"گرگان", "گنبد کاووس", "علی‌آباد", "کردکوی"});

        provinces.put("گیلان",
                new String[]{"رشت", "انزلی", "لاهیجان", "رودسر", "آستارا"});

        provinces.put("لرستان",
                new String[]{"خرم‌آباد", "بروجرد", "دورود", "الیگودرز", "کوهدشت"});

        provinces.put("مازندران",
                new String[]{"ساری", "بابل", "آمل", "قائم‌شهر", "نوشهر", "چالوس"});

        provinces.put("مرکزی",
                new String[]{"اراک", "ساوه", "خمین", "محلات"});

        provinces.put("هرمزگان",
                new String[]{"بندرعباس", "قشم", "میناب", "بندر لنگه"});

        provinces.put("همدان",
                new String[]{"همدان", "ملایر", "نهاوند", "رزن"});

        provinces.put("یزد",
                new String[]{"یزد", "میبد", "اردکان", "بافق"});
    }

    // =========================================================
    // صفحه اصلی
    // =========================================================
    private void showHome() {

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(DARK_GREEN);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(18), dp(20), dp(18), dp(25));

        TextView logo = text("ZERIVA", 32, GOLD);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setGravity(Gravity.CENTER);
        content.addView(logo, params(-1, 65));

        TextView subtitle = text("Shani Grape • Mariwan • Zarivar", 15, LIGHT);
        subtitle.setGravity(Gravity.CENTER);
        content.addView(subtitle, params(-1, 35));

        addButton(content, "👥  مشتریان", v -> showCustomers());
        addButton(content, "💰  حساب‌ها و معاملات", v -> showTransactions());
        addButton(content, "🍇  خرید از باغدار", v -> showBuyFromFarmer());
        addButton(content, "🚚  فروش و ارسال", v -> showSales());
        addButton(content, "📊  گزارش‌ها", v -> showReports());

        TextView info = text(
                "\nZERIVA\nانگور شانی مریوان\nدریاچه زریوار\n\nنسخه مدیریت کسب‌وکار",
                14,
                LIGHT
        );
        info.setGravity(Gravity.CENTER);
        content.addView(info, params(-1, -2));

        scroll.addView(content);
        root.addView(scroll);

        setContentView(root);
    }

    // =========================================================
    // مشتریان
    // =========================================================
    private void showCustomers() {

        LinearLayout page = page("مشتریان");

        addButton(page, "➕ ثبت مشتری جدید", v -> addCustomer());

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT id,name,phone FROM customers ORDER BY id ASC",
                null
        );

        if (c.getCount() == 0) {
            page.addView(text("هنوز مشتری ثبت نشده است.", 16, LIGHT));
        }

        while (c.moveToNext()) {

            int id = c.getInt(0);
            String name = c.getString(1);
            String phone = c.getString(2);

            TextView item = text(
                    "شماره مشتری: " + id +
                            "\nنام: " + name +
                            "\nتماس: " + phone,
                    16,
                    DARK
            );

            item.setPadding(dp(15), dp(15), dp(15), dp(15));
            item.setBackgroundColor(Color.WHITE);

            LinearLayout.LayoutParams p =
                    new LinearLayout.LayoutParams(-1, -2);

            p.setMargins(0, dp(5), 0, dp(5));

            page.addView(item, p);
        }

        c.close();

        setPage(page);
    }

    // =========================================================
    // ثبت مشتری
    // =========================================================
    private void addCustomer() {

        LinearLayout page = page("ثبت مشتری");

        EditText name = input("نام مشتری");
        EditText phone = input("شماره تماس");

        page.addView(name);
        page.addView(phone);

        addButton(page, "💾 ذخیره مشتری", v -> {

            String n = name.getText().toString().trim();
            String p = phone.getText().toString().trim();

            if (n.isEmpty()) {
                toast("نام مشتری را وارد کنید");
                return;
            }

            SQLiteDatabase database = db.getWritableDatabase();

            database.execSQL(
                    "INSERT INTO customers(name,phone) VALUES(?,?)",
                    new Object[]{n, p}
            );

            toast("مشتری با موفقیت ثبت شد");

            showCustomers();
        });

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // خرید از باغدار
    // =========================================================
    private void showBuyFromFarmer() {

        LinearLayout page = page("خرید از باغدار");

        EditText farmer = input("نام باغدار");
        EditText weight = input("وزن خرید (کیلو)");
        EditText price = input("قیمت هر کیلو");

        page.addView(farmer);
        page.addView(weight);
        page.addView(price);

        addButton(page, "💾 ثبت خرید", v -> {

            if (farmer.getText().toString().trim().isEmpty()) {
                toast("نام باغدار را وارد کنید");
                return;
            }

            double w = number(weight);
            double pr = number(price);

            if (w <= 0) {
                toast("وزن را وارد کنید");
                return;
            }

            double total = w * pr;

            db.getWritableDatabase().execSQL(
                    "INSERT INTO purchases(farmer,weight,price,total) VALUES(?,?,?,?)",
                    new Object[]{
                            farmer.getText().toString(),
                            w,
                            pr,
                            total
                    }
            );

            toast("خرید ثبت شد\nمبلغ: " + money(total));

            showHome();
        });

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // فروش و ارسال
    // =========================================================
    private void showSales() {

        LinearLayout page = page("فروش و ارسال");

        final Spinner province = spinner(
                new ArrayList<>(provinces.keySet())
        );

        final Spinner city = new Spinner(this);

        EditText customer = input("نام مشتری");
        EditText weight = input("وزن سفارش (کیلو)");
        EditText price = input("قیمت هر کیلو");

        TextView totalText = text("مبلغ کل: 0", 18, GOLD);

        page.addView(label("استان"));
        page.addView(province);

        page.addView(label("شهر"));
        page.addView(city);

        updateCities(city, province.getSelectedItem().toString());

        province.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        updateCities(
                                city,
                                province.getItemAtPosition(position).toString()
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        page.addView(customer);
        page.addView(weight);

        // کنترل وزن
        LinearLayout weightRow = new LinearLayout(this);
        weightRow.setOrientation(LinearLayout.HORIZONTAL);
        weightRow.setGravity(Gravity.CENTER);

        Button minus = new Button(this);
        minus.setText("−");

        Button plus = new Button(this);
        plus.setText("+");

        weightRow.addView(minus, params(80, 55));
        weightRow.addView(plus, params(80, 55));

        page.addView(weightRow);

        page.addView(price);
        page.addView(totalText);

        View.OnClickListener calculate = v -> {

            double w = number(weight);
            double p = number(price);

            totalText.setText(
                    "مبلغ کل: " + money(w * p)
            );
        };

        minus.setOnClickListener(v -> {

            double w = number(weight);

            w -= 100;

            if (w < 0) w = 0;

            weight.setText(formatNumber(w));

            calculate.onClick(v);
        });

        plus.setOnClickListener(v -> {

            double w = number(weight);

            w += 100;

            weight.setText(formatNumber(w));

            calculate.onClick(v);
        });

        addButton(page, "📦 ثبت سفارش", v -> {

            String cust = customer.getText().toString().trim();

            if (cust.isEmpty()) {
                toast("نام مشتری را وارد کنید");
                return;
            }

            double w = number(weight);
            double p = number(price);

            if (w <= 0) {
                toast("وزن سفارش را وارد کنید");
                return;
            }

            String selectedProvince =
                    province.getSelectedItem().toString();

            String selectedCity =
                    city.getSelectedItem().toString();

            double total = w * p;

            db.getWritableDatabase().execSQL(
                    "INSERT INTO orders(customer,province,city,weight,price,total) " +
                            "VALUES(?,?,?,?,?,?)",
                    new Object[]{
                            cust,
                            selectedProvince,
                            selectedCity,
                            w,
                            p,
                            total
                    }
            );

            toast(
                    "سفارش ثبت شد\n" +
                            selectedCity +
                            " — " +
                            formatNumber(w) +
                            " کیلو"
            );

            showSales();
        });

        addButton(page, "📊 جمع سفارش شهرها", v -> showCityOrders());

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // شهرها
    // =========================================================
    private void updateCities(Spinner citySpinner, String province) {

        String[] cities = provinces.get(province);

        if (cities == null) {
            cities = new String[]{"انتخاب شهر"};
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        cities
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        citySpinner.setAdapter(adapter);
    }

    // =========================================================
    // جمع سفارش هر شهر جداگانه
    // =========================================================
    private void showCityOrders() {

        LinearLayout page = page("جمع سفارش شهرها");

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT city, SUM(weight), COUNT(*) " +
                        "FROM orders GROUP BY city ORDER BY city",
                null
        );

        if (c.getCount() == 0) {

            page.addView(
                    text(
                            "هنوز سفارشی ثبت نشده است.",
                            16,
                            LIGHT
                    )
            );

        } else {

            while (c.moveToNext()) {

                String city = c.getString(0);
                double weight = c.getDouble(1);
                int count = c.getInt(2);

                TextView item = text(
                        city +
                                "\nجمع سفارش: " +
                                formatNumber(weight) +
                                " کیلو" +
                                "\nتعداد سفارش: " +
                                count,
                        17,
                        DARK
                );

                item.setPadding(
                        dp(15),
                        dp(15),
                        dp(15),
                        dp(15)
                );

                item.setBackgroundColor(Color.WHITE);

                LinearLayout.LayoutParams p =
                        new LinearLayout.LayoutParams(-1, -2);

                p.setMargins(0, dp(5), 0, dp(5));

                page.addView(item, p);
            }
        }

        c.close();

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // معاملات
    // =========================================================
    private void showTransactions() {

        LinearLayout page = page("حساب‌ها و معاملات");

        addButton(
                page,
                "➕ ثبت معامله",
                v -> addTransaction()
        );

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT person,description,debt,payment " +
                        "FROM transactions ORDER BY id DESC",
                null
        );

        while (c.moveToNext()) {

            String person = c.getString(0);
            String description = c.getString(1);
            double debt = c.getDouble(2);
            double payment = c.getDouble(3);

            page.addView(
                    text(
                            "طرف حساب: " + person +
                                    "\nشرح: " + description +
                                    "\nبدهکار: " + money(debt) +
                                    "\nپرداخت: " + money(payment),
                            16,
                            LIGHT
                    )
            );
        }

        c.close();

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // ثبت معامله
    // =========================================================
    private void addTransaction() {

        LinearLayout page = page("ثبت معامله");

        EditText person = input("نام طرف حساب");
        EditText description = input("شرح معامله");
        EditText debt = input("بدهکار");
        EditText payment = input("پرداخت");

        page.addView(person);
        page.addView(description);
        page.addView(debt);
        page.addView(payment);

        addButton(page, "💾 ذخیره معامله", v -> {

            db.getWritableDatabase().execSQL(
                    "INSERT INTO transactions(person,description,debt,payment) " +
                            "VALUES(?,?,?,?)",
                    new Object[]{
                            person.getText().toString(),
                            description.getText().toString(),
                            number(debt),
                            number(payment)
                    }
            );

            toast("معامله ذخیره شد");

            showTransactions();
        });

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // گزارش‌ها
    // =========================================================
    private void showReports() {

        LinearLayout page = page("گزارش‌ها");

        SQLiteDatabase database = db.getReadableDatabase();

        Cursor orders = database.rawQuery(
                "SELECT COUNT(*), COALESCE(SUM(weight),0), " +
                        "COALESCE(SUM(total),0) FROM orders",
                null
        );

        if (orders.moveToFirst()) {

            int count = orders.getInt(0);
            double weight = orders.getDouble(1);
            double total = orders.getDouble(2);

            page.addView(
                    text(
                            "📦 سفارش‌ها\n" +
                                    "تعداد: " + count +
                                    "\nوزن کل: " + formatNumber(weight) + " کیلو" +
                                    "\nمبلغ کل: " + money(total),
                            17,
                            LIGHT
                    )
            );
        }

        orders.close();

        Cursor purchases = database.rawQuery(
                "SELECT COUNT(*), COALESCE(SUM(weight),0), " +
                        "COALESCE(SUM(total),0) FROM purchases",
                null
        );

        if (purchases.moveToFirst()) {

            int count = purchases.getInt(0);
            double weight = purchases.getDouble(1);
            double total = purchases.getDouble(2);

            page.addView(
                    text(
                            "\n🍇 خرید از باغدار\n" +
                                    "تعداد خرید: " + count +
                                    "\nوزن کل: " + formatNumber(weight) + " کیلو" +
                                    "\nمبلغ کل: " + money(total),
                            17,
                            LIGHT
                    )
            );
        }

        purchases.close();

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // ابزارهای UI
    // =========================================================
    private LinearLayout page(String title) {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(
                dp(18),
                dp(20),
                dp(18),
                dp(30)
        );
        layout.setBackgroundColor(DARK_GREEN);

        TextView titleView = text(title, 25, GOLD);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        titleView.setGravity(Gravity.CENTER);

        layout.addView(titleView, params(-1, 65));

        return layout;
    }

    private void setPage(LinearLayout page) {

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.addView(page);

        setContentView(scroll);
    }

    private void addBack(LinearLayout layout) {

        addButton(
                layout,
                "⬅ بازگشت",
                v -> showHome()
        );
    }

    private void addButton(
            LinearLayout layout,
            String title,
            View.OnClickListener listener) {

        Button button = new Button(this);

        button.setText(title);
        button.setTextSize(16);
        button.setTextColor(GOLD);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setAllCaps(false);
        button.setBackgroundColor(GREEN);
        button.setOnClickListener(listener);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(58)
                );

        p.setMargins(
                0,
                dp(7),
                0,
                dp(7)
        );

        layout.addView(button, p);
    }

    private TextView label(String s) {

        TextView t = text(s, 15, GOLD);
        t.setPadding(0, dp(8), 0, dp(3));

        return t;
    }

    private TextView text(
            String value,
            float size,
            int color) {

        TextView t = new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);

        return t;
    }

    private EditText input(String hint) {

        EditText e = new EditText(this);

        e.setHint(hint);
        e.setTextSize(16);
        e.setTextColor(WHITE);
        e.setHintTextColor(Color.LTGRAY);
        e.setPadding(
                dp(12),
                dp(8),
                dp(12),
                dp(8)
        );

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(58)
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

    private Spinner spinner(ArrayList<String> values) {

        Spinner spinner = new Spinner(this);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        values
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);

        return spinner;
    }

    private LinearLayout.LayoutParams params(
            int width,
            int height) {

        return new LinearLayout.LayoutParams(
                width == -1 ? -1 : dp(width),
                height == -1 ? -1 : dp(height)
        );
    }

    // =========================================================
    // ابزارهای عددی
    // =========================================================
    private double number(EditText editText) {

        try {

            String s =
                    editText.getText()
                            .toString()
                            .replace(",", "")
                            .replace("٬", "")
                            .trim();

            if (s.isEmpty()) return 0;

            return Double.parseDouble(s);

        } catch (Exception e) {

            return 0;
        }
    }

    private String formatNumber(double n) {

        if (n == (long) n) {

            return String.format(
                    Locale.US,
                    "%d",
                    (long) n
            );
        }

        return String.format(
                Locale.US,
                "%.2f",
                n
        );
    }

    private String money(double n) {

        return String.format(
                Locale.US,
                "%,.0f تومان",
                n
        );
    }

    private int dp(int value) {

        return (int) (
                value *
                        getResources()
                                .getDisplayMetrics()
                                .density
        );
    }

    private void toast(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

    // =========================================================
    // دیتابیس
    // =========================================================
    private static class DB extends SQLiteOpenHelper {

        private static final String DB_NAME =
                "zeriva.db";

        private static final int VERSION = 1;

        DB(Context context) {

            super(
                    context,
                    DB_NAME,
                    null,
                    VERSION
            );
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(
                    "CREATE TABLE customers (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT NOT NULL," +
                            "phone TEXT)"
            );

            db.execSQL(
                    "CREATE TABLE transactions (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "person TEXT," +
                            "description TEXT," +
                            "debt REAL DEFAULT 0," +
                            "payment REAL DEFAULT 0)"
            );

            db.execSQL(
                    "CREATE TABLE purchases (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "farmer TEXT," +
                            "weight REAL," +
                            "price REAL," +
                            "total REAL)"
            );

            db.execSQL(
                    "CREATE TABLE orders (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "customer TEXT," +
                            "province TEXT," +
                            "city TEXT," +
                            "weight REAL," +
                            "price REAL," +
                            "total REAL)"
            );
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase db,
                int oldVersion,
                int newVersion) {

            // فعلاً نیازی به حذف اطلاعات نداریم.
        }
    }
}
