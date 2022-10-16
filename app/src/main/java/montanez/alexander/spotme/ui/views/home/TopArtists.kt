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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import montanez.alexander.spotme.R
import montanez.alexander.spotme.data.model.Artist
import montanez.alexander.spotme.data.model.Track
import montanez.alexander.spotme.ui.components.TimeFilterChipsComponent
import montanez.alexander.spotme.ui.theme.SpotMeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopArtistsContent(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current
) {

    LaunchedEffect(key1 = Unit){
        coroutineScope.launch {
            viewModel.getListOfTopArtists(context)
        }
    }

    Column(Modifier.padding(paddingValues)) {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Top Artists",
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
            viewModel.shuffleTopArtists()
        }

        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn{
            items(
                items = viewModel.listOfTopArtists,
                key = {it.id}
            ){ artist  ->
                TopArtistRow(artist, Modifier.animateItemPlacement(
                    animationSpec = tween(500)
                ))
                Spacer(Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun TopArtistRow(artist: Artist, modifier: Modifier){
    Row (
        modifier
            .fillMaxWidth()
    ){
        Text(
            text = "${artist.id}°",
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
            bitmap = artist.bitmap.asImageBitmap(),
            contentDescription = "Album Cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = artist.name,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun TopArtistsPreview() {
    SpotMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TopArtistsContent(PaddingValues(bottom = 16.dp))
        }
    }
}