package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLogger
import priceReceipt.TagOnlyPriceLogger

class TollPriceHandler(
    private val percent: Float = 0.1f,
    logger: PriceLogger = TagOnlyPriceLogger("Toll")
) :
    PriceHandler(logger) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (percent * prevPrice.value).toLong())
    }
}