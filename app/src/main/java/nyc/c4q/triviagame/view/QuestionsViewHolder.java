package nyc.c4q.triviagame.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nyc.c4q.triviagame.Listener;
import nyc.c4q.triviagame.R;
import nyc.c4q.triviagame.model.Question;


public class QuestionsViewHolder extends RecyclerView.ViewHolder {

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
            }
        });

    }

}
