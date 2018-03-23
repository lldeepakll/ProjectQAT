package com.deepak.projectqat.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.projectqat.R;
import com.deepak.projectqat.app.OnSelectedQuestionBackListener;
import com.deepak.projectqat.modal.Questions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Deepak Kumar on 07-03-2018.
 * Get all questions from server and set into recycler view
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {


    private Context context;
    private List<Questions> quesList;
    private HashMap<Integer,String> ans;
    private OnSelectedQuestionBackListener mCallBack;

    public QuestionAdapter(Context context, List<Questions> quesList, OnSelectedQuestionBackListener mCallBack) {
        this.context = context;
        this.quesList = quesList;
        ans =  new HashMap<>();
        this.mCallBack = mCallBack;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Questions quest = quesList.get(position);
        holder.q_no.setText("Q:"+quest.getId());
        holder.quest.setText(quest.getQuest());
        holder.ans_a.setText(quest.getAns_a());
        holder.ans_b.setText(quest.getAns_b());
        holder.ans_c.setText(quest.getAns_c());
        holder.ans_d.setText(quest.getAns_d());

        holder.questionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    String selected_answer = rb.getText().toString();
                    Toast.makeText(context, selected_answer, Toast.LENGTH_SHORT).show();

                    ans.put(quest.getId(),selected_answer);
                }
            }
        });

        mCallBack.answerCallBack(ans);

    }

    @Override
    public int getItemCount() {
        return quesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView q_no;
        public TextView quest;
        public RadioGroup questionGroup;
        public RadioButton ans_a;
        public RadioButton ans_b;
        public RadioButton ans_c;
        public RadioButton ans_d;

        public MyViewHolder(View view) {
            super(view);
            q_no = (TextView) view.findViewById(R.id.q_no_tv);
            quest = (TextView) view.findViewById(R.id.quest_tv);
            questionGroup = (RadioGroup) view.findViewById(R.id.radioGroupQues);
            ans_a = (RadioButton) view.findViewById(R.id.ans_a_tv);
            ans_b = (RadioButton) view.findViewById(R.id.ans_b_tv);
            ans_c = (RadioButton) view.findViewById(R.id.ans_c_tv);
            ans_d = (RadioButton) view.findViewById(R.id.ans_d_tv);
        }
    }

    public int getItemViewType(int position) {
        return position;
    }
}
