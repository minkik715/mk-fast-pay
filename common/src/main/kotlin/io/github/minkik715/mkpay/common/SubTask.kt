package io.github.minkik715.mkpay.common

data class SubTask(
    val membershipId: Long,
    val subTaskName: String,
    val taskType: String,
    var status: String,
){


    fun updateStatus(newStatus: String){{
        this.status = newStatus
    }}
}