package com.fylora.materialcalculator.domain

sealed interface ExpressionPart {
    data class Number(val value: Double): ExpressionPart
    data class Operator(val value: Operation): ExpressionPart
    data class Parentheses(val type: ParenthesesType): ExpressionPart
}

sealed interface ParenthesesType {
    data object Opening: ParenthesesType
    data object Closing: ParenthesesType
}