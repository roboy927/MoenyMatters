package com.kanishthika.moneymatters.display.accounting.type.borrower.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.theme.MoneyMattersTheme

@Composable
fun BorrowerDetailBox(
    modifier: Modifier,
    borrowerName: String,
    borrowerAmount: String,
    borrowerContactNumber: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = borrowerName,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp
                )
                Text(
                    text = stringResource(id = R.string.rupee_symbol) +" "+ borrowerAmount ,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Text(
                text = borrowerContactNumber,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    MoneyMattersTheme {
        BorrowerDetailBox(
            modifier = Modifier,
            "Rahul Sp Visit caller",
            "2000",
            "9427071298"
        )
    }
}