package com.example.exampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.text.HtmlCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.exampleapplication.custom_views.CreditCardView;
import com.example.exampleapplication.custom_views.PolygonShapeView;
import com.example.exampleapplication.custom_views.RectangularLoader;
import com.example.exampleapplication.custom_views.SolarSystemView;
import com.example.exampleapplication.interfaces.CreditCardListener;
import com.example.exampleapplication.pojo.CardInfo;

import java.io.File;


import static android.view.View.GONE;
import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT;
import static com.example.exampleapplication.constants.Statics.MOTION_LAYOUT_VIEWPAGER_NUMBER_OF_ITEMS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    MotionLayout onboardingRoot;
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;

    Button prevButton, nextButton, finishButton;

    PolygonShapeView shapeView;
    RectangularLoader progressCustom;
    SolarSystemView solarSystemView;
    RecyclerView pageSnapperRecyclerView;

    AppCompatTextView scrollbaleText;

    float progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        scrollableTextExample();
//        Utils.startRxMainActivity(this);
//        pageSnapperRecyclerViewExample();
//        solarSystemViewExample();
//        searchAnimationExample();
//        rounderLoaderViewExample();
//        polygonShapesViewExample();
//        collapsingToolbarLayoutExample();
//       motionLayoutExample();

        creditCardViewExample();


    }

    public void scrollableTextExample() {
        setContentView(R.layout.layout_scrollable_text);
        String s = "<font color=\"#F00\">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse vel congue ipsum. Nunc in feugiat risus. Nam faucibus sodales lacus vel luctus. Donec accumsan nibh non congue mollis. Cras eget sollicitudin magna, a varius enim. Duis elementum efficitur augue non vehicula. Ut malesuada semper sodales. Donec posuere aliquam felis. Curabitur eget libero consectetur, dapibus est quis, iaculis leo.</font>";
        scrollbaleText = findViewById(R.id.scrollbale_text);
        scrollbaleText.setText(Html.fromHtml(s), TextView.BufferType.SPANNABLE);
//        scrollbaleText.setMovementMethod(new ScrollingMovementMethod());
        //this is important to have scrolling textview
    }

    public void pageSnapperRecyclerViewExample() {
        setContentView(R.layout.view_pager_recycler_view);
        pageSnapperRecyclerView = findViewById(R.id.pageSnapperRecyclerView);
        pageSnapperRecyclerView.setBackgroundColor(getResources()
                .getColor(R.color.colorPrimaryDark));

        PageSnapperAdapter adapter = new PageSnapperAdapter();
        pageSnapperRecyclerView.setAdapter(adapter);
        pageSnapperRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        pageSnapperRecyclerView.addItemDecoration(new
                SeparatorDecoration(this, getResources().getColor(R.color.grey), 20));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(pageSnapperRecyclerView);

        File mp4Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        System.out.println(mp4Directory.getAbsolutePath());
        File[] mp4Files = mp4Directory.listFiles();

        for (File f : mp4Files) {
            System.out.println("mp4Files" + f.getAbsolutePath());
        }
    }


    public void solarSystemViewExample() {
        setContentView(R.layout.rounder_loader_view_example_layout);
        progressCustom = findViewById(R.id.progress_custom);
        progressCustom.setVisibility(GONE);
        solarSystemView = findViewById(R.id.solar_system_view);
        solarSystemView.startAnimationHere();

    }

    public void searchAnimationExample() {
        setContentView(R.layout.search_main_activity);
        AppCompatImageView searchImage = findViewById(R.id.ic_search);

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                i.putExtra(SearchActivity.EXTRA_PARAM_ID, "SEARCH_ID");

                @SuppressWarnings("unchecked")
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        new Pair<>(findViewById(R.id.appbar_layout),
                                SearchActivity.VIEW_NAME_SEARCH_ICON));

                ActivityCompat.startActivity(MainActivity.this, i, activityOptions.toBundle());
            }
        });
    }

    public void rounderLoaderViewExample() {
        setContentView(R.layout.rounder_loader_view_example_layout);
        progressCustom = findViewById(R.id.progress_custom);
        progressCustom.expandCircleAnim();
    }

    public void polygonShapesViewExample() {
        setContentView(R.layout.polygon_shapes_view_example_layout);
        shapeView = findViewById(R.id.shape_view);
        shapeView.animatePath();
    }

    public void collapsingToolbarLayoutExample() {
        setContentView(R.layout.collapsing_toolbar_layout);
    }

    public void motionLayoutExample() {
        setContentView(R.layout.understanding_motionlayout);
        _initMotionLayoutExample();//R.layout.understanding_motionlayout
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finishButton:
                break;

            case R.id.nextButton:
                break;

            case R.id.previousButton:
                break;
        }
    }

    private void _initMotionLayoutExample() {
        onboardingRoot = findViewById(R.id.onboardingRoot);
        viewPager = findViewById(R.id.pagesList);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        finishButton = findViewById(R.id.finishButton);
        prevButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

        finishButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                progress = (position + positionOffset) / (MOTION_LAYOUT_VIEWPAGER_NUMBER_OF_ITEMS - 1);
                onboardingRoot.setProgress(progress);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void creditCardViewExample() {
        setContentView(R.layout.card_view_example);
        ((CreditCardView) findViewById(R.id.card_view)).setCardListener(new CreditCardListener() {
            @Override
            public void getCardType(CardInfo cardInfo) {
                Log.d(TAG, "getCardType: " + cardInfo.toString());
            }

            @Override
            public void getCardNumber(String cardNumber) {
                Log.d(TAG, "getCardNumber: " + cardNumber);
            }

            @Override
            public void isValid(boolean isValid) {
                Log.d(TAG, "isValid: " + isValid);
            }
        });
    }
}
