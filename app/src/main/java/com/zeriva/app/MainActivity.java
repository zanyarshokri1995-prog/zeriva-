package com.zeriva.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity {

    // =========================================================
    // رنگ‌های ZERIVA
    // =========================================================

    private static final int DARK_GREEN = Color.rgb(5, 28, 20);
    private static final int GREEN = Color.rgb(12, 55, 37);
    private static final int GREEN_LIGHT = Color.rgb(27, 86, 55);
    private static final int GOLD = Color.rgb(212, 175, 55);
    private static final int WHITE = Color.WHITE;
    private static final int LIGHT = Color.rgb(245, 244, 236);
    private static final int DARK = Color.rgb(25, 30, 27);
    private static final int GRAY = Color.rgb(190, 196, 190);

    private static final String PHONE = "09172172402";
    private static final String INSTAGRAM = "@zeriva_grapes";

    private LinearLayout root;
    private DB db;

    private Uri selectedMediaUri = null;
    private Uri selectedStoryUri = null;

    private final HashMap<String, String[]> provinces = new HashMap<>();

    // =========================================================
    // شروع برنامه
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DB(this);

        loadCities();

        showHome();
    }

    // =========================================================
    // استان‌ها و شهرها
    // =========================================================

    private void loadCities() {

        provinces.clear();

        provinces.put("آذربایجان شرقی",
                new String[]{
                        "تبریز", "مراغه", "مرند", "میانه", "اهر"
                });

        provinces.put("آذربایجان غربی",
                new String[]{
                        "ارومیه", "خوی", "مهاباد", "بوکان", "پیرانشهر"
                });

        provinces.put("اردبیل",
                new String[]{
                        "اردبیل", "پارس‌آباد", "مشگین‌شهر", "خلخال"
                });

        provinces.put("اصفهان",
                new String[]{
                        "اصفهان", "کاشان", "نجف‌آباد", "خمینی‌شهر"
                });

        provinces.put("البرز",
                new String[]{
                        "کرج", "نظرآباد", "هشتگرد"
                });

        provinces.put("تهران",
                new String[]{
                        "تهران", "ری", "شهریار", "اسلامشهر"
                });

        provinces.put("خوزستان",
                new String[]{
                        "اهواز", "آبادان", "خرمشهر", "دزفول", "ماهشهر"
                });

        provinces.put("کردستان",
                new String[]{
                        "سنندج",
                        "مریوان",
                        "سقز",
                        "بانه",
                        "کامیاران",
                        "بیجار"
                });

        provinces.put("کرمانشاه",
                new String[]{
                        "کرمانشاه", "اسلام‌آباد غرب", "جوانرود", "پاوه"
                });

        provinces.put("همدان",
                new String[]{
                        "همدان", "ملایر", "نهاوند", "تویسرکان"
                });

        provinces.put("فارس",
                new String[]{
                        "شیراز", "مرودشت", "جهرم", "فسا"
                });

        provinces.put("قم",
                new String[]{
                        "قم"
                });

        provinces.put("گیلان",
                new String[]{
                        "رشت", "انزلی", "لاهیجان", "رودسر"
                });

        provinces.put("مازندران",
                new String[]{
                        "ساری", "بابل", "آمل", "قائم‌شهر"
                });

        provinces.put("مرکزی",
                new String[]{
                        "اراک", "ساوه", "خمین"
                });

        provinces.put("قزوین",
                new String[]{
                        "قزوین", "تاکستان"
                });

        provinces.put("یزد",
                new String[]{
                        "یزد", "میبد"
                });

        provinces.put("کرمان",
                new String[]{
                        "کرمان", "رفسنجان", "سیرجان"
                });

        provinces.put("هرمزگان",
                new String[]{
                        "بندرعباس", "میناب", "قشم"
                });

        provinces.put("سیستان و بلوچستان",
                new String[]{
                        "زاهدان", "چابهار", "زابل"
                });

        provinces.put("لرستان",
                new String[]{
                        "خرم‌آباد", "بروجرد", "دورود"
                });

        provinces.put("گلستان",
                new String[]{
                        "گرگان", "گنبدکاووس"
                });

        provinces.put("سمنان",
                new String[]{
                        "سمنان", "شاهرود", "دامغان"
                });

        provinces.put("زنجان",
                new String[]{
                        "زنجان", "ابهر"
                });

        provinces.put("ایلام",
                new String[]{
                        "ایلام", "دهلران", "مهران"
                });

        provinces.put("چهارمحال و بختیاری",
                new String[]{
                        "شهرکرد", "بروجن", "فارسان"
                });

        provinces.put("خراسان رضوی",
                new String[]{
                        "مشهد", "نیشابور", "سبزوار"
                });

        provinces.put("خراسان شمالی",
                new String[]{
                        "بجنورد", "شیروان"
                });

        provinces.put("خراسان جنوبی",
                new String[]{
                        "بیرجند", "قائن", "طبس"
                });

        provinces.put("کهگیلویه و بویراحمد",
                new String[]{
                        "یاسوج", "دهدشت"
                });

        provinces.put("بوشهر",
                new String[]{
                        "بوشهر", "دشتستان", "گناوه"
                });

        provinces.put("اردبیل",
                new String[]{
                        "اردبیل", "پارس‌آباد", "مشگین‌شهر"
                });
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
        content.setPadding(
                dp(16),
                dp(20),
                dp(16),
                dp(30)
        );

        scroll.addView(content);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        -1
                )
        );

        setContentView(root);

        // لوگو
        TextView logo = text(
                "ZERIVA",
                34,
                GOLD,
                Gravity.CENTER
        );

        logo.setTypeface(
                Typeface.create(
                        Typeface.SERIF,
                        Typeface.BOLD
                )
        );

        content.addView(
                logo,
                params(-1, -2)
        );

        TextView title = text(
                "زریوار",
                25,
                WHITE,
                Gravity.CENTER
        );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        content.addView(
                title,
                params(-1, -2)
        );

        TextView subtitle = text(
                "Shani Grape • Mariwan • Zarivar",
                14,
                GOLD,
                Gravity.CENTER
        );

        content.addView(
                subtitle,
                params(-1, -2)
        );

        space(content, 18);

        addStorySection(content);

        space(content, 16);

        TextView info = text(
                "انگور شانی ممتاز مریوان\n"
                        + "کیفیت، بسته‌بندی و ارسال مطمئن",
                16,
                LIGHT,
                Gravity.CENTER
        );

        info.setPadding(
                dp(16),
                dp(16),
                dp(16),
                dp(16)
        );

        info.setBackground(
                rounded(
                        GREEN,
                        20,
                        GOLD
                )
        );

        content.addView(
                info,
                params(-1, -2)
        );

        space(content, 20);

        TextView menuTitle = text(
                "مدیریت ZERIVA",
                20,
                GOLD,
                Gravity.CENTER
        );

        menuTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        content.addView(
                menuTitle,
                params(-1, -2)
        );

        space(content, 12);

        // ردیف اول
        LinearLayout row1 = new LinearLayout(this);
        row1.setGravity(Gravity.CENTER);
        row1.setOrientation(LinearLayout.HORIZONTAL);

        row1.addView(
                circleMenu(
                        "👥",
                        "مشتریان",
                        v -> showCustomers()
                ),
                new LinearLayout.LayoutParams(
                        0,
                        dp(125),
                        1
                )
        );

        row1.addView(
                circleMenu(
                        "💰",
                        "حساب‌ها",
                        v -> showTransactions()
                ),
                new LinearLayout.LayoutParams(
                        0,
                        dp(125),
                        1
                )
        );

        content.addView(
                row1,
                params(-1, -2)
        );

        space(content, 10);

        // ردیف دوم
        LinearLayout row2 = new LinearLayout(this);
        row2.setGravity(Gravity.CENTER);
        row2.setOrientation(LinearLayout.HORIZONTAL);

        row2.addView(
                circleMenu(
                        "🍇",
                        "خرید",
                        v -> showBuyFromFarmer()
                ),
                new LinearLayout.LayoutParams(
                        0,
                        dp(125),
                        1
                )
        );

        row2.addView(
                circleMenu(
                        "🚚",
                        "فروش",
                        v -> showSales()
                ),
                new LinearLayout.LayoutParams(
                        0,
                        dp(125),
                        1
                )
        );

        content.addView(
                row2,
                params(-1, -2)
        );

        space(content, 10);

        // ردیف سوم
        LinearLayout row3 = new LinearLayout(this);
        row3.setGravity(Gravity.CENTER);
        row3.setOrientation(LinearLayout.HORIZONTAL);

        row3.addView(
                circleMenu(
                        "📊",
                        "گزارش‌ها",
                        v -> showReports()
                ),
                new LinearLayout.LayoutParams(
                        0,
                        dp(125),
                        1
                )
        );

        row3.addView(
                circleMenu(
                        "⚙️",
                        "بیشتر",
                        v -> showMore()
                ),
                new LinearLayout.LayoutParams(
                        0,
                        dp(125),
                        1
                )
        );

        content.addView(
                row3,
                params(-1, -2)
        );

        space(content, 25);

        addContactFooter(content);
    }

    // =========================================================
    // استوری
    // =========================================================

    private void addStorySection(
            LinearLayout content) {

        LinearLayout card = new LinearLayout(this);

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
                rounded(
                        GREEN,
                        20,
                        GOLD
                )
        );

        TextView title = text(
                "📸 استوری ZERIVA",
                19,
                GOLD,
                Gravity.CENTER
        );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        card.addView(
                title,
                params(-1, -2)
        );

        space(card, 8);

        Button add = addButton(
                "➕ افزودن عکس یا فیلم به استوری",
                v -> showAddStory()
        );

        card.addView(
                add,
                params(-1, -2)
        );

        Cursor c = db.getReadableDatabase()
                .rawQuery(
                        "SELECT id,uri,type "
                                + "FROM stories "
                                + "ORDER BY id DESC",
                        null
                );

        if (c.getCount() == 0) {

            TextView empty = text(
                    "هنوز استوری‌ای اضافه نشده است.",
                    14,
                    GRAY,
                    Gravity.CENTER
            );

            empty.setPadding(
                    8, 12, 8, 8
            );

            card.addView(
                    empty,
                    params(-1, -2)
            );

        } else {

            while (c.moveToNext()) {

                int id = c.getInt(0);
                String uri = c.getString(1);
                String type = c.getString(2);

                addStoryItem(
                        card,
                        id,
                        uri,
                        type
                );
            }
        }

        c.close();

        content.addView(
                card,
                params(-1, -2)
        );
    }

    private void addStoryItem(
            LinearLayout parent,
            int id,
            String uriString,
            String type) {

        LinearLayout item =
                new LinearLayout(this);

        item.setGravity(Gravity.CENTER_VERTICAL);

        TextView t = text(
                type.equals("video")
                        ? "🎬 ویدیوی ZERIVA"
                        : "📷 عکس ZERIVA",
                15,
                WHITE,
                Gravity.CENTER_VERTICAL
        );

        item.addView(
                t,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        Button view = addButton(
                "مشاهده",
                v -> openMedia(
                        Uri.parse(uriString),
                        type
                )
        );

        item.addView(
                view,
                new LinearLayout.LayoutParams(
                        dp(100),
                        dp(45)
                )
        );

        item.setOnLongClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("حذف استوری")
                    .setMessage(
                            "آیا این استوری حذف شود؟"
                    )
                    .setNegativeButton(
                            "لغو",
                            null
                    )
                    .setPositiveButton(
                            "حذف",
                            (dialog, which) -> {

                                db.getWritableDatabase()
                                        .delete(
                                                "stories",
                                                "id=?",
                                                new String[]{
                                                        String.valueOf(id)
                                                }
                                        );

                                showHome();
                            }
                    )
                    .show();

            return true;
        });

        parent.addView(
                item,
                params(-1, -2)
        );
    }

    private void showAddStory() {

        selectedStoryUri = null;

        Intent intent =
                new Intent(Intent.ACTION_OPEN_DOCUMENT);

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
                2001
        );
    }

    private void saveStory() {

        if (selectedStoryUri == null) {

            Toast.makeText(
                    this,
                    "ابتدا عکس یا فیلم را انتخاب کنید.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String uriString =
                selectedStoryUri.toString();

        String type =
                getContentResolver()
                        .getType(selectedStoryUri);

        if (type == null) {
            type = "image";
        }

        String mediaType =
                type.startsWith("video")
                        ? "video"
                        : "image";

        try {

            getContentResolver()
                    .takePersistableUriPermission(
                            selectedStoryUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );

        } catch (Exception ignored) {
        }

        db.getWritableDatabase()
                .execSQL(
                        "INSERT INTO stories"
                                + "(uri,type,created_at)"
                                + " VALUES(?,?,?)",
                        new Object[]{
                                uriString,
                                mediaType,
                                System.currentTimeMillis()
                        }
                );

        Toast.makeText(
                this,
                "استوری با موفقیت اضافه شد.",
                Toast.LENGTH_SHORT
        ).show();

        showHome();
    }

    private void openMedia(
            Uri uri,
            String type) {

        try {

            Intent intent =
                    new Intent(Intent.ACTION_VIEW);

            intent.setDataAndType(
                    uri,
                    type.equals("video")
                            ? "video/*"
                            : "image/*"
            );

            intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            startActivity(intent);

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "امکان باز کردن فایل وجود ندارد.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // مشتریان
    // =========================================================

    private void showCustomers() {

        LinearLayout page =
                page("مشتریان");

        addBack(page);

        Button add = addButton(
                "➕ افزودن مشتری",
                v -> addCustomer()
        );

        page.addView(
                add,
                params(-1, -2)
        );

        Cursor c = db.getReadableDatabase()
                .rawQuery(
                        "SELECT id,name,phone "
                                + "FROM customers "
                                + "ORDER BY id DESC",
                        null
                );

        while (c.moveToNext()) {

            String cardText =
                    "👤 " + c.getString(1)
                            + "\n"
                            + "شماره مشتری: "
                            + c.getInt(0)
                            + "\n"
                            + "📞 "
                            + (c.getString(2) == null
                            ? ""
                            : c.getString(2));

            page.addView(
                    infoCard(cardText),
                    params(-1, -2)
            );
        }

        c.close();
    }

    private void addCustomer() {

        final EditText name =
                input("نام مشتری");

        final EditText phone =
                input("شماره تماس");

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        box.setPadding(
                dp(20),
                dp(10),
                dp(20),
                dp(5)
        );

        box.addView(
                name,
                params(-1, -2)
        );

        box.addView(
                phone,
                params(-1, -2)
        );

        new AlertDialog.Builder(this)
                .setTitle("مشتری جدید")
                .setView(box)
                .setNegativeButton(
                        "لغو",
                        null
                )
                .setPositiveButton(
                        "ذخیره",
                        (dialog, which) -> {

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

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO customers"
                                                    + "(name,phone)"
                                                    + " VALUES(?,?)",
                                            new Object[]{
                                                    n,
                                                    p
                                            }
                                    );

                            showCustomers();
                        }
                )
                .show();
    }

    // =========================================================
    // خرید از باغدار
    // =========================================================

    private void showBuyFromFarmer() {

        LinearLayout page =
                page("خرید از باغدار");

        addBack(page);

        EditText farmer =
                input("نام باغدار");

        EditText weight =
                input("وزن خرید به کیلو");

        EditText price =
                input("قیمت هر کیلو");

        page.addView(farmer);
        page.addView(weight);
        page.addView(price);

        Button save = addButton(
                "💾 ثبت خرید",
                v -> {

                    double w =
                            number(weight);

                    double p =
                            number(price);

                    double total = w * p;

                    db.getWritableDatabase()
                            .execSQL(
                                    "INSERT INTO purchases"
                                            + "(farmer,weight,price,total)"
                                            + " VALUES(?,?,?,?)",
                                    new Object[]{
                                            farmer.getText()
                                                    .toString(),
                                            w,
                                            p,
                                            total
                                    }
                            );

                    Toast.makeText(
                            this,
                            "خرید ثبت شد: "
                                    + money(total),
                            Toast.LENGTH_SHORT
                    ).show();

                    farmer.setText("");
                    weight.setText("");
                    price.setText("");
                }
        );

        page.addView(
                save,
                params(-1, -2)
        );
    }

    // =========================================================
    // فروش و ارسال
    // =========================================================

    private void showSales() {

        selectedMediaUri = null;

        LinearLayout page =
                page("فروش و ارسال");

        addBack(page);

        Spinner province =
                spinner();

        Spinner city =
                spinner();

        EditText customer =
                input("نام مشتری");

        EditText weight =
                input("وزن سفارش به کیلو");

        EditText price =
                input("قیمت هر کیلو");

        page.addView(
                label("استان"),
                params(-1, -2)
        );

        page.addView(
                province,
                params(-1, -2)
        );

        page.addView(
                label("شهر"),
                params(-1, -2)
        );

        page.addView(
                city,
                params(-1, -2)
        );

        page.addView(customer);
        page.addView(weight);
        page.addView(price);

        ArrayList<String> provinceList =
                new ArrayList<>(
                        provinces.keySet()
                );

        createSpinnerAdapter(
                province,
                provinceList
        );

        if (!provinceList.isEmpty()) {

            updateCities(
                    province,
                    city
            );
        }

        province.setOnItemSelectedListener(
                new android.widget.AdapterView
                        .OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        updateCities(
                                province,
                                city
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        LinearLayout weightRow =
                new LinearLayout(this);

        weightRow.setGravity(
                Gravity.CENTER
        );

        Button minus =
                addButton(
                        "− 100",
                        v -> {

                            double value =
                                    number(weight);

                            value =
                                    Math.max(
                                            0,
                                            value - 100
                                    );

                            weight.setText(
                                    formatNumber(value)
                            );
                        }
                );

        Button plus =
                addButton(
                        "+ 100",
                        v -> {

                            double value =
                                    number(weight);

                            value += 100;

                            weight.setText(
                                    formatNumber(value)
                            );
                        }
                );

        weightRow.addView(
                minus,
                new LinearLayout.LayoutParams(
                        0,
                        dp(50),
                        1
                )
        );

        weightRow.addView(
                plus,
                new LinearLayout.LayoutParams(
                        0,
                        dp(50),
                        1
                )
        );

        page.addView(
                weightRow,
                params(-1, -2)
        );

        Button media =
                addButton(
                        "📷 انتخاب عکس یا فیلم سفارش",
                        v -> chooseOrderMedia()
                );

        page.addView(
                media,
                params(-1, -2)
        );

        Button save =
                addButton(
                        "🚚 ثبت فروش و ارسال",
                        v -> {

                            String c =
                                    customer.getText()
                                            .toString()
                                            .trim();

                            String provinceName =
                                    province.getSelectedItem()
                                            .toString();

                            String cityName =
                                    city.getSelectedItem()
                                            .toString();

                            double w =
                                    number(weight);

                            double p =
                                    number(price);

                            double total =
                                    w * p;

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO orders"
                                                    + "(customer,province,city,"
                                                    + "weight,price,total,media_uri)"
                                                    + " VALUES(?,?,?,?,?,?,?)",
                                            new Object[]{
                                                    c,
                                                    provinceName,
                                                    cityName,
                                                    w,
                                                    p,
                                                    total,
                                                    selectedMediaUri == null
                                                            ? null
                                                            : selectedMediaUri
                                                            .toString()
                                            }
                                    );

                            Toast.makeText(
                                    this,
                                    "فروش ثبت شد: "
                                            + money(total),
                                    Toast.LENGTH_SHORT
                            ).show();

                            showCityOrders();
                        }
                );

        page.addView(
                save,
                params(-1, -2)
        );
    }

    private void chooseOrderMedia() {

        Intent intent =
                new Intent(Intent.ACTION_OPEN_DOCUMENT);

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

    private void updateCities(
            Spinner province,
            Spinner city) {

        if (province.getSelectedItem() == null) {
            return;
        }

        String selected =
                province.getSelectedItem()
                        .toString();

        String[] cities =
                provinces.get(selected);

        ArrayList<String> list =
                new ArrayList<>();

        if (cities != null) {

            for (String c : cities) {
                list.add(c);
            }
        }

        createSpinnerAdapter(
                city,
                list
        );
    }

    // =========================================================
    // سفارش‌های شهر
    // =========================================================

    private void showCityOrders() {

        LinearLayout page =
                page("فروش و سفارش‌ها");

        addBack(page);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT city,"
                                        + "COUNT(*),"
                                        + "SUM(weight),"
                                        + "SUM(total) "
                                        + "FROM orders "
                                        + "GROUP BY city "
                                        + "ORDER BY city",
                                null
                        );

        if (c.getCount() == 0) {

            page.addView(
                    infoCard(
                            "هنوز سفارشی ثبت نشده است."
                    ),
                    params(-1, -2)
            );
        }

        while (c.moveToNext()) {

            String city =
                    c.getString(0);

            int count =
                    c.getInt(1);

            double weight =
                    c.getDouble(2);

            double total =
                    c.getDouble(3);

            String s =
                    "📍 " + city
                            + "\n"
                            + "تعداد سفارش: "
                            + count
                            + "\n"
                            + "وزن: "
                            + formatNumber(weight)
                            + " کیلو"
                            + "\n"
                            + "مبلغ: "
                            + money(total);

            page.addView(
                    infoCard(s),
                    params(-1, -2)
            );
        }

        c.close();
    }

    // =========================================================
    // حساب‌ها و معاملات
    // =========================================================

    private void showTransactions() {

        LinearLayout page =
                page("حساب‌ها و معاملات");

        addBack(page);

        Button add =
                addButton(
                        "➕ ثبت معامله",
                        v -> addTransaction()
                );

        page.addView(
                add,
                params(-1, -2)
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT person,description,"
                                        + "debt,payment "
                                        + "FROM transactions "
                                        + "ORDER BY id DESC",
                                null
                        );

        while (c.moveToNext()) {

            double debt =
                    c.getDouble(2);

            double payment =
                    c.getDouble(3);

            String s =
                    "👤 "
                            + c.getString(0)
                            + "\n"
                            + "شرح: "
                            + c.getString(1)
                            + "\n"
                            + "بدهکار: "
                            + money(debt)
                            + "\n"
                            + "پرداخت: "
                            + money(payment);

            page.addView(
                    infoCard(s),
                    params(-1, -2)
            );
        }

        c.close();
    }

    private void addTransaction() {

        EditText person =
                input("نام شخص");

        EditText description =
                input("شرح معامله");

        EditText debt =
                input("بدهکار");

        EditText payment =
                input("پرداخت");

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        box.setPadding(
                dp(20),
                dp(5),
                dp(20),
                dp(5)
        );

        box.addView(person);
        box.addView(description);
        box.addView(debt);
        box.addView(payment);

        new AlertDialog.Builder(this)
                .setTitle("ثبت معامله")
                .setView(box)
                .setNegativeButton(
                        "لغو",
                        null
                )
                .setPositiveButton(
                        "ثبت",
                        (dialog, which) -> {

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO transactions"
                                                    + "(person,description,"
                                                    + "debt,payment)"
                                                    + " VALUES(?,?,?,?)",
                                            new Object[]{
                                                    person.getText()
                                                            .toString(),
                                                    description.getText()
                                                            .toString(),
                                                    number(debt),
                                                    number(payment)
                                            }
                                    );

                            showTransactions();
                        }
                )
                .show();
    }

    // =========================================================
    // گزارش‌ها
    // =========================================================

    private void showReports() {

        LinearLayout page =
                page("گزارش‌های ZERIVA");

        addBack(page);

        SQLiteDatabase database =
                db.getReadableDatabase();

        Cursor purchases =
                database.rawQuery(
                        "SELECT COUNT(*),"
                                + "COALESCE(SUM(weight),0),"
                                + "COALESCE(SUM(total),0)"
                                + " FROM purchases",
                        null
                );

        if (purchases.moveToFirst()) {

            page.addView(
                    infoCard(
                            "🍇 خرید از باغدار\n"
                                    + "تعداد: "
                                    + purchases.getInt(0)
                                    + "\n"
                                    + "وزن کل: "
                                    + formatNumber(
                                    purchases.getDouble(1)
                            )
                                    + " کیلو\n"
                                    + "مبلغ کل: "
                                    + money(
                                    purchases.getDouble(2)
                            )
                    ),
                    params(-1, -2)
            );
        }

        purchases.close();

        Cursor sales =
                database.rawQuery(
                        "SELECT COUNT(*),"
                                + "COALESCE(SUM(weight),0),"
                                + "COALESCE(SUM(total),0)"
                                + " FROM orders",
                        null
                );

        if (sales.moveToFirst()) {

            page.addView(
                    infoCard(
                            "🚚 فروش و ارسال\n"
                                    + "تعداد سفارش: "
                                    + sales.getInt(0)
                                    + "\n"
                                    + "وزن کل: "
                                    + formatNumber(
                                    sales.getDouble(1)
                            )
                                    + " کیلو\n"
                                    + "فروش کل: "
                                    + money(
                                    sales.getDouble(2)
                            )
                    ),
                    params(-1, -2)
            );
        }

        sales.close();

        Button city =
                addButton(
                        "📍 گزارش فروش بر اساس شهر",
                        v -> showCityOrders()
                );

        page.addView(
                city,
                params(-1, -2)
        );
    }

    // =========================================================
    // بیشتر
    // =========================================================

    private void showMore() {

        LinearLayout page =
                page("بیشتر");

        addBack(page);

        page.addView(
                infoCard(
                        "ZERIVA\n"
                                + "Premium Shani Grape\n"
                                + "Mariwan • Zarivar"
                ),
                params(-1, -2)
        );

        Button city =
                addButton(
                        "📍 سفارش‌ها بر اساس شهر",
                        v -> showCityOrders()
                );

        page.addView(
                city,
                params(-1, -2)
        );

        Button home =
                addButton(
                        "🏠 صفحه اصلی",
                        v -> showHome()
                );

        page.addView(
                home,
                params(-1, -2)
        );
    }

    // =========================================================
    // ارتباط با ZERIVA
    // =========================================================

    private void addContactFooter(
            LinearLayout content) {

        space(content, 20);

        TextView line =
                text(
                        "━━━━━━━━━━━━━━━━━━",
                        14,
                        GOLD,
                        Gravity.CENTER
                );

        content.addView(
                line,
                params(-1, -2)
        );

        TextView title =
                text(
                        "ارتباط با ZERIVA",
                        18,
                        GOLD,
                        Gravity.CENTER
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        content.addView(
                title,
                params(-1, -2)
        );

        Button phone =
                addButton(
                        "📞 " + PHONE,
                        v -> openWhatsApp()
                );

        content.addView(
                phone,
                params(-1, -2)
        );

        Button whatsapp =
                addButton(
                        "💬 واتساپ",
                        v -> openWhatsApp()
                );

        content.addView(
                whatsapp,
                params(-1, -2)
        );

        Button instagram =
                addButton(
                        "📷 Instagram: " + INSTAGRAM,
                        v -> openInstagram()
                );

        content.addView(
                instagram,
                params(-1, -2)
        );

        space(content, 10);

        TextView footer =
                text(
                        "ZERIVA • Premium Shani Grape\n"
                                + "Mariwan • Zarivar",
                        13,
                        GRAY,
                        Gravity.CENTER
                );

        content.addView(
                footer,
                params(-1, -2)
        );
    }

    private void openWhatsApp() {

        String phone =
                PHONE.replace(
                        "0",
                        ""
                );

        phone = "98" + phone;

        try {

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                    "https://wa.me/" + phone
                            )
                    );

            startActivity(intent);

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "واتساپ در دسترس نیست.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void openInstagram() {

        try {

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                    "https://instagram.com/zeriva_grapes"
                            )
                    );

            startActivity(intent);

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "امکان باز کردن اینستاگرام نیست.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // Activity Result
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

        if (
                resultCode != RESULT_OK
                        || data == null
                        || data.getData() == null
        ) {
            return;
        }

        Uri uri = data.getData();

        try {

            getContentResolver()
                    .takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );

        } catch (Exception ignored) {
        }

        if (requestCode == 2001) {

            selectedStoryUri = uri;

            new AlertDialog.Builder(this)
                    .setTitle("استوری ZERIVA")
                    .setMessage(
                            "این فایل به استوری اضافه شود؟"
                    )
                    .setNegativeButton(
                            "لغو",
                            null
                    )
                    .setPositiveButton(
                            "انتشار",
                            (dialog, which) ->
                                    saveStory()
                    )
                    .show();

        } else if (requestCode == 1001) {

            selectedMediaUri = uri;

            Toast.makeText(
                    this,
                    "فایل سفارش انتخاب شد.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // ابزارهای رابط کاربری
    // =========================================================

    private LinearLayout page(
            String title) {

        root = new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        root.setBackgroundColor(
                DARK_GREEN
        );

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout content =
                new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                dp(16),
                dp(18),
                dp(16),
                dp(30)
        );

        TextView header =
                text(
                        title,
                        25,
                        GOLD,
                        Gravity.CENTER
                );

        header.setTypeface(
                        Typeface.DEFAULT_BOLD
                );

        content.addView(
                header,
                params(-1, -2)
        );

        space(content, 15);

        scroll.addView(content);

        root.addView(
                scroll,
                params(-1, -1)
        );

        setContentView(root);

        return content;
    }

    private void addBack(
            LinearLayout page) {

        Button back =
                addButton(
                        "← بازگشت",
                        v -> showHome()
                );

        page.addView(
                back,
                params(-1, -2)
        );

        space(page, 10);
    }

    private TextView infoCard(
            String message) {

        TextView t =
                text(
                        message,
                        15,
                        LIGHT,
                        Gravity.RIGHT
                );

        t.setPadding(
                dp(16),
                dp(16),
                dp(16),
                dp(16)
        );

        t.setBackground(
                rounded(
                        GREEN,
                        18,
                        GOLD
                )
        );

        return t;
    }

    private TextView circleMenu(
            String icon,
            String title,
            View.OnClickListener listener) {

        TextView t =
                new TextView(this);

        t.setText(
                icon + "\n" + title
        );

        t.setTextColor(
                WHITE
        );

        t.setTextSize(
                16
        );

        t.setGravity(
                Gravity.CENTER
        );

        t.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        t.setPadding(
                dp(5),
                dp(5),
                dp(5),
                dp(5)
        );

        t.setBackground(
                rounded(
                        GREEN_LIGHT,
                        100,
                        GOLD
                )
        );

        t.setOnClickListener(
                listener
        );

        return t;
    }

    private Button addButton(
            String title,
            View.OnClickListener listener) {

        Button b =
                new Button(this);

        b.setText(title);

        b.setTextSize(15);

        b.setTextColor(
                DARK
        );

        b.setAllCaps(false);

        b.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        b.setGravity(
                Gravity.CENTER
        );

        b.setPadding(
                dp(10),
                dp(8),
                dp(10),
                dp(8)
        );

        b.setBackground(
                rounded(
                        GOLD,
                        18,
                        GOLD
                )
        );

        b.setOnClickListener(
                listener
        );

        return b;
    }

    private TextView label(
            String value) {

        TextView t =
                text(
                        value,
                        15,
                        GOLD,
                        Gravity.RIGHT
                );

        t.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        return t;
    }

    private TextView text(
            String value,
            float size,
            int color,
            int gravity) {

        TextView t =
                new TextView(this);

        t.setText(value);

        t.setTextSize(size);

        t.setTextColor(color);

        t.setGravity(gravity);

        return t;
    }

    private EditText input(
            String hint) {

        EditText e =
                new EditText(this);

        e.setHint(hint);

        e.setHintTextColor(
                GRAY
        );

        e.setTextColor(
                WHITE
        );

        e.setTextSize(16);

        e.setPadding(
                dp(14),
                dp(8),
                dp(14),
                dp(8)
        );

        e.setSingleLine(true);

        e.setBackground(
                rounded(
                        GREEN,
                        15,
                        GRAY
                )
        );

        LinearLayout.LayoutParams lp =
                params(-1, -2);

        lp.setMargins(
                0,
                dp(5),
                0,
                dp(5)
        );

        e.setLayoutParams(lp);

        return e;
    }

    private Spinner spinner() {

        Spinner spinner =
                new Spinner(this);

        spinner.setBackground(
                rounded(
                        GREEN,
                        15,
                        GOLD
                )
        );

        return spinner;
    }

    private void createSpinnerAdapter(
            Spinner spinner,
            ArrayList<String> items) {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_item,
                        items
                ) {

                    @Override
                    public View getView(
                            int position,
                            View convertView,
                            ViewGroup parent) {

                        TextView v =
                                (TextView) super.getView(
                                        position,
                                        convertView,
                                        parent
                                );

                        v.setTextColor(
                                WHITE
                        );

                        v.setTextSize(15);

                        v.setGravity(
                                Gravity.CENTER
                        );

                        v.setPadding(
                                dp(8),
                                dp(8),
                                dp(8),
                                dp(8)
                        );

                        return v;
                    }

                    @Override
                    public View getDropDownView(
                            int position,
                            View convertView,
                            ViewGroup parent) {

                        TextView v =
                                (TextView) super
                                        .getDropDownView(
                                                position,
                                                convertView,
                                                parent
                                        );

                        v.setTextColor(
                                DARK
                        );

                        v.setTextSize(15);

                        v.setPadding(
                                dp(12),
                                dp(12),
                                dp(12),
                                dp(12)
                        );

                        return v;
                    }
                };

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);
    }

    private GradientDrawable rounded(
            int color,
            int radius,
            int strokeColor) {

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(color);

        drawable.setCornerRadius(
                dp(radius)
        );

        drawable.setStroke(
                dp(1),
                strokeColor
        );

        return drawable;
    }

    private LinearLayout.LayoutParams params(
            int width,
            int height) {

        int w =
                width == -1
                        ? -1
                        : width == -2
                        ? -2
                        : dp(width);

        int h =
                height == -1
                        ? -1
                        : height == -2
                        ? -2
                        : dp(height);

        return new LinearLayout.LayoutParams(
                w,
                h
        );
    }

    private void space(
            LinearLayout parent,
            int height) {

        Space s =
                new Space(this);

        parent.addView(
                s,
                params(-1, height)
        );
    }

    // =========================================================
    // تبدیل عدد
    // =========================================================

    private double number(
            EditText editText) {

        try {

            String s =
                    editText.getText()
                            .toString()
                            .trim();

            if (s.isEmpty()) {
                return 0;
            }

            s = s
                    .replace(",", "")
                    .replace("٬", "")
                    .replace("،", "")
                    .replace("۰", "0")
                    .replace("۱", "1")
                    .replace("۲", "2")
                    .replace("۳", "3")
                    .replace("۴", "4")
                    .replace("۵", "5")
                    .replace("۶", "6")
                    .replace("۷", "7")
                    .replace("۸", "8")
                    .replace("۹", "9")
                    .replace("٫", ".");

            return Double.parseDouble(s);

        } catch (Exception e) {

            return 0;
        }
    }

    private String formatNumber(
            double value) {

        if (value == (long) value) {

            return String.format(
                    Locale.US,
                    "%d",
                    (long) value
            );
        }

        return String.format(
                Locale.US,
                "%.2f",
                value
        );
    }

    private String money(
            double value) {

        return formatNumber(value)
                + " تومان";
    }

    private int dp(
            int value) {

        return (int) (
                value *
                        getResources()
                                .getDisplayMetrics()
                                .density
        );
    }

    // =========================================================
    // دیتابیس
    // =========================================================

    private static class DB
            extends SQLiteOpenHelper {

        private static final String DB_NAME =
                "zeriva.db";

        private static final int VERSION = 3;

        DB(Context context) {

            super(
                    context,
                    DB_NAME,
                    null,
                    VERSION
            );
        }

        @Override
        public void onCreate(
                SQLiteDatabase db) {

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS customers (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT NOT NULL," +
                            "phone TEXT" +
                            ")"
            );

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS transactions (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "person TEXT," +
                            "description TEXT," +
                            "debt REAL DEFAULT 0," +
                            "payment REAL DEFAULT 0" +
                            ")"
            );

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS purchases (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "farmer TEXT," +
                            "weight REAL DEFAULT 0," +
                            "price REAL DEFAULT 0," +
                            "total REAL DEFAULT 0" +
                            ")"
            );

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS orders (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "customer TEXT," +
                            "province TEXT," +
                            "city TEXT," +
                            "weight REAL DEFAULT 0," +
                            "price REAL DEFAULT 0," +
                            "total REAL DEFAULT 0," +
                            "media_uri TEXT" +
                            ")"
            );

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS stories (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "uri TEXT NOT NULL," +
                            "type TEXT NOT NULL," +
                            "created_at INTEGER DEFAULT 0" +
                            ")"
            );
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase db,
                int oldVersion,
                int newVersion) {

            if (oldVersion < 2) {

                try {

                    db.execSQL(
                            "ALTER TABLE orders "
                                    + "ADD COLUMN media_uri TEXT"
                    );

                } catch (Exception ignored) {
                }
            }

            if (oldVersion < 3) {

                db.execSQL(
                        "CREATE TABLE IF NOT EXISTS stories (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "uri TEXT NOT NULL," +
                                "type TEXT NOT NULL," +
                                "created_at INTEGER DEFAULT 0" +
                                ")"
                );
            }
        }
    }
    }
