package priceReceipt

fun Price.formatToThousands(): String {
    return value.toString().reversed().chunked(3).joinToString(separator = ",").reversed()
}