package com.example.valbets

import android.media.Image

class Match(
    val teamA: Team,
    val teamB: Team,
    val scoreA: Int,
    val scoreB: Int,
    val date: String,
    val topFragger: Player,
    val topAssist: Player,
    val topDeath: Player,
    val logoA: Int,
    val logoB: Int){

}