package com.zeriva.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends Activity {

    // =========================================================
    // ZERIVA
    // =========================================================

    private static final int DARK_GREEN = Color.rgb(8, 55, 38);
    private static final int GREEN = Color.rgb(18, 91, 62);
    private static final int GOLD = Color.rgb(212, 175, 55);
    private static final int LIGHT_GOLD = Color.rgb(244, 220, 130);
    private static final int WHITE = Color.WHITE;
    private static final int LIGHT = Color.rgb(245, 245, 240);
    private static final int RED = Color.rgb(180, 55, 55);
    private static final int GRAY = Color.rgb(110, 110, 110);

    private static final String PHONE = "0912";
    private static final String INSTAGRAM = "@zeriva_grapes";

    private LinearLayout root;
    private DB db;

    private Uri selectedStoryUri = null;
    private Uri selectedGalleryUri = null;
    private Uri selectedSatisfactionUri = null;
    private Uri selectedVoiceUri = null;

    private boolean isHome = true;

    private final HashMap<String, String[]> provinces = new HashMap<>();

    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DB(this);

        loadCities();

        showHome();
    }

    // =========================================================
    // ANDROID BACK BUTTON
    // =========================================================

    @Override
    public void onBackPressed() {

        if (!isHome) {
            showHome();
        } else {
            super.onBackPressed();
        }
    }

    // =========================================================
    // PROVINCES / CITIES
    // =========================================================

    private void loadCities() {

        provinces.clear();

        provinces.put("آذربایجان شرقی",
                new String[]{
                        "تبریز",
                        "مراغه",
                        "مرند",
                        "اهر",
                        "میانه",
                        "شبستر",
                        "بناب",
                        "سراب",
                        "هشترود"
                });

        provinces.put("آذربایجان غربی",
                new String[]{
                        "ارومیه",
                        "خوی",
                        "بوکان",
                        "مهاباد",
                        "میاندوآب",
                        "نقده",
                        "سلماس",
                        "پیرانشهر",
                        "سردشت"
                });

        provinces.put("اردبیل",
                new String[]{
                        "اردبیل",
                        "مشگین‌شهر",
                        "خلخال",
                        "پارس‌آباد",
                        "نمین",
                        "گرمی"
                });

        provinces.put("اصفهان",
                new String[]{
                        "اصفهان",
                        "کاشان",
                        "خمینی‌شهر",
                        "نجف‌آباد",
                        "شاهین‌شهر",
                        "فلاورجان",
                        "مبارکه"
                });

        provinces.put("البرز",
                new String[]{
                        "کرج",
                        "نظرآباد",
                        "ساوجبلاغ",
                        "طالقان",
                        "اشتهارد"
                });

        provinces.put("ایلام",
                new String[]{
                        "ایلام",
                        "دهلران",
                        "مهران",
                        "آبدانان",
                        "دره‌شهر",
                        "ایوان"
                });

        provinces.put("بوشهر",
                new String[]{
                        "بوشهر",
                        "برازجان",
                        "گناوه",
                        "دیر",
                        "کنگان",
                        "جم"
                });

        provinces.put("تهران",
                new String[]{
                        "تهران",
                        "ری",
                        "شهریار",
                        "اسلامشهر",
                        "قدس",
                        "ملارد",
                        "پاکدشت",
                        "ورامین",
                        "دماوند"
                });

        provinces.put("چهارمحال و بختیاری",
                new String[]{
                        "شهرکرد",
                        "بروجن",
                        "فارسان",
                        "لردگان",
                        "اردل"
                });

        provinces.put("خراسان جنوبی",
                new String[]{
                        "بیرجند",
                        "قائن",
                        "فردوس",
                        "طبس",
                        "نهبندان"
                });

        provinces.put("خراسان رضوی",
                new String[]{
                        "مشهد",
                        "نیشابور",
                        "سبزوار",
                        "تربت حیدریه",
                        "تربت جام",
                        "قوچان",
                        "کاشمر",
                        "گناباد"
                });

        provinces.put("خراسان شمالی",
                new String[]{
                        "بجنورد",
                        "شیروان",
                        "اسفراین",
                        "جاجرم",
                        "فاروج"
                });

        provinces.put("خوزستان",
                new String[]{
                        "اهواز",
                        "آبادان",
                        "خرمشهر",
                        "دزفول",
                        "اندیمشک",
                        "شوش",
                        "بهبهان",
                        "ماهشهر"
                });

        provinces.put("زنجان",
                new String[]{
                        "زنجان",
                        "ابهر",
                        "خرمدره",
                        "قیدار",
                        "طارم"
                });

        provinces.put("سمنان",
                new String[]{
                        "سمنان",
                        "شاهرود",
                        "دامغان",
                        "گرمسار"
                });

        provinces.put("سیستان و بلوچستان",
                new String[]{
                        "زاهدان",
                        "چابهار",
                        "زابل",
                        "ایرانشهر",
                        "سراوان",
                        "خاش"
                });

        provinces.put("فارس",
                new String[]{
                        "شیراز",
                        "مرودشت",
                        "جهرم",
                        "فسا",
                        "لار",
                        "کازرون",
                        "آباده"
                });

        provinces.put("قزوین",
                new String[]{
                        "قزوین",
                        "تاکستان",
                        "آبیک",
                        "الوند"
                });

        provinces.put("قم",
                new String[]{
                        "قم"
                });

        provinces.put("کردستان",
                new String[]{
                        "سنندج",
                        "مریوان",
                        "سقز",
                        "بانه",
                        "کامیاران",
                        "بیجار",
                        "قروه",
                        "دیواندره"
                });

        provinces.put("کرمان",
                new String[]{
                        "کرمان",
                        "رفسنجان",
                        "سیرجان",
                        "جیرفت",
                        "بم",
                        "زرند"
                });

        provinces.put("کرمانشاه",
                new String[]{
                        "کرمانشاه",
                        "اسلام‌آباد غرب",
                        "جوانرود",
                        "پاوه",
                        "کنگاور",
                        "سنقر",
                        "هرسین"
                });

        provinces.put("کهگیلویه و بویراحمد",
                new String[]{
                        "یاسوج",
                        "دهدشت",
                        "گچساران",
                        "لیکک"
                });

        provinces.put("گلستان",
                new String[]{
                        "گرگان",
                        "گنبد کاووس",
                        "علی‌آباد کتول",
                        "بندر ترکمن",
                        "آق‌قلا"
                });

        provinces.put("گیلان",
                new String[]{
                        "رشت",
                        "لاهیجان",
                        "انزلی",
                        "رودسر",
                        "آستارا",
                        "تالش"
                });

        provinces.put("لرستان",
                new String[]{
                        "خرم‌آباد",
                        "بروجرد",
                        "دورود",
                        "الیگودرز",
                        "کوهدشت",
                        "پلدختر"
                });

        provinces.put("مازندران",
                new String[]{
                        "ساری",
                        "بابل",
                        "آمل",
                        "قائم‌شهر",
                        "نوشهر",
                        "چالوس",
                        "تنکابن"
                });

        provinces.put("مرکزی",
                new String[]{
                        "اراک",
                        "ساوه",
                        "خمین",
                        "محلات",
                        "دلیجان"
                });

        provinces.put("هرمزگان",
                new String[]{
                        "بندرعباس",
                        "قشم",
                        "کیش",
                        "میناب",
                        "بندر لنگه",
                        "رودان"
                });

        provinces.put("همدان",
                new String[]{
                        "همدان",
                        "ملایر",
                        "نهاوند",
                        "تویسرکان",
                        "کبودرآهنگ"
                });

        provinces.put("یزد",
                new String[]{
                        "یزد",
                        "میبد",
                        "اردکان",
                        "بافق",
                        "مهریز"
                });
        }
        // =========================================================
    // ADD STORY
    // =========================================================

    private void showAddStory() {

        selectedStoryUri = null;

        LinearLayout content =
                page("افزودن استوری");

        TextView info = text(
                "یک عکس یا ویدیو برای استوری انتخاب کنید.",
                15,
                WHITE,
                Typeface.NORMAL
        );

        content.addView(info);

        space(content, 12);

        Button choose = addButton(
                "🖼️ انتخاب عکس یا ویدیو",
                v -> chooseStoryMedia()
        );

        content.addView(choose);

        space(content, 10);

        Button save = addButton(
                "💾 ذخیره استوری",
                v -> saveStory()
        );

        content.addView(save);
    }

    // =========================================================
    // CHOOSE STORY MEDIA
    // =========================================================

    private void chooseStoryMedia() {

        Intent intent = new Intent(
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

    // =========================================================
    // SAVE STORY
    // =========================================================

    private void saveStory() {

        if (selectedStoryUri == null) {

            Toast.makeText(
                    this,
                    "ابتدا عکس یا ویدیو را انتخاب کنید.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String uri =
                selectedStoryUri.toString();

        String type =
                getContentResolver()
                        .getType(selectedStoryUri);

        if (type == null) {
            type = "unknown";
        }

        ContentValues values =
                new ContentValues();

        values.put("uri", uri);
        values.put("type", type);
        values.put(
                "created_at",
                System.currentTimeMillis()
        );
        values.put("archived", 0);

        long id =
                db.getWritableDatabase()
                        .insert(
                                "stories",
                                null,
                                values
                        );

        if (id == -1) {

            Toast.makeText(
                    this,
                    "ذخیره استوری انجام نشد.",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            persistUri(selectedStoryUri);

            Toast.makeText(
                    this,
                    "استوری با موفقیت ذخیره شد.",
                    Toast.LENGTH_SHORT
            ).show();

            showHome();
        }
    }

    // =========================================================
    // STORY ARCHIVE
    // =========================================================

    private void showStoryArchive() {

        LinearLayout content =
                page("آرشیو استوری‌ها");

        SQLiteDatabase database =
                db.getReadableDatabase();

        Cursor c = database.rawQuery(
                "SELECT id,uri,type,created_at " +
                "FROM stories " +
                "WHERE archived=1 " +
                "ORDER BY id DESC",
                null
        );

        if (c.getCount() == 0) {

            content.addView(
                    text(
                            "هنوز استوری آرشیو شده‌ای وجود ندارد.",
                            15,
                            WHITE,
                            Typeface.NORMAL
                    )
            );
        }

        while (c.moveToNext()) {

            final String uri =
                    c.getString(1);

            String type =
                    c.getString(2);

            long created =
                    c.getLong(3);

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(10),
                    dp(12),
                    dp(10)
            );

            TextView title =
                    text(
                            "📖 استوری آرشیو شده\n" +
                            "تاریخ: " +
                            formatDateTime(created),
                            14,
                            WHITE,
                            Typeface.NORMAL
                    );

            card.addView(title);

            Button view =
                    addButton(
                            "مشاهده",
                            v -> openMedia(uri)
                    );

            card.addView(view);

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 8);
        }

        c.close();
    }

    // =========================================================
    // END PART 1
    // =========================================================


    // =========================================================
    // CUSTOMERS
    // =========================================================

    private void showCustomers() {

        LinearLayout content =
                page("مشتریان ZERIVA");

        Button add =
                addButton(
                        "➕ افزودن مشتری جدید",
                        v -> addCustomer()
                );

        content.addView(add);

        space(content, 12);

        SQLiteDatabase database =
                db.getReadableDatabase();

        Cursor c = database.rawQuery(
                "SELECT id,name,phone " +
                "FROM customers " +
                "ORDER BY id DESC",
                null
        );

        if (c.getCount() == 0) {

            content.addView(
                    text(
                            "هنوز مشتری ثبت نشده است.",
                            15,
                            WHITE,
                            Typeface.NORMAL
                    )
            );
        }

        while (c.moveToNext()) {

            int id = c.getInt(0);

            String name =
                    safe(c.getString(1));

            String phone =
                    safe(c.getString(2));

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(10),
                    dp(12),
                    dp(10)
            );

            TextView info =
                    text(
                            "👤 " + name +
                            "\n📞 " + phone +
                            "\n🔢 شماره مشتری: " + id,
                            15,
                            WHITE,
                            Typeface.NORMAL
                    );

            card.addView(info);

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 8);
        }

        c.close();
    }

    // =========================================================
    // ADD CUSTOMER
    // =========================================================

    private void addCustomer() {

        LinearLayout content =
                page("افزودن مشتری");

        EditText name =
                input("نام مشتری");

        EditText phone =
                input("شماره تماس");

        content.addView(
                label("نام مشتری")
        );

        content.addView(name);

        content.addView(
                label("شماره تماس")
        );

        content.addView(phone);

        space(content, 12);

        Button save =
                addButton(
                        "💾 ذخیره مشتری",
                        v -> {

                            String n =
                                    name.getText()
                                            .toString()
                                            .trim();

                            String p =
                                    phone.getText()
                                            .toString()
                                            .trim();

                            if (n.isEmpty()) {

                                Toast.makeText(
                                        this,
                                        "نام مشتری را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "name",
                                    n
                            );

                            values.put(
                                    "phone",
                                    p
                            );

                            long id =
                                    db.getWritableDatabase()
                                            .insert(
                                                    "customers",
                                                    null,
                                                    values
                                            );

                            if (id == -1) {

                                Toast.makeText(
                                        this,
                                        "ذخیره مشتری انجام نشد.",
                                        Toast.LENGTH_SHORT
                                ).show();

                            } else {

                                Toast.makeText(
                                        this,
                                        "مشتری با موفقیت ذخیره شد.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                showCustomers();
                            }
                        }
                );

        content.addView(save);
    }

    // =========================================================
    // FARMER PURCHASE
    // =========================================================

    private void showBuyFromFarmer() {

        LinearLayout content =
                page("خرید از باغدار");

        TextView title =
                text(
                        "👨‍🌾 ثبت خرید جدید از باغدار",
                        20,
                        GOLD,
                        Typeface.BOLD
                );

        content.addView(title);

        space(content, 10);

        // -----------------------------------------------------
        // FARMER NAME
        // -----------------------------------------------------

        content.addView(
                label("نام باغدار")
        );

        EditText farmer =
                input("مثلاً: احمد محمدی");

        content.addView(farmer);

        // -----------------------------------------------------
        // BOX COUNT
        // -----------------------------------------------------

        content.addView(
                label("تعداد جعبه")
        );

        EditText boxes =
                input("مثلاً: 200");

        boxes.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER
        );

        content.addView(boxes);

        // -----------------------------------------------------
        // WEIGHT
        // -----------------------------------------------------

        content.addView(
                label("وزن کل (کیلوگرم)")
        );

        EditText weight =
                input("مثلاً: 3000");

        weight.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        content.addView(weight);

        // -----------------------------------------------------
        // PRICE
        // -----------------------------------------------------

        content.addView(
                label("قیمت خرید هر کیلو (تومان)")
        );

        EditText price =
                input("مثلاً: 70000");

        price.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        content.addView(price);

        // -----------------------------------------------------
        // TOTAL
        // -----------------------------------------------------

        content.addView(
                label("مبلغ کل خرید")
        );

        TextView total =
                text(
                        "0 تومان",
                        20,
                        GOLD,
                        Typeface.BOLD
                );

        total.setGravity(Gravity.CENTER);

        GradientDrawable totalBg =
                new GradientDrawable();

        totalBg.setColor(
                Color.rgb(12, 69, 48)
        );

        totalBg.setCornerRadius(
                dp(12)
        );

        totalBg.setStroke(
                dp(1),
                GOLD
        );

        total.setBackground(totalBg);

        total.setPadding(
                dp(10),
                dp(14),
                dp(10),
                dp(14)
        );

        content.addView(
                total,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(60)
                )
        );

        // -----------------------------------------------------
        // DATE
        // -----------------------------------------------------

        content.addView(
                label("تاریخ خرید")
        );

        EditText purchaseDate =
                input("تاریخ شمسی");

        purchaseDate.setText(
                today()
        );

        content.addView(purchaseDate);

        // -----------------------------------------------------
        // DESCRIPTION
        // -----------------------------------------------------

        content.addView(
                label("شرح / توضیحات")
        );

        EditText description =
                input(
                        "مثلاً کیفیت انگور، نوع بار، توضیحات حمل..."
                );

        description.setMinLines(3);

        description.setGravity(
                Gravity.TOP
        );

        content.addView(description);

        // -----------------------------------------------------
        // AUTO CALCULATE
        // -----------------------------------------------------

        android.text.TextWatcher watcher =
                new android.text.TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                        double w =
                                parseDouble(
                                        weight.getText()
                                                .toString()
                                );

                        double p =
                                parseDouble(
                                        price.getText()
                                                .toString()
                                );

                        double amount =
                                w * p;

                        total.setText(
                                money(amount) +
                                " تومان"
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            android.text.Editable s
                    ) {
                    }
                };

        weight.addTextChangedListener(watcher);
        price.addTextChangedListener(watcher);

        space(content, 12);

        // -----------------------------------------------------
        // SAVE PURCHASE
        // -----------------------------------------------------

        Button save =
                addButton(
                        "💾 ثبت خرید باغدار",
                        v -> {

                            String farmerName =
                                    farmer.getText()
                                            .toString()
                                            .trim();

                            double boxCount =
                                    parseDouble(
                                            boxes.getText()
                                                    .toString()
                                    );

                            double totalWeight =
                                    parseDouble(
                                            weight.getText()
                                                    .toString()
                                    );

                            double pricePerKg =
                                    parseDouble(
                                            price.getText()
                                                    .toString()
                                    );

                            String date =
                                    purchaseDate.getText()
                                            .toString()
                                            .trim();

                            String desc =
                                    description.getText()
                                            .toString()
                                            .trim();

                            if (farmerName.isEmpty()) {

                                Toast.makeText(
                                        this,
                                        "نام باغدار را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            if (totalWeight <= 0) {

                                Toast.makeText(
                                        this,
                                        "وزن کل را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            if (pricePerKg <= 0) {

                                Toast.makeText(
                                        this,
                                        "قیمت هر کیلو را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            double amount =
                                    totalWeight *
                                    pricePerKg;

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "farmer",
                                    farmerName
                            );

                            values.put(
                                    "box_count",
                                    boxCount
                            );

                            values.put(
                                    "weight",
                                    totalWeight
                            );

                            values.put(
                                    "price",
                                    pricePerKg
                            );

                            values.put(
                                    "total",
                                    amount
                            );

                            values.put(
                                    "purchase_date",
                                    date
                            );

                            values.put(
                                    "description",
                                    desc
                            );

                            values.put(
                                    "settled",
                                    0
                            );

                            values.put(
                                    "settled_date",
                                    ""
                            );

                            long id =
                                    db.getWritableDatabase()
                                            .insert(
                                                    "purchases",
                                                    null,
                                                    values
                                            );

                            if (id == -1) {

                                Toast.makeText(
                                        this,
                                        "خرید ذخیره نشد.",
                                        Toast.LENGTH_SHORT
                                ).show();

                            } else {

                                Toast.makeText(
                                        this,
                                        "خرید باغدار با موفقیت ثبت شد.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                showBuyFromFarmer();
                            }
                        }
                );

        content.addView(save);

        space(content, 20);

        // -----------------------------------------------------
        // FARMER FILES
        // -----------------------------------------------------

        Button farmerFiles =
                addButton(
                        "👨‍🌾 پرونده باغداران",
                        v -> showFarmerFiles()
                );

        content.addView(farmerFiles);

        space(content, 10);

        // -----------------------------------------------------
        // UNSETTLED PURCHASES
        // -----------------------------------------------------

        TextView unpaidTitle =
                text(
                        "💰 خریدهای تسویه‌نشده",
                        19,
                        GOLD,
                        Typeface.BOLD
                );

        content.addView(unpaidTitle);

        showUnsettledPurchases(content);

        space(content, 15);

        // -----------------------------------------------------
        // SETTLED PURCHASES
        // -----------------------------------------------------

        Button settled =
                addButton(
                        "✅ مشاهده تصفیه‌حساب‌های انجام‌شده",
                        v -> showSettledPurchases()
                );

        content.addView(settled);
    }

    // =========================================================
    // UNSETTLED PURCHASES
    // =========================================================

    private void showUnsettledPurchases(
            LinearLayout parent
    ) {

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,farmer,box_count," +
                                "weight,price,total," +
                                "purchase_date,description " +
                                "FROM purchases " +
                                "WHERE settled=0 " +
                                "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            parent.addView(
                    text(
                            "خرید تسویه‌نشده‌ای وجود ندارد.",
                            14,
                            Color.LTGRAY,
                            Typeface.NORMAL
                    )
            );

            c.close();

            return;
        }

        while (c.moveToNext()) {

            int id =
                    c.getInt(0);

            String farmer =
                    safe(c.getString(1));

            double boxes =
                    c.getDouble(2);

            double weight =
                    c.getDouble(3);

            double price =
                    c.getDouble(4);

            double total =
                    c.getDouble(5);

            String date =
                    safe(c.getString(6));

            String description =
                    safe(c.getString(7));

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
            );

            TextView info =
                    text(
                            "👨‍🌾 باغدار: " + farmer +
                            "\n📦 جعبه: " + formatNumber(boxes) +
                            "\n⚖️ وزن: " + formatNumber(weight) + " کیلو" +
                            "\n💵 قیمت هر کیلو: " + money(price) +
                            "\n💰 مبلغ کل: " + money(total) + " تومان" +
                            "\n📅 تاریخ: " + date +
                            "\n📝 " + description +
                            "\n🔴 وضعیت: تسویه نشده",
                            14,
                            WHITE,
                            Typeface.NORMAL
                    );

            card.addView(info);

            spaceInside(card, 8);

            Button settle =
                    addButton(
                            "💰 تسویه حساب",
                            v -> settlePurchase(id)
                    );

            card.addView(settle);

            parent.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(parent, 8);
        }

        c.close();
    }

    // =========================================================
    // SETTLE PURCHASE
    // =========================================================

    private void settlePurchase(int id) {

        new AlertDialog.Builder(this)
                .setTitle("تسویه حساب باغدار")
                .setMessage(
                        "آیا این خرید تسویه شده است?\n" +
                        "رکورد حذف نمی‌شود و در سوابق باقی می‌ماند."
                )
                .setPositiveButton(
                        "تسویه شد",
                        (dialog, which) -> {

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "settled",
                                    1
                            );

                            values.put(
                                    "settled_date",
                                    today()
                            );

                            int result =
                                    db.getWritableDatabase()
                                                                          .update(
                                                     "purchases",
                                                     values,
                                                     "id=?",
                                                     new String[]{
                                                             String.valueOf(id)
                                                     }
                                             );

                            if (result > 0) {

                                Toast.makeText(
                                        this,
                                        "تسویه حساب ثبت شد.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            showBuyFromFarmer();
                        }
                )
                .setNegativeButton(
                        "لغو",
                        null
                )
                .show();
    }

    // =========================================================
    // SETTLED PURCHASES
    // =========================================================

    private void showSettledPurchases() {

        LinearLayout content =
                page("تصفیه‌حساب‌های انجام‌شده");

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT farmer,box_count," +
                                "weight,price,total," +
                                "purchase_date,settled_date," +
                                "description " +
                                "FROM purchases " +
                                "WHERE settled=1 " +
                                "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            content.addView(
                    text(
                            "هنوز تسویه‌حساب انجام‌شده‌ای وجود ندارد.",
                            15,
                            WHITE,
                            Typeface.NORMAL
                    )
            );
        }

        while (c.moveToNext()) {

            String farmer =
                    safe(c.getString(0));

            double boxes =
                    c.getDouble(1);

            double weight =
                    c.getDouble(2);

            double price =
                    c.getDouble(3);

            double total =
                    c.getDouble(4);

            String purchaseDate =
                    safe(c.getString(5));

            String settledDate =
                    safe(c.getString(6));

            String description =
                    safe(c.getString(7));

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
            );

            card.addView(
                    text(
                            "👨‍🌾 باغدار: " + farmer +
                            "\n📦 جعبه: " + formatNumber(boxes) +
                            "\n⚖️ وزن: " + formatNumber(weight) + " کیلو" +
                            "\n💵 قیمت: " + money(price) + " تومان" +
                            "\n💰 مبلغ: " + money(total) + " تومان" +
                            "\n📅 تاریخ خرید: " + purchaseDate +
                            "\n✅ تاریخ تسویه: " + settledDate +
                            "\n📝 " + description,
                            14,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 8);
        }

        c.close();
    }

    // =========================================================
    // FARMER FILES
    // =========================================================

    private void showFarmerFiles() {

        LinearLayout content =
                page("پرونده باغداران");

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT DISTINCT farmer " +
                                "FROM purchases " +
                                "ORDER BY farmer",
                                null
                        );

        if (c.getCount() == 0) {

            content.addView(
                    text(
                            "هنوز باغداری ثبت نشده است.",
                            15,
                            WHITE,
                            Typeface.NORMAL
                    )
            );
        }

        while (c.moveToNext()) {

            final String farmer =
                    safe(c.getString(0));

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
            );

            TextView name =
                    text(
                            "👨‍🌾 " + farmer,
                            17,
                            WHITE,
                            Typeface.BOLD
                    );

            card.addView(name);

            Button view =
                    addButton(
                            "📋 مشاهده سابقه و حساب",
                            v -> showFarmerHistory(farmer)
                    );

            card.addView(view);

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 8);
        }

        c.close();
    }

    // =========================================================
    // FARMER HISTORY
    // =========================================================

    private void showFarmerHistory(
            String farmer
    ) {

        LinearLayout content =
                page("حساب باغدار: " + farmer);

        Cursor summary =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT " +
                                "COUNT(*), " +
                                "COALESCE(SUM(weight),0), " +
                                "COALESCE(SUM(total),0), " +
                                "COALESCE(SUM(CASE " +
                                "WHEN settled=0 THEN total " +
                                "ELSE 0 END),0), " +
                                "COALESCE(SUM(CASE " +
                                "WHEN settled=1 THEN total " +
                                "ELSE 0 END),0) " +
                                "FROM purchases " +
                                "WHERE farmer=?",
                                new String[]{
                                        farmer
                                }
                        );

        if (summary.moveToFirst()) {

            int count =
                    summary.getInt(0);

            double weight =
                    summary.getDouble(1);

            double total =
                    summary.getDouble(2);

            double unpaid =
                    summary.getDouble(3);

            double settled =
                    summary.getDouble(4);

            content.addView(
                    text(
                            "📊 خلاصه حساب\n" +
                            "تعداد خرید: " + count +
                            "\n⚖️ مجموع وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💰 مجموع خرید: " +
                            money(total) +
                            " تومان" +
                            "\n🔴 بدهی باقی‌مانده: " +
                            money(unpaid) +
                            " تومان" +
                            "\n✅ تسویه‌شده: " +
                            money(settled) +
                            " تومان",
                            16,
                            WHITE,
                            Typeface.NORMAL
                    )
            );
        }

        summary.close();

        space(content, 15);

        Cursor history =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT box_count,weight," +
                                "price,total,purchase_date," +
                                "settled,settled_date," +
                                "description " +
                                "FROM purchases " +
                                "WHERE farmer=? " +
                                "ORDER BY id DESC",
                                new String[]{
                                        farmer
                                }
                        );

        while (history.moveToNext()) {

            double boxes =
                    history.getDouble(0);

            double weight =
                    history.getDouble(1);

            double price =
                    history.getDouble(2);

            double total =
                    history.getDouble(3);

            String purchaseDate =
                    safe(history.getString(4));

            int settled =
                    history.getInt(5);

            String settledDate =
                    safe(history.getString(6));

            String description =
                    safe(history.getString(7));

            String status =
                    settled == 1
                            ? "✅ تسویه شده"
                            : "🔴 تسویه نشده";

            content.addView(
                    text(
                            "📦 جعبه: " +
                            formatNumber(boxes) +
                            "\n⚖️ وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💵 قیمت: " +
                            money(price) +
                            " تومان" +
                            "\n💰 مبلغ: " +
                            money(total) +
                            " تومان" +
                            "\n📅 خرید: " +
                            purchaseDate +
                            "\n" + status +
                            (settled == 1
                                    ? "\n✅ تاریخ تسویه: " +
                                      settledDate
                                    : "") +
                            "\n📝 " +
                            description,
                            14,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            space(content, 10);
        }

        history.close();
    }

    // =========================================================
    // END PART 2
    // =========================================================
    // =========================================================
    // DAILY PRICE
    // =========================================================

    private void showDailyPrice() {

        LinearLayout content =
                page("قیمت روز انگور");

        content.addView(
                text(
                        "🍇 ثبت و مدیریت قیمت روز انگور شانی",
                        19,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 12);

        content.addView(
                label("قیمت هر کیلو (تومان)")
        );

        EditText price =
                input("مثلاً 70000");

        price.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        content.addView(price);

        space(content, 10);

        Button save =
                addButton(
                        "💾 ثبت قیمت امروز",
                        v -> {

                            double value =
                                    parseDouble(
                                            price.getText()
                                                    .toString()
                                    );

                            if (value <= 0) {

                                Toast.makeText(
                                        this,
                                        "قیمت را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "price",
                                    value
                            );

                            values.put(
                                    "price_date",
                                    today()
                            );

                            values.put(
                                    "created_at",
                                    System.currentTimeMillis()
                            );

                            long id =
                                    db.getWritableDatabase()
                                            .insert(
                                                    "daily_prices",
                                                    null,
                                                    values
                                            );

                            if (id != -1) {

                                Toast.makeText(
                                        this,
                                        "قیمت روز ثبت شد.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                showDailyPrice();
                            }
                        }
                );

        content.addView(save);

        space(content, 20);

        content.addView(
                text(
                        "قیمت‌های ثبت‌شده",
                        18,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 8);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT price,price_date " +
                                "FROM daily_prices " +
                                "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            content.addView(
                    text(
                            "هنوز قیمتی ثبت نشده است.",
                            14,
                            Color.LTGRAY,
                            Typeface.NORMAL
                    )
            );
        }

        while (c.moveToNext()) {

            double value =
                    c.getDouble(0);

            String date =
                    safe(c.getString(1));

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
            );

            card.addView(
                    text(
                            "📅 " + date +
                            "\n💵 " + money(value) +
                            " تومان برای هر کیلو",
                            15,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 8);
        }

        c.close();
    }

    // =========================================================
    // CURRENT DAILY PRICE
    // =========================================================

    private double currentDailyPrice() {

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT price " +
                                "FROM daily_prices " +
                                "ORDER BY id DESC " +
                                "LIMIT 1",
                                null
                        );

        double price = 0;

        if (c.moveToFirst()) {
            price = c.getDouble(0);
        }

        c.close();

        return price;
    }

    // =========================================================
    // ORDERS
    // =========================================================

    private void showOrders() {

        LinearLayout content =
                page("سفارش‌های ZERIVA");

        Button add =
                addButton(
                        "➕ ثبت سفارش جدید",
                        v -> addOrder()
                );

        content.addView(add);

        space(content, 15);

        content.addView(
                text(
                        "📦 سفارش‌های قطعی",
                        20,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 8);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,customer,province,city," +
                                "weight,order_date,delivery_date," +
                                "status,final_price,total " +
                                "FROM orders " +
                                "WHERE status!='awaiting_deposit' " +
                                "AND confirmed=1 " +
                                "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            content.addView(
                    text(
                            "هنوز سفارش قطعی ثبت نشده است.",
                            14,
                            Color.LTGRAY,
                            Typeface.NORMAL
                    )
            );
        }

        while (c.moveToNext()) {

            int id =
                    c.getInt(0);

            String customer =
                    safe(c.getString(1));

            String province =
                    safe(c.getString(2));

            String city =
                    safe(c.getString(3));

            double weight =
                    c.getDouble(4);

            String orderDate =
                    safe(c.getString(5));

            String deliveryDate =
                    safe(c.getString(6));

            String status =
                    safe(c.getString(7));

            double finalPrice =
                    c.getDouble(8);

            double total =
                    c.getDouble(9);

            double price =
                    finalPrice > 0
                            ? finalPrice
                            : currentDailyPrice();

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
            );

            String statusText;

            if ("delivered".equals(status)) {

                statusText =
                        "✅ تحویل داده شده";

            } else {

                statusText =
                        "🟡 در انتظار ارسال";
            }

            card.addView(
                    text(
                            "🔢 سفارش شماره: " + id +
                            "\n👤 مشتری: " + customer +
                            "\n📍 استان: " + province +
                            "\n🏙️ شهر: " + city +
                            "\n⚖️ وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💵 قیمت هر کیلو: " +
                            money(price) +
                            " تومان" +
                            "\n💰 مبلغ کل: " +
                            money(total) +
                            " تومان" +
                            "\n📅 تاریخ سفارش: " +
                            orderDate +
                            "\n" +
                            statusText +
                            (deliveryDate.isEmpty()
                                    ? ""
                                    : "\n📅 تاریخ تحویل: " +
                                      deliveryDate),
                            14,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            spaceInside(card, 8);

            Button edit =
                    addButton(
                            "⚖️ تغییر وزن سفارش",
                            v -> editOrderWeight(
                                    id,
                                    weight
                            )
                    );

            card.addView(edit);

            if (!"delivered".equals(status)) {

                Button deliver =
                        addButton(
                                "🚚 ثبت تحویل سفارش",
                                v -> deliverOrder(id)
                        );

                card.addView(deliver);
            }

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 10);
        }

        c.close();

        // -----------------------------------------------------
        // WAITING FOR DEPOSIT
        // -----------------------------------------------------

        space(content, 12);

        content.addView(
                text(
                        "⏳ سفارش‌های در انتظار بیعانه",
                        20,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 8);

        Cursor waiting =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,customer,province,city," +
                                "weight,order_date,total," +
                                "deposit_required,deposit_paid," +
                                "deposit_status " +
                                "FROM orders " +
                                "WHERE status='awaiting_deposit' " +
                                "OR confirmed=0 " +
                                "ORDER BY id DESC",
                                null
                        );

        if (waiting.getCount() == 0) {

            content.addView(
                    text(
                            "سفارشی در انتظار بیعانه وجود ندارد.",
                            14,
                            Color.LTGRAY,
                            Typeface.NORMAL
                    )
            );
        }

        while (waiting.moveToNext()) {

            int id =
                    waiting.getInt(0);

            String customer =
                    safe(waiting.getString(1));

            String province =
                    safe(waiting.getString(2));

            String city =
                    safe(waiting.getString(3));

            double weight =
                    waiting.getDouble(4);

            String date =
                    safe(waiting.getString(5));

            double total =
                    waiting.getDouble(6);

            double depositRequired =
                    waiting.getDouble(7);

            double depositPaid =
                    waiting.getDouble(8);

            String depositStatus =
                    safe(waiting.getString(9));

            LinearLayout card =
                    rounded(
                            Color.rgb(65, 55, 18),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
            );

            card.addView(
                    text(
                            "🔢 سفارش: " + id +
                            "\n👤 مشتری: " + customer +
                            "\n📍 " + province +
                            " - " + city +
                            "\n⚖️ وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💰 مبلغ کل: " +
                            money(total) +
                            " تومان" +
                            "\n💳 بیعانه موردنیاز: " +
                            money(depositRequired) +
                            " تومان" +
                            "\n💵 بیعانه پرداخت‌شده: " +
                            money(depositPaid) +
                            " تومان" +
                            "\n📅 تاریخ: " + date +
                            "\n⏳ وضعیت بیعانه: " +
                            depositStatus,
                            14,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            spaceInside(card, 8);

            Button info =
                    addButton(
                            "💳 وضعیت پرداخت",
                            v -> showDepositPaymentStatus(id)
                    );

            card.addView(info);

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 10);
        }

        waiting.close();
    }

    // =========================================================
    // ADD ORDER
    // =========================================================

    private void addOrder() {

        LinearLayout content =
                page("ثبت سفارش");

        content.addView(
                text(
                        "📦 سفارش جدید",
                        20,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 10);

        // -----------------------------------------------------
        // CUSTOMER
        // -----------------------------------------------------

        content.addView(
                label("نام مشتری")
        );

        EditText customer =
                input("نام مشتری");

        content.addView(customer);

        // -----------------------------------------------------
        // PROVINCE
        // -----------------------------------------------------

        content.addView(
                label("استان")
        );

        String[] provinceList =
                provinces.keySet()
                        .toArray(
                                new String[0]
                        );

        Spinner provinceSpinner =
                new Spinner(this);

        ArrayAdapter<String> provinceAdapter =
                createSpinnerAdapter(
                        provinceList
                );

        provinceSpinner.setAdapter(
                provinceAdapter
        );

        content.addView(
                provinceSpinner,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(55)
                )
        );

        // -----------------------------------------------------
        // CITY
        // -----------------------------------------------------

        content.addView(
                label("شهر")
        );

        Spinner citySpinner =
                new Spinner(this);

        String firstProvince =
                provinceList.length > 0
                        ? provinceList[0]
                        : "";

        String[] firstCities =
                provinces.containsKey(firstProvince)
                        ? provinces.get(firstProvince)
                        : new String[]{};

        citySpinner.setAdapter(
                createSpinnerAdapter(
                        firstCities
                )
        );   

        content.addView(
                citySpinner,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(55)
                )
        );

        provinceSpinner.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        String selected =
                                provinceList[position];

                        String[] cities =
                                provinces.get(
                                        selected
                                );

                        citySpinner.setAdapter(
                                createSpinnerAdapter(
                                        cities
                                )
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent
                    ) {
                    }
                }
        );

        // -----------------------------------------------------
        // WEIGHT
        // -----------------------------------------------------

        content.addView(
                label("وزن سفارش (کیلوگرم)")
        );

        EditText weight =
                input("مثلاً 1000");

        weight.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        content.addView(weight);

        // -----------------------------------------------------
        // PRICE
        // -----------------------------------------------------

        double dailyPrice =
                currentDailyPrice();

        content.addView(
                label("قیمت فعلی هر کیلو")
        );

        TextView price =
                text(
                        money(dailyPrice) +
                        " تومان",
                        18,
                        GOLD,
                        Typeface.BOLD
                );

        content.addView(price);

        // -----------------------------------------------------
        // TOTAL
        // -----------------------------------------------------

        content.addView(
                label("مبلغ کل سفارش")
        );

        TextView total =
                text(
                        "0 تومان",
                        20,
                        GOLD,
                        Typeface.BOLD
                );

        content.addView(total);

        weight.addTextChangedListener(
                new android.text.TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                        double w =
                                parseDouble(
                                        s.toString()
                                );

                        total.setText(
                                money(
                                        w * dailyPrice
                                ) +
                                " تومان"
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            android.text.Editable s
                    ) {
                    }
                }
        );

        space(content, 12);

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        Button save =
                addButton(
                        "📦 ثبت سفارش",
                        v -> {

                            String customerName =
                                    customer.getText()
                                            .toString()
                                            .trim();

                            double orderWeight =
                                    parseDouble(
                                            weight.getText()
                                                    .toString()
                                    );

                            if (customerName.isEmpty()) {

                                Toast.makeText(
                                        this,
                                        "نام مشتری را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            if (orderWeight <= 0) {

                                Toast.makeText(
                                        this,
                                        "وزن سفارش را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            String selectedProvince =
                                    provinceSpinner
                                            .getSelectedItem()
                                            .toString();

                            String selectedCity =
                                    citySpinner
                                            .getSelectedItem()
                                            .toString();

                            double orderTotal =
                                    orderWeight *
                                    dailyPrice;

                            double requiredDeposit =
                                    calculateDeposit(
                                            orderTotal
                                    );

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "customer",
                                    customerName
                            );

                            values.put(
                                    "province",
                                    selectedProvince
                            );

                            values.put(
                                    "city",
                                    selectedCity
                            );

                            values.put(
                                    "weight",
                                    orderWeight
                            );

                            values.put(
                                    "price",
                                    dailyPrice
                            );

                            values.put(
                                    "total",
                                    orderTotal
                            );

                            values.put(
                                    "media_uri",
                                    ""
                            );

                            values.put(
                                    "order_date",
                                    today()
                            );

                            values.put(
                                    "delivery_date",
                                    ""
                            );

                            if (requiredDeposit > 0) {

                                values.put(
                                        "status",
                                        "awaiting_deposit"
                                );

                                values.put(
                                        "confirmed",
                                        0
                                );

                                values.put(
                                        "deposit_required",
                                        requiredDeposit
                                );

                                values.put(
                                        "deposit_paid",
                                        0
                                );

                                values.put(
                                        "deposit_status",
                                        "unpaid"
                                );

                                values.put(
                                        "transaction_id",
                                        ""
                                );

                                values.put(
                                        "payment_date",
                                        ""
                                );

                            } else {

                                values.put(
                                        "status",
                                        "pending"
                                );

                                values.put(
                                        "confirmed",
                                        1
                                );

                                values.put(
                                        "deposit_required",
                                        0
                                );

                                values.put(
                                        "deposit_paid",
                                        0
                                );

                                values.put(
                                        "deposit_status",
                                        "not_required"
                                );

                                values.put(
                                        "transaction_id",
                                        ""
                                );

                                values.put(
                                        "payment_date",
                                        ""
                                );
                            }

                            values.put(
                                    "final_price",
                                    0
                            );

                            long orderId =
                                    db.getWritableDatabase()
                                            .insert(
                                                    "orders",
                                                    null,
                                                    values
                                            );

                            if (orderId == -1) {

                                Toast.makeText(
                                        this,
                                        "ثبت سفارش انجام نشد.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            if (requiredDeposit > 0) {

                                Toast.makeText(
                                        this,
                                        "سفارش ذخیره شد، اما تا پرداخت و تأیید بیعانه قطعی نیست.",
                                        Toast.LENGTH_LONG
                                ).show();

                            } else {

                                Toast.makeText(
                                        this,
                                        "سفارش با موفقیت ثبت شد.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            showOrders();
                        }
                );

        content.addView(save);
    }

    // =========================================================
    // CALCULATE DEPOSIT
    // =========================================================

    private double calculateDeposit(
            double total
    ) {

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT mode,value " +
                                "FROM deposit_settings " +
                                "WHERE id=1",
                                null
                        );

        double result = 0;

        if (c.moveToFirst()) {

            String mode =
                    safe(c.getString(0));

            double value =
                    c.getDouble(1);

            if ("percent".equals(mode)) {

                result =
                        total * value / 100.0;

            } else if ("fixed".equals(mode)) {

                result = value;
            }
        }

        c.close();

        return result;
    }

    // =========================================================
    // DEPOSIT PAYMENT STATUS
    // =========================================================

    private void showDepositPaymentStatus(
            int orderId
    ) {

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT customer,total," +
                                "deposit_required,deposit_paid," +
                                "deposit_status,transaction_id," +
                                "payment_date " +
                                "FROM orders " +
                                "WHERE id=?",
                                new String[]{
                                        String.valueOf(orderId)
                                }
                        );

        if (!c.moveToFirst()) {

            c.close();

            Toast.makeText(
                    this,
                    "سفارش پیدا نشد.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String customer =
                safe(c.getString(0));

        double total =
                c.getDouble(1);

        double required =
                c.getDouble(2);

        double paid =
                c.getDouble(3);

        String status =
                safe(c.getString(4));

        String transaction =
                safe(c.getString(5));

        String paymentDate =
                safe(c.getString(6));

        c.close();

        new AlertDialog.Builder(this)
                .setTitle(
                        "بیعانه سفارش " +
                        orderId
                )
                .setMessage(
                        "مشتری: " + customer +
                        "\nمبلغ سفارش: " +
                        money(total) +
                        " تومان" +
                        "\nبیعانه موردنیاز: " +
                        money(required) +
                        " تومان" +
                        "\nپرداخت‌شده: " +
                        money(paid) +
                        " تومان" +
                        "\nوضعیت: " +
                        status +
                        "\nشماره تراکنش: " +
                        transaction +
                        "\nتاریخ پرداخت: " +
                        paymentDate +
                        "\n\n⚠️ تأیید نهایی پرداخت آنلاین باید توسط درگاه و سرور انجام شود."
                )
                .setPositiveButton(
                        "باشه",
                        null
                )
                .show();
    }

    // =========================================================
    // EDIT ORDER WEIGHT
    // =========================================================

    private void editOrderWeight(
            int orderId,
            double currentWeight
    ) {

        EditText input =
                input(
                        "وزن جدید"
                );

        input.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        input.setText(
                String.valueOf(
                        currentWeight
                )
        );

        new AlertDialog.Builder(this)
                .setTitle(
                        "تغییر وزن سفارش"
                )
                .setView(input)
                .setPositiveButton(
                        "ذخیره",
                        (dialog, which) -> {

                            double newWeight =
                                    parseDouble(
                                            input.getText()
                                                    .toString()
                                    );

                            if (newWeight <= 0) {

                                Toast.makeText(
                                        this,
                                        "وزن معتبر نیست.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            Cursor c =
                                    db.getReadableDatabase()
                                            .rawQuery(
                                                    "SELECT price " +
                                                    "FROM orders " +
                                                    "WHERE id=?",
                                                    new String[]{
                                                            String.valueOf(
                                                                    orderId
                                                            )
                                                    }
                                            );

                            double price = 0;

                            if (c.moveToFirst()) {
                                price = c.getDouble(0);
                            }

                            c.close();

                            double newTotal =
                                    newWeight * price;

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "weight",
                                    newWeight
                            );

                            values.put(
                                    "total",
                                    newTotal
                            );

                            db.getWritableDatabase()
                                    .update(
                                            "orders",
                                            values,
                                            "id=?",
                                            new String[]{
                                                    String.valueOf(
                                                            orderId
                                                    )
                                            }
                                    );

                            Toast.makeText(
                                    this,
                                    "وزن و مبلغ سفارش به‌روزرسانی شد.",
                                    Toast.LENGTH_SHORT
                            ).show();

                            showOrders();
                        }
                )
                .setNegativeButton(
                        "لغو",
                        null
                )
                .show();
    }

    // =========================================================
    // DELIVER ORDER
    // =========================================================

    private void deliverOrder(
            int orderId
    ) {

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT weight,price " +
                                "FROM orders " +
                                "WHERE id=?",
                                new String[]{
                                        String.valueOf(orderId)
                                }
                        );

        double weight = 0;
        double price = 0;

        if (c.moveToFirst()) {

            weight =
                    c.getDouble(0);

            price =
                    c.getDouble(1);
        }

        c.close();

        double finalTotal =
                weight * price;

        ContentValues values =
                new ContentValues();

        values.put(
                "delivery_date",
                today()
        );

        values.put(
                "status",
                "delivered"
        );

        values.put(
                "final_price",
                price
        );

        values.put(
                "total",
                finalTotal
        );

        int result =
                db.getWritableDatabase()
                        .update(
                                "orders",
                                values,
                                "id=?",
                                new String[]{
                                        String.valueOf(orderId)
                                }
                        );

        if (result > 0) {

            Toast.makeText(
                    this,
                    "تحویل سفارش ثبت شد.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        showOrders();
    }

    // =========================================================
    // END PART 3
    // =========================================================
    // =========================================================
    // REPORTS
    // =========================================================

    private void showReports() {

        LinearLayout content =
                page("گزارش‌ها");

        content.addView(
                text(
                        "📊 گزارش کلی ZERIVA",
                        20,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 12);

        SQLiteDatabase database =
                db.getReadableDatabase();

        // -----------------------------------------------------
        // PURCHASE SUMMARY
        // -----------------------------------------------------

        Cursor purchase =
                database.rawQuery(
                        "SELECT " +
                        "COUNT(*)," +
                        "COALESCE(SUM(weight),0)," +
                        "COALESCE(SUM(total),0)," +
                        "COALESCE(SUM(CASE WHEN settled=0 " +
                        "THEN total ELSE 0 END),0)," +
                        "COALESCE(SUM(CASE WHEN settled=1 " +
                        "THEN total ELSE 0 END),0) " +
                        "FROM purchases",
                        null
                );

        if (purchase.moveToFirst()) {

            int count =
                    purchase.getInt(0);

            double weight =
                    purchase.getDouble(1);

            double total =
                    purchase.getDouble(2);

            double unpaid =
                    purchase.getDouble(3);

            double settled =
                    purchase.getDouble(4);

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            15
                    );

            card.setPadding(
                    dp(14),
                    dp(14),
                    dp(14),
                    dp(14)
            );

            card.addView(
                    text(
                            "👨‍🌾 خرید از باغداران\n\n" +
                            "تعداد خریدها: " +
                            count +
                            "\n⚖️ مجموع وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💰 مجموع مبلغ خرید: " +
                            money(total) +
                            " تومان" +
                            "\n🔴 بدهی باغداران: " +
                            money(unpaid) +
                            " تومان" +
                            "\n✅ تسویه‌شده: " +
                            money(settled) +
                            " تومان",
                            15,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
        }

        purchase.close();

        space(content, 12);

        // -----------------------------------------------------
        // ORDER SUMMARY
        // -----------------------------------------------------

        Cursor orders =
                database.rawQuery(
                        "SELECT " +
                        "COUNT(*)," +
                        "COALESCE(SUM(weight),0)," +
                        "COALESCE(SUM(total),0) " +
                        "FROM orders " +
                        "WHERE confirmed=1",
                        null
                );

        if (orders.moveToFirst()) {

            int count =
                    orders.getInt(0);

            double weight =
                    orders.getDouble(1);

            double total =
                    orders.getDouble(2);

            content.addView(
                    roundedReportCard(
                            "📦 سفارش‌های قطعی\n\n" +
                            "تعداد سفارش: " +
                            count +
                            "\n⚖️ مجموع وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💰 مجموع مبلغ: " +
                            money(total) +
                            " تومان"
                    )
            );
        }

        orders.close();

        space(content, 12);

        // -----------------------------------------------------
        // PENDING / DELIVERED
        // -----------------------------------------------------

        Cursor pending =
                database.rawQuery(
                        "SELECT COUNT(*) " +
                        "FROM orders " +
                        "WHERE confirmed=1 " +
                        "AND status='pending'",
                        null
                );

        int pendingCount = 0;

        if (pending.moveToFirst()) {
            pendingCount =
                    pending.getInt(0);
        }

        pending.close();

        Cursor delivered =
                database.rawQuery(
                        "SELECT COUNT(*) " +
                        "FROM orders " +
                        "WHERE confirmed=1 " +
                        "AND status='delivered'",
                        null
                );

        int deliveredCount = 0;

        if (delivered.moveToFirst()) {
            deliveredCount =
                    delivered.getInt(0);
        }

        delivered.close();

        content.addView(
                roundedReportCard(
                        "🚚 وضعیت ارسال\n\n" +
                        "🟡 در انتظار ارسال: " +
                        pendingCount +
                        "\n✅ تحویل داده‌شده: " +
                        deliveredCount
                )
        );

        space(content, 12);

        // -----------------------------------------------------
        // WAITING DEPOSITS
        // -----------------------------------------------------

        Cursor deposits =
                database.rawQuery(
                        "SELECT " +
                        "COUNT(*)," +
                        "COALESCE(SUM(deposit_required),0) " +
                        "FROM orders " +
                        "WHERE status='awaiting_deposit'",
                        null
                );

        if (deposits.moveToFirst()) {

            int count =
                    deposits.getInt(0);

            double amount =
                    deposits.getDouble(1);

            content.addView(
                    roundedReportCard(
                            "💳 بیعانه‌های در انتظار\n\n" +
                            "تعداد سفارش: " +
                            count +
                            "\n💰 مجموع بیعانه موردنیاز: " +
                            money(amount) +
                            " تومان"
                    )
            );
        }

        deposits.close();

        space(content, 12);

        // -----------------------------------------------------
        // CUSTOMER COUNT
        // -----------------------------------------------------

        Cursor customers =
                database.rawQuery(
                        "SELECT COUNT(*) " +
                        "FROM customers",
                        null
                );

        int customerCount = 0;

        if (customers.moveToFirst()) {
            customerCount =
                    customers.getInt(0);
        }

        customers.close();

        content.addView(
                roundedReportCard(
                        "👥 مشتریان\n\n" +
                        "تعداد مشتریان ثبت‌شده: " +
                        customerCount
                )
        );

        space(content, 15);

        // -----------------------------------------------------
        // CITY BREAKDOWN
        // -----------------------------------------------------

        content.addView(
                text(
                        "🏙️ گزارش سفارش بر اساس شهر",
                        18,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 8);

        Cursor cities =
                database.rawQuery(
                        "SELECT city,COUNT(*)," +
                        "COALESCE(SUM(weight),0)," +
                        "COALESCE(SUM(total),0) " +
                        "FROM orders " +
                        "WHERE confirmed=1 " +
                        "GROUP BY city " +
                        "ORDER BY COUNT(*) DESC",
                        null
                );

        if (cities.getCount() == 0) {

            content.addView(
                    text(
                            "هنوز سفارش قطعی برای گزارش شهرها وجود ندارد.",
                            14,
                            Color.LTGRAY,
                            Typeface.NORMAL
                    )
            );
        }

        while (cities.moveToNext()) {

            String city =
                    safe(cities.getString(0));

            int count =
                    cities.getInt(1);

            double weight =
                    cities.getDouble(2);

            double total =
                    cities.getDouble(3);

            content.addView(
                    roundedReportCard(
                            "🏙️ " + city +
                            "\n📦 تعداد سفارش: " +
                            count +
                            "\n⚖️ وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💰 مبلغ: " +
                            money(total) +
                            " تومان"
                    )
            );

            space(content, 8);
        }

        cities.close();
    }

    // =========================================================
    // REPORT CARD
    // =========================================================

    private LinearLayout roundedReportCard(
            String value
    ) {

        LinearLayout card =
                rounded(
                        Color.rgb(12, 69, 48),
                        GOLD,
                        1,
                        15
                );

        card.setPadding(
                dp(14),
                dp(14),
                dp(14),
                dp(14)
        );

        card.addView(
                text(
                        value,
                        15,
                        WHITE,
                        Typeface.NORMAL
                )
        );

        return card;
    }

    // =========================================================
    // GALLERY
    // =========================================================

    private void showGallery() {

        LinearLayout content =
                page("گالری ZERIVA");

        content.addView(
                text(
                        "🖼️ گالری تصاویر و ویدیوها",
                        20,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 10);

        Button choose =
                addButton(
                        "➕ افزودن عکس یا ویدیو",
                        v -> chooseGalleryMedia()
                );

        content.addView(choose);

        space(content, 15);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,uri,type,title " +
                                "FROM gallery " +
                                "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {
            content.addView(
                    text(
                            "گالری هنوز خالی است.",
                            15,
                            Color.LTGRAY,
                            Typeface.NORMAL
                    )
            );
        }

        while (c.moveToNext()) {

            String uri =
                    safe(c.getString(1));

            String type =
                    safe(c.getString(2));

            String title =
                    safe(c.getString(3));

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(10),
                    dp(10),
                    dp(10),
                    dp(10)
            );

            ImageView image =
                    createMediaPreview(
                            uri,
                            type
                    );

            card.addView(
                    image,
                    new LinearLayout.LayoutParams(
                            -1,
                            dp(210)
                    )
            );

            spaceInside(card, 8);

            card.addView(
                    text(
                            title.isEmpty()
                                    ? "تصویر ZERIVA"
                                    : title,
                            14,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            Button view =
                    addButton(
                            "👁️ مشاهده",
                            v -> openMedia(uri)
                    );

            card.addView(view);

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 10);
        }

        c.close();
    }

    // =========================================================
    // CHOOSE GALLERY MEDIA
    // =========================================================

    private void chooseGalleryMedia() {

        selectedGalleryUri = null;

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
                1002
        );
    }

    // =========================================================
    // SAVE GALLERY
    // =========================================================

    private void saveGalleryMedia() {

        if (selectedGalleryUri == null) {
            return;
        }

        String uri =
                selectedGalleryUri.toString();

        String type =
                getContentResolver()
                        .getType(
                                selectedGalleryUri
                        );

        if (type == null) {
            type = "unknown";
        }

        ContentValues values =
                new ContentValues();

        values.put(
                "uri",
                uri
        );

        values.put(
                "type",
                type
        );

        values.put(
                "title",
                "ZERIVA"
        );

        values.put(
                "created_at",
                System.currentTimeMillis()
        );

        long id =
                db.getWritableDatabase()
                        .insert(
                                "gallery",
                                null,
                                values
                        );

        if (id != -1) {

            persistUri(
                    selectedGalleryUri
            );

            Toast.makeText(
                    this,
                    "فایل به گالری اضافه شد.",
                    Toast.LENGTH_SHORT
            ).show();

            showGallery();
        }
    }

    // =========================================================
    // MEDIA PREVIEW
    // =========================================================

    private ImageView createMediaPreview(
            String uriText,
            String type
    ) {

        ImageView image =
                new ImageView(this);

        image.setScaleType(
                ImageView.ScaleType.CENTER_CROP
        );

        try {

            if (uriText != null &&
                    !uriText.isEmpty()) {

                Uri uri =
                        Uri.parse(uriText);

                InputStream input =
                        getContentResolver()
                                .openInputStream(uri);

                Bitmap bitmap =
                        BitmapFactory
                                .decodeStream(input);

                if (input != null) {
                    input.close();
                }

                if (bitmap != null) {
                    image.setImageBitmap(
                            bitmap
                    );

                } else {
                    image.setImageResource(
                            android.R.drawable.ic_menu_gallery
                    );
                }

            } else {

                image.setImageResource(
                        android.R.drawable.ic_menu_gallery
                );
            }

        } catch (Exception e) {

            image.setImageResource(
                    android.R.drawable.ic_menu_gallery
            );
        }

        return image;
    }

    // =========================================================
    // SATISFACTION
    // =========================================================

    private void showSatisfaction() {

        LinearLayout content =
                page("رضایت مشتری");

        content.addView(
                text(
                        "⭐ ثبت رضایت مشتریان",
                        20,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 10);

        content.addView(
                label("نام مشتری")
        );

        EditText customer =
                input("نام مشتری");

        content.addView(customer);

        content.addView(
                label("شهر")
        );

        EditText city =
                input("شهر مشتری");

        content.addView(city);

        content.addView(
                label("متن رضایت مشتری")
        );

        EditText message =
                input(
                        "متن رضایت یا نظر مشتری..."
                );

        message.setMinLines(4);

        message.setGravity(
                Gravity.TOP
        );

        content.addView(message);

        space(content, 10);

        Button media =
                addButton(
                        "🖼️ افزودن عکس یا ویدیو",
                        v -> chooseSatisfactionMedia()
                );

        content.addView(media);

        Button save =
                addButton(
                        "💾 ذخیره رضایت",
                        v -> {

                            String customerName =
                                    customer.getText()
                                            .toString()
                                            .trim();

                            String customerCity =
                                    city.getText()
                                            .toString()
                                            .trim();

                            String text =
                                    message.getText()
                                            .toString()
                                            .trim();

                            if (customerName.isEmpty() &&
                                    text.isEmpty()) {

                                Toast.makeText(
                                        this,
                                        "اطلاعات رضایت را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            String uri =
                                    selectedSatisfactionUri == null
                                            ? ""
                                            : selectedSatisfactionUri
                                                    .toString();

                            String type =
                                    selectedSatisfactionUri == null
                                            ? ""
                                            : safe(
                                                    getContentResolver()
                                                            .getType(
                                                                    selectedSatisfactionUri
                                                            )
                                              );

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "uri",
                                    uri
                            );

                            values.put(
                                    "type",
                                    type
                            );

                            values.put(
                                    "customer_name",
                                    customerName
                            );

                            values.put(
                                    "city",
                                    customerCity
                            );

                            values.put(
                                    "date_text",
                                    today()
                            );

                            values.put(
                                    "created_at",
                                    System.currentTimeMillis()
                            );

                            long id =
                                    db.getWritableDatabase()
                                            .insert(
                                                    "satisfaction",
                                                    null,
                                                    values
                                            );

                            if (id != -1) {

                                if (
                                        selectedSatisfactionUri
                                        != null
                                ) {

                                    persistUri(
                                            selectedSatisfactionUri
                                    );
                                }

                                Toast.makeText(
                                        this,
                                        "رضایت مشتری ذخیره شد.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                showSatisfaction();
                            }
                        }
                );

        content.addView(save);

        space(content, 20);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT customer_name,city," +
                                "date_text,uri,type " +
                                "FROM satisfaction " +
                                "ORDER BY id DESC",
                                null
                        );

        while (c.moveToNext()) {

            String name =
                    safe(c.getString(0));

            String customerCity =
                    safe(c.getString(1));

            String date =
                    safe(c.getString(2));

            String uri =
                    safe(c.getString(3));

            String type =
                    safe(c.getString(4));

            LinearLayout card =
                    rounded(
                            Color.rgb(12, 69, 48),
                            GOLD,
                            1,
                            14
                    );

            card.setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
            );

            card.addView(
                    text(
                            "⭐ " + name +
                            "\n📍 " + customerCity +
                            "\n📅 " + date,
                            14,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            if (!uri.isEmpty()) {

                Button view =
                        addButton(
                                "👁️ مشاهده رسانه",
                                v -> openMedia(uri)
                        );

                card.addView(view);
            }

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 8);
        }

        c.close();
    }

    // =========================================================
    // CHOOSE SATISFACTION MEDIA
    // =========================================================

    private void chooseSatisfactionMedia() {

        selectedSatisfactionUri =
                null;

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
                1003
        );
    }

    // =========================================================
    // END PART 4
    // =========================================================
    // =========================
    // PART 5
    // مدیریت، فروش، معاملات و تنظیمات
    // =========================

    private void showChat() {

        LinearLayout content = page("ارتباط با مشتری");

        content.addView(
                text(
                        "💬 ارتباط با مشتریان ZERIVA",
                        20,
                        GOLD,
                        Typeface.BOLD
                )
        );

        content.addView(
                text(
                        "برای ارتباط با مشتریان می‌توانید از شماره تماس و صفحه اینستاگرام ZERIVA استفاده کنید.",
                        15,
                        WHITE,
                        Gravity.RIGHT
                )
        );

        space(content, 12);

        content.addView(
                text(
                        "📞 تماس: " + PHONE +
                        "\n📱 اینستاگرام: " + INSTAGRAM,
                        16,
                        WHITE,
                        Gravity.RIGHT
                )
        );
    }

    private void showManagement() {

        page("مدیریت ZERIVA");

        addButton(
                root,
                "تنظیم پیش‌پرداخت سفارش‌ها",
                v -> showDepositSettings()
        );

        addButton(
                root,
                "گزارش سفارش‌ها",
                v -> showOrders()
        );

        addButton(
                root,
                "حساب‌ها و معاملات",
                v -> showTransactions()
        );

        addButton(
                root,
                "فروش و ارسال",
                v -> showSales()
        );

        addButton(
                root,
                "گزارش مالی",
                v -> showReports()
        );

        addButton(
                root,
                "امنیت برنامه",
                v -> showSecuritySettings()
        );

        space(root, 20);

        text(
                root,
                "تنظیمات مدیریتی فقط برای صاحب برنامه در نظر گرفته شده است.",
                14,
                GRAY,
                Gravity.CENTER
        );
    }

    private void showDepositSettings() {

        page("تنظیم پیش‌پرداخت");

        SQLiteDatabase d =
                db.getReadableDatabase();

        Cursor c =
                d.rawQuery(
                        "SELECT type,value FROM deposit_settings WHERE id=1",
                        null
                );

        String type = "percent";
        double value = 0;

        if (c.moveToFirst()) {

            type =
                    safe(
                            c.getString(0),
                            "percent"
                    );

            value =
                    c.getDouble(1);
        }

        c.close();

        TextView info =
                text(
                        root,
                        "این مبلغ برای همه مشتریان اعمال می‌شود.\n" +
                        "سفارش تا زمان پرداخت و تأیید پیش‌پرداخت، قطعی نمی‌شود.",
                        15,
                        GRAY,
                        Gravity.RIGHT
                );

        space(root, 10);

        Spinner typeSpinner =
                new Spinner(this);

        ArrayList<String> types =
                new ArrayList<>();

        types.add("درصدی");
        types.add("مبلغ ثابت");

        typeSpinner.setAdapter(
                createSpinnerAdapter(types)
        );

        if ("fixed".equals(type)) {
            typeSpinner.setSelection(1);
        } else {
            typeSpinner.setSelection(0);
        }

        root.addView(typeSpinner);

        EditText valueInput =
                input(
                        "مقدار پیش‌پرداخت",
                        String.valueOf(value)
                );

        root.addView(valueInput);

        space(root, 10);

        Button save =
                addButton(
                        root,
                        "ذخیره تنظیمات",
                        v -> {

                            double amount =
                                    parseDouble(
                                            valueInput
                                                    .getText()
                                                    .toString()
                                    );

                            if (amount < 0) {

                                Toast.makeText(
                                        this,
                                        "مقدار صحیح وارد کنید",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            String selected =
                                    typeSpinner
                                            .getSelectedItemPosition() == 1
                                            ? "fixed"
                                            : "percent";

                            SQLiteDatabase w =
                                    db.getWritableDatabase();

                            ContentValues cv =
                                    new ContentValues();

                            cv.put(
                                    "id",
                                    1
                            );

                            cv.put(
                                    "type",
                                    selected
                            );

                            cv.put(
                                    "value",
                                    amount
                            );

                            w.insertWithOnConflict(
                                    "deposit_settings",
                                    null,
                                    cv,
                                    SQLiteDatabase.CONFLICT_REPLACE
                            );

                            Toast.makeText(
                                    this,
                                    "تنظیمات پیش‌پرداخت ذخیره شد",
                                    Toast.LENGTH_SHORT
                            ).show();

                            showManagement();
                        }
                );

        space(root, 15);

        text(
                root,
                "نکته: اتصال به درگاه بانکی واقعی و تأیید ضدتقلب تراکنش، در مرحله اتصال درگاه و سرور انجام می‌شود.",
                13,
                RED,
                Gravity.RIGHT
        );
    }

    private void showTransactions() {

        page("حساب‌ها و معاملات");

        addButton(
                root,
                "مشتریان",
                v -> showCustomers()
        );

        addButton(
                root,
                "خرید از باغدار",
                v -> showBuyFromFarmer()
        );

        addButton(
                root,
                "فروش و ارسال",
                v -> showSales()
        );

        addButton(
                root,
                "گزارش مالی",
                v -> showReports()
        );

        space(root, 15);

        SQLiteDatabase d =
                db.getReadableDatabase();

        Cursor c =
                d.rawQuery(
                        "SELECT " +
                        "COUNT(*) AS cnt, " +
                        "COALESCE(SUM(total),0) AS total " +
                        "FROM orders " +
                        "WHERE confirmed=1",
                        null
                );

        if (c.moveToFirst()) {

            LinearLayout card =
                    roundedReportCard(
                            "سفارش‌های قطعی",
                            formatNumber(c.getInt(0)) +
                            " سفارش\n" +
                            money(c.getDouble(1)) +
                            " تومان"
                    );

            root.addView(card);
        }

        c.close();

        Cursor p =
                d.rawQuery(
                        "SELECT " +
                        "COUNT(*) AS cnt, " +
                        "COALESCE(SUM(total),0) AS total " +
                        "FROM purchases " +
                        "WHERE settled=0",
                        null
                );

        if (p.moveToFirst()) {

            LinearLayout card =
                    roundedReportCard(
                            "بدهی به باغداران",
                            formatNumber(p.getInt(0)) +
                            " خرید تسویه‌نشده\n" +
                            money(p.getDouble(1)) +
                            " تومان"
                    );

            root.addView(card);
        }

        p.close();
    }

    private void showSales() {

        page("فروش و ارسال");

        addButton(
                root,
                "ثبت فروش / سفارش جدید",
                v -> addOrder()
        );

        addButton(
                root,
                "سفارش‌های جاری",
                v -> showOrders()
        );

        SQLiteDatabase d =
                db.getReadableDatabase();

        Cursor c =
                d.rawQuery(
                        "SELECT id,customer,province,city,weight,price,total,status,order_no " +
                        "FROM orders " +
                        "WHERE confirmed=1 " +
                        "ORDER BY id DESC",
                        null
                );

        while (c.moveToNext()) {

            final int id =
                    c.getInt(0);

            String customer =
                    safe(
                            c.getString(1),
                            "بدون نام"
                    );

            String province =
                    safe(
                            c.getString(2),
                            ""
                    );

            String city =
                    safe(
                            c.getString(3),
                            ""
                    );

            double weight =
                    c.getDouble(4);

            double price =
                    c.getDouble(5);

            double total =
                    c.getDouble(6);

            String status =
                    safe(
                            c.getString(7),
                            "pending"
                    );

            String orderNo =
                    safe(
                            c.getString(8),
                            String.valueOf(id)
                    );

            LinearLayout card =
                    new LinearLayout(this);

            card.setOrientation(
                    LinearLayout.VERTICAL
            );

            card.setPadding(
                    dp(14),
                    dp(14),
                    dp(14),
                    dp(14)
            );

            card.setBackground(
                    roundedBg(
                            WHITE,
                            GOLD,
                            1,
                            14
                    )
            );

            TextView title =
                    text(
                            card,
                            "سفارش #" +
                            orderNo +
                            " — " +
                            customer,
                            17,
                            DARK_GREEN,
                            Gravity.RIGHT
                    );

            title.setTypeface(
                    Typeface.DEFAULT_BOLD
            );

            text(
                    card,
                    "مقصد: " +
                    province +
                    " / " +
                    city,
                    14,
                    GRAY,
                    Gravity.RIGHT
            );

            text(
                    card,
                    "وزن: " +
                    formatNumber(weight) +
                    " کیلو",
                    14,
                    GRAY,
                    Gravity.RIGHT
            );

            text(
                    card,
                    "قیمت هر کیلو: " +
                    money(price) +
                    " تومان",
                    14,
                    GRAY,
                    Gravity.RIGHT
            );

            text(
                    card,
                    "مبلغ کل: " +
                    money(total) +
                    " تومان",
                    15,
                    DARK_GREEN,
                    Gravity.RIGHT
            );

            String statusText;

            if ("delivered".equals(status)) {

                statusText =
                        "وضعیت: تحویل داده شده";

            } else if ("cancelled".equals(status)) {

                statusText =
                        "وضعیت: لغو شده";

            } else {

                statusText =
                        "وضعیت: در انتظار ارسال";
            }

            text(
                    card,
                    statusText,
                    14,
                    GRAY,
                    Gravity.RIGHT
            );

            space(card, 8);

            LinearLayout buttons =
                    new LinearLayout(this);

            buttons.setOrientation(
                    LinearLayout.HORIZONTAL
            );

            buttons.setGravity(
                    Gravity.CENTER
            );

            Button weightBtn =
                    addButton(
                            buttons,
                            "تغییر وزن",
                            v -> editOrderWeight(id)
                    );

            Button deliverBtn =
                    addButton(
                            buttons,
                            "تحویل شد",
                            v -> deliverOrder(id)
                    );

            Button cancelBtn =
                    addButton(
                            buttons,
                            "لغو سفارش",
                            v -> {

                                new AlertDialog.Builder(this)
                                        .setTitle("لغو سفارش")
                                        .setMessage(
                                                "آیا این سفارش لغو شود؟\n" +
                                                "اطلاعات سفارش حذف نخواهد شد."
                                        )
                                        .setNegativeButton(
                                                "خیر",
                                                null
                                        )
                                        .setPositiveButton(
                                                "بله",
                                                (dialog, which) -> {

                                                    ContentValues cv =
                                                            new ContentValues();

                                                    cv.put(
                                                            "status",
                                                            "cancelled"
                                                    );

                                                    db.getWritableDatabase()
                                                            .update(
                                                                    "orders",
                                                                    cv,
                                                                    "id=?",
                                                                    new String[]{
                                                                            String.valueOf(id)
                                                                    }
                                                            );

                                                    showSales();
                                                }
                                        )
                                        .show();
                            }
                    );

            spaceHorizontal(buttons, 4);

            root.addView(card);
            space(root, 10);
        }

        c.close();
    }

    private void showSecuritySettings() {

        page("امنیت برنامه");

        text(
                root,
                "بخش‌های حساس برنامه مانند حساب‌ها، خرید باغدار و مدیریت، باید فقط در اختیار صاحب برنامه باشند.",
                15,
                GRAY,
                Gravity.RIGHT
        );

        space(root, 15);

        addButton(
                root,
                "تغییر رمز ورود",
                v -> changePassword()
        );

        addButton(
                root,
                "فعال / غیرفعال کردن رمز",
                v -> togglePassword()
        );

        space(root, 15);

        text(
                root,
                "نسخه فعلی از رمز محلی برنامه استفاده می‌کند. اتصال اثرانگشت به لایه امنیتی دستگاه در مرحله بعدی قابل اضافه شدن است.",
                13,
                GRAY,
                Gravity.RIGHT
        );
    }

    private void changePassword() {

        final EditText password = input(
                "رمز جدید",
                ""
        );

        password.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                        android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
        );

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("تغییر رمز")
                .setView(password)
                .setNegativeButton("انصراف", null)
                .setPositiveButton("ذخیره", null)
                .create();

        dialog.setOnShowListener(v -> {

            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener(btn -> {

                        String pass =
                                password.getText().toString().trim();

                        if (pass.length() < 4) {

                            Toast.makeText(
                                    this,
                                    "رمز باید حداقل ۴ رقم باشد",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        getSharedPreferences(
                                "zeriva_security",
                                MODE_PRIVATE
                        )
                                .edit()
                                .putString("password", pass)
                                .putBoolean("enabled", true)
                                .apply();

                        Toast.makeText(
                                this,
                                "رمز با موفقیت ذخیره شد",
                                Toast.LENGTH_SHORT
                        ).show();

                        dialog.dismiss();
                    });
        });

        dialog.show();
    }

    private void togglePassword() {

        android.content.SharedPreferences sp =
                getSharedPreferences(
                        "zeriva_security",
                        MODE_PRIVATE
                );

        boolean enabled =
                sp.getBoolean("enabled", false);

        sp.edit()
                .putBoolean("enabled", !enabled)
                .apply();

        Toast.makeText(
                this,
                !enabled
                        ? "رمز برنامه فعال شد"
                        : "رمز برنامه غیرفعال شد",
                Toast.LENGTH_SHORT
        ).show();
    }

    // =========================
    // پایان قسمت ۵
    // =========================
        // =========================
    // پایان قسمت ۵
    // =========================
    // =========================
    // PART 6
    // توابع کمکی رابط کاربری
    // =========================

    private void page(String title) {
        isHome = false;

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(LIGHT);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(
                dp(14),
                dp(14),
                dp(14),
                dp(30)
        );

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(
                dp(10),
                dp(10),
                dp(10),
                dp(10)
        );
        header.setBackground(roundedBg(DARK_GREEN, DARK_GREEN, 1, 16));

        TextView titleText = text(
                header,
                title,
                20,
                GOLD,
                Gravity.CENTER
        );

        titleText.setTypeface(Typeface.DEFAULT_BOLD);

        header.addView(
                titleText,
                new LinearLayout.LayoutParams(
                        0,
                        dp(55),
                        1
                )
        );

        content.addView(header);

        scroll.addView(content);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        setContentView(root);

        // محتوای صفحه از این قسمت به بعد به root اضافه می‌شود.
    }

    private TextView text(
            String value,
            float size,
            int color,
            int gravity
    ) {
        TextView tv = new TextView(this);
        tv.setText(value);
        tv.setTextSize(size);
        tv.setTextColor(color);
        tv.setGravity(gravity);
        tv.setPadding(
                dp(6),
                dp(6),
                dp(6),
                dp(6)
        );
        return tv;
    }

    private TextView text(
            ViewGroup parent,
            String value,
            float size,
            int color,
            int gravity
    ) {
        TextView tv = new TextView(this);

        tv.setText(value);
        tv.setTextSize(size);
        tv.setTextColor(color);
        tv.setGravity(gravity);
        tv.setPadding(
                dp(6),
                dp(6),
                dp(6),
                dp(6)
        );

        parent.addView(
                tv,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        return tv;
    }

    private EditText input(
            String hint,
            String value
    ) {
        EditText edit = new EditText(this);

        edit.setHint(hint);
        edit.setText(value);
        edit.setTextSize(15);
        edit.setTextColor(DARK_GREEN);
        edit.setHintTextColor(GRAY);
        edit.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        edit.setPadding(
                dp(12),
                dp(8),
                dp(12),
                dp(8)
        );

        edit.setBackground(
                roundedBg(WHITE, GOLD, 1, 12)
        );

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(52)
                );

        lp.setMargins(
                0,
                dp(6),
                0,
                dp(6)
        );

        edit.setLayoutParams(lp);

        return edit;
    }

    private Button addButton(
            ViewGroup parent,
            String title,
            View.OnClickListener listener
    ) {
        Button button = new Button(this);

        button.setText(title);
        button.setTextSize(14);
        button.setTextColor(DARK_GREEN);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setPadding(
                dp(8),
                dp(4),
                dp(8),
                dp(4)
        );

        button.setBackground(
                roundedBg(WHITE, GOLD, 2, 14)
        );

        button.setOnClickListener(listener);

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(54)
                );

        lp.setMargins(
                0,
                dp(5),
                0,
                dp(5)
        );

        parent.addView(button, lp);

        return button;
    }

    private LinearLayout rounded(
            int fillColor,
            int strokeColor,
            int strokeWidth,
            int radius
    ) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackground(roundedBg(
                fillColor,
                strokeColor,
                strokeWidth,
                radius
        ));
        return layout;
    }

    private GradientDrawable roundedBg(
            int fillColor,
            int strokeColor,
            int strokeWidth,
            int radius
    ) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fillColor);
        drawable.setCornerRadius(dp(radius));

        if (strokeWidth > 0) {
            drawable.setStroke(
                    dp(strokeWidth),
                    strokeColor
            );
        }

        return drawable;
    }

    private void space(
            ViewGroup parent,
            int height
    ) {
        Space s = new Space(this);

        parent.addView(
                s,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(height)
                )
        );
    }

    private void spaceHorizontal(
            ViewGroup parent,
            int width
    ) {
        Space s = new Space(this);

        parent.addView(
                s,
                new LinearLayout.LayoutParams(
                        dp(width),
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
    }

    private void spaceInside(
            ViewGroup parent,
            int size
    ) {
        parent.setPadding(
                dp(size),
                dp(size),
                dp(size),
                dp(size)
        );
    }

    private ArrayAdapter<String> createSpinnerAdapter(
            ArrayList<String> values
    ) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        values
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        return adapter;
    }

    private int dp(int value) {
        return (int) (
                value *
                        getResources()
                                .getDisplayMetrics()
                                .density
        );
    }

    private String safe(
            String value,
            String fallback
    ) {
        if (value == null ||
                value.trim().isEmpty()) {
            return fallback;
        }

        return value;
    }

    private double parseDouble(String value) {
        if (value == null) {
            return 0;
        }

        try {
            String clean = value
                    .replace(",", "")
                    .replace("٬", "")
                    .replace(" ", "")
                    .trim();

            if (clean.isEmpty()) {
                return 0;
            }

            return Double.parseDouble(clean);

        } catch (Exception e) {
            return 0;
        }
    }

    private String formatNumber(double value) {
        if (value == Math.rint(value)) {
            return String.format(
                    Locale.US,
                    "%,.0f",
                    value
            );
        }

        return String.format(
                Locale.US,
                "%,.2f",
                value
        );
    }

    private String number(double value) {
        return formatNumber(value);
    }

    private String money(double value) {
        return formatNumber(value);
    }

    private String today() {
        SimpleDateFormat sdf =
                new SimpleDateFormat(
                        "yyyy/MM/dd",
                        Locale.US
                );

        return sdf.format(new Date());
    }

    private String formatDateTime() {
        SimpleDateFormat sdf =
                new SimpleDateFormat(
                        "yyyy/MM/dd HH:mm",
                        Locale.US
                );

        return sdf.format(new Date());
    }

    // =========================
    // پایان قسمت ۶
    // =========================
    // =========================
    // PART 7
    // رسانه، فایل‌ها، تاریخ و توابع عمومی
    // =========================

    private void addContactFooter(ViewGroup parent) {
        space(parent, 20);

        LinearLayout footer = new LinearLayout(this);
        footer.setOrientation(LinearLayout.VERTICAL);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(
                dp(12),
                dp(12),
                dp(12),
                dp(12)
        );

        footer.setBackground(
                roundedBg(
                        DARK_GREEN,
                        GOLD,
                        1,
                        16
                )
        );

        TextView brand = text(
                footer,
                "ZERIVA",
                18,
                GOLD,
                Gravity.CENTER
        );

        brand.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        text(
                footer,
                "Shani Grape • Mariwan • Zarivar",
                13,
                LIGHT_GOLD,
                Gravity.CENTER
        );

        text(
                footer,
                "تماس: " + PHONE,
                13,
                WHITE,
                Gravity.CENTER
        );

        text(
                footer,
                INSTAGRAM,
                13,
                LIGHT_GOLD,
                Gravity.CENTER
        );

        parent.addView(footer);
    }

    private void openMedia(Uri uri) {
        if (uri == null) {
            Toast.makeText(
                    this,
                    "فایلی انتخاب نشده است",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        try {
            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            uri
                    );

            intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "امکان باز کردن فایل وجود ندارد",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private String persistUri(Uri uri) {
        if (uri == null) {
            return "";
        }

        try {
            getContentResolver()
                    .takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );
        } catch (Exception ignored) {
        }

        return uri.toString();
    }

    private Uri stringToUri(String value) {
        if (value == null ||
                value.trim().isEmpty()) {
            return null;
        }

        try {
            return Uri.parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    private void showMedia(
            String uriText,
            String title
    ) {
        Uri uri = stringToUri(uriText);

        if (uri == null) {
            Toast.makeText(
                    this,
                    "فایل موجود نیست",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(
                        "برای باز کردن فایل، دکمه زیر را بزنید."
                )
                .setNegativeButton(
                        "بستن",
                        null
                )
                .setPositiveButton(
                        "باز کردن",
                        (dialog, which) ->
                                openMedia(uri)
                )
                .show();
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (resultCode != RESULT_OK ||
                data == null ||
                data.getData() == null) {
            return;
        }

        Uri uri = data.getData();

        if (requestCode == 1001) {
            selectedStoryUri = uri;
        }

        else if (requestCode == 1002) {
            selectedGalleryUri = uri;
        }

        else if (requestCode == 1003) {
            selectedSatisfactionUri = uri;
        }

        else if (requestCode == 1004) {
            selectedVoiceUri = uri;
        }

        try {
            getContentResolver()
                    .takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );
        } catch (Exception ignored) {
        }

        Toast.makeText(
                this,
                "فایل انتخاب شد",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void chooseMedia(
            int requestCode,
            String title
    ) {
        Intent intent =
                new Intent(
                        Intent.ACTION_OPEN_DOCUMENT
                );

        intent.addCategory(
                Intent.CATEGORY_OPENABLE
        );

        intent.setType("*/*");

        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION |
                        Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        );

        try {
            startActivityForResult(
                    Intent.createChooser(
                            intent,
                            title
                    ),
                    requestCode
            );
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "انتخاب فایل امکان‌پذیر نیست",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void addBack(
            ViewGroup parent
    ) {
        // ناوبری اصلی با دکمه Back خود گوشی انجام می‌شود.
        // عمداً دکمه بازگشت داخل صفحات اضافه نشده است.
    }

    private void showToast(
            String message
    ) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

    private String cleanText(
            String value
    ) {
        if (value == null) {
            return "";
        }

        return value.trim();
    }

    private boolean isEmpty(
            String value
    ) {
        return value == null ||
                value.trim().isEmpty();
    }

    private String now() {
        return formatDateTime();
    }

    // =========================
    // بکاپ ساده اطلاعات URI
    // =========================

    private void saveLastBackupTime() {
        getSharedPreferences(
                "zeriva_backup",
                MODE_PRIVATE
        )
                .edit()
                .putString(
                        "last_backup",
                        formatDateTime()
                )
                .apply();
    }

    private String getLastBackupTime() {
        return getSharedPreferences(
                "zeriva_backup",
                MODE_PRIVATE
        )
                .getString(
                        "last_backup",
                        "هنوز انجام نشده"
                );
    }

    private void showBackupInfo() {
        new AlertDialog.Builder(this)
                .setTitle("پشتیبان اطلاعات")
                .setMessage(
                        "آخرین وضعیت پشتیبان:\n\n" +
                                getLastBackupTime() +
                                "\n\n" +
                                "اطلاعات برنامه در پایگاه داده داخلی دستگاه نگهداری می‌شود. " +
                                "برای پشتیبان‌گیری ابری واقعی، اتصال Google Drive یا سرویس ابری باید اضافه شود."
                )
                .setPositiveButton(
                        "متوجه شدم",
                        null
                )
                .show();
    }

    // =========================
    // تبدیل متن به عدد
    // =========================

    private int parseInt(
            String value
    ) {
        try {
            return Integer.parseInt(
                    value
                            .replace(",", "")
                            .replace("٬", "")
                            .trim()
            );
        } catch (Exception e) {
            return 0;
        }
    }

    private long parseLong(
            String value
    ) {
        try {
            return Long.parseLong(
                    value
                            .replace(",", "")
                            .replace("٬", "")
                            .trim()
            );
        } catch (Exception e) {
            return 0;
        }
    }

    // =========================
    // پایان قسمت ۷
    // =========================
    // =========================
    // PART 8
    // پایگاه داده SQLite
    // =========================

    private static class DB extends SQLiteOpenHelper {

        private static final String DB_NAME =
                "zeriva.db";

        private static final int VERSION = 10;

        private final Context context;

        DB(Context context) {
            super(
                    context,
                    DB_NAME,
                    null,
                    VERSION
            );

            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase d) {

            // -------------------------
            // مشتریان
            // -------------------------

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS customers (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT NOT NULL," +
                            "customer_no TEXT," +
                            "phone TEXT," +
                            "province TEXT," +
                            "city TEXT," +
                            "address TEXT," +
                            "description TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            // -------------------------
            // خرید از باغدار
            // -------------------------

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS purchases (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "farmer TEXT NOT NULL," +
                            "box_count INTEGER DEFAULT 0," +
                            "weight REAL DEFAULT 0," +
                            "price REAL DEFAULT 0," +
                            "total REAL DEFAULT 0," +
                            "purchase_date TEXT," +
                            "description TEXT," +
                            "settled INTEGER DEFAULT 0," +
                            "settled_date TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            // -------------------------
            // سفارش‌ها
            // -------------------------

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS orders (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "order_no TEXT," +
                            "customer TEXT," +
                            "province TEXT," +
                            "city TEXT," +
                            "weight REAL DEFAULT 0," +
                            "price REAL DEFAULT 0," +
                            "total REAL DEFAULT 0," +
                            "deposit_required REAL DEFAULT 0," +
                            "deposit_paid REAL DEFAULT 0," +
                            "deposit_status TEXT DEFAULT 'not_required'," +
                            "transaction_id TEXT," +
                            "payment_date TEXT," +
                            "payment_status TEXT DEFAULT 'unpaid'," +
                            "confirmed INTEGER DEFAULT 1," +
                            "status TEXT DEFAULT 'pending'," +
                            "order_date TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            // -------------------------
            // تنظیمات پیش‌پرداخت
            // -------------------------

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS deposit_settings (" +
                            "id INTEGER PRIMARY KEY," +
                            "type TEXT DEFAULT 'percent'," +
                            "value REAL DEFAULT 0" +
                            ")"
            );

            d.execSQL(
                    "INSERT OR IGNORE INTO deposit_settings " +
                            "(id,type,value) " +
                            "VALUES (1,'percent',0)"
            );

            // -------------------------
            // استوری
                    // -------------------------
            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS stories (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "uri TEXT," +
                            "type TEXT," +
                            "caption TEXT," +
                            "date_text TEXT," +
                            "archived INTEGER DEFAULT 0," +
                            "created_at TEXT" +
                            ")"
            );

            // -------------------------
            // گالری
            // -------------------------
            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS gallery (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "uri TEXT," +
                            "type TEXT," +
                            "caption TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            // -------------------------
            // رضایت مشتری
            // -------------------------
            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS satisfaction (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "uri TEXT," +
                            "type TEXT," +
                            "customer_name TEXT," +
                            "city TEXT," +
                            "message TEXT," +
                            "date_text TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            // -------------------------
            // قیمت روز
            // -------------------------
            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS daily_price (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "price REAL DEFAULT 0," +
                            "date_text TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            // -------------------------
            // معاملات
            // -------------------------
            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS transactions (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "customer TEXT," +
                            "type TEXT," +
                            "description TEXT," +
                            "debit REAL DEFAULT 0," +
                            "payment REAL DEFAULT 0," +
                            "total REAL DEFAULT 0," +
                            "date_text TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            // -------------------------
            // تنظیم شماره مشتری
            // -------------------------
            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS settings (" +
                            "key_name TEXT PRIMARY KEY," +
                            "value TEXT" +
                            ")"
            );
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase d,
                int oldVersion,
                int newVersion
        ) {

            // نسخه‌های قبلی ممکن است بعضی جدول‌ها
            // یا ستون‌های جدید را نداشته باشند.

            ensureColumn(
                    d,
                    "customers",
                    "province",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "customers",
                    "city",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "customers",
                    "address",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "customers",
                    "description",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "customers",
                    "created_at",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "purchases",
                    "box_count",
                    "INTEGER DEFAULT 0"
            );

            ensureColumn(
                    d,
                    "purchases",
                    "purchase_date",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "purchases",
                    "description",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "purchases",
                    "settled",
                    "INTEGER DEFAULT 0"
            );

            ensureColumn(
                    d,
                    "purchases",
                    "settled_date",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "purchases",
                    "created_at",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "orders",
                    "order_no",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "orders",
                    "deposit_required",
                    "REAL DEFAULT 0"
            );

            ensureColumn(
                    d,
                    "orders",
                    "deposit_paid",
                    "REAL DEFAULT 0"
            );

            ensureColumn(
                    d,
                    "orders",
                    "deposit_status",
                    "TEXT DEFAULT 'not_required'"
            );

            ensureColumn(
                    d,
                    "orders",
                    "transaction_id",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "orders",
                    "payment_date",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "orders",
                    "payment_status",
                    "TEXT DEFAULT 'unpaid'"
            );

            ensureColumn(
                    d,
                    "orders",
                    "confirmed",
                    "INTEGER DEFAULT 1"
            );

            ensureColumn(
                    d,
                    "orders",
                    "status",
                    "TEXT DEFAULT 'pending'"
            );

            ensureColumn(
                    d,
                    "orders",
                    "order_date",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "orders",
                    "created_at",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "stories",
                    "caption",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "stories",
                    "archived",
                    "INTEGER DEFAULT 0"
            );

            ensureColumn(
                    d,
                    "stories",
                    "created_at",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "gallery",
                    "caption",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "gallery",
                    "created_at",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "satisfaction",
                    "message",
                    "TEXT"
            );

            ensureColumn(
                    d,
                    "satisfaction",
                    "created_at",
                    "TEXT"
            );

            // اگر جدول‌های جدید در نسخه قبلی وجود نداشته باشند،
            // ایجاد می‌شوند.
            createMissingTables(d);

            // سفارش‌های قدیمی برنامه را قطعی در نظر می‌گیریم
            // تا اطلاعات قبلی کاربر از بین نرود.
            try {
                d.execSQL(
                        "UPDATE orders " +
                                "SET confirmed=1 " +
                                "WHERE confirmed IS NULL"
                );
            } catch (Exception ignored) {
            }

            try {
                d.execSQL(
                        "UPDATE orders " +
                                "SET status='pending' " +
                                "WHERE status IS NULL OR status=''"
                );
            } catch (Exception ignored) {
            }
        }

        private void ensureColumn(
                SQLiteDatabase d,
                String table,
                String column,
                String definition
        ) {
            try {
                Cursor c = d.rawQuery(
                        "PRAGMA table_info(" + table + ")",
                        null
                );

                boolean exists = false;

                int nameIndex =
                        c.getColumnIndex("name");

                while (c.moveToNext()) {
                    if (nameIndex >= 0 &&
                            column.equals(
                                    c.getString(nameIndex)
                            )) {
                        exists = true;
                        break;
                    }
                }

                c.close();

                if (!exists) {
                    d.execSQL(
                            "ALTER TABLE " +
                                    table +
                                    " ADD COLUMN " +
                                    column +
                                    " " +
                                    definition
                    );
                }

            } catch (Exception ignored) {
            }
        }

        private void createMissingTables(
                SQLiteDatabase d
        ) {

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS deposit_settings (" +
                            "id INTEGER PRIMARY KEY," +
                            "type TEXT DEFAULT 'percent'," +
                            "value REAL DEFAULT 0" +
                            ")"
            );

            d.execSQL(
                    "INSERT OR IGNORE INTO deposit_settings " +
                            "(id,type,value) " +
                            "VALUES (1,'percent',0)"
            );

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS stories (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "uri TEXT," +
                            "type TEXT," +
                            "caption TEXT," +
                            "date_text TEXT," +
                            "archived INTEGER DEFAULT 0," +
                            "created_at TEXT" +
                            ")"
            );

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS gallery (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "uri TEXT," +
                            "type TEXT," +
                            "caption TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS satisfaction (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "uri TEXT," +
                            "type TEXT," +
                            "customer_name TEXT," +
                            "city TEXT," +
                            "message TEXT," +
                            "date_text TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS daily_price (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "price REAL DEFAULT 0," +
                            "date_text TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS transactions (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "customer TEXT," +
                            "type TEXT," +
                            "description TEXT," +
                            "debit REAL DEFAULT 0," +
                            "payment REAL DEFAULT 0," +
                            "total REAL DEFAULT 0," +
                            "date_text TEXT," +
                            "created_at TEXT" +
                            ")"
            );

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS settings (" +
                            "key_name TEXT PRIMARY KEY," +
                            "value TEXT" +
                            ")"
            );
        }
    }

    // =========================
    // پایان قسمت ۸
    // =========================
    // =========================
    // PART 9
    // امنیت نهایی و پایان MainActivity
    // =========================

    private boolean isPasswordEnabled() {
        return getSharedPreferences(
                "zeriva_security",
                MODE_PRIVATE
        ).getBoolean(
                "enabled",
                false
        );
    }

    private boolean checkPassword() {
        android.content.SharedPreferences sp =
                getSharedPreferences(
                        "zeriva_security",
                        MODE_PRIVATE
                );

        boolean enabled =
                sp.getBoolean("enabled", false);

        if (!enabled) {
            return true;
        }

        String savedPassword =
                sp.getString("password", "");

        if (savedPassword == null ||
                savedPassword.isEmpty()) {
            return true;
        }

        final EditText password =
                input(
                        "رمز ورود",
                        ""
                );

        password.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                        android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
        );

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("ورود به بخش خصوصی")
                        .setMessage(
                                "این بخش فقط برای صاحب برنامه است."
                        )
                        .setView(password)
                        .setNegativeButton(
                                "انصراف",
                                null
                        )
                        .setPositiveButton(
                                "ورود",
                                null
                        )
                        .create();

        dialog.setOnShowListener(
                v -> {

                    dialog.getButton(
                            AlertDialog.BUTTON_POSITIVE
                    ).setOnClickListener(
                            btn -> {

                                String entered =
                                        password
                                                .getText()
                                                .toString()
                                                .trim();

                                if (savedPassword.equals(
                                        entered
                                )) {

                                    dialog.dismiss();

                                    Toast.makeText(
                                            this,
                                            "ورود موفق بود",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                } else {

                                    Toast.makeText(
                                            this,
                                            "رمز اشتباه است",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    password.setText("");
                                }
                            }
                    );
                }
        );

        dialog.show();

        return false;
    }

    private void protectManagement() {
        android.content.SharedPreferences sp =
                getSharedPreferences(
                        "zeriva_security",
                        MODE_PRIVATE
                );

        boolean enabled =
                sp.getBoolean(
                        "enabled",
                        false
                );

        if (!enabled) {
            showManagement();
            return;
        }

        final EditText password =
                input(
                        "رمز ورود",
                        ""
                );

        password.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                        android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
        );

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("بخش مدیریت")
                        .setMessage(
                                "برای ورود رمز صاحب برنامه را وارد کنید."
                        )
                        .setView(password)
                        .setNegativeButton(
                                "انصراف",
                                null
                        )
                        .setPositiveButton(
                                "ورود",
                                null
                        )
                        .create();

        dialog.setOnShowListener(
                v -> {

                    dialog.getButton(
                            AlertDialog.BUTTON_POSITIVE
                    ).setOnClickListener(
                            btn -> {

                                String saved =
                                        sp.getString(
                                                "password",
                                                ""
                                        );

                                String entered =
                                        password
                                                .getText()
                                                .toString()
                                                .trim();

                                if (saved.equals(
                                        entered
                                )) {

                                    dialog.dismiss();

                                    showManagement();

                                } else {

                                    Toast.makeText(
                                            this,
                                            "رمز اشتباه است",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    password.setText("");
                                }
                            }
                    );
                }
        );

        dialog.show();
    }

    private void showBackupMenu() {

        new AlertDialog.Builder(this)
                .setTitle("پشتیبان اطلاعات ZERIVA")
                .setItems(
                        new String[]{
                                "وضعیت پشتیبان",
                                "بستن"
                        },
                        (dialog, which) -> {

                            if (which == 0) {
                                showBackupInfo();
                            }
                        }
                )
                .show();
    }

    private void confirmExit() {

        new AlertDialog.Builder(this)
                .setTitle("خروج")
                .setMessage(
                        "آیا می‌خواهید از برنامه زریوار خارج شوید؟"
                )
                .setNegativeButton(
                        "خیر",
                        null
                )
                .setPositiveButton(
                        "بله",
                        (dialog, which) -> finish()
                )
                .show();
    }

    // =========================
    // پایان کامل MainActivity
    // =========================

}
