package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.Screen
import com.example.ui.ShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: ShopViewModel,
    modifier: Modifier = Modifier
) {
    val name by viewModel.checkoutName.collectAsState()
    val phone by viewModel.checkoutPhone.collectAsState()
    val address by viewModel.checkoutAddress.collectAsState()

    val cardNumber by viewModel.cardNumber.collectAsState()
    val cardExpiry by viewModel.cardExpiry.collectAsState()
    val cardCvv by viewModel.cardCvv.collectAsState()

    val paymentProcessing by viewModel.paymentProcessing.collectAsState()
    val paymentSuccess by viewModel.paymentSuccess.collectAsState()
    val placedOrder by viewModel.recentlyPlacedOrder.collectAsState()

    val isFormValid = name.isNotBlank() && phone.isNotBlank() && address.isNotBlank() &&
            cardNumber.replace(" ", "").length >= 15 && cardExpiry.length == 5 && cardCvv.length >= 3

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("الدفع الأمن المشفر", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("checkout_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "قفل"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // SSL Security Badge Alert Banner (Themed Professional Polish style!)
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE7F3FF),
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = "حماية فورية",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Column {
                            Text(
                                "نظام دفع آمن ومحمي بالكامل",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "جميع بيانات بطاقتك الائتمانية مشفرة محلياً لضمان السرية التامة.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Interactive Premium Credit Card Visualizer (Avoids generic AI defaults!)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .testTag("credit_card_visualizer"),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF1E1E24), // Luxury obsidian dark
                                        Color(0xFF2E4057)  // Deep slate blue
                                    )
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Chip and Card Vendor Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Golden Sim Chip Representation
                                Box(
                                    modifier = Modifier
                                        .size(width = 38.dp, height = 28.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFFD4AF37))
                                )
                                // Card Network Logos: Mada & Visa
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color.White.copy(alpha = 0.2f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            "مدى", // Saudi Mada Network
                                            color = Color.White,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 9.sp
                                        )
                                    }
                                    Text(
                                        "VISA",
                                        color = Color.White,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.sp,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            // Card Number formatted
                            Text(
                                text = cardNumber.ifEmpty { "•••• •••• •••• ••••" },
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Expiry & Name Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        "اسم حامل البطاقة",
                                        color = Color.White.copy(alpha = 0.5f),
                                        fontSize = 9.sp
                                    )
                                    Text(
                                        text = name.ifEmpty { "صاحب الحساب الموقر" },
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        textAlign = TextAlign.Start
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        "تنتهي في",
                                        color = Color.White.copy(alpha = 0.5f),
                                        fontSize = 9.sp
                                    )
                                    Text(
                                        text = cardExpiry.ifEmpty { "MM/YY" },
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }

                Text(
                    "المعلومات الشخصية وعنوان الشحن",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Recipient Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModel.setCheckoutName(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("checkout_name_field"),
                    label = { Text("المستلم بالكامل") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Recipient Phone
                OutlinedTextField(
                    value = phone,
                    onValueChange = { viewModel.setCheckoutPhone(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("checkout_phone_field"),
                    label = { Text("رقم جوال التواصل (مثال: +966...)") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Delivery Shipping Address
                OutlinedTextField(
                    value = address,
                    onValueChange = { viewModel.setCheckoutAddress(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("checkout_address_field"),
                    label = { Text("عنوان التوصيل التفصيلي (المدينة، الحي، الشارع)") },
                    leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
                    maxLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )

                Text(
                    "تفاصيل بطاقة السداد",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Credit Card inputs row
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { input ->
                        // Automatically format spacing while typing: e.g. "1234 5678"
                        val clean = input.replace(" ", "")
                        val formatted = StringBuilder()
                        for (i in clean.indices) {
                            formatted.append(clean[i])
                            if ((i + 1) % 4 == 0 && i != clean.lastIndex && formatted.length < 19) {
                                formatted.append(" ")
                            }
                        }
                        viewModel.setCardNumber(formatted.toString())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("checkout_card_field"),
                    label = { Text("رقم البطاقة الائتمانية (Visa / MasterCard)") },
                    leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Expiry Input
                    OutlinedTextField(
                        value = cardExpiry,
                        onValueChange = { input ->
                            // Automatically add slash "MM/YY"
                            var clean = input.filter { it.isDigit() || it == '/' }
                            if (clean.length == 2 && !clean.contains('/')) {
                                clean += "/"
                            }
                            viewModel.setCardExpiry(clean)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("checkout_expiry_field"),
                        label = { Text("تاريخ الانتهاء (MM/YY)") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // CVV Input
                    OutlinedTextField(
                        value = cardCvv,
                        onValueChange = { viewModel.setCardCvv(it) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("checkout_cvv_field"),
                        label = { Text("رمز الأمان (CVV)") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Submit Payment Button
                Button(
                    onClick = { viewModel.processSecurePayment() },
                    enabled = isFormValid && !paymentProcessing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("payment_submit_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFormValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.EnhancedEncryption, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "ادفع واشحن طلبي بأمان",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // Secure processing Dialog Loading state overlay
            if (paymentProcessing) {
                Dialog(
                    onDismissRequest = {},
                    properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        modifier = Modifier
                            .size(240.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 5.dp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(56.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "تشفير",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                "تشفير ثنائي آمن...",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "جارٍ إتمام العملية بأمان عبر البوابة المصرفية المشفرة.",
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }

            // Payment success dialog Celebration Box overlay
            if (paymentSuccess) {
                val recentOrder = placedOrder
                Dialog(
                    onDismissRequest = {},
                    properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Checkmark circle animation visual placeholder
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F5E9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "نجاح العملية",
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            Text(
                                "نشكر عائلتك! تم الدفع بنجاح 🎉",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1B5E20)
                            )

                            Text(
                                "عملية الدفع تمت بأمان وتم تسجيل طلب شحن ملابسك بنجاح.",
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.outline
                            )

                            if (recentOrder != null) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("رقم الطلب:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text(recentOrder.id, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("رقم التتبع البهائي الداخلي:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text(recentOrder.trackingNumber, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("المبلغ الإجمالي المدفوع:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text("${recentOrder.totalAmount} ر.س", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Black)
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = { viewModel.acknowledgePaymentSuccess() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("checkout_success_done"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("تتبع شحنة ملابسك الآن", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
