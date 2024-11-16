package com.example.smr_coffeeshops

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smr_coffeeshops.ui.theme.Pink80
import com.example.smr_coffeeshops.ui.theme.PurpleClaro
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
@Composable
fun Comentarios(nombreCafe: String) {

    //Variable que almacena la funcion de la lista de comentarios
    val SuperComents by remember { mutableStateOf(listaComentarios()) }

    //Variables para controlar estado
    //Varieble para recordar el estado del desplazamiento (scroll) de la lista Lazy
    val rvState = rememberLazyStaggeredGridState()
    //variable que recuerde el estado de la corutina (acciones asincrónicas), al hacer click en el boton
    val corutineScope = rememberCoroutineScope()

    //Variables para la barra de busqueda
    var buscarComent by remember { mutableStateOf("") }
    val filtrarComents = SuperComents.filter { it.texto.contains(buscarComent, ignoreCase = true) }

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


        //Barra de busqueda
        TextField(
            value = buscarComent,
            onValueChange = { buscarComent = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {Text("Filtrar comentarios")},
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.Gray)
                }
            },
        )



        //Titulo cafeteria seleccionada en la Pantalla1
        Text(
            text = nombreCafe,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 150.dp),   //Padding para centrar
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 50.sp
        )


        //Lazy Vertical Staggered Grid para los comentarios
        LazyVerticalStaggeredGrid(
            state = rvState,    //Conecta el estado del LazyColumn con la variable rvState, lo que permite controlar y observar el desplazamiento de la lista.
            columns = StaggeredGridCells.Fixed(2),  //Numero de column
            modifier = Modifier.weight(1f), //Importante para que se vea el 100% del boton
            content = {
                items(filtrarComents) { comentarios ->
                    ItemComentarios(comentario = comentarios)
                }
            }
        )

        //Boton control desplazamiento
        //firstVisibleItemIndex nos da el índice del primer ítem visible en la lista.
        //rvState.firstVisibleItemIndex > 0: aparece el boton al hacer scroll hacia arriba
        val mostrarBoton by remember { derivedStateOf { rvState.firstVisibleItemIndex > 0 } }
        //si el primer ítem visible tiene un índice mayor a 0 (es decir, la lista está al principio), no se muestra el boton.
        if (mostrarBoton) {
            Button(
                onClick = {
                    corutineScope.launch {  //launch inicia la corutina
                        //animar el desplazamiento de la lista hasta el primer ítem (ítem en la posición 0)
                        //animateScrollToItem mueve la lista de forma animada
                        rvState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
                colors = ButtonDefaults.buttonColors(Pink80)
            ) {
                Text("Add new comment", color = Color.Black)
            }
        }
    }
}

//Funcion para agrupar los comentarios en una lista
fun listaComentarios(): List<Comentario>{
    return listOf(
        Comentario("Servicio algo flojo, aún así lo recomiendo"),
        Comentario("Céntrica y acogedora. Volveremos seguro"),
        Comentario("La ambientacion muy buena, pero en la planta de arriba un poco escasa."),
        Comentario("La comida estaba deliciosa y bastante bien de precio, mucha variedad de platos"),
        Comentario("El personal muy amable, nos permitieron ver todo el establecimiento."),
        Comentario("Muy bueno"),
        Comentario("Excelente. Destacable la extensa carta de cafés"),
        Comentario("Buen ambiente y buen servicio. Lo recomiendo."),
        Comentario("En días festivos demasiado tiempo de espera. Los camareros/as no dan abasto. No lo recomiendo. No volveré"),
        Comentario("Repetiremos. Gran selección de tartas y cafés."),
        Comentario("Todo lo que he probado en la cafetería está riquísimo, dulce o salado."),
        Comentario("La vajilla muy bonita todo de diseño que en el entorno del bar queda ideal."),
        Comentario("Puntos negativos: el servicio es muy lento y los precios son un poco elevados."),
        Comentario("Servicio algo flojo, aún así lo recomiendo"),
        Comentario("Céntrica y acogedora. Volveremos seguro"),
        Comentario("La ambientacion muy buena, pero en la planta de arriba un poco escasa."),
        Comentario("La comida estaba deliciosa y bastante bien de precio, mucha variedad de platos"),
        Comentario("El personal muy amable, nos permitieron ver todo el establecimiento."),
        Comentario("Muy bueno"),
        Comentario("Excelente. Destacable la extensa carta de cafés"),
        Comentario("Buen ambiente y buen servicio. Lo recomiendo."),
        Comentario("En días festivos demasiado tiempo de espera. Los camareros/as no dan abasto. No lo recomiendo. No volveré"),
        Comentario("Repetiremos. Gran selección de tartas y cafés."),
        Comentario("Todo lo que he probado en la cafetería está riquísimo, dulce o salado."),
        Comentario("La vajilla muy bonita todo de diseño que en el entorno del bar queda ideal."),
        Comentario("Puntos negativos: el servicio es muy lento y los precios son un poco elevados."),
    )
}

//Funcion que crea las Cards
@Composable
fun ItemComentarios(comentario: Comentario){

        Card (modifier = Modifier.padding(16.dp),
            elevation = CardDefaults.cardElevation(10.dp) //Elevación de la Card para sombreado por detras
        ) {
           //Crear un Box para poder aplicar el color a la Card
           Box(modifier = Modifier.fillMaxWidth().background(PurpleClaro)){
                //Comentarios
                Text(text = comentario.texto, modifier = Modifier.padding(5.dp))
        }
    }
}