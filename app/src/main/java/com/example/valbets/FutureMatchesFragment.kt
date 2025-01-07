package com.example.valbets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FutureMatchesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_future_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewFutureMatches)
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


        val futureMatchesList = listOf(
            Match(teamA, teamB, 0, 0, "20/11/2024",logoA,logoB),
            Match(teamC, teamD, 0, 0, "25/11/2024",logoC,logoD),
            Match(teamD, teamA, 0, 0, "30/11/2024",logoD,logoA),
            Match(teamC, teamB, 0, 0, "02/12/2024",logoC,logoB),

            )

        recyclerView.adapter = MatchAdapter(futureMatchesList)
    }
}
