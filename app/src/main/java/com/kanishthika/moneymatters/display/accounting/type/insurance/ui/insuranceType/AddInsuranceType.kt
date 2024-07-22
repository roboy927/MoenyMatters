package com.kanishthika.moneymatters.display.accounting.type.insurance.ui.insuranceType

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.components.MMOutlinedTextField
import com.kanishthika.moneymatters.config.utils.clickableOnce

@Composable
fun AddInsuranceType(
    modifier: Modifier,
    insuranceTypeModel: InsuranceTypeModel,
    navController: NavController
) {

    val insuranceTypeName by insuranceTypeModel.name.collectAsState("")
    val buttonEnabled by insuranceTypeModel.buttonEnabled.collectAsState(false)

    Dialog(
        onDismissRequest = { navController.popBackStack() }
    ) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Column {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "New Insurance Type",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp, 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MMOutlinedTextField(
                        value = insuranceTypeName,
                        labelText = "Name",
                        onValueChange = { insuranceTypeModel.updateName(it) },
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    )

                }
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(
                            if (buttonEnabled)
                                MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.tertiaryContainer.copy(
                                0.5f
                            )
                        )
                        .clickableOnce(enabled = buttonEnabled) {
                            insuranceTypeModel.addInsuranceType {
                                navController.popBackStack()
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ADD",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (buttonEnabled)
                            MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onTertiaryContainer.copy(
                            0.5f
                        ),
                    )
                }

            }
        }
    }
}