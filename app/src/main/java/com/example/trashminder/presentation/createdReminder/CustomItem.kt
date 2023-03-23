package com.example.trashminder.presentation.createdReminder

import android.content.Context
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
import com.example.trashminder.utils.toLocalizedString

@Composable
fun CustomItem(reminder: Reminder?, context: Context) {

    when (reminder?.type) {
        TrashType.Plastic -> CardItem(
            context = context,
            reminder = reminder,
            startColor = yellow,
            painterId = R.drawable.plastic_bottle_png_download_image,
            modifierImage = Modifier.size(90.dp)
        )
        TrashType.Metals -> CardItem(
            context = context,
            reminder = reminder,
            startColor = red,
            painterId = R.drawable._removal_ai__tmp_63a2fb077da67,
            modifierImage = Modifier.size(90.dp)
        )
        TrashType.Paper -> CardItem(
            context = context,
            reminder = reminder,
            startColor = blueTrash,
            painterId = R.drawable.stack_of_old_blank_photographs_thumb30,
            modifierImage = Modifier.size(90.dp)
        )
        TrashType.Glass -> CardItem(
            context = context,
            reminder = reminder,
            startColor = greenTrash,
            painterId = R.drawable.broken_bottle_png23,
            modifierImage = Modifier.size(130.dp, 80.dp)
        )
        TrashType.Residuals -> CardItem(
            context = context,
            reminder = reminder,
            startColor = black,
            painterId = R.drawable.report_feat_removebg_preview,
            modifierImage = Modifier.size(110.dp, 90.dp)
        )
        else -> {}
    }
}

@Composable
private fun CardItem(
    reminder: Reminder,
    startColor: Color,
    painterId: Int,
    modifierImage: Modifier,
    context: Context
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
                        text = reminder.type.toLocalizedString(context),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "${context.getString(R.string.next_text)}: ${reminder.date}")
                    Text(text = reminder.repetition.toLocalizedString(context))
                }
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = painterId),
                    contentDescription = reminder.type.toLocalizedString(context),
                    alpha = 0.5f,
                    modifier = modifierImage
                )
            }
        }
    }
}
