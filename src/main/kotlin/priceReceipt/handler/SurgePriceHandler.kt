package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.TagOnlyPriceLogger

class SurgePriceHandler(
    private val amount: Float,
    logger: TagOnlyPriceLogger = TagOnlyPriceLogger("Surge")
) :
    PriceHandler(logger) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price((prevPrice.value * amount).toLong())
    }
}