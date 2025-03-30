package com.example.sodv3203project.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sodv3203project.R

val winkle = FontFamily(
    Font(R.font.winkle_regular)
)

val kanit = FontFamily(
    Font(R.font.kanit_regular),
    Font(R.font.kanit_italic),
    Font(R.font.kanit_bolditalic),
    Font(R.font.kanit_bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = winkle,
        fontSize = 40.sp
    )
)