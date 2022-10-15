package montanez.alexander.spotme.ui.components

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TimeFilterChipsComponent(onChipSelected: (Int) -> Unit){
    val list = remember { getListOfChips()}

    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(items = list, key = {it.id}) { chip ->
            FilterChip(
                modifier = Modifier.animateItemPlacement(
                    animationSpec = tween(
                        durationMillis = 500
                    )
                ),
                selected = chip.isSelected,
                onClick = {
                    list.map {
                        it.apply { isSelected = (it.id == chip.id) }
                    }
                    list.sortWith(compareBy({!it.isSelected},{it.id}))
                    onChipSelected(chip.id)
                },
                label = { Text(text = chip.text) },
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

private fun getListOfChips() = mutableStateListOf(
    TimeChipObject(1,true,"4 Weeks"),
    TimeChipObject(2,false,"6 Months"),
    TimeChipObject(3,false,"Lifetime")
)

data class TimeChipObject(
    val id: Int,
    var isSelected: Boolean,
    val text: String
)