package com.kanishthika.moneymatters.display.accounting.type.insurance.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.convertToLocalDate
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import com.kanishthika.moneymatters.display.accounting.data.PremiumMode
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.Insurance
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceType
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceTypeRepository
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.BaseFinancialModel
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.FinancialUiState
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class InsuranceModel @Inject constructor(
    repository: FinancialRepository<Insurance>,
    transactionRepository: TransactionRepository,
    insuranceTypeRepository: InsuranceTypeRepository,
) : BaseFinancialModel<Insurance>(repository, transactionRepository) {

    init {
        Log.d("TAG", "Insurance Model Started")
        viewModelScope.launch {
            insuranceTypeRepository.getAllInsuranceType().collectLatest { list ->
                _insuranceTypes.value = list + InsuranceType(0, "Add New")
            }
        }
    }

    override fun getAccountingType(): AccountingType = AccountingType.INVESTMENT

    override fun createNewItem(): Insurance {
        calculateTotalPaid {}
        return Insurance(
            id = 0,
            insuranceType = uiState.value.type.name,
            providerName = capitalizeWords(uiState.value.providerName),
            paidAmount = uiState.value.amount.toDouble(),
            premiumAmount = capitalizeWords(uiState.value.premiumAmount).toDouble(),
            policyNumber = if (uiState.value.identicalNo.isNotEmpty()){uiState.value.identicalNo.toInt()} else 0,
            startDate = uiState.value.startDate,
            maturityDate = uiState.value.endDate,
            sumAssured = if(uiState.value.sumAssured.isNotEmpty()){uiState.value.sumAssured.toDouble()} else 0.0,
            premiumMode = uiState.value.premiumMode.name
        )
    }

    private val _insuranceTypes = MutableStateFlow<List<InsuranceType>>(emptyList())
    val insuranceTypes: StateFlow<List<InsuranceType>> get() = _insuranceTypes.asStateFlow()

    fun isAnyFieldEmpty(insuranceUiState: FinancialUiState<Insurance>) : Boolean{
        return insuranceUiState.amount.isEmpty() || insuranceUiState.providerName.isEmpty() ||
                insuranceUiState.premiumAmount.isEmpty() || insuranceUiState.type.name.isEmpty()
    }

    fun updateInsuranceType(insuranceType: InsuranceType){
        _uiState.update {
            it.copy(
                type = insuranceType
            )
        }
    }

    fun updateProviderName(string: String){
        _uiState.update {
            it.copy(providerName = string)
        }
    }

    fun updateIdenticalNo(string: String){
        _uiState.update {
            it.copy(identicalNo = string)
        }
    }

    fun updatePremiumAmount(string: String){
        _uiState.update {
            it.copy(premiumAmount = string)
        }

    }

    fun updateStartDate(newDate: String){
        _uiState.update {
            it.copy(startDate = newDate)
        }
    }

    fun updateEndDate(newDate: String){
        _uiState.update {
            it.copy(endDate = newDate)
        }
    }

    fun updateSumAssured(string: String){
        _uiState.update {
            it.copy(sumAssured = string)
        }
    }

    fun updatePremiumMode(premiumMode: PremiumMode) {
        _uiState.update {
            it.copy(
                premiumMode = premiumMode
            )
        }
    }

    fun calculateTotalPaid(makeToast: () -> Unit) {
        if (_uiState.value.startDate.isNotEmpty() && _uiState.value.premiumAmount.isNotEmpty()){
            val startDate = convertToLocalDate(_uiState.value.startDate, "dd MMMM yyyy")
            val premiumAmount = _uiState.value.premiumAmount.toDouble()
            val premiumMode = _uiState.value.premiumMode

            val now = LocalDate.now()
            val monthsBetween = ChronoUnit.MONTHS.between(startDate, now)

            Log.d("TAG", "calculateTotalPaid: $monthsBetween")

            val periods = when (premiumMode) {
                PremiumMode.MONTHLY -> monthsBetween
                PremiumMode.QUARTERLY -> (monthsBetween / 3)
                PremiumMode.HALF_YEARLY -> (monthsBetween / 6)
                PremiumMode.YEARLY -> (monthsBetween / 12)
            }
            val totalPaid = (periods * premiumAmount) + premiumAmount

            Log.d("TAG", "calculateTotalPaid: periods = $periods")
            Log.d("TAG", "calculateTotalPaid: total = $totalPaid")
            _uiState.update {
                it.copy(
                    amount = totalPaid.toString()
                )
            }
        } else {
           makeToast()
        }
    }
}