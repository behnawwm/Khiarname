package priceReceipt

fun main() {

    val basePrice = 5000L
    println("Base price : $basePrice")
    println("--------------------------")

    val etaPriceHandler = EtaPriceHandler(20)
    val surgePriceHandler = SurgePriceHandler(1.1f)
    val waitingTimePriceHandler = WaitingTimePriceHandler(20)
    val wheelchairPriceHandler = WheelchairPriceHandler()
    val discountPriceHandler = DiscountPriceHandler(30)
    val tollPriceHandler = TollPriceHandler()


    etaPriceHandler.setNext(surgePriceHandler)
    surgePriceHandler.setNext(waitingTimePriceHandler)
    waitingTimePriceHandler.setNext(wheelchairPriceHandler)
    wheelchairPriceHandler.setNext(discountPriceHandler)
    discountPriceHandler.setNext(tollPriceHandler)

    val finalPrice = etaPriceHandler.handle(basePrice)
    println("--------------------------")
    println("Final price: $finalPrice")
}

class EtaPriceHandler(private val minutes: Int, private val pricePerMinute: Long = 1000) :
    PriceHandler(PriceLogger("Eta")) {

    override fun calculateNewPrice(prev: Long): Long {
        return prev + (minutes * pricePerMinute)
    }
}

class SurgePriceHandler(private val amount: Float) : PriceHandler(PriceLogger("Surge")) {

    override fun calculateNewPrice(prev: Long): Long {
        return (prev * amount).toLong()
    }
}

class WaitingTimePriceHandler(private val minutes: Int, private val pricePerMinute: Long = 1000) :
    PriceHandler(PriceLogger("Waiting Time")) {

    override fun calculateNewPrice(prev: Long): Long {
        return prev + (minutes * pricePerMinute)
    }
}

class WheelchairPriceHandler(private val reductionAmount: Long = 2000) : PriceHandler(PriceLogger("Wheelchair")) {

    override fun calculateNewPrice(prev: Long): Long {
        return prev - reductionAmount
    }
}

class DiscountPriceHandler(private val percent: Int) : PriceHandler(PriceLogger("Discount")) {

    override fun calculateNewPrice(prev: Long): Long {
        return (prev * (100 - percent)) / 100
    }
}

class TollPriceHandler(private val percent: Float = 0.1f) : PriceHandler(PriceLogger("Toll")) {

    override fun calculateNewPrice(prev: Long): Long {
        return prev + (percent * prev).toLong()
    }
}