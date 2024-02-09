package com.fylora.materialcalculator.domain

class ExpressionParser(
    private val calculation: String
) {

    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()

        var i = 0
        while (i < calculation.length) {
            val currentChar = calculation[i]

            when {
                currentChar in operationSymbols ->
                    result.add(ExpressionPart.Operator(operationFromSymbol(currentChar)))
                currentChar.isDigit() -> {
                    i = parseNumber(i, result)
                    continue
                }
                currentChar in "()" -> {
                    parseParentheses(currentChar, result)
                }
            }

            i++
        }

        return result
    }

    private fun parseNumber(startingIndex: Int, result: MutableList<ExpressionPart>): Int {
        var i = startingIndex
        val numberAsString = buildString {
            while (i < calculation.length && (calculation[i].isDigit() || calculation[i] == '.')) {
                append(calculation[i])
                i++
            }
        }
        result.add(ExpressionPart.Number(numberAsString.toDouble()))
        return i
    }

    private fun parseParentheses(currentChar: Char, result: MutableList<ExpressionPart>) {
        result.add(
            ExpressionPart.Parentheses(
                type = when (currentChar) {
                    '(' -> ParenthesesType.Opening
                    ')' -> ParenthesesType.Closing
                    else -> throw IllegalArgumentException("Invalid parentheses type: $currentChar")
                }
            )
        )
    }
}