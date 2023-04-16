package rideRequestProcess

fun <T> readFromCli(
    onInitial: () -> Unit,
    onStepInitial: () -> Unit, //todo choose a better name!
    inputDataMapper: (String) -> T?,
    dataPredicate: (T?) -> Boolean,
    onError: (T?) -> Unit
): T {
    onInitial()
    var data: T?
    while (true) {
        onStepInitial()
        val test = readln()
        data = inputDataMapper(test)
        if (dataPredicate(data))
            break
        onError(data)
    }
    return data!!
}