package com.kanishthika.moneymatters.display.label.ui.labelType

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.label.data.labelType.LabelType
import com.kanishthika.moneymatters.display.label.data.labelType.LabelTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLabelTypeModel @Inject constructor(private val labelTypeRepository: LabelTypeRepository) :
    ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun updateName(string: String){
        _name.value = string
        _buttonEnabled.value = string.isNotEmpty()
    }

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled = _buttonEnabled.asStateFlow()

    fun addLabelType(onAdded: () -> Unit){
        viewModelScope.launch {
            val id =  labelTypeRepository.addLabelType(LabelType(0, capitalizeWords(_name.value) ))
            if (id> 0){
                onAdded()
                _name.value = ""
            } else {
                Log.d("TAG", "addLabelType: Not Added ")
            }
        }
    }
}