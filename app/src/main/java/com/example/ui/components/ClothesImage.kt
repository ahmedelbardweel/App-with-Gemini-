package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp

@Composable
fun ClothesImage(
    productId: Int,
    category: String,
    primaryColorHex: String,
    modifier: Modifier = Modifier
) {
    val themeColor = runCatching { Color(android.graphics.Color.parseColor(primaryColorHex)) }
        .getOrDefault(Color(0xFFE0E0E0))

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFF9F9FB)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            when (productId) {
                1 -> { // بشت حساوي - Royal Bisht
                    // Elegant layered drapes
                    val outerPath = Path().apply {
                        moveTo(width * 0.15f, height * 0.9f)
                        lineTo(width * 0.15f, height * 0.3f)
                        quadraticTo(width * 0.2f, height * 0.15f, width * 0.5f, height * 0.2f)
                        quadraticTo(width * 0.8f, height * 0.15f, width * 0.85f, height * 0.3f)
                        lineTo(width * 0.85f, height * 0.9f)
                        close()
                    }
                    drawPath(outerPath, color = themeColor)

                    // Deluxe gold zari embroidery down the center
                    val goldColor1 = Color(0xFFD4AF37)
                    val goldColor2 = Color(0xFFFFDF00)

                    // Left drape line
                    drawLine(
                        color = goldColor1,
                        start = Offset(width * 0.5f, height * 0.2f),
                        end = Offset(width * 0.35f, height * 0.9f),
                        strokeWidth = 10f,
                        cap = StrokeCap.Round
                    )

                    // Right drape line
                    drawLine(
                        color = goldColor1,
                        start = Offset(width * 0.5f, height * 0.2f),
                        end = Offset(width * 0.65f, height * 0.9f),
                        strokeWidth = 10f,
                        cap = StrokeCap.Round
                    )

                    // Deluxe Golden central embroidery details
                    drawCircle(
                        brush = Brush.linearGradient(listOf(goldColor2, goldColor1)),
                        radius = 18f,
                        center = Offset(width * 0.5f, height * 0.38f)
                    )
                    drawCircle(
                        brush = Brush.linearGradient(listOf(goldColor2, goldColor1)),
                        radius = 14f,
                        center = Offset(width * 0.5f, height * 0.5f)
                    )
                }
                2 -> { // ثوب سعودي - Saudi Thobe
                    // Pristine white long robe with neck collar
                    val thobePath = Path().apply {
                        moveTo(width * 0.35f, height * 0.15f)
                        lineTo(width * 0.65f, height * 0.15f)
                        lineTo(width * 0.72f, height * 0.28f)
                        lineTo(width * 0.76f, height * 0.9f)
                        lineTo(width * 0.24f, height * 0.9f)
                        lineTo(width * 0.28f, height * 0.28f)
                        close()
                    }
                    drawPath(thobePath, color = themeColor)

                    // Collar detail
                    val collarPath = Path().apply {
                        moveTo(width * 0.42f, height * 0.15f)
                        quadraticTo(width * 0.5f, height * 0.23f, width * 0.58f, height * 0.15f)
                        lineTo(width * 0.58f, height * 0.23f)
                        quadraticTo(width * 0.5f, height * 0.28f, width * 0.42f, height * 0.23f)
                        close()
                    }
                    drawPath(collarPath, color = Color(0xFFECEFF1))

                    // Buttons line
                    drawLine(
                        color = Color(0xFFB0BEC5),
                        start = Offset(width * 0.5f, height * 0.23f),
                        end = Offset(width * 0.5f, height * 0.6f),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 15f), 0f)
                    )
                }
                3 -> { // عباءة نسائية - Abaya
                    // Flowy dark abaya silhouette
                    val abayaPath = Path().apply {
                        moveTo(width * 0.3f, height * 0.15f)
                        lineTo(width * 0.7f, height * 0.15f)
                        lineTo(width * 0.82f, height * 0.9f)
                        lineTo(width * 0.18f, height * 0.9f)
                        close()
                    }
                    drawPath(abayaPath, color = themeColor)

                    // Elegant lace or sleeve decorations
                    val goldBorders = Color(0xFFC5A059)
                    // Left sleeve panel
                    drawLine(
                        color = goldBorders,
                        start = Offset(width * 0.32f, height * 0.3f),
                        end = Offset(width * 0.2f, height * 0.8f),
                        strokeWidth = 6f
                    )
                    // Right sleeve panel
                    drawLine(
                        color = goldBorders,
                        start = Offset(width * 0.68f, height * 0.3f),
                        end = Offset(width * 0.8f, height * 0.8f),
                        strokeWidth = 6f
                    )

                    // Center embroidery trim
                    drawLine(
                        color = goldBorders,
                        start = Offset(width * 0.5f, height * 0.15f),
                        end = Offset(width * 0.5f, height * 0.9f),
                        strokeWidth = 4f
                    )
                }
                4 -> { // فستان شيفون - Evening Dress
                    // Flaired ballgown dress shape
                    val dressPath = Path().apply {
                        moveTo(width * 0.4f, height * 0.15f)
                        lineTo(width * 0.6f, height * 0.15f)
                        quadraticTo(width * 0.55f, height * 0.45f, width * 0.75f, height * 0.55f)
                        lineTo(width * 0.85f, height * 0.9f)
                        lineTo(width * 0.15f, height * 0.9f)
                        lineTo(width * 0.25f, height * 0.55f)
                        quadraticTo(width * 0.45f, height * 0.45f, width * 0.4f, height * 0.15f)
                        close()
                    }
                    drawPath(dressPath, color = themeColor)

                    // Sparkling details (dots) for chiffon sparkles
                    drawCircle(Color.White.copy(alpha = 0.8f), 8f, Offset(width * 0.45f, height * 0.6f))
                    drawCircle(Color.White.copy(alpha = 0.8f), 6f, Offset(width * 0.55f, height * 0.75f))
                    drawCircle(Color.White.copy(alpha = 0.8f), 10f, Offset(width * 0.35f, height * 0.8f))
                    drawCircle(Color.White.copy(alpha = 0.8f), 7f, Offset(width * 0.65f, height * 0.65f))
                }
                5 -> { // طقم ولادي قطني - Kids Boy Set
                    // Cute t-shirt and small shorts
                    // Shirt
                    val shirtPath = Path().apply {
                        moveTo(width * 0.3f, height * 0.2f)
                        lineTo(width * 0.7f, height * 0.2f)
                        lineTo(width * 0.8f, height * 0.33f)
                        lineTo(width * 0.7f, height * 0.38f)
                        lineTo(width * 0.65f, height * 0.33f)
                        lineTo(width * 0.65f, height * 0.55f)
                        lineTo(width * 0.35f, height * 0.55f)
                        lineTo(width * 0.35f, height * 0.33f)
                        lineTo(width * 0.3f, height * 0.38f)
                        close()
                    }
                    drawPath(shirtPath, color = themeColor)

                    // Shorts
                    val shortsColor = Color(0xFF1E3A8A) // Stylish Denim Blue shorts
                    drawRect(
                        color = shortsColor,
                        topLeft = Offset(width * 0.36f, height * 0.58f),
                        size = Size(width * 0.28f, height * 0.28f)
                    )
                    // Gap in center bottom for legs
                    drawRect(
                        color = Color.White,
                        topLeft = Offset(width * 0.47f, height * 0.75f),
                        size = Size(width * 0.06f, height * 0.12f)
                    )
                }
                6 -> { // فستان بناتي ربيعي - Kids Dress
                    // Adorable wide a-line flared floral dress
                    val girlDressPath = Path().apply {
                        moveTo(width * 0.42f, height * 0.22f)
                        lineTo(width * 0.58f, height * 0.22f)
                        lineTo(width * 0.78f, height * 0.85f)
                        lineTo(width * 0.22f, height * 0.85f)
                        close()
                    }
                    drawPath(girlDressPath, color = themeColor)

                    // Tiny stylized floral dots (circles)
                    val flowerColor = Color(0xFFFFCDD2) // Soft pink floral
                    drawCircle(flowerColor, 12f, Offset(width * 0.4f, height * 0.55f))
                    drawCircle(flowerColor, 12f, Offset(width * 0.6f, height * 0.66f))
                    drawCircle(flowerColor, 12f, Offset(width * 0.48f, height * 0.78f))
                    drawCircle(Color(0xFFFFF9C4), 5f, Offset(width * 0.4f, height * 0.55f)) // flower center
                    drawCircle(Color(0xFFFFF9C4), 5f, Offset(width * 0.6f, height * 0.66f))
                    drawCircle(Color(0xFFFFF9C4), 5f, Offset(width * 0.48f, height * 0.78f))
                }
                7 -> { // هودي رياضي - Hoodie
                    // Cozy hoodie with hood and kangaroo pocket
                    val hoodiePath = Path().apply {
                        moveTo(width * 0.25f, height * 0.28f)
                        lineTo(width * 0.75f, height * 0.28f)
                        lineTo(width * 0.85f, height * 0.42f)
                        lineTo(width * 0.74f, height * 0.48f)
                        lineTo(width * 0.7f, height * 0.42f)
                        lineTo(width * 0.7f, height * 0.85f)
                        lineTo(width * 0.30f, height * 0.85f)
                        lineTo(width * 0.30f, height * 0.42f)
                        lineTo(width * 0.26f, height * 0.48f)
                        close()
                    }
                    drawPath(hoodiePath, color = themeColor)

                    // Hood
                    val hoodPath = Path().apply {
                        moveTo(width * 0.36f, height * 0.28f)
                        quadraticTo(width * 0.5f, height * 0.08f, width * 0.64f, height * 0.28f)
                        close()
                    }
                    drawPath(hoodPath, color = themeColor.copy(alpha = 0.85f))

                    // Kangaroo Pocket
                    val pocketPath = Path().apply {
                        moveTo(width * 0.4f, height * 0.66f)
                        lineTo(width * 0.6f, height * 0.66f)
                        lineTo(width * 0.56f, height * 0.78f)
                        lineTo(width * 0.44f, height * 0.78f)
                        close()
                    }
                    drawPath(pocketPath, color = Color.White.copy(alpha = 0.2f))
                }
                8 -> { // جاكيت طويل - Winter Coat
                    // Heavy long coat with fur-lined collar
                    val coatPath = Path().apply {
                        moveTo(width * 0.32f, height * 0.2f)
                        lineTo(width * 0.68f, height * 0.2f)
                        lineTo(width * 0.78f, height * 0.35f)
                        lineTo(width * 0.72f, height * 0.9f)
                        lineTo(width * 0.28f, height * 0.9f)
                        lineTo(width * 0.22f, height * 0.35f)
                        close()
                    }
                    drawPath(coatPath, color = themeColor)

                    // Fur Collar (thick V-shape)
                    val collarPath = Path().apply {
                        moveTo(width * 0.3f, height * 0.2f)
                        lineTo(width * 0.5f, height * 0.52f)
                        lineTo(width * 0.7f, height * 0.2f)
                        lineTo(width * 0.55f, height * 0.2f)
                        lineTo(width * 0.5f, height * 0.42f)
                        lineTo(width * 0.45f, height * 0.2f)
                        close()
                    }
                    drawPath(collarPath, color = Color(0xFFCFD8DC))

                    // Center line zip
                    drawLine(
                        color = Color(0xFF37474F),
                        start = Offset(width * 0.5f, height * 0.52f),
                        end = Offset(width * 0.5f, height * 0.9f),
                        strokeWidth = 6f
                    )
                }
                else -> {
                    // Fallback shopping bag illustration
                    val bagPath = Path().apply {
                        moveTo(width * 0.3f, height * 0.25f)
                        lineTo(width * 0.7f, height * 0.25f)
                        lineTo(width * 0.75f, height * 0.85f)
                        lineTo(width * 0.25f, height * 0.85f)
                        close()
                    }
                    drawPath(bagPath, color = themeColor)

                    // Bag handle
                    drawArc(
                        color = Color(0xFF757575),
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(width * 0.4f, height * 0.13f),
                        size = Size(width * 0.2f, height * 0.25f),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8f)
                    )
                }
            }
        }
    }
}
