package com.kata

data class Position(val x: Int, val y: Int)

abstract class Vehicle(val name: String, initialPosition: Position) {
    var position: Position = initialPosition
        protected set
}

annotation class Author(val name: String)

@Author("Pepe")
class Tank(name: String, initialPosition: Position): Vehicle(name, initialPosition) {
    var isShooting = false
        private set

    fun move(x: Int, y: Int) {
        position = Position(x, y)
    }

    fun calculateTimeTo(x: Int, y: Int): Int {
        return if (isShooting) 20 else 10
    }

    fun shoot() {
        isShooting = true
        logShoot()
    }

    private fun logShoot() {
        println("shoot called")
    }
}
