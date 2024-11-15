package com.example.valbets

import android.media.Image

class Match(
    val equipeA: Team,
    val equipeB: Team,
    val placarA: Int,
    val placarB: Int,
    val data: String,
    val topFragger: Player,
    val topAssist: Player,
    val topDeath: Player,
    val logoA: Int,
    val logoB: Int){

}