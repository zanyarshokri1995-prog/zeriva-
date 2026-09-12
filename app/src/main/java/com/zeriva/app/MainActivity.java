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
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity {

    private final int GREEN = Color.rgb(12, 45, 31);
    private final int GOLD = Color.rgb(212, 175, 55);
    private final int WHITE = Color.WHITE;

    private DB db;

    private TextView orderPriceView;

    private EditText orderKgView;

    private Spinner provinceSpinner;
    private Spinner citySpinner;

    private String selectedMediaUri = "";

    // =========================================================
    // استان‌ها و شهرها
    // =========================================================

    private final LinkedHashMap<String, String[]> provinceCities =
            new LinkedHashMap<>();

    private void loadProvinceCities() {

        provinceCities.clear();

        provinceCities.put("آذربایجان شرقی",
                new String[]{"تبریز","مراغه","مرند","میانه","اهر","شبستر","سراب","بناب","اسکو","هریس"});

        provinceCities.put("آذربایجان غربی",
                new String[]{"ارومیه","خوی","مهاباد","میاندوآب","بوکان","نقده","سلماس","پیرانشهر","سردشت","ماکو"});

        provinceCities.put("اردبیل",
                new String[]{"اردبیل","مشگین‌شهر","پارس‌آباد","خلخال","نمین","گرمی","بیله‌سوار","نیر"});

        provinceCities.put("اصفهان",
                new String[]{"اصفهان","کاشان","خمینی‌شهر","نجف‌آباد","شاهین‌شهر","فلاورجان","مبارکه","گلپایگان","نطنز"});

        provinceCities.put("البرز",
                new String[]{"کرج","فردیس","نظرآباد","هشتگرد","طالقان","محمدشهر","ماهدشت"});

        provinceCities.put("ایلام",
                new String[]{"ایلام","دهلران","آبدانان","مهران","دره‌شهر","ایوان","سرابله"});

        provinceCities.put("بوشهر",
                new String[]{"بوشهر","برازجان","گناوه","دیر","کنگان","جم","خورموج","عسلویه"});

        provinceCities.put("تهران",
                new String[]{"تهران","ری","شمیرانات","شهریار","اسلامشهر","رباط‌کریم","پاکدشت","ورامین","دماوند","فیروزکوه"});

        provinceCities.put("چهارمحال و بختیاری",
                new String[]{"شهرکرد","بروجن","فارسان","لردگان","اردل","کوهرنگ","سامان"});

        provinceCities.put("خراسان جنوبی",
                new String[]{"بیرجند","قائن","فردوس","طبس","نهبندان","سرایان","درمیان"});

        provinceCities.put("خراسان رضوی",
                new String[]{"مشهد","نیشابور","سبزوار","تربت حیدریه","قوچان","کاشمر","تربت جام","تایباد","گناباد","چناران"});

        provinceCities.put("خراسان شمالی",
                new String[]{"بجنورد","شیروان","اسفراین","جاجرم","گرمه","آشخانه","فاروج"});

        provinceCities.put("خوزستان",
                new String[]{"اهواز","آبادان","خرمشهر","دزفول","شوشتر","اندیمشک","بهبهان","ماهشهر","شادگان","ایذه"});

        provinceCities.put("زنجان",
                new String[]{"زنجان","ابهر","خرمدره","قیدار","طارم","ماه‌نشان","سلطانیه"});

        provinceCities.put("سمنان",
                new String[]{"سمنان","شاهرود","دامغان","گرمسار","مهدی‌شهر","سرخه","آرادان"});

        provinceCities.put("سیستان و بلوچستان",
                new String[]{"زاهدان","چابهار","زابل","ایرانشهر","خاش","سراوان","کنارک","نیک‌شهر"});

        provinceCities.put("فارس",
                new String[]{"شیراز","مرودشت","جهرم","فسا","کازرون","لار","داراب","آباده","نورآباد","اقلید"});

        provinceCities.put("قزوین",
                new String[]{"قزوین","تاکستان","آبیک","الوند","بوئین‌زهرا","آوج"});

        provinceCities.put("قم",
                new String[]{"قم"});

        provinceCities.put("کردستان",
                new String[]{"سنندج","مریوان","سقز","بانه","کامیاران","قروه","بیجار","دیواندره","دهگلان","سروآباد"});

        provinceCities.put("کرمان",
                new String[]{"کرمان","رفسنجان","سیرجان","جیرفت","بم","زرند","کهنوج","راور","بردسیر"});

        provinceCities.put("کرمانشاه",
                new String[]{"کرمانشاه","اسلام‌آباد غرب","پاوه","جوانرود","سنقر","کنگاور","هرسین","سرپل ذهاب","قصرشیرین","گیلانغرب"});

        provinceCities.put("کهگیلویه و بویراحمد",
                new String[]{"یاسوج","دهدشت","گچساران","لیکک","سی‌سخت"});

        provinceCities.put("گلستان",
                new String[]{"گرگان","گنبدکاووس","علی‌آباد کتول","آق‌قلا","کردکوی","بندرترکمن","مینودشت","کلاله"});

        provinceCities.put("گیلان",
                new String[]{"رشت","انزلی","لاهیجان","رودسر","آستانه اشرفیه","لنگرود","رودبار","فومن","صومعه‌سرا","تالش"});

        provinceCities.put("لرستان",
                new String[]{"خرم‌آباد","بروجرد","دورود","الیگودرز","کوهدشت","نورآباد","ازنا","الشتر","پلدختر"});

        provinceCities.put("مازندران",
                new String[]{"ساری","بابل","آمل","قائم‌شهر","بهشهر","نکا","نوشهر","چالوس","تنکابن","رامسر"});

        provinceCities.put("مرکزی",
                new String[]{"اراک","ساوه","خمین","محلات","دلیجان","شازند","تفرش","آشتیان"});

        provinceCities.put("هرمزگان",
                new String[]{"بندرعباس","قشم","کیش","بندر لنگه","میناب","رودان","حاجی‌آباد","جاسک"});

        provinceCities.put("همدان",
                new String[]{"همدان","ملایر","نهاوند","رزن","اسدآباد","تویسرکان","کبودرآهنگ","فامنین"});

        provinceCities.put("یزد",
                new String[]{"یزد","میبد","اردکان","مهریز","بافق","ابرکوه","تفت","اشکذر"});

        provinceCities.put("کرج",
                new String[]{"کرج"});

        provinceCities.put("خارج از ایران",
                new String[]{"اقلیم کردستان عراق","عراق","ترکیه","امارات","سایر"});
    }

    // =========================================================
    // شروع برنامه
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DB();

        loadProvinceCities();

        showHome();
    }

    // =========================================================
    // صفحه اصلی
    // =========================================================

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

        subtitle.setPadding(
                0,
                dp(5),
                0,
                dp(25)
        );

        main.addView(subtitle);

        button(main, "مشتریان", v -> customers());
        button(main, "حساب‌ها و معاملات", v -> accounts());
        button(main, "ثبت سفارش", v -> addOrder());
        button(main, "سفارش‌ها", v -> orderList());
        button(main, "قیمت روز", v -> dailyPrice());
        button(main, "خرید از باغدار", v -> purchase());
        button(main, "فروش و ارسال", v -> sale());
        button(main, "گزارش‌ها", v -> reports());

        supportBox(main);

        setPage(main);
    }

    // =========================================================
    // مشتریان
    // =========================================================

    private void customers() {

        LinearLayout l = layout();

        l.addView(title("مشتریان ZERIVA", 30));

        button(l, "➕ ثبت مشتری جدید", v -> addCustomer());

        button(l, "📋 مشتریان بر اساس شهر",
                v -> customerByCity());

        button(l, "📋 لیست همه مشتریان",
                v -> customerList());

        button(l, "بازگشت",
                v -> showHome());

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

            String n =
                    name.getText()
                            .toString()
                            .trim();

            String p =
                    phone.getText()
                            .toString()
                            .trim();

            if (n.isEmpty()) {

                toast("نام مشتری را وارد کنید");

                return;
            }

            ContentValues x =
                    new ContentValues();

            x.put("name", n);
            x.put("phone", p);

            db.getWritableDatabase()
                    .insert(
                            "customers",
                            null,
                            x
                    );

            toast("مشتری ذخیره شد");

            customers();
        });

        button(l, "بازگشت",
                v -> customers());

        setPage(l);
    }

    private void customerList() {

        LinearLayout l = layout();

        l.addView(title("لیست مشتریان", 30));

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,name,phone " +
                                "FROM customers " +
                                "ORDER BY name COLLATE NOCASE",
                                null
                        );

        if (c.getCount() == 0) {

            l.addView(
                    text("هنوز مشتری ثبت نشده است.")
            );
        }

        while (c.moveToNext()) {

            int id = c.getInt(0);

            String name = c.getString(1);

            String phone = c.getString(2);

            l.addView(
                    card(
                            "شماره مشتری: " + id +
                            "\nنام: " + name +
                            "\nتلفن: " +
                            (
                                    phone == null ||
                                    phone.isEmpty()
                                            ? "ثبت نشده"
                                            : phone
                            )
                    )
            );
        }

        c.close();

        button(
                l,
                "بازگشت",
                v -> customers()
        );

        setPage(l);
    }

    private void customerByCity() {

        LinearLayout l = layout();

        l.addView(
                title("مشتریان بر اساس شهر", 30)
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT city,customer_no," +
                                "full_name,phone " +
                                "FROM orders " +
                                "ORDER BY " +
                                "COALESCE(NULLIF(city,'')," +
                                "'بدون شهر'),full_name",
                                null
                        );

        String lastCity = null;

        int count = 0;

        while (c.moveToNext()) {

            String city = c.getString(0);

            String cityName =
                    city == null ||
                    city.trim().isEmpty()
                            ? "بدون شهر"
                            : city;

            if (lastCity == null ||
                    !lastCity.equals(cityName)) {

                l.addView(
                        cityHeader(cityName)
                );

                lastCity = cityName;
            }

            l.addView(
                    card(
                            "شماره مشتری: " +
                            c.getInt(1) +

                            "\nنام: " +
                            c.getString(2) +

                            "\nموبایل: " +
                            c.getString(3)
                    )
            );

            count++;
        }

        c.close();

        if (count == 0) {

            l.addView(
                    text(
                            "هنوز سفارشی ثبت نشده است."
                    )
            );
        }

        button(
                l,
                "بازگشت",
                v -> customers()
        );

        setPage(l);
    }

    private TextView cityHeader(String city) {

        TextView t =
                title("🏙️ " + city, 24);

        t.setPadding(
                0,
                dp(18),
                0,
                dp(10)
        );

        return t;
    }

    // =========================================================
    // قیمت روز
    // =========================================================

    private void dailyPrice() {

        LinearLayout l = layout();

        l.addView(
                title("قیمت روز انگور", 30)
        );

        double current =
                getDailyPrice();

        l.addView(
                card(
                        "قیمت فعلی هر کیلو:\n" +
                        money(current) +
                        " تومان"
                )
        );

        EditText price =
                input(
                        "قیمت جدید هر کیلو - تومان"
                );

        l.addView(price);

        button(
                l,
                "ثبت قیمت روز",
                v -> {

                    double p =
                            number(
                                    price.getText()
                                            .toString()
                            );

                    if (p <= 0) {

                        toast(
                                "قیمت صحیح وارد کنید"
                        );

                        return;
                    }

                    ContentValues x =
                            new ContentValues();

                    x.put(
                            "key",
                            "daily_price"
                    );

                    x.put(
                            "value",
                            p
                    );

                    SQLiteDatabase d =
                            db.getWritableDatabase();

                    d.delete(
                            "settings",
                            "key=?",
                            new String[]{
                                    "daily_price"
                            }
                    );

                    d.insert(
                            "settings",
                            null,
                            x
                    );

                    updateUndeliveredOrdersPrice(p);

                    toast(
                            "قیمت روز ذخیره شد\n" +
                            "سفارش‌های تحویل‌نشده بروزرسانی شدند"
                    );

                    dailyPrice();
                }
        );

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    private double getDailyPrice() {

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT value " +
                                "FROM settings " +
                                "WHERE key='daily_price' " +
                                "LIMIT 1",
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

    // =========================================================
    // ثبت سفارش جدید
    // =========================================================

    private void addOrder() {

        selectedMediaUri = "";

        LinearLayout l = layout();

        l.addView(
                title("ثبت سفارش جدید", 30)
        );

        EditText name =
                input("نام و نام خانوادگی");

        EditText phone =
                input("شماره موبایل");

        l.addView(name);
        l.addView(phone);

        // استان
        provinceSpinner =
                createProvinceSpinner(l);

        // شهر
        citySpinner =
                createCitySpinner(l);

        EditText address =
                input("آدرس");

        EditText grape =
                input("نوع انگور");

        l.addView(address);
        l.addView(grape);

        // =====================================================
        // وزن با + و -
        // =====================================================

        l.addView(
                title("وزن سفارش", 21)
        );

        LinearLayout weightBox =
                new LinearLayout(this);

        weightBox.setOrientation(
                LinearLayout.HORIZONTAL
        );

        weightBox.setGravity(
                Gravity.CENTER
        );

        Button minus =
                smallButton("➖");

        Button plus =
                smallButton("➕");

        orderKgView =
                input("وزن - کیلو");

        orderKgView.setText("0");

        orderKgView.setGravity(
                Gravity.CENTER
        );

        LinearLayout.LayoutParams weightParams =
                new LinearLayout.LayoutParams(
                        0,
                        dp(60),
                        1
                );

        weightParams.setMargins(
                dp(5),
                dp(5),
                dp(5),
                dp(5)
        );

        orderKgView.setLayoutParams(
                weightParams
        );

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        dp(65),
                        dp(60)
                );

        buttonParams.setMargins(
                dp(5),
                dp(5),
                dp(5),
                dp(5)
        );

        weightBox.addView(
                minus,
                buttonParams
        );

        weightBox.addView(
                orderKgView
        );

        weightBox.addView(
                plus,
                buttonParams
        );

        l.addView(weightBox);

        minus.setOnClickListener(
                v -> changeWeight(-1)
        );

        plus.setOnClickListener(
                v -> changeWeight(1)
        );

        // قیمت
        orderPriceView =
                card(
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

        EditText deposit =
                input("مبلغ بیعانه");

        EditText description =
                input("توضیحات");

        l.addView(deposit);
        l.addView(description);

        // =====================================================
        // عکس / فیلم بار
        // =====================================================

        l.addView(
                title("رسانه بار", 21)
        );

        TextView mediaStatus =
                card(
                        "هنوز عکس یا فیلمی انتخاب نشده است."
                );

        l.addView(mediaStatus);

        button(
                l,
                "📷 انتخاب عکس یا فیلم بار",
                v -> {

                    Intent intent =
                            new Intent(
                                    Intent.ACTION_OPEN_DOCUMENT
                            );

                    intent.addCategory(
                            Intent.CATEGORY_OPENABLE
                    );

                    intent.setType("*/*");

                    intent.putExtra(
                            Intent.EXTRA_MIME_TYPES,
                            new String[]{
                                    "image/*",
                                    "video/*"
                            }
                    );

                    startActivityForResult(
                            intent,
                            1001
                    );
                }
        );

        button(
                l,
                "ثبت سفارش",
                v -> {

                    String n =
                            name.getText()
                                    .toString()
                                    .trim();

                    String p =
                            phone.getText()
                                    .toString()
                                    .trim();

                    String pr =
                            provinceSpinner
                                    .getSelectedItem()
                                    .toString();

                    String c =
                            citySpinner
                                    .getSelectedItem()
                                    .toString();

                    String a =
                            address.getText()
                                    .toString()
                                    .trim();

                    String g =
                            grape.getText()
                                    .toString()
                                    .trim();

                    double weight =
                            number(
                                    orderKgView
                                            .getText()
                                            .toString()
                            );

                    double dep =
                            number(
                                    deposit.getText()
                                            .toString()
                            );

                    String desc =
                            description.getText()
                                    .toString()
                                    .trim();

                    double daily =
                            getDailyPrice();

                    if (n.isEmpty()) {

                        toast(
                                "نام و نام خانوادگی را وارد کنید"
                        );

                        return;
                    }

                    if (p.isEmpty()) {

                        toast(
                                "شماره موبایل را وارد کنید"
                        );

                        return;
                    }

                    if (weight <= 0) {

                        toast(
                                "وزن سفارش را وارد کنید"
                        );

                        return;
                    }

                    if (daily <= 0) {

                        toast(
                                "ابتدا قیمت روز را ثبت کنید"
                        );

                        return;
                    }

                    if (dep < 0 ||
                            dep > weight * daily) {

                        toast(
                                "مبلغ بیعانه صحیح نیست"
                        );

                        return;
                    }

                    double total =
                            weight * daily;

                    double balance =
                            total - dep;

                    int customerNo =
                            createCustomerIfNeeded(
                                    n,
                                    p
                            );

                    ContentValues x =
                            new ContentValues();

                    x.put(
                            "customer_no",
                            customerNo
                    );

                    x.put(
                            "full_name",
                            n
                    );

                    x.put(
                            "phone",
                            p
                    );

                    x.put(
                            "province",
                            pr
                    );

                    x.put(
                            "city",
                            c
                    );

                    x.put(
                            "address",
                            a
                    );

                    x.put(
                            "grape_type",
                            g
                    );

                    x.put(
                            "kg",
                            weight
                    );

                    x.put(
                            "daily_price",
                            daily
                    );

                    x.put(
                            "total_amount",
                            total
                    );

                    x.put(
                            "deposit",
                            dep
                    );

                    x.put(
                            "balance",
                            balance
                    );

                    x.put(
                            "description",
                            desc
                    );

                    x.put(
                            "media_uri",
                            selectedMediaUri
                    );

                    x.put(
                            "delivered",
                            0
                    );

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
                }
        );

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    // =========================================================
    // انتخاب استان
    // =========================================================

    private Spinner createProvinceSpinner(
            LinearLayout parent) {

        TextView label =
                title("انتخاب استان", 20);

        parent.addView(label);

        Spinner spinner =
                new Spinner(this);

        List<String> provinces =
                new ArrayList<>(
                        provinceCities.keySet()
                );

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        provinces
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(60)
                );

        p.setMargins(
                0,
                dp(5),
                0,
                dp(10)
        );

        parent.addView(
                spinner,
                p
        );

        return spinner;
    }

    // =========================================================
    // انتخاب شهر بر اساس استان
    // =========================================================

    private Spinner createCitySpinner(
            LinearLayout parent) {

        TextView label =
                title("انتخاب شهر", 20);

        parent.addView(label);

        Spinner spinner =
                new Spinner(this);

        updateCities(
                spinner,
                provinceSpinner
                        .getSelectedItem()
                        .toString()
        );

        provinceSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String province =
                                parent
                                        .getItemAtPosition(
                                                position
                                        )
                                        .toString();

                        updateCities(
                                spinner,
                                province
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                }
        );

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(60)
                );

        p.setMargins(
                0,
                dp(5),
                0,
                dp(10)
        );

        parent.addView(
                spinner,
                p
        );

        return spinner;
    }

    private void updateCities(
            Spinner spinner,
            String province) {

        String[] cities =
                provinceCities.get(province);

        if (cities == null ||
                cities.length == 0) {

            cities =
                    new String[]{
                            "سایر"
                    };
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

        spinner.setAdapter(adapter);
    }

    // =========================================================
    // تغییر وزن
    // =========================================================

    private void changeWeight(
            double amount) {

        if (orderKgView == null) {
            return;
        }

        double current =
                number(
                        orderKgView
                                .getText()
                                .toString()
                );

        current += amount;

        if (current < 0) {
            current = 0;
        }

        orderKgView.setText(
                money(current)
        );

        orderKgView.setSelection(
                orderKgView.length()
        );
    }

    private Button smallButton(
            String text) {

        Button b =
                new Button(this);

        b.setText(text);
        b.setTextSize(20);
        b.setTextColor(GOLD);

        GradientDrawable g =
                new GradientDrawable();

        g.setColor(GREEN);
        g.setStroke(dp(2), GOLD);
        g.setCornerRadius(dp(15));

        b.setBackground(g);

        return b;
    }

    // =========================================================
    // دریافت رسانه
    // =========================================================

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == 1001 &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getData() != null) {

            Uri uri =
                    data.getData();

            selectedMediaUri =
                    uri.toString();

            try {

                getContentResolver()
                        .takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );

            } catch (Exception ignored) {
            }

            toast(
                    "عکس یا فیلم انتخاب شد"
            );
        }
    }

    private void openMedia(
            String mediaUri) {

        if (mediaUri == null ||
                mediaUri.trim().isEmpty()) {

            toast(
                    "برای این سفارش عکس یا فیلمی ثبت نشده است."
            );

            return;
        }

        try {

            Uri uri =
                    Uri.parse(mediaUri);

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW
                    );

            intent.setData(uri);

            intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            startActivity(intent);

        } catch (Exception e) {

            toast(
                    "امکان بازکردن رسانه وجود ندارد."
            );
        }
    }

    // =========================================================
    // بروزرسانی قیمت
    // =========================================================

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

    // =========================================================
    // ساخت مشتری
    // =========================================================

    private int createCustomerIfNeeded(
            String name,
            String phone) {

        SQLiteDatabase d =
                db.getWritableDatabase();

        Cursor c =
                d.rawQuery(
                        "SELECT id " +
                        "FROM customers " +
                        "WHERE phone=? " +
                        "LIMIT 1",
                        new String[]{
                                phone
                        }
                );

        if (c.moveToFirst()) {

            int id =
                    c.getInt(0);

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

    // =========================================================
    // لیست سفارش‌ها
    // =========================================================

    private void orderList() {

        LinearLayout l = layout();

        l.addView(
                title("سفارش‌های ZERIVA", 30)
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,customer_no," +
                                "full_name,phone,province," +
                                "city,address,grape_type," +
                                "kg,daily_price,total_amount," +
                                "deposit,balance,description," +
                                "delivered,media_uri " +
                                "FROM orders " +
                                "ORDER BY city ASC,id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            l.addView(
                    text(
                            "هنوز سفارشی ثبت نشده است."
                    )
            );
        }

        String lastCity = "";

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

            String mediaUri =
                    c.getString(15);

            String cityName =
                    city == null ||
                    city.trim().isEmpty()
                            ? "بدون شهر"
                            : city;

            if (!lastCity.equals(cityName)) {

                l.addView(
                        cityHeader(
                                "🏙️ " + cityName
                        )
                );

                lastCity = cityName;
            }

            l.addView(
                    card(
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
                    )
            );

            if (mediaUri != null &&
                    !mediaUri.trim().isEmpty()) {

                button(
                        l,
                        "📷 مشاهده عکس / فیلم بار",
                        v -> openMedia(mediaUri)
                );
            }

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

    private void markDelivered(
            int orderId) {

        ContentValues x =
                new ContentValues();

        x.put(
                "delivered",
                1
        );

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

    // =========================================================
    // گزارش سفارش هر شهر
    // =========================================================

    private void cityOrderReport() {

        LinearLayout l = layout();

        l.addView(
                title(
                        "📍 سفارش‌های هر شهر",
                        30
                )
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT " +
                                "COALESCE(" +
                                "NULLIF(TRIM(city),'')," +
                                "'بدون شهر'" +
                                ") AS city_name," +

                                "COALESCE(SUM(kg),0) " +
                                "AS total_kg," +

                                "COUNT(*) AS order_count " +

                                "FROM orders " +

                                "GROUP BY city_name " +

                                "ORDER BY city_name ASC",
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

    // =========================================================
    // حساب‌ها
    // =========================================================

    private void accounts() {

        LinearLayout l = layout();

        l.addView(
                title(
                        "حساب‌ها و معاملات",
                        30
                )
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

        l.addView(
                title(
                        "ثبت معامله",
                        30
                )
        );

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

        button(
                l,
                "ذخیره معامله",
                v -> {

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
                }
        );

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
                title(
                        "دفتر معاملات",
                        30
                )
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

            l.addView(
                    card(
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
                    )
            );
        }

        c.close();

        button(
                l,
                "بازگشت",
                v -> accounts()
        );

        setPage(l);
    }

    // =========================================================
    // خرید
    // =========================================================

    private void purchase() {

        LinearLayout l = layout();

        l.addView(
                title(
                        "خرید از باغدار",
                        30
                )
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

        button(
                l,
                "ذخیره خرید",
                v -> {

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

                    x.put(
                            "farmer",
                            f
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
                                    "purchases",
                                    null,
                                    x
                            );

                    toast(
                            "خرید ذخیره شد"
                    );

                    showHome();
                }
        );

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    // =========================================================
    // فروش
    // =========================================================

    private void sale() {

        LinearLayout l = layout();

        l.addView(
                title(
                        "فروش و ارسال",
                        30
                )
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

        button(
                l,
                "ذخیره فروش",
                v -> {

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
                }
        );

        button(
                l,
                "بازگشت",
                v -> showHome()
        );

        setPage(l);
    }

    // =========================================================
    // گزارش‌ها
    // =========================================================

    private void reports() {

        LinearLayout l = layout();

        l.addView(
                title(
                        "گزارش‌های ZERIVA",
                        30
                )
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

        l.addView(
                card(
                        "📦 خرید\n" +
                        "مقدار: " +
                        money(purchasedKg) +
                        " کیلو\n" +
                        "مبلغ: " +
                        money(purchases) +
                        " تومان"
                )
        );

        l.addView(
                card(
                        "🚚 فروش\n" +
                        "مقدار: " +
                        money(soldKg) +
                        " کیلو\n" +
                        "مبلغ: " +
                        money(sales) +
                        " تومان"
                )
        );

        l.addView(
                card(
                        "🛒 سفارش‌ها\n" +
                        "بیعانه دریافت‌شده: " +
                        money(deposits) +
                        " تومان\n" +
                        "مانده سفارش‌ها: " +
                        money(orderBalance) +
                        " تومان"
                )
        );

        l.addView(
                card(
                        "💰 فروش منهای خرید\n" +
                        money(sales - purchases) +
                        " تومان"
                )
        );

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

    // =========================================================
    // پشتیبانی
    // =========================================================

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

        phone1.setOnClickListener(
                v -> {

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
                }
        );

        box.addView(phone1);

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

    // =========================================================
    // ظاهر
    // =========================================================

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

        t.setGravity(
                Gravity.CENTER
        );

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

        t.setGravity(
                Gravity.CENTER
        );

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
        g.setStroke(
                dp(1),
                GOLD
        );

        g.setCornerRadius(
                dp(15)
        );

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
        g.setStroke(
                dp(1),
                GOLD
        );

        g.setCornerRadius(
                dp(15)
        );

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

        b.setGravity(
                Gravity.CENTER
        );

        GradientDrawable g =
                new GradientDrawable();

        g.setColor(GREEN);

        g.setStroke(
                dp(2),
                GOLD
        );

        g.setCornerRadius(
                dp(18)
        );

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

        parent.addView(
                b,
                p
        );
    }

    // =========================================================
    // ابزارها
    // =========================================================

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

            result =
                    c.getDouble(0);
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

    // =========================================================
    // لوگوی ZERIVA
    // =========================================================

    private class ZerivaLogoView
            extends View {

        private final Paint paint =
                new Paint(
                        Paint.ANTI_ALIAS_FLAG
                );

        ZerivaLogoView(
                Context context) {

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

            Path leaf =
                    new Path();

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

            canvas.drawPath(
                    leaf,
                    paint
            );

            Path leaf2 =
                    new Path();

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

            float r =
                    dp(8);

            float gap =
                    dp(15);

            for (
                    int row = 0;
                    row < 6;
                    row++
            ) {

                int count =
                        row + 1;

                float startX =
                        cx -
                        ((count - 1) * gap)
                        / 2f;

                for (
                        int col = 0;
                        col < count;
                        col++
                ) {

                    float x =
                            startX +
                            col * gap;

                    float y =
                            cy -
                            dp(20) +
                            row * dp(12);

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

                    paint.setColor(GOLD);

                    canvas.drawCircle(
                            x,
                            y,
                            r,
                            paint
                    );
                }
            }

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

    // =========================================================
    // دیتابیس
    // =========================================================

    private class DB
            extends SQLiteOpenHelper {

        DB() {

            super(
                    MainActivity.this,
                    "ZERIVA.db",
                    null,
                    4
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
                    "delivered INTEGER DEFAULT 0," +
                    "media_uri TEXT DEFAULT ''" +
                    ")"
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
                }
            }

            if (oldVersion < 4) {

                try {

                    d.execSQL(
                            "ALTER TABLE orders " +
                            "ADD COLUMN media_uri " +
                            "TEXT DEFAULT ''"
                    );

                } catch (Exception ignored) {
                }
            }
        }
    }
}
