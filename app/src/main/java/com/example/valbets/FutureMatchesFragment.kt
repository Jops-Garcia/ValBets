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
        val jops = Player("Jops", 10, 5, 3)
        val dudalol = Player("Dudalol", 15, 8, 4)
        val players : List<Player> = listOf(jops, dudalol)
        val teamA = Team("EDG", players, true)
        val teamB = Team("Heretics", players, false)

        val logoA = R.drawable._2c82049253b2
        val logoB = R.drawable._37b755224c12


        val futureMatchesList = listOf(
            Match(teamA, teamB, 0, 0, "05/11/2024",jops,jops,jops,logoA,logoB),
            Match(teamA, teamB, 0, 0, "05/11/2024",jops,jops,jops,logoA,logoB),
            Match(teamA, teamB, 0, 0, "05/11/2024",jops,jops,jops,logoA,logoB),

            )

        recyclerView.adapter = MatchAdapter(futureMatchesList)
    }
}
