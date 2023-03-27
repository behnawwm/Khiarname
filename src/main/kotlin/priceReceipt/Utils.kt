package priceReceipt

fun Price.formatPrice(): String {
    return value.toString().reversed().chunked(3).joinToString(separator = ",").reversed()
}