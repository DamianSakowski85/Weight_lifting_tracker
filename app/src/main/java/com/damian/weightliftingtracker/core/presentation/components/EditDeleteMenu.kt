package com.damian.weightliftingtracker.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damian.weightliftingtracker.R

@Composable
fun EditDeleteMenu(
    title: String?,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if (title != null) {
            Header(
                text = title,
            )
        }

        Divider(startIndent = 0.dp)

        TextButton(
            onClick = onUpdateClick,
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(id = R.string.edit),
                tint = MaterialTheme.colors.onSurface,
            )
            Text(
                text = stringResource(id = R.string.edit),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        TextButton(
            onClick = onDeleteClick,
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete),
                tint = MaterialTheme.colors.onSurface
            )
            Text(
                text = stringResource(id = R.string.delete),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview("Edit-Delete Menu")
@Composable
private fun PlanMenuPreview() {
    Surface {
        EditDeleteMenu(
            title = "Full Body Workout",
            onDeleteClick = {},
            onUpdateClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}