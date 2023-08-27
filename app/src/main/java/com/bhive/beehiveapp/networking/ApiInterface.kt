package com.bhive.beehiveapp.networking

import com.bhive.beehiveapp.models.*
import com.bhive.beehiveapp.viewmodel.UpdateIssueStatusViewModel
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface<T>
{
    @POST("auth/login")
    fun signInUser(@Body jsonObject: JsonObject): Call<DataModal>

    @POST("auth/register")
    fun registerUser(@Body jsonObject: JsonObject): Call<DataModal>

    @POST("auth/change-password")
    fun changePassword(@Body jsonObject: JsonObject): Call<changePasswordModelClass>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body jsonObject: JsonObject): Call<DataModal>

    @GET("societies")
    fun getSocitiesList() : Call<SocietiesDetails>

    @GET("societies/{id}")
    fun getBuidlingDetails(@Path(value = "id" ) id: String ) : Call<GetBuildingDetails>

    @GET("societies/{id}/events")
    fun getEventsList(
        @Path (value =  "id") id : String,
        @Query ("start") startDate : Long,
        @Query ("end") endDate : Long,
    ) : Call<EventsDetails>

 @GET("societies/{id}/events/upcoming")
    fun getMonthlyEventsList(@Path (value = "id") id : String,
        @Query ("month") currentMonth : Int,
    ) : Call<MonthlyEventsDetails>

    @GET("auth/get-profile")
    fun getProfile() : Call<ProfileDetails>

    @POST("auth/update-profile")
    fun updateProfile(@Body jsonObject: JsonObject) : Call<ProfileDetails>

    @POST("auth/reset-password")
    fun resetPassword(@Body jsonObject: JsonObject) : Call<DataModal>

//    @POST("societies/1/issues")
//    fun addNewIssue(@Body jsonObject: JsonObject) : Call<AddIssueDetails>

    @Multipart
    @POST("societies/{id}/issues")
    fun addNewIssue(
        @Path(value = "id") id: String,
        @Part ("title") title: RequestBody?,
        @Part ("description") description: RequestBody?,
        @Part images: MultipartBody.Part?,
        @Part images2: MultipartBody.Part?,
        @Part images3: MultipartBody.Part?,
        @Part images4: MultipartBody.Part?,
        @Part images5: MultipartBody.Part?,
    ) : Call<AddIssueDetails>

    @GET("societies/{id}/issues")
    fun getReportedIssues(@Path(value = "id" ) id: String) : Call<IssuesDetails>

    @GET("societies/{id}/issues")
    fun getTotalReportedIssues(@Path(value = "id" ) id: String,
                               @Query ("ownerType") onwerType : Int,
                               @Query ("page") page : Int) : Call<IssuesDetails>

    @GET("societies/{id}/issues")
    fun getIssues(@Path(value = "id" ) id: String,
                  @Query ("status") status : Int,
                  @Query ("page") page : Int,
                  @Query ("ownerType") onwerType : Int
                  ): Call<IssuesDetails>

    @GET("societies/{id}/issues")
    fun getIssuesWithOwner(@Path(value = "id" ) id: String,
                  @Query ("ownerType") onwerType : Int,
                  @Query ("page") page : Int
    ): Call<IssuesDetails>

    @GET("societies/{ids}/issues/{id}")
    fun getAnIssue( @Path(value = "ids" ) ids: String ,
    @Path (value = "id") id: String) : Call<GetIssueDetails>

    @DELETE("notifications/{id}")
    fun deleteNotification( @Path(value = "id") id: String ) : Call<NotificationsDetails>

    @GET("notifications")
    fun getNotifications() : Call<NotificationsDetails>

    @GET("societies/{id}/rules")
    fun getBuildingRules(@Path(value = "id") id : String) : Call<BuildingRulesModelClass>

    @GET("societies/{id}/facilities")
    fun getBuildingFacilities(@Path(value = "id") id : String) : Call<BuildingFaciltiesModelClass>

    @PATCH("issues/{id}/messages")
    fun messageRead( @Path(value = "id")  id : String) : Call<MessageStatus>

    @POST("issues/{id}/messages")
    fun sendMessageToManager(@Path(value = "id") id : String
    , @Body jsonObject: JsonObject) : Call<MessageManagerDetails>

    @DELETE("auth/delete-profile")
    fun deleteAccount() : Call<DeleteAccountResponse>

    @GET("societies/{id}/events")
    fun getAllEvents(@Path(value = "id") id : String,
                     @Query ("start") todayDate : Long) : Call<AllEventsModelClass>

    @Multipart
    @PUT("societies/{id}/issues/android/{idI}")
    fun updateIssue(
        @Path(value = "id") id: String?,
        @Path(value = "idI") idI: String?,
        @Part("files") files: RequestBody?,
        @Part ("title") title: RequestBody?,
        @Part ("description") description: RequestBody?,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?
//        @Part image3: MultipartBody.Part?,
//        @Part image4: MultipartBody.Part?,
//        @Part image5: MultipartBody.Part?
        ) : Call<UpdateIssueModelClass>

    @PUT("societies/{idS}/issues/{idI}/change-status")
    fun updateIssueStatus(@Path(value = "idS") ids: String,
    @Path(value = "idI") idI: String,
                          @Body jsonObject: JsonObject
    ) :Call<UpdateIssueStatusModelClass>

    @PUT("notifications")
    fun notificationsRead(): Call<NotificationsReadModelClass>

    @GET("notifications/count")
    fun getNotificationsCount() : Call<NotificationsCountModelClass>

    @POST("push-tokens")
    fun updateFcmToken(@Body jsonObject: JsonObject) : Call<UpdateTokenModelClass>
}