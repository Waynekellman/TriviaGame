package nyc.c4q.triviagame;

import android.content.ClipData;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import nyc.c4q.triviagame.backend.QuestionsService;
import nyc.c4q.triviagame.controller.QuestionsAdapter;
import nyc.c4q.triviagame.model.Question;
import nyc.c4q.triviagame.model.QuestionsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Listener, ScoreListener {

    public static final String TAG = "response";
    public static final String SCORE = "score";
    public static final String VISIBILITY_KEY = "visibility";

    private int score = 0;
    private ScoreFragment scoreFragment;
    private FrameLayout frameLayout;
    private int visibility;

    private int numOfQuestions, category;
    private String difficulty, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.score_container);


        if(savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE);
            visibility = savedInstanceState.getInt(VISIBILITY_KEY);
            frameLayout.setVisibility(visibility);

        }

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final QuestionsAdapter questionsAdapter = new QuestionsAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(questionsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        scoreFragment = new ScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SCORE, score);
        scoreFragment.setArguments(bundle);

        Intent intent = getIntent();
        numOfQuestions = intent.getIntExtra("Number of Question",0);
        category = intent.getIntExtra("Category", 9);
        difficulty = intent.getStringExtra("Difficulty");
        type = intent.getStringExtra("Type");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.score_container, scoreFragment);
        fragmentTransaction.commit();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuestionsService questionsService = retrofit.create(QuestionsService.class);

        final Call<QuestionsResponse> questions = questionsService.getQuestions(numOfQuestions,category,difficulty, type);
        questions.enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(Call<QuestionsResponse> call, Response<QuestionsResponse> response) {
                if(response != null && response.isSuccessful()) {
                    Log.d(TAG, "on response: " + response.body().getResults());
                    List<Question> listOfQuestions = response.body().getResults();

                        questionsAdapter.setData(listOfQuestions);
                        questionsAdapter.notifyDataSetChanged();
                }
                else if (response.code() == 401) {
                    Log.d(TAG, "on response: response error");

                }
                else {
                    Log.d(TAG, "on response: other error");

                }
            }

            @Override
            public void onFailure(Call<QuestionsResponse> call, Throwable t) {
                Log.d(TAG, "on response: " + t.toString());
            }
        });

    }

    @Override
    public void addFragment(Question question) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                fragmentTransaction.remove(prev);
            }
            fragmentTransaction.addToBackStack(null);

            MyDialogueFragment newFragment = MyDialogueFragment.newInstance(question, score);
            if(!this.isFinishing()){
                newFragment.show(fragmentTransaction, "dialog");
                newFragment.passListener(this);

            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.score:
                if(frameLayout.getVisibility() == View.GONE) {

                    frameLayout.setVisibility(View.VISIBLE);
                }
                else{
                    frameLayout.setVisibility(View.GONE);
                }
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    receiving score from DialogueFragment and passing it to ScoreFragment
     */
    @Override
    public void passScore(int score) {
        this.score = score;
        scoreFragment.setData(score);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE, score);
        int isVisible = frameLayout.getVisibility();
        outState.putInt(VISIBILITY_KEY, isVisible);
    }
}

