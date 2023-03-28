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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trashminder.R
import com.example.trashminder.model.Reminder
import com.example.trashminder.presentation.theme.black
import com.example.trashminder.presentation.theme.blueTrash
import com.example.trashminder.presentation.theme.greenTrash
import com.example.trashminder.presentation.theme.red
import com.example.trashminder.presentation.theme.yellow
import com.example.trashminder.utils.TrashType
import com.example.trashminder.utils.toResourceId

@Composable
fun CustomItem(reminder: Reminder) {

    when (reminder.type) {
        TrashType.Plastic -> CardItem(
            reminder = reminder,
            startColor = yellow,
            painterId = R.drawable.plastic_bottle_png_download_image,
            modifierImage = Modifier.size(90.dp)
        )
        TrashType.Metal -> CardItem(
            reminder = reminder,
            startColor = red,
            painterId = R.drawable._removal_ai__tmp_63a2fb077da67,
            modifierImage = Modifier.size(90.dp)
        )
        TrashType.Paper -> CardItem(
            reminder = reminder,
            startColor = blueTrash,
            painterId = R.drawable.stack_of_old_blank_photographs_thumb30,
            modifierImage = Modifier.size(90.dp)
        )
        TrashType.Glass -> CardItem(
            reminder = reminder,
            startColor = greenTrash,
            painterId = R.drawable.broken_bottle_png23,
            modifierImage = Modifier.size(130.dp, 80.dp)
        )
        TrashType.Residual -> CardItem(
            reminder = reminder,
            startColor = black,
            painterId = R.drawable.report_feat_removebg_preview,
            modifierImage = Modifier.size(110.dp, 90.dp)
        )
    }
}

@Composable
private fun CardItem(
    reminder: Reminder,
    startColor: Color,
    painterId: Int,
    modifierImage: Modifier,
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
                        text = stringResource(id = reminder.type.toResourceId()),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "${stringResource(R.string.next_text)}: ${reminder.date}")
                    Text(text = stringResource(id = reminder.repetition.toResourceId()))
                }
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = painterId),
                    contentDescription = stringResource(id = reminder.type.toResourceId()),
                    alpha = 0.5f,
                    modifier = modifierImage
                )
            }
        }
    }
}
