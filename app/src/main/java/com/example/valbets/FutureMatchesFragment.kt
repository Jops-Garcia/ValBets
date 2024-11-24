package com.example.valbets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.compose.foundation.Image

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
        val jops: Player = Player("Jops", 10, 5, 3)
        val dudalol: Player = Player("Dudalol", 15, 8, 4)
        var players : List<Player> = listOf(jops, dudalol)
        val teamA = Team("EDG", players, true)
        val teamB = Team("Heretics", players, false)

        var logoA = R.drawable._2c82049253b2
        var logoB = R.drawable._37b755224c12


        val partidasFuturas = listOf(
            Match(teamA, teamB, 0, 0, "05/11/2024",jops,jops,jops,logoA,logoB),
            Match(teamA, teamB, 0, 0, "05/11/2024",jops,jops,jops,logoA,logoB),
            Match(teamA, teamB, 0, 0, "05/11/2024",jops,jops,jops,logoA,logoB),

            )

        recyclerView.adapter = PartidaAdapter(requireContext(), partidasFuturas)
    }
}
