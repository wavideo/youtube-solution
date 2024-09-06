package com.example.youtubesolution

interface testAPI {

    @GET("베이스 URL 뒷부분")
    suspend fun getData(
        @Query("요청파라미터") 변수명 : String,
        @Query("") 변수명 : Int = 10
    ): APIResponse

}