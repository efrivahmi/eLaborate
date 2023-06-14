package com.efrivahmi.elaborate.data.api.ml

import com.efrivahmi.elaborate.data.model.Diagnose
import com.efrivahmi.elaborate.data.response.DiagnoseResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServiceMl {
    @POST("/predict")
    fun submitDiagnosticForm(
        @Body diagnose: Diagnose
    ): Call<DiagnoseResponse>

    @GET("/diagnose/{diagnosis_id}")
    fun getDiagnosticResults(
        @Query("results") diagnosisId: String
    ): Call<ResponseBody>
}