package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLoggerImpl

class SurgePriceHandler(private val amount: Float) : PriceHandler(PriceLoggerImpl("Surge")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price((prevPrice.value * amount).toLong())
    }
}