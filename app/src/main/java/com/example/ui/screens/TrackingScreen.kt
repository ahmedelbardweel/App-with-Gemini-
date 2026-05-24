package com.example.ui.screens

import android.text.format.DateFormat
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Order
import com.example.ui.Screen
import com.example.ui.ShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(
    viewModel: ShopViewModel,
    modifier: Modifier = Modifier
) {
    val orders by viewModel.orders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تتبع شحنات الملبوسات", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.HOME) },
                        modifier = Modifier.testTag("tracking_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "الرجوع للرئيسية"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = "لا يوجد شحنات",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "لا توجد أي شحنات نشطة حالياً!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "قم بشراء أزيائك المفضلة من المتجر وسيظهر تتبع الشحنة المباشر هنا فوراً.",
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                    Button(
                        onClick = { viewModel.navigateTo(Screen.HOME) },
                        modifier = Modifier.testTag("tracking_go_home")
                    ) {
                        Text("اذهب للمتجر وتسوق الآن", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
            ) {
                items(orders, key = { it.id }) { order ->
                    OrderTrackerCard(
                        order = order,
                        onSimulateNextState = { viewModel.simulateNextTrackingState(order) }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderTrackerCard(
    order: Order,
    onSimulateNextState: () -> Unit
) {
    val dateString = remember(order.orderDate) {
        val calendar = java.util.Calendar.getInstance().apply { timeInMillis = order.orderDate }
        DateFormat.format("yyyy-MM-dd hh:mm a", calendar).toString()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("order_card_${order.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card Title Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "طلب رقم: ${order.id}",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = dateString,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                // Delivery Status Tag
                val statusTextAndColor = when (order.status) {
                    "PROCESSING" -> Pair("قيد التجهيز الفاخر", MaterialTheme.colorScheme.primary)
                    "SHIPPED" -> Pair("تم الشحن وتسليمها لشركة التناقل", Color(0xFFF2994A))
                    "IN_TRANSIT" -> Pair("في طريق التوصيل لعنوانك", Color(0xFF2F80ED))
                    "DELIVERED" -> Pair("تم التوصيل بنجاح وسلامة", Color(0xFF27AE60))
                    else -> Pair("في خط الانتظار", Color.Gray)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusTextAndColor.second.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = statusTextAndColor.first,
                        color = statusTextAndColor.second,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }

            // Summary of item contents
            Text(
                text = "محتويات الشحنة: ${order.summary}",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )

            // Address & Courier summary
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("المستلم:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                    Text(order.recipientName, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("جوال للتواصل:", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                    Text(order.recipientPhone, fontSize = 11.sp)
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("عنوان التوصيل:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(order.shippingAddress, fontSize = 11.sp)
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("رقم تتبع الشحنة المباشر:", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        order.trackingNumber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text("السداد:", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${order.totalAmount} ر.س", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant)

            // Shipment Timeline (RTL orientation!)
            Text(
                text = "مراحل الشحنة ونقاط النقل:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Steps mapping based on current order status
            val stepStatusInt = when (order.status) {
                "PROCESSING" -> 1
                "SHIPPED" -> 2
                "IN_TRANSIT" -> 3
                "DELIVERED" -> 4
                else -> 0
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimelineStep(
                    stepNumber = 1,
                    title = "تجهيز الطلب",
                    subtitle = "تغليف فاخر",
                    currentActiveStep = stepStatusInt
                )

                TimelineConnector(isActive = stepStatusInt >= 2, modifier = Modifier.weight(1f).padding(top = 16.dp))

                TimelineStep(
                    stepNumber = 2,
                    title = "شحن الطرد",
                    subtitle = "لشركة النقل",
                    currentActiveStep = stepStatusInt
                )

                TimelineConnector(isActive = stepStatusInt >= 3, modifier = Modifier.weight(1f).padding(top = 16.dp))

                TimelineStep(
                    stepNumber = 3,
                    title = "قيد التوصيل",
                    subtitle = "مع المندوب",
                    currentActiveStep = stepStatusInt
                )

                TimelineConnector(isActive = stepStatusInt >= 4, modifier = Modifier.weight(1f).padding(top = 16.dp))

                TimelineStep(
                    stepNumber = 4,
                    title = "تم التوصيل",
                    subtitle = "بالهناء والرضا",
                    currentActiveStep = stepStatusInt
                )
            }

            // Interactive simulation button for visual testing of the tracker pipeline!
            OutlinedButton(
                onClick = { onSimulateNextState() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("simulate_tracking_${order.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "محاكاة حالة الشحن",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "محاكاة وتحديث حالة الشحنة تلقائياً",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineStep(
    stepNumber: Int,
    title: String,
    subtitle: String,
    currentActiveStep: Int
) {
    val isCompleted = currentActiveStep >= stepNumber
    val isCurrent = currentActiveStep == stepNumber

    val circleColor = when {
        isCurrent -> MaterialTheme.colorScheme.primary
        isCompleted -> Color(0xFF2E7D32) // Finished Green
        else -> MaterialTheme.colorScheme.outlineVariant
    }

    val contentColor = if (isCompleted || isCurrent) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.width(62.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(circleColor),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted && !isCurrent) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Text(
                    text = stepNumber.toString(),
                    color = contentColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = subtitle,
            fontSize = 8.sp,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
fun TimelineConnector(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    val barColor = if (isActive) Color(0xFF2E7D32) else MaterialTheme.colorScheme.outlineVariant

    Box(
        modifier = modifier
            .height(4.dp)
            .background(barColor)
    )
}
