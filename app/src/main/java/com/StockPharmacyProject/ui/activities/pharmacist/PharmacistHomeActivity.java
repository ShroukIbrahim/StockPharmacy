package com.StockPharmacyProject.ui.activities.pharmacist;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.StockPharmacyProject.R;
import com.StockPharmacyProject.data.local.AppDatabase;
import com.StockPharmacyProject.data.local.CartItem;
import com.StockPharmacyProject.helper.HelperMethod;
import com.StockPharmacyProject.ui.activities.BaseActivity;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.NotificationFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.SearchFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.cart.CartFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.orders.OrderDetilsFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.orders.OrderFragment;
import com.StockPharmacyProject.ui.fragments.pharmacist.homecycle.sidemenu.pharmacy.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.StockPharmacyProject.helper.HelperMethod.replace;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.setSharedPreferences;

public class PharmacistHomeActivity extends BaseActivity {


    Unbinder unbinder;
    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    @BindView(R.id.count_num)
    TextView countNum;
    @BindView(R.id.cart_icon)
    ImageView cartIcon;
    @BindView(R.id.count_cart_num)
    TextView countCartNum;
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.back_title)
    TextView backTitle;
    public ProfileFragment profileFragment=new ProfileFragment();
    @BindView(R.id.icons)
    RelativeLayout icons;
    @BindView(R.id.back_layout)
    RelativeLayout backLayout;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private AppDatabase appDatabase;
    private List<CartItem> items = new ArrayList<>();
    private int count_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_home);
        unbinder = ButterKnife.bind(this);
        appDatabase  = AppDatabase.getAppDatabase(this);
       setCountCartNum(View.VISIBLE,appDatabase.getItemDAO().getItems().size());
        setSharedPreferences(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_medications,
                R.id.nav_offers, R.id.nav_stocks, R.id.nav_points_gifts,
                R.id.nav_terms_conditions, R.id.nav_logout)
                .setDrawerLayout(drawer)

                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setCountCartNum(int visibility ,int count_num){
        if (visibility == View.VISIBLE) {
            if (count_num != 0) {
                countCartNum.setVisibility(View.VISIBLE);
                countCartNum.setText(" " + count_num);
            }
        }
        else {
            countCartNum.setVisibility(View.GONE);
        }
    }


    public void setToolBar(int visibility, String title, View.OnClickListener backActionBtn) {

        if (visibility == View.VISIBLE) {
          toolbar.setVisibility(View.GONE);
            backLayout.setVisibility(View.VISIBLE);
            backTitle.setText(title);
            imageBack.setOnClickListener(backActionBtn);
        }
        else {
            backLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        }


    }

    @OnClick({R.id.notification_icon, R.id.cart_icon, R.id.search_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notification_icon:
//                NotificationFragment notificationFragment = new NotificationFragment();
//                replace(notificationFragment,getSupportFragmentManager(),R.id.nav_host_fragment,null,null);

                break;
            case R.id.cart_icon:
                CartFragment cartFragment = new CartFragment();
                replace(cartFragment,getSupportFragmentManager(),R.id.nav_host_fragment,null,null);
                break;
            case R.id.search_icon:
                searchIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SearchFragment searchFragment = new SearchFragment();
                        replace(searchFragment, getSupportFragmentManager(), R.id.nav_host_fragment, null, null);
                    }
                });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           //superBackPressed();
            //baseFragment.onBack();
            super.onBackPressed();
           setToolBar(View.GONE,"",null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        appDatabase  = AppDatabase.getAppDatabase(this);
//        items.addAll(appDatabase.getItemDAO().getItems());
//        count_num=items.size();
//        if (count_num!=0) {
//            countCartNum.setVisibility(View.VISIBLE);
//            countCartNum.setText(""+count_num);
//        }
    }
}
