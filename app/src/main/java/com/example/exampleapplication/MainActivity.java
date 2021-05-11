package com.example.exampleapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.exampleapplication.adapter.GridVaryingSpanAdapter;
import com.example.exampleapplication.adapter.ImageViewerAdapter;
import com.example.exampleapplication.adapter.StaggeredExampleAdapter;
import com.example.exampleapplication.custom_views.CreditCardView;
import com.example.exampleapplication.custom_views.PolygonShapeView;
import com.example.exampleapplication.custom_views.RectangularLoader;
import com.example.exampleapplication.custom_views.SolarSystemView;
import com.example.exampleapplication.extraclasses.DataSource;
import com.example.exampleapplication.fragment.DetailsFragment;
import com.example.exampleapplication.fragment.ListingFragment;
import com.example.exampleapplication.fragment.SimpleFragment;
import com.example.exampleapplication.interfaces.CreditCardListener;
import com.example.exampleapplication.interfaces.OnFragmentInteractionListener;
import com.example.exampleapplication.pojo.CardInfo;
import com.example.exampleapplication.pojo.Task;
import com.scorpio.player_lib.PlayerActivity;

import java.io.File;
import java.util.List;


import static android.app.Notification.DEFAULT_ALL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static com.example.exampleapplication.constants.Statics.MOTION_LAYOUT_VIEWPAGER_NUMBER_OF_ITEMS;
import static com.example.exampleapplication.constants.Statics.SCROLL_DOWN;
import static com.example.exampleapplication.fragment.SimpleFragment.NONE;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, OnFragmentInteractionListener {
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
    private Button button;
    private boolean isDisplayed = false;

    float progress;

    private int radioChoice = NONE;
    private int[] lastVisiblePositions = new int[]{-1, -1};

    interface MNotifiaction {
        String CHANNEL_ID_ONE = "channel_id_one";
        String NOTIFICATION_CHANNEL_NAME_ONE = "MAIN_NOTIFICATION_ONE";
        int NOTIFICATION_ID = 0;

        String CHANNEL_ID_TWO = "channel_id_two";
        String NOTIFICATION_CHANNEL_NAME_TWO = "MAIN_NOTIFICATION_TWO";
    }

    private NotificationManager mNotifyManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        scrollableTextExample();
//        Utils.startRxMainActivity(this);
//        pageSnapperRecyclerViewExample();
        solarSystemViewExample();
//        searchAnimationExample();
//        rounderLoaderViewExample();
//        polygonShapesViewExample();
//        collapsingToolbarLayoutExample();
//       motionLayoutExample();

//        creditCardViewExample();
//        overrideEditTextonDrawExample();
//        codelabsFragmentExample();
//        masterDetailLayoutExample();
//        notificationChannelExample();
//        getLifecycle().addObserver(new MainActivityLifeCycleObserver());
//        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit(); //editor is to allow editing
//        editor.apply();//asynchronous

//        staggeredRecyclerViewExample();
//        gridLayoutRecyclerViewExampleWithVaryingSpanSize();
//        recyclerViewPagerSnaperHelperWithImagesScaling();

//        gradientDrawableExample();
    }

    public void gradientDrawableExample() {
        setContentView(R.layout.activity_main);
        ConstraintLayout lauyout = findViewById(R.id.main);
        GradientDrawable gradientDrawable = new GradientDrawable(
//                GradientDrawable.Orientation.LEFT_RIGHT,
//                new int[]{
//                        0XFFD98880,
//                        0XFFD98880,
//                        0XFFF4D03F,
//                        0XFF48C9B0
//                }
                );
        gradientDrawable.setColor(Color.argb(0, 255, 0, 0));
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setGradientRadius(20f);
        lauyout.setBackground(gradientDrawable);
    }


    //    String[] imagesIds = new String[]{"https://w0.pngwave.com/png/763/47/koala-bear-cuteness-koala-png-clip-art.png",
