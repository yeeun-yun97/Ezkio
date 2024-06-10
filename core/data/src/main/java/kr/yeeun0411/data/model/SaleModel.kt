package kr.yeeun0411.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class SaleModel(
    val saleCode: String = UUID.randomUUID().toString(),
    val timeStamp: Long,//epochMillis
): Parcelable
