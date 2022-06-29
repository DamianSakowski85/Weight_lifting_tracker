package com.damian.weightliftingtracker.feature_plans.presentation.plans.components

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
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun PlanItem(
    plan: Plan,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = plan.planName }
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = plan.planName,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(8.dp))

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically),
                        text = plan.planDescription,
                        style = MaterialTheme.typography.body2,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically),
                        text = stringResource(id = R.string.created),
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                val createdText = buildAnnotatedString {
                    val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
                        background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
                    )
                    withStyle(tagStyle) {
                        append(plan.getTimestampFormatted)
                    }
                }

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically),
                        text = createdText,
                        //style = MaterialTheme.typography.caption,
                        maxLines = 1,
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
                        .testTag(plan.planName),
                    onClick = onMenuClick,
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

@Preview("Plan Item")
@Composable
private fun PlanItemPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            PlanItem(
                plan = Plan(
                    0,
                    "Fbw",
                    planDescription = "Full Body Workout",
                    timestamp = 0
                ),
                onMenuClick = {  },
            )
        }
    }
}