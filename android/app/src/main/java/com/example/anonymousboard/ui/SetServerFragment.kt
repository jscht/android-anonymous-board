package com.example.anonymousboard.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.anonymousboard.R
import com.example.anonymousboard.api.JsServer
import com.example.anonymousboard.api.TaskServer
import com.example.anonymousboard.databinding.SetServerFragmentBinding

class SetServerFragment : Fragment() {
    private var _binding: SetServerFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= SetServerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setServerButton.setOnClickListener {
            Log.i("serverJusoData", binding.inputServerJuso.text.toString())

            //TaskServer().setIP(binding.inputServerJuso.text.toString())
            Log.i("server", JsServer.url)
            findNavController().navigate(R.id.action_setServerFragment_to_postsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}