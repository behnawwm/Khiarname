package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLoggerImpl

class WheelchairPriceHandler(private val reductionAmount: Long = 2000) : PriceHandler(PriceLoggerImpl("Wheelchair")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value - reductionAmount)
    }
}