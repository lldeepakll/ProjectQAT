package com.deepak.projectqat.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.deepak.projectqat.adapter.SetsAdapter;
import com.deepak.projectqat.app.MyApplication;
import com.deepak.projectqat.modal.Sets;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Deepak Kumar on 12-03-2018.
 * Sets Activity
 * Display all the set as per user click item (Topic)
 * get from server
 * send to server topic  name and id
 */



public class SetsActivity extends AppCompatActivity {

    @BindView(R.id.lbl_topic_name)
    TextView lbl_topic_name;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private static final String mURL = "http://www.mocky.io/v2/5aa6afdf310000a93ce71752";
    private static final String TAG = SetsActivity.class.getSimpleName();
    private List<Sets> mSets;
    private SetsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        // bind the view using butterknife
        ButterKnife.bind(this);

        // set custom font for title and quote
        Typeface mConcertOneTF = Typeface.createFromAsset(getAssets(), "fonts/Concert_one_regular.ttf");
        lbl_topic_name.setTypeface(mConcertOneTF);

        //Get Topic name from previous activity
        Bundle bundle = getIntent().getExtras();
        lbl_topic_name.setText(bundle.getString("KEY_TOPIC_NAME"));

        mSets = new ArrayList<>();

        mAdapter = new SetsAdapter(this, mSets);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);


        fetchSets();

    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    private void fetchSets() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,mURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, response);
                        // stop animating Shimmer and hide the layout
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);

                        try {
                            JSONObject object = new JSONObject(response);

                            String topic = object.optString("topic");

                            JSONArray array = object.getJSONArray("set");

                            List<Sets> set = new Gson().fromJson(array.toString(), new TypeToken<List<Sets>>() {
                            }.getType());

                            // adding recipes to cart list
                            mSets.clear();
                            mSets.addAll(set);

                            // refreshing recycler view
                            mAdapter.notifyDataSetChanged();

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
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
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
