package com.efrivahmi.elaborate.data.api

import com.efrivahmi.elaborate.data.model.ForgotPassword
import com.efrivahmi.elaborate.data.model.ResetPassword
import com.efrivahmi.elaborate.data.model.UserLogin
import com.efrivahmi.elaborate.data.model.UserRegister
import com.efrivahmi.elaborate.data.model.Verify
import com.efrivahmi.elaborate.data.response.FpResponse
import com.efrivahmi.elaborate.data.response.RpResponse
import com.efrivahmi.elaborate.data.response.SignIn
import com.efrivahmi.elaborate.data.response.SignUp
import com.efrivahmi.elaborate.data.response.VerifyCode
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/signup")
    fun signUp(
        @Body user: UserRegister
    ): Call<SignUp>

    @POST("/signin")
    fun signIn(
        @Body user: UserLogin
    ): Call<SignIn>

    @POST("/forgot-password")
    fun forgotPassword(
        @Body user: ForgotPassword
    ): Call<FpResponse>

    @POST("/verify-code")
    fun verifyCode(
        @Body user: Verify
    ): Call<VerifyCode>

    @POST("/reset-password")
    fun resetPassword(
        @Body request: ResetPassword
    ): RpResponse

    @GET("/{userId}")
    fun getHome(
        @Path("userId") userId: String
    ): Call<ResponseBody>

    @GET("/{userId}/profile")
    fun getProfile(
        @Path("userId") userId: String
    ): Call<ResponseBody>

    @POST("/{userId}/profile/edit")
    fun editProfile(
        @Path("userId") userId: String,
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @POST("/{userId}/diagnose")
    fun submitDiagnosticForm(
        @Path("userId") userId: String,
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @GET("/{userId}/diagnose")
    fun getDiagnosticResults(
        @Path("userId") userId: String,
        @Query("results") diagnosisId: String
    ): Call<ResponseBody>

    @POST("/private/{privateKey}/add-doctor")
    fun addDoctor(
        @Path("privateKey") privateKey: String,
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @GET("/{userId}/doctor-list")
    fun getDoctorList(
        @Path("userId") userId: String
    ): Call<ResponseBody>

    @POST("/private/{privateKey}/add-workout")
    fun addWorkout(
        @Path("privateKey") privateKey: String,
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @GET("/{userId}/workout-list")
    fun getWorkoutList(
        @Path("userId") userId: String
    ): Call<ResponseBody>
}