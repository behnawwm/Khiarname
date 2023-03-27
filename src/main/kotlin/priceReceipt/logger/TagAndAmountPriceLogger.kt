package priceReceipt.logger

import priceReceipt.Price
import priceReceipt.PriceLogger
import priceReceipt.formatToThousands

class TagAndAmountPriceLogger(
    private val tag: String,
    private val amount: String,
    private val amountLabel: String,
    private val tagEndPadLength: Int = 20,  //todo replace with max length between all tags
    private val amountEndPadLength: Int = 10,  //todo replace with max length between all amounts
) : PriceLogger {
    override fun log(price: Price) {
        val formattedPrice = price.formatToThousands()
        println(
            tag.padEnd(tagEndPadLength) +
                    amountLabel.padEnd(amountEndPadLength) +
                    amount.padEnd(amountEndPadLength) +
                    formattedPrice
        )
    }
}