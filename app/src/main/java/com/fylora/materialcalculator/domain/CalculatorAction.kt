package com.fylora.materialcalculator.domain

sealed interface CalculatorAction {
    data class Number(val value: Int): CalculatorAction
    data class Operator(val value: Operation): CalculatorAction
    data object Clear: CalculatorAction
    data object Delete: CalculatorAction
    data object Parentheses: CalculatorAction
    data object Calculate: CalculatorAction
    data object Decimal: CalculatorAction
}