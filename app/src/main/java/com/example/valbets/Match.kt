package com.example.valbets

class Match(
    val teamA: Team,
    val teamB: Team,
    val scoreA: Int,
    val scoreB: Int,
    val date: String,
    val topFragger: Player? = null,
    val topAssist: Player? = null,
    val topDeath: Player? = null,
    val logoA: Int,
    val logoB: Int){
    constructor(teamA: Team, teamB: Team, scoreA: Int, scoreB: Int, date: String, logoA: Int, logoB: Int) : this(teamA,teamB,scoreA,scoreB,date,null,null,null,logoA,logoB)

}