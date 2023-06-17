package com.example.todaytask

import android.icu.text.MeasureFormat.FormatWidth
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todaytask.ui.theme.TodayTaskTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.FormatStyle
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodayTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@Composable
fun MainScreenContent(drawerState: DrawerState) {
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "TodayTaskApp")},
                navigationIcon = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.Default).launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                    ){
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Drawer Menu")
                    }
                }
            )
        },

        drawerBackgroundColor = Color.Gray,
        drawerGesturesEnabled = drawerState.isOpen,
        drawerContent = {
            //DRAWER HEADER
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .height(16.dp)
            ){
                Text(text = "Opções")
                Text(text = "--------")
            }
            Column() {
                Text(text = "Opção 1")
                Text(text = "Opção 2")
                Text(text = "Opção 3")
            }
        },
        content = {
            paddingValues -> Log.i("paddinValues", "$paddingValues")
            Column(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxSize()
            ) {
                MySerchField(modificador = Modifier.fillMaxWidth())

                val calendar = Calendar.getInstance()

                //(Fazendo)listOf<Tarefa>(Tarefa("A Fazer", ""))

                MyTaskWidgetList()
                MyTaskWidget(
                    modificador = Modifier.fillMaxWidth(),
                    taskName = "Ei eu sou uma notificação",
                    taskDetails = "É isso to fazeno pesquisa",
                    deadEndDate = Date()
                )
                MyTaskWidget(
                    modificador = Modifier.fillMaxWidth(),
                    taskName = "TCC",
                    taskDetails = "Fazer Wireframe Baixa/Alta fidelidade",
                    deadEndDate = Date())
            }
        },
        bottomBar = {
            BottomAppBar(
                content = { Text("...") }
            )
        }
    )//Scaffold(
}//fun MainScreenContent(){

@Composable
fun MyTaskWidgetList(listaDeTarefas: List<Tarefa>){

    //(AFAZER) listaDeTarefas.forEach(action = { Log.i("####%%%%####", "${it.nome}") })

}

@Composable
fun MySerchField(modificador: Modifier){
    TextField(
        value = "",
        onValueChange = {},
        modifier = modificador,
        placeholder = { Text(text = "Pesquisar tarefas")},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon")
        }
    )
}//fun MySearchField(modificador: Modifier){


@Composable
fun MyTaskWidget(
        modificador: Modifier,
        taskName: String,
        taskDetails: String,
        deadEndDate: Date
    ){
    val dataFormatter = SimpleDateFormat("EEE,MMM dd, yyyy", Locale.getDefault())
    Row(modifier = modificador) {
        Column(){
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Icons of a pendent task"
            )
            Text(
                text = dataFormatter.format(deadEndDate),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                fontSize = 12.sp
            )
        }//Column Icone e data

        // Abaixo o column do Task
        Column(
            modifier = modificador
                .border(width = 1.dp, color = Color.Black)
                .padding(3.dp)
        ) {
            Text(
                text = taskName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = taskDetails,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            )
        }//Column(
    }//Row(modifier = modificador) {
    Spacer(modifier = Modifier.height(16.dp))
}//fun MyTaskWidget(modificador: Modifier){



@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    TodayTaskTheme {
        MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))

    }
}