package com.deepak.projectqat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deepak.projectqat.R;
import com.deepak.projectqat.app.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Deepak Kumar on 14-03-2018.
 * Results/check your correct and incorrect answers
 * helps user to know his/her error
 */

public class ResultsActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //Inject ButterKnife
        ButterKnife.bind(this);
    }

}
