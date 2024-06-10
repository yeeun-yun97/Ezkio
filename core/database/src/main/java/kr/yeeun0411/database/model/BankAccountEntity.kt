package kr.yeeun0411.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "bank_accounts")
data class BankAccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "bank_account_code")
    val bankAccountCode: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "bank_name")
    val bankName: String,
    @ColumnInfo(name = "account_number")
    val accountNumber: String,
    @ColumnInfo(name = "account_holder")
    val accountHolder: String,
    @ColumnInfo(name = "use_qr")
    val useQr: Boolean
)