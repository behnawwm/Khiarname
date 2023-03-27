package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLogger
import priceReceipt.TagOnlyPriceLogger

class WaitingTimePriceHandler(
    private val minutes: Int,
    private val pricePerMinute: Long = 1000,
    logger: PriceLogger = TagOnlyPriceLogger("Waiting Time")
) :
    PriceHandler(logger) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (minutes * pricePerMinute))
    }
}