package com.roysoft.documentmanagementsystem.network

import `in`.cbslgroup.ucobank.model.response.DashboardResponse
import `in`.cbslgroup.ucobank.model.response.DefaultResponse
import `in`.cbslgroup.ucobank.model.response.DuplicateVO
import `in`.cbslgroup.ucobank.model.response.appointment.*
import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListItemResponse
import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListResponse
import `in`.cbslgroup.ucobank.model.response.dashboard.ProfileVO
import `in`.cbslgroup.ucobank.model.response.login.ChangePasswordResponse
import `in`.cbslgroup.ucobank.model.response.login.ForgotPasswordResponse
import `in`.cbslgroup.ucobank.model.response.login.LoginResponse
import `in`.cbslgroup.ucobank.model.response.login.RegisterResponse
import com.google.gson.GsonBuilder
import com.roysoft.documentmanagementsystem.utils.ApiUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface ApiInterface {


    /***************************************************************
     *                  Web Methods For the Dashboard Module         *
     ***************************************************************/

   /* @GET("Dashboard/{customerid}")
    suspend fun getDashboardData(@Path("customerid") customerid: String): DashboardResponse*/

    @GET("GetMethod/")
    suspend fun getDashboardData(@Query("url")url:String,): DashboardResponse

    /*@GET("Dashboard/{apttype}/{customerid}")
    suspend fun getAppointment(
        @Path("customerid") customerid: String,
        @Path("apttype") apyType: String
    ): AppointmentListItemResponse*/
    @GET("GetMethod/")
    suspend fun getAppointment(@Query("url")url:String,): AppointmentListItemResponse


    /***************************************************************
     *                  Web Methods For the Login Module         *
     ***************************************************************/
    @Headers( "Content-Type: application/json")
    @POST("PostMethod")
    suspend fun login(@Body data:RequestBody): LoginResponse

    /*@FormUrlEncoded
    @POST("Account/Register")
    suspend fun register(
        @Field("MobileNo") mobileNo: String,
        @Field("Name") name: String,
        @Field("Email") email: String
    ): RegisterResponse
*/
    @Headers( "Content-Type: application/json")
    @POST("PostMethod")
    suspend fun register(@Body data:RequestBody): RegisterResponse

    @GET("GetMethod/")
    suspend fun isDuplicateMobileNo(@Query("url")url:String,): DuplicateVO


    @Headers( "Content-Type: application/json")
    @POST("PostMethod")
    suspend fun verifyOtp(@Body data:RequestBody): DefaultResponse

    @Headers( "Content-Type: application/json")
    @POST("PostMethod")
    suspend fun changePwd(@Body data:RequestBody): ChangePasswordResponse


    @GET("GetMethod/")
    suspend fun resendOTP(@Query("url")url:String,): DefaultResponse


    @GET("GetMethod/")
    suspend fun forgotPassword(@Query("url")url:String,): ForgotPasswordResponse

    /***************************************************************
     *                  Web Methods For the Apointment Module         *
     ***************************************************************/


    @GET("GetMethod/")
    suspend fun getBranch(@Query("url")url:String,): BranchResponse

    @GET("GetMethod/")
    suspend fun getServices(@Query("url")url:String,): ServicesResponse

    @GET("GetMethod/")
    suspend fun getSlotByBranchWise(@Query("url")url:String,): TimeSlotVO


    @Headers( "Content-Type: application/json")
    @POST("PostMethod")
    suspend fun saveAppointment(@Body data:RequestBody): AppointmentVO


    @GET("GetMethod/")
    suspend fun getKioskIdbyBranch(@Query("url")url:String,): String

    @GET("GetMethod/")
    suspend fun getUserDetails(@Query("url")url:String,): ProfileVO

    @GET("GetMethod/")
    suspend fun getFilterAppointment(@Query("url")url:String,): AppointmentListItemResponse



    companion object {

        private var apiInterface: ApiInterface? = null
        private var retrofit: Retrofit? = null


        operator fun invoke(): ApiInterface {

            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY

            }
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (apiInterface == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(ApiUrl.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                apiInterface = retrofit!!.create(ApiInterface::class.java)
            }
            return apiInterface!!
        }
    }
}

