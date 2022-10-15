package montanez.alexander.spotme.ui.views.home

import android.content.res.Configuration
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import montanez.alexander.spotme.R
import montanez.alexander.spotme.ui.components.*
import montanez.alexander.spotme.ui.theme.SpotMeTheme




@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeContent(padding: PaddingValues){
    val listOfRecentTracks = remember { getDemoList() }

    Column(
        Modifier
            .padding(padding)
            .fillMaxSize()
    ) {

        WelcomeRow()

        Text(
            text = "Now Playing",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp, bottom = 4.dp),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Box(
                Modifier.gradientBackground(
                    listOf(
                        MaterialTheme.colorScheme.surface,
                        if(isSystemInDarkTheme()) getPaletteFromBitmap().darkMutedSwatch?.rgbAsColor()
                            ?: MaterialTheme.colorScheme.surface
                        else getPaletteFromBitmap().lightMutedSwatch?.rgbAsColor()
                            ?: MaterialTheme.colorScheme.surface
                    ),
                    280f
                )
            ){
                NowPlayingCard()
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Recently Played Tracks",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp, bottom = 16.dp),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        SideEffect {
            listOfRecentTracks.shuffle()
        }
        LazyColumn{
            items(
                listOfRecentTracks,
                key = {it}
            ){ text  ->
                TrackCard(
                    "Johanna",
                    Modifier.animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                        )
                    )
                )
                Spacer(Modifier.size(8.dp))
            }
        }
    }
}

@Composable
private fun WelcomeRow(){
    Row(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ){
        Column{
            Text(
                text = "Hi Alexander!",
                style = TextStyle(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Oct 13, 2022",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun NowPlayingCard(){
    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Image(
            painter = painterResource(id = R.drawable.new_sweney_cover),
            contentDescription = "Album Cover",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(180.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                Text(
                    text = "Johanna",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "Stephen Sondheim, Edmund Lyndeck",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Text(
                text = "My Laptop",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = getPaletteFromBitmap().vibrantSwatch?.titleRGBAsColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant
            )

        }
    }
}

@Composable
fun TrackCard(title: String, modifier: Modifier){
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.sweeney_cover),
            contentDescription = "Album Cover",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(50.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Stephen Sondheim, Edmund Lyndeck",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

private fun getDemoList() = mutableStateListOf("Johanna1","Johanna2","Johanna3","Johanna4","Johanna5")

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun HomeContentPreview() {
    SpotMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeContent(PaddingValues(0.dp))
        }
    }
}