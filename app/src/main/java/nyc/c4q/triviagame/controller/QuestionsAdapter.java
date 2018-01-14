package nyc.c4q.triviagame.controller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.triviagame.Listener;
import nyc.c4q.triviagame.R;
import nyc.c4q.triviagame.model.Question;


public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {


    List<Question> questionsList = new ArrayList<>();
    private Listener listener;

    public QuestionsAdapter(Listener listener) {
     this.listener = listener;
}

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_layout, parent, false);
        return new QuestionsViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        Question question = questionsList.get(position);
        holder.onBind(question, listener);

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public void setData(List<Question> results) {
        questionsList = results;
    }



    class QuestionsViewHolder extends RecyclerView.ViewHolder {

        private TextView questionTextview;


        public QuestionsViewHolder(View itemView) {
            super(itemView);

            questionTextview = itemView.findViewById(R.id.question_itemview);

        }

        public void onBind(final Question question, final Listener listener) {
            questionTextview.setText(question.getQuestion());

            questionTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.addFragment(question);
                    int current = getAdapterPosition();
                    questionsList.remove(current);
                    notifyDataSetChanged();
                }
            });

        }

    }

}