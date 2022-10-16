package montanez.alexander.spotme.data.model

import android.graphics.Bitmap

data class Artist(
    val id: Int,
    val name: String,
    val bitmap: Bitmap,
    val ranking: Int
)
