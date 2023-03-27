package priceReceipt

interface Handler<T> {
    fun setNext(handler: Handler<T>)
    fun handle(prev: T): T
}

abstract class BaseHandler<T> : Handler<T> {
    protected var nextHandler: Handler<T>? = null
}

abstract class PriceHandler(private val logger: PriceLogger) : BaseHandler<Price>() {
    override fun setNext(handler: Handler<Price>) {
        nextHandler = handler
    }

    override fun handle(prev: Price): Price {
        val calculatedPrice = calculateNewPrice(prev)
        logger.log(calculatedPrice)
        return if (nextHandler == null) {
            calculatedPrice
        } else {
            nextHandler!!.handle(calculatedPrice)
        }
    }

    abstract fun calculateNewPrice(prevPrice: Price): Price
}