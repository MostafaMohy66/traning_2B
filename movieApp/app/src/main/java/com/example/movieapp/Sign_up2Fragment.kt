package com.example.movieapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings.Global
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movieapp.databinding.FragmentSignUp2Binding
import com.example.movieapp.model.entity.User
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
 * Use the [Sign_up2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Sign_up2Fragment : Fragment() {
    lateinit var binding: FragmentSignUp2Binding
    lateinit var sharedPreferences:SharedPreferences
    lateinit var ed:SharedPreferences.Editor
    lateinit var laocalRepositoryImp :LocalRepositoryImp
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentSignUp2Binding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*
        var dp = ProductDatabase.getInstance(requireContext())
        laocalRepositoryImp = LocalRepositoryImp(dp)
        binding.btnSignup.setOnClickListener{
          var  user =User(0, binding.edName.text.toString(),binding.edEmail.text.toString(),binding.edPhone.text.toString(),binding.edPassword.text.toString())
            GlobalScope.launch(Dispatchers.IO){
                laocalRepositoryImp.addUser(user)
            }
        }*/
        val sharedPreferences = requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val bundle = Bundle()
        binding.btnSignup.setOnClickListener {
            if (binding.edName.text.isNotEmpty()&&binding.edEmail.text.isNotEmpty()&& binding.edPassword.text.isNotEmpty()&&binding.edPhone.text.isNotEmpty()){
                editor?.putString("userName",binding.edName.text.toString() )
                editor?.putString("email", binding.edEmail.text.toString())
                editor?.putString("password", binding.edPassword.text.toString())
                editor?.putString("phone", binding.edPhone.text.toString())

                editor?.apply()
                var action =Sign_up2FragmentDirections.actionSignUp2FragmentToLoginFragment()
                findNavController().navigate(action)
            }else{
                binding.txError.text="Please fill in all information"
            }
        }





        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Sign_up2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Sign_up2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}