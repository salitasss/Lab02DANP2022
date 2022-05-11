package com.example.lab02_danp_2022

import android.R
import android.content.ClipData
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.lab02_danp_2022.ui.theme.LAB02_DANP_2022Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme

                    //Greeting("Android")
                    dropDownMenu()


        }
    }
}
@Preview(showBackground = true)
@Composable
fun dropDownMenu(){
    var expanded by remember { mutableStateOf(false)}
    val list = listOf("1. Amazonas","2. Áncash","3. Apurímac", "4. Arequipa", "5. Ayacucho",
        "6. Cajamarca", "7. Callao", "8. Cusco" , "9. Huancavelica" , "10. Huánuco" , "11.Ica" , "12. Junín" ,
        "13.La Libertad", "14. Lambayeque", "15. Lima Metropolitana", "16. Loreto" , "17. Madre de Dios" ,
        "18. Moquegua" , "19. Pasco" , "20. Piura" , "21. Puno" , "22. San Martín" , "23. Tacna" , "24. Tumbes" , "25. Ucayali")
    var selectedItem by remember { mutableStateOf("")}

    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    
    val icon = if (expanded){
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(20.dp)){
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {selectedItem = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.toSize()
                },
            label = {Text(text ="Escoger Ciudad")},
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
    }
            

    }






