package com.kanishthika.moneymatters.display.accounting.ui.financialGenerics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.components.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.components.MMOutlinedTextField
import com.kanishthika.moneymatters.config.components.MMTopAppBar
import com.kanishthika.moneymatters.config.utils.clickableOnce
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T: FinancialItem> AddOrUpdateItemScreen(
    viewModel: BaseFinancialModel<T>,
    modifier: Modifier = Modifier,
    navController: NavController,
    screenTitle: String,
    buttonText: String,
    isEnabled: Boolean,
    content: @Composable (() -> Unit),

) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = { MMTopAppBar(titleText = screenTitle) },
        bottomBar = {
            FinancialBottomBar(
                buttonText = buttonText,
                onClick = {
                    viewModel.addItemToDB {
                        navController.popBackStack()
                        focusManager.clearFocus()
                    }
                },
                isEnabled = isEnabled
            )
        }
    ) { paddingValues ->
        MMColumnScaffoldContentColumn(
            modifier = modifier,
            scaffoldPaddingValues = paddingValues,
            content = content
        )
    }
}

@Composable
fun MMOutlinedTextFieldWithState(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    supportingText: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    readOnly: Boolean = false
) {
    MMOutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        labelText = labelText,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        supportingText = supportingText,
        trailingIcon = trailingIcon,
        readOnly = readOnly
    )
}

@Composable
fun FinancialBottomBar(
    buttonText: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        containerColor = if (isEnabled)
            MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f),
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .clickableOnce(enabled = isEnabled) {
                onClick()
            }
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buttonText,
                color = if (isEnabled)
                    MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onTertiaryContainer.copy(0.5f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InformationText(modifier: Modifier){
    Box(
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = "* You can't edit expense name and amount, Because it is used in one or more transaction",
            color = MaterialTheme.colorScheme.error
        )
    }
}
