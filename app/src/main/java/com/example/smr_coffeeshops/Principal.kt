package com.example.smr_coffeeshops

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smr_coffeeshops.ui.theme.Pink80
import com.example.smr_coffeeshops.ui.theme.Purple40
import kotlin.math.ceil
import kotlin.math.floor


//@Preview(showBackground = true)
@Composable
fun Pantalla1(navController: NavController) {

    //Variable que almacena la funcion de la lista de cafeterias
    val Super by remember { mutableStateOf(listaCafeterias()) }

    //Variable para la seleccion de la cafeteria, para pasar a la siguiente pantalla y que aparezca el titulo de la elegida
    val selectedCafe by remember { mutableStateOf<Cafeterias?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize() //Ocupa toda la pantalla
    ) {

        /*//Barra de arriba
    //TopAppBar pero no lo hemos visto, uso text
    Text(
        "List of CofeeShops",
        modifier = Modifier.background(Pink80).fillMaxWidth(),
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 40.sp
    )*/

        //SUSTITUCION BARRA DE ARRIBA TEXT POR EL RETO.
        //Menu superior
        Menu()

            LazyColumn (verticalArrangement = Arrangement.spacedBy(10.dp),//Añade espacio entre las Cards)
                modifier = Modifier.fillMaxWidth())
            {
                items(Super) { cafeterias ->
                    //Funcion con la lista y los datos de la cafeteria
                    ItemCafeterias(cafeterias = cafeterias) {
                        //Redirige a la pantalla2 teniendo en cuenta la cafeteria selecccionada
                         selectedCafe -> navController.navigate("Pantalla2/${cafeterias.nombre}")
                    }
                }
            }
    }
}

//Funcion para obtener la lista de cafeterias y que se pueda clicar en ellas
//Devuelve la lista
fun listaCafeterias(): List<Cafeterias>{
    return listOf(
        Cafeterias(R.drawable.cafe1, "Kofu", "Pl. Borrull, Castellón"),
        Cafeterias(R.drawable.cafe2, "Sirope", "C/ Alloza, Castellón"),
        Cafeterias(R.drawable.cafe3, "Dama Blanca", "C/ Asensi, Castellón"),
    )
}

//Esta funcion construye la card
//Gestion de clicks con onItemSelected (lo que se quiera mostrar con el clck)
@Composable
fun ItemCafeterias(cafeterias: Cafeterias, onItemSelected: (Cafeterias) -> Unit){

    //Cards
    Card (modifier = Modifier.padding(16.dp)
        .clickable {onItemSelected(cafeterias)},    //Hace clicable toda la Card, cualquier elemento
        elevation = CardDefaults.cardElevation(10.dp) //Elevación de la Card para sombreado por detras
          ){

            //Agrupar la info en columna
            Column (modifier = Modifier.background(Pink80))
            {
                //Imagen cafeteria
                Image(
                    modifier = Modifier.fillMaxWidth(), //Ocupa todo el ancho de la Card
                    painter = painterResource(id = cafeterias.img),
                    contentDescription = "img",
                    contentScale = ContentScale.Crop
                )

                //Columa para añadir espacio a sus elementos
                Column (verticalArrangement = Arrangement.spacedBy(10.dp)){ //scapedBy añade espacio entre elementos
                        //Nombre cafeteria
                        Text(text = cafeterias.nombre, fontWeight = FontWeight.Bold,fontFamily = FontFamily.Cursive, fontSize = 45.sp)

                        //RatingBar, llamada a la funcion
                        RatingBar()

                        //Direccion cafeteria
                        Text(text = cafeterias.direc)
                }

                //DIVIDED
                //Ralla que separa los items
                HorizontalDivider(color = Color.Gray)

                //BOTON - sin funcionalidad
                //TextButton, con texto
                //Al clicar aparece sombra, la forma del boton.
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(top=10.dp)) {
                    Text("RESERVE", color = Purple40, fontSize = 15.sp)
                }

            }
    }
}

//Funcion Ratingar
@Composable
fun RatingBar(
    starsColor: Color= Purple40
) {

    //Variable para el rating (0 a 5)
    var rating by rememberSaveable { mutableStateOf(0) } //Valor inicial del rating

    Row(modifier = Modifier.padding(top = 10.dp)) {
        //Iconos estrella
        (1..5).forEach { index ->
            Icon(
                //Si el indice es menor o igual que el rating, estrella llena
                imageVector = if (index <= rating) {Icons.Filled.Star}
                                else Icons.Filled.StarOutline,  //sino, vacia
                contentDescription = "Estrella",
                tint = starsColor,
                modifier = Modifier.size(40.dp)
                    .clickable {
                        rating = index //Se actualiza el rating con el número de estrellas
                    }
            )
        }
    }
}

//Menu Superior. RETO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(){
    TopAppBar(modifier = Modifier.background(Pink80),
        title = {
            Text(
                "CoffeShops",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp)
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Menu,
                    contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink80))
}