package rideRequestProcess

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

            val lat = readFromCli(
                onInitial = {},
                onStepInitial = { println("latitude:") },
                inputDataMapper = { it.toDoubleOrNull() },
                dataPredicate = { it != null },
                onError = { println("enter number only!") }
            )

            val lon = readFromCli(
                onInitial = {},
                onStepInitial = { println("longitude:") },
                inputDataMapper = { it.toDoubleOrNull() },
                dataPredicate = { it != null },
                onError = { println("enter number only!") }
            )

            return Location(lat, lon)
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

            val lat = readFromCli(
                onInitial = {},
                onStepInitial = { println("latitude:") },
                inputDataMapper = { it.toDoubleOrNull() },
                dataPredicate = { it != null },
                onError = { println("enter number only!") }
            )

            val lon = readFromCli(
                onInitial = {},
                onStepInitial = { println("longitude:") },
                inputDataMapper = { it.toDoubleOrNull() },
                dataPredicate = { it != null },
                onError = { println("enter number only!") }
            )

            return Location(lat, lon)
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

            val serviceNumber = readFromCli(
                onInitial = {
                    availableServices.forEachIndexed { index, service ->
                        println("$index. $service")
                    }
                },
                onStepInitial = { println("Service number:") },
                inputDataMapper = { it.toIntOrNull() },
                dataPredicate = { serviceNumber -> serviceNumber != null && serviceNumber in availableServices.indices },
                onError = { println("enter number only!") }
            )

            return availableServices[serviceNumber]
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