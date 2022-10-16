package montanez.alexander.spotme.ui.views.home

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import montanez.alexander.spotme.data.model.Track
import montanez.alexander.spotme.ui.components.TimeFilterChipsComponent
import montanez.alexander.spotme.ui.theme.SpotMeTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopSongsContent(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = viewModel()
) {
    val context: Context = LocalContext.current
    LaunchedEffect(key1 = Unit){
        viewModel.getListOfTopTracks(context)
    }

    Column(Modifier.padding(paddingValues)) {
        Log.d("Testeo","Composing Top Songs")
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Top Songs",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp, bottom = 4.dp),
            style = TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.size(8.dp))

        TimeFilterChipsComponent {
            viewModel.shuffleTopTracks()
            Log.d("Testeo","Sorting")
        }

        Spacer(modifier = Modifier.size(16.dp))

        LazyColumn{
            items(
                items = viewModel.listOfTopTracks,
                key = {it.id}
            ){ track  ->
                TopTrackCard(track,Modifier.animateItemPlacement(
                    animationSpec = tween(500)
                ))
                Spacer(Modifier.size(8.dp))
            }
            Log.d("Testeo","Recompose songs")
        }
    }
}

@Composable
fun TopTrackCard(track: Track, modifier: Modifier){
    Row (
        modifier
            .fillMaxWidth()
    ){
        Text(
            text = "${track.id}°",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Image(
            bitmap = track.albumArt.asImageBitmap(),
            contentDescription = "Album Cover",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = track.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = track.artists,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun TopSongsPreview() {
    SpotMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TopSongsContent(PaddingValues(bottom = 16.dp))
        }
    }
}