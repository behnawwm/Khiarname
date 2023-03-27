package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLogger
import priceReceipt.TagOnlyPriceLogger

class WheelchairPriceHandler(
    private val reductionAmount: Long = 2000,
    logger: PriceLogger = TagOnlyPriceLogger("Wheelchair")
) : PriceHandler(logger) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value - reductionAmount)
    }
}