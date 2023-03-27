package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLogger
import priceReceipt.logger.TagOnlyPriceLogger

class SurgePriceHandler(
    private val amount: Float,
    logger: PriceLogger = TagOnlyPriceLogger("Surge")
) :
    PriceHandler(logger) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price((prevPrice.value * amount).toLong())
    }
}