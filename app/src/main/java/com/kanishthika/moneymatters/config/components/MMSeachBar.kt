package com.kanishthika.moneymatters.config.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.config.theme.MoneyMattersTheme


@SuppressLint("SuspiciousIndentation")
@Composable
fun MMSearchBar(
    query: String,
    modifier: Modifier,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit
) {
   val interactionSource = remember {
       MutableInteractionSource()
   }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val unfocusedColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    val focusedColor: Color = MaterialTheme.colorScheme.primary


        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = LocalTextStyle.current.copy(
                color = focusedColor,
                fontSize = 15.sp,
            ),
            modifier = modifier
                .focusRequester(focusRequester)
                .onKeyEvent { event ->
                    if (event.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_BACK) {
                        onClearClick()
                        focusManager.clearFocus()
                        true
                    } else {
                        false
                    }
                }
                .padding(10.dp)
                .border(
                    1.dp,
                    if (isFocused) focusedColor else unfocusedColor,
                    RoundedCornerShape(50)
                )
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(50)
                )
                .fillMaxWidth(),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Characters
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(10.dp, 6.dp)
                        .height(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = modifier.padding(end = 5.dp),
                        tint =  if (isFocused) focusedColor else unfocusedColor,
                    )
                    Box(Modifier.weight(1f)) {
                        if (query.isEmpty()) Text(
                            "Search",
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                                fontSize = 15.sp
                            )
                        )
                        innerTextField()
                    }
                    if (isFocused){
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint =  focusedColor,
                            modifier = modifier.clickable {
                                if (query.isEmpty())
                                focusManager.clearFocus()
                                else
                                onClearClick()
                            }
                        )
                    }

                }
            }
        )


    }


@Preview(showSystemUi = true)
@Composable
fun MMSearchBarPreview() {
    var searchQuery by remember { mutableStateOf("") }
    var active by remember {
        mutableStateOf(false)
    }
    MoneyMattersTheme {
        MMSearchBar(
            query = searchQuery,
            modifier = Modifier,
            onQueryChange = { searchQuery = it },
            onClearClick = {
                if (searchQuery.isNotEmpty()) {
                    searchQuery = ""
                } else {
                    active = false
                }
            }
        )
    }

}