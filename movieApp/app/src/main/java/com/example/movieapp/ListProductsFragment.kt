package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentListProductsBinding
import com.example.movieapp.model.entity.Product
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
 * Use the [ListProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListProductsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentListProductsBinding
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
        binding=FragmentListProductsBinding.inflate(layoutInflater,container,false)
        var db = ProductDatabase.getInstance(requireContext())
        localRepositoryImp= LocalRepositoryImp(db)
        /*
        getAllProducts()
        val adapter=ProductAdapter(productsList)
        binding.rvProducts.adapter=adapter
        binding.rvProducts.layoutManager= LinearLayoutManager(requireContext())
        getAllProducts()
        Log.i("mostafamohy","the size of list ${productsList.size}")
        val adapter=ProductAdapter(productsList)
        binding.rvProducts.adapter=adapter*/
        binding.rvProducts.layoutManager= LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
/*
        var db = ProductDatabase.getInstance(requireContext())
        localRepositoryImp= LocalRepositoryImp(db)

        getAllProducts()
        val adapter=ProductAdapter(productsList)
        binding.rvProducts.adapter=adapter
        binding.rvProducts.layoutManager= LinearLayoutManager(requireContext())*/
        var db = ProductDatabase.getInstance(requireContext())
        localRepositoryImp= LocalRepositoryImp(db)
        //getAllProducts()
        //productsList = listOf(Product(1,"mostaf",2,30))
        //productsList=getAllProducts()
        //Log.i("mostafa","${productsList.size}")
        //var new=getAllProducts()
        //Log.i("mostafa2","${new.size}")
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
             productsList = getAllProductsnew()
            Log.i("mostafain", "${productsList.size}")
            val adapter=ProductAdapter(productsList)
            binding.rvProducts.adapter=adapter
            binding.rvProducts.layoutManager= LinearLayoutManager(requireContext())
            binding.progressBar.visibility = View.GONE
        }
        Log.i("mostafaout", "${productsList.size}")

        /*val adapter=ProductAdapter(productsList)
        binding.rvProducts.adapter=adapter
        binding.rvProducts.layoutManager= LinearLayoutManager(requireContext())*/
        super.onViewCreated(view, savedInstanceState)
    }
    fun getAllProducts():List<Product>{
        var newlist= emptyList<Product>()
        GlobalScope.launch(Dispatchers.IO){
            var returnedProduct = async {
                localRepositoryImp.getProducts()
            }
            withContext(Dispatchers.Main){
                binding.progressBar.visibility =View.VISIBLE
                productsList = returnedProduct.await()
                newlist=returnedProduct.await()
                Log.i("mostafa2","${newlist.size}")
                binding.progressBar.visibility =View.GONE
            }
        }
        return newlist
    }
    suspend fun getAllProductsnew(): List<Product> {
        return withContext(Dispatchers.IO) {
            val productList = localRepositoryImp.getProducts()
            productList
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListProductsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}