package com.example.tasktodayapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasktodayapp.model.Tarefa.Tarefa
import com.example.tasktodayapp.ui.theme.Blue
import com.example.tasktodayapp.ui.theme.Purple200
import com.example.tasktodayapp.ui.theme.TaskTodayAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
        }
    }
}

@Composable
fun MainScreenContent(drawerState: DrawerState){
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    val scope = rememberCoroutineScope()
    //val tabIndex = by remember {  mutableStateOf(0)  }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White,
                title = { Text(text = "Today Task")},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Drawer Menu"
                        )
                    }
                }
            )
        },
        drawerBackgroundColor = Color.DarkGray,
        drawerGesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Box(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .height(16.dp)
            ){
                Text(text = "Opções", color = Color.White)
            }
            Column(){
                Text(text = "Opção 1", color = Color.White)
                Text(text = "Opção 2", color = Color.White)
                Text(text = "Opção 3", color = Color.White)
            }
        },
        content = {
                paddingValues -> Log.i("paddingValues", "$paddingValues")
            Column(
                modifier = Modifier
                    .background(color = Color(0x59C6CACA))
                    .fillMaxSize()
            ) {
                MySearchField(modificador = Modifier.fillMaxWidth())

                val tProvaDeCalculo = Tarefa(
                    "Estudar Prova de Português",
                    "Materia passada em sala",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tProvaDeKotlin = Tarefa(
                    "Finalizar App de PAM",
                    "Começãmos em sala",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tAdicionarRm = Tarefa(
                    "Adicionar RM",
                    "22319",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tAdicionarNome = Tarefa(
                    "Adicionar nome",
                    "Victor Roma",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tMudarBackground = Tarefa(
                    "Mudar background",
                    "Feito",
                    Date(),
                    Date(),
                    status = 0.0
                )

                var minhaListaDeTarefas = listOf<Tarefa>(tProvaDeCalculo, tProvaDeKotlin, tAdicionarRm, tAdicionarNome, tMudarBackground)

                MyTaskWidgetList(minhaListaDeTarefas)
            }//Column
        },//content
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White,
                content = { Text(" Victor R. F de Roma                                                             RM: 22319")
                }
            )
        },

        isFloatingActionButtonDocked = false,
        floatingActionButton = { ExtendedFloatingActionButton(
            backgroundColor = Color.Black,
            contentColor = Color.White,
            icon = {
                   Icon(
                       imageVector = Icons.Default.AddCircle,
                       contentDescription = "Add Task"
                   )
            },
            text = { Text("ADD")  },
            onClick = { /*TODO*/ })

        }
    ) //Scaffold
} //MainScreenContent

@Composable
fun MyTaskWidgetList(listaDeTarefas: List<Tarefa>){
    listaDeTarefas.forEach(
        action = { MyTaskWidget(modificador = Modifier.fillMaxWidth(), taredasASerMostrada = it) }
    )
} //MyTaskWidgetList

@Composable
fun MySearchField(modificador: Modifier){
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
} //MySearchField

@Composable
fun MyTaskWidget(
    modificador: Modifier,
    taredasASerMostrada: Tarefa
){
    val dateFormatter = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())

        Column(modifier = modificador

            .padding(3.dp)
        ){
            Row(modifier = modificador) {
                Column(modifier = Modifier.width(150.dp).padding(10.dp)){
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Icons of a pendent task")
                    Text(
                        text = dateFormatter.format(taredasASerMostrada.pzoFinal),
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 12.sp
                    )
                }
                Column(){
                    Text(
                        text = taredasASerMostrada.nome,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )

                    Text(
                        text = taredasASerMostrada.detalhes,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    )
                }

            Spacer(modifier = Modifier.height(16.dp))
            }

    }
    Spacer(modifier = Modifier.height(16.dp))
} //MyTaskWidget


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
}