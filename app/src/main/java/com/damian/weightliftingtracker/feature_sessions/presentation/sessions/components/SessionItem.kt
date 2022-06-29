package com.damian.weightliftingtracker.feature_sessions.presentation.sessions.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun SessionItem(
    session: Session,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit
) {
    Card(
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = session.sessionName }
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = session.sessionName,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically),
                        text = session.sessionDescription,
                        style = MaterialTheme.typography.body2,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                val divider = " : "

                val lastSessionTextText = buildAnnotatedString {
                    append(stringResource(id = R.string.last_session))
                    append(divider)

                    val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
                        background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
                    )

                    withStyle(tagStyle) {
                        append(text = session.lastSessionDate)
                    }
                }

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically),
                        text = lastSessionTextText,
                        style = MaterialTheme.typography.body2,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                TextButton(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .testTag(session.sessionName),
                    onClick = onMenuClick
                ) {
                    Text(
                        text = stringResource(id = R.string.menu_icon),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}

@Preview("Session Item Preview")
@Composable
private fun SessionItemPreview() {
    val session = Session(1, 1, "Push", "Monday")
    session.lastSessionDate = "06.06.2022"
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            SessionItem(
                session = session,
                onMenuClick = {}
            )
        }
    }
}