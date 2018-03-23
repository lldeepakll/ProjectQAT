package com.deepak.projectqat;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deepak.projectqat.activity.TopicListActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lbl_title)
    TextView lblTitle;

    @BindView(R.id.lbl_quote)
    TextView lblQuote;

    @BindView(R.id.layout_top_box)
    LinearLayout llTop;

    @BindView(R.id.layout_bottom_box)
    LinearLayout llDown;

    @BindView(R.id.input_layout_email)
    TextInputLayout input_layout_email;

    @BindView(R.id.input_layout_password)
    TextInputLayout input_layout_password;

    @BindView(R.id.input_layout_phone)
    TextInputLayout input_layout_phone;

    @BindView(R.id.sign_in_button)
    Button mSignIn;

    @BindView(R.id.sign_up_button)
    Button mSignUp;

    @BindView(R.id.text_sign_up)
    TextView askSignUp;

    @BindView(R.id.text_sign_in)
    TextView askSignIn;

    private Animation mTopToDown,mDownToUp,mSwipeLeft,mSwipeRight;

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // bind the view using butterknife
        ButterKnife.bind(this);

        // set custom font for title and quote
        Typeface mAlluraTF = Typeface.createFromAsset(getAssets(), "fonts/allura_regular.ttf");
        Typeface mConcertOneTF = Typeface.createFromAsset(getAssets(), "fonts/Concert_one_regular.ttf");
        lblQuote.setTypeface(mAlluraTF);
        lblTitle.setTypeface(mConcertOneTF);
        input_layout_email.setTypeface(mConcertOneTF);
        input_layout_password.setTypeface(mConcertOneTF);
        input_layout_phone.setTypeface(mConcertOneTF);
        mSignIn.setTypeface(mConcertOneTF);
        mSignUp.setTypeface(mConcertOneTF);

        // animation for top to down llTop
        mTopToDown = AnimationUtils.loadAnimation(this,R.anim.up_to_down);
        llTop.setAnimation(mTopToDown);

        // animation for down to top llDown
        mDownToUp = AnimationUtils.loadAnimation(this,R.anim.down_to_up);
        llDown.setAnimation(mDownToUp);

        //animation
        mSwipeRight = AnimationUtils.loadAnimation(this, R.anim.swipe_right);
        mSwipeLeft = AnimationUtils.loadAnimation(this, R.anim.swipe_left);
        mSwipeLeft.setAnimationListener(animationListener);
    }

    //on click not an user text,
    //visibility gone of sign in button or not an user text view
    //visibility visible of phone edit text or sign up button
    @OnClick(R.id.text_sign_up)
    public void onClickNotAnUser(View view) {
        mSignIn.setVisibility(View.GONE);
        mSignUp.setVisibility(View.VISIBLE);
        askSignIn.setVisibility(View.VISIBLE);
        askSignUp.setVisibility(View.GONE);
        input_layout_phone.startAnimation(mSwipeRight);
        input_layout_phone.setVisibility(View.VISIBLE);
    }

    //on click already user text
    //visibility gone sign up button or already user text view or input layout phone
    //visibility visible of sign in button or not an user text
    @OnClick(R.id.text_sign_in)
    public void onClickAlreadyUser(View view){
        mSignIn.setVisibility(View.VISIBLE);
        mSignUp.setVisibility(View.GONE);
        askSignIn.setVisibility(View.GONE);
        askSignUp.setVisibility(View.VISIBLE);
        input_layout_phone.startAnimation(mSwipeLeft);
        input_layout_phone.setVisibility(View.GONE);
    }

    @OnClick(R.id.sign_in_button)
    public void onClickSignIn(View view){
        Intent intent = new Intent(this, TopicListActivity.class);
        startActivity(intent);
        finish();
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener(){

        @Override
        public void onAnimationEnd(Animation animation) {
            input_layout_phone.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }};
}
