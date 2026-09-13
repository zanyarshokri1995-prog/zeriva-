package com.zeriva.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity {

    // =========================================================
    // رنگ‌های جدید ZERIVA - سبز تیره و لوکس
    // =========================================================
    private static final int DARK_GREEN = Color.rgb(8, 31, 22);
    private static final int GREEN = Color.rgb(18, 57, 39);
    private static final int GREEN_LIGHT = Color.rgb(28, 78, 51);
    private static final int GOLD = Color.rgb(212, 175, 55);
    private static final int WHITE = Color.WHITE;
    private static final int LIGHT = Color.rgb(242, 241, 232);
    private static final int DARK = Color.rgb(25, 30, 27);
    private static final int GRAY = Color.rgb(185, 190, 184);

    private static final String PHONE = "09172172402";
    private static final String INSTAGRAM = "@zeriva_grapes";

    private LinearLayout root;
    private DB db;

    // رسانه انتخاب‌شده برای سفارش
    private Uri selectedMediaUri = null;

    // رسانه انتخاب‌شده برای استوری
    private Uri selectedStoryUri = null;

    // استان‌ها و شهرها
    private final HashMap<String, String[]> provinces = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DB(this);
        loadCities();

        showHome();
    }

    // =========================================================
    // استان‌ها
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
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(dp(15), dp(18), dp(15), dp(30));

        // =====================================================
        // لوگو
        // =====================================================
        TextView logo = text("ZERIVA", 34, GOLD);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setGravity(Gravity.CENTER);

        content.addView(logo, params(-1, 55));

        TextView title = text("زریوار", 21, WHITE);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);

        content.addView(title, params(-1, 38));

        TextView subtitle = text(
                "Shani Grape • Mariwan • Zarivar",
                14,
                LIGHT
        );

        subtitle.setGravity(Gravity.CENTER);
        content.addView(subtitle, params(-1, 35));

        // =====================================================
        // استوری ZERIVA
        // =====================================================
        addStorySection(content);

        // =====================================================
        // کارت اطلاعات
        // =====================================================
        LinearLayout infoCard = new LinearLayout(this);
        infoCard.setOrientation(LinearLayout.VERTICAL);
        infoCard.setGravity(Gravity.CENTER);
        infoCard.setPadding(
                dp(15),
                dp(10),
                dp(15),
                dp(10)
        );

        GradientDrawable infoBackground = new GradientDrawable();
        infoBackground.setColor(GREEN);
        infoBackground.setCornerRadius(dp(18));
        infoBackground.setStroke(dp(1), GOLD);

        infoCard.setBackground(infoBackground);

        TextView infoTitle =
                text("مدیریت کسب‌وکار ZERIVA", 17, GOLD);

        infoTitle.setTypeface(Typeface.DEFAULT_BOLD);
        infoTitle.setGravity(Gravity.CENTER);

        infoCard.addView(infoTitle, params(-1, 35));

        TextView infoText =
                text(
                        "انگور شانی مریوان • دریاچه زریوار",
                        14,
                        LIGHT
                );

        infoText.setGravity(Gravity.CENTER);

        infoCard.addView(infoText, params(-1, 30));

        LinearLayout.LayoutParams infoParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(78)
                );

        infoParams.setMargins(
                0,
                dp(5),
                0,
                dp(18)
        );

        content.addView(infoCard, infoParams);

        // =====================================================
        // منوهای دایره‌ای
        // =====================================================
        LinearLayout row1 = new LinearLayout(this);
        row1.setOrientation(LinearLayout.HORIZONTAL);
        row1.setGravity(Gravity.CENTER);

        row1.addView(
                circleMenu("👥", "مشتریان", v -> showCustomers()),
                circleParams()
        );

        row1.addView(
                circleMenu("💰", "حساب‌ها", v -> showTransactions()),
                circleParams()
        );

        row1.addView(
                circleMenu("🍇", "خرید", v -> showBuyFromFarmer()),
                circleParams()
        );

        content.addView(row1);

        LinearLayout row2 = new LinearLayout(this);
        row2.setOrientation(LinearLayout.HORIZONTAL);
        row2.setGravity(Gravity.CENTER);

        row2.addView(
                circleMenu("🚚", "فروش", v -> showSales()),
                circleParams()
        );

        row2.addView(
                circleMenu("📊", "گزارش‌ها", v -> showReports()),
                circleParams()
        );

        row2.addView(
                circleMenu("⚙", "بیشتر", v -> showMore()),
                circleParams()
        );

        content.addView(row2);

        // =====================================================
        // اطلاعات تماس
        // =====================================================
        addContactFooter(content);

        scroll.addView(content);
        root.addView(scroll);

        setContentView(root);
    }

    // =========================================================
    // بخش استوری
    // =========================================================
    private void addStorySection(LinearLayout content) {

        LinearLayout storyCard = new LinearLayout(this);
        storyCard.setOrientation(LinearLayout.VERTICAL);
        storyCard.setPadding(
                dp(10),
                dp(10),
                dp(10),
                dp(12)
        );

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(GREEN);
        bg.setCornerRadius(dp(18));
        bg.setStroke(dp(1), GOLD);

        storyCard.setBackground(bg);

        TextView title = text(
                "📸 استوری ZERIVA",
                18,
                GOLD
        );

        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);

        storyCard.addView(title, params(-1, 40));

        addButton(
                storyCard,
                "➕ افزودن عکس یا فیلم به استوری",
                v -> showAddStory()
        );

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT id,uri,type FROM stories ORDER BY id DESC",
                null
        );

        if (c.getCount() == 0) {

            TextView empty = text(
                    "هنوز استوری‌ای اضافه نشده است.",
                    14,
                    LIGHT
            );

            empty.setGravity(Gravity.CENTER);

            storyCard.addView(
                    empty,
                    params(-1, 40)
            );

        } else {

            while (c.moveToNext()) {

                int id = c.getInt(0);
                String uriString = c.getString(1);
                String type = c.getString(2);

                addStoryItem(
                        storyCard,
                        id,
                        uriString,
                        type
                );
            }
        }

        c.close();

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        p.setMargins(
                0,
                dp(5),
                0,
                dp(18)
        );

        content.addView(storyCard, p);
    }

    // =========================================================
    // نمایش یک استوری
    // =========================================================
    private void addStoryItem(
            LinearLayout parent,
            int id,
            String uriString,
            String type) {

        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setGravity(Gravity.CENTER_VERTICAL);
        item.setPadding(
                dp(10),
                dp(8),
                dp(10),
                dp(8)
        );

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(GREEN_LIGHT);
        bg.setCornerRadius(dp(14));

        item.setBackground(bg);

        TextView icon = text(
                type.equals("video") ? "🎬" : "📷",
                28,
                WHITE
        );

        icon.setGravity(Gravity.CENTER);

        item.addView(
                icon,
                new LinearLayout.LayoutParams(
                        dp(55),
                        dp(55)
                )
        );

        TextView info = text(
                type.equals("video")
                        ? "ویدیوی ZERIVA"
                        : "عکس ZERIVA",
                15,
                WHITE
        );

        info.setTypeface(Typeface.DEFAULT_BOLD);
        info.setPadding(dp(10), 0, dp(10), 0);

        item.addView(
                info,
                new LinearLayout.LayoutParams(
                        0,
                        dp(55),
                        1
                )
        );

        Button open = new Button(this);
        open.setText("مشاهده");
        open.setTextSize(13);
        open.setTextColor(GOLD);
        open.setAllCaps(false);
        open.setOnClickListener(
                v -> openMedia(uriString, type)
        );

        item.addView(
                open,
                new LinearLayout.LayoutParams(
                        dp(85),
                        dp(50)
                )
        );

        item.setOnClickListener(
                v -> openMedia(uriString, type)
        );

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(75)
                );

        p.setMargins(
                0,
                dp(4),
                0,
                dp(4)
        );

        parent.addView(item, p);
    }

    // =========================================================
    // افزودن استوری
    // =========================================================
    private void showAddStory() {

        LinearLayout page = page("افزودن استوری");

        TextView info = text(
                "یک عکس یا فیلم از گالری گوشی انتخاب کنید.",
                16,
                LIGHT
        );

        info.setGravity(Gravity.CENTER);
        info.setPadding(
                dp(10),
                dp(10),
                dp(10),
                dp(20)
        );

        page.addView(info);

        TextView selected = text(
                "هنوز فایلی انتخاب نشده است.",
                15,
                GRAY
        );

        selected.setGravity(Gravity.CENTER);

        page.addView(
                selected,
                params(-1, 55)
        );

        addButton(
                page,
                "📷 انتخاب عکس یا فیلم",
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
                            2001
                    );
                }
        );

        addButton(
                page,
                "💾 انتشار استوری",
                v -> {

                    if (selectedStoryUri == null) {

                        toast(
                                "ابتدا یک عکس یا فیلم انتخاب کنید"
                        );

                        return;
                    }

                    String uri =
                            selectedStoryUri.toString();

                    String type =
                            getContentResolver()
                                    .getType(
                                            selectedStoryUri
                                    );

                    if (type == null) {
                        type = "";
                    }

                    String mediaType =
                            type.startsWith("video")
                                    ? "video"
                                    : "image";

                    db.getWritableDatabase()
                            .execSQL(
                                    "INSERT INTO stories(uri,type) VALUES(?,?)",
                                    new Object[]{
                                            uri,
                                            mediaType
                                    }
                            );

                    toast(
                            "استوری ZERIVA اضافه شد"
                    );

                    selectedStoryUri = null;

                    showHome();
                }
        );

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // باز کردن عکس یا فیلم
    // =========================================================
    private void openMedia(
            String uriString,
            String type) {

        try {

            Uri uri = Uri.parse(uriString);

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW
                    );

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

            toast(
                    "امکان باز کردن فایل وجود ندارد"
            );
        }
    }

    // =========================================================
    // دریافت عکس/فیلم
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

        if (resultCode != RESULT_OK ||
                data == null ||
                data.getData() == null) {
            return;
        }

        Uri uri = data.getData();

        if (requestCode == 2001) {

            selectedStoryUri = uri;

            toast(
                    "فایل استوری انتخاب شد"
            );

        } else if (requestCode == 1001) {

            selectedMediaUri = uri;

            toast(
                    "عکس یا فیلم سفارش انتخاب شد"
            );
        }
    }

    // =========================================================
    // اطلاعات تماس پایین صفحه
    // =========================================================
    private void addContactFooter(
            LinearLayout content) {

        Space space = new Space(this);

        content.addView(
                space,
                new LinearLayout.LayoutParams(
                        1,
                        dp(15)
                )
        );

        TextView line = text(
                "────────────────────",
                12,
                GOLD
        );

        line.setGravity(Gravity.CENTER);

        content.addView(
                line,
                params(-1, 25)
        );

        TextView contactTitle = text(
                "ارتباط با ZERIVA",
                16,
                GOLD
        );

        contactTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        contactTitle.setGravity(Gravity.CENTER);

        content.addView(
                contactTitle,
                params(-1, 38)
        );

        TextView phone = text(
                "📞 09172172402",
                16,
                WHITE
        );

        phone.setGravity(Gravity.CENTER);
        phone.setTypeface(Typeface.DEFAULT_BOLD);

        phone.setOnClickListener(
                v -> openWhatsApp()
        );

        content.addView(
                phone,
                params(-1, 40)
        );

        TextView whatsapp = text(
                "💬 واتساپ",
                14,
                LIGHT
        );

        whatsapp.setGravity(Gravity.CENTER);

        whatsapp.setOnClickListener(
                v -> openWhatsApp()
        );

        content.addView(
                whatsapp,
                params(-1, 35)
        );

        TextView instagram = text(
                "📷 Instagram: " + INSTAGRAM,
                15,
                WHITE
        );

        instagram.setGravity(Gravity.CENTER);
        instagram.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        instagram.setOnClickListener(
                v -> openInstagram()
        );

        content.addView(
                instagram,
                params(-1, 42)
        );

        TextView copyright = text(
                "ZERIVA • Premium Shani Grape\nMariwan • Zarivar",
                12,
                GRAY
        );

        copyright.setGravity(Gravity.CENTER);

        content.addView(
                copyright,
                params(-1, 65)
        );
    }

    // =========================================================
    // واتساپ
    // =========================================================
    private void openWhatsApp() {

        try {

            String phone =
                    "98" + PHONE.substring(1);

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                    "https://wa.me/" + phone
                            )
                    );

            startActivity(intent);

        } catch (Exception e) {

            toast(
                    "واتساپ در دسترس نیست"
            );
        }
    }

    // =========================================================
    // اینستاگرام
    // =========================================================
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

            toast(
                    "اینستاگرام در دسترس نیست"
            );
        }
    }

    // =========================================================
    // منوی دایره‌ای
    // =========================================================
    private LinearLayout circleMenu(
            String icon,
            String title,
            View.OnClickListener listener) {

        LinearLayout box = new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        box.setGravity(
                Gravity.CENTER
        );

        TextView circle = new TextView(this);

        circle.setText(icon);
        circle.setTextSize(27);
        circle.setGravity(
                Gravity.CENTER
        );
        circle.setTextColor(WHITE);

        GradientDrawable circleBackground =
                new GradientDrawable();

        circleBackground.setShape(
                GradientDrawable.OVAL
        );

        circleBackground.setColor(
                GREEN
        );

        circleBackground.setStroke(
                dp(2),
                GOLD
        );

        circle.setBackground(
                circleBackground
        );

        circle.setElevation(
                dp(4)
        );

        box.addView(
                circle,
                new LinearLayout.LayoutParams(
                        dp(82),
                        dp(82)
                )
        );

        TextView label =
                text(title, 14, LIGHT);

        label.setGravity(
                Gravity.CENTER
        );

        label.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        box.addView(
                label,
                new LinearLayout.LayoutParams(
                        dp(92),
                        dp(35)
                )
        );

        box.setOnClickListener(listener);
        circle.setOnClickListener(listener);
        label.setOnClickListener(listener);

        return box;
    }

    private LinearLayout.LayoutParams circleParams() {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        dp(130),
                        1
                );

        p.setMargins(
                dp(3),
                dp(5),
                dp(3),
                dp(5)
        );

        return p;
    }

    // =========================================================
    // بیشتر
    // =========================================================
    private void showMore() {

        LinearLayout page = page("بیشتر");

        TextView info = text(
                "امکانات بیشتر ZERIVA در نسخه‌های آینده اضافه می‌شوند.",
                16,
                LIGHT
        );

        info.setGravity(Gravity.CENTER);
        info.setPadding(
                dp(10),
                dp(20),
                dp(10),
                dp(20)
        );

        page.addView(info);

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // مشتریان
    // =========================================================
    private void showCustomers() {

        LinearLayout page = page("مشتریان");

        addButton(
                page,
                "➕ ثبت مشتری جدید",
                v -> addCustomer()
        );

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT id,name,phone FROM customers ORDER BY id ASC",
                null
        );

        if (c.getCount() == 0) {

            page.addView(
                    text(
                            "هنوز مشتری ثبت نشده است.",
                            16,
                            LIGHT
                    )
            );
        }

        while (c.moveToNext()) {

            int id = c.getInt(0);
            String name = c.getString(1);
            String phone = c.getString(2);

            TextView item =
                    text(
                            "شماره مشتری: " + id +
                                    "\nنام: " + name +
                                    "\nتماس: " + phone,
                            16,
                            DARK
                    );

            item.setPadding(
                    dp(15),
                    dp(15),
                    dp(15),
                    dp(15)
            );

            item.setBackgroundColor(
                    Color.WHITE
            );

            LinearLayout.LayoutParams p =
                    new LinearLayout.LayoutParams(
                            -1,
                            -2
                    );

            p.setMargins(
                    0,
                    dp(5),
                    0,
                    dp(5)
            );

            page.addView(item, p);
        }

        c.close();

        addBack(page);

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

        addButton(
                page,
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

                        toast(
                                "نام مشتری را وارد کنید"
                        );

                        return;
                    }

                    db.getWritableDatabase()
                            .execSQL(
                                    "INSERT INTO customers(name,phone) VALUES(?,?)",
                                    new Object[]{
                                            n,
                                            p
                                    }
                            );

                    toast(
                            "مشتری با موفقیت ثبت شد"
                    );

                    showCustomers();
                }
        );

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // خرید از باغدار
    // =========================================================
    private void showBuyFromFarmer() {

        LinearLayout page =
                page("خرید از باغدار");

        EditText farmer =
                input("نام باغدار");

        EditText weight =
                input("وزن خرید (کیلو)");

        EditText price =
                input("قیمت هر کیلو");

        page.addView(farmer);
        page.addView(weight);
        page.addView(price);

        addButton(
                page,
                "💾 ثبت خرید",
                v -> {

                    if (farmer.getText()
                            .toString()
                            .trim()
                            .isEmpty()) {

                        toast(
                                "نام باغدار را وارد کنید"
                        );

                        return;
                    }

                    double w =
                            number(weight);

                    double pr =
                            number(price);

                    if (w <= 0) {

                        toast(
                                "وزن را وارد کنید"
                        );

                        return;
                    }

                    double total =
                            w * pr;

                    db.getWritableDatabase()
                            .execSQL(
                                    "INSERT INTO purchases(farmer,weight,price,total) VALUES(?,?,?,?)",
                                    new Object[]{
                                            farmer.getText()
                                                    .toString()
                                                    .trim(),
                                            w,
                                            pr,
                                            total
                                    }
                            );

                    toast(
                            "خرید ثبت شد\nمبلغ: " +
                                    money(total)
                    );

                    showHome();
                }
        );

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // فروش و ارسال
    // =========================================================
    private void showSales() {

        selectedMediaUri = null;

        LinearLayout page =
                page("فروش و ارسال");

        final Spinner province =
                spinner(
                        new ArrayList<>(
                                provinces.keySet()
                        )
                );

        final Spinner city =
                new Spinner(this);

        EditText customer =
                input("نام مشتری");

        EditText weight =
                input("وزن سفارش (کیلو)");

        EditText price =
                input("قیمت هر کیلو");

        TextView totalText =
                text(
                        "مبلغ کل: 0 تومان",
                        18,
                        GOLD
                );

        totalText.setGravity(
                Gravity.CENTER
        );

        // =====================================================
        // استان
        // =====================================================
        page.addView(
                label("استان")
        );

        page.addView(
                province,
                spinnerParams()
        );

        // =====================================================
        // شهر
        // =====================================================
        page.addView(
                label("شهر")
        );

        page.addView(
                city,
                spinnerParams()
        );

        if (province.getSelectedItem() != null) {

            updateCities(
                    city,
                    province.getSelectedItem()
                            .toString()
            );
        }

        province.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        updateCities(
                                city,
                                province
                                        .getItemAtPosition(position)
                                        .toString()
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                }
        );

        page.addView(customer);

        page.addView(
                label("وزن سفارش")
        );

        page.addView(weight);

        // =====================================================
        // کنترل وزن
        // =====================================================
        LinearLayout weightRow =
                new LinearLayout(this);

        weightRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        weightRow.setGravity(
                Gravity.CENTER
        );

        Button minus =
                new Button(this);

        minus.setText("−");
        minus.setTextSize(22);
        minus.setAllCaps(false);

        Button plus =
                new Button(this);

        plus.setText("+");
        plus.setTextSize(22);
        plus.setAllCaps(false);

        weightRow.addView(
                minus,
                params(80, 55)
        );

        weightRow.addView(
                plus,
                params(80, 55)
        );

        page.addView(weightRow);

        page.addView(price);
        page.addView(totalText);

        View.OnClickListener calculate =
                v -> {

                    double w =
                            number(weight);

                    double p =
                            number(price);

                    totalText.setText(
                            "مبلغ کل: " +
                                    money(w * p)
                    );
                };

        minus.setOnClickListener(
                v -> {

                    double w =
                            number(weight);

                    w -= 100;

                    if (w < 0) {
                        w = 0;
                    }

                    weight.setText(
                            formatNumber(w)
                    );

                    calculate.onClick(v);
                }
        );

        plus.setOnClickListener(
                v -> {

                    double w =
                            number(weight);

                    w += 100;

                    weight.setText(
                            formatNumber(w)
                    );

                    calculate.onClick(v);
                }
        );

        // =====================================================
        // عکس / فیلم بار
        // =====================================================
        TextView mediaTitle =
                text(
                        "📸 عکس / فیلم بار",
                        17,
                        GOLD
                );

        mediaTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        mediaTitle.setPadding(
                0,
                dp(15),
                0,
                dp(5)
        );

        page.addView(mediaTitle);

        TextView mediaStatus =
                text(
                        "هنوز عکس یا فیلمی انتخاب نشده است.",
                        14,
                        LIGHT
                );

        mediaStatus.setGravity(
                Gravity.CENTER
        );

        page.addView(
                mediaStatus,
                params(-1, 45)
        );

        addButton(
                page,
                "📷 انتخاب عکس یا فیلم",
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

        // =====================================================
        // ثبت سفارش
        // =====================================================
        addButton(
                page,
                "📦 ثبت سفارش",
                v -> {

                    String cust =
                            customer.getText()
                                    .toString()
                                    .trim();

                    if (cust.isEmpty()) {

                        toast(
                                "نام مشتری را وارد کنید"
                        );

                        return;
                    }

                    double w =
                            number(weight);

                    double p =
                            number(price);

                    if (w <= 0) {

                        toast(
                                "وزن سفارش را وارد کنید"
                        );

                        return;
                    }

                    if (province.getSelectedItem() == null ||
                            city.getSelectedItem() == null) {

                        toast(
                                "استان و شهر را انتخاب کنید"
                        );

                        return;
                    }

                    String selectedProvince =
                            province
                                    .getSelectedItem()
                                    .toString();

                    String selectedCity =
                            city
                                    .getSelectedItem()
                                    .toString();

                    double total =
                            w * p;

                    String mediaUri =
                            selectedMediaUri == null
                                    ? ""
                                    : selectedMediaUri.toString();

                    db.getWritableDatabase()
                            .execSQL(
                                    "INSERT INTO orders(" +
                                            "customer,province,city,weight,price,total,media_uri" +
                                            ") VALUES(?,?,?,?,?,?,?)",
                                    new Object[]{
                                            cust,
                                            selectedProvince,
                                            selectedCity,
                                            w,
                                            p,
                                            total,
                                            mediaUri
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
                }
        );

        addButton(
                page,
                "📊 جمع سفارش شهرها",
                v -> showCityOrders()
        );

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // شهرها
    // =========================================================
    private void updateCities(
            Spinner citySpinner,
            String province) {

        String[] cities =
                provinces.get(province);

        if (cities == null) {

            cities =
                    new String[]{
                            "انتخاب شهر"
                    };
        }

        ArrayList<String> values =
                new ArrayList<>();

        for (String city : cities) {
            values.add(city);
        }

        citySpinner.setAdapter(
                createSpinnerAdapter(values)
        );
    }

    // =========================================================
    // جمع سفارش شهرها
    // =========================================================
    private void showCityOrders() {

        LinearLayout page =
                page("جمع سفارش شهرها");

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT city,SUM(weight),COUNT(*) " +
                                        "FROM orders " +
                                        "GROUP BY city " +
                                        "ORDER BY city",
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

                String city =
                        c.getString(0);

                double weight =
                        c.getDouble(1);

                int count =
                        c.getInt(2);

                TextView item =
                        text(
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

                item.setBackgroundColor(
                        Color.WHITE
                );

                LinearLayout.LayoutParams p =
                        new LinearLayout.LayoutParams(
                                -1,
                                -2
                        );

                p.setMargins(
                        0,
                        dp(5),
                        0,
                        dp(5)
                );

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

        LinearLayout page =
                page("حساب‌ها و معاملات");

        addButton(
                page,
                "➕ ثبت معامله",
                v -> addTransaction()
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT person,description,debt,payment " +
                                        "FROM transactions " +
                                        "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            page.addView(
                    text(
                            "هنوز معامله‌ای ثبت نشده است.",
                            16,
                            LIGHT
                    )
            );
        }

        while (c.moveToNext()) {

            String person =
                    c.getString(0);

            String description =
                    c.getString(1);

            double debt =
                    c.getDouble(2);

            double payment =
                    c.getDouble(3);

            page.addView(
                    text(
                            "طرف حساب: " +
                                    person +
                                    "\nشرح: " +
                                    description +
                                    "\nبدهکار: " +
                                    money(debt) +
                                    "\nپرداخت: " +
                                    money(payment),
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

        LinearLayout page =
                page("ثبت معامله");

        EditText person =
                input("نام طرف حساب");

        EditText description =
                input("شرح معامله");

        EditText debt =
                input("بدهکار");

        EditText payment =
                input("پرداخت");

        page.addView(person);
        page.addView(description);
        page.addView(debt);
        page.addView(payment);

        addButton(
                page,
                "💾 ذخیره معامله",
                v -> {

                    db.getWritableDatabase()
                            .execSQL(
                                    "INSERT INTO transactions(" +
                                            "person,description,debt,payment" +
                                            ") VALUES(?,?,?,?)",
                                    new Object[]{
                                            person.getText()
                                                    .toString()
                                                    .trim(),
                                            description.getText()
                                                    .toString()
                                                    .trim(),
                                            number(debt),
                                            number(payment)
                                    }
                            );

                    toast(
                            "معامله ذخیره شد"
                    );

                    showTransactions();
                }
        );

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // گزارش‌ها
    // =========================================================
    private void showReports() {

        LinearLayout page =
                page("گزارش‌ها");

        SQLiteDatabase database =
                db.getReadableDatabase();

        Cursor orders =
                database.rawQuery(
                        "SELECT COUNT(*),COALESCE(SUM(weight),0),COALESCE(SUM(total),0) " +
                                "FROM orders",
                        null
                );

        if (orders.moveToFirst()) {

            int count =
                    orders.getInt(0);

            double weight =
                    orders.getDouble(1);

            double total =
                    orders.getDouble(2);

            page.addView(
                    text(
                            "📦 سفارش‌ها\n" +
                                    "تعداد: " +
                                    count +
                                    "\nوزن کل: " +
                                    formatNumber(weight) +
                                    " کیلو" +
                                    "\nمبلغ کل: " +
                                    money(total),
                            17,
                            LIGHT
                    )
            );
        }

        orders.close();

        Cursor purchases =
                database.rawQuery(
                        "SELECT COUNT(*),COALESCE(SUM(weight),0),COALESCE(SUM(total),0) " +
                                "FROM purchases",
                        null
                );

        if (purchases.moveToFirst()) {

            int count =
                    purchases.getInt(0);

            double weight =
                    purchases.getDouble(1);

            double total =
                    purchases.getDouble(2);

            page.addView(
                    text(
                            "\n🍇 خرید از باغدار\n" +
                                    "تعداد خرید: " +
                                    count +
                                    "\nوزن کل: " +
                                    formatNumber(weight) +
                                    " کیلو" +
                                    "\nمبلغ کل: " +
                                    money(total),
                            17,
                            LIGHT
                    )
            );
        }

        purchases.close();

        addButton(
                page,
                "🏙 گزارش سفارش هر شهر",
                v -> showCityOrders()
        );

        addBack(page);

        setPage(page);
    }

    // =========================================================
    // صفحه
    // =========================================================
    private LinearLayout page(
            String title) {

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                dp(18),
                dp(20),
                dp(18),
                dp(30)
        );

        layout.setBackgroundColor(
                DARK_GREEN
        );

        TextView titleView =
                text(
                        title,
                        25,
                        GOLD
                );

        titleView.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        titleView.setGravity(
                Gravity.CENTER
        );

        layout.addView(
                titleView,
                params(-1, 65)
        );

        return layout;
    }

    // =========================================================
    // نمایش صفحه
    // =========================================================
    private void setPage(
            LinearLayout page) {

        ScrollView scroll =
                new ScrollView(this);

        scroll.setFillViewport(true);

        scroll.addView(page);

        setContentView(scroll);
    }

    // =========================================================
    // بازگشت
    // =========================================================
    private void addBack(
            LinearLayout layout) {

        addButton(
                layout,
                "⬅ بازگشت",
                v -> showHome()
        );
    }

    // =========================================================
    // دکمه
    // =========================================================
    private void addButton(
            LinearLayout layout,
            String title,
            View.OnClickListener listener) {

        Button button =
                new Button(this);

        button.setText(title);
        button.setTextSize(16);
        button.setTextColor(GOLD);
        button.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        button.setAllCaps(false);

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(GREEN);
        bg.setCornerRadius(dp(12));
        bg.setStroke(dp(1), GOLD);

        button.setBackground(bg);

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

    // =========================================================
    // برچسب
    // =========================================================
    private TextView label(
            String s) {

        TextView t =
                text(
                        s,
                        17,
                        GOLD
                );

        t.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        t.setPadding(
                dp(5),
                dp(10),
                dp(5),
                dp(5)
        );

        return t;
    }

    // =========================================================
    // TextView
    // =========================================================
    private TextView text(
            String value,
            float size,
            int color) {

        TextView t =
                new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);

        t.setGravity(
                Gravity.CENTER_VERTICAL
        );

        return t;
    }

    // =========================================================
    // EditText
    // =========================================================
    private EditText input(
            String hint) {

        EditText e =
                new EditText(this);

        e.setHint(hint);
        e.setTextSize(16);
        e.setTextColor(WHITE);
        e.setHintTextColor(
                Color.rgb(205, 210, 205)
        );

        e.setSingleLine(true);

        e.setPadding(
                dp(12),
                dp(8),
                dp(12),
                dp(8)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(
                Color.rgb(15, 45, 31)
        );

        bg.setCornerRadius(
                dp(10)
        );

        bg.setStroke(
                dp(1),
                Color.rgb(90, 120, 100)
        );

        e.setBackground(bg);

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

    // =========================================================
    // Spinner
    // =========================================================
    private Spinner spinner(
            ArrayList<String> values) {

        Spinner spinner =
                new Spinner(this);

        spinner.setAdapter(
                createSpinnerAdapter(values)
        );

        return spinner;
    }

    private ArrayAdapter<String> createSpinnerAdapter(
            ArrayList<String> values) {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_item,
                        values
                ) {

                    @Override
                    public View getView(
                            int position,
                            View convertView,
                            android.view.ViewGroup parent) {

                        TextView v =
                                (TextView) super.getView(
                                        position,
                                        convertView,
                                        parent
                                );

                        v.setTextColor(WHITE);
                        v.setTextSize(17);
                        v.setGravity(
                                Gravity.CENTER_VERTICAL
                        );
                        v.setPadding(
                                dp(12),
                                0,
                                dp(12),
                                0
                        );

                        return v;
                    }

                    @Override
                    public View getDropDownView(
                            int position,
                            View convertView,
                            android.view.ViewGroup parent) {

                        TextView v =
                                (TextView) super.getDropDownView(
                                        position,
                                        convertView,
                                        parent
                                );

                        v.setTextColor(DARK);
                        v.setTextSize(17);
                        v.setGravity(Gravity.CENTER);
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

        return adapter;
    }

    private LinearLayout.LayoutParams spinnerParams() {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(60)
                );

        p.setMargins(
                0,
                dp(3),
                0,
                dp(8)
        );

        return p;
    }

    // =========================================================
    // Layout Params
    // =========================================================
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

    // =========================================================
    // تبدیل عدد
    // =========================================================
    private double number(
            EditText editText) {

        try {

            String s =
                    editText.getText()
                            .toString()
                            .replace(",", "")
                            .replace("٬", "")
                            .trim();

            if (s.isEmpty()) {
                return 0;
            }

            s = s
                    .replace("۰", "0")
                    .replace