//            "https://w0.pngwave.com/png/297/962/polar-bear-brown-bear-american-black-bear-grizzly-bear-chicago-bears-png-clip-art.png",
//            "https://w0.pngwave.com/png/5/748/teddy-bear-stuffed-animals-cuddly-toys-plush-plush-png-clip-art.png"}
    private void recyclerViewPagerSnaperHelperWithImagesScaling() {
        setContentView(R.layout.image_scaling_recycler_view_layout);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        int[] imagesIds = new int[]{R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3};
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new ImageViewerAdapter(imagesIds));
    }

    private void gridLayoutRecyclerViewExampleWithVaryingSpanSize() {

        setContentView(R.layout.activity_staggered_recycler);
        RecyclerView rv = findViewById(R.id.staggeredRV);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position) {
                    case 2:
                    case 0:
                        return 2;

                    case 1:
                    default:
                        return 1;
                }
            }
        });
        GridVaryingSpanAdapter adapter = new GridVaryingSpanAdapter();

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(SCROLL_DOWN)) {
                    Log.d(TAG, "onScrolled: reached End of recyclerview" + adapter.getVisitedTasks());
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "onScrolled: " + lastVisiblePositions[0] + " " + lastVisiblePositions[1]);
                if (!recyclerView.canScrollVertically(SCROLL_DOWN)) {
                    Log.d(TAG, "onScrolled: reached End of recyclerview" + adapter.getVisitedTasks());
                }
            }
        });

        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
        adapter.setData(DataSource.createTasksList());
    }


    private void staggeredRecyclerViewExample() {
        setContentView(R.layout.activity_staggered_recycler);
        RecyclerView rv = findViewById(R.id.staggeredRV);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        StaggeredExampleAdapter adapter = new StaggeredExampleAdapter();

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(SCROLL_DOWN)) {
                    Log.d(TAG, "onScrolled: reached End of recyclerview" + adapter.getVisitedTasks());
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                layoutManager.findLastVisibleItemPositions(lastVisiblePositions);
                Log.d(TAG, "onScrolled: " + lastVisiblePositions[0] + " " + lastVisiblePositions[1]);
                if (!recyclerView.canScrollVertically(SCROLL_DOWN)) {
                    Log.d(TAG, "onScrolled: reached End of recyclerview" + adapter.getVisitedTasks());
                }
            }
        });

        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
        adapter.setData(DataSource.createTasksList());

    }

    @Override
    protected void onStop() {
        List<Task> taskList = DataSource.createTasksList();
        if (lastVisiblePositions[0] != -1 || lastVisiblePositions[1] != -1) {
            int lastVisibleItem = Math.max(lastVisiblePositions[0], lastVisiblePositions[1]);
            for (int i = 0; i <= lastVisibleItem; i++) {
                Log.d(TAG, "onStop: " + taskList.get(i).getDescription());
            }
        }
        super.onStop();
    }

    private void overrideEditTextonDrawExample() {
        setContentView(R.layout.layout_override_edit_ondraw);
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

//        File mp4Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//        System.out.println(mp4Directory.getAbsolutePath());
//        File[] mp4Files = mp4Directory.listFiles();
//
//        for (File f : mp4Files) {
//            System.out.println("mp4Files" + f.getAbsolutePath());
//        }
    }

    public void solarSystemViewExample() {
        setContentView(R.layout.rounder_loader_view_example_layout);
        progressCustom = findViewById(R.id.progress_custom);
        progressCustom.setVisibility(VISIBLE);
        progressCustom.expandCircleAnim();
//        solarSystemView = findViewById(R.id.solar_system_view);
//        solarSystemView.startAnimationHere();

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

    private void codelabsFragmentExample() {
        setContentView(R.layout.codelabs_fragment_example);
        button = findViewById(R.id.open_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDisplayed) {
                    hideFragment();
                } else {
                    displayFragment();
                }
            }
        });
    }

    private void masterDetailLayoutExample() {
        setContentView(R.layout.list_details_layout);
        boolean isTwoPane = findViewById(R.id.details_container) != null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Log.d(TAG, "masterDetailLayoutExample: " + getSupportFragmentManager().getBackStackEntryCount());
        });


        //region load list fragment
        FrameLayout listLayout = findViewById(R.id.list_container);
        ListingFragment listFragment = ListingFragment.newInstance();
        fragmentTransaction
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
                .add(R.id.list_container, listFragment);
        //endregion

        //region load details fragment (Wider Screens)
        if (isTwoPane) {
            FrameLayout detailsLayout = findViewById(R.id.details_container);
            DetailsFragment detailsFragment = DetailsFragment.newInstance();
            fragmentTransaction
                    .add(R.id.details_container, detailsFragment);
        }
        //endregion
        fragmentTransaction.commit();

    }

    @Override
    public void onClickMe() {
        boolean isTwoPane = findViewById(R.id.details_container) != null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //region load details fragment (Both Cases)
        if (isTwoPane) {
//            FrameLayout detailsLayout = findViewById(R.id.details_container);
//            DetailsFragment detailsFragment = DetailsFragment.newInstance();
//            fragmentTransaction
//                    .add(R.id.details_container, detailsFragment)
//                    .commit();
        } else {
            FrameLayout detailsLayout = findViewById(R.id.list_container);
            DetailsFragment detailsFragment = DetailsFragment.newInstance();
            fragmentTransaction
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, 0, R.anim.slide_out_right)
                    .addToBackStack(null)
                    .setTransition(TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.list_container, detailsFragment)
                    .commit();
        }
        //endregion
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance(radioChoice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .add(R.id.fragment, simpleFragment)
                .addToBackStack(null)
                .commit();

        button.setText("Close");
        isDisplayed = true;

        Log.d(TAG, "displayFragment: after adding" +
                fragmentManager.getBackStackEntryCount());

    }

    public void hideFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SimpleFragment fragment = (SimpleFragment) fragmentManager
                .findFragmentById(R.id.fragment);

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment)
                    .commit();
        } else {

        }
        isDisplayed = false;
        button.setText("Open");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "displayFragment: after adding" +
                getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        radioChoice = choice;
    }

    public void notificationChannelExample() {
        setContentView(R.layout.notification_example);
        AppCompatButton notifyMe = findViewById(R.id.bt_notifyMe);
        notifyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
        createNotificationChannel(MNotifiaction.CHANNEL_ID_ONE);
        createNotificationChannel(MNotifiaction.CHANNEL_ID_TWO);

    }

    private void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(MNotifiaction.CHANNEL_ID_TWO);
        mNotifyManager.notify(MNotifiaction.NOTIFICATION_ID, notifyBuilder.build());
    }

    private void createNotificationChannel(String channelId) {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, MNotifiaction.NOTIFICATION_CHANNEL_NAME_TWO
                    , NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(String channelId) {
        //for devices running Android 7.1 or lower
        //setDefault Priority and default VIBRATION,LIGHT
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                MNotifiaction.NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(DEFAULT_ALL)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_tick);
        return notifyBuilder;

    }
}
