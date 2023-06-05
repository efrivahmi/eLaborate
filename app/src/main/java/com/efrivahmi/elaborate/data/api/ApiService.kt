package com.efrivahmi.elaborate.data.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/signup")
    fun signUp(
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @POST("/signin")
    fun signIn(
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @POST("/signout")
    fun signOut(
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @POST("/forgot-password")
    fun forgotPassword(
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @POST("/verify-code")
    fun verifyCode(
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @POST("/reset-password")
    fun resetPassword(
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

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