package com.rhmn.calculatorX

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rhmn.calculatorX.model.ButtonsData
import com.rhmn.calculatorX.model.Types
import com.rhmn.calculatorX.ui.theme.KotlinTutorialTheme
import com.rhmn.calculatorX.ui.theme.background
import com.rhmn.calculatorX.ui.theme.mathButtons
import com.rhmn.calculatorX.ui.theme.numberButtons
import com.rhmn.calculatorX.utils.Prefs
import com.rhmn.calculatorX.utils.Tools.Companion.getColorTextActionColor
import com.rhmn.calculatorX.utils.Tools.Companion.mathDo
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinTutorialTheme {
                // A surface container using the 'background' color from the theme
                Greeting()

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@Composable
fun Greeting() {
    val context = LocalContext.current
    var resultTv by remember { mutableStateOf("0") }
    var lastNumber by remember { mutableStateOf("") }

    var buttonsList = arrayOf(
        ButtonsData(1, Types.ACTION, "AC"),
        ButtonsData(2, Types.ACTION, "⬅️"),
        ButtonsData(3, Types.MATH, "%"),
        ButtonsData(4, Types.MATH, "÷"),
        ButtonsData(5, Types.NUMBER, "7"),
        ButtonsData(6, Types.NUMBER, "8"),
        ButtonsData(7, Types.NUMBER, "9"),
        ButtonsData(8, Types.MATH, "x"),
        ButtonsData(9, Types.NUMBER, "4"),
        ButtonsData(10, Types.NUMBER, "5"),
        ButtonsData(11, Types.NUMBER, "6"),
        ButtonsData(12, Types.MATH, "-"),
        ButtonsData(13, Types.NUMBER, "1"),
        ButtonsData(14, Types.NUMBER, "2"),
        ButtonsData(15, Types.NUMBER, "3"),
        ButtonsData(16, Types.MATH, "+"),
        ButtonsData(17, Types.ACTION, ""),
        ButtonsData(18, Types.NUMBER, "0"),
        ButtonsData(19, Types.NUMBER, "."),
        ButtonsData(20, Types.MATH, "="),
    )

        val historyList = mutableStateListOf<String>()
    var resultTvList = mutableStateListOf<ButtonsData>()

    val lazyColumnListState = rememberLazyListState()
    val lazyRowListState = rememberLazyListState()
    val corroutineScope = rememberCoroutineScope()

    suspend fun innerFunc() {
        // your code
        val gson = Gson()
        val itemType = object : TypeToken<List<String>>() {}.type
        Log.d("KIAA", "innerFunc: " + Gson().toJson(Prefs(context).mathsData))
        val itemList = gson.fromJson<List<String>>(Prefs(context).mathsData, itemType)
        itemList?.forEach {
            historyList += it
        }

        if (!historyList.isEmpty()) {
            lazyColumnListState.scrollToItem(historyList.size - 1)

        }
        resultTvList.add(ButtonsData(0, Types.NUMBER, "0"))

    }

    LaunchedEffect(Unit) {
        innerFunc()

    }




    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(background)
        ) {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp,0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = context.resources.getString(R.string.app_name), color = Color.White, fontSize = 18.sp
                        )
                        IconButton(onClick = {
                            historyList.clear()
                            Prefs(context).mathsData = Gson().toJson(historyList)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "clear History Btn",
                                tint = Color.White,

                                )
                        }
                    }

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = numberButtons),
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color.DarkGray,
                        shape = RoundedCornerShape(0.dp)
                    )
            )
            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    state = lazyColumnListState,
                ) {


                    items(historyList.size,
                        key = { listItem ->
                            listItem // or any other unique
                        })
                    { index ->
                        Text(
                            text = historyList[index],
                            modifier = Modifier
                                .padding(12.dp, 24.dp)
                                .fillMaxWidth()
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = {
//                                        Toast
//                                            .makeText(context, "hi", Toast.LENGTH_SHORT)
//                                            .show()
                                    },
                                    enabled = true,
                                    role = androidx.compose.ui.semantics.Role.Button,

                                    ),
                            textAlign = TextAlign.Right,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Divider()
                    }
                }
            }
            Column {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = numberButtons),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(12.dp, 0.dp)
                ) {
                    LazyRow(state = lazyRowListState, modifier = Modifier.padding(12.dp, 0.dp)) {
                        corroutineScope.launch {
                            if (resultTvList.size > 0) {
                                lazyRowListState.scrollToItem(resultTvList.size - 1)
                            }
                        }
                        items(resultTvList.size) { item ->
                            Text(
                                text = resultTvList[item].name,
                                modifier = Modifier
                                    .padding(2.dp, 24.dp)
                                    .combinedClickable(
                                        onClick = { },
                                        onLongClick = {

                                        },
                                        enabled = true,
                                        role = androidx.compose.ui.semantics.Role.Button,

                                        ),
                                textAlign = TextAlign.Right,
                                fontSize = 28.sp,
                                color = Color.White
                            )
                        }
                    }
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 24.dp, 0.dp, 0.dp),
                    columns = GridCells.Fixed(count = 4),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp), // top and bottom margin between each item
                    horizontalArrangement = Arrangement.spacedBy(space = 16.dp), // left and right margin between each item
                    contentPadding = PaddingValues(all = 10.dp), // margin for the whole layout
                    userScrollEnabled = false,
                ) {
                    items(count = buttonsList.size) { index ->
                        Button(
                            onClick = {
                                when (buttonsList[index].type) {
                                    Types.ACTION -> {
                                        when (buttonsList[index].name) {
                                            "AC" -> {
                                                lastNumber = "0"
                                                resultTvList.clear()
                                                resultTvList.add(ButtonsData(0, Types.NUMBER, "0"))
                                                resultTv = "0"
                                            }

                                            "⬅️" -> {
                                                if (resultTvList[resultTvList.size - 1].type == Types.MATH) {
                                                    resultTvList.removeAt(resultTvList.size - 1)

                                                } else {

                                                    var edite =
                                                        resultTvList[resultTvList.size - 1].name
                                                    if (edite.length > 1) {
                                                        resultTvList[resultTvList.size - 1] =
                                                            resultTvList[resultTvList.size - 1].copy(
                                                                name = edite.dropLast(1)
                                                            )
                                                        lastNumber = edite.dropLast(1)


                                                    } else {
                                                        if (resultTvList.size > 1) {
                                                            resultTvList.removeAt(resultTvList.size - 1)
                                                            lastNumber = "0"
                                                        } else {
                                                            resultTvList[resultTvList.size - 1] =
                                                                resultTvList[resultTvList.size - 1].copy(
                                                                    name = "0"
                                                                )
                                                            lastNumber = "0"

                                                        }

                                                    }


                                                }
                                            }
                                        }
                                    }

                                    Types.MATH -> {
                                        when (buttonsList[index].name) {
                                            "=" -> {
                                                if (resultTvList.size > 2) {
                                                    lastNumber = ""
                                                    historyList.add("")
                                                    resultTvList.forEach {
                                                        historyList[historyList.size - 1] += it.name
                                                    }
                                                    resultTvList = mathDo(resultTvList)
                                                    historyList[historyList.size - 1] += " = ${resultTvList[0].name}"
                                                    lastNumber = resultTvList.first().name
                                                    Prefs(context).mathsData =
                                                        Gson().toJson(historyList)
                                                }
                                            }

                                            else -> {
                                                if (!resultTvList.isEmpty()) {
                                                    if (resultTvList.last().type == Types.NUMBER) {
                                                        lastNumber = ""
                                                        resultTvList.add(
                                                            ButtonsData(
                                                                resultTvList.last().id + 1,
                                                                Types.MATH,
                                                                buttonsList[index].name
                                                            )
                                                        )

                                                    }

                                                }
                                            }
                                        }


                                    }

                                    Types.NUMBER -> {
                                        if(lastNumber.contains(".")){
                                            if(buttonsList[index].name != "."){
                                                lastNumber += buttonsList[index].name
                                                try {

                                                    lastNumber = if(lastNumber.contains(".")){
//                                                        var i = lastNumber.toFloat()
//                                                        i.toString()
                                                lastNumber
                                                    }else{
                                                        var i = lastNumber.toInt()
                                                        i.toString()

                                                    }


                                                    if (resultTvList.isEmpty()) {
                                                        resultTvList.add(
                                                            ButtonsData(
                                                                0,
                                                                Types.NUMBER,
                                                                lastNumber
                                                            )
                                                        )

                                                    } else
                                                        if (resultTvList.last().type == Types.NUMBER) {
                                                            resultTvList[resultTvList.size - 1] =
                                                                resultTvList.last().copy(name = lastNumber)

                                                        } else {
                                                            resultTvList.add(
                                                                ButtonsData(
                                                                    resultTvList.last().id + 1,
                                                                    Types.NUMBER,
                                                                    lastNumber
                                                                )
                                                            )

                                                        }
                                                } catch (e: Exception) {
                                                    Toast.makeText(
                                                        context,
                                                        "number too big!",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }else{
                                            lastNumber += buttonsList[index].name
                                            try {

                                                lastNumber = if(lastNumber.contains(".")){
//                                                    var i = lastNumber.toFloat()
//                                                    i.toString()
                                                lastNumber
                                                }else{
                                                    var i = lastNumber.toInt()
                                                    i.toString()

                                                }


                                                if (resultTvList.isEmpty()) {
                                                    resultTvList.add(
                                                        ButtonsData(
                                                            0,
                                                            Types.NUMBER,
                                                            lastNumber
                                                        )
                                                    )

                                                } else
                                                    if (resultTvList.last().type == Types.NUMBER) {
                                                        resultTvList[resultTvList.size - 1] =
                                                            resultTvList.last().copy(name = lastNumber)

                                                    } else {
                                                        resultTvList.add(
                                                            ButtonsData(
                                                                resultTvList.last().id + 1,
                                                                Types.NUMBER,
                                                                lastNumber
                                                            )
                                                        )

                                                    }
                                            } catch (e: Exception) {
                                                Toast.makeText(
                                                    context,
                                                    "number too big!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }



                                    }

                                    else -> {}
                                }
                                resultTv = ""
                                resultTvList?.forEach {
                                    if (it.type == Types.NUMBER) {
                                        val a = it.name.toFloat()
                                        var b = a.toString()
                                        if (b.endsWith(".0")) {
                                            b = b.replace(".0", "")
                                        }
                                        resultTv += b
                                    } else {
                                        resultTv += it.name
                                    }


                                }
                            },
                            modifier = Modifier
                                .size(52.dp)
                                .run {
                                if(buttonsList[index].id == 17 || buttonsList[index].id == 18){
                                    alpha(0f)
                                }else{
                                    alpha(1f)

                                }
                            },
                            colors = ButtonDefaults
                                .run {
                                    if (buttonsList[index].type == Types.MATH) {
                                        buttonColors(
                                            containerColor = mathButtons,
                                            disabledContainerColor = Color.Gray,
                                        )
                                    } else if (buttonsList[index].type == Types.NUMBER) {
                                        buttonColors(
                                            containerColor = numberButtons,
                                            disabledContainerColor = Color.Gray,
                                        )
                                    } else {
                                        buttonColors(
                                            containerColor = Color.White,
                                            disabledContainerColor = Color.Gray,
                                        )
                                    }
                                },
                            shape = RoundedCornerShape(100)

                        ) {
                            Text(
                                text = buttonsList[index].name,
                                color = getColorTextActionColor(isAction = buttonsList[index].type == Types.ACTION),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp

                            )

                        }
                    }
                }
            }

        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,


            ) {

            Button(
                onClick = {

                    lastNumber += "0"
                    try {

                        lastNumber = if(lastNumber.contains(".")){
//                            var i = lastNumber.toFloat()
                            lastNumber

                        }else{
                            var i = lastNumber.toInt()
                            i.toString()

                        }


                        if (resultTvList.isEmpty()) {
                            resultTvList.add(
                                ButtonsData(
                                    0,
                                    Types.NUMBER,
                                    lastNumber
                                )
                            )

                        } else
                            if (resultTvList.last().type == Types.NUMBER) {
                                resultTvList[resultTvList.size - 1] =
                                    resultTvList.last().copy(name = lastNumber)

                            } else {
                                resultTvList.add(
                                    ButtonsData(
                                        resultTvList.last().id + 1,
                                        Types.NUMBER,
                                        lastNumber
                                    )
                                )

                            }
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "number too big!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    resultTv = ""
                    resultTvList?.forEach {
                        if (it.type == Types.NUMBER) {
                            val a = it.name.toFloat()
                            var b = a.toString()
                            if (b.endsWith(".0")) {
                                b = b.replace(".0", "")
                            }
                            resultTv += b
                        } else {
                            resultTv += it.name
                        }


                    }
                },
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 10.dp , end = 10.dp)
                    .fillMaxWidth(0.49f)
                        .size(52.dp),
                shape = RoundedCornerShape(100)
            ) {
                Text(
                    text = "0",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp

                )
            }


        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinTutorialTheme {
        Greeting()
    }
}