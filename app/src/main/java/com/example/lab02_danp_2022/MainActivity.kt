package com.example.lab02_danp_2022

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main"){
                composable("main"){
                    MainScreen(navController = navController)
                }
                composable("second/{city}"){
                    backStackEntry ->
                    val city = backStackEntry.arguments?.getString("city")
                    requireNotNull(city)
                    SecondScreen(city)
                }
            }

        }
    }
}

@Composable
fun MainScreen(navController: NavController){

    var expanded by remember { mutableStateOf(false)}
    val list = listOf("Amazonas","Áncash","Apurímac", "Arequipa", "Ayacucho",
        "Cajamarca", "Callao", "Cusco" , "Huancavelica" , "Huánuco" , "Ica" , "Junín" ,
        "La Libertad", "Lambayeque", "Lima Metropolitana", "Loreto" , "Madre de Dios" ,
        "Moquegua" , "Pasco" , "Piura" , "Puno" , "San Martín" , "Tacna" , "Tumbes" , "Ucayali")
    var selectedItem by remember { mutableStateOf("")}

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val context = LocalContext.current
    
    val icon = if (expanded){
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {selectedItem = it },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.toSize()
                },
            label = {Text(text ="Elegir cuidad")},
            readOnly = true,
            enabled = false,
            trailingIcon = {
             Icon(icon,"", Modifier.clickable{ expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textFiledSize.width.toDp()})
        ) {
            list.forEach{ label ->
                DropdownMenuItem(onClick = {
                    selectedItem = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                if(selectedItem == "")
                    Toast.makeText(
                    context,
                    "Seleccione ciudad",
                    Toast.LENGTH_SHORT
                ).show()
                else
                    navController.navigate("second/$selectedItem")
        }, colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Red
        )){
            Text("Buscar platos")
        }

    }
}


@Composable
fun SecondScreen(city: String) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(city)
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = getDishesOfRegion(city)) { name ->
                ItemDish(name = name)
            }
        }
    }
}

fun getDishesOfRegion(city: String): List<String> {

    when (city){
        "Arequipa" -> return Dishes.arequipaList
        "Lima Metropolitana" -> return Dishes.limaList

        //???

        else -> {
            return List(1000) { "$it" }
        }
    }

}

@Composable
private fun ItemDish(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }

    }
}






