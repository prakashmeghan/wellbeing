package com.conceptappsworld.wellbeing.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.conceptappsworld.wellbeing.R;
import com.conceptappsworld.wellbeing.SignupActivity;
import com.conceptappsworld.wellbeing.adapter.CategoryAdapter;
import com.conceptappsworld.wellbeing.asynctask.CategoryAsyncTask;
import com.conceptappsworld.wellbeing.model.AsyncResponse;
import com.conceptappsworld.wellbeing.model.Category;
import com.conceptappsworld.wellbeing.util.ConnectionDetector;
import com.conceptappsworld.wellbeing.util.Constants;
import com.conceptappsworld.wellbeing.util.PrefsManager;
import com.conceptappsworld.wellbeing.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements CategoryAdapter.CategoryAdapterOnClickHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private PrefsManager prefsManager;

    Activity activity;

    private TextDrawable drawable;

    private ImageView ivProfile;
    private TextView tvName, tvEmail, tvMobile, tvTown;
    private ProgressBar pbProfile;
    private RecyclerView rvProfile;
    private CategoryAdapter categoryAdapter;
    private TextView mErrorMessageDisplay;
    private ConnectionDetector internet;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activity = getActivity();

    }

    private void initObjects() {
        internet = new ConnectionDetector(activity);
        prefsManager = new PrefsManager(activity);
        categoryAdapter = new CategoryAdapter(activity, this);

        rvProfile.setAdapter(categoryAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        findViewByIds(view);

        initObjects();
        profileImage();



        if (!internet.isConnectingToInternet()) {
            showErrorMessage(getString(R.string.internet_not_working));
        }else {
            loadCategoryData();
        }
        return view;
    }

    private void loadCategoryData() {
        showCategoryDataView();

        pbProfile.setVisibility(View.VISIBLE);

        new CategoryAsyncTask(activity, new CategoryAsyncResponse()).execute();
    }

    public class CategoryAsyncResponse implements AsyncResponse{

        @Override
        public void processFinish(Object output) {
            pbProfile.setVisibility(View.INVISIBLE);
            if (output != null) {
                    ArrayList<Category> alCats = (ArrayList<Category>) output;
                    showCategoryDataView();
                    categoryAdapter.setCategoryData(alCats);
            } else {
                showErrorMessage(null);
            }
        }
    }

    private void showCategoryDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        rvProfile.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(String msg) {
        rvProfile.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        if (msg != null) {
            mErrorMessageDisplay.setText(msg);
        }
    }

    private void findViewByIds(View view) {
        ivProfile = view.findViewById(R.id.iv_profile);
        tvName = view.findViewById(R.id.tv_name);
        tvMobile = view.findViewById(R.id.tv_name);
        tvName = view.findViewById(R.id.tv_name);
        tvName = view.findViewById(R.id.tv_name);
        tvName = view.findViewById(R.id.tv_name);
        pbProfile = view.findViewById(R.id.pb_loading_indicator);
        rvProfile = view.findViewById(R.id.rv_profile);
        mErrorMessageDisplay = view.findViewById(R.id.tv_error_message_display);

        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(activity, 2);

        rvProfile.setLayoutManager(gridLayoutManager);

        rvProfile.setHasFixedSize(true);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(Category category) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void profileImage() {
        String f = "", l = "";
        String fName = prefsManager.getFname();
        String lName = prefsManager.getLname();
        if(fName != null && !fName.trim().equalsIgnoreCase("") && !fName.trim().isEmpty()){
            f = String.valueOf(fName.charAt(0)).toUpperCase();
        }

        if(lName != null && !lName.trim().equalsIgnoreCase("") && !lName.trim().isEmpty()){
            l = String.valueOf(lName.charAt(0)).toUpperCase();
        }
        String fl =  f + l;
        drawable = TextDrawable.builder()
                .buildRound(fl, Color.parseColor("#205eb3"));

        if(prefsManager.getProfilePic().equalsIgnoreCase("")){
            ivProfile.setImageDrawable(drawable);
        }else {
            String imgUrl = Constants.UPLOAD_URL + prefsManager.getProfilePic();
            Picasso.with(activity).load(imgUrl)
                    .placeholder(drawable)
                    .transform(new RoundedTransformation(Constants.RADIUS_PROFILE_IMAGE, 0))
                    .fit()
                    .centerCrop()
                    .into(ivProfile);
        }

        tvName.setText(fName + " " + lName);
//        displayText();
    }
}
