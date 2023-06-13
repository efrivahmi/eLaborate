package com.efrivahmi.elaborate.data.response

data class DiagnoseResponse(
    val error: Boolean,
    val message: String,
    val userId: String,
    val username: String,
    val token: String,
    val diagnosisData: DiagnosisData
)

data class DiagnosisData(
    val diagnosisId: String,
    val age: Int,
    val sex: String,
    val rbc: Double,
    val hgb: Double,
    val hct: Double,
    val mcv: Double,
    val mch: Double,
    val mchc: Double,
    val rdwCv: Double,
    val wbc: Double,
    val neu: Double,
    val lym: Double,
    val mo: Double,
    val eos: Double,
    val ba: Double
)
