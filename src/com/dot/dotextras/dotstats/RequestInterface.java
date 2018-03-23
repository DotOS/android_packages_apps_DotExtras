package com.dot.dotextras.dotstats;

import com.dot.dotextras.dotstats.models.ServerRequest;
import com.dot.dotextras.dotstats.models.ServerResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ishubhamsingh on 25/9/17.
 */

public interface RequestInterface {

    @POST("dotstats_api/")
    Observable<ServerResponse> operation(@Body ServerRequest request);

}