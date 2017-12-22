package com.conceptappsworld.wellbeing;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.conceptappsworld.wellbeing.fragment.AppMapFragment;
import com.conceptappsworld.wellbeing.fragment.GiveFragment;
import com.conceptappsworld.wellbeing.fragment.HomeFragment;
import com.conceptappsworld.wellbeing.fragment.IdeasFragment;
import com.conceptappsworld.wellbeing.fragment.InspireFragment;
import com.conceptappsworld.wellbeing.fragment.LikeFavFragment;
import com.conceptappsworld.wellbeing.fragment.MyWbFragment;
import com.conceptappsworld.wellbeing.fragment.ProfileFragment;
import com.conceptappsworld.wellbeing.fragment.ShareFragment;
import com.conceptappsworld.wellbeing.fragment.TodoFragment;
import com.conceptappsworld.wellbeing.util.Constants;


public class MainActivity extends FragmentActivity implements View.OnClickListener,
        AppMapFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        GiveFragment.OnFragmentInteractionListener,
        MyWbFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        LikeFavFragment.OnFragmentInteractionListener,
        IdeasFragment.OnFragmentInteractionListener,
        ShareFragment.OnFragmentInteractionListener,
        TodoFragment.OnFragmentInteractionListener,
        InspireFragment.OnFragmentInteractionListener {

    private LinearLayout llAppMap;
    private LinearLayout llProfile;
    private LinearLayout llGive;
    private LinearLayout llMyWb;
    private LinearLayout llHome;

    private LinearLayout llLikeFav;
    private LinearLayout llIdeas;
    private LinearLayout llShare;
    private LinearLayout llTodo;
    private LinearLayout llInspire;

    private ImageView ivAppMap;
    private ImageView ivProfile;
    private ImageView ivGive;
    private ImageView ivMyWb;
    private ImageView ivHome;

    private ImageView ivLikeFav;
    private ImageView ivIdeas;
    private ImageView ivShare;
    private ImageView ivTodo;
    private ImageView ivInspire;

    private TextView tvAppMap;
    private TextView tvProfile;
    private TextView tvGive;
    private TextView tvMyWb;
    private TextView tvHome;

    private TextView tvLikeFav;
    private TextView tvIdeas;
    private TextView tvShare;
    private TextView tvTodo;
    private TextView tvInspire;

    private LinearLayout llLast, llCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();
        setClickListeners();

        displayView(Constants.HOME_FRAG_ID, true);
        llCurrent = llHome;
    }

    private void setClickListeners() {
        llAppMap.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        llGive.setOnClickListener(this);
        llMyWb.setOnClickListener(this);
        llHome.setOnClickListener(this);

        llLikeFav.setOnClickListener(this);
        llIdeas.setOnClickListener(this);
        llShare.setOnClickListener(this);
        llTodo.setOnClickListener(this);
        llInspire.setOnClickListener(this);

        ivAppMap.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        ivGive.setOnClickListener(this);
        ivMyWb.setOnClickListener(this);
        ivHome.setOnClickListener(this);

        ivLikeFav.setOnClickListener(this);
        ivIdeas.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivTodo.setOnClickListener(this);
        ivInspire.setOnClickListener(this);

        tvAppMap.setOnClickListener(this);
        tvProfile.setOnClickListener(this);
        tvGive.setOnClickListener(this);
        tvMyWb.setOnClickListener(this);
        tvHome.setOnClickListener(this);

        tvLikeFav.setOnClickListener(this);
        tvIdeas.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        tvTodo.setOnClickListener(this);
        tvInspire.setOnClickListener(this);
    }

    private void findViewByIds() {
        llAppMap = findViewById(R.id.ll_app_map);
        llProfile = findViewById(R.id.ll_profile);
        llGive = findViewById(R.id.ll_give);
        llMyWb = findViewById(R.id.ll_my_wb);
        llHome = findViewById(R.id.ll_home);

        llLikeFav = findViewById(R.id.ll_like_fav);
        llIdeas = findViewById(R.id.ll_ideas);
        llShare = findViewById(R.id.ll_share);
        llTodo = findViewById(R.id.ll_todo);
        llInspire = findViewById(R.id.ll_inspire_me);

        ivAppMap = findViewById(R.id.iv_app_map);
        ivProfile = findViewById(R.id.iv_profile);
        ivGive = findViewById(R.id.iv_give);
        ivMyWb = findViewById(R.id.iv_my_wb);
        ivHome = findViewById(R.id.iv_home);

        ivLikeFav = findViewById(R.id.iv_like_fav);
        ivIdeas = findViewById(R.id.iv_ideas);
        ivShare = findViewById(R.id.iv_share);
        ivTodo = findViewById(R.id.iv_todo);
        ivInspire = findViewById(R.id.iv_inspire_me);

        tvAppMap = findViewById(R.id.tv_app_map);
        tvProfile = findViewById(R.id.tv_profile);
        tvGive = findViewById(R.id.tv_give);
        tvMyWb = findViewById(R.id.tv_my_wb);
        tvHome = findViewById(R.id.tv_home);

        tvLikeFav = findViewById(R.id.tv_like_fav);
        tvIdeas = findViewById(R.id.tv_ideas);
        tvShare = findViewById(R.id.tv_share);
        tvTodo = findViewById(R.id.tv_todo);
        tvInspire = findViewById(R.id.tv_inspire_me);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_app_map:
            case R.id.iv_app_map:
            case R.id.tv_app_map:
                displayView(Constants.APP_MENU_FRAG_ID, false);
                break;
            case R.id.ll_profile:
            case R.id.iv_profile:
            case R.id.tv_profile:
                displayView(Constants.PROFILE_FRAG_ID, false);
                break;
            case R.id.ll_give:
            case R.id.iv_give:
            case R.id.tv_give:
                displayView(Constants.GIVE_FRAG_ID, false);
                break;
            case R.id.ll_my_wb:
            case R.id.iv_my_wb:
            case R.id.tv_my_wb:
                displayView(Constants.MYWB_FRAG_ID, false);
                break;
            case R.id.ll_home:
            case R.id.iv_home:
            case R.id.tv_home:
                displayView(Constants.HOME_FRAG_ID, false);
                break;
            case R.id.ll_like_fav:
            case R.id.iv_like_fav:
            case R.id.tv_like_fav:
                displayView(Constants.LIKE_FAV_FRAG_ID, false);
                break;
            case R.id.ll_ideas:
            case R.id.iv_ideas:
            case R.id.tv_ideas:
                displayView(Constants.IDEAS_FRAG_ID, false);
                break;
            case R.id.ll_share:
            case R.id.iv_share:
            case R.id.tv_share:
                displayView(Constants.SHARE_FRAG_ID, false);
                break;
            case R.id.ll_todo:
            case R.id.iv_todo:
            case R.id.tv_todo:
                displayView(Constants.TODO_FRAG_ID, false);
                break;
            case R.id.ll_inspire_me:
            case R.id.iv_inspire_me:
            case R.id.tv_inspire_me:
                displayView(Constants.INSPIRE_FRAG_ID, false);
                break;
        }

    }

    private void displayView(int viewId, boolean isFirstTime) {
        Fragment fragment = null;
        llLast = llCurrent;

        switch (viewId) {
            case Constants.APP_MENU_FRAG_ID:
                llCurrent = llAppMap;
                fragment = new AppMapFragment();
                break;
            case Constants.PROFILE_FRAG_ID:
                llCurrent = llProfile;
                fragment = new ProfileFragment();
                break;
            case Constants.GIVE_FRAG_ID:
                llCurrent = llGive;
                fragment = new GiveFragment();
                break;
            case Constants.MYWB_FRAG_ID:
                llCurrent = llMyWb;
                fragment = new MyWbFragment();
                break;
            case Constants.HOME_FRAG_ID:
                llCurrent = llHome;
                fragment = new HomeFragment();
                break;
            case Constants.LIKE_FAV_FRAG_ID:
                llCurrent = llLikeFav;
                fragment = new LikeFavFragment();
                break;
            case Constants.IDEAS_FRAG_ID:
                llCurrent = llIdeas;
                fragment = new IdeasFragment();
                break;
            case Constants.SHARE_FRAG_ID:
                llCurrent = llShare;
                fragment = new ShareFragment();
                break;
            case Constants.TODO_FRAG_ID:
                llCurrent = llTodo;
                fragment = new TodoFragment();
                break;
            case Constants.INSPIRE_FRAG_ID:
                llCurrent = llInspire;
                fragment = new InspireFragment();
                break;
        }

        selectionColor(llCurrent, llLast, isFirstTime);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content_frame, fragment).commit();

    }

    private void selectionColor(LinearLayout llSelected, LinearLayout llDeselected, boolean isFirstTime) {
        llSelected.setBackgroundColor(getResources().getColor(R.color.red));
        if (!isFirstTime)
            llDeselected.setBackgroundColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
