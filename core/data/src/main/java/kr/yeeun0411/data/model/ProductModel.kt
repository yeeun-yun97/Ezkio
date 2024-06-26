package kr.yeeun0411.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class ProductModel(
    val productCode: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Int,
    val image: String,
    val stock: Int? = null,//null일 시 무제한
    val categoryCode: String? = null,
    val stopped: Boolean = false,
):Parcelable