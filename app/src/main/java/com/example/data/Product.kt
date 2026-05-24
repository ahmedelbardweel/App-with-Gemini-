package com.example.data

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val description: String,
    val rating: Float,
    val sizes: List<String>,
    val colors: List<String>, // HEX colors like "#2C3E50" etc.
    val isAvailable: Boolean = true
) {
    companion object {
        val SAMPLE_PRODUCTS = listOf(
            Product(
                id = 1,
                name = "بشت حساوي ملكي فاخر",
                category = "رجال",
                price = 850.00,
                description = "بشت حساوي فاخر مصنوع من أجود خيوط الصوف الطبيعي مع زري مذهب يدوي غاية في الدقة والأناقة. خيار مثالي للمناسبات والأعراس الرسمية ليعكس هيبتك وأصالتك.",
                rating = 4.9f,
                sizes = listOf("أقصر", "متوسط", "طويل"),
                colors = listOf("#0F0F0F", "#5D4037", "#F5F5DC") // Black, Brown, Beige
            ),
            Product(
                id = 2,
                name = "ثوب سعودي كلاسيك مطرز",
                category = "رجال",
                price = 180.00,
                description = "ثوب سعودي بتصميم رسمي كلاسيكي يجمع بين الراحة والأناقة. قماش ياباني مقاوم للتجاعيد وياقة مريحة مع تطريز ناعم حول الأزرار الكلاسيكية.",
                rating = 4.8f,
                sizes = listOf("52", "54", "56", "58", "60"),
                colors = listOf("#FFFFFF", "#ECEFF1", "#ECE5C8") // White, Light Gray, Cream
            ),
            Product(
                id = 3,
                name = "عباءة مخملية مطرزة بالذهب",
                category = "نساء",
                price = 390.00,
                description = "عباءة نسائية أنيقة من أجود أنواع المخمل الفاخر، مزينة بتطريزات ذهبية ناعمة على الأكمام والأطراف. صممت خصيصاً للمناسبات المسائية الفخمة لتمنحكِ طلّة ساحرة.",
                rating = 4.7f,
                sizes = listOf("52", "54", "56", "58"),
                colors = listOf("#1A1A1A", "#1B365D", "#3E1B5D") // Black, Navy, Purple
            ),
            Product(
                id = 4,
                name = "فستان شيفون نسائي مطرز",
                category = "نساء",
                price = 450.00,
                description = "فستان نسائي كلاسيكي رقيق مصنوع من طبقات متعددة من الشيفون عالي الجودة ومطرز بالخرز البراق بتناسق تام. مثالي للسهرات السعيدة والمناسبات العائلية الدينية والرسمية.",
                rating = 4.6f,
                sizes = listOf("S", "M", "L", "XL"),
                colors = listOf("#E91E63", "#9C27B0", "#00BCD4") // Pink, Purple, Teal
            ),
            Product(
                id = 5,
                name = "طقم ولادي قطني ناعم",
                category = "أطفال",
                price = 120.00,
                description = "طقم قطني متكامل للأولاد يتضمن تيشيرت بنقشة كرتونية ناعمة وشورت قصير من الجينز الخفيف المريح والمثالي للحركة المستمرة واللعب اليومي الطويل.",
                rating = 4.5f,
                sizes = listOf("سنتين", "4 سنوات", "6 سنوات", "8 سنوات"),
                colors = listOf("#2196F3", "#4CAF50", "#FFC107") // Blue, Green, Yellow
            ),
            Product(
                id = 6,
                name = "فستان بناتي ربيعي بنقش الورد",
                category = "أطفال",
                price = 140.00,
                description = "فستان بناتي ساحر مزين بنقوش وباقات من الزهور الطبيعية الجميلة، مصنوع بالكامل من القطن العضوي الناعم لراحة تدوم طيلة اليوم في كل الحفلات والنزهات الخارجيه الممتعة.",
                rating = 4.8f,
                sizes = listOf("سنتين", "4 سنوات", "6 سنوات", "8 سنوات"),
                colors = listOf("#FFF5F5", "#FFE0E6", "#E1F5FE") // Soft white, Soft pink, Soft blue
            ),
            Product(
                id = 7,
                name = "هودي رياضي دافئ وعصري",
                category = "رجال",
                price = 165.00,
                description = "جاكيت هودي شبابي للمشاوير والرياضة، بياقة مزودة بقبعة وجيب أمامي واسع (جيب الكنغر). صوف ناعم يمنحك الدفء في الشتاء وراحة مطلقة في الأيام الباردة.",
                rating = 4.4f,
                sizes = listOf("S", "M", "L", "XL", "XXL"),
                colors = listOf("#37474F", "#004D40", "#BF360C") // Slate, Dark green, Dark rust
            ),
            Product(
                id = 8,
                name = "جاكيت شتوي نسائي طويل",
                category = "نساء",
                price = 520.00,
                description = "جاكيت شتوي نسائي طويل ومبطن بمادة عازلة متطورة لمقاومة الرياح الشتوية القارسة. غطاء للرأس من الفرو الناعم الذي يضفي مظهراً يعج بالفخامة والدفء الملوكي التام.",
                rating = 4.9f,
                sizes = listOf("S", "M", "L", "XL"),
                colors = listOf("#2E2E2E", "#FFFDF9", "#8D6E63") // Off-black, Beige, Light brown
            )
        )
    }
}
