package rideRequestProcess

import kotlin.math.max

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
            println("------ Select your origin ------")

            val lat = readFromCli(
                onInitial = {},
                onStepInitial = { println("Latitude:") },
                inputDataMapper = { it.toDoubleOrNull() },
                dataPredicate = { it != null },
                onError = { println("enter number only!") }
            )

            val lon = readFromCli(
                onInitial = {},
                onStepInitial = { println("Longitude:") },
                inputDataMapper = { it.toDoubleOrNull() },
                dataPredicate = { it != null },
                onError = { println("Enter number only!") }
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
            println("------ Select your destination ------")

            val lat = readFromCli(
                onInitial = { println("Enter latitude:") },
                onStepInitial = { println("Latitude:") },
                inputDataMapper = { it.toDoubleOrNull() },
                dataPredicate = { it != null },
                onError = { println("Enter number only!") }
            )

            val lon = readFromCli(
                onInitial = { println("Enter longitude:") },
                onStepInitial = { println("Longitude:") },
                inputDataMapper = { it.toDoubleOrNull() },
                dataPredicate = { it != null },
                onError = { println("Enter number only!") }
            )

            return Location(lat, lon)
        }

        override fun toString(): String = "SelectDestination"
    }

    class SelectService(private val initialRide: Ride) : State(initialRide) {
        private val availableServices =
            listOf(
                Ride.Service.Normal,
                Ride.Service.Line(false, 0),
                Ride.Service.Peyk("")
            )

        override fun next(): State {
            return when (val service = getInputFromUser()) {
                is Ride.Service.Line -> LineGuide(initialRide.copy(service = service))
                Ride.Service.Normal -> End(initialRide.copy(service = service))
                is Ride.Service.Peyk -> PeykDescriptionSelect(initialRide.copy(service = service))
            }
        }

        override fun back(): State {
            return SelectDestination(initialRide)
        }

        private fun getInputFromUser(): Ride.Service {
            val serviceNumber = readFromCli(
                onInitial = {
                    println("------ Select your service number ------")
                    availableServices.forEachIndexed { index, service ->
                        println("$index. $service")
                    }
                },
                onStepInitial = { println("Service number:") },
                inputDataMapper = { it.toIntOrNull() },
                dataPredicate = { serviceNumber -> serviceNumber != null && serviceNumber in availableServices.indices },
                onError = { serviceNumber ->
                    when (serviceNumber) {
                        null -> println("Enter number only")
                        in availableServices.indices -> println("Enter correct item index")
                        else -> println("Wrong input")
                    }
                }
            )

            return availableServices[serviceNumber]
        }

        override fun toString(): String = "SelectService"
    }

    class PeykDescriptionSelect(private val initialRide: Ride) : State(initialRide) {
        override fun next(): State {
            val peykDescription = getInputFromUser()
            val newService =
                (initialRide.service as Ride.Service.Peyk).copy(description = peykDescription) // todo remove casting
            return End(initialRide.copy(service = newService))
        }

        private fun getInputFromUser(): String {
            return readFromCli(
                onInitial = {
                    println("------ Enter your Peyk description ------")
                },
                onStepInitial = {
                    println("Enter text:")
                },
                inputDataMapper = { it },
                dataPredicate = { description -> !description.isNullOrEmpty() },
                onError = { println("Description should not be empty") }
            )
        }

        override fun back(): State {
            return SelectService(initialRide)
        }

        override fun toString(): String = "PeykDescriptionSelect"

    }

    class LineGuide(private val initialRide: Ride) : State(initialRide) {
        private val choices = listOf(
            Choice(1, "Accept"),
            Choice(2, "Deny")
        )
        private val acceptChoice = choices.first()

        override fun next(): State {
            val hasAccepted = getInputFromUser()
            return if (hasAccepted) {
                val newService =
                    (initialRide.service as Ride.Service.Line).copy(hasSeenGuide = true) // todo remove casting
                LineSeatCountSelection(initialRide.copy(service = newService))
            } else
                back()
        }

        private fun getInputFromUser(): Boolean {
            val choice = readFromCli(
                onInitial = {
                    println("------ Line Service Guide ------")
                    println("You are using a Line service which is Felan!")
                },
                onStepInitial = {
                    println("Do you accept?")
                    choices.forEach {
                        println("${it.number}. ${it.text}")
                    }
                },
                inputDataMapper = { it.toIntOrNull() },
                dataPredicate = { choiceNumber -> choiceNumber != null && choiceNumber in choices.map { it.number } },
                onError = { println("Enter valid number!") }
            )
            return choice == acceptChoice.number
        }


        override fun back(): State {
            return SelectService(initialRide)
        }

        override fun toString(): String = "LineGuide"

        data class Choice(
            val number: Int,
            val text: String,
        )
    }

    class LineSeatCountSelection(private val initialRide: Ride) : State(initialRide) {
        private val maxSeatCount = 2

        override fun next(): State {
            val seatCount = getInputFromUser()
            val newService =
                (initialRide.service as Ride.Service.Line).copy(seatCount = seatCount) // todo remove casting
            return End(initialRide.copy(service = newService))
        }

        private fun getInputFromUser(): Int {
            return readFromCli(
                onInitial = {
                    println("------ Seat count selection ------")
                },
                onStepInitial = {
                    println("Enter seat count:")
                },
                inputDataMapper = { it.toIntOrNull() },
                dataPredicate = { seatCount -> seatCount != null && seatCount <= maxSeatCount },
                onError = { seatCount ->
                    when {
                        seatCount == null -> println("Enter valid number")
                        seatCount > maxSeatCount -> println("Max seat count is: $maxSeatCount")
                    }
                }
            )
        }

        override fun back(): State {
            return LineGuide(initialRide)
        }

        override fun toString(): String = "LineSeatCountSelection"
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
