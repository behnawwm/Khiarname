package priceReceipt

interface HeaderLogger {
    fun log(basePrice: Price)
    fun logSeparator()
}

interface FooterLogger {
    fun log(finalPrice: Price)
    fun logSeparator()
}

class GeneralHeaderLogger : HeaderLogger {
    override fun log(basePrice: Price) {
        println("Base price: ${basePrice.value}")
    }

    override fun logSeparator() {
        println("--------------------------")
    }

}

class GeneralFooterLogger : FooterLogger {
    override fun log(finalPrice: Price) {
        println("Base price: ${finalPrice.value}")
    }

    override fun logSeparator() {
        println("--------------------------")
    }

}



