package com.fylora.materialcalculator.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ExpressionEvaluatorTest {

    private lateinit var evaluator: ExpressionEvaluator

    @ParameterizedTest
    @MethodSource("expressionProvider")
    fun `Expression properly evaluated`(testData: Triple<String, List<ExpressionPart>, Double>) {
        // SETUP
        print(testData.first)
        evaluator = ExpressionEvaluator(testData.second)

        // ACTION
        val result = evaluator.evaluate()

        // ASSERTION
        assertThat(result).isEqualTo(testData.third)
    }

    companion object {
        @JvmStatic
        fun expressionProvider(): Stream<Triple<String, List<ExpressionPart>, Double>> {
            return listOf(
                Triple(
                    "Test long expression",
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
                    ),
                    4.0
                ),
                Triple(
                    "Test expression with 0",
                    listOf(
                        ExpressionPart.Number(5.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(0.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(0.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0)
                    ),
                    7.0
                ),
                Triple(
                    "Test expression with parentheses",
                    listOf(
                        ExpressionPart.Number(4.0),
                        ExpressionPart.Operator(Operation.SUBTRACT),
                        ExpressionPart.Parentheses(ParenthesesType.Opening),
                        ExpressionPart.Number(4.0),
                        ExpressionPart.Operator(Operation.MULTIPLY),
                        ExpressionPart.Number(5.0),
                        ExpressionPart.Parentheses(ParenthesesType.Closing),
                    ),
                    -16.0
                ),
                Triple(
                    "Test expression with a single number",
                    listOf(
                        ExpressionPart.Number(423.33)
                    ),
                    423.33
                ),
                Triple(
                    "Test expression of a decimal with whole number",
                    listOf(
                        ExpressionPart.Number(4513.55),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0)
                    ),
                    4515.55
                ),
                Triple(
                    "Test expression of whole numbers",
                    listOf(
                        ExpressionPart.Number(100.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0)
                    ),
                    104.0
                ),
                Triple(
                    "Test expression with percent",
                    listOf(
                        ExpressionPart.Number(50.0),
                        ExpressionPart.Operator(Operation.PERCENT),
                        ExpressionPart.Number(5.0),
                        ExpressionPart.Operator(Operation.ADD),
                        ExpressionPart.Number(2.0)
                    ),
                    4.5
                )
            ).stream()
        }
    }
}