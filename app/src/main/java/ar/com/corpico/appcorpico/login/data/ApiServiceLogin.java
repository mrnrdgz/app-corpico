package ar.com.corpico.appcorpico.login.data;

import ar.com.corpico.appcorpico.login.domain.entity.PostLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/**
 * Created by sistemas on 14/11/2017.
 */

public interface ApiServiceLogin {
    @POST("/token")
    @FormUrlEncoded
    Call<PostLogin> getToken(@Field("username") String userText,
                             @Field("password") String passwordText,
                             @Field("grant_type") String grantTypeText);

}

