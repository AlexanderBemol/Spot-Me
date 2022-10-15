package montanez.alexander.spotme.ui.views.login

import android.content.res.Configuration
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import montanez.alexander.spotme.R
import montanez.alexander.spotme.Routes
import montanez.alexander.spotme.ui.theme.*

val defaultLoadedValue = LoginAnimationState.ENTER

@Composable
fun LoginView(navController: NavHostController){
    var loaded by remember{ mutableStateOf(defaultLoadedValue) }
    val offsetTitleAnimation : Dp by animateDpAsState(
        when(loaded){
            LoginAnimationState.ENTER -> 400.dp
            LoginAnimationState.IDLE -> 0.dp
            else -> (-400).dp
        },
        tween(
            durationMillis = 1000,
            delayMillis = 100,
            easing = { OvershootInterpolator().getInterpolation(it) }
        )
    )

    val onLoginButtonClicked = {
        navController.navigate(Routes.Home.route)
        loaded = LoginAnimationState.EXIT
    }

    SideEffect {
        loaded = LoginAnimationState.IDLE
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.login_background_1),
            contentDescription = "background_image",
            contentScale = ContentScale.Crop
        )
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
                style = SpotMeTypography.displayLarge,
                color = md_theme_dark_onBackground
            )

            LoginAnimation(loaded)

            LoginButton(loaded,onLoginButtonClicked)

        }
    }
}

@Composable
fun LoginAnimation(loaded: LoginAnimationState){
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.music_bars)
    )
    val offsetIconAnimation : Dp by animateDpAsState(
        when(loaded){
            LoginAnimationState.ENTER -> 400.dp
            LoginAnimationState.IDLE -> 0.dp
            else -> (-400).dp
        },
        tween(
            durationMillis = 1000,
            delayMillis = 300,
            easing = { OvershootInterpolator().getInterpolation(it) }
        )
    )
    LottieAnimation(
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1f,
        modifier = Modifier
            .size(400.dp)
            .absoluteOffset(x = offsetIconAnimation)
    )
}

@Composable
fun LoginButton(loaded: LoginAnimationState,onButtonClicked:() -> Unit){
    val offsetButtonAnimation : Dp by animateDpAsState(
        when(loaded){
            LoginAnimationState.ENTER -> 400.dp
            LoginAnimationState.IDLE -> 0.dp
            else -> (-400).dp
        },
        tween(
            durationMillis = 1000,
            delayMillis = 500,
            easing = { OvershootInterpolator().getInterpolation(it) }
        )
    )
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
                colorFilter = ColorFilter.tint(
                    if(isSystemInDarkTheme()) dark_spotify_icon_color else light_spotify_icon_color
                )
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 32.dp)
            ){
                Text(
                    text = "Continue with Spotify",
                    style = ButtonTextStyle,
                    textAlign = TextAlign.Start,
                    color = if(isSystemInDarkTheme()) dark_spotify_icon_color
                    else light_spotify_icon_color
                )
            }
        }

    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun DefaultPreview() {
    montanez.alexander.spotme.ui.theme.SpotMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            LoginView(navController)
        }
    }
}