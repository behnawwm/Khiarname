package priceReceipt

interface Logger {
    fun log(input: String)
}

class PriceLogger(private val tag: String) : Logger {
    override fun log(input: String) {
        val formattedPrice = input.reversed().chunked(3).joinToString(separator = ",").reversed()
        println("$tag: $formattedPrice")
    }
}