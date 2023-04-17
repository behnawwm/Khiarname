package rideRequestProcess

data class Ride(
    val origin: Location?,
    val destination: Location?,
    val service: Service?,
) {
    sealed class Service {
        abstract val simpleName: String

        object Normal : Service() {
            override val simpleName = "Normal"
        }

        data class Peyk(val description: String) : Service(){
            override val simpleName = "Peyk"
        }

        data class Line(val hasSeenGuide: Boolean, val seatCount: Int) : Service(){
            override val simpleName = "Line"
        }
    }
}
