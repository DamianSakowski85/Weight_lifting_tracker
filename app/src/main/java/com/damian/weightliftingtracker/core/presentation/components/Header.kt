package com.damian.weightliftingtracker.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R

@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        color = colorResource(id = R.color.header_text),
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.header_background))
            .semantics { heading() }
            .padding(horizontal = 8.dp, vertical = 8.dp)
    )
}

@Preview("Header", showBackground = true)
@Composable
private fun HeaderPreview() {
    Header(text = "Im the Header")
}