package priceReceipt

class ReceiptHandler(
    private val headerLogger: HeaderLogger = GeneralHeaderLogger(),
    private val footerLogger: FooterLogger = GeneralFooterLogger()
) {
    fun handleAllAndLog(handlerList: List<PriceHandler>, basePrice: Price): Price {
        logHeader(basePrice)

        val first = handlerList.firstOrNull() ?: return basePrice.also {
            logHeader(it)
        }

        for (i in 0 until handlerList.size - 1) {
            val currentItem = handlerList[i]
            val nextItem = handlerList[i + 1]
            currentItem.setNext(nextItem)
        }

        return first.handle(basePrice).also { finalPrice ->
            logFooter(finalPrice)
        }
    }

    private fun logHeader(basePrice: Price) {
        headerLogger.log(basePrice)
        headerLogger.logSeparator()
    }

    private fun logFooter(finalPrice: Price) {
        footerLogger.logSeparator()
        footerLogger.log(finalPrice)
    }
}