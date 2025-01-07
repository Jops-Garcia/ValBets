package com.example.valbets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PastMatchesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_past_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPastMatches)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val player1 = Player("player1", 10, 5, 3)
        val player2 = Player("player2", 15, 8, 4)
        val players : List<Player> = listOf(player1, player2)

        val teamA = Team("EDG", players)
        val teamB = Team("Heretics", players)
        val teamC = Team("Loud", players)
        val teamD = Team("G2", players)

        val logoA = R.drawable._2c82049253b2
        val logoB = R.drawable._37b755224c12
        val logoC = R.drawable.loud
        val logoD = R.drawable.g2



        val partidasPassadas = listOf(
            Match(teamA, teamB, 3, 0, "05/11/2024",player1,player1,player1,logoA,logoB),
            Match(teamA, teamC, 0, 3, "18/11/2024",player1,player1,player1,logoA,logoC),
            )

        recyclerView.adapter = MatchAdapter(partidasPassadas)
    }
}
