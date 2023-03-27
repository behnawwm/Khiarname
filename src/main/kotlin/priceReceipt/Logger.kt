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

class TagOnlyPriceLogger(private val tag: String) : PriceLogger {
    override fun log(price: Price) {
        val formattedPrice = price.formatPrice()
        println(tag.padEnd(20) + formattedPrice)    //todo replace 20 with maxTagLength
    }
}

class GeneralHeaderLogger : HeaderLogger {
    override fun log(price: Price) {
        val tag = "Base price:"
        val formattedPrice = price.formatPrice()
        println(tag.padEnd(20) + formattedPrice)      //todo replace 20 with maxTagLength
    }

    override fun logSeparator() {
        println("--------------------------")
    }

}

class GeneralFooterLogger : FooterLogger {
    override fun log(price: Price) {
        val tag = "Final price:"
        val formattedPrice = price.formatPrice()
        println(tag.padEnd(20) + formattedPrice)
    }

    override fun logSeparator() {
        println("--------------------------")
    }

}