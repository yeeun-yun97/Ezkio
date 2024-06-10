package kr.yeeun0411.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kr.yeeun0411.database.model.BankAccountEntity

@Dao
interface BankAccountDao {

    @Query("SELECT * FROM bank_accounts LIMIT 1")
    fun getBankAccount(): Flow<BankAccountEntity?>

    @Upsert
    fun upsertBankAccount(bankAccount: BankAccountEntity)

}