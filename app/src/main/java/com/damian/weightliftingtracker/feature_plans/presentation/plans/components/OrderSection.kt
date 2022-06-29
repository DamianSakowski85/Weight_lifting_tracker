package com.damian.weightliftingtracker.feature_plans.presentation.plans.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    planOrder: PlanOrder,
    onOrderChange: (PlanOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_by_name),
                selected = planOrder is PlanOrder.Name,
                onSelect = { onOrderChange(PlanOrder.Name(planOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_by_date),
                selected = planOrder is PlanOrder.Date,
                onSelect = { onOrderChange(PlanOrder.Date(planOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.asc),
                selected = planOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(planOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.desc),
                selected = planOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(planOrder.copy(OrderType.Descending))
                }
            )
        }
        Divider(startIndent = 0.dp)
    }
}

@Preview("Order Section Preview")
@Composable
private fun OrderSectionPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            OrderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.ORDER_SECTION),
                planOrder = PlanOrder.Name(OrderType.Ascending),
                onOrderChange = {
                })
        }
    }
}