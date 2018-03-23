package com.deepak.projectqat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.deepak.projectqat.R;
import com.deepak.projectqat.adapter.TopicAdapter;
import com.deepak.projectqat.app.MyApplication;
import com.deepak.projectqat.app.MyDividerItemDecoration;
import com.deepak.projectqat.modal.Topics;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deeapk Kumar on 12-03-2018.
 * Show All Topics load from server
 */

public class TopicFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private static final String TAG = TopicFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Topics> topics;
    private TopicAdapter mAdapter;
    private static final String mURL = "http://www.mocky.io/v2/5a981f1f3000006f005c206a";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic, container, false);

        mShimmerViewContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        topics = new ArrayList<>();
        mAdapter = new TopicAdapter(getActivity(), topics);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        // making http call and fetching topic json
        fetchTopics();


        return view;
    }

    private void fetchTopics() {
        JsonArrayRequest request = new JsonArrayRequest(mURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getActivity(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<Topics> tops = new Gson().fromJson(response.toString(), new TypeToken<List<Topics>>() {
                        }.getType());

                        // adding recipes to cart list
                        topics.clear();
                        topics.addAll(tops);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();

                        // stop animating Shimmer and hide the layout
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
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
}
