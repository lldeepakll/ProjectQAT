package com.deepak.projectqat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.projectqat.R;
import com.deepak.projectqat.activity.SetsActivity;
import com.deepak.projectqat.modal.Topics;

import java.util.List;

/**
 * Created by HP LAPTOP on 01-03-2018.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder>{

    private Context context;
    private List<Topics> topList;

    public TopicAdapter(Context context, List<Topics> topList) {
        this.context = context;
        this.topList = topList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Topics tops = topList.get(position);
        holder.topic.setText(tops.getTopic());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ""+tops.getTopic(), Toast.LENGTH_SHORT).show();
                Intent intentToTopicSet = new Intent(context, SetsActivity.class);
                intentToTopicSet.putExtra("KEY_TOPIC_NAME",tops.getTopic());
                context.startActivity(intentToTopicSet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topic;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            topic = (TextView) view.findViewById(R.id.topic_lbl);
            cardView = (CardView)view.findViewById(R.id.card_view);

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Concert_one_regular.ttf");
            topic.setTypeface(custom_font);
        }
    }

}
