package com.kx.waimaidemo.model.net;

import retrofit2.Call;
import retrofit2.http.GET;

/*
 *  创建者:   KX
 *  创建时间:  2017/10/31 18:16
 *  描述：    TODO
 */
public interface WaiMaiService {

    @GET("home")
    Call<ResponseInfo> loadHome();
}
