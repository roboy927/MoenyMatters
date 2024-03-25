package com.kanishthika.moneymatters.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kanishthika.moneymatters.R

@Composable
fun MMBottomAppBar(
    content: @Composable() (RowScope.() -> Unit)
){
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.uni_screen_padding))
    ) {
        content()
        }
}