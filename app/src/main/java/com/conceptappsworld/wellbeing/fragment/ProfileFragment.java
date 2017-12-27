package com.conceptappsworld.wellbeing.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.conceptappsworld.wellbeing.R;
import com.conceptappsworld.wellbeing.util.Constants;
import com.conceptappsworld.wellbeing.util.PrefsManager;
import com.conceptappsworld.wellbeing.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
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
        initObjects();
    }

    private void initObjects() {
        prefsManager = new PrefsManager(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        findViewByIds(view);
        return view;
    }

    private void findViewByIds(View view) {
        ivProfile = view.findViewById(R.id.iv_profile);
        tvName = view.findViewById(R.id.tv_name);
        tvMobile = view.findViewById(R.id.tv_name);
        tvName = view.findViewById(R.id.tv_name);
        tvName = view.findViewById(R.id.tv_name);
        tvName = view.findViewById(R.id.tv_name);
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
        displayText();
    }
}
