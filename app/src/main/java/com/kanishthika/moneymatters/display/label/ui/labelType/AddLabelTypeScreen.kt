package com.kanishthika.moneymatters.display.label.ui.labelType

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.config.mmComposable.MMOutlinedTextField
import com.kanishthika.moneymatters.config.utils.clickableOnce

@Composable
fun AddLabelTypeScreen(
    modifier: Modifier,
    addLabelTypeModel: AddLabelTypeModel = hiltViewModel(),
    onBack: () -> Unit,

) {

    val labelTypeName by addLabelTypeModel.name.collectAsStateWithLifecycle("")
    val buttonEnabled by addLabelTypeModel.buttonEnabled.collectAsStateWithLifecycle(false)

    Dialog(
        onDismissRequest = { onBack() }
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
                        text = "New Label Type",
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
                        value = labelTypeName,
                        labelText = "Name",
                        onValueChange = { addLabelTypeModel.updateName(it) },
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        keyboardOptions = KeyboardOptions(KeyboardCapitalization.Words)
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
                            addLabelTypeModel.addLabelType {
                                onBack()
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