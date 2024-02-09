package com.fylora.materialcalculator.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ExpressionParserTest {

    private lateinit var parser: ExpressionParser

    @ParameterizedTest
    @MethodSource("expressionProvider")
    fun `Expression is properly parsed`(testData: Pair<String, List<ExpressionPart>>) {
        // SETUP
        parser = ExpressionParser(testData.first)

        // ACTION
        val actual = parser.parse()

        // ASSERT
        assertThat(actual).isEqualTo(testData.second)
    }

    companion object {
        @JvmStatic
        fun expressionProvider(): Stream<Pair<String, List<ExpressionPart>>> {
            return listOf(
                Pair(
                    "3+5-3x4÷3",
                    listOf(
                        ExpressionPart.Number(3.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(5.0),
                        ExpressionPart.Operator(Operation.SUBTRACT),
                        ExpressionPart.Number(3.0),
                        ExpressionPart.Operator(Operation.MULTIPLY),
                        ExpressionPart.Number(4.0),
                        ExpressionPart.Operator(Operation.DIVIDE),
                        ExpressionPart.Number(3.0),
                    )
                ),
                Pair(
                    "5+0+0+2",
                    listOf(
                        ExpressionPart.Number(5.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(0.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(0.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0)
                    )
                ),
                Pair(
                    "4-(4x5)",
                    listOf(
                        ExpressionPart.Number(4.0),
                        ExpressionPart.Operator(Operation.SUBTRACT),
                        ExpressionPart.Parentheses(ParenthesesType.Opening),
                        ExpressionPart.Number(4.0),
                        ExpressionPart.Operator(Operation.MULTIPLY),
                        ExpressionPart.Number(5.0),
                        ExpressionPart.Parentheses(ParenthesesType.Closing),
                    )
                ),
                Pair(
                    "423.33",
                    listOf(
                        ExpressionPart.Number(423.33)
                    )
                ),
                Pair(
                    "4513.55+2.0",
                    listOf(
                        ExpressionPart.Number(4513.55),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0)
                    )
                ),
                Pair(
                    "100+2+2",
                    listOf(
                        ExpressionPart.Number(100.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0)
                    )
                ),
                Pair(
                    "100%5+2",
                    listOf(
                        ExpressionPart.Number(100.0),
                        ExpressionPart.Operator(Operation.PERCENT),
                        ExpressionPart.Number(5.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0)
                    )
                )
            ).stream()
        }
    }
}