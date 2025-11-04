package com.junior.projetomvvmcleanxml.presentation.principal.list_item_room_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junior.projetomvvmcleanxml.databinding.FragmentCreateItemBinding

class CreateItemFragmentFake : Fragment() {

    private var _binding: FragmentCreateItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Simula o comportamento básico (sem ViewModel)
        binding.btnCriar.setOnClickListener {
            binding.progressLoadingItem.visibility = View.VISIBLE
            binding.btnCriar.isEnabled = false
            binding.btnCriar.alpha = 0.6f
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}