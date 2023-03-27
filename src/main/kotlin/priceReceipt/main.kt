package priceReceipt

import priceReceipt.handler.*
import priceReceipt.logger.GeneralFooterLogger
import priceReceipt.logger.GeneralHeaderLogger
import priceReceipt.logger.TagAndAmountPriceLogger

fun main() {

    //    runWithTagOnlyLogger()
    runWithTagAndAmountPriceLogger()
}

fun runWithTagOnlyLogger() {
    val basePrice = Price(5000L)

    val etaPriceHandler = EtaPriceHandler(minutes = 20)
    val surgePriceHandler = SurgePriceHandler(amount = 1.1f)
    val waitingTimePriceHandler = WaitingTimePriceHandler(minutes = 5)
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

    val receiptHandler = ReceiptHandler()
    val finalPrice = receiptHandler.handleAllAndLog(
        handlerList = handlerList,
        basePrice = basePrice,
    )
}

fun runWithTagAndAmountPriceLogger() {
    val basePrice = Price(5000L)

    val etaPriceHandler = EtaPriceHandler(
        minutes = 20,
        logger = TagAndAmountPriceLogger(tag = "eta", amount = "20", amountLabel = "minutes:")
    )
    val surgePriceHandler = SurgePriceHandler(
        amount = 1.1f,
        logger = TagAndAmountPriceLogger(tag = "Surge", amount = "1.1", amountLabel = "amount:")
    )
    val waitingTimePriceHandler = WaitingTimePriceHandler(
        minutes = 5,
        logger = TagAndAmountPriceLogger(tag = "Waiting Time", amount = "5", amountLabel = "minutes:")
    )
    val wheelchairPriceHandler = WheelchairPriceHandler(
        logger = TagAndAmountPriceLogger(tag = "Wheelchair", amount = "", amountLabel = "")
    )
    val discountPriceHandler = DiscountPriceHandler(
        percent = 30,
        logger = TagAndAmountPriceLogger(tag = "Discount", amount = "30%", amountLabel = "percent:")
    )
    val tollPriceHandler = TollPriceHandler(
        percent = 0.1f,
        logger = TagAndAmountPriceLogger(tag = "Toll", amount = "10%", amountLabel = "percent:")
    )

    val handlerList = listOf(
        etaPriceHandler,
        surgePriceHandler,
        waitingTimePriceHandler,
        wheelchairPriceHandler,
        discountPriceHandler,
        tollPriceHandler
    )

    val receiptHandler = ReceiptHandler(
        headerLogger = GeneralHeaderLogger(tagEndPadLength = 40, separatorLength = 46),
        footerLogger = GeneralFooterLogger(tagEndPadLength = 40, separatorLength = 46)
    )
    val finalPrice = receiptHandler.handleAllAndLog(
        handlerList = handlerList,
        basePrice = basePrice,
    )
}

