package com.kanishthika.moneymatters.display.accounting.type.insurance.ui.insuranceType

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceType
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InsuranceTypeModel @Inject constructor(private val insuranceTypeRepository: InsuranceTypeRepository) :
    ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun updateName(string: String){
       _name.value = string
        _buttonEnabled.value = string.isNotEmpty()
    }

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled = _buttonEnabled.asStateFlow()

    fun addInsuranceType(onAdded: () -> Unit){
        viewModelScope.launch {
           val id =  insuranceTypeRepository.addInsuranceType(InsuranceType(0, _name.value))
            if (id> 0){
                onAdded()
                _name.value = ""
            } else {
                Log.d("TAG", "addInsuranceType: Not Added ")
            }
        }
    }

}