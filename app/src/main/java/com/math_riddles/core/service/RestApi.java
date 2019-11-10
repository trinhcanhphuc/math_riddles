package com.math_riddles.core.service;

import com.math_riddles.core.base.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author phuocns on 26/11/2018.
 */

public interface RestApi {
    @GET("test")
    Observable<BaseResponse> testApi();
}
