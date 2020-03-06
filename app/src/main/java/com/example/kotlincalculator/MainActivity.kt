package com.example.kotlincalculator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import net.objecthunter.exp4j.operator.Operator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Numbers
        tvOne.setOnClickListener { appendOnExpression("1", true) }
        tvTwo.setOnClickListener { appendOnExpression("2", true) }
        tvThree.setOnClickListener { appendOnExpression("3", true) }
        tvFour.setOnClickListener { appendOnExpression("4", true) }
        tvFive.setOnClickListener { appendOnExpression("5", true) }
        tvSix.setOnClickListener { appendOnExpression("6", true) }
        tvSeven.setOnClickListener { appendOnExpression("7", true) }
        tvEight.setOnClickListener { appendOnExpression("8", true) }
        tvNine.setOnClickListener { appendOnExpression("9", true) }
        tvZero.setOnClickListener { appendOnExpression("0", true) }
        tvDot.setOnClickListener { appendOnExpression(".", true) }
        tvE.setOnClickListener { appendOnExpression( "e", true) }
        tvPi.setOnClickListener { appendOnExpression( "pi", true) }

        //Operators
        tvPlus.setOnClickListener { appendOnExpression("+", false) }
        tvMinus.setOnClickListener { appendOnExpression("-", false) }
        tvMul.setOnClickListener { appendOnExpression("*", false) }
        tvDivide.setOnClickListener { appendOnExpression("/", false) }
        tvOpen.setOnClickListener { appendOnExpression("(", false) }
        tvClose.setOnClickListener { appendOnExpression(")", false) }
        tvInverse.setOnClickListener { appendOnExpression("^(-1)", false) }
        tvSqRoot.setOnClickListener { appendOnExpression ("sqrt", false) }
        tvYRoot.setOnClickListener { appendOnExpression("^(1/", false) }
        tvXSquared.setOnClickListener { appendOnExpression("^2", false) }
        tvXToTheY.setOnClickListener { appendOnExpression("^", false) }
        tvLog.setOnClickListener { appendOnExpression("log10(", false) }
        tvLn.setOnClickListener { appendOnExpression( "log(", false) }
        tvSine.setOnClickListener { if (tvRad.text == "DEG") {
            appendOnExpression("sin(toRadians(", false) }
            else appendOnExpression("sin(", false) }
        tvCosine.setOnClickListener { if (tvRad.text == "DEG") {
            appendOnExpression("cos(|", false) }
        else appendOnExpression("cos(", false) }
        tvTangent.setOnClickListener { if (tvRad.text == "DEG") {
            appendOnExpression("tan(|", false) }
        else appendOnExpression("tan(", false) }
        tvExp.setOnClickListener { appendOnExpression( "exp(", false) }
        tvFac.setOnClickListener { appendOnExpression("!", false) }
        tvDeg.setOnClickListener {
            if (tvRad.text == "DEG") {
                tvRad.text = "RAD"
        } else tvRad.text = "DEG" }
        tvClear.setOnClickListener {
            tvExpression.text = ""
            tvResult.text = ""
        }

        tvBack.setOnClickListener {
            val string = tvExpression.text.toString()
            if (string.isNotEmpty()) {
                tvExpression.text = string.substring(0, string.length - 1)
            }
            tvResult.text = ""
        }

        val factorial: Operator = object :
            Operator(
                "!",
                1,
                true,
                PRECEDENCE_POWER + 1
            ) {
            override fun apply(vararg args: Double): Double {
                val arg = args[0].toInt()
                require(arg.toDouble() == args[0]) //{ "Operand for factorial has to be an integer" }
                require(arg >= 0) //{ "The operand of the factorial can not be less than zero" }
                var result = 1.0
                for (i in 1..arg) {
                    result *= i.toDouble()
                }
                return result
            }
        }

        val toRadians: Function = object :
            Function("toRadians", 1) {
                override fun apply(vararg args: Double): Double {
                    val arg = args[0]
                    return Math.toRadians(arg)
                }
            }

        tvEquals.setOnClickListener {
            try {

                val expression = ExpressionBuilder(tvExpression.text.toString())
                    .operator(factorial)
                    .functions(toRadians)
                    .build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble())
                    tvResult.text = longResult.toString()
                else
                    tvResult.text = result.toString()

            } catch (e: Exception) {
                Log.d("Exception", " message : " + e.message)
            }
        }

    }

    fun appendOnExpression(string: String, canClear: Boolean) {

        if(tvResult.text.isNotEmpty()){
            tvExpression.text = ""
        }

        if (canClear) {
            tvResult.text = ""
            tvExpression.append(string)
        } else {
            tvExpression.append(tvResult.text)
            tvExpression.append(string)
            tvResult.text = ""
        }
    }
}

