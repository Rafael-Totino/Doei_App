package com.example.doei.ui.product_register

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.doei.R
import com.example.doei.databinding.ProductRegisterFragmentBinding
import com.example.doei.domain.models.Product
import com.example.doei.ui.home.HomeViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductRegisterFragment : Fragment() {


    private var _binding: ProductRegisterFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var estadoTextInputLayout: TextInputLayout
    private lateinit var telefoneTextInputLayout: TextInputLayout


    private lateinit var estado: EditText
    private lateinit var cidade: EditText
    private lateinit var telefone: EditText

    private lateinit var titulo: EditText
    private lateinit var categoria: EditText
    private lateinit var detalhes: EditText

    private lateinit var imgViewer: ImageView
    private lateinit var botaoEscolherFoto: Button

    private lateinit var botaoAnunciar: Button

    private val viewModel: ProductRegisterViewModel by viewModels()

    companion object {
        fun newInstance() = ProductRegisterFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductRegisterFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initObservers()

        init()
        return root
    }

    private fun initObservers() {
        activity?.let {
            viewModel.handleProductAdded().observe(it) { productAdded ->
                if (productAdded) {
                    Toast.makeText(context, "Produto Cadastrado", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.navigation_home)
                } else {
                    Toast.makeText(context, "Houve um erro no cadastro", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
        setListeners()
    }

    fun init() {
        estadoTextInputLayout = binding.textInputLayoutState
        telefoneTextInputLayout = binding.textInputLayoutPhone

        estado = binding.editTextState
        cidade = binding.editTextCity
        telefone = binding.editTextPhone

        titulo = binding.editTextProductTitle
        categoria = binding.editTextProductCategory
        detalhes = binding.editTextProductDetails

        imgViewer = binding.imageViewDonationPic
        botaoEscolherFoto = binding.buttonChoosePhoto

        botaoAnunciar = binding.buttonAnnounceProduct

        //binding dos componentes com as vari??veis
    }


    fun enableAnnounceButton() {
        botaoAnunciar.setTextColor(Color.WHITE)
        botaoAnunciar.background.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                androidx.appcompat.R.color.material_deep_teal_500
            ), PorterDuff.Mode.MULTIPLY
        )
        //muda o estilo do bot??o para o usu??rio ver que est?? enabled

        botaoAnunciar.isEnabled = true  //libera o bot??o para toque
    }

    fun anunciarProduto() {
        var jsonProduto = pegarInfosProduto()

        viewModel.addProductToDatabase(jsonProduto)
    }

    private fun pegarInfosProduto(): Product {
        var produto: Product = Product()
        produto.name = titulo.text.toString()
        produto.local = "${cidade.text.toString()} - ${estado.text.toString()} "
        produto.category = categoria.text.toString()
        produto.description = detalhes.text.toString()
        produto.photo = fileImage.toString()
        produto.phone = telefone.text.toString()

        return produto
    }

    private fun checarInputs() {
        var check: Boolean = true

        if (estado.text.length != 2 ||
            cidade.text.length == 0 ||
            telefone.text.length < 14 ||
            titulo.text.length == 0 ||
            categoria.text.length == 0 ||
            detalhes.text.length == 0 ||
            fileImage.equals(Uri.EMPTY)
        ) {
            check = false
        }

        if (check) {
            enableAnnounceButton()
        }


    }

    val PICK_IMAGE = 1
    var fileImage: Uri = Uri.EMPTY //vari??vel para armazenar a URI da imagem escolhida pelo usu??rio

    //ao receber o resultado da atividade de escolha de imagem
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            try {
                imgViewer.setImageURI(data?.data)
                fileImage = data?.data ?: Uri.EMPTY
                checarInputs()
            } catch (e: Exception) {
                throw e
            }

        }
    }

    fun setListeners() {

        botaoAnunciar.setOnClickListener() {
            anunciarProduto()
        } //listener de click no bot??o

        botaoEscolherFoto.setOnClickListener {
            var intentImagePicker: Intent = Intent(Intent.ACTION_PICK)
            intentImagePicker.setType("image/*")
            startActivityForResult(intentImagePicker, PICK_IMAGE)
        }


        val focusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                checarInputs()
            } else {
                checarInputs()
            }
        }

        //restante: listeners de focus change nas EditTexts
        //estado.setOnFocusChangeListener(focusChangeListener)
        cidade.setOnFocusChangeListener(focusChangeListener)
        titulo.setOnFocusChangeListener(focusChangeListener)
        categoria.setOnFocusChangeListener(focusChangeListener)
        detalhes.setOnFocusChangeListener(focusChangeListener)

        val focusChangeListenerEstado = View.OnFocusChangeListener { view, b ->
            if (!b) {
                if (estado.text.length != 2) {
                    estadoTextInputLayout.error = "A sigla deve ter duas letras"
                } else {
                    estadoTextInputLayout.error = null
                    checarInputs()
                }
            }
        }

        val focusChangeListenerTelefone = View.OnFocusChangeListener { view, b ->
            if (!b) {
                //aplicando a m??scara de telefone ao input
                if (telefone.text.length < 10 ) {
                    telefoneTextInputLayout.error = "O telefone completo deve ter pelo menos 10 n??meros"
                } else {
                    if(telefone.text.length == 10)
                        telefone.text.insert(6,"-")

                    else if(telefone.text.length == 11)
                        telefone.text.insert(7,"-")

                    if(!telefone.text.contains("("))
                        telefone.text = telefone.text.insert(0,"(").insert(3,")").insert(4," ")

                    telefoneTextInputLayout.error = null
                    checarInputs()
                }
            }
        }

        estado.setOnFocusChangeListener(focusChangeListenerEstado)
        telefone.setOnFocusChangeListener(focusChangeListenerTelefone)

    }
}