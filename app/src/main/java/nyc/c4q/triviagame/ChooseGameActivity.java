package nyc.c4q.triviagame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseGameActivity extends AppCompatActivity implements View.OnClickListener{
    private Button generalKnowledge, mathematics, history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        generalKnowledge = findViewById(R.id.game_1);
        mathematics = findViewById(R.id.game_2);
        history = findViewById(R.id.game_3);
        generalKnowledge.setOnClickListener(this);
        mathematics.setOnClickListener(this);
        history.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.game_1:
                sendIntent(50,9, "medium","multiple");
                break;
            case R.id.game_2:
                sendIntent(10,19, "medium","multiple");
                break;
            case R.id.game_3:
                sendIntent(50,23, "medium","multiple");
                break;

        }
    }

    public void sendIntent(int numOfQuestions, int categories, String difficulty, String type){
        Intent intent = new Intent(ChooseGameActivity.this, MainActivity.class);
        intent.putExtra("Number of Question", numOfQuestions);
        intent.putExtra("Category", categories);
        intent.putExtra("Difficulty", difficulty);
        intent.putExtra("Type", type);
        startActivity(intent);
    }

}
