package com.nagyadam.eke_enekeskonyv_app.ui


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nagyadam.eke_enekeskonyv_app.R
import com.nagyadam.eke_enekeskonyv_app.ui.theme.EkeEnekeskonyvTheme
import kotlinx.coroutines.launch


//@OptIn(ExperimentalPagerApi::class)
@Composable
fun PageScreen(
    idList: List<Int>
) {
    EkeEnekeskonyvTheme {

        val context = LocalContext.current

        val state = rememberPagerState(pageCount = { idList.size })
        val animationScope = rememberCoroutineScope()

        HorizontalPager(
            state = state,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val song: Song = loadSong(idList[page], context)

                Column(
                    modifier = Modifier
                    //.padding(dimensionResource(R.dimen.padding_medium))
                    //.padding(padding)
                    //horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,

                        )
                    {
                        //VISSZA GOMB
                        if (page > 0) {
                            IconButton(
                                onClick = {
                                    animationScope.launch {
                                        state.animateScrollToPage(state.currentPage - 1)
                                    }
                                }
                            ) {

                                Icon(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    tint = colorResource(R.color.purple_500),
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "Előző"
                                )
                            }
                        }
                        //CÍM
                        Box(
                            modifier = Modifier
                                .width(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${song.id} - ${song.title}",
                                modifier = Modifier
                                    .padding(10.dp),
//                        .padding(start = 30.dp)
//                        .padding(bottom = 10.dp),
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        //ELŐRE GOMB
//                    if(idList[page + 1] != null) {
                        IconButton(
                            onClick = {
                                animationScope.launch {
                                    state.animateScrollToPage(state.currentPage + 1)
                                }
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .fillMaxSize(),
                                tint = colorResource(R.color.purple_500),
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Következő"
                            )
                        }
//                    }
                    }

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                    ) {

                        //KOTTA
                        Image(
                            painter = painterResource(id = getDrawableId(context, song.sheets)),
                            contentDescription = song.id.toString(),
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
                            fontSize = 20.sp,
                        )

                    }
                }


            }


        }
    }
}

fun loadSong(id: Int, context: Context): Song {
//    return when (id){
////        in 1..13 -> liturgyLoader(id, context)
////        in 14..16 -> liturgy14to16Loader(id, context)
////        17 -> liturgy17Loader(17,context)
////        in 1000..10000 -> liturgyAbcLoader(id ,context)
////        else -> songLoader(id, context)
//
//    }
    return songLoader(id, context)
}


