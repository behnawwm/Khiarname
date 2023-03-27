package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLogger

class TollPriceHandler(private val percent: Float = 0.1f) : PriceHandler(PriceLogger("Toll")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (percent * prevPrice.value).toLong())
    }
}