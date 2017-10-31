package com.kx.waimaidemo.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kx.waimaidemo.model.bean.Seller;
import com.kx.waimaidemo.model.net.ResponseInfo;
import com.kx.waimaidemo.model.net.WaiMaiService;
import com.kx.waimaidemo.ui.fragment.HomeFragment;
import com.kx.waimaidemo.uitls.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *  创建者:   KX
 *  创建时间:  2017/10/31 17:26
 *  描述：    TODO
 */
public class HomeFragmentPresenter {

    private HomeFragment mHomeFragment;
    private WaiMaiService mApi;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void loadhomeInfo() {
       /* new Retrofit
                .Builder()
                 .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.HOST)
                .build()
                .create(WaiMaiService.class)
                .loadHome()
                .enqueue(new Callback<ResponseInfo>() {
                    @Override
                    public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseInfo> call, Throwable t) {

                    }
                });*/

        //2部曲 创建对象，添加host、添加接口
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = retrofit.create(WaiMaiService.class);

        //3部曲 调用请求，
        Call<ResponseInfo> homeCall = mApi.loadHome();

        homeCall.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                if (response.isSuccessful()) {
                    ResponseInfo body = response.body();
                    String code = body.getCode();
                    if (code.equals("0")) {
                        //拿到数据
                        String jsonData = body.getData();
                        //解析数据
                        parserJson(jsonData);
                    } else if (code.equals("-1")) {

                    }
                } else {
                    //服务器超时 500 400服务器异常
                }
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {

            }
        });

    }

    private void parserJson(String jsonData) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String nearby = jsonObject.getString("nearbySellerList");
            List<Seller> nearbySellers = gson.fromJson(nearby, new TypeToken<List<Seller>>() {
            }.getType());

            String other = jsonObject.getString("otherSellerList");
            List<Seller> otherSellerLists = gson.fromJson(other, new TypeToken<List<Seller>>() {
            }.getType());
            if (nearbySellers != null && nearbySellers.size() > 0 && otherSellerLists != null && otherSellerLists.size() > 0) {
                mHomeFragment.loadhomeSuccess(nearbySellers, otherSellerLists);
            }else{
                mHomeFragment.loadhomeFailed("返回了空数据");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mHomeFragment.loadhomeFailed(e.getLocalizedMessage());
        }
    }


}
