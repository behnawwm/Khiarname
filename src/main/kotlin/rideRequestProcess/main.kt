package rideRequestProcess

fun main() {

    val initialRide = Ride(origin = null, destination = null, service = null)
    val initialState = State.Start(initialRide = initialRide)

    val toAddOrigin = Location(1, 2)
    val toAddDestination = Location(3, 4)
    val toAddService = Ride.Service.Normal


    println("started")

    var nextState = initialState.next()
    while (nextState !is State.End) {
        println("state: $nextState")
        when (nextState) {
            is State.SelectOrigin -> {
                nextState.setOrigin(origin = toAddOrigin)
            }

            is State.SelectDestination -> {
                nextState.setDestination(destination = toAddDestination)
            }

            is State.SelectService -> {
                nextState.setService(service = toAddService)
            }

            is State.Start -> {}
            is State.End -> {}
        }
        nextState = nextState.next()
    }

    println("finished")
}

sealed class State(val ride: Ride) {
    abstract fun next(): State
    abstract fun back(): State

    class Start(private val initialRide: Ride) : State(initialRide) {
        override fun next(): State {
            return when {
                initialRide.origin == null -> SelectOrigin(initialRide)
                initialRide.destination == null -> SelectDestination(initialRide)
                else -> SelectService(initialRide)
            }
        }

        override fun back(): State {
            // nothing!
            return Start(initialRide)
        }
    }

    class SelectOrigin(private val initialRide: Ride) : State(initialRide) {
        private var origin: Location? = null

        override fun next(): State {
            return if (origin != null)
                SelectDestination(initialRide.copy(origin = origin))
            else {
                SelectOrigin(initialRide)
            }
        }

        override fun back(): State {
            TODO("Not yet implemented")
        }

        fun setOrigin(origin: Location) {
            this.origin = origin
        }
    }

    class SelectDestination(private val initialRide: Ride) : State(initialRide) {
        private var destination: Location? = null

        override fun next(): State {
            return if (destination != null)
                SelectService(initialRide.copy(destination = destination))
            else {
                SelectDestination(initialRide)
            }
        }

        override fun back(): State {
            TODO("Not yet implemented")
        }

        fun setDestination(destination: Location) {
            this.destination = destination
        }
    }


    class SelectService(private val initialRide: Ride) : State(initialRide) {
        private var service: Ride.Service? = null

        override fun next(): State {
            return if (service != null)
                End(initialRide.copy(service = service))
            else {
                SelectDestination(initialRide)
            }
        }

        override fun back(): State {
            TODO("Not yet implemented")
        }

        fun setService(service: Ride.Service) {
            this.service = service
        }
    }

    class End(private val initialRide: Ride) : State(initialRide) {

        override fun next(): State {
            // nothing!
            return End(initialRide)
        }

        override fun back(): State {
            TODO("Not yet implemented")
        }
    }

}


data class Location(
    val lat: Long,
    val lon: Long,
)

data class Ride(
    val origin: Location?,
    val destination: Location?,
    val service: Service?,
) {
    sealed class Service {
        object Normal : Service()
        class Peyk(val description: String) : Service()
        class Line(val hasSeenGuide: Boolean, val seatCount: Int) : Service()
    }
}

