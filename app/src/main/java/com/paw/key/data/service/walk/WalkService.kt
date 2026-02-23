package com.paw.key.data.service.walk

import retrofit2.http.POST

interface WalkService {
    @POST("walks/stream/start")
    suspend fun startWalk(

    )

    @POST("walks/stream/point")
    suspend fun pointWalk(

    )

    @POST("routes/{routeId}/finish")
    suspend fun finishWalk(
        
    )
}