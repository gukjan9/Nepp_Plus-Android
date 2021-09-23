package com.gukjang.myfinalproject_210910.web

import com.gukjang.myfinalproject_210910.datas.BasicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ServerAPIService {
    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nickname: String) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") pw: String ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(
        @Field("provider") provider: String,
        @Field("uid") id: String,
        @Field("nick_name") name: String  ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/appointment")
    fun postRequestAppointment(
        @Field("title")  title: String,
        @Field("datetime") datetime:String,
        @Field("start_place") startPlaceName: String,
        @Field("start_latitude") startLat: Double,
        @Field("start_longitude") startLng: Double,
        @Field("place") placeName: String,
        @Field("latitude") lat: Double,
        @Field("longitude") lng: Double,
        @Field("friend_list") list : String
    ) : Call<BasicResponse>

    // GET - 약속 목록 가져오기
    @GET("/appointment")
    fun getRequestAppointmentList() : Call<BasicResponse>

    @GET("/user")
    fun getRequestMyInfo() : Call<BasicResponse>

    // POST - PUT - PATCH : FormData 활용
    // 회원 정보 수정 API
    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestMyInfo(
        @Field("field") field:String,
        @Field("value") value:String) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/place")
    fun postRequestAddMyPlace(
        @Field("name") name : String,
        @Field("latitude") lat : Double,
        @Field("longitude") lng : Double,
        @Field("is_primary") isPrimary : Boolean) : Call<BasicResponse>

    @GET("/user/place")
    fun getRequestMyPlaceList() : Call<BasicResponse>

    // 프로필 사진 첨부 -> Multipart 활용
    // Multipart 통신에서는 Field 를 담지 않고, MultipartBody.Part 양식으로 데이터 첨부
    @Multipart
    @PUT("/user/image")
    fun putRequestProfileImg(@Part profileImg : MultipartBody.Part) : Call<BasicResponse>

    // 친구 목록 불러오기
    @GET("/user/friend")
    fun getRequestFriendList(@Query("type") type : String) : Call<BasicResponse>

    // 닉네임으로 사용자 검색하기
    @GET("/search/user")
    fun getRequestSearchUser(@Query("nickname") keyword : String) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/friend")
    fun postRequestAddFriend(@Field("user_id") userId : Int) : Call<BasicResponse>

    @FormUrlEncoded
    @PUT("/user/friend")
    fun putRequestSendOkOrNoFriend(
        @Field("user_id") userId : Int,
        @Field("type") type : String) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/appointment/arrival")
    fun postRequestArrival(
        @Field("appointment_id") id : Int,
        @Field("latitude") lat : Double,
        @Field("longitude") lng : Double) : Call<BasicResponse>
}