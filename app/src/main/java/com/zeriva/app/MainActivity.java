package com.zeriva.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
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
    private final int DARK_GREEN = Color.rgb(7, 30, 21);
    private final int GOLD = Color.rgb(212, 175, 55);
    private final int WHITE = Color.WHITE;
    private final int LIGHT_GOLD = Color.rgb(240, 215, 125);

    private DB db;

    private TextView orderPriceView;
    private EditText orderKgView;

    private Spinner provinceSpinner;
    private Spinner citySpinner;

    private String selectedMediaUri = "";

    /*
     * 1001 = قدیمی: رسانه سفارش
     * 2001 = رسانه بار / استوری
     */
    private static final int REQUEST_STORY_MEDIA = 2001;

    private EditText storyTitleInput;
    private Spinner storyProvinceSpinner;
    private Spinner storyCitySpinner;

    private final LinkedHashMap<String, String[]> provinceCities =
            new LinkedHashMap<>();

    // =========================================================
    // استان‌ها و شهرها
    // =========================================================

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

        provinceCities.put("خارج از ایران",
                new String[]{"اقلیم کردستان عراق","عراق","ترکیه","امارات","سایر"});
    }

    // =========================================================
    // شروع
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

        main.addView(title("زریوار", 38));

        TextView subtitle = new TextView(this);

        subtitle.setText(
                "انگور ممتاز مریوان\n" +
                "دریاچه زریوار • مریوان"
        );

        subtitle.setTextColor(WHITE);
        subtitle.setTextSize(18);
        subtitle.setGravity(Gravity.CENTER);

        subtitle.setPadding
