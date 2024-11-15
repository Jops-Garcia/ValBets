package com.example.valbets

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: REMOVE BAD CODE
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Upcoming"
                1 -> "Matches"
                else -> null
            }
        }.attach()

    }
}




class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FutureMatchesFragment()
            1 -> PastMatchesFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}


class PartidaAdapter(private val matches: List<Match>) : RecyclerView.Adapter<PartidaAdapter.PartidaViewHolder>() {

    inner class PartidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTeamA: TextView = itemView.findViewById(R.id.textTeamA)
        val textTeamB: TextView = itemView.findViewById(R.id.textTeamB)
        val textScore: TextView = itemView.findViewById(R.id.textScore)
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val imageTeamA: ImageView = itemView.findViewById(R.id.imageTeamA)
        val imageTeamB: ImageView = itemView.findViewById(R.id.imageTeamB)
        val buttonPlaceBet: Button = itemView.findViewById(R.id.buttonPlaceBet)  // Referência ao botão

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partida, parent, false)
        return PartidaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartidaViewHolder, position: Int) {
        val match = matches[position]
        holder.textTeamA.text = match.equipeA.name
        holder.textTeamB.text = match.equipeB.name
        holder.textScore.text = "${match.placarA} - ${match.placarB}"
        holder.textDate.text = match.data

        holder.imageTeamA.setImageResource(match.logoA)
        holder.imageTeamB.setImageResource(match.logoB)

        val infuraUrl = "https://sepolia.infura.io/v3/4600bb8b5bba4b249949044e28f64b07"
        val web3 = Web3j.build(HttpService(infuraUrl))
        val credentials = Credentials.create("4600bb8b5bba4b249949044e28f64b07")
        val contractAddress = "0xc2aD23B58278Dd729Ca16d7b7ecD6Bb9C2AF60A7"

        // Carregue o contrato usando a classe gerada
        val bettingContract = BetVal.load(contractAddress, web3, credentials, DefaultGasProvider())


        // Verifique se a partida já teve um placar registrado
        if (match.placarA != 0 || match.placarB != 0) {
            holder.buttonPlaceBet.visibility = View.GONE
        } else {
            holder.buttonPlaceBet.visibility = View.VISIBLE
        }

        holder.buttonPlaceBet.setOnClickListener {
            // Defina o valor da aposta (em wei) - 0.01 ETH, por exemplo
            val betAmount = BigInteger.valueOf(1000000000000000)  // 0.01 ETH em wei

            try {
                // Envia a transação para fazer uma aposta no Time A
                val transactionReceipt = bettingContract.placeBetOnTeamA(betAmount).send()

                // Verifica o status da transação
                if (transactionReceipt.isStatusOK) {
                    Toast.makeText(holder.itemView.context, "Aposta realizada com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(holder.itemView.context, "Falha na aposta", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(holder.itemView.context, "Erro ao realizar a aposta: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int = matches.size
}

