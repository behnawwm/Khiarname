package priceReceipt.logger

import priceReceipt.HeaderLogger
import priceReceipt.Price
import priceReceipt.formatToThousands

class GeneralHeaderLogger(
    private val tagEndPadLength: Int = 20,  //todo replace with max length between all tags
    private val separatorLength: Int = 26,  //todo replace with max length between all prices
    private val tag: String = "Base price:",
) : HeaderLogger {

    override fun log(price: Price) {
        val formattedPrice = price.formatToThousands()
        println(tag.padEnd(tagEndPadLength) + formattedPrice)
    }

    override fun logSeparator() {
        println("-".repeat(separatorLength))
    }

}
