package rideRequestProcess

fun main() {

    val initialRide = Ride(origin = null, destination = null, service = null)
    val initialState = State.Start(initialRide = initialRide)

    println("started")

    var nextState = initialState.next()
    while (nextState !is State.End) {
        println("state: $nextState")
        nextState = nextState.next()
    }

    println("finished")
    println("result ride: ${nextState.ride}")
}

