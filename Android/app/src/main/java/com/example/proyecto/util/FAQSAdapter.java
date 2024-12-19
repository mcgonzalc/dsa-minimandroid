package com.example.proyecto.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.models.FAQ;
import com.example.proyecto.models.Item;

import java.util.ArrayList;
import java.util.List;

public class FAQSAdapter extends RecyclerView.Adapter<FAQSAdapter.ViewHolder> {
    private List<FAQ> questions = new ArrayList<>();
    private Context context;

    public FAQSAdapter(Context context, List<FAQ> questions) {
        this.context = context;
        this.questions = questions;
    }
    public void updateQuestions(List<FAQ> newQuestions) {
        this.questions.clear();
        if (newQuestions != null) {
            this.questions.addAll(newQuestions);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_faq, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FAQ question = questions.get(position);
        holder.FAQquestion.setText(question.getQuestion());
        holder.FAQanswer.setText(question.getAnswer());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView FAQquestion;
        TextView FAQanswer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FAQquestion = itemView.findViewById(R.id.faqQuestion);
            FAQanswer = itemView.findViewById(R.id.faqAnswer);
        }
    }
}