package nyc.c4q.triviagame.backend;

import nyc.c4q.triviagame.model.QuestionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;


public interface QuestionsService {

    @GET("api.php?amount=50&category=9&difficulty=medium&type=multiple")
    Call<QuestionsResponse> getQuestions();
}
