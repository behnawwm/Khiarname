package rideRequestProcess

fun main() {

    val initialRide = Ride(origin = null, destination = null, service = null)
    val initialState = State.Start(initialRide = initialRide)

    val toAddOrigin = Location(1.0, 2.0)
    val toAddDestination = Location(3.0, 4.0)
    val toAddService = Ride.Service.Normal


    println("started")

    var nextState = initialState.next()
    while (nextState !is State.End) {
        println("state: $nextState")
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

        override fun toString(): String = "Start"
    }

    class SelectOrigin(private val initialRide: Ride) : State(initialRide) {

        override fun next(): State {
            val origin = getInputFromUser()
            return SelectDestination(initialRide.copy(origin = origin))
        }

        private fun getInputFromUser(): Location {
            println("Enter your origin:")

            var lat: Double?
            var lon: Double?

            while (true) {
                println("latitude:")
                lat = readln().toDoubleOrNull()
                if (lat != null)
                    break
                println("enter number only!")
            }

            while (true) {
                println("longitude:")
                lon = readln().toDoubleOrNull()
                if (lon != null)
                    break
                println("enter number only!")
            }
            return Location(lat!!, lon!!)
        }

        override fun back(): State {
            return Start(initialRide)
        }

        override fun toString(): String = "SelectOrigin"

    }

    class SelectDestination(private val initialRide: Ride) : State(initialRide) {
        override fun next(): State {
            val destination = getInputFromUser()
            return SelectService(initialRide.copy(destination = destination))
        }

        override fun back(): State {
            return SelectOrigin(initialRide)
        }

        private fun getInputFromUser(): Location {
            println("Enter your destination:")

            var lat: Double?
            var lon: Double?

            while (true) {
                println("latitude:")
                lat = readln().toDoubleOrNull()
                if (lat != null)
                    break
                println("enter number only!")
            }

            while (true) {
                println("longitude:")
                lon = readln().toDoubleOrNull()
                if (lon != null)
                    break
                println("enter number only!")
            }
            return Location(lat!!, lon!!)
        }

        override fun toString(): String = "SelectDestination"
    }


    class SelectService(private val initialRide: Ride) : State(initialRide) {
        private val availableServices =
            listOf(Ride.Service.Normal, Ride.Service.Line(true, 2), Ride.Service.Peyk(""))

        override fun next(): State {
            val service = getInputFromUser()
            return End(initialRide.copy(service = service))
        }

        override fun back(): State {
            return SelectDestination(initialRide)
        }

        private fun getInputFromUser(): Ride.Service {
            println("Enter your service number:")
            availableServices.forEachIndexed { index, service ->
                println("$index. $service")
            }

            var serviceNumber: Int?

            while (true) {
                println("Service number:")
                serviceNumber = readln().toIntOrNull()
                if (serviceNumber != null && serviceNumber in availableServices.indices)
                    break
                println("enter number only!")
            }

            return availableServices[serviceNumber!!]
        }

        override fun toString(): String = "SelectService"
    }

    class End(private val initialRide: Ride) : State(initialRide) {

        override fun next(): State {
            // nothing!
            return End(initialRide)
        }

        override fun back(): State {
            return SelectService(initialRide)
        }

        override fun toString(): String = "End"
    }

}


data class Location(
    val lat: Double,
    val lon: Double,
)

data class Ride(
    val origin: Location?,
    val destination: Location?,
    val service: Service?,
) {
    sealed class Service {
        object Normal : Service() {
            override fun toString(): String = "Normal"
        }

        class Peyk(val description: String) : Service() {
            override fun toString(): String = "Peyk"
        }

        class Line(val hasSeenGuide: Boolean, val seatCount: Int) : Service() {
            override fun toString(): String = "Line"
        }
    }
}

