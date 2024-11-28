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
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import org.web3j.abi.FunctionEncoder
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameterName


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


class MatchAdapter(private val context: Context, private val matches: List<Match>) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTeamA: TextView = itemView.findViewById(R.id.textTeamA)
        val textTeamB: TextView = itemView.findViewById(R.id.textTeamB)
        val textScore: TextView = itemView.findViewById(R.id.textScore)
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val imageTeamA: ImageView = itemView.findViewById(R.id.imageTeamA)
        val imageTeamB: ImageView = itemView.findViewById(R.id.imageTeamB)
        val buttonPlaceBet: Button = itemView.findViewById(R.id.buttonPlaceBet)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.textTeamA.text = match.teamA.name
        holder.textTeamB.text = match.teamB.name
        holder.textScore.text = "${match.scoreA} - ${match.scoreB}"
        holder.textDate.text = match.date

        holder.imageTeamA.setImageResource(match.logoA)
        holder.imageTeamB.setImageResource(match.logoB)

        holder.buttonPlaceBet.visibility = if (match.scoreA == 0 && match.scoreB == 0) {
            View.VISIBLE
        } else {
            View.GONE
        }

        holder.buttonPlaceBet.setOnClickListener {
            showBetDialog(holder.itemView.context, match)
        }

    }

    override fun getItemCount(): Int = matches.size

    private fun showBetDialog(context: Context, match: Match) {
        val dialog = AlertDialog.Builder(context)
        val layoutInflater = LayoutInflater.from(context)
        val dialogView = layoutInflater.inflate(R.layout.bet_dialog, null)

        val edtValue = dialogView.findViewById<EditText>(R.id.edtValue)
        val radioGroupTeam = dialogView.findViewById<RadioGroup>(R.id.radioGroupTeam)

        val radioButtonTeamA = RadioButton(context).apply {
            text = match.teamA.name
            id = View.generateViewId()
        }
        val radioButtonTeamB = RadioButton(context).apply {
            text = match.teamB.name
            id = View.generateViewId()
        }

        radioGroupTeam.addView(radioButtonTeamA)
        radioGroupTeam.addView(radioButtonTeamB)

        dialog.setView(dialogView)
        dialog.setTitle("Place bet")
        dialog.setPositiveButton("Bet") { _, _ ->
            val betValue = edtValue.text.toString().toBigDecimalOrNull()
            val selectedTeamId = radioGroupTeam.checkedRadioButtonId

            if (betValue != null && selectedTeamId != -1) {
                val selectedTeam = dialogView.findViewById<RadioButton>(selectedTeamId).text.toString()

                sendTransaction(betValue, match, selectedTeam, context)
            } else {
                Toast.makeText(context, "Please enter all data!", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setNegativeButton("Cancel", null)
        dialog.show()
    }

    private fun sendTransaction(betValue: BigDecimal, match: Match, team: String, context: Context) {
        val web3j = Web3j.build(HttpService("https://sepolia.infura.io/v3/<YOUR_INFURA_PROJECT_ID>"))
        val credentials = Credentials.create("<PRIVATE_KEY>")
        val contractAdress = "<CONTRACT_ADDRESS>"

        try {
            val nonce = web3j.ethGetTransactionCount(credentials.address, DefaultBlockParameterName.LATEST)
                .send()
                .transactionCount

            val gasPrice = web3j.ethGasPrice().send().gasPrice

            val betValueConverted = Convert.toWei(betValue, Convert.Unit.ETHER).toBigInteger()

            val functionName = if (team == match.teamA.name) "placeBetOnTeamA" else "placeBetOnTeamB"

            val function = org.web3j.abi.datatypes.Function(
                functionName,
                listOf(),
                emptyList()
            )

            val encodedFunction = FunctionEncoder.encode(function)

            val transaction = Transaction.createFunctionCallTransaction(
                credentials.address,
                nonce,
                gasPrice,
                null,
                contractAdress,
                betValueConverted,
                encodedFunction
            )

            val estimatedGas = web3j.ethEstimateGas(transaction).send().amountUsed
            val gasLimit = estimatedGas.add(BigInteger.valueOf(10000))

            val rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAdress,
                betValueConverted,
                encodedFunction
            )

            val signedTransaction = TransactionEncoder.signMessage(rawTransaction, credentials)
            val signedHex = "0x" + signedTransaction.joinToString("") { "%02x".format(it) }

            val response = web3j.ethSendRawTransaction(signedHex).send()

            if (response.hasError()) {
                println("Transaction error: ${response.error.message}")
                Toast.makeText(context, "Error: ${response.error.message}", Toast.LENGTH_LONG).show()
            } else {
                println("Transaction sent successfully! Hash: ${response.transactionHash}")
                Toast.makeText(context, "Transaction sent! Hash: ${response.transactionHash}", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            println("Error sending transaction: ${e.message}")
        }
    }

}

