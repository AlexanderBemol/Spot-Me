package montanez.alexander.spotme

import android.content.res.Configuration
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import montanez.alexander.spotme.ui.theme.ButtonTextStyle
import montanez.alexander.spotme.ui.theme.SpotMeTheme
import montanez.alexander.spotme.ui.theme.SpotMeTypography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain()
                }
            }
        }
    }
}

@Composable
fun ScreenMain(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Login.route ){
        composable(Routes.Login.route){
            LoginPage()
        }
    }
}

@Composable
fun LoginPage(){
    var loaded by remember{ mutableStateOf(false)}

    val offsetTitleAnimation : Dp by animateDpAsState(
        if(loaded) 0.dp else 400.dp,
        tween(
            durationMillis = 1000,
            delayMillis = 100,
            easing = { OvershootInterpolator().getInterpolation(it) }
        )
    )

    val onLoginButtonClicked = {

    }

    SideEffect {
        loaded = true
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        Text(
            text = "Welcome to Spot Me!",
            modifier = Modifier
                .padding(36.dp)
                .absoluteOffset(offsetTitleAnimation)
            ,
            style = SpotMeTypography.displayLarge
        )

        LoginAnimation()

        LoginButton(onLoginButtonClicked)

    }
}

@Composable
fun LoginAnimation(){
    var loaded by remember{ mutableStateOf(false)}
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.music_listen)
    )
    val offsetIconAnimation : Dp by animateDpAsState(
        if(loaded) 0.dp else 400.dp,
        tween(
            durationMillis = 1000,
            delayMillis = 300,
            easing = { OvershootInterpolator().getInterpolation(it) }
        )
    )
    SideEffect {
        loaded = true
    }
    LottieAnimation(
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 0.2f,
        modifier = Modifier
            .size(400.dp)
            .absoluteOffset(x = offsetIconAnimation)
    )
}

@Composable
fun LoginButton(onButtonClicked:() -> Unit ){
    var loaded by remember{ mutableStateOf(false)}
    val offsetButtonAnimation : Dp by animateDpAsState(
        if(loaded) 0.dp else 400.dp,
        tween(
            durationMillis = 1000,
            delayMillis = 500,
            easing = { OvershootInterpolator().getInterpolation(it) }
        )
    )
    SideEffect {
        loaded = true
    }
    Button(
        onClick = onButtonClicked,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(32.dp)
            .offset(x = offsetButtonAnimation)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ){
            Image(
                painterResource(id = R.drawable.spotify_icon_rgb_white),
                contentDescription = "Spotify Logo",
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 32.dp)
            ){
                Text(
                    text = "Continue With Spotify", style = ButtonTextStyle,
                    textAlign = TextAlign.Start
                )
            }
        }

    }

}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun DefaultPreview() {
    SpotMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LoginPage()
        }
    }
}