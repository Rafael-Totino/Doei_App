package com.example.doei.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.doei.R
import com.example.doei.databinding.FragmentHomeBinding
import com.example.doei.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var loginButton: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loginButton = binding.login
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}