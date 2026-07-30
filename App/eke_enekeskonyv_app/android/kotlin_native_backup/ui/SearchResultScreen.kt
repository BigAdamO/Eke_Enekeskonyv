package com.nagyadam.eke_enekeskonyv_app.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nagyadam.eke_enekeskonyv_app.R
import com.nagyadam.eke_enekeskonyv_app.ui.theme.EkeEnekeskonyvTheme


@Composable
fun SearchResultScreen(
    searched: String,
    //songList: List<Content>,
    navController: NavController,
) {
    EkeEnekeskonyvTheme {
        Scaffold(
            topBar = {
                SongTopBar(navController)
            }
        )
        { padding ->
            val context = LocalContext.current

            Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(padding)
                        .padding(5.dp)
                        .padding(top = 30.dp),
            ) {
                if (searched == "tartalom") {
                    Tartalom(navController, context)
                } else {
                    SearchResult(searched, navController, context)
                }
            }
        }

    }
}

@Composable
fun SearchResult(searched: String, navController: NavController, context: Context = LocalContext.current){

    var songList = liturgyListLoader(context) + liturgy14ListLoader(context) + liturgy17ListLoader(context) + songListLoader(context)

    songList = songList.filter { it.title.contains(searched) || it.title.lowercase().contains(searched.lowercase())}

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Találatok:",
            fontSize = (30 * textSize).sp,
            lineHeight = (35 * textSize).sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)

        )

        if(songList.isEmpty()){
            Text(
                text = "Nincs találat",
                fontSize = (30 * textSize).sp,
                lineHeight = (35 * textSize).sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(50.dp)
            )
        }

        LoadToScreen(songList, navController)

    }

}

@Composable
fun Tartalom(navController: NavController, context: Context){

    Title("Tartalom")
    Spacer(Modifier.padding(20.dp))

    // Titles("Mi Atyánk:")
    // Text(
    //     text = "Mi Atyánk",
    //     fontSize = (16 * textSize).sp,
    //     lineHeight = (20 * textSize).sp,
    //     fontWeight = FontWeight.Medium,
    //     color = colorResource(R.color.app_blue),
    //     textDecoration = TextDecoration.Underline,
    //     modifier = Modifier
    //         .padding(10.dp)
    //         .padding(start = 20.dp)
    //         .clickable { navController.navigate("prayers/${2000}") }
    // )

    // Titles("Hitvallások:")
    // Text(
    //     text = "Apostoli hitvallás",
    //     fontSize = (16 * textSize).sp,
    //     lineHeight = (20 * textSize).sp,
    //     fontWeight = FontWeight.Medium,
    //     color = colorResource(R.color.app_blue),
    //     textDecoration = TextDecoration.Underline,
    //     modifier = Modifier
    //         .padding(10.dp)
    //         .padding(start = 20.dp)
    //         .clickable { navController.navigate("prayers/${2300}") }
    // )

    // Text(
    //     text = "Níceai hitvallás",
    //     fontSize = (16 * textSize).sp,
    //     lineHeight = (20 * textSize).sp,
    //     fontWeight = FontWeight.Medium,
    //     color = colorResource(R.color.app_blue),
    //     textDecoration = TextDecoration.Underline,
    //     modifier = Modifier
    //         .padding(10.dp)
    //         .padding(start = 20.dp)
    //         .clickable { navController.navigate("prayers/${2301}") }
    // )

    // //ABC
    // Titles("Istentiszteleti rendek:")

    // val liturgyAbc = liturgyAbcListLoader(context)
    // for (song in liturgyAbc){
    //     Text(
    //         text = "${idToString(song.id)} - ${song.title}",
    //         fontSize = (16 * textSize).sp,
    //         lineHeight = (20 * textSize).sp,
    //         fontWeight = FontWeight.Medium,
    //         color = colorResource(R.color.app_blue),
    //         textDecoration = TextDecoration.Underline,
    //         modifier = Modifier
    //             .padding(10.dp)
    //             .padding(start = 20.dp)
    //             .clickable { liturgyOrSong(song.id.toString(), navController, true) }
    //     )
    // }
    // Spacer(modifier = Modifier.padding(20.dp))

    // //1-13
    // Titles("Énekversek a vasárnapi istentisztelet énekverses rendjéhez:")

    // val liturgy = liturgyListLoader(context)
    // LoadToScreen(liturgy, navController)

    // //14-16

    // Titles("A vasárnapi istentisztelet énekelt liturgikus rendjei:")

    // val liturgy14 = liturgy14ListLoader(context)
    // LoadToScreen(liturgy14, navController)

    // //17
    // Titles("A mindennapi istentisztelet énekelt liturgikus rendje:")

    // val liturgy17 = liturgy17ListLoader(context)
    // LoadToScreen(liturgy17, navController)

    // //18-591
    // Titles("A mindennapi istentisztelet énekelt liturgikus rendje:")

    val songs = songListLoader(context)
    LoadToScreen(songs, navController)
}




@Composable
fun LoadToScreen(songList: List<Content>, navController: NavController) {

    for (song in songList){
        Text(
            text = "${song.id} - ${song.title}",
            fontSize = (16 * textSize).sp,
            lineHeight = (20 * textSize).sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(10.dp)
                .padding(start = 20.dp)
                .clickable { liturgyOrSong(song.id.toString(), navController, true) }
        )
    }
    Spacer(modifier = Modifier.padding(20.dp))
}

@Composable
fun Titles(title: String){

    Text(
        text = title,
        fontSize = (20 * textSize).sp,
        lineHeight = (25 * textSize).sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(10.dp),
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun Title(title: String){
    Text(
        text = title,
        fontSize = (30 * textSize).sp,
        lineHeight = (35 * textSize).sp,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .padding(10.dp)
            .padding(start = 15.dp),
        color = MaterialTheme.colorScheme.tertiary
    )
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SearchResultScreenPreview() {
    EkeEnekeskonyvTheme(darkTheme = false) {
        SearchResultScreen("aki", navController = NavController(LocalContext.current))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SearchResultScreenPreviewDark() {
    Eke EnekeskonyvTheme(darkTheme = true) {
        SearchResultScreen("tartalom", navController = NavController(LocalContext.current))
    }
}