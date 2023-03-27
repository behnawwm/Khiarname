package priceReceipt.logger

import priceReceipt.Price
import priceReceipt.PriceLogger
import priceReceipt.formatToThousands

class TagOnlyPriceLogger(
    private val tag: String,
    private val tagEndPadLength: Int = 20,  //todo replace with max length between all tags
) : PriceLogger {
    override fun log(price: Price) {
        val formattedPrice = price.formatToThousands()
        println(tag.padEnd(tagEndPadLength) + formattedPrice)
    }
}