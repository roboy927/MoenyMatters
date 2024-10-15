package com.kanishthika.moneymatters.display.accounting.type.insurance.ui

import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.mmComposable.MMDatePickerInput
import com.kanishthika.moneymatters.config.mmComposable.MMDropDownMenu
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.clickableOnce
import com.kanishthika.moneymatters.config.utils.convertToLocalDate
import com.kanishthika.moneymatters.display.accounting.data.PremiumMode
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.Insurance
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.AddOrUpdateItemScreen
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.InformationText
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.MMOutlinedTextFieldWithState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInsuranceScreen(
    insuranceModel: InsuranceModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    insurance: Insurance? = null,
) {

    val insuranceUiState by insuranceModel.uiState.collectAsState()
    val insuranceTypeList by insuranceModel.insuranceTypes.collectAsState(initial = emptyList())
    var insuranceEditEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (insurance != null) {
            insuranceModel.updateName(insurance.name)
            insuranceModel.updateDescription(insurance.description)
            insuranceModel.updateAmount(insurance.amount.toString())
            insuranceModel.isEditEnabled(capitalizeWords(insurance.name)) {
                insuranceEditEnabled = it
            }
        }
    }

    AddOrUpdateItemScreen(
        viewModel = insuranceModel,
        modifier = modifier,
        navController = navController,
        screenTitle = "Add Insurance",
        buttonText = if (insurance == null) "Add" else "Update",
        isEnabled = insuranceModel.isAnyFieldEmpty(insuranceUiState).not(),
        onBottomBarClick = {
            insuranceModel.addItemToDB {
                navController.navigateUp()
            }
        }
    ) {
        MMDropDownMenu(
            list = insuranceTypeList,
            name = insuranceUiState.type.name,
            modifier = modifier,
            itemToName = { it.name },
            onItemSelected = {
                if (it.name == "Add New"){
                    navController.navigate(NavigationItem.AddInsuranceType.route)
                } else {
                    insuranceModel.updateInsuranceType(it)
                }
            },
            labelText = "Type *"
        )

        MMOutlinedTextFieldWithState(
            enabled = insuranceEditEnabled,
            value = insuranceUiState.providerName,
            onValueChange = { insuranceModel.updateProviderName(it) },
            labelText = "Provider Name *",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        MMOutlinedTextFieldWithState(
            value = insuranceUiState.identicalNo,
            onValueChange = { insuranceModel.updateIdenticalNo(it) },
            labelText = "Policy No.",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        MMOutlinedTextFieldWithState(
            enabled = insuranceEditEnabled,
            value = insuranceUiState.premiumAmount,
            onValueChange = { insuranceModel.updatePremiumAmount(it) },
            labelText = "Premium Amount *",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        MMOutlinedTextFieldWithState(
            enabled = insuranceEditEnabled,
            value = insuranceUiState.sumAssured,
            onValueChange = { insuranceModel.updateSumAssured(it) },
            labelText = "Sum Assured",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        MMDatePickerInput(
            modifier = modifier,
            date = insuranceUiState.startDate,
            labelText = "Start Date *",
            onDateSelected = { insuranceModel.updateStartDate(it) },
            initialDate = if (insuranceUiState.startDate.isEmpty()) null else convertToLocalDate(insuranceUiState.startDate, "dd MMMM yyyy"),
            initialDisplayMode = DisplayMode.Input
        )

        MMDatePickerInput(
            modifier = modifier,
            date = insuranceUiState.endDate,
            labelText = "Maturity Date",
            onDateSelected = { insuranceModel.updateEndDate(it) },
            initialDate = if (insuranceUiState.endDate.isEmpty()) null else convertToLocalDate(insuranceUiState.endDate, "dd MMMM yyyy"),
            initialDisplayMode = DisplayMode.Input
        )

        MMDropDownMenu(
            list = PremiumMode.values().toList(),
            name = insuranceUiState.premiumMode.name,
            modifier = modifier,
            itemToName = { it.name },
            onItemSelected = { insuranceModel.updatePremiumMode(it) },
            labelText = "Premium Mode"
        )
        MMOutlinedTextFieldWithState(
            modifier = modifier.onFocusChanged {
                if (it.isFocused) {
                        insuranceModel.calculateTotalPaid {
                            Toast.makeText(
                                context,
                                "Please fill Premium Amount & Start Date",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            },
            enabled = insuranceEditEnabled,
            value = insuranceUiState.amount,
            onValueChange = {  },
            labelText = "Paid Amount *",
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Calculate",
                    modifier = modifier.clickableOnce {
                            insuranceModel.calculateTotalPaid{
                                Toast.makeText(
                                    context,
                                    "Please fill Premium Amount & Start Date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        if (!insuranceEditEnabled) {
            InformationText(modifier = modifier)
        }
    }
}