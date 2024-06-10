package kr.yeeun0411.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class CategoryModel(
    val categoryCode: String = UUID.randomUUID().toString(),
    val name: String,
):Parcelable