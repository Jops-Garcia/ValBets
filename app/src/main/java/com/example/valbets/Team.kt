package com.example.valbets

class Team(
    val name: String,
    val players: List<Player>,
    val isWinner: Boolean,) {
    constructor(name: String, players: List<Player>) : this(name,players,false)

}