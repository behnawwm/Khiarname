package priceReceipt

fun main() {

    val basePrice = Price(5000L)

    val etaPriceHandler = EtaPriceHandler(20)
    val surgePriceHandler = SurgePriceHandler(1.1f)
    val waitingTimePriceHandler = WaitingTimePriceHandler(20)
    val wheelchairPriceHandler = WheelchairPriceHandler()
    val discountPriceHandler = DiscountPriceHandler(30)
    val tollPriceHandler = TollPriceHandler()

    val handlerList = listOf(
        etaPriceHandler,
        surgePriceHandler,
        waitingTimePriceHandler,
        wheelchairPriceHandler,
        discountPriceHandler,
        tollPriceHandler
    )

    val finalPrice = handlerList.handleAll(basePrice)
}

fun List<PriceHandler>.handleAll(basePrice: Price): Price {
    println("Base price: ${basePrice.value}")
    println("--------------------------")

    val first = firstOrNull() ?: return basePrice //todo log

    for (i in 0 until size - 1) {
        val currentItem = get(i)
        val nextItem = get(i + 1)
        currentItem.setNext(nextItem)
    }

    return first.handle(basePrice).also { finalPrice ->
        println("--------------------------")
        println("Final price: ${finalPrice.value}")
    }
}

@JvmInline
value class Price(val value: Long)

class EtaPriceHandler(private val minutes: Int, private val pricePerMinute: Long = 1000) :
    PriceHandler(PriceLogger("Eta")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (minutes * pricePerMinute))
    }
}

class SurgePriceHandler(private val amount: Float) : PriceHandler(PriceLogger("Surge")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price((prevPrice.value * amount).toLong())
    }
}

class WaitingTimePriceHandler(private val minutes: Int, private val pricePerMinute: Long = 1000) :
    PriceHandler(PriceLogger("Waiting Time")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (minutes * pricePerMinute))
    }
}

class WheelchairPriceHandler(private val reductionAmount: Long = 2000) : PriceHandler(PriceLogger("Wheelchair")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value - reductionAmount)
    }
}

class DiscountPriceHandler(private val percent: Int) : PriceHandler(PriceLogger("Discount")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price((prevPrice.value * (100 - percent)) / 100)
    }
}

class TollPriceHandler(private val percent: Float = 0.1f) : PriceHandler(PriceLogger("Toll")) {

    override fun calculateNewPrice(prevPrice: Price): Price {
        return Price(prevPrice.value + (percent * prevPrice.value).toLong())
    }
}