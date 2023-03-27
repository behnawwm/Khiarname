package priceReceipt.logger

import priceReceipt.FooterLogger
import priceReceipt.Price
import priceReceipt.formatToThousands

class GeneralFooterLogger(
    private val tagEndPadLength: Int = 20,  //todo replace with max length between all tags
    private val separatorLength: Int = 26,  //todo replace with max length between all prices
    private val tag: String = "Final price:"
) : FooterLogger {

    override fun log(price: Price) {
        val formattedPrice = price.formatToThousands()
        println(tag.padEnd(tagEndPadLength) + formattedPrice)
    }

    override fun logSeparator() {
        println("-".repeat(separatorLength))
    }

}