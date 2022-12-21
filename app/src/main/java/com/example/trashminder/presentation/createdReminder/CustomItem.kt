package com.example.trashminder.presentation.createdReminder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trashminder.R
import com.example.trashminder.model.Reminder
import com.example.trashminder.presentation.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun CustomItem(reminder: Reminder) {
    if (reminder.type == "plastic") {
        Card(
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            Box(Modifier.background(brush = Brush.verticalGradient(listOf(yellow, Color.White)))) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Column {
                        Text(text = "Plastic", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
                        Text(text = "Urmator: ${reminder.date}")
                        Text(text = "${reminder.repetition}")
                    }
                    Image(
                        painter = painterResource(id = R.drawable.plastic_bottle_png_download_image),
                        contentDescription = "plastic",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

        }
    }

    if (reminder.type == "metal") {
        Card(
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            Box(Modifier.background(brush = Brush.verticalGradient(listOf(red, Color.White)))) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Column {
                        Text(text = "Metal", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
                        Text(text = "Urmator: ${reminder.date}")
                        Text(text = "${reminder.repetition}")
                    }
                    Image(
                        painter = painterResource(id = R.drawable._removal_ai__tmp_63a2fb077da67),
                        contentDescription = "metal",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

        }
    }

    if (reminder.type == "hartie") {
        Card(
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            Box(Modifier.background(brush = Brush.verticalGradient(listOf(blueTrash, Color.White)))) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Column {
                        Text(text = "Hartie si Carton", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
                        Text(text = "Urmator: ${reminder.date}")
                        Text(text = "${reminder.repetition}")
                    }
                    Image(
                        painter = painterResource(id = R.drawable.stack_of_old_blank_photographs_thumb30),
                        contentDescription = "hartie",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

        }
    }

    if (reminder.type == "sticla") {
        Card(
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            Box(Modifier.background(brush = Brush.verticalGradient(listOf(greenTrash, Color.White)))) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Column {
                        Text(text = "Sticla", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
                        Text(text = "Urmator: ${reminder.date}")
                        Text(text = "${reminder.repetition}")
                    }
                    Image(
                        painter = painterResource(id = R.drawable.broken_bottle_png23),
                        contentDescription = "sticla",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

        }
    }

    if (reminder.type == "menajer") {
        Card(
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            Box(Modifier.background(brush = Brush.verticalGradient(listOf(black, Color.White)))) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Column {
                        Text(text = "Menajer", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
                        Text(text = "Urmator: ${reminder.date}")
                        Text(text = "${reminder.repetition}")
                    }
                    Image(
                        painter = painterResource(id = R.drawable.document),
                        contentDescription = "menajer",
                        modifier = Modifier.size(70.dp)
                    )
                }
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