package com.deepak.projectqat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.deepak.projectqat.adapter.PopUpResultsAdapter;
import com.deepak.projectqat.adapter.QuestionAdapter;
import com.deepak.projectqat.app.ConnectivityReceiver;
import com.deepak.projectqat.app.FadingTextView;
import com.deepak.projectqat.app.MyApplication;
import com.deepak.projectqat.app.OnSelectedQuestionBackListener;
import com.deepak.projectqat.app.Utility;
import com.deepak.projectqat.modal.Questions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.deepak.projectqat.app.FadingTextView.MILLISECONDS;

/**
 * Created by Deepak kumar on 14-03-2018.
 * mobile based test activity -
 * question load from online/server
 */

public class MBTActivity extends AppCompatActivity implements OnSelectedQuestionBackListener {

    @BindView(R.id.lbl_topic_name)
    TextView lbl_topic_name;

    @BindView(R.id.lbl_set_no)
    TextView lbl_set_no;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;

    @BindView(R.id.submit_test_btn)
    Button mSubmitTest;

    @BindView(R.id.no_connection)
    RelativeLayout noConnectionLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.main_land_linear_layout)
    LinearLayout mainLandLL;

    private static final String mURL = "http://www.mocky.io/v2/5aa00f102e0000404374d36b";
    private static final String BLACK_LABEL = "BLACK LABEL";

    private List<Questions> question;
    private HashMap<Integer,String> store_correct_answer;
    private HashMap<Integer, String> selected_answer;
    private Map<Integer, String> get_all_ans;
    private PopupWindow popUpResults;

    private QuestionAdapter mAdapter;

    private String topic;
    private int set_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbt);
        // bind the view using butterknife
        ButterKnife.bind(this);
        //Set custom font
        Utility.setConcertTypeFace(this,lbl_topic_name);
        //Check For Internet Connection
        boolean isConnected = ConnectivityReceiver.isConnected();
        if(!isConnected){
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
        //initialize variables
        question = new ArrayList<>();
        selected_answer = new HashMap<>();
        store_correct_answer = new HashMap<>();
        get_all_ans = new TreeMap<>();

        mAdapter = new QuestionAdapter(this, question,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        fetchQuestions();

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

    private void fetchQuestions() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,mURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(BLACK_LABEL, response);
                        // stop animating Shimmer and hide the layout
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);

                        mSubmitTest.setVisibility(View.VISIBLE);
                        lbl_set_no.setVisibility(View.VISIBLE);

                        try {
                            JSONObject object = new JSONObject(response);

                            int paper_id = object.optInt("paper_id");
                            topic = object.optString("topic");
                            set_no = object.optInt("set_no");
                            int no_of_q = object.optInt("no_of_q");

                            lbl_topic_name.setText(topic);
                            lbl_set_no.setText("SET NO - "+set_no);

                            JSONArray array = object.getJSONArray("question");

                            List<Questions> quest = new Gson().fromJson(array.toString(), new TypeToken<List<Questions>>() {
                            }.getType());

                            // adding recipes to cart list
                            question.clear();
                            question.addAll(quest);

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


    @Override
    public void answerCallBack(HashMap<Integer, String> selected_answer) {
        this.selected_answer = selected_answer;
    }

    @OnClick(R.id.submit_test_btn)
    public void submitTest(View view){

        for(int q=0;q<question.size();q++){
            get_all_ans.put(question.get(q).getId(),question.get(q).getAns());
        }

        for (Map.Entry<Integer, String> entry : selected_answer.entrySet()) {
            System.err.println(BLACK_LABEL +">> Q: " + entry.getKey() + ", Value = " + entry.getValue());
        }

        int getNoOfQues = question.size();
        int getNoOfAns = selected_answer.size();

        System.err.println(BLACK_LABEL+">> Questions Attempted "+getNoOfAns+" Out of "+getNoOfQues);

        if(getNoOfAns>0){

            for (Map.Entry<Integer, String> entry : selected_answer.entrySet()) {
                String getAns = entry.getValue();
                int getQid = entry.getKey();

                for(int i = 0 ; i<question.size() ; i++){

                    if(getAns.equals(question.get(i).getAns())){
                        System.out.println(BLACK_LABEL+">> Q no. "+getQid);
                        System.out.println(BLACK_LABEL+">> "+question.get(i).getAns()+"=="+getAns);
                        store_correct_answer.put(getQid,getAns);
                    }
                }
            }
        }

        int correct_answer_size = store_correct_answer.size();
        int incorrect = getNoOfAns - correct_answer_size;

        double marks = incorrect * 0.25;
        marks = correct_answer_size - marks;

        //PARAMS : ("Questions Attempted 8 Out of 8","Correct : 8","Incorrect : 0","Marks : 8");
        showBottomSheetDialog("Questions Attempted "+getNoOfAns+" Out of "+getNoOfQues,"CORRECT : "+correct_answer_size,"INCORRECT : "+incorrect,"MARKS : "+marks);

    }

    public void showBottomSheetDialog(String attempt,String correct, String incorrect, String marks) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_result_dialog_layout, null);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        final FadingTextView score_card = (FadingTextView)view.findViewById(R.id.score_text_dialog);
        String[] texts = {"SCORE CARD","Click Here to See Results"};
        score_card.setTexts(texts);
        score_card.setTimeout(1500, MILLISECONDS);

        final LinearLayout main_land_bottom_ll = (LinearLayout)view.findViewById(R.id.main_land_bottom_ll);
        TextView attempt_text_view = (TextView)view.findViewById(R.id.attempt_text_view);
        TextView correct_text_view = (TextView)view.findViewById(R.id.correct_text_view);
        TextView incorrect_text_view = (TextView)view.findViewById(R.id.incorrect_text_view);
        TextView marks_text_view = (TextView)view.findViewById(R.id.marks_text_view);
        attempt_text_view.setText(attempt);
        correct_text_view.setText(correct);
        incorrect_text_view.setText(incorrect);
        marks_text_view.setText(marks);

        Utility.setConcertTypeFace(this,attempt_text_view);
        Utility.setConcertTypeFace(this,correct_text_view);
        Utility.setConcertTypeFace(this,incorrect_text_view);
        Utility.setConcertTypeFace(this,marks_text_view);

        TextView take_another_btn = (TextView) view.findViewById(R.id.take_another_btn);
        take_another_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        score_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater)MBTActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_results,null);
                popUpResults = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popUpResults.showAtLocation(main_land_bottom_ll, Gravity.CENTER, 0, 0);

                TextView titlePopup = (TextView)customView.findViewById(R.id.lbl_topic_name_pop_up);
                TextView setPopup = (TextView)customView.findViewById(R.id.lbl_set_no_pop_up);
                titlePopup.setText(topic);
                setPopup.setText("SET NO - "+set_no);

                //Sort answers key
                Map<Integer, String> sorted_answer = new HashMap<>();
                if(selected_answer.size()>0) {
                    sorted_answer = Utility.sortHashMap(selected_answer);
                    System.out.println(BLACK_LABEL + ">> Sort");
                    for (Map.Entry<Integer, String> entry : sorted_answer.entrySet()) {
                        System.err.println(BLACK_LABEL + ">> Q: " + entry.getKey() + ", Value = " + entry.getValue());
                    }
                }

                //Make a dummy values for answers
                Map<Integer, String> dummy_answers = new HashMap<>();
                for(int i=0;i<question.size();i++){
                    dummy_answers.put(question.get(i).getId(),"don\'t answered");
                }
                dummy_answers = Utility.sortMap(dummy_answers);
                System.out.println(BLACK_LABEL + ">> Dummy");
                for (Map.Entry<Integer, String> entry : dummy_answers.entrySet()) {
                    System.err.println(BLACK_LABEL + ">> Q: " + entry.getKey() + ", Value = " + entry.getValue());
                }

                //Final Results
                Map<Integer, String> resultedMap = new HashMap<>();
                resultedMap.putAll(dummy_answers);
                resultedMap.putAll(sorted_answer);
                resultedMap = Utility.sortMap(resultedMap);
                System.out.println(BLACK_LABEL + ">> Final Result");
                for (Map.Entry<Integer, String> entry : resultedMap.entrySet()) {
                    System.err.println(BLACK_LABEL + ">> Q: " + entry.getKey() + ", Value = " + entry.getValue());
                }


                RecyclerView recyclerView = (RecyclerView)customView.findViewById(R.id.recycler_view_pop_up);
                PopUpResultsAdapter mAdapter = new PopUpResultsAdapter(MBTActivity.this,question,resultedMap);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

                ImageView imageView = (ImageView)customView.findViewById(R.id.back_nav_arrow_pop_up);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUpResults.dismiss();
                    }
                });

            }
        });
    }

}
