package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLoggerImpl

class WaitingTimePriceHandler(private val minutes: Int, private val pricePerMinute: Long = 1000) :
    PriceHandler(PriceLoggerImpl("Waiting Time")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (minutes * pricePerMinute))
    }
}