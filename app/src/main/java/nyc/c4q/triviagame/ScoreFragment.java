package nyc.c4q.triviagame;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoreFragment extends Fragment {

    private TextView scoreTV;
    View rootView;

    public ScoreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_score, container, false);
        scoreTV = rootView.findViewById(R.id.score_textview);
        Bundle bundle = getArguments();
        int score = bundle.getInt(MainActivity.SCORE);
        scoreTV.setText(String.valueOf(score));
        return rootView;
    }

    public void setData(int score) {
        scoreTV.setText(String.valueOf(score));
    }

}
