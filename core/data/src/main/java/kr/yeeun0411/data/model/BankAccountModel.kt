package kr.yeeun0411.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class BankAccountModel(
    val bankAccountCode: String = UUID.randomUUID().toString(),
    val bankName: String,
    val accountNumber: String,
    val accountHolder: String,
    val useQr: Boolean = true
):Parcelable