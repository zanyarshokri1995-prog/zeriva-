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
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

    private Uri selectedStoryUri = null;
    private Uri selectedGalleryUri = null;
    private Uri selectedSatisfactionUri = null;
    private Uri selectedVoiceUri = null;

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
                        "تبریز",
                        "مراغه",
                        "مرند",
                        "میانه",
                        "اهر"
                });

        provinces.put("آذربایجان غربی",
                new String[]{
                        "ارومیه",
                        "خوی",
                        "مهاباد",
                        "بوکان",
                        "پیرانشهر"
                });

        provinces.put("اردبیل",
                new String[]{
                        "اردبیل",
                        "پارس‌آباد",
                        "مشگین‌شهر",
                        "خلخال"
                });

        provinces.put("اصفهان",
                new String[]{
                        "اصفهان",
                        "کاشان",
                        "نجف‌آباد",
                        "خمینی‌شهر"
                });

        provinces.put("البرز",
                new String[]{
                        "کرج",
                        "نظرآباد",
                        "هشتگرد"
                });

        provinces.put("تهران",
                new String[]{
                        "تهران",
                        "ری",
                        "شهریار",
                        "اسلامشهر"
                });

        provinces.put("خوزستان",
                new String[]{
                        "اهواز",
                        "آبادان",
                        "خرمشهر",
                        "دزفول",
                        "ماهشهر"
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
                        "کرمانشاه",
                        "اسلام‌آباد غرب",
                        "جوانرود",
                        "پاوه"
                });

        provinces.put("همدان",
                new String[]{
                        "همدان",
                        "ملایر",
                        "نهاوند",
                        "تویسرکان"
                });

        provinces.put("فارس",
                new String[]{
                        "شیراز",
                        "مرودشت",
                        "جهرم",
                        "فسا"
                });

        provinces.put("قم",
                new String[]{
                        "قم"
                });

        provinces.put("گیلان",
                new String[]{
                        "رشت",
                        "انزلی",
                        "لاهیجان",
                        "رودسر"
                });

        provinces.put("مازندران",
                new String[]{
                        "ساری",
                        "بابل",
                        "آمل",
                        "قائم‌شهر"
                });

        provinces.put("مرکزی",
                new String[]{
                        "اراک",
                        "ساوه",
                        "خمین"
                });

        provinces.put("قزوین",
                new String[]{
                        "قزوین",
                        "تاکستان"
                });

        provinces.put("یزد",
                new String[]{
                        "یزد",
                        "میبد"
                });

        provinces.put("کرمان",
                new String[]{
                        "کرمان",
                        "رفسنجان",
                        "سیرجان"
                });

        provinces.put("هرمزگان",
                new String[]{
                        "بندرعباس",
                        "میناب",
                        "قشم"
                });

        provinces.put("سیستان و بلوچستان",
                new String[]{
                        "زاهدان",
                        "چابهار",
                        "زابل"
                });

        provinces.put("لرستان",
                new String[]{
                        "خرم‌آباد",
                        "بروجرد",
                        "دورود"
                });

        provinces.put("گلستان",
                new String[]{
                        "گرگان",
                        "گنبدکاووس"
                });

        provinces.put("سمنان",
                new String[]{
                        "سمنان",
                        "شاهرود",
                        "دامغان"
                });

        provinces.put("زنجان",
                new String[]{
                        "زنجان",
                        "ابهر"
                });

        provinces.put("ایلام",
                new String[]{
                        "ایلام",
                        "دهلران",
                        "مهران"
                });

        provinces.put("چهارمحال و بختیاری",
                new String[]{
                        "شهرکرد",
                        "بروجن",
                        "فارسان"
                });

        provinces.put("خراسان رضوی",
                new String[]{
                        "مشهد",
                        "نیشابور",
                        "سبزوار"
                });

        provinces.put("خراسان شمالی",
                new String[]{
                        "بجنورد",
                        "شیروان"
                });

        provinces.put("خراسان جنوبی",
                new String[]{
                        "بیرجند",
                        "قائن",
                        "طبس"
                });

        provinces.put("کهگیلویه و بویراحمد",
                new String[]{
                        "یاسوج",
                        "دهدشت"
                });

        provinces.put("بوشهر",
                new String[]{
                        "بوشهر",
                        "دشتستان",
                        "گناوه"
                });
    }

    // =========================================================
    // صفحه اصلی
    // =========================================================

    private void showHome() {

        root = new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        root.setBackgroundColor(
                DARK_GREEN
        );

        ScrollView scroll =
                new ScrollView(this);

        scroll.setFillViewport(true);

        LinearLayout content =
                new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                dp(16),
                dp(20),
                dp(16),
                dp(30)
        );

        scroll.addView(content);

        root.addView(
                scroll,
                params(-1, -1)
        );

        setContentView(root);

        // لوگو
        TextView logo =
                text(
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

        // نام برنامه
        TextView title =
                text(
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

        TextView subtitle =
                text(
                        "Shani Grape • Mariwan • Zarivar",
                        14,
                        GOLD,
                        Gravity.CENTER
                );

        content.addView(
                subtitle,
                params(-1, -2)
        );

        space(content, 12);

        // تاریخ امروز
        TextView date =
                text(
                        "امروز: " + today(),
                        15,
                        LIGHT,
                        Gravity.CENTER
                );

        date.setPadding(
                dp(12),
                dp(10),
                dp(12),
                dp(10)
        );

        date.setBackground(
                rounded(
                        GREEN,
                        15,
                        GOLD
                )
        );

        content.addView(
                date,
                params(-1, -2)
        );

        space(content, 12);

        // استوری
        addStorySection(content);

        space(content, 12);

        // قیمت روز
        TextView dailyPrice =
                text(
                        "🍇 قیمت روز انگور: "
                                + money(
                                currentDailyPrice()
                        ),
                        17,
                        GOLD,
                        Gravity.CENTER
                );

        dailyPrice.setPadding(
                dp(14),
                dp(14),
                dp(14),
                dp(14)
        );

        dailyPrice.setBackground(
                rounded(
                        GREEN,
                        18,
                        GOLD
                )
        );

        content.addView(
                dailyPrice,
                params(-1, -2)
        );

        space(content, 12);

        TextView info =
                text(
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

        TextView menuTitle =
                text(
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
        addMenuRow(
                content,
                "👥\nمشتریان",
                "💰\nحساب‌ها",
                v -> showCustomers(),
                v -> showTransactions()
        );

        space(content, 10);

        // ردیف دوم
        addMenuRow(
                content,
                "🍇\nخرید",
                "🚚\nفروش",
                v -> showBuyFromFarmer(),
                v -> showSales()
        );

        space(content, 10);

        // ردیف سوم
        addMenuRow(
                content,
                "💵\nقیمت روز",
                "📦\nسفارش بار",
                v -> showDailyPrice(),
                v -> showOrders()
        );

        space(content, 10);

        // ردیف چهارم
        addMenuRow(
                content,
                "📊\nگزارش‌ها",
                "🖼️\nگالری",
                v -> showReports(),
                v -> showGallery()
        );

        space(content, 10);

        // ردیف پنجم
        addMenuRow(
                content,
                "⭐\nرضایت مشتری",
                "💬\nچت",
                v -> showSatisfaction(),
                v -> showChat()
        );

        space(content, 10);

        // ردیف ششم
        addMenuRow(
                content,
                "⚙️\nمدیریت",
                "📚\nآرشیو استوری",
                v -> showManagement(),
                v -> showStoryArchive()
        );

        space(content, 25);

        addContactFooter(content);
    }

    // =========================================================
    // ردیف منو
    // =========================================================

    private void addMenuRow(
            LinearLayout parent,
            String first,
            String second,
            View.OnClickListener firstListener,
            View.OnClickListener secondListener) {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setGravity(
                Gravity.CENTER
        );

        row.addView(
                circleMenu(
                        "",
                        first,
                        firstListener
                ),
                new LinearLayout.LayoutParams(
                        0,
                        dp(120),
                        1
                )
        );

        row.addView(
                circleMenu(
                        "",
                        second,
                        secondListener
                ),
                new LinearLayout.LayoutParams(
                        0,
                        dp(120),
                        1
                )
        );

        parent.addView(
                row,
                params(-1, -2)
        );
    }

    // =========================================================
    // استوری
    // =========================================================

    private void addStorySection(
            LinearLayout content) {

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
                rounded(
                        GREEN,
                        20,
                        GOLD
                )
        );

        TextView title =
                text(
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

        Button add =
                addButton(
                        "➕ افزودن عکس یا فیلم به استوری",
                        v -> showAddStory()
                );

        card.addView(
                add,
                params(-1, -2)
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,uri,type "
                                        + "FROM stories "
                                        + "WHERE archived=0 "
                                        + "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            TextView empty =
                    text(
                            "هنوز استوری‌ای اضافه نشده است.",
                            14,
                            GRAY,
                            Gravity.CENTER
                    );

            empty.setPadding(
                    dp(8),
                    dp(12),
                    dp(8),
                    dp(8)
            );

            card.addView(
                    empty,
                    params(-1, -2)
            );

        } else {

            while (c.moveToNext()) {

                int id =
                        c.getInt(0);

                String uri =
                        c.getString(1);

                String type =
                        c.getString(2);

                addStoryItem(
                        card,
                        id,
                        uri,
                        type,
                        false
                );
            }
        }

        c.close();

        card.addView(
                addButton(
                        "📚 مشاهده آرشیو استوری",
                        v -> showStoryArchive()
                ),
                params(-1, -2)
        );

        content.addView(
                card,
                params(-1, -2)
        );
    }

    private void addStoryItem(
            LinearLayout parent,
            int id,
            String uriString,
            String type,
            boolean archived) {

        LinearLayout item =
                new LinearLayout(this);

        item.setGravity(
                Gravity.CENTER_VERTICAL
        );

        String title;

        if (archived) {

            title =
                    type.equals("video")
                            ? "🗃️ ویدیوی آرشیوی"
                            : "🗃️ عکس آرشیوی";

        } else {

            title =
                    type.equals("video")
                            ? "🎬 ویدیوی ZERIVA"
                            : "📷 عکس ZERIVA";
        }

        TextView t =
                text(
                        title,
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

        Button view =
                addButton(
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

        // حذف واقعی نداریم؛ به آرشیو منتقل می‌کنیم.
        if (!archived) {

            item.setOnLongClickListener(
                    v -> {

                        new AlertDialog.Builder(this)
                                .setTitle(
                                        "انتقال به آرشیو"
                                )
                                .setMessage(
                                        "این استوری از صفحه اصلی برداشته و به آرشیو منتقل شود؟"
                                )
                                .setNegativeButton(
                                        "لغو",
                                        null
                                )
                                .setPositiveButton(
                                        "انتقال",
                                        (dialog, which) -> {

                                            db.getWritableDatabase()
                                                    .execSQL(
                                                            "UPDATE stories "
                                                                    + "SET archived=1 "
                                                                    + "WHERE id=?",
                                                            new Object[]{
                                                                    id
                                                            }
                                                    );

                                            showHome();
                                        }
                                )
                                .show();

                        return true;
                    }
            );
        }

        parent.addView(
                item,
                params(-1, -2)
        );
    }

    // =========================================================
    // آرشیو استوری
    // =========================================================

    private void showStoryArchive() {

        LinearLayout page =
                page("آرشیو استوری");

        addBack(page);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,uri,type "
                                        + "FROM stories "
                                        + "WHERE archived=1 "
                                        + "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            page.addView(
                    infoCard(
                            "آرشیو استوری خالی است."
                    ),
                    params(-1, -2)
            );

        } else {

            while (c.moveToNext()) {

                addStoryItem(
                        page,
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        true
                );
            }
        }

        c.close();
    }

    // =========================================================
    // افزودن استوری
    // =========================================================

    private void showAddStory() {

        selectedStoryUri = null;

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

    private void saveStory() {

        if (selectedStoryUri == null) {
            return;
        }

        String type =
                getContentResolver()
                        .getType(
                                selectedStoryUri
                        );

        String mediaType =
                type != null &&
                        type.startsWith("video")
                        ? "video"
                        : "image";

        persistUri(
                selectedStoryUri
        );

        db.getWritableDatabase()
                .execSQL(
                        "INSERT INTO stories"
                                + "(uri,type,created_at,archived)"
                                + " VALUES(?,?,?,0)",
                        new Object[]{
                                selectedStoryUri.toString(),
                                mediaType,
                                System.currentTimeMillis()
                        }
                );

        Toast.makeText(
                this,
                "استوری منتشر شد.",
                Toast.LENGTH_SHORT
        ).show();

        showHome();
    }

    // =========================================================
    // مشتریان
    // =========================================================

    private void showCustomers() {

        LinearLayout page =
                page("مشتریان");

        addBack(page);

        Button add =
                addButton(
                        "➕ افزودن مشتری",
                        v -> addCustomer()
                );

        page.addView(
                add,
                params(-1, -2)
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,name,phone "
                                        + "FROM customers "
                                        + "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            page.addView(
                    infoCard(
                            "هنوز مشتری‌ای ثبت نشده است."
                    ),
                    params(-1, -2)
            );
        }

        while (c.moveToNext()) {

            String cardText =
                    "👤 "
                            + c.getString(1)
                            + "\n"
                            + "شماره مشتری: "
                            + c.getInt(0)
                            + "\n"
                            + "📞 "
                            + safe(
                            c.getString(2)
                    );

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
                verticalBox();

        box.addView(name);
        box.addView(phone);

        new AlertDialog.Builder(this)
                .setTitle(
                        "مشتری جدید"
                )
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
                input("وزن خرید (کیلو)");

        EditText price =
                input("قیمت هر کیلو");

        page.addView(farmer);
        page.addView(weight);
        page.addView(price);

        TextView total =
                infoCard("مبلغ کل: 0");

        page.addView(
                total,
                params(-1, -2)
        );

        Button calculate =
                addButton(
                        "🧮 محاسبه مبلغ",
                        v -> {

                            double w =
                                    number(
                                            weight.getText().toString()
                                    );

                            double p =
                                    number(
                                            price.getText().toString()
                                    );

                            double result =
                                    w * p;

                            total.setText(
                                    "مبلغ کل: "
                                            + money(result)
                            );
                        }
                );

        page.addView(
                calculate,
                params(-1, -2)
        );

        Button save =
                addButton(
                        "💾 ثبت خرید",
                        v -> {

                            String f =
                                    farmer.getText()
                                            .toString()
                                            .trim();

                            double w =
                                    number(
                                            weight.getText().toString()
                                    );

                            double p =
                                    number(
                                            price.getText().toString()
                                    );

                            if (f.isEmpty() || w <= 0 || p <= 0) {

                                Toast.makeText(
                                        this,
                                        "اطلاعات خرید را کامل وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            double t = w * p;

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO purchases"
                                                    + "(farmer,weight,price,total)"
                                                    + " VALUES(?,?,?,?)",
                                            new Object[]{
                                                    f,
                                                    w,
                                                    p,
                                                    t
                                            }
                                    );

                            Toast.makeText(
                                    this,
                                    "خرید ثبت شد.",
                                    Toast.LENGTH_SHORT
                            ).show();

                            showBuyFromFarmer();
                        }
                );

        page.addView(
                save,
                params(-1, -2)
        );

        space(page, 15);

        TextView history =
                text(
                        "📋 خریدهای ثبت‌شده",
                        18,
                        GOLD,
                        Gravity.CENTER
                );

        history.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(
                history,
                params(-1, -2)
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT farmer,weight,price,total "
                                        + "FROM purchases "
                                        + "ORDER BY id DESC",
                                null
                        );

        while (c.moveToNext()) {

            String item =
                    "🍇 باغدار: "
                            + c.getString(0)
                            + "\n"
                            + "وزن: "
                            + formatNumber(c.getDouble(1))
                            + " کیلو"
                            + "\n"
                            + "قیمت هر کیلو: "
                            + money(c.getDouble(2))
                            + "\n"
                            + "مبلغ کل: "
                            + money(c.getDouble(3));

            page.addView(
                    infoCard(item),
                    params(-1, -2)
            );
        }

        c.close();
    }

    // =========================================================
    // قیمت روز
    // =========================================================

    private void showDailyPrice() {

        LinearLayout page =
                page("قیمت روز");

        addBack(page);

        page.addView(
                infoCard(
                        "قیمت روز روی سفارش‌های جدید اعمال می‌شود.\n"
                                + "سفارش‌های تحویل‌نشده نیز با قیمت روز محاسبه می‌شوند."
                ),
                params(-1, -2)
        );

        space(page, 12);

        EditText price =
                input("قیمت امروز هر کیلو");

        page.addView(
                price,
                params(-1, -2)
        );

        Button save =
                addButton(
                        "💾 ثبت قیمت امروز",
                        v -> {

                            double p =
                                    number(
                                            price.getText().toString()
                                    );

                            if (p <= 0) {

                                Toast.makeText(
                                        this,
                                        "قیمت معتبر وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            String date =
                                    today();

                            SQLiteDatabase database =
                                    db.getWritableDatabase();

                            database.execSQL(
                                    "INSERT INTO daily_prices"
                                            + "(price,price_date,created_at)"
                                            + " VALUES(?,?,?)",
                                    new Object[]{
                                            p,
                                            date,
                                            System.currentTimeMillis()
                                    }
                            );

                            Toast.makeText(
                                    this,
                                    "قیمت امروز ثبت شد.",
                                    Toast.LENGTH_SHORT
                            ).show();

                            showDailyPrice();
                        }
                );

        page.addView(
                save,
                params(-1, -2)
        );

        space(page, 15);

        double current =
                currentDailyPrice();

        page.addView(
                infoCard(
                        "💵 قیمت فعلی:\n"
                                + money(current)
                                + " تومان"
                ),
                params(-1, -2)
        );

        space(page, 10);

        TextView historyTitle =
                text(
                        "تاریخچه قیمت",
                        18,
                        GOLD,
                        Gravity.CENTER
                );

        page.addView(
                historyTitle,
                params(-1, -2)
        );

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT price,price_date "
                                        + "FROM daily_prices "
                                        + "ORDER BY id DESC",
                                null
                        );

        while (c.moveToNext()) {

            page.addView(
                    infoCard(
                            "📅 "
                                    + c.getString(1)
                                    + "\n"
                                    + "💰 "
                                    + money(
                                    c.getDouble(0)
                            )
                                    + " تومان"
                    ),
                    params(-1, -2)
            );
        }

        c.close();
    }

    private double currentDailyPrice() {

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT price "
                                        + "FROM daily_prices "
                                        + "ORDER BY id DESC "
                                        + "LIMIT 1",
                                null
                        );

        double result = 0;

        if (c.moveToFirst()) {
            result = c.getDouble(0);
        }

        c.close();

        return result;
    }

    // =========================================================
    // سفارش بار
    // =========================================================

    private void showOrders() {

        LinearLayout page =
                page("سفارش بار");

        addBack(page);

        page.addView(
                addButton(
                        "➕ ثبت سفارش جدید",
                        v -> addOrder()
                ),
                params(-1, -2)
        );

        space(page, 10);

        page.addView(
                infoCard(
                        "قیمت سفارش‌های در انتظار، همیشه از آخرین قیمت روز محاسبه می‌شود."
                ),
                params(-1, -2)
        );

        space(page, 12);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,customer,province,city,"
                                        + "weight,order_date,delivery_date,"
                                        + "status,final_price "
                                        + "FROM orders "
                                        + "ORDER BY id DESC",
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

            int id =
                    c.getInt(0);

            String customer =
                    c.getString(1);

            String province =
                    c.getString(2);

            String city =
                    c.getString(3);

            double weight =
                    c.getDouble(4);

            String orderDate =
                    c.getString(5);

            String deliveryDate =
                    c.getString(6);

            String status =
                    c.getString(7);

            double finalPrice =
                    c.getDouble(8);

            double price;

            if ("delivered".equals(status)
                    && finalPrice > 0) {

                price = finalPrice;

            } else {

                price =
                        currentDailyPrice();
            }

            double total =
                    weight * price;

            String statusText;

            if ("delivered".equals(status)) {

                statusText =
                        "✅ تحویل شده";

            } else {

                statusText =
                        "⏳ در انتظار تحویل";
            }

            String delivery =
                    deliveryDate == null
                            || deliveryDate.isEmpty()
                            ? "هنوز تحویل نشده"
                            : deliveryDate;

            String item =
                    "👤 مشتری: "
                            + safe(customer)
                            + "\n"
                            + "📍 "
                            + safe(province)
                            + " - "
                            + safe(city)
                            + "\n"
                            + "⚖️ وزن: "
                            + formatNumber(weight)
                            + " کیلو"
                            + "\n"
                            + "📅 تاریخ سفارش: "
                            + safe(orderDate)
                            + "\n"
                            + "🚚 تاریخ تحویل: "
                            + delivery
                            + "\n"
                            + "💰 قیمت فعلی هر کیلو: "
                            + money(price)
                            + "\n"
                            + "💵 مبلغ فعلی سفارش: "
                            + money(total)
                            + "\n"
                            + "وضعیت: "
                            + statusText;

            page.addView(
                    infoCard(item),
                    params(-1, -2)
            );

            if (!"delivered".equals(status)) {

                Button deliver =
                        addButton(
                                "🚚 ثبت تحویل سفارش #" + id,
                                v -> deliverOrder(id, weight)
                        );

                page.addView(
                        deliver,
                        params(-1, -2)
                );
            }
        }

        c.close();
    }

    // =========================================================
    // ثبت سفارش جدید
    // =========================================================

    private void addOrder() {

        LinearLayout box =
                verticalBox();

        EditText customer =
                input("نام مشتری");

        Spinner province =
                spinner(
                        new ArrayList<>(
                                provinces.keySet()
                        )
                );

        Spinner city =
                spinner(
                        new ArrayList<>()
                );

        province.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String selected =
                                parent.getItemAtPosition(
                                        position
                                ).toString();

                        String[] cities =
                                provinces.get(
                                        selected
                                );

                        ArrayList<String> list =
                                new ArrayList<>();

                        if (cities != null) {

                            for (String c : cities) {
                                list.add(c);
                            }
                        }

                        city.setAdapter(
                                createSpinnerAdapter(list)
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        EditText weight =
                input("وزن سفارش (کیلو)");

        box.addView(customer);

        TextView provinceLabel =
                label("استان");

        box.addView(provinceLabel);
        box.addView(province);

        TextView cityLabel =
                label("شهر");

        box.addView(cityLabel);
        box.addView(city);

        box.addView(weight);

        TextView currentPrice =
                text(
                        "💰 قیمت روز فعلی: "
                                + money(
                                currentDailyPrice()
                        ),
                        16,
                        GOLD,
                        Gravity.CENTER
                );

        currentPrice.setPadding(
                dp(10),
                dp(12),
                dp(10),
                dp(12)
        );

        box.addView(
                currentPrice,
                params(-1, -2)
        );

        new AlertDialog.Builder(this)
                .setTitle(
                        "ثبت سفارش بار"
                )
                .setView(box)
                .setNegativeButton(
                        "لغو",
                        null
                )
                .setPositiveButton(
                        "ثبت سفارش",
                        (dialog, which) -> {

                            String customerName =
                                    customer.getText()
                                            .toString()
                                            .trim();

                            double w =
                                    number(
                                            weight.getText()
                                                    .toString()
                                    );

                            if (customerName.isEmpty()
                                    || w <= 0) {

                                Toast.makeText(
                                        this,
                                        "نام مشتری و وزن سفارش الزامی است.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            String selectedProvince =
                                    province.getSelectedItem()
                                            .toString();

                            String selectedCity =
                                    city.getSelectedItem() == null
                                            ? ""
                                            : city.getSelectedItem()
                                            .toString();

                            double price =
                                    currentDailyPrice();

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO orders"
                                                    + "(customer,province,city,"
                                                    + "weight,price,total,"
                                                    + "order_date,delivery_date,"
                                                    + "status,final_price)"
                                                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                                            new Object[]{
                                                    customerName,
                                                    selectedProvince,
                                                    selectedCity,
                                                    w,
                                                    price,
                                                    w * price,
                                                    today(),
                                                    "",
                                                    "pending",
                                                    0
                                            }
                                    );

                            Toast.makeText(
                                    this,
                                    "سفارش با موفقیت ثبت شد.",
                                    Toast.LENGTH_SHORT
                            ).show();

                            showOrders();
                        }
                )
                .show();
    }

    // =========================================================
    // تحویل سفارش
    // =========================================================

    private void deliverOrder(
            int orderId,
            double weight) {

        double finalPrice =
                currentDailyPrice();

        double total =
                weight * finalPrice;

        new AlertDialog.Builder(this)
                .setTitle(
                        "تحویل سفارش"
                )
                .setMessage(
                        "قیمت نهایی روز تحویل:\n"
                                + money(finalPrice)
                                + " تومان\n\n"
                                + "مبلغ نهایی:\n"
                                + money(total)
                                + " تومان\n\n"
                                + "سفارش تحویل شده ثبت شود؟"
                )
                .setNegativeButton(
                        "لغو",
                        null
                )
                .setPositiveButton(
                        "تأیید تحویل",
                        (dialog, which) -> {

                            db.getWritableDatabase()
                                    .execSQL(
                                            "UPDATE orders "
                                                    + "SET delivery_date=?,"
                                                    + "status=?,"
                                                    + "final_price=?,"
                                                    + "price=?,"
                                                    + "total=? "
                                                    + "WHERE id=?",
                                            new Object[]{
                                                    today(),
                                                    "delivered",
                                                    finalPrice,
                                                    finalPrice,
                                                    total,
                                                    orderId
                                            }
                                    );

                            Toast.makeText(
                                    this,
                                    "تحویل سفارش ثبت شد.",
                                    Toast.LENGTH_SHORT
                            ).show();

                            showOrders();
                        }
                )
                .show();
    }    // =========================================================
    // گزارش‌ها
    // =========================================================

    private void showReports() {

        LinearLayout page =
                page("گزارش‌ها");

        addBack(page);

        SQLiteDatabase database =
                db.getReadableDatabase();

        Cursor purchases =
                database.rawQuery(
                        "SELECT COUNT(*),"
                                + "COALESCE(SUM(weight),0),"
                                + "COALESCE(SUM(total),0) "
                                + "FROM purchases",
                        null
                );

        if (purchases.moveToFirst()) {

            page.addView(
                    infoCard(
                            "🍇 خرید از باغداران\n"
                                    + "تعداد خرید: "
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
                                    + " تومان"
                    ),
                    params(-1, -2)
            );
        }

        purchases.close();

        Cursor sales =
                database.rawQuery(
                        "SELECT COUNT(*),"
                                + "COALESCE(SUM(weight),0),"
                                + "COALESCE(SUM(total),0) "
                                + "FROM orders",
                        null
                );

        if (sales.moveToFirst()) {

            page.addView(
                    infoCard(
                            "🚚 سفارش‌ها\n"
                                    + "تعداد سفارش: "
                                    + sales.getInt(0)
                                    + "\n"
                                    + "وزن کل: "
                                    + formatNumber(
                                    sales.getDouble(1)
                            )
                                    + " کیلو\n"
                                    + "مبلغ فعلی کل: "
                                    + money(
                                    sales.getDouble(2)
                            )
                                    + " تومان"
                    ),
                    params(-1, -2)
            );
        }

        sales.close();

        Cursor pending =
                database.rawQuery(
                        "SELECT COUNT(*),"
                                + "COALESCE(SUM(weight),0) "
                                + "FROM orders "
                                + "WHERE status='pending'",
                        null
                );

        if (pending.moveToFirst()) {

            page.addView(
                    infoCard(
                            "⏳ سفارش‌های در انتظار\n"
                                    + "تعداد: "
                                    + pending.getInt(0)
                                    + "\n"
                                    + "وزن: "
                                    + formatNumber(
                                    pending.getDouble(1)
                            )
                                    + " کیلو"
                    ),
                    params(-1, -2)
            );
        }

        pending.close();

        Cursor delivered =
                database.rawQuery(
                        "SELECT COUNT(*),"
                                + "COALESCE(SUM(weight),0),"
                                + "COALESCE(SUM(total),0) "
                                + "FROM orders "
                                + "WHERE status='delivered'",
                        null
                );

        if (delivered.moveToFirst()) {

            page.addView(
                    infoCard(
                            "✅ سفارش‌های تحویل‌شده\n"
                                    + "تعداد: "
                                    + delivered.getInt(0)
                                    + "\n"
                                    + "وزن: "
                                    + formatNumber(
                                    delivered.getDouble(1)
                            )
                                    + " کیلو\n"
                                    + "مبلغ نهایی: "
                                    + money(
                                    delivered.getDouble(2)
                            )
                                    + " تومان"
                    ),
                    params(-1, -2)
            );
        }

        delivered.close();

        space(page, 15);

        page.addView(
                text(
                        "📍 گزارش بر اساس شهر",
                        19,
                        GOLD,
                        Gravity.CENTER
                ),
                params(-1, -2)
        );

        Cursor cities =
                database.rawQuery(
                        "SELECT city,"
                                + "COUNT(*),"
                                + "COALESCE(SUM(weight),0),"
                                + "COALESCE(SUM(total),0) "
                                + "FROM orders "
                                + "GROUP BY city "
                                + "ORDER BY COUNT(*) DESC",
                        null
                );

        while (cities.moveToNext()) {

            page.addView(
                    infoCard(
                            "📍 شهر: "
                                    + safe(cities.getString(0))
                                    + "\n"
                                    + "تعداد سفارش: "
                                    + cities.getInt(1)
                                    + "\n"
                                    + "وزن: "
                                    + formatNumber(
                                    cities.getDouble(2)
                            )
                                    + " کیلو\n"
                                    + "مبلغ: "
                                    + money(
                                    cities.getDouble(3)
                            )
                                    + " تومان"
                    ),
                    params(-1, -2)
            );
        }

        cities.close();
    }

    // =========================================================
    // گالری مدیریت
    // =========================================================

    private void showGallery() {

        LinearLayout page =
                page("گالری ZERIVA");

        addBack(page);

        page.addView(
                infoCard(
                        "🖼️ این بخش برای عکس‌ها و فیلم‌های رسمی ZERIVA است."
                                + "\nمحتوا می‌تواند برای معرفی محصول، بسته‌بندی، باغ و ارسال استفاده شود."
                ),
                params(-1, -2)
        );

        space(page, 10);

        page.addView(
                addButton(
                        "➕ افزودن عکس یا فیلم",
                        v -> chooseGalleryMedia()
                ),
                params(-1, -2)
        );

        space(page, 12);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,uri,type,title "
                                        + "FROM gallery "
                                        + "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            page.addView(
                    infoCard(
                            "گالری هنوز خالی است."
                    ),
                    params(-1, -2)
            );
        }

        while (c.moveToNext()) {

            int id =
                    c.getInt(0);

            String uri =
                    c.getString(1);

            String type =
                    c.getString(2);

            String title =
                    c.getString(3);

            LinearLayout item =
                    new LinearLayout(this);

            item.setOrientation(
                    LinearLayout.VERTICAL
            );

            item.setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
            );

            item.setBackground(
                    rounded(
                            GREEN,
                            18,
                            GOLD
                    )
            );

            item.addView(
                    infoCard(
                            (type.equals("video")
                                    ? "🎬 "
                                    : "📷 ")
                                    + safe(title)
                    ),
                    params(-1, -2)
            );

            LinearLayout buttons =
                    new LinearLayout(this);

            buttons.setOrientation(
                    LinearLayout.HORIZONTAL
            );

            Button view =
                    addButton(
                            "مشاهده",
                            v -> openMedia(
                                    Uri.parse(uri),
                                    type
                            )
                    );

            buttons.addView(
                    view,
                    new LinearLayout.LayoutParams(
                            0,
                            dp(50),
                            1
                    )
            );

            Button delete =
                    addButton(
                            "حذف",
                            v -> {

                                db.getWritableDatabase()
                                        .execSQL(
                                                "DELETE FROM gallery "
                                                        + "WHERE id=?",
                                                new Object[]{
                                                        id
                                                }
                                        );

                                showGallery();
                            }
                    );

            buttons.addView(
                    delete,
                    new LinearLayout.LayoutParams(
                            0,
                            dp(50),
                            1
                    )
            );

            item.addView(
                    buttons,
                    params(-1, -2)
            );

            page.addView(
                    item,
                    params(-1, -2)
            );

            space(page, 8);
        }

        c.close();
    }

    private void chooseGalleryMedia() {

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
                3001
        );
    }

    private void saveGalleryMedia() {

        if (selectedGalleryUri == null) {
            return;
        }

        String mime =
                getContentResolver()
                        .getType(
                                selectedGalleryUri
                        );

        String type =
                mime != null &&
                        mime.startsWith("video")
                        ? "video"
                        : "image";

        persistUri(
                selectedGalleryUri
        );

        final EditText title =
                input(
                        "عنوان عکس یا فیلم"
                );

        new AlertDialog.Builder(this)
                .setTitle(
                        "افزودن به گالری"
                )
                .setView(title)
                .setNegativeButton(
                        "لغو",
                        null
                )
                .setPositiveButton(
                        "ذخیره",
                        (dialog, which) -> {

                            String t =
                                    title.getText()
                                            .toString()
                                            .trim();

                            if (t.isEmpty()) {
                                t = "محتوای ZERIVA";
                            }

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO gallery"
                                                    + "(uri,type,title,created_at)"
                                                    + " VALUES(?,?,?,?)",
                                            new Object[]{
                                                    selectedGalleryUri.toString(),
                                                    type,
                                                    t,
                                                    System.currentTimeMillis()
                                            }
                                    );

                            showGallery();
                        }
                )
                .show();
    }

    // =========================================================
    // رضایت مشتری
    // =========================================================

    private void showSatisfaction() {

        LinearLayout page =
                page("رضایت مشتری");

        addBack(page);

        page.addView(
                infoCard(
                        "⭐ عکس و فیلم رضایت مشتریان\n"
                                + "نام مشتری، شهر و تاریخ در کنار محتوا ثبت می‌شود."
                ),
                params(-1, -2)
        );

        space(page, 10);

        page.addView(
                addButton(
                        "➕ ثبت رضایت مشتری",
                        v -> chooseSatisfactionMedia()
                ),
                params(-1, -2)
        );

        space(page, 12);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT id,uri,type,"
                                        + "customer_name,city,date_text "
                                        + "FROM satisfaction "
                                        + "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            page.addView(
                    infoCard(
                            "هنوز رضایتی ثبت نشده است."
                    ),
                    params(-1, -2)
            );
        }

        while (c.moveToNext()) {

            int id =
                    c.getInt(0);

            String uri =
                    c.getString(1);

            String type =
                    c.getString(2);

            String customer =
                    c.getString(3);

            String city =
                    c.getString(4);

            String date =
                    c.getString(5);

            String text =
                    "⭐ مشتری: "
                            + safe(customer)
                            + "\n"
                            + "📍 شهر: "
                            + safe(city)
                            + "\n"
                            + "📅 تاریخ: "
                            + safe(date);

            LinearLayout item =
                    new LinearLayout(this);

            item.setOrientation(
                    LinearLayout.VERTICAL
            );

            item.addView(
                    infoCard(text),
                    params(-1, -2)
            );

            item.addView(
                    addButton(
                            type.equals("video")
                                    ? "🎬 مشاهده فیلم"
                                    : "📷 مشاهده عکس",
                            v -> openMedia(
                                    Uri.parse(uri),
                                    type
                            )
                    ),
                    params(-1, -2)
            );

            item.addView(
                    addButton(
                            "🗑️ حذف",
                            v -> {

                                db.getWritableDatabase()
                                        .execSQL(
                                                "DELETE FROM satisfaction "
                                                        + "WHERE id=?",
                                                new Object[]{
                                                        id
                                                }
                                        );

                                showSatisfaction();
                            }
                    ),
                    params(-1, -2)
            );

            page.addView(
                    item,
                    params(-1, -2)
            );

            space(page, 8);
        }

        c.close();
    }

    private void chooseSatisfactionMedia() {

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
                4001
        );
    }

    private void saveSatisfactionMedia() {

        if (selectedSatisfactionUri == null) {
            return;
        }

        final EditText customer =
                input("نام مشتری");

        final EditText city =
                input("شهر مشتری");

        LinearLayout box =
                verticalBox();

        box.addView(customer);
        box.addView(city);

        new AlertDialog.Builder(this)
                .setTitle(
                        "ثبت رضایت مشتری"
                )
                .setView(box)
                .setNegativeButton(
                        "لغو",
                        null
                )
                .setPositiveButton(
                        "ثبت",
                        (dialog, which) -> {

                            String customerName =
                                    customer.getText()
                                            .toString()
                                            .trim();

                            String customerCity =
                                    city.getText()
                                            .toString()
                                            .trim();

                            if (customerName.isEmpty()) {

                                Toast.makeText(
                                        this,
                                        "نام مشتری را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            String mime =
                                    getContentResolver()
                                            .getType(
                                                    selectedSatisfactionUri
                                            );

                            String type =
                                    mime != null
                                            && mime.startsWith("video")
                                            ? "video"
                                            : "image";

                            persistUri(
                                    selectedSatisfactionUri
                            );

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO satisfaction"
                                                    + "(uri,type,customer_name,"
                                                    + "city,date_text,created_at)"
                                                    + " VALUES(?,?,?,?,?,?)",
                                            new Object[]{
                                                    selectedSatisfactionUri.toString(),
                                                    type,
                                                    customerName,
                                                    customerCity,
                                                    today(),
                                                    System.currentTimeMillis()
                                            }
                                    );

                            showSatisfaction();
                        }
                )
                .show();
    }

    // =========================================================
    // مدیریت
    // =========================================================

    private void showManagement() {

        LinearLayout page =
                page("مدیریت ZERIVA");

        addBack(page);

        page.addView(
                infoCard(
                        "⚙️ بخش مدیریت\n\n"
                                + "در این نسخه ابزارهای مدیریتی اصلی در همین برنامه قرار گرفته‌اند.\n"
                                + "برای استفاده واقعی چندکاربره، احراز هویت و سرور آنلاین باید در مرحله بعد اضافه شود."
                ),
                params(-1, -2)
        );

        space(page, 12);

        page.addView(
                addButton(
                        "💵 مدیریت قیمت روز",
                        v -> showDailyPrice()
                ),
                params(-1, -2)
        );

        page.addView(
                addButton(
                        "🖼️ مدیریت گالری",
                        v -> showGallery()
                ),
                params(-1, -2)
        );

        page.addView(
                addButton(
                        "⭐ مدیریت رضایت مشتری",
                        v -> showSatisfaction()
                ),
                params(-1, -2)
        );

        page.addView(
                addButton(
                        "📚 آرشیو استوری",
                        v -> showStoryArchive()
                ),
                params(-1, -2)
        );

        page.addView(
                addButton(
                        "📊 گزارش‌ها",
                        v -> showReports()
                ),
                params(-1, -2)
        );
    }

    // =========================================================
    // چت
    // =========================================================

    private void showChat() {

        LinearLayout page =
                page("چت ZERIVA");

        addBack(page);

        page.addView(
                infoCard(
                        "💬 چت متنی\n\n"
                                + "صفحه چت آماده شده است.\n"
                                + "برای اینکه مشتریان از گوشی‌های مختلف واقعاً به‌صورت آنلاین پیام بدهند، "
                                + "در مرحله بعد باید سرور و حساب کاربری به برنامه متصل شود."
                ),
                params(-1, -2)
        );

        space(page, 12);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT customer,sender_role,"
                                        + "message,message_type,created_at "
                                        + "FROM chat_messages "
                                        + "ORDER BY id ASC",
                                null
                        );

        while (c.moveToNext()) {

            String customer =
                    c.getString(0);

            String role =
                    c.getString(1);

            String message =
                    c.getString(2);

            String type =
                    c.getString(3);

            String bubble =
                    (role.equals("management")
                            ? "🟢 مدیریت"
                            : "👤 مشتری")
                            + "\n"
                            + (type.equals("voice")
                            ? "🎤 پیام صوتی"
                            : message);

            page.addView(
                    infoCard(bubble),
                    params(-1, -2)
            );
        }

        c.close();

        EditText message =
                input("پیام خود را بنویسید...");

        page.addView(
                message,
                params(-1, -2)
        );

        page.addView(
                addButton(
                        "📤 ارسال پیام",
                        v -> {

                            String m =
                                    message.getText()
                                            .toString()
                                            .trim();

                            if (m.isEmpty()) {
                                return;
                            }

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO chat_messages"
                                                    + "(customer,sender_role,"
                                                    + "message,message_type,"
                                                    + "created_at)"
                                                    + " VALUES(?,?,?,?,?)",
                                            new Object[]{
                                                    "مدیریت",
                                                    "management",
                                                    m,
                                                    "text",
                                                    System.currentTimeMillis()
                                            }
                                    );

                            showChat();
                        }
                ),
                params(-1, -2)
        );

        page.addView(
                addButton(
                        "🎤 پیام صوتی",
                        v -> chooseVoiceMessage()
                ),
                params(-1, -2)
        );
    }

    private void chooseVoiceMessage() {

        Intent intent =
                new Intent(
                        Intent.ACTION_OPEN_DOCUMENT
                );

        intent.addCategory(
                Intent.CATEGORY_OPENABLE
        );

        intent.setType("audio/*");

        startActivityForResult(
                intent,
                5001
        );
    }

    private void saveVoiceMessage() {

        if (selectedVoiceUri == null) {
            return;
        }

        persistUri(
                selectedVoiceUri
        );

        db.getWritableDatabase()
                .execSQL(
                        "INSERT INTO chat_messages"
                                + "(customer,sender_role,"
                                + "message,message_type,uri,created_at)"
                                + " VALUES(?,?,?,?,?,?)",
                        new Object[]{
                                "مدیریت",
                                "management",
                                "",
                                "voice",
                                selectedVoiceUri.toString(),
                                System.currentTimeMillis()
                        }
                );

        showChat();
    }    // =========================================================
    // فروش / ثبت سفارش سریع
    // =========================================================

    private void showSales() {

        LinearLayout page =
                page("فروش و ارسال");

        addBack(page);

        page.addView(
                infoCard(
                        "🚚 برای ثبت فروش جدید از بخش «سفارش بار» استفاده کنید."
                ),
                params(-1, -2)
        );

        page.addView(
                addButton(
                        "📦 رفتن به سفارش بار",
                        v -> showOrders()
                ),
                params(-1, -2)
        );

        page.addView(
                addButton(
                        "📊 گزارش فروش و ارسال",
                        v -> showReports()
                ),
                params(-1, -2)
        );
    }

    // =========================================================
    // حساب‌ها / معاملات
    // =========================================================

    private void showTransactions() {

        LinearLayout page =
                page("حساب‌ها و معاملات");

        addBack(page);

        page.addView(
                addButton(
                        "➕ ثبت معامله",
                        v -> addTransaction()
                ),
                params(-1, -2)
        );

        space(page, 10);

        Cursor c =
                db.getReadableDatabase()
                        .rawQuery(
                                "SELECT person,description,debt,payment "
                                        + "FROM transactions "
                                        + "ORDER BY id DESC",
                                null
                        );

        if (c.getCount() == 0) {

            page.addView(
                    infoCard(
                            "هنوز معامله‌ای ثبت نشده است."
                    ),
                    params(-1, -2)
            );
        }

        while (c.moveToNext()) {

            double debt =
                    c.getDouble(2);

            double payment =
                    c.getDouble(3);

            page.addView(
                    infoCard(
                            "👤 "
                                    + safe(c.getString(0))
                                    + "\n"
                                    + "شرح: "
                                    + safe(c.getString(1))
                                    + "\n"
                                    + "💰 بدهکار: "
                                    + money(debt)
                                    + "\n"
                                    + "💵 پرداخت: "
                                    + money(payment)
                    ),
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
                verticalBox();

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
                        "ذخیره",
                        (dialog, which) -> {

                            String p =
                                    person.getText()
                                            .toString()
                                            .trim();

                            String d =
                                    description.getText()
                                            .toString()
                                            .trim();

                            double debtValue =
                                    number(
                                            debt.getText()
                                                    .toString()
                                    );

                            double paymentValue =
                                    number(
                                            payment.getText()
                                                    .toString()
                                    );

                            if (p.isEmpty()) {

                                Toast.makeText(
                                        this,
                                        "نام شخص را وارد کنید.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            db.getWritableDatabase()
                                    .execSQL(
                                            "INSERT INTO transactions"
                                                    + "(person,description,"
                                                    + "debt,payment)"
                                                    + " VALUES(?,?,?,?)",
                                            new Object[]{
                                                    p,
                                                    d,
                                                    debtValue,
                                                    paymentValue
                                            }
                                    );

                            showTransactions();
                        }
                )
                .show();
    }

    // =========================================================
    // باز کردن عکس / فیلم / صدا
    // =========================================================

    private void openMedia(
            Uri uri,
            String type) {

        try {

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

            Toast.makeText(
                    this,
                    "امکان باز کردن فایل وجود ندارد.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================================================
    // دریافت فایل‌های انتخاب‌شده
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

        if (resultCode != RESULT_OK
                || data == null
                || data.getData() == null) {

            return;
        }

        Uri uri =
                data.getData();

        if (requestCode == 2001) {

            selectedStoryUri = uri;

            saveStory();

        } else if (requestCode == 3001) {

            selectedGalleryUri = uri;

            saveGalleryMedia();

        } else if (requestCode == 4001) {

            selectedSatisfactionUri = uri;

            saveSatisfactionMedia();

        } else if (requestCode == 5001) {

            selectedVoiceUri = uri;

            saveVoiceMessage();
        }
    }

    // =========================================================
    // اجازه دسترسی دائمی به فایل انتخاب‌شده
    // =========================================================

    private void persistUri(
            Uri uri) {

        try {

            getContentResolver()
                    .takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );

        } catch (Exception ignored) {
        }
    }

    // =========================================================
    // صفحه عمومی
    // =========================================================

    private LinearLayout page(
            String title) {

        LinearLayout outer =
                new LinearLayout(this);

        outer.setOrientation(
                LinearLayout.VERTICAL
        );

        outer.setBackgroundColor(
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

        TextView titleView =
                text(
                        title,
                        25,
                        GOLD,
                        Gravity.CENTER
                );

        titleView.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        content.addView(
                titleView,
                params(-1, -2)
        );

        space(content, 15);

        scroll.addView(content);

        outer.addView(
                scroll,
                params(-1, -1)
        );

        setContentView(outer);

        return content;
    }

    // =========================================================
    // دکمه بازگشت
    // =========================================================

    private void addBack(
            LinearLayout parent) {

        Button back =
                addButton(
                        "← بازگشت به صفحه اصلی",
                        v -> showHome()
                );

        parent.addView(
                back,
                params(-1, -2)
        );

        space(parent, 10);
    }

    // =========================================================
    // منوی دایره‌ای
    // =========================================================

    private TextView circleMenu(
            String icon,
            String title,
            View.OnClickListener listener) {

        TextView view =
                text(
                        title,
                        15,
                        WHITE,
                        Gravity.CENTER
                );

        view.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        view.setPadding(
                dp(8),
                dp(8),
                dp(8),
                dp(8)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setShape(
                GradientDrawable.OVAL
        );

        bg.setColor(
                GREEN
        );

        bg.setStroke(
                dp(2),
                GOLD
        );

        view.setBackground(bg);

        view.setOnClickListener(
                listener
        );

        return view;
    }

    // =========================================================
    // کارت اطلاعات
    // =========================================================

    private TextView infoCard(
            String message) {

        TextView view =
                text(
                        message,
                        15,
                        LIGHT,
                        Gravity.CENTER
                );

        view.setPadding(
                dp(15),
                dp(15),
                dp(15),
                dp(15)
        );

        view.setBackground(
                rounded(
                        GREEN,
                        18,
                        GOLD
                )
        );

        return view;
    }

    // =========================================================
    // دکمه
    // =========================================================

    private Button addButton(
            String title,
            View.OnClickListener listener) {

        Button button =
                new Button(this);

        button.setText(
                title
        );

        button.setTextSize(
                15
        );

        button.setTextColor(
                WHITE
        );

        button.setAllCaps(false);

        button.setGravity(
                Gravity.CENTER
        );

        button.setBackground(
                rounded(
                        GREEN_LIGHT,
                        16,
                        GOLD
                )
        );

        button.setPadding(
                dp(10),
                dp(5),
                dp(10),
                dp(5)
        );

        button.setOnClickListener(
                listener
        );

        return button;
    }

    // =========================================================
    // متن
    // =========================================================

    private TextView text(
            String value,
            float size,
            int color,
            int gravity) {

        TextView view =
                new TextView(this);

        view.setText(
                value
        );

        view.setTextSize(
                size
        );

        view.setTextColor(
                color
        );

        view.setGravity(
                gravity
        );

        return view;
    }

    // =========================================================
    // برچسب
    // =========================================================

    private TextView label(
            String value) {

        TextView view =
                text(
                        value,
                        14,
                        GOLD,
                        Gravity.RIGHT
                );

        view.setPadding(
                dp(4),
                dp(8),
                dp(4),
                dp(4)
        );

        return view;
    }

    // =========================================================
    // ورودی
    // =========================================================

    private EditText input(
            String hint) {

        EditText edit =
                new EditText(this);

        edit.setHint(
                hint
        );

        edit.setHintTextColor(
                GRAY
        );

        edit.setTextColor(
                WHITE
        );

        edit.setTextSize(
                15
        );

        edit.setGravity(
                Gravity.RIGHT
        );

        edit.setSingleLine(
                true
        );

        edit.setPadding(
                dp(12),
                dp(12),
                dp(12),
                dp(12)
        );

        edit.setBackground(
                rounded(
                        GREEN,
                        14,
                        GOLD
                )
        );

        LinearLayout.LayoutParams lp =
                params(
                        -1,
                        dp(55)
                );

        lp.setMargins(
                0,
                dp(5),
                0,
                dp(5)
        );

        edit.setLayoutParams(
                lp
        );

        return edit;
    }

    // =========================================================
    // اسپینر
    // =========================================================

    private Spinner spinner(
            ArrayList<String> items) {

        Spinner spinner =
                new Spinner(this);

        spinner.setAdapter(
                createSpinnerAdapter(items)
        );

        return spinner;
    }

    private ArrayAdapter<String>
    createSpinnerAdapter(
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

                        TextView view =
                                (TextView) super.getView(
                                        position,
                                        convertView,
                                        parent
                                );

                        view.setTextColor(
                                WHITE
                        );

                        view.setGravity(
                                Gravity.CENTER
                        );

                        view.setTextSize(
                                15
                        );

                        return view;
                    }

                    @Override
                    public View getDropDownView(
                            int position,
                            View convertView,
                            ViewGroup parent) {

                        TextView view =
                                (TextView) super.getDropDownView(
                                        position,
                                        convertView,
                                        parent
                                );

                        view.setTextColor(
                                DARK
                        );

                        view.setGravity(
                                Gravity.CENTER
                        );

                        return view;
                    }
                };

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        return adapter;
    }

    // =========================================================
    // جعبه عمودی
    // =========================================================

    private LinearLayout verticalBox() {

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        box.setPadding(
                dp(4),
                dp(4),
                dp(4),
                dp(4)
        );

        return box;
    }

    // =========================================================
    // پس‌زمینه گرد
    // =========================================================

    private GradientDrawable rounded(
            int color,
            int radius,
            int strokeColor) {

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(
                color
        );

        drawable.setCornerRadius(
                dp(radius)
        );

        drawable.setStroke(
                dp(1),
                strokeColor
        );

        return drawable;
    }

    // =========================================================
    // اندازه
    // =========================================================

    private LinearLayout.LayoutParams params(
            int width,
            int height) {

        return new LinearLayout.LayoutParams(
                width,
                height
        );
    }

    // =========================================================
    // فاصله
    // =========================================================

    private void space(
            LinearLayout parent,
            int size) {

        Space space =
                new Space(this);

        parent.addView(
                space,
                new LinearLayout.LayoutParams(
                        1,
                        dp(size)
                )
        );
    }

    // =========================================================
    // فوتر
    // =========================================================

    private void addContactFooter(
            LinearLayout parent) {

        TextView footer =
                text(
                        "ZERIVA\n"
                                + "Mariwan • Zarivar\n"
                                + "📞 "
                                + PHONE
                                + "\n"
                                + "📷 "
                                + INSTAGRAM,
                        14,
                        GRAY,
                        Gravity.CENTER
                );

        footer.setPadding(
                dp(10),
                dp(15),
                dp(10),
                dp(10)
        );

        parent.addView(
                footer,
                params(-1, -2)
        );
    }

    // =========================================================
    // تاریخ امروز
    // =========================================================

    private String today() {

        SimpleDateFormat format =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.US
                );

        return format.format(
                new Date()
        );
    }

    // =========================================================
    // تبدیل عدد
    // =========================================================

    private double number(
            String value) {

        if (value == null) {
            return 0;
        }

        value =
                value.replace(
                        ",",
                        ""
                ).trim();

        if (value.isEmpty()) {
            return 0;
        }

        try {

            return Double.parseDouble(
                    value
            );

        } catch (Exception e) {

            return 0;
        }
    }

    // =========================================================
    // نمایش پول
    // =========================================================

    private String money(
            double value) {

        return String.format(
                Locale.US,
                "%,.0f",
                value
        );
    }

    // =========================================================
    // نمایش وزن
    // =========================================================

    private String formatNumber(
            double value) {

        if (value == Math.floor(value)) {

            return String.format(
                    Locale.US,
                    "%.0f",
                    value
            );
        }

        return String.format(
                Locale.US,
                "%.2f",
                value
        );
    }

    // =========================================================
    // متن امن
    // =========================================================

    private String safe(
            String value) {

        if (value == null) {
            return "";
        }

        return value;
    }

    // =========================================================
    // تبدیل dp
    // =========================================================

    private int dp(
            int value) {

        return (int) (
                value
                        * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }

    // =========================================================
    // دیتابیس ZERIVA
    // =========================================================

    private static class DB
            extends SQLiteOpenHelper {

        private static final String NAME =
                "zeriva.db";

        private static final int VERSION =
                7;

        DB(Context context) {

            super(
                    context,
                    NAME,
                    null,
                    VERSION
            );
        }

        @Override
        public void onCreate(
                SQLiteDatabase database) {

            // مشتریان
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS customers("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "name TEXT,"
                            + "phone TEXT)"
            );

            // معاملات
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS transactions("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "person TEXT,"
                            + "description TEXT,"
                            + "debt REAL DEFAULT 0,"
                            + "payment REAL DEFAULT 0)"
            );

            // خرید از باغدار
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS purchases("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "farmer TEXT,"
                            + "weight REAL DEFAULT 0,"
                            + "price REAL DEFAULT 0,"
                            + "total REAL DEFAULT 0)"
            );

            // سفارش‌ها
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS orders("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "customer TEXT,"
                            + "province TEXT,"
                            + "city TEXT,"
                            + "weight REAL DEFAULT 0,"
                            + "price REAL DEFAULT 0,"
                            + "total REAL DEFAULT 0,"
                            + "media_uri TEXT,"
                            + "order_date TEXT,"
                            + "delivery_date TEXT,"
                            + "status TEXT DEFAULT 'pending',"
                            + "final_price REAL DEFAULT 0)"
            );

            // استوری
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS stories("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "uri TEXT,"
                            + "type TEXT,"
                            + "created_at INTEGER,"
                            + "archived INTEGER DEFAULT 0)"
            );

            // قیمت روز
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS daily_prices("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "price REAL DEFAULT 0,"
                            + "price_date TEXT,"
                            + "created_at INTEGER)"
            );

            // گالری
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS gallery("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "uri TEXT,"
                            + "type TEXT,"
                            + "title TEXT,"
                            + "created_at INTEGER)"
            );

            // رضایت مشتری
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS satisfaction("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "uri TEXT,"
                            + "type TEXT,"
                            + "customer_name TEXT,"
                            + "city TEXT,"
                            + "date_text TEXT,"
                            + "created_at INTEGER)"
            );

            // چت
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS chat_messages("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "customer TEXT,"
                            + "sender_role TEXT,"
                            + "message TEXT,"
                            + "message_type TEXT,"
                            + "uri TEXT,"
                            + "created_at INTEGER)"
            );
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase database,
                int oldVersion,
                int newVersion) {

            // نسخه 2
            if (oldVersion < 2) {

                addColumnIfMissing(
                        database,
                        "orders",
                        "media_uri",
                        "TEXT"
                );
            }

            // نسخه 3
            if (oldVersion < 3) {

                database.execSQL(
                        "CREATE TABLE IF NOT EXISTS stories("
                                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                + "uri TEXT,"
                                + "type TEXT,"
                                + "created_at INTEGER)"
                );
            }

            // نسخه 4
            if (oldVersion < 4) {

                addColumnIfMissing(
                        database,
                        "orders",
                        "order_date",
                        "TEXT"
                );

                addColumnIfMissing(
                        database,
                        "orders",
                        "delivery_date",
                        "TEXT"
                );

                addColumnIfMissing(
                        database,
                        "orders",
                        "status",
                        "TEXT DEFAULT 'pending'"
                );

                addColumnIfMissing(
                        database,
                        "orders",
                        "final_price",
                        "REAL DEFAULT 0"
                );

                database.execSQL(
                        "CREATE TABLE IF NOT EXISTS daily_prices("
                                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                + "price REAL DEFAULT 0,"
                                + "price_date TEXT,"
                                + "created_at INTEGER)"
                );
            }

            // نسخه 5
            if (oldVersion < 5) {

                addColumnIfMissing(
                        database,
                        "stories",
                        "archived",
                        "INTEGER DEFAULT 0"
                );

                database.execSQL(
                        "CREATE TABLE IF NOT EXISTS gallery("
                                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                + "uri TEXT,"
                                + "type TEXT,"
                                + "title TEXT,"
                                + "created_at INTEGER)"
                );

                database.execSQL(
                        "CREATE TABLE IF NOT EXISTS satisfaction("
                                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                + "uri TEXT,"
                                + "type TEXT,"
                                + "customer_name TEXT,"
                                + "city TEXT,"
                                + "date_text TEXT,"
                                + "created_at INTEGER)"
                );
            }

            // نسخه 6
            if (oldVersion < 6) {

                database.execSQL(
                        "CREATE TABLE IF NOT EXISTS chat_messages("
                                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                + "customer TEXT,"
                                + "sender_role TEXT,"
                                + "message TEXT,"
                                + "message_type TEXT,"
                                + "uri TEXT,"
                                + "created_at INTEGER)"
                );
            }

            // نسخه 7
            if (oldVersion < 7) {

                addColumnIfMissing(
                        database,
                        "chat_messages",
                        "uri",
                        "TEXT"
                );
            }
        }

        private void addColumnIfMissing(
                SQLiteDatabase database,
                String table,
                String column,
                String type) {

            Cursor cursor =
                    database.rawQuery(
                            "PRAGMA table_info("
                                    + table
                                    + ")",
                            null
                    );

            boolean exists = false;

            while (cursor.moveToNext()) {

                String name =
                        cursor.getString(1);

                if (column.equalsIgnoreCase(name)) {

                    exists = true;
                    break;
                }
            }

            cursor.close();

            if (!exists) {

                database.execSQL(
                        "ALTER TABLE "
                                + table
                                + " ADD COLUMN "
                                + column
                                + " "
                                + type
                );
            }
        }
    }
}
