package com.example.movieapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movieapp.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var binding: FragmentLoginBinding

class LoginFragment : Fragment() {

    val userName:String="mostafa"
    val password:String="123456"
    var userEmail:String?=null
    var userPassword:String?=null
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
        // Inflate the layout for this fragment
        binding =FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        if (sharedPreferences != null) {
            userEmail = sharedPreferences.getString("email", "")
            val userName = Editable.Factory.getInstance().newEditable(userEmail)
            binding.edUsername.text = userName
            userPassword = sharedPreferences.getString("password", "")
            val password = Editable.Factory.getInstance().newEditable(userPassword)
            binding.edPassword.text = password
        }
        binding.btnLogin.setOnClickListener {
            if (binding.edUsername.text.toString().isNotEmpty() && binding.edPassword.text.toString().isNotEmpty()){
                if (binding.edUsername.text.toString().equals(userEmail) && binding.edPassword.text.toString().equals(userPassword)){
                    var action =LoginFragmentDirections.actionLoginFragmentToAddProductFragment()
                    findNavController().navigate(action)
                }
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}