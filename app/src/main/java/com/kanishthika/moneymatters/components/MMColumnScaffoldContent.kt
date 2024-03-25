package com.kanishthika.moneymatters.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kanishthika.moneymatters.R

@Composable
fun MMColumnScaffoldContentColumn(
    modifier: Modifier,
    scaffoldPaddingValues: PaddingValues,
    content: @Composable (() -> Unit)
){
    Column(
        modifier
            .padding(scaffoldPaddingValues)
            .background(MaterialTheme.colorScheme.background)
            .padding(dimensionResource(id = R.dimen.uni_screen_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing)),
        horizontalAlignment = Alignment.CenterHorizontally){
        content()
    }
}