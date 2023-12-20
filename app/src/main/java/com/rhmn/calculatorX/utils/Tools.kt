package com.rhmn.calculatorX.utils

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.rhmn.calculatorX.model.ButtonsData
import com.rhmn.calculatorX.model.Types

class Tools {

    companion object {
        fun getColorTextActionColor(isAction: Boolean): Color {
            if (isAction) {
                return Color.Black
            } else {
                return Color.White

            }
        }

        fun getButtonTextName(name: String): String {
            return when (name) {
                "=" -> "🟰"
                "+" -> "➕"
                "-" -> "➖"
                "x" -> "✖️"
                "÷" -> "➗"
                else -> name
            }

        }

        fun mathDo(mutableList: SnapshotStateList<ButtonsData>): SnapshotStateList<ButtonsData> {
            if (mutableList.size > 2) {

                do {
                    Log.e("KIAA", "forEach: " + mutableList[0].name)

                    val x = mutableList[0].name.toFloat()
                    val y = mutableList[2].name.toFloat()
                    var result = 0f
                    when (mutableList[1].name) {
                        "+" -> {
                            result = x + y
                        }

                        "-" -> {
                            result = x - y

                        }

                        "x" -> {
                            result = x * y
                        }

                        "÷" -> {
                            result = x / y

                        }

                        "%" -> {
                            result = x % y

                        }
                    }
                    mutableList.removeAt(0)
                    mutableList.removeAt(0)
                    mutableList.removeAt(0)
                    var resString = result.toString()
                    if (resString.endsWith(".0")) {
                        resString = resString.replace(".0", "")
                    }
                    mutableList.add(0, ButtonsData(0, Types.NUMBER, resString))

                } while (mutableList.size > 2)

            }

            return mutableList

        }
    }
}