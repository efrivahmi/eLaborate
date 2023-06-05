package com.efrivahmi.elaborate.data.response

data class Doctor(
    val doctors: List<Doctor>,
    val error: Boolean,
    val message: String,
    val token: String,
    val userId: String,
    val username: String
) {
    data class Doctor(
        val age: Int,
        val doctorId: String,
        val experiences: Int,
        val gender: String,
        val name: String,
        val specialty: String,
        val work_place: List<String>
    )
}