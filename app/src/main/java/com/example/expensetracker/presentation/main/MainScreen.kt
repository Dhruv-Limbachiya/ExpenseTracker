package com.example.expensetracker.presentation.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.presentation.add_update_expense.AddUpdateExpenseScreen
import com.example.expensetracker.presentation.dashboard.DashboardScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        DashboardScreen(modifier = modifier)
        FabAnimatedContainer(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun FabAnimatedContainer(modifier: Modifier = Modifier) {
    var containerTargetState by remember { mutableStateOf(ContainerState.FAB) }

    val transition = updateTransition(
        targetState = containerTargetState,
        label = "fab_container_transition"
    ) // start & synchronize animation of all the child animations

    // animate color
    val animatedColor by transition.animateColor(label = "animate_container_color") { state ->
        when (state) {
            ContainerState.FAB -> MaterialTheme.colorScheme.primaryContainer
            ContainerState.AddExpenseScreen -> MaterialTheme.colorScheme.surface
        }
    }

    // animate corner radius
    val animatedCornerRadius by transition.animateDp(
        label = "animate_container_corner_radius", transitionSpec = {
            tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        }) { state ->

        when (state) {
            ContainerState.FAB -> 22.dp
            ContainerState.AddExpenseScreen -> 0.dp
        }
    }

    // animate elevation
    val animatedElevation by transition.animateDp(label = "animate_container_elevation") { state ->
        when (state) {
            ContainerState.FAB -> 8.dp
            ContainerState.AddExpenseScreen -> 0.dp
        }
    }

    // animate padding
    val animatedPadding by transition.animateDp(label = "animate_container_padding") { state ->
        when (state) {
            ContainerState.FAB -> 16.dp
            ContainerState.AddExpenseScreen -> 0.dp
        }
    }

    transition.AnimatedContent(
        modifier = modifier
            .padding(
                end = animatedPadding, bottom = animatedPadding
            )
            .shadow(
                elevation = animatedElevation,
                shape = RoundedCornerShape(animatedCornerRadius)
            )
            .drawBehind {
                drawRect(animatedColor)
            },
    ) { state ->
        when (state) {
            ContainerState.FAB -> {
                AddFAB(modifier = Modifier) {
                    containerTargetState = ContainerState.AddExpenseScreen
                }
            }

            ContainerState.AddExpenseScreen -> {
                AddUpdateExpenseScreen(modifier = Modifier) {
                    containerTargetState = ContainerState.FAB
                }
            }
        }
    }
}

@Composable
fun AddFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier
            .defaultMinSize(
                minWidth = 76.dp,
                minHeight = 76.dp,
            ),
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Expense")
    }
}

enum class ContainerState {
    FAB,
    AddExpenseScreen,
}