import io.github.minkik715.mkpay.common.SubTask

data class RechargingMoneyTask(
    val taskId: String,

    val taskName: String,

    val membershipId: Long,

    val subTasks: List<SubTask>,

    //법인 계좌
    val toBankName: String,

    // 법인 계좌 번호
    val toBankAccountNumber: String,

    val moneyAmount: Int
)
