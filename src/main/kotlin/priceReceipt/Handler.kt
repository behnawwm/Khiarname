package priceReceipt

interface Handler<T> {
    fun setNext(handler: Handler<T>)
    fun handle(prev: T): T
}

abstract class BaseHandler<T> : Handler<T> {
    protected var nextHandler: Handler<T>? = null
}

abstract class PriceHandler(private val logger: Logger) : BaseHandler<Price>() {
    override fun setNext(handler: Handler<Price>) {
        nextHandler = handler
    }

    override fun handle(prevPrice: Price): Price {
        val calculatedPrice = calculateNewPrice(prevPrice)
        logger.log(calculatedPrice.value.toString())
        return if (nextHandler == null) {
            calculatedPrice
        } else {
            nextHandler!!.handle(calculatedPrice)
        }
    }

    abstract fun calculateNewPrice(prevPrice: Price): Price
}