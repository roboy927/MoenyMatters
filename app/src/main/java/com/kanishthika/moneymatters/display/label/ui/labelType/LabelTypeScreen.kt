package com.kanishthika.moneymatters.display.label.ui.labelType

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelTypeScreen(
    modifier: Modifier = Modifier,
    navigateToAdd: () -> Unit,
    labelTypeScreenModel: LabelTypeScreenModel = hiltViewModel()
) {
    val labelTypeList by labelTypeScreenModel.labelTypeList.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
      //  delay(600)
        labelTypeScreenModel.loadData()
    }



    Scaffold(
        topBar = { MMTopAppBar(titleText = "Label Types") },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.height(IntrinsicSize.Min),
                onClick = { navigateToAdd() },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.7f),
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f)
            ) {
                Row(
                    modifier = modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                    Text(
                        text = "LabelType",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        FlowingLabels(
            modifier = Modifier.padding(paddingValues).padding(12.dp),
            horizontalSpacing = 8.dp,
            verticalSpacing = 8.dp
        ) {
            labelTypeList.forEach { label ->
                LabelTypeCard(
                    modifier = modifier,
                    labelType = label.labelType,
                    onDelete = {labelTypeScreenModel.deleteLabelType(label)}
                )
            }
        }
       /* LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(dimensionResource(id = R.dimen.uni_screen_padding))
        ) {

            items(labelTypeList) { labelType ->
                LabelTypeCard(modifier = modifier, labelType = labelType.labelType)
            }
        }*/
    }
}

@Composable
fun LabelTypeCard(
    modifier: Modifier,
    labelType: String,
    onDelete:()-> Unit
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20))
            .padding(8.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = labelType, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = modifier.width(2.dp))
            Icon(
                imageVector = Icons.Sharp.Delete,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                modifier = modifier.size(20.dp).clickable {
                    onDelete()
                }
            )
        }

    }
}

@Composable
fun FlowingLabels(
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 0.dp,
    verticalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val horizontalSpacingPx = horizontalSpacing.roundToPx()
        val verticalSpacingPx = verticalSpacing.roundToPx()

        var currentRowWidth = 0
        var currentRowHeight = 0
        var totalHeight = 0

        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentRowWidth + placeable.width > constraints.maxWidth) {
                totalHeight += currentRowHeight + verticalSpacingPx
                currentRowWidth = 0
                currentRowHeight = 0
            }

            currentRowWidth += placeable.width + horizontalSpacingPx
            currentRowHeight = maxOf(currentRowHeight, placeable.height)

            placeable
        }

        totalHeight += currentRowHeight

        layout(width = constraints.maxWidth, height = totalHeight) {
            var xPosition = 0
            var yPosition = 0

            placeables.forEach { placeable ->
                if (xPosition + placeable.width > constraints.maxWidth) {
                    xPosition = 0
                    yPosition += currentRowHeight + verticalSpacingPx
                    currentRowHeight = placeable.height
                }

                placeable.placeRelative(x = xPosition, y = yPosition)

                xPosition += placeable.width + horizontalSpacingPx
            }
        }
    }
}