package kr.yeeun0411.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.yeeun0411.data.converters.convertToEntity
import kr.yeeun0411.data.converters.convertToModel
import kr.yeeun0411.data.model.BankAccountModel
import kr.yeeun0411.database.dao.BankAccountDao
import javax.inject.Inject

class SettingRepository @Inject constructor(
    private val bankAccountDao: BankAccountDao,
) {
    fun getBankAccount(): Flow<BankAccountModel?> {
        return bankAccountDao.getBankAccount().map {
            it?.convertToModel()
        }
    }

    fun upsertBankAccount(bankAccount: BankAccountModel) {
        bankAccountDao.upsertBankAccount(bankAccount.convertToEntity())
    }


}