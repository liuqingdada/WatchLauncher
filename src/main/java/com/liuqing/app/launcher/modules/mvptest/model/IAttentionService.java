package com.liuqing.app.launcher.modules.mvptest.model;

import com.liuqing.app.launcher.modules.mvptest.bean.AttentionBean;
import com.liuqing.app.launcher.modules.mvptest.bean.RecommendTagBean;
import com.liuqing.app.launcher.modules.mvptest.bean.RecommendTagDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者: Dream on 16/9/29 20:51
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public interface IAttentionService {

    @GET("api_open.php?a=friend_recommend&c=user")
    Observable<AttentionBean> getAttentionList();

    @GET("api_open.php?a=category&c=subscribe")
    Observable<RecommendTagBean> getRecommendTag();

    @GET("api_open.php?a=list&c=subscribe")
    Observable<RecommendTagDetailBean> getRecommendTagDetail(
            @Query("category_id") String categoryId);

}
