package priceReceipt

interface PriceLogger {
    fun log(price: Price)
}

interface HeaderLogger : PriceLogger {
    fun logSeparator()
}

interface FooterLogger : PriceLogger {
    fun logSeparator()
}

class TagOnlyPriceLogger(
    private val tag: String,
    private val tagEndPadLength: Int = 20,  //todo replace with max length between all tags
) : PriceLogger {
    override fun log(price: Price) {
        val formattedPrice = price.formatPrice()
        println(tag.padEnd(tagEndPadLength) + formattedPrice)
    }
}

class GeneralHeaderLogger(
    private val tagEndPadLength: Int = 20,  //todo replace with max length between all tags
    private val separatorLength: Int = 26,  //todo replace with max length between all prices
    private val tag: String = "Base price:",
) : HeaderLogger {

    override fun log(price: Price) {
        val formattedPrice = price.formatPrice()
        println(tag.padEnd(tagEndPadLength) + formattedPrice)
    }

    override fun logSeparator() {
        println("-".repeat(separatorLength))
    }

}

class GeneralFooterLogger(
    private val tagEndPadLength: Int = 20,  //todo replace with max length between all tags
    private val separatorLength: Int = 26,  //todo replace with max length between all prices
    private val tag: String = "Final price:"
) : FooterLogger {

    override fun log(price: Price) {
        val formattedPrice = price.formatPrice()
        println(tag.padEnd(tagEndPadLength) + formattedPrice)
    }

    override fun logSeparator() {
        println("-".repeat(separatorLength))
    }

}