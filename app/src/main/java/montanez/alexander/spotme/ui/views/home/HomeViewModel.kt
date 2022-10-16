package montanez.alexander.spotme.ui.views.home

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import montanez.alexander.spotme.R
import montanez.alexander.spotme.data.model.Artist
import montanez.alexander.spotme.data.model.Track

class HomeViewModel: ViewModel() {
    val listOfRecentTracks = mutableStateListOf<Track>()
    val listOfTopTracks = mutableStateListOf<Track>()
    val listOfTopArtists = mutableStateListOf<Artist>()

    suspend fun getListOfRecentTracks(context: Context){
        listOfRecentTracks.clear()
        populateRecentTrackList(context)
    }

    suspend fun getListOfTopTracks(context: Context){
        listOfTopTracks.clear()
        Log.d("Testeo","Getting list")
        populateTopTrackList(context)
    }

    suspend fun getListOfTopArtists(context: Context){
        listOfTopArtists.clear()
        populateTopArtistsList(context)
    }

    fun shuffleTopTracks() = listOfTopTracks.shuffle()

    fun shuffleTopArtists() = listOfTopArtists.shuffle()

    private suspend fun populateRecentTrackList(context: Context){
        withContext(Dispatchers.IO){
            val list = mutableListOf<Track>()
            for(i in 1..5){
                list.add(
                    Track(
                        id = i,
                        name = "Johanna",
                        artists = "Stephen Sondheim, Edmund Lyndeck",
                        albumArt = BitmapFactory
                            .decodeResource(context.resources, R.drawable.sweeney_cover),
                        ranking = i
                    )
                )
            }
            listOfRecentTracks.addAll(list)
        }
    }

    private suspend fun populateTopTrackList(context: Context){
        withContext(Dispatchers.IO){
            val list = mutableListOf<Track>()
            for(i in 1..5){
                list.add(
                    Track(
                        id = i,
                        name = "Johanna",
                        artists = "Stephen Sondheim, Edmund Lyndeck",
                        albumArt = BitmapFactory
                            .decodeResource(context.resources, R.drawable.sweeney_cover),
                        ranking = i
                    )
                )
            }
            listOfTopTracks.addAll(list)
        }
    }

    private suspend fun populateTopArtistsList(context: Context){
        withContext(Dispatchers.IO){
            val list = mutableListOf<Artist>()
            for(i in 1..5){
                list.add(
                    Artist(
                        i,
                        "Lin Manuel",
                        BitmapFactory
                            .decodeResource(context.resources, R.drawable.lin_manuel),
                        i
                    )
                )
            }
            listOfTopArtists.addAll(list)
        }
    }


}