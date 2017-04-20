package com.example.kimo.axdemo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ClassName:${TYPE_NAME} <br/>
 * Function: ${TODO} ADD FUNCTION. <br/>
 * Reason:   ${TODO} ADD REASON. <br/>
 * Date:     2017/4/16 11:47 <br/>
 *
 * @author 76dgs02
 * @see
 * @since JDK 1.6
 */


public interface Api {
    /**
     * 请求绑定小号接口
     */
    @POST("{version}/Accounts/{accountSid}/safetyCalls/chooseNumber")
    Observable<String> bindAX(@Path("version") String version, @Path("accountSid") String accountSid, @Query("sig") String SigParameter, @Body RequestBody body);

    /**
     * 请求解绑小号接口
     */
    @POST("{version}/Accounts/{accountSid}/safetyCalls/unbindNumber")
    Observable<String> unBindAX(@Path("version") String version, @Path("accountSid") String accountSid, @Query("sig") String SigParameter, @Body RequestBody body);

    /**
     * 请求在线主叫接口
     */
    @POST("{version}/Accounts/{accountSid}/safetyCalls/setCallerNumber")
    Observable<String> updataB(@Path("version") String version, @Path("accountSid") String accountSid, @Query("sig") String SigParameter, @Body RequestBody body);
}
