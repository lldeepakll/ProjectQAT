package com.deepak.projectqat.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deepak.projectqat.R;
import com.deepak.projectqat.modal.Questions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Deepak Kumar on 15-03-2018.
 * Set Results in Pop Up List
 */

public class PopUpResultsAdapter extends RecyclerView.Adapter<PopUpResultsAdapter.MyViewHolder> {


    private Context context;
    private List<Questions> quesList;
    private Map<Integer,String> selected_answer;
    private static final String BLACK_LABEL = "BLACK LABEL";

    public PopUpResultsAdapter(Context context,List<Questions> quesList,Map<Integer,String> selected_answer) {
        this.context = context;
        this.quesList = quesList;
        this.selected_answer = selected_answer;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pop_up_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Questions quest = quesList.get(position);
        holder.q_no_tv.setText("Q:"+quest.getId());
        holder.question_tv.setText(quest.getQuest());
        holder.corr_ans_tv.setText(quest.getAns());

        holder.your_ans_tv.setText(selected_answer.get(position+1));
    }

    @Override
    public int getItemCount() {
        return quesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView q_no_tv, question_tv, corr_ans_tv, your_ans_tv;

        public MyViewHolder(View view) {
            super(view);

            cardView = (CardView)view.findViewById(R.id.card_view);
            q_no_tv = (TextView)view.findViewById(R.id.q_no_tv);
            question_tv = (TextView)view.findViewById(R.id.quest_tv);
            corr_ans_tv = (TextView)view.findViewById(R.id.corr_ans_tv);
            your_ans_tv = (TextView)view.findViewById(R.id.your_ans_tv);

        }
    }

    public int getItemViewType(int position) {
        return position;
    }
}
