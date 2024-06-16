package com.kanishthika.moneymatters.config.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction

@Composable
fun MMOutlinedTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    value: String,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    labelText: String,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    disableColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedColor: Color = MaterialTheme.colorScheme.secondary,
    containerColor: Color = MaterialTheme.colorScheme.background,
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current
    ) {


    OutlinedTextField(
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        modifier = modifier,
        label = { Text(text = labelText) },
        supportingText = supportingText,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = 1,
        textStyle = textStyle,
        isError = isError,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedTextColor = focusedColor,
            unfocusedTextColor = unfocusedColor,
            disabledTextColor = disableColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            cursorColor = focusedColor,
            focusedIndicatorColor = focusedColor,
            unfocusedIndicatorColor = unfocusedColor,
            disabledIndicatorColor = disableColor,
            focusedLeadingIconColor = focusedColor,
            unfocusedLeadingIconColor = unfocusedColor,
            focusedTrailingIconColor = focusedColor,
            unfocusedTrailingIconColor = unfocusedColor,
            disabledTrailingIconColor = disableColor,
            focusedLabelColor = focusedColor,
            unfocusedLabelColor = unfocusedColor,
            disabledLabelColor = disableColor,
            focusedPlaceholderColor = containerColor,
            unfocusedPlaceholderColor = containerColor,
            disabledPlaceholderColor = containerColor,
            focusedSupportingTextColor = focusedColor,
            unfocusedSupportingTextColor = unfocusedColor,
            disabledSupportingTextColor = disableColor,
            focusedPrefixColor = focusedColor,
            unfocusedPrefixColor = unfocusedColor,
            disabledPrefixColor = disableColor,
            focusedSuffixColor = focusedColor,
            unfocusedSuffixColor = unfocusedColor,
            disabledSuffixColor = disableColor
        )


    )
}