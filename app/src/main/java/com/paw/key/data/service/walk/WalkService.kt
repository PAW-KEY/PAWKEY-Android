package com.paw.key.data.service.walk

import com.paw.key.data.dto.response.BaseResponse
import retrofit2.http.POST

interface WalkService {
    @POST("walks/stream/start")
    suspend fun startWalk(

    ) : BaseResponse

    @POST("walks/stream/point")
    suspend fun pointWalk(

    ) : BaseResponse

    @POST("routes/{routeId}/finish")
    suspend fun finishWalk(
        
    ) : BaseResponse
}
