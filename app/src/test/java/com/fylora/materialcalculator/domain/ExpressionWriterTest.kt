package com.fylora.materialcalculator.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ExpressionWriterTest {

    private lateinit var writer: ExpressionWriter

    @ParameterizedTest
    @MethodSource("parenthesesExpressionProvider")
    fun `Initial parentheses parsed`(testData: TestData) {
        print(testData.description)

        writer = ExpressionWriter()

        testData.actions.forEach { action ->
            writer.processAction(action)
        }

        assertThat(writer.expression).isEqualTo(testData.expression)
    }

    data class TestData(
        val description: String,
        val actions: List<CalculatorAction>,
        val expression: String
    )

    companion object {
        @JvmStatic
        fun parenthesesExpressionProvider(): Stream<TestData> {
            return listOf(
                TestData(
                    "Expression with normal parentheses",
                    listOf(
                        CalculatorAction.Parentheses,
                        CalculatorAction.Number(5),
                        CalculatorAction.Operator(Operation.ADD),
                        CalculatorAction.Number(4),
                        CalculatorAction.Parentheses,
                    ),
                    "(5+4)"
                ),
                TestData(
                    "Closing parentheses at the start not parsed",
                    listOf(
                        CalculatorAction.Parentheses,
                        CalculatorAction.Parentheses,
                    ),
                    "(("
                ),
                TestData(
                    "Closing parentheses at the start not parsed",
                    listOf(
                        CalculatorAction.Parentheses,
                        CalculatorAction.Parentheses,
                    ),
                    "(("
                ),
                TestData(
                    "Parentheses around a number parsed",
                    listOf(
                        CalculatorAction.Parentheses,
                        CalculatorAction.Number(9),
                        CalculatorAction.Parentheses,
                    ),
                    "(9)"
                ),
                TestData(
                    "Expression cleared",
                    listOf(
                        CalculatorAction.Parentheses,
                        CalculatorAction.Number(9),
                        CalculatorAction.Parentheses,
                        CalculatorAction.Operator(Operation.DIVIDE),
                        CalculatorAction.Number(5),
                        CalculatorAction.Operator(Operation.ADD),
                        CalculatorAction.Number(4),
                        CalculatorAction.Parentheses,
                        CalculatorAction.Clear
                    ),
                    ""
                ),
            ).stream()
        }
    }
}