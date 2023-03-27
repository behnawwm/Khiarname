package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLoggerImpl

class EtaPriceHandler(private val minutes: Int, private val pricePerMinute: Long = 1000) :
    PriceHandler(PriceLoggerImpl("Eta")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (minutes * pricePerMinute))
    }
}