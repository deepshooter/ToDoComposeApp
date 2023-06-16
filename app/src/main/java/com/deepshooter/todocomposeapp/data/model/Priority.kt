package com.deepshooter.todocomposeapp.data.model

import androidx.compose.ui.graphics.Color
import com.deepshooter.todocomposeapp.ui.theme.HighPriorityColor
import com.deepshooter.todocomposeapp.ui.theme.LowPriorityColor
import com.deepshooter.todocomposeapp.ui.theme.MediumPriorityColor
import com.deepshooter.todocomposeapp.ui.theme.NonePriorityColor

enum class Priority(color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}