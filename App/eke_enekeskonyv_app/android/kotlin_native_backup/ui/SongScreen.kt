package com.nagyadam.eke_enekeskonyv_app.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagyadam.eke_enekeskonyv_app.R
import com.nagyadam.eke_enekeskonyv_app.ui.theme.EkeEnekeskonyvTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Composable
fun SongScreen(
    idx: Int,
    navController: NavController,
    searched: Boolean
    //songs: Array<String>,
    //songsIdx: Int
) {
    EkeEnekeskonyvTheme {
        val id: Int
        if (searched) {
            id = idx
        } else {
            id = songNumbers[idx].toInt()
        }

        val context = LocalContext.current
        val song = songLoader(id, context)
        Scaffold(
            topBar = {
                SongTopBar(
                    navController = navController
                )
            }
        )
        { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    )
                {
                    TitleRow(
                        searched = searched,
                        idx = idx,
                        id = song.id,
                        navController = navController,
                        title = song.title
                    )
                }

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                ) {

                    //KOTTA
                    Image(
                        painter = painterResource(id = getDrawableId(context, song.sheets)),
                        contentDescription = id.toString(),
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .padding(end = 20.dp)
                            .padding(bottom = 10.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )

                    //SZÖVEG
                    Text(
                        text = "${song.lyrics} \n\n\n\n\n\n\n",
                        modifier = Modifier
                            //.verticalScroll(rememberScrollState())
                            .padding(15.dp),
                        fontSize = (20 * textSize).sp,
                        lineHeight = (25 * textSize).sp,
                    )

                }
            }


        }
    }
}


@Serializable
data class Song(
    override val id: Int,
    override val title: String,
    val lyrics: String,
    val sheets: String
) : Content

fun songLoader(id: Int, context: Context) : Song {
    val songList = songListLoader(context)
    return songList.find { it.id == id }!!
}

fun songListLoader(context: Context) : List<Song> {
    val songs = loadJsonFromRaw( context, R.raw.songs)
    return Json.decodeFromString(songs)
}

fun getDrawableId(context: Context, resourceName: String): Int {
    return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
}

fun loadJsonFromRaw(context: Context, resourceId: Int): String {
    return context.resources.openRawResource(resourceId).bufferedReader().use { it.readText() }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongTopBar(
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = (22).sp,
                lineHeight = (25).sp,
                modifier = Modifier.clickable { navController.navigate("home_screen") }
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Vissza",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

        }
    )
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SongScreenPreview() {
    EkeEnekeskonyvTheme(darkTheme = false) {
        SongScreen(41, rememberNavController(), true)
    }
}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun SongScreenPreviewDark() {
//    EkeEnekeskonyvTheme(darkTheme = true) {
//        SongScreen(40, rememberNavController(), true)
//    }
//}