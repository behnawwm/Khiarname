package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLogger

class WheelchairPriceHandler(private val reductionAmount: Long = 2000) : PriceHandler(PriceLogger("Wheelchair")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value - reductionAmount)
    }
}