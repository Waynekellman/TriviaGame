package nyc.c4q.triviagame.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by olgakoleda on 1/13/18.
 */

public class Question implements Parcelable{

    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    public String getQuestion() {
        return question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeString(this.correct_answer);
        dest.writeStringList(this.incorrect_answers);
    }

    public Question() {
    }

    protected Question(Parcel in) {
        this.question = in.readString();
        this.correct_answer = in.readString();
        this.incorrect_answers = in.createStringArrayList();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
