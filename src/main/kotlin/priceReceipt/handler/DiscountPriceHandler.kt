package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLoggerImpl

class DiscountPriceHandler(private val percent: Int) : PriceHandler(PriceLoggerImpl("Discount")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price((prevPrice.value * (100 - percent)) / 100)
    }
}