package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLoggerImpl

class TollPriceHandler(private val percent: Float = 0.1f) : PriceHandler(PriceLoggerImpl("Toll")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (percent * prevPrice.value).toLong())
    }
}