package com.example.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ProductItemBinding
import com.example.movieapp.model.entity.Product
import com.example.movieapp.model.local.LocalRepositoryImp
import com.example.movieapp.model.local.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductAdapter(
    var products:List<Product>
):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    lateinit var localRepositoryImp: LocalRepositoryImp
    inner class ProductViewHolder(val binding:ProductItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var db = ProductDatabase.getInstance(parent.context)
        localRepositoryImp= LocalRepositoryImp(db)
        val binding =ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.tvName.text=products[position].productName
        holder.binding.tvQuantity.text=products[position].quantity.toString()
        holder.binding.tvPrice.text=products[position].productPrice.toString()
        holder.binding.tvTotalPrice.text=products[position].totalPrice.toString()

        holder.binding.btnUpdate.setOnClickListener {
            val productToUpdate = products[position]

            // Navigate to the update fragment
            val updateFragment = UpdateProductFragment()
            val bundle = Bundle()
            bundle.putSerializable("product", productToUpdate)
            updateFragment.arguments = bundle

            val transaction = (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, updateFragment)
            transaction.addToBackStack(null)
            transaction.commit()
            notifyDataSetChanged()
        }

        holder.binding.btnDelete.setOnClickListener {
            val context = holder.itemView.context
            val productToDelete = products[position]

            // Show a confirmation dialog
            AlertDialog.Builder(context)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes") { dialog, _ ->
                    // Handle delete operation
                    // For example, you can call a function to delete the product from the database
                    deleteProduct(productToDelete)

                    // Notify the adapter that the item has been removed
                    notifyItemRemoved(position)

                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
    private fun deleteProduct(product: Product) {
        GlobalScope.launch(Dispatchers.IO){
            localRepositoryImp.deleteProduct(product)
        }

    }


}