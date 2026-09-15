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
    // CUSTOMERS
    // =========================================================

    private void showCustomers() {

        LinearLayout content =
                page("مشتریان ZERIVA");

        Button add =
                addButton(
                        "➕ افزودن مشتری",
                        v -> addCustomer()
                );

        content.addView(add);

        space(content, 10);

        SQLiteDatabase database =
                db.getReadableDatabase();

        Cursor c =
                database.rawQuery(
                        "SELECT id,name,phone,province,city,address " +
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

            c.close();

            return;
        }

        while (c.moveToNext()) {

            final long customerId =
                    c.getLong(0);

            String name =
                    safe(c.getString(1));

            String phone =
                    safe(c.getString(2));

            String province =
                    safe(c.getString(3));

            String city =
                    safe(c.getString(4));

            String address =
                    safe(c.getString(5));

            LinearLayout card =
                    rounded(
                            WHITE,
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

            card.addView(
                    text(
                            "👤 " + name,
                            16,
                            DARK_GREEN,
                            Typeface.BOLD
                    )
            );

            card.addView(
                    text(
                            "📞 " + phone,
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            card.addView(
                    text(
                            "📍 " + province +
                            " - " + city,
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            if (!address.isEmpty()) {

                card.addView(
                        text(
                                "🏠 " + address,
                                14,
                                DARK_GREEN,
                                Typeface.NORMAL
                        )
                );
            }

            space(card, 6);

            Button orders =
                    addButton(
                            "📦 سفارش‌های مشتری",
                            v -> showOrdersForCustomer(
                                    customerId
                            )
                    );

            card.addView(orders);

            Button edit =
                    addButton(
                            "✏️ ویرایش",
                            v -> editCustomer(
                                    customerId
                            )
                    );

            card.addView(edit);

            Button delete =
                    addButton(
                            "🗑️ حذف مشتری",
                            v -> {

                                new AlertDialog.Builder(
                                        this
                                )
                                        .setTitle(
                                                "حذف مشتری"
                                        )
                                        .setMessage(
                                                "آیا از حذف این مشتری مطمئن هستید؟"
                                        )
                                        .setPositiveButton(
                                                "بله",
                                                (dialog, which) -> {

                                                    db.getWritableDatabase()
                                                            .delete(
                                                                    "customers",
                                                                    "id=?",
                                                                    new String[]{
                                                                            String.valueOf(
                                                                                    customerId
                                                                            )
                                                                    }
                                                            );

                                                    showCustomers();
                                                }
                                        )
                                        .setNegativeButton(
                                                "خیر",
                                                null
                                        )
                                        .show();
                            }
                    );

            card.addView(delete);

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
    // ADD CUSTOMER
    // =========================================================

    private void addCustomer() {

        LinearLayout content =
                page("افزودن مشتری");

        EditText name =
                input("نام مشتری");

        EditText phone =
                input("شماره تماس");

        Spinner provinceSpinner =
                new Spinner(this);

        ArrayList<String> provinceList =
                new ArrayList<>(
                        provinces.keySet()
                );

        provinceList.sort(
                String::compareTo
        );

        provinceList.add(
                0,
                "استان را انتخاب کنید"
        );

        provinceSpinner.setAdapter(
                createSpinnerAdapter(
                        provinceList
                )
        );

        Spinner citySpinner =
                new Spinner(this);

        citySpinner.setAdapter(
                createSpinnerAdapter(
                        new String[]{
                                "ابتدا استان را انتخاب کنید"
                        }
                )
        );

        provinceSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        if (position <= 0) {

                            citySpinner.setAdapter(
                                    createSpinnerAdapter(
                                            new String[]{
                                                    "ابتدا استان را انتخاب کنید"
                                            }
                                    )
                            );

                            return;
                        }

                        String selectedProvince =
                                provinceList.get(
                                        position
                                );

                        String[] cities =
                                provinces.get(
                                        selectedProvince
                                );

                        if (cities == null) {

                            cities =
                                    new String[]{
                                            "شهری موجود نیست"
                                    };
                        }

                        citySpinner.setAdapter(
                                createSpinnerAdapter(
                                        cities
                                )
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent
                    ) {
                    }
                }
        );

        EditText address =
                input("آدرس");

        content.addView(
                text(
                        "نام مشتری",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(name);

        space(content, 6);

        content.addView(
                text(
                        "شماره تماس",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(phone);

        space(content, 6);

        content.addView(
                text(
                        "استان",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(
                provinceSpinner
        );

        space(content, 6);

        content.addView(
                text(
                        "شهر",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(
                citySpinner
        );

        space(content, 6);

        content.addView(
                text(
                        "آدرس",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(address);

        space(content, 12);

        Button save =
                addButton(
                        "💾 ذخیره مشتری",
                        v -> {

                            String nameValue =
                                    cleanText(
                                            name.getText()
                                                    .toString()
                                    );

                            String phoneValue =
                                    cleanText(
                                            phone.getText()
                                                    .toString()
                                    );

                            String provinceValue =
                                    provinceSpinner
                                            .getSelectedItem()
                                            .toString();

                            String cityValue =
                                    citySpinner
                                            .getSelectedItem()
                                            .toString();

                            String addressValue =
                                    cleanText(
                                            address.getText()
                                                    .toString()
                                    );

                            if (nameValue.isEmpty()) {

                                showToast(
                                        "نام مشتری را وارد کنید."
                                );

                                return;
                            }

                            if (phoneValue.isEmpty()) {

                                showToast(
                                        "شماره تماس را وارد کنید."
                                );

                                return;
                            }

                            if (
                                    provinceValue.equals(
                                            "استان را انتخاب کنید"
                                    )
                            ) {

                                showToast(
                                        "استان را انتخاب کنید."
                                );

                                return;
                            }

                            if (
                                    cityValue.equals(
                                            "ابتدا استان را انتخاب کنید"
                                    )
                                    ||
                                    cityValue.equals(
                                            "شهری موجود نیست"
                                    )
                            ) {

                                showToast(
                                        "شهر را انتخاب کنید."
                                );

                                return;
                            }

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "name",
                                    nameValue
                            );

                            values.put(
                                    "phone",
                                    phoneValue
                            );

                            values.put(
                                    "province",
                                    provinceValue
                            );

                            values.put(
                                    "city",
                                    cityValue
                            );

                            values.put(
                                    "address",
                                    addressValue
                            );

                            long result =
                                    db.getWritableDatabase()
                                            .insert(
                                                    "customers",
                                                    null,
                                                    values
                                            );

                            if (result == -1) {

                                showToast(
                                        "ذخیره مشتری انجام نشد."
                                );

                                return;
                            }

                            showToast(
                                    "مشتری با موفقیت ثبت شد."
                            );

                            showCustomers();
                        }
                );

        content.addView(save);
    }

    // =========================================================
    // EDIT CUSTOMER
    // =========================================================

    private void editCustomer(
            long customerId
    ) {

        Cursor cursor =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT name,phone,province,city,address " +
                                "FROM customers WHERE id=?",
                                new String[]{
                                        String.valueOf(
                                                customerId
                                        )
                                }
                        );

        if (!cursor.moveToFirst()) {

            cursor.close();

            showToast(
                    "مشتری پیدا نشد."
            );

            return;
        }

        String oldName =
                safe(
                        cursor.getString(0)
                );

        String oldPhone =
                safe(
                        cursor.getString(1)
                );

        String oldProvince =
                safe(
                        cursor.getString(2)
                );

        String oldCity =
                safe(
                        cursor.getString(3)
                );

        String oldAddress =
                safe(
                        cursor.getString(4)
                );

        cursor.close();

        LinearLayout content =
                page("ویرایش مشتری");

        EditText name =
                input(
                        "نام مشتری",
                        oldName
                );

        EditText phone =
                input(
                        "شماره تماس",
                        oldPhone
                );

        EditText address =
                input(
                        "آدرس",
                        oldAddress
                );

        content.addView(
                text(
                        "نام مشتری",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(name);

        space(content, 6);

        content.addView(
                text(
                        "شماره تماس",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(phone);

        space(content, 6);

        content.addView(
                text(
                        "استان",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(
                text(
                        oldProvince,
                        14,
                        LIGHT_GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 6);

        content.addView(
                text(
                        "شهر",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(
                text(
                        oldCity,
                        14,
                        LIGHT_GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 6);

        content.addView(
                text(
                        "آدرس",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(address);

        space(content, 12);

        Button save =
                addButton(
                        "💾 ذخیره تغییرات",
                        v -> {

                            String nameValue =
                                    cleanText(
                                            name.getText()
                                                    .toString()
                                    );

                            String phoneValue =
                                    cleanText(
                                            phone.getText()
                                                    .toString()
                                    );

                            String addressValue =
                                    cleanText(
                                            address.getText()
                                                    .toString()
                                    );

                            if (nameValue.isEmpty()) {

                                showToast(
                                        "نام مشتری را وارد کنید."
                                );

                                return;
                            }

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "name",
                                    nameValue
                            );

                            values.put(
                                    "phone",
                                    phoneValue
                            );

                            values.put(
                                    "address",
                                    addressValue
                            );

                            int result =
                                    db.getWritableDatabase()
                                            .update(
                                                    "customers",
                                                    values,
                                                    "id=?",
                                                    new String[]{
                                                            String.valueOf(
                                                                    customerId
                                                            )
                                                    }
                                            );

                            if (result <= 0) {

                                showToast(
                                        "تغییرات ذخیره نشد."
                                );

                                return;
                            }

                            showToast(
                                    "اطلاعات مشتری ویرایش شد."
                            );

                            showCustomers();
                        }
                );

        content.addView(save);
    }

    // =========================================================
    // SHOW ORDERS FOR CUSTOMER
    // =========================================================

    private void showOrdersForCustomer(
            long customerId
    ) {

        LinearLayout content =
                page("سفارش‌های مشتری");

        Cursor cursor =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,weight,total,status,order_date " +
                                "FROM orders WHERE customer_id=? " +
                                "ORDER BY id DESC",
                                new String[]{
                                        String.valueOf(
                                                customerId
                                        )
                                }
                        );

        if (cursor.getCount() == 0) {

            content.addView(
                    text(
                            "برای این مشتری سفارشی ثبت نشده است.",
                            15,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            cursor.close();

            return;
        }

        while (cursor.moveToNext()) {

            long id =
                    cursor.getLong(0);

            double weight =
                    cursor.getDouble(1);

            double total =
                    cursor.getDouble(2);

            String status =
                    safe(
                            cursor.getString(3)
                    );

            String orderDate =
                    safe(
                            cursor.getString(4)
                    );

            LinearLayout card =
                    rounded(
                            WHITE,
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

            card.addView(
                    text(
                            "📦 سفارش #" + id,
                            16,
                            DARK_GREEN,
                            Typeface.BOLD
                    )
            );

            card.addView(
                    text(
                            "وزن: " +
                            formatNumber(weight) +
                            " کیلو",
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            card.addView(
                    text(
                            "مبلغ: " +
                            money(total),
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            card.addView(
                    text(
                            "وضعیت: " +
                            status,
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            card.addView(
                    text(
                            "تاریخ سفارش: " +
                            orderDate,
                            14,
                            DARK_GREEN,
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

            space(content, 10);
        }

        cursor.close();
    }

    // =========================================================
    // BUY FROM FARMER
    // =========================================================

    private void showBuyFromFarmer() {

        LinearLayout content =
                page("خرید از باغدار");

        Button add =
                addButton(
                        "➕ ثبت خرید جدید از باغدار",
                        v -> addFarmerPurchase()
                );

        content.addView(add);

        space(content, 8);

        Button unsettled =
                addButton(
                        "💰 خریدهای تسویه‌نشده",
                        v -> showUnsettledPurchases()
                );

        content.addView(unsettled);

        space(content, 8);

        Button settled =
                addButton(
                        "✅ خریدهای تسویه‌شده",
                        v -> showSettledPurchases()
                );

        content.addView(settled);

        space(content, 8);

        Button files =
                addButton(
                        "📁 پرونده باغدارها",
                        v -> showFarmerFiles()
                );

        content.addView(files);

        space(content, 8);

        Button history =
                addButton(
                        "📜 تاریخچه خرید باغدار",
                        v -> showFarmerHistory()
                );

        content.addView(history);

        addContactFooter(content);
    }

    // =========================================================
    // ADD FARMER PURCHASE
    // =========================================================

    private void addFarmerPurchase() {

        LinearLayout content =
                page("ثبت خرید از باغدار");

        EditText farmer =
                input("نام باغدار");

        EditText phone =
                input("شماره تماس باغدار");

        EditText boxes =
                input("تعداد جعبه");

        EditText weight =
                input("وزن کل به کیلو");

        EditText price =
                input("قیمت خرید هر کیلو");

        EditText description =
                input("توضیحات باغدار");

        content.addView(
                text(
                        "نام باغدار",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(farmer);

        space(content, 6);

        content.addView(
                text(
                        "شماره تماس",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(phone);

        space(content, 6);

        content.addView(
                text(
                        "تعداد جعبه",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(boxes);

        space(content, 6);

        content.addView(
                text(
                        "وزن کل",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(weight);

        space(content, 6);

        content.addView(
                text(
                        "قیمت خرید هر کیلو",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(price);

        space(content, 6);

        content.addView(
                text(
                        "توضیحات",
                        14,
                        WHITE,
                        Typeface.BOLD
                )
        );

        content.addView(description);

        space(content, 12);

        Button save =
                addButton(
                        "💾 ثبت خرید",
                        v -> {

                            String farmerName =
                                    cleanText(
                                            farmer.getText()
                                                    .toString()
                                    );

                            String phoneValue =
                                    cleanText(
                                            phone.getText()
                                                    .toString()
                                    );

                            int boxCount =
                                    parseInt(
                                            boxes.getText()
                                                    .toString()
                                    );

                            double weightValue =
                                    parseDouble(
                                            weight.getText()
                                                    .toString()
                                    );

                            double priceValue =
                                    parseDouble(
                                            price.getText()
                                                    .toString()
                                    );

                            String descriptionValue =
                                    cleanText(
                                            description.getText()
                                                    .toString()
                                    );

                            if (farmerName.isEmpty()) {

                                showToast(
                                        "نام باغدار را وارد کنید."
                                );

                                return;
                            }

                            if (weightValue <= 0) {

                                showToast(
                                        "وزن خرید باید بیشتر از صفر باشد."
                                );

                                return;
                            }

                            if (priceValue <= 0) {

                                showToast(
                                        "قیمت خرید را وارد کنید."
                                );

                                return;
                            }

                            double total =
                                    weightValue *
                                    priceValue;

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "farmer_name",
                                    farmerName
                            );

                            values.put(
                                    "phone",
                                    phoneValue
                            );

                            values.put(
                                    "boxes",
                                    boxCount
                            );

                            values.put(
                                    "weight",
                                    weightValue
                            );

                            values.put(
                                    "price",
                                    priceValue
                            );

                            values.put(
                                    "total",
                                    total
                            );

                            values.put(
                                    "description",
                                    descriptionValue
                            );

                            values.put(
                                    "date",
                                    today()
                            );

                            values.put(
                                    "settled",
                                    0
                            );

                            long result =
                                    db.getWritableDatabase()
                                            .insert(
                                                    "farmer_purchases",
                                                    null,
                                                    values
                                            );

                            if (result == -1) {

                                showToast(
                                        "ثبت خرید انجام نشد."
                                );

                                return;
                            }

                            showToast(
                                    "خرید باغدار با موفقیت ثبت شد."
                            );

                            showBuyFromFarmer();
                        }
                );

        content.addView(save);
    }

    // =========================================================
    // UNSETTLED PURCHASES
    // =========================================================

    private void showUnsettledPurchases() {

        LinearLayout content =
                page("خریدهای تسویه‌نشده");

        Cursor cursor =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,farmer_name,boxes,weight,price,total,date,description " +
                                "FROM farmer_purchases " +
                                "WHERE settled=0 " +
                                "ORDER BY id DESC",
                                null
                        );

        if (cursor.getCount() == 0) {

            content.addView(
                    text(
                            "خرید تسویه‌نشده‌ای وجود ندارد.",
                            15,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            cursor.close();

            return;
        }

        while (cursor.moveToNext()) {

            final long id =
                    cursor.getLong(0);

            String farmerName =
                    safe(cursor.getString(1));

            int boxes =
                    cursor.getInt(2);

            double weight =
                    cursor.getDouble(3);

            double price =
                    cursor.getDouble(4);

            double total =
                    cursor.getDouble(5);

            String date =
                    safe(cursor.getString(6));

            String description =
                    safe(cursor.getString(7));

            LinearLayout card =
                    rounded(
                            WHITE,
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

            card.addView(
                    text(
                            "👨‍🌾 " + farmerName,
                            16,
                            DARK_GREEN,
                            Typeface.BOLD
                    )
            );

            card.addView(
                    text(
                            "📦 جعبه: " +
                            boxes,
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            card.addView(
                    text(
                            "⚖️ وزن: " +
                            formatNumber(weight) +
                            " کیلو",
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            card.addView(
                    text(
                            "💵 قیمت هر کیلو: " +
                            money(price),
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            card.addView(
                    text(
                            "💰 مبلغ کل: " +
                            money(total),
                            14,
                            DARK_GREEN,
                            Typeface.BOLD
                    )
            );

            card.addView(
                    text(
                            "📅 تاریخ: " +
                            date,
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            if (!description.isEmpty()) {

                card.addView(
                        text(
                                "📝 " +
                                description,
                                14,
                                DARK_GREEN,
                                Typeface.NORMAL
                        )
                );
            }

            space(card, 8);

            Button settle =
                    addButton(
                            "✅ تسویه حساب",
                            v -> settlePurchase(id)
                    );

            card.addView(settle);

            content.addView(
                    card,
                    new LinearLayout.LayoutParams(
                            -1,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            space(content, 10);
        }

        cursor.close();
                      }
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

            spaceInside(parent, 10);
        }

        c.close();
    }

    // =========================================================
    // SETTLE PURCHASE
    // =========================================================

    private void settlePurchase(
            int id
    ) {

        new AlertDialog.Builder(this)
                .setTitle(
                        "تسویه خرید باغدار"
                )
                .setMessage(
                        "آیا این خرید تسویه شده است؟"
                )
                .setPositiveButton(
                        "بله، تسویه شد",
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
                                        "خرید با موفقیت تسویه شد.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                showBuyFromFarmer();

                            } else {

                                Toast.makeText(
                                        this,
                                        "تسویه انجام نشد.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                )
                .setNegativeButton(
                        "انصراف",
                        null
                )
                .show();
    }

    // =========================================================
    // SETTLED PURCHASES
    // =========================================================

    private void showSettledPurchases() {

        LinearLayout content =
                page("خریدهای تسویه‌شده");

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,farmer,box_count," +
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
                            "هنوز خرید تسویه‌شده‌ای وجود ندارد.",
                            14,
                            WHITE,
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

            String purchaseDate =
                    safe(c.getString(6));

            String settledDate =
                    safe(c.getString(7));

            String description =
                    safe(c.getString(8));

            LinearLayout card =
                    rounded(
                            WHITE,
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
                            "\n📦 تعداد جعبه: " +
                            formatNumber(boxes) +
                            "\n⚖️ وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💵 قیمت هر کیلو: " +
                            money(price) +
                            "\n💰 مبلغ کل: " +
                            money(total) +
                            " تومان" +
                            "\n📅 تاریخ خرید: " +
                            purchaseDate +
                            "\n✅ تاریخ تسویه: " +
                            settledDate +
                            (description.isEmpty()
                                    ? ""
                                    : "\n📝 " +
                                      description),
                            14,
                            DARK_GREEN,
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

            space(content, 10);
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
                                "SELECT farmer," +
                                "COUNT(*)," +
                                "COALESCE(SUM(box_count),0)," +
                                "COALESCE(SUM(weight),0)," +
                                "COALESCE(SUM(total),0) " +
                                "FROM purchases " +
                                "GROUP BY farmer " +
                                "ORDER BY farmer ASC",
                                null
                        );

        if (c.getCount() == 0) {

            content.addView(
                    text(
                            "هنوز پرونده باغداری ثبت نشده است.",
                            14,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            c.close();

            return;
        }

        while (c.moveToNext()) {

            String farmer =
                    safe(c.getString(0));

            int count =
                    c.getInt(1);

            double boxes =
                    c.getDouble(2);

            double weight =
                    c.getDouble(3);

            double total =
                    c.getDouble(4);

            LinearLayout card =
                    rounded(
                            WHITE,
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
                            "👨‍🌾 " + farmer,
                            17,
                            DARK_GREEN,
                            Typeface.BOLD
                    )
            );

            card.addView(
                    text(
                            "تعداد خرید: " +
                            count +
                            "\n📦 مجموع جعبه: " +
                            formatNumber(boxes) +
                            "\n⚖️ مجموع وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💰 مجموع مبلغ: " +
                            money(total) +
                            " تومان",
                            14,
                            DARK_GREEN,
                            Typeface.NORMAL
                    )
            );

            Button history =
                    addButton(
                            "📜 مشاهده تاریخچه این باغدار",
                            v -> showFarmerHistory(
                                    farmer
                            )
                    );

            card.addView(history);

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
    // FARMER HISTORY
    // =========================================================

    private void showFarmerHistory(
            String farmer
    ) {

        LinearLayout content =
                page(
                        "تاریخچه: " + farmer
                );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,box_count,weight," +
                                "price,total,purchase_date," +
                                "settled,settled_date,description " +
                                "FROM purchases " +
                                "WHERE farmer=? " +
                                "ORDER BY id DESC",
                                new String[]{
                                        farmer
                                }
                        );

        if (c.getCount() == 0) {

            content.addView(
                    text(
                            "برای این باغدار سابقه‌ای ثبت نشده است.",
                            14,
                            WHITE,
                            Typeface.NORMAL
                    )
            );

            c.close();

            return;
        }

        while (c.moveToNext()) {

            int id =
                    c.getInt(0);

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

            int settled =
                    c.getInt(6);

            String settledDate =
                    safe(c.getString(7));

            String description =
                    safe(c.getString(8));

            String status =
                    settled == 1
                            ? "تسویه شده"
                            : "تسویه نشده";

            LinearLayout card =
                    rounded(
                            WHITE,
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
                            "خرید #" + id +
                            "\n📦 جعبه: " +
                            formatNumber(boxes) +
                            "\n⚖️ وزن: " +
                            formatNumber(weight) +
                            " کیلو" +
                            "\n💵 قیمت هر کیلو: " +
                            money(price) +
                            "\n💰 مبلغ کل: " +
                            money(total) +
                            " تومان" +
                            "\n📅 تاریخ خرید: " +
                            purchaseDate +
                            "\n🔵 وضعیت: " +
                            status +
                            (settledDate.isEmpty()
                                    ? ""
                                    : "\n✅ تاریخ تسویه: " +
                                      settledDate) +
                            (description.isEmpty()
                                    ? ""
                                    : "\n📝 " +
                                      description),
                            14,
                            DARK_GREEN,
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

            space(content, 10);
        }

        c.close();
    }

    // =========================================================
    // DAILY PRICE
    // =========================================================

    private void showDailyPrice() {

        LinearLayout content =
                page("قیمت روز انگور شانی");

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

        space(content, 10);

        // -----------------------------------------------------
        // ADDRESS
        // -----------------------------------------------------

        content.addView(
                label("آدرس تحویل")
        );

        EditText address =
                input("آدرس کامل محل تحویل");

        content.addView(address);

        space(content, 10);

        // -----------------------------------------------------
        // DEPOSIT
        // -----------------------------------------------------

        content.addView(
                label("بیعانه")
        );

        EditText deposit =
                input("مبلغ بیعانه");

        deposit.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        content.addView(deposit);

        space(content, 12);

        Button calculate =
                addButton(
                        "🧮 محاسبه مبلغ سفارش",
                        v -> {

                            double weightValue =
                                    parseDouble(
                                            weight.getText()
                                                    .toString()
                                    );

                            if (weightValue <= 0) {

                                showToast(
                                        "وزن سفارش را وارد کنید."
                                );

                                return;
                            }

                            double currentPrice =
                                    currentDailyPrice();

                            if (currentPrice <= 0) {

                                showToast(
                                        "ابتدا قیمت روز را ثبت کنید."
                                );

                                return;
                            }

                            double total =
                                    weightValue *
                                    currentPrice;

                            double required =
                                    calculateDeposit(
                                            total
                                    );

                            price.setText(
                                    money(currentPrice) +
                                    " تومان\n" +
                                    "مبلغ کل: " +
                                    money(total) +
                                    " تومان\n" +
                                    "بیعانه موردنیاز: " +
                                    money(required) +
                                    " تومان"
                            );
                        }
                );

        content.addView(calculate);

        space(content, 8);

        Button save =
                addButton(
                        "📦 ثبت سفارش",
                        v -> {

                            String customerValue =
                                    cleanText(
                                            customer.getText()
                                                    .toString()
                                    );

                            String provinceValue =
                                    provinceSpinner
                                            .getSelectedItem()
                                            .toString();

                            String cityValue =
                                    citySpinner
                                            .getSelectedItem()
                                            .toString();

                            double weightValue =
                                    parseDouble(
                                            weight.getText()
                                                    .toString()
                                    );

                            double depositValue =
                                    parseDouble(
                                            deposit.getText()
                                                    .toString()
                                    );

                            String addressValue =
                                    cleanText(
                                            address.getText()
                                                    .toString()
                                    );

                            double currentPrice =
                                    currentDailyPrice();

                            if (customerValue.isEmpty()) {

                                showToast(
                                        "نام مشتری را وارد کنید."
                                );

                                return;
                            }

                            if (weightValue <= 0) {

                                showToast(
                                        "وزن سفارش را وارد کنید."
                                );

                                return;
                            }

                            if (currentPrice <= 0) {

                                showToast(
                                        "ابتدا قیمت روز را ثبت کنید."
                                );

                                return;
                            }

                            double total =
                                    weightValue *
                                    currentPrice;

                            double required =
                                    calculateDeposit(
                                            total
                                    );

                            String status;

                            int confirmed;

                            if (depositValue >= required) {

                                status =
                                        "confirmed";

                                confirmed = 1;

                            } else {

                                status =
                                        "awaiting_deposit";

                                confirmed = 0;
                            }

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "customer",
                                    customerValue
                            );

                            values.put(
                                    "province",
                                    provinceValue
                            );

                            values.put(
                                    "city",
                                    cityValue
                            );

                            values.put(
                                    "address",
                                    addressValue
                            );

                            values.put(
                                    "weight",
                                    weightValue
                            );

                            values.put(
                                    "price",
                                    currentPrice
                            );

                            values.put(
                                    "final_price",
                                    0
                            );

                            values.put(
                                    "total",
                                    total
                            );

                            values.put(
                                    "deposit_required",
                                    required
                            );

                            values.put(
                                    "deposit_paid",
                                    depositValue
                            );

                            values.put(
                                    "deposit_status",
                                    depositValue >= required
                                            ? "paid"
                                            : "pending"
                            );

                            values.put(
                                    "transaction_id",
                                    ""
                            );

                            values.put(
                                    "payment_date",
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

                            values.put(
                                    "status",
                                    status
                            );

                            values.put(
                                    "confirmed",
                                    confirmed
                            );

                            long result =
                                    db.getWritableDatabase()
                                            .insert(
                                                    "orders",
                                                    null,
                                                    values
                                            );

                            if (result == -1) {

                                showToast(
                                        "ثبت سفارش انجام نشد."
                                );

                                return;
                            }

                            showToast(
                                    confirmed == 1
                                            ? "سفارش قطعی ثبت شد."
                                            : "سفارش ثبت شد و در انتظار بیعانه است."
                            );

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

        if (total <= 0) {
            return 0;
        }

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT value " +
                                "FROM settings " +
                                "WHERE key='deposit_percent' " +
                                "LIMIT 1",
                                null
                        );

        double percent = 20;

        if (c.moveToFirst()) {

            percent =
                    parseDouble(
                            c.getString(0)
                    );
        }

        c.close();

        return total *
                percent /
                100.0;
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

            showToast(
                    "سفارش پیدا نشد."
            );

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
                        "وضعیت بیعانه سفارش #" +
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

                            double priceValue = 0;

                            if (c.moveToFirst()) {

                                priceValue =
                                        c.getDouble(0);
                            }

                            c.close();

                            double newTotal =
                                    newWeight *
                                    priceValue;

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
        double priceValue = 0;

        if (c.moveToFirst()) {

            weight =
                    c.getDouble(0);

            priceValue =
                    c.getDouble(1);
        }

        c.close();

        double finalTotal =
                weight *
                priceValue;

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
                priceValue
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

            if (image != null) {

                card.addView(
                        image,
                        new LinearLayout.LayoutParams(
                                -1,
                                dp(220)
                        )
                );

                spaceInside(card, 8);
            }

            if (!title.isEmpty()) {

                card.addView(
                        text(
                                title,
                                15,
                                WHITE,
                                Typeface.BOLD
                        )
                );
            }

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
    // SAVE GALLERY MEDIA
    // =========================================================

    private void saveGalleryMedia() {

        if (selectedGalleryUri == null) {

            showToast(
                    "ابتدا عکس یا ویدیو را انتخاب کنید."
            );

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

        if (id == -1) {

            showToast(
                    "ذخیره رسانه انجام نشد."
            );

            return;
        }

        persistUri(
                selectedGalleryUri
        );

        showToast(
                "رسانه به گالری اضافه شد."
        );

        showGallery();
    }

    // =========================================================
    // CREATE MEDIA PREVIEW
    // =========================================================

    private ImageView createMediaPreview(
            String uriString,
            String type
    ) {

        if (uriString == null ||
                uriString.isEmpty()) {

            return null;
        }

        try {

            Uri uri =
                    Uri.parse(
                            uriString
                    );

            ImageView image =
                    new ImageView(this);

            image.setScaleType(
                    ImageView.ScaleType.CENTER_CROP
            );

            if (type != null &&
                    type.startsWith("image")) {

                InputStream input =
                        getContentResolver()
                                .openInputStream(uri);

                if (input != null) {

                    Bitmap bitmap =
                            BitmapFactory.decodeStream(
                                    input
                            );

                    input.close();

                    if (bitmap != null) {

                        image.setImageBitmap(
                                bitmap
                        );

                        return image;
                    }
                }
            }

            image.setImageResource(
                    android.R.drawable.ic_media_play
            );

            return image;

        } catch (Exception e) {

            return null;
        }
    }

    // =========================================================
    // SATISFACTION
    // =========================================================

    private void showSatisfaction() {

        LinearLayout content =
                page("رضایت مشتریان");

        content.addView(
                text(
                        "⭐ رضایت مشتریان ZERIVA",
                        20,
                        GOLD,
                        Typeface.BOLD
                )
        );

        space(content, 10);

        EditText customerName =
                input("نام مشتری");

        EditText customerCity =
                input("شهر");

        EditText message =
                input("متن رضایت مشتری");

        content.addView(
                label("نام مشتری")
        );

        content.addView(
                customerName
        );

        space(content, 6);

        content.addView(
                label("شهر")
        );

        content.addView(
                customerCity
        );

        space(content, 6);

        content.addView(
                label("متن رضایت")
        );

        content.addView(
                message
        );

        space(content, 10);

        Button media =
                addButton(
                        "🖼️ افزودن عکس یا ویدیو",
                        v -> chooseSatisfactionMedia()
                );

        content.addView(media);

        space(content, 8);

        Button save =
                addButton(
                        "💾 ثبت رضایت مشتری",
                        v -> {

                            String customerValue =
                                    cleanText(
                                            customerName
                                                    .getText()
                                                    .toString()
                                    );

                            String cityValue =
                                    cleanText(
                                            customerCity
                                                    .getText()
                                                    .toString()
                                    );

                            String messageValue =
                                    cleanText(
                                            message
                                                    .getText()
                                                    .toString()
                                    );

                            if (customerValue.isEmpty()) {

                                showToast(
                                        "نام مشتری را وارد کنید."
                                );

                                return;
                            }

                            ContentValues values =
                                    new ContentValues();

                            values.put(
                                    "customer_name",
                                    customerValue
                            );

                            values.put(
                                    "city",
                                    cityValue
                            );

                            values.put(
                                    "message",
                                    messageValue
                            );

                            values.put(
                                    "uri",
                                    selectedSatisfactionUri == null
                                            ? ""
                                            : selectedSatisfactionUri.toString()
                            );

                            values.put(
                                    "type",
                                    selectedSatisfactionUri == null
                                            ? ""
                                            : safe(
                                                    getContentResolver()
                                                            .getType(
                                                                    selectedSatisfactionUri
                                                            )
                                              )
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
                            "type TEXT," +
                            "value REAL DEFAULT 0" +
                            ")"
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
            // تنظیمات عمومی
            // -------------------------

            d.execSQL(
                    "CREATE TABLE IF NOT EXISTS settings (" +
                            "key_name TEXT PRIMARY KEY," +
                            "value TEXT" +
                            ")"
            );
        }
    }

    // =========================
    // امنیت نهایی
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
                sp.getBoolean(
                        "enabled",
                        false
                );

        if (!enabled) {
            return true;
        }

        String savedPassword =
                sp.getString(
                        "password",
                        ""
                );

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
