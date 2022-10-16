package montanez.alexander.spotme.data.model

import android.graphics.Bitmap

data class Track(
    val id: Int,
    val name: String,
    val artists: String,
    val albumArt: Bitmap,
    val ranking: Int
)
