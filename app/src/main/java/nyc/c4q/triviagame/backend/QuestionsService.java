package nyc.c4q.triviagame.backend;

import nyc.c4q.triviagame.model.QuestionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface QuestionsService {

    @GET("api.php")
    Call<QuestionsResponse> getQuestions(@Query("amount") int amount, @Query("category") int categoryInt ,@Query("difficulty") String difficulty, @Query("type") String type);
}
