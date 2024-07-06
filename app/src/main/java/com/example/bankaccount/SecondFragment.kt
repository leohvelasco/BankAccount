package com.example.bankaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.bankaccount.databinding.FragmentSecondBinding
import java.util.Timer

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /////////////////////////////////////////////////
    var accountBalance : Int = (0..1000).random()

    fun withdraw (amount : Int) : Int{
        accountBalance-= amount
        println("Value to withdraw $amount")
        println("Your new balance is $accountBalance")
        return accountBalance
    }

    fun deposit (amount : Int) : Int{
        try {
            accountBalance+= amount
            println("Value to deposit $amount")
            println("Your new balance is $accountBalance")
            return accountBalance
        }
        catch(e : NumberFormatException){return accountBalance}

    }
    /////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        //getting parameter from first fragment
        setFragmentResultListener("requestKey1") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            val result = bundle.getString("bundleKey1")

            println(result)
            when (result) {
                "1" -> {
                    binding.textviewSecond.append("\n" + "Amount available $$accountBalance.")
                    binding.textviewSecond.append("\nHow much you would like to debt?")
                    binding.buttonSecond.setOnClickListener {
                        withdraw(binding.option1.text.toString().toInt())
                        binding.textviewSecond.append("\nCongratulations! Get your money before leave.")
                        binding.textviewSecond.append("\n" + "Your new amount available is $$accountBalance.")
                        binding.option1.visibility = View.GONE
                        binding.buttonSecond.text = "Back"
                        binding.buttonSecond.setOnClickListener {
                            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                        }
                    }
                }
                "2" -> {
                    binding.textviewSecond.append("\n" + "Amount available $$accountBalance.")
                    binding.textviewSecond.append("\nHow much you would like to add?")
                    binding.buttonSecond.setOnClickListener {
                        deposit(binding.option1.text.toString().toInt())
                        binding.textviewSecond.append("\nCongratulations!")
                        binding.textviewSecond.append("\n" + "Your new amount available is $$accountBalance.")
                        binding.option1.visibility = View.GONE
                        binding.buttonSecond.text = "Back"
                        binding.buttonSecond.setOnClickListener {
                            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                        }
                    }
                }
                "3" -> {
                    binding.textviewSecond.append("\nChecking...")
                    binding.textviewSecond.append("\nAmount available $$accountBalance.")
                    binding.buttonSecond.text = "Back"
                    binding.buttonSecond.setOnClickListener {
                        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    }
                }
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}