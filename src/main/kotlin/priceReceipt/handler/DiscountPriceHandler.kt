package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLogger
import priceReceipt.TagOnlyPriceLogger

class DiscountPriceHandler(private val percent: Int, logger: PriceLogger = TagOnlyPriceLogger("Discount")) :
    PriceHandler(logger) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price((prevPrice.value * (100 - percent)) / 100)
    }
}