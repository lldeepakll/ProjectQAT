package com.deepak.projectqat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.projectqat.R;
import com.deepak.projectqat.activity.MBTActivity;
import com.deepak.projectqat.modal.Sets;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Deepak Kumar on 12-03-2018.
 * Sets Adapter class
 */

public class SetsAdapter extends RecyclerView.Adapter<SetsAdapter.MyViewHolder> {

    private Context context;
    private List<Sets> setList;

    public SetsAdapter(Context context, List<Sets> setList) {
        this.context = context;
        this.setList = setList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sets_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Sets sets = setList.get(position);
        holder.set_no.setText("Set No. : "+sets.getSet_no());
        holder.q_no.setText("No of Question : "+sets.getNo_of_q());
        holder.uploaded_on.setText(sets.getUploaded_on());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+sets.getSet_no(), Toast.LENGTH_SHORT).show();
                Intent intentToMBT = new Intent(context, MBTActivity.class);
                context.startActivity(intentToMBT);
            }
        });



    }

    @Override
    public int getItemCount() {
        return setList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView q_no;
        public TextView set_no;
        public TextView uploaded_on;
        public CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            q_no = (TextView) view.findViewById(R.id.q_no_tv);
            set_no = (TextView) view.findViewById(R.id.set_no);
            uploaded_on = (TextView) view.findViewById(R.id.uploaded_on_tv);
            card_view = (CardView) view.findViewById(R.id.card_view);

            Typeface watch_word_font = Typeface.createFromAsset(context.getAssets(), "fonts/watchword_thin.otf");
            q_no.setTypeface(watch_word_font);
            uploaded_on.setTypeface(watch_word_font);
            Typeface concert_font = Typeface.createFromAsset(context.getAssets(), "fonts/Concert_one_regular.ttf");
            set_no.setTypeface(concert_font);

        }
    }
}
