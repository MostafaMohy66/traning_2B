package com.example.movieapp

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.movieapp.databinding.FragmentUpdateProductBinding
import com.example.movieapp.model.entity.Product
import com.example.movieapp.model.local.LocalRepositoryImp
import com.example.movieapp.model.local.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentUpdateProductBinding
    lateinit var localRepositoryImp: LocalRepositoryImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentUpdateProductBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var db = ProductDatabase.getInstance(requireContext())
        localRepositoryImp= LocalRepositoryImp(db)
        val productToUpdate = arguments?.getSerializable("product") as Product
        populateUI(productToUpdate)
        binding.btnSave2.setOnClickListener {
            val updatedProduct = getUpdatedProduct(productToUpdate)
            GlobalScope.launch(Dispatchers.IO){
                localRepositoryImp.updateProduct(updatedProduct)

            }
            Toast.makeText(requireContext(),"product updated Successfully", Toast.LENGTH_SHORT).show()
        }

    }
    private fun populateUI(product: Product) {
        binding.edProductName2.text = Editable.Factory.getInstance().newEditable(product.productName)
        binding.edQuantity2.text = Editable.Factory.getInstance().newEditable(product.quantity.toString())
        binding.edPrice2.text = Editable.Factory.getInstance().newEditable(product.productPrice.toString())
    }
    private fun getUpdatedProduct(oldProduct: Product): Product {
        val updatedName = binding.edProductName2.text.toString()
        val updatedQuantity = binding.edQuantity2.text.toString().toInt()
        val updatedPrice = binding.edPrice2.text.toString().toInt()


        return Product(0,updatedName, updatedQuantity, updatedPrice)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}