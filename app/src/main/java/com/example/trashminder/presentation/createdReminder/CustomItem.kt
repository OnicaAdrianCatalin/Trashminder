package com.example.trashminder.presentation.createdReminder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trashminder.R
import com.example.trashminder.model.Reminder
import com.example.trashminder.presentation.theme.black
import com.example.trashminder.presentation.theme.blueTrash
import com.example.trashminder.presentation.theme.greenTrash
import com.example.trashminder.presentation.theme.red
import com.example.trashminder.presentation.theme.yellow
import com.example.trashminder.utils.TrashTypeItems

@Composable
fun CustomItem(reminder: Reminder) {

    when (reminder.type) {
        TrashTypeItems.Plastic.trash -> CardItem(
            reminder = reminder,
            startColor = yellow,
            type = TrashTypeItems.Plastic.type,
            painterId = R.drawable.plastic_bottle_png_download_image,
            modifierImage = Modifier.size(90.dp)
        )
        TrashTypeItems.Metale.trash -> CardItem(
            reminder = reminder,
            startColor = red,
            type = TrashTypeItems.Metale.type,
            painterId = R.drawable._removal_ai__tmp_63a2fb077da67,
            modifierImage = Modifier.size(90.dp)
        )
        TrashTypeItems.Hartie.trash -> CardItem(
            reminder = reminder,
            startColor = blueTrash,
            type = TrashTypeItems.Hartie.type,
            painterId = R.drawable.stack_of_old_blank_photographs_thumb30,
            modifierImage = Modifier.size(90.dp)
        )
        TrashTypeItems.Sticla.trash -> CardItem(
            reminder = reminder,
            startColor = greenTrash,
            type = TrashTypeItems.Sticla.type,
            painterId = R.drawable.broken_bottle_png23,
            modifierImage = Modifier.size(130.dp, 80.dp)
        )
        TrashTypeItems.Menajer.trash -> CardItem(
            reminder = reminder,
            startColor = black,
            type = TrashTypeItems.Menajer.type,
            painterId = R.drawable.report_feat_removebg_preview,
            modifierImage = Modifier.size(110.dp, 90.dp)
        )
    }
}

@Composable
private fun CardItem(
    reminder: Reminder,
    startColor: Color,
    type: String,
    painterId: Int,
    modifierImage: Modifier
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Box(Modifier.background(brush = Brush.verticalGradient(listOf(startColor, Color.White)))) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = type,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "Urmator: ${reminder.date}")
                    Text(text = reminder.repetition)
                }
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = painterId),
                    contentDescription = type,
                    alpha = 0.5f,
                    modifier = modifierImage
                )
            }
        }
    }
}

@Composable
@Preview
fun CustomItemPreview() {
    CustomItem(
        reminder = Reminder(
            id = 0,
            type = "plastic",
            date = "",
            repetition = "La 2 saptamani"
        )
    )
}
