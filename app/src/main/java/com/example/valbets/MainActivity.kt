package com.example.valbets

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
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
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Function
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameterName


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


class PartidaAdapter(private val context: Context, private val matches: List<Match>) : RecyclerView.Adapter<PartidaAdapter.PartidaViewHolder>() {

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

        // Mostrar ou ocultar o botão de aposta
        holder.buttonPlaceBet.visibility = if (match.placarA == 0 && match.placarB == 0) {
            View.VISIBLE
        } else {
            View.GONE
        }

        holder.buttonPlaceBet.setOnClickListener {
            exibirDialogoAposta(holder.itemView.context, match)
        }

    }

    override fun getItemCount(): Int = matches.size

    fun exibirDialogoAposta(context: Context, partida: Match) {
        val dialog = AlertDialog.Builder(context)
        val layoutInflater = LayoutInflater.from(context)
        val dialogView = layoutInflater.inflate(R.layout.bet_dialog, null)

        val edtQuantidade = dialogView.findViewById<EditText>(R.id.edtQuantidade)
        val radioGroupTimes = dialogView.findViewById<RadioGroup>(R.id.radioGroupTimes)

        // Adicionar dinamicamente os nomes dos times
        val radioButtonTeamA = RadioButton(context).apply {
            text = partida.equipeA.name
            id = View.generateViewId()
        }
        val radioButtonTeamB = RadioButton(context).apply {
            text = partida.equipeB.name
            id = View.generateViewId()
        }

        radioGroupTimes.addView(radioButtonTeamA)
        radioGroupTimes.addView(radioButtonTeamB)

        dialog.setView(dialogView)
        dialog.setTitle("Fazer Aposta")
        dialog.setPositiveButton("Apostar") { _, _ ->
            val quantidade = edtQuantidade.text.toString().toBigDecimalOrNull()
            val timeSelecionadoId = radioGroupTimes.checkedRadioButtonId

            if (quantidade != null && timeSelecionadoId != -1) {
                val timeSelecionado = dialogView.findViewById<RadioButton>(timeSelecionadoId).text.toString()

                enviarTransacao(quantidade, partida, timeSelecionado, context)
            } else {
                Toast.makeText(context, "Por favor, insira todos os dados!", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setNegativeButton("Cancelar", null)
        dialog.show()
    }

    fun enviarTransacao(valor: BigDecimal, partida: Match, time: String, context: Context) {
        val web3j = Web3j.build(HttpService("https://sepolia.infura.io/v3/<YOUR_INFURA_PROJECT_ID>"))
        val credentials = Credentials.create("<PRIVATE_KEY>")
        val contratoEndereco = "0x09E39230a55310E616ee8B72B7b4a07fE8204477"

        try {
            val nonce = web3j.ethGetTransactionCount(credentials.address, DefaultBlockParameterName.LATEST)
                .send()
                .transactionCount

            val gasPrice = web3j.ethGasPrice().send().gasPrice

            // Converter valor para Wei
            val value = Convert.toWei(valor, Convert.Unit.ETHER).toBigInteger()

            // Definir a função a ser chamada com base no time
            val functionName = if (time == partida.equipeA.name) "placeBetOnTeamA" else "placeBetOnTeamB"

            val function = org.web3j.abi.datatypes.Function(
                functionName,
                listOf(), // Nenhum parâmetro para estas funções
                emptyList()
            )

            val encodedFunction = FunctionEncoder.encode(function)

            val transaction = Transaction.createFunctionCallTransaction(
                credentials.address,
                nonce,
                gasPrice,
                null, // Deixe o gasLimit nulo para estimativa
                contratoEndereco,
                value,
                encodedFunction
            )

            val estimatedGas = web3j.ethEstimateGas(transaction).send().amountUsed
            val gasLimit = estimatedGas.add(BigInteger.valueOf(10000)) // Adicione margem

            // Construir transação final
            val rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contratoEndereco,
                value,
                encodedFunction
            )

            val signedTransaction = TransactionEncoder.signMessage(rawTransaction, credentials)
            val signedHex = "0x" + signedTransaction.joinToString("") { "%02x".format(it) }

            val response = web3j.ethSendRawTransaction(signedHex).send()

            if (response.hasError()) {
                println("Erro na transação: ${response.error.message}")
                Toast.makeText(context, "Erro: ${response.error.message}", Toast.LENGTH_LONG).show()
            } else {
                println("Transação enviada com sucesso! Hash: ${response.transactionHash}")
                Toast.makeText(context, "Transação enviada! Hash: ${response.transactionHash}", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
            println("Erro ao enviar transação: ${e.message}")
        }
    }

}

