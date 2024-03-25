package com.kanishthika.moneymatters.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.ui.theme.MoneyMattersTheme

@Composable
fun MMOutlinedButton(
    enabled: Boolean = true,
    modifier: Modifier,
    text: String,
    onclick: () -> Unit
){
    OutlinedButton(
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        onClick = { onclick() },
        shape = RoundedCornerShape(50),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                .copy(alpha = 0.5F)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
    ) {
       Text(
           text = text,
           fontSize = 18.sp

       )
    }
}

@Preview
@Composable
fun PreviewMMOutlinedButton(){
    MoneyMattersTheme {
        MMOutlinedButton(
            modifier = Modifier ,
            text = "ADD"
        ) { }
    }
}