package com.example.doei.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.doei.MainActivity
import com.example.doei.R
import com.example.doei.databinding.ActivityMainBinding
import com.example.doei.databinding.FragmentSettingsBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    lateinit var themeSwitch: SwitchMaterial
    lateinit var buttonAccount: ImageButton

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        themeSwitch = binding.switchDarkMode
        themeSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                themeSwitch.setText("On")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeSwitch.setText("Off")
            }
        }

        buttonAccount = binding.buttonAccount
        buttonAccount.setOnClickListener {
            findNavController().navigate(R.id.navigation_account)
        }

        _binding?.btAddProduct?.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_settings_to_product_register)
        }
//        val textView: TextView = binding.textSettings
//        settingsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }


}