package nyc.c4q.triviagame;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import nyc.c4q.triviagame.model.Question;


public class MyDialogueFragment extends DialogFragment implements View.OnClickListener {


    private static final String BUNDLE_KEY = "Question";
    private static final String SCORE_KEY = "score";
    private Question question;
    private int score;
    private ScoreListener scoreListener;

    static MyDialogueFragment newInstance(Question question, int score) {
        MyDialogueFragment f = new MyDialogueFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY, question);
        bundle.putInt(SCORE_KEY, score);
        f.setArguments(bundle);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = getArguments().getParcelable(BUNDLE_KEY);
        score = getArguments().getInt(SCORE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        TextView answer1TV = v.findViewById(R.id.answer1_itemview);
        TextView answer2TV = v.findViewById(R.id.answer2_itemview);
        TextView answer3TV = v.findViewById(R.id.answer3_itemview);
        TextView answer4TV = v.findViewById(R.id.answer4_itemview);
        TextView questionTV = v.findViewById(R.id.question_itemview);
        String[] randomizeAnswerArray = new String[4];

        List<String> randomizedQuestions = new ArrayList<>();
        randomizedQuestions.add(question.getIncorrect_answers().get(0));
        randomizedQuestions.add(question.getIncorrect_answers().get(1));
        randomizedQuestions.add(question.getIncorrect_answers().get(2));
        randomizedQuestions.add(question.getCorrect_answer());
        long seed = System.nanoTime();
        Collections.shuffle(randomizedQuestions, new Random(seed));


        answer1TV.setText("1. " + randomizedQuestions.get(0));
        answer2TV.setText("2. " + randomizedQuestions.get(1));
        answer3TV.setText("3. " + randomizedQuestions.get(2));
        answer4TV.setText("4. " + randomizedQuestions.get(3));
        questionTV.setText(question.getQuestion());
        answer1TV.setOnClickListener(this);
        answer2TV.setOnClickListener(this);
        answer3TV.setOnClickListener(this);
        answer4TV.setOnClickListener(this);

//        answer1TV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "You could do better! Wrong Answer!", Toast.LENGTH_LONG).show();
//                dismiss();
//            }
//        });
//
//        answer2TV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "You could do better! Wrong Answer!", Toast.LENGTH_LONG).show();
//                dismiss();
//
//            }
//        });
//
//        answer3TV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "You could do better! Wrong Answer!", Toast.LENGTH_LONG).show();
//                dismiss();
//
//            }
//        });
//
//        answer4TV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Congratulation! This is a Correct Answer!", Toast.LENGTH_LONG).show();
//                score += 1;
////                passing score back to Activity
//                scoreListener.passScore(score);
//                dismiss();
//
//            }
//        });
        return v;
    }

    public void passListener(ScoreListener scoreListener) {
        this.scoreListener = scoreListener;
    }


    @Override
    public void onClick(View view) {
        if (view instanceof TextView) {
            if (((TextView) view).getText().toString().contains(question.getCorrect_answer())) {
                Toast.makeText(getActivity(), "Congratulation! This is a Correct Answer!", Toast.LENGTH_LONG).show();
                score += 1;
//                passing score back to Activity
                scoreListener.passScore(score);
                dismiss();
            } else {
                Toast.makeText(getActivity(), "You could do better! Wrong Answer!", Toast.LENGTH_LONG).show();
                dismiss();
            }
        }
    }
}
