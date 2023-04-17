package rideRequestProcess

data class Ride(
    val origin: Location?,
    val destination: Location?,
    val service: Service?,
) {
    sealed class Service {
        object Normal : Service() {
            override fun toString(): String = "Normal"
        }

        data class Peyk(val description: String) : Service()

        data class Line(val hasSeenGuide: Boolean, val seatCount: Int) : Service()
    }
}
