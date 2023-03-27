package priceReceipt

import priceReceipt.handler.*

fun main() {

    val basePrice = Price(5000L)

    val etaPriceHandler = EtaPriceHandler(minutes = 20)
    val surgePriceHandler = SurgePriceHandler(amount = 1.1f)
    val waitingTimePriceHandler = WaitingTimePriceHandler(minutes = 20)
    val wheelchairPriceHandler = WheelchairPriceHandler()
    val discountPriceHandler = DiscountPriceHandler(percent = 30)
    val tollPriceHandler = TollPriceHandler()

    val handlerList = listOf(
        etaPriceHandler,
        surgePriceHandler,
        waitingTimePriceHandler,
        wheelchairPriceHandler,
        discountPriceHandler,
        tollPriceHandler
    )

    val receiptHandler = ReceiptHandler(
        handlerList = handlerList,
        basePrice = basePrice,
    )
    val finalPrice = receiptHandler.handleAllAndLog()
}