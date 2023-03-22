package priceReceipt

interface Handler<T> {
    fun setNext(handler: Handler<T>)
    fun handle(prev: T): T
}

abstract class BaseHandler<T> : Handler<T> {
    protected var nextHandler: Handler<T>? = null
}

abstract class PriceHandler(private val logger: Logger) : BaseHandler<Long>() {
    override fun setNext(handler: Handler<Long>) {
        nextHandler = handler
    }

    override fun handle(prev: Long): Long {
        val calculatedPrice = calculateNewPrice(prev)
        logger.log(calculatedPrice.toString())
        return if (nextHandler == null) {
            calculatedPrice
        } else {
            nextHandler!!.handle(calculatedPrice)
        }
    }

    abstract fun calculateNewPrice(prev: Long): Long
}