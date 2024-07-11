package presentation.screen

import di.LocalAppNavigator
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_awards
import bubble.composeapp.generated.resources.ic_bubble_paywall
import bubble.composeapp.generated.resources.ic_close
import bubble.composeapp.generated.resources.ic_messages
import bubble.composeapp.generated.resources.ic_objective
import bubble.composeapp.generated.resources.ic_statistics
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import presentation.model.UIAnnualPaymentPlan
import presentation.model.UIBenefit
import presentation.model.UIFreeBenefits
import presentation.model.UIFreeChallenges
import presentation.model.UIFreeChat
import presentation.model.UIFreeRewards
import presentation.model.UIFreeStatistics
import presentation.model.UIMonthlyPaymentPlan
import presentation.model.UIPaymentPlan
import presentation.model.UIPremiumBenefits
import presentation.model.UIPremiumChallenges
import presentation.model.UIPremiumChat
import presentation.model.UIPremiumRewards
import presentation.model.UIPremiumStatistics

@OptIn(ExperimentalMaterialApi::class)
class PaywallScreen : Screen {
    @Composable
    override fun Content() {
        val appNavigator = LocalAppNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            appNavigator.pop()
                        }) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_close),
                                contentDescription = null,
                            )
                        }
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                )
            }
        ) {
            var isPremiumChecked by remember { mutableStateOf(true) }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(256.dp)
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Image(
                            painterResource(Res.drawable.ic_bubble_paywall),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(32.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 32.dp,
                                        topEnd = 32.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                                .background(MaterialTheme.colors.background)
                        ) { }
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            "Únete a Bubble Premium",
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "¡Aprovecha la promoción exclusiva para nuevos miembros!",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        val animatedSpacerWidthFraction by animateFloatAsState(if (isPremiumChecked) 0f else 0.5f)
                        val primary = MaterialTheme.colors.primary
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .height(56.dp)
                                .clip(CircleShape)
                                .background(color = MaterialTheme.colors.secondary)
                                .clickable {
                                    isPremiumChecked = !isPremiumChecked
                                }
                        ) {
                            val localDensity = LocalDensity.current
                            Canvas(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(40.dp))
                            ) {
                                drawRoundRect(
                                    color = primary,
                                    topLeft = Offset.Zero.copy(x = size.width * animatedSpacerWidthFraction),
                                    size = size.copy(size.width * 0.5f),
                                    cornerRadius = with(localDensity) { CornerRadius(40.dp.toPx()) }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .fillMaxWidth(0.5f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(40.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    "Premium",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPremiumChecked) MaterialTheme.colors.onPrimary
                                    else MaterialTheme.colors.onSecondary
                                )
                                val isOfferActive = false
                                if (isOfferActive) {
                                    Text(
                                        "50% OFF",
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(color = MaterialTheme.colors.primaryVariant)
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        color = MaterialTheme.colors.onPrimary
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .fillMaxWidth(0.5f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(40.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    "Plan actual",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPremiumChecked) MaterialTheme.colors.onSecondary
                                    else MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val premiumXOffset by animateDpAsState(if (isPremiumChecked) 0.dp else maxWidth)
                        val freeXOffset by animateDpAsState(if (isPremiumChecked) -maxWidth else 0.dp)
                        PremiumBenefits(
                            modifier = Modifier
                                .offset(
                                    x = premiumXOffset
                                )
                        )
                        FreeBenefits(
                            modifier = Modifier
                                .offset(
                                    x = freeXOffset
                                )
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    @Composable
    private fun FreeBenefits(
        modifier: Modifier = Modifier
    ) {
        val appNavigator = LocalAppNavigator.currentOrThrow
        val uriHandler = LocalUriHandler.current
        val freeBenefits = listOf(
            UIFreeChat,
            UIFreeChallenges,
            UIFreeStatistics,
            UIFreeRewards,
        )
        BenefitsCard(
            freeBenefits,
            modifier = modifier,
            callToAction = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Button(
                        onClick = {
                            appNavigator.pop()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .height(56.dp),
                        shape = CircleShape
                    ) {
                        Text(
                            "Continuar con el plan gratuito",
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            uriHandler.openUri("https://tally.so/r/3yD26d")
                        },
                        modifier = Modifier.fillMaxWidth()
                            .height(56.dp),
                        shape = CircleShape
                    ) {
                        Text(
                            "Suscribirme a Bubble Premium",
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Puedes cancelar en cualquier momento.",
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        """
                            La suscripción se renueva de forma automática 24 horas antes de que el periodo de suscripción termine.
                        """.trimIndent(),
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        )
    }

    @Composable
    private fun PremiumBenefits(
        modifier: Modifier = Modifier
    ) {
        val appNavigator = LocalAppNavigator.currentOrThrow
        val uriHandler = LocalUriHandler.current
        val premiumBenefits = listOf(
            UIPremiumChat,
            UIPremiumChallenges,
            UIPremiumStatistics,
            UIPremiumRewards,
        )
        BenefitsCard(
            premiumBenefits,
            modifier = modifier,
            paymentPlan = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.secondary,
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 32.dp)
                    ) {
                        var isAnnualSelected by remember { mutableStateOf(true) }
                        PlanSelection(
                            isSelected = isAnnualSelected,
                            onPlanSelected = {
                                isAnnualSelected = true
                            },
                            paymentPlan = UIAnnualPaymentPlan
                        )
                        PlanSelection(
                            isSelected = !isAnnualSelected,
                            onPlanSelected = {
                                isAnnualSelected = false
                            },
                            paymentPlan = UIMonthlyPaymentPlan
                        )
                    }
                }
            },
            callToAction = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        "Puedes cancelar en cualquier momento.",
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            uriHandler.openUri("https://tally.so/r/3yD26d")
                        },
                        modifier = Modifier.fillMaxWidth()
                            .height(56.dp),
                        shape = CircleShape
                    ) {
                        Text(
                            "Suscribirme a Bubble Premium",
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            appNavigator.pop()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .height(56.dp),
                        shape = CircleShape
                    ) {
                        Text(
                            "Continuar con el plan gratuito",
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        """
                            La suscripción se renueva de forma automática 24 horas antes de que el periodo de suscripción termine.
                        """.trimIndent(),
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        )
    }

    @Composable
    private fun PlanSelection(
        isSelected: Boolean,
        onPlanSelected: () -> Unit = {},
        paymentPlan: UIPaymentPlan
    ) {
        ListItem(
            modifier = Modifier.clickable {
                onPlanSelected()
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .let {
                            if (isSelected) {
                                it.background(
                                    MaterialTheme.colors.primary
                                )
                            } else it
                        }
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colors.primary,
                            shape = CircleShape,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    if (isSelected) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(24.dp)
                        )
                    }
                }
            },
            text = {
                Text(
                    paymentPlan.title,
                    color = if (isSelected) {
                        MaterialTheme.colors.primaryVariant
                    } else {
                        MaterialTheme.colors.onSurface
                    },
                    fontWeight = FontWeight.Bold
                )
            },
            secondaryText = {
                Column {
                    Text(
                        paymentPlan.price,
                        color = MaterialTheme.colors.primary
                    )
                    Text(
                        paymentPlan.description,
                        color = MaterialTheme.colors.primary
                    )
                }
            },
            trailing = {
                if (paymentPlan is UIAnnualPaymentPlan) {
                    Text(
                        "Mejor valor",
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary)
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.caption,
                        fontSize = 10.sp
                    )
                }
            },
            singleLineSecondaryText = true
        )
    }

    @Composable
    private fun BenefitsCard(
        benefits: List<UIBenefit>,
        modifier: Modifier = Modifier,
        paymentPlan: @Composable () -> Unit = {},
        callToAction: @Composable () -> Unit = {},
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    benefits.forEach { benefit ->
                        BenefitItem(benefit)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    paymentPlan()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            callToAction()
            Spacer(modifier = Modifier.height(56.dp))
        }
    }

    @Composable
    private fun BenefitItem(
        benefit: UIBenefit
    ) {
        ListItem(
            icon = {
                Image(
                    painterResource(
                        when (benefit) {
                            UIFreeChallenges,
                            UIPremiumChallenges -> Res.drawable.ic_objective

                            UIFreeChat,
                            UIPremiumChat -> Res.drawable.ic_messages

                            UIFreeRewards,
                            UIPremiumRewards -> Res.drawable.ic_awards

                            UIFreeStatistics,
                            UIPremiumStatistics -> Res.drawable.ic_statistics
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (benefit is UIPremiumBenefits) MaterialTheme.colors.primary
                            else MaterialTheme.colors.secondary
                        )
                        .padding(8.dp)
                        .alpha(
                            if (benefit is UIPremiumBenefits) 1f
                            else 0.5f
                        ),
                )
            },
            text = {
                Text(benefit.title)
            },
            secondaryText = {
                Text(benefit.description)
            }
        )
    }
}