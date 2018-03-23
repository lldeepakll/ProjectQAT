package com.deepak.projectqat.activity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deepak.projectqat.R;
import com.deepak.projectqat.app.ConnectivityReceiver;
import com.deepak.projectqat.app.MyApplication;
import com.deepak.projectqat.dev.DeveloperDialog;
import com.deepak.projectqat.fragment.HistoryFragment;
import com.deepak.projectqat.fragment.ProfileFragment;
import com.deepak.projectqat.fragment.TopicFragment;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Deepak Kumar on 11-03-2018.
 * Topic List fetch from server
 * JSON
 */

public class TopicListActivity extends AppCompatActivity{

    @BindView(R.id.lbl_title)
    TextView lblTitle;

    @BindView(R.id.dev_icon)
    ImageView dev;

    private static final String devURL = "http://www.mocky.io/v2/5aac08962e00004900138fe2";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        // bind the view using butterknife
        ButterKnife.bind(this);

        // set custom font for title and quote
        Typeface mConcertOneTF = Typeface.createFromAsset(getAssets(), "fonts/Concert_one_regular.ttf");
        lblTitle.setTypeface(mConcertOneTF);

        boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected){
            dev.setVisibility(View.VISIBLE);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new TopicFragment());

        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = ProgressDialog
                        .show(TopicListActivity.this, "", "Loading Profile...");
                progressDialog.setCancelable(true);
                viewDeveloperProfile();
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_topic:
                    fragment = new TopicFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_history:
                    fragment = new HistoryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    private void viewDeveloperProfile() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,devURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("BLACK LABEL", response);
                        // stop animating Shimmer and hide the layout

                        try {
                            JSONObject object = new JSONObject(response);

                            String dev_name = object.optString("developer");
                            String mail = object.optString("mail");
                            String profile = object.optString("profile");
                            String picture = object.optString("picture");

                            Log.e("BLACK LABEL", dev_name);
                            Log.e("BLACK LABEL", mail);
                            Log.e("BLACK LABEL", profile);
                            Log.e("BLACK LABEL", picture);

                            DeveloperDialog devBox = new DeveloperDialog(TopicListActivity.this,dev_name,profile,mail,picture);
                            devBox.show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("response", volleyError.toString());
                        // stop animating Shimmer and hide the layout
                        progressDialog.dismiss();
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                    }
                }){

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }
}
