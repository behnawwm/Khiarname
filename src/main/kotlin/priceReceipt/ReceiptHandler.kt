package priceReceipt

class ReceiptHandler(
    private val handlerList: List<PriceHandler>,
    private val basePrice: Price,
    private val headerLogger: HeaderLogger = GeneralHeaderLogger(),
    private val footerLogger: FooterLogger = GeneralFooterLogger()
) {
    fun handleAllAndLog(): Price {
        headerLogger.log(basePrice)
        headerLogger.logSeparator()

        val first = handlerList.firstOrNull() ?: return basePrice //todo log

        for (i in 0 until handlerList.size - 1) {
            val currentItem = handlerList[i]
            val nextItem = handlerList[i + 1]
            currentItem.setNext(nextItem)
        }

        return first.handle(basePrice).also { finalPrice ->
            footerLogger.logSeparator()
            footerLogger.log(finalPrice)
        }
    }
}