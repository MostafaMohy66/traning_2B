package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentAddProductBinding
import com.example.movieapp.databinding.FragmentListProductsBinding
import com.example.movieapp.model.entity.Product
import com.example.movieapp.model.local.LocalRepository
import com.example.movieapp.model.local.LocalRepositoryImp
import com.example.movieapp.model.local.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentAddProductBinding
    lateinit var bindingListP: FragmentListProductsBinding
    lateinit var localRepositoryImp: LocalRepositoryImp
     var productsList= emptyList<Product>()
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
        bindingListP=FragmentListProductsBinding.inflate(layoutInflater)
        binding=FragmentAddProductBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var db =ProductDatabase.getInstance(requireContext())
        localRepositoryImp= LocalRepositoryImp(db)
        binding.btnAdd.setOnClickListener {
            if(binding.edProductName.text.toString().isNotEmpty()&&binding.edQuantity.text.toString().isNotEmpty()&&binding.edPrice.text.toString().isNotEmpty()){

                var id =0
                var name =binding.edProductName.text.toString()
                var quantity = binding.edQuantity.text.toString().toInt()
                var price =binding.edPrice.text.toString().toInt()
                //var totalPrice=binding.edTotalPrice.text.toString().toDouble()
                var product=Product(id,name,quantity,price)
                GlobalScope.launch(Dispatchers.IO){
                    localRepositoryImp.addProduct(product)
                }
                Toast.makeText(requireContext(),"product added Successfully",Toast.LENGTH_SHORT).show()

            }

        }
        binding.btnDisplayProducts.setOnClickListener {

            /*var pro =getAllProducts()
            Log.i("mostafaIN","size ${pro.size}")
            val adapter=ProductAdapter(pro)
            bindingListP.rvProducts.adapter=adapter
            bindingListP.rvProducts.layoutManager= LinearLayoutManager(requireContext())
            adapter.notifyItemInserted(pro.size-1)*/
            var action =AddProductFragmentDirections.actionAddProductFragmentToListProductsFragment()
            findNavController().navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)
    }
    fun getAllProducts():List<Product>{
        GlobalScope.launch(Dispatchers.IO){
            var returnedProduct = async {
                localRepositoryImp.getProducts()

            }
            //Log.i("mostafa","first item ${returnedProduct}")
            withContext(Dispatchers.Main){
                bindingListP.progressBar.visibility =View.VISIBLE
                productsList = returnedProduct.await()
                bindingListP.progressBar.visibility =View.GONE
            }
            //Log.i("mostafa","first item ${returnedProduct}")
            //Log.i("mostafa2","size ${productsList[0]}")
            //Log.i("mostafa3","size ${productsList.size}")
        }
        return productsList
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}