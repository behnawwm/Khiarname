package priceReceipt.handler

import priceReceipt.Price
import priceReceipt.PriceHandler
import priceReceipt.PriceLogger

class SurgePriceHandler(private val amount: Float) : PriceHandler(PriceLogger("Surge")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price((prevPrice.value * amount).toLong())
    }
}