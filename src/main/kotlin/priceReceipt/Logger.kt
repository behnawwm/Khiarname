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