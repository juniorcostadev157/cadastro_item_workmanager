package com.junior.projetomvvmcleanxml.presentation.principal.list_item_room_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import com.junior.projetomvvmcleanxml.R
import com.junior.projetomvvmcleanxml.databinding.FragmentListRoomBinding
import com.junior.projetomvvmcleanxml.presentation.principal.adapter.AdapterItem
import com.junior.projetomvvmcleanxml.presentation.principal.list_item_firebase_fragment.ListItemUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListRoomFragment : Fragment() {

    private var _binding: FragmentListRoomBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListRoomViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        val recyclerItems = binding.recylerItemsLocal
        recyclerItems.layoutManager = LinearLayoutManager(requireContext())
        recyclerItems.setHasFixedSize(true)
        recyclerItems.isNestedScrollingEnabled = true
        recyclerItems.clipToPadding = false
        val adapter = AdapterItem(requireContext(), mutableListOf(), false)
        recyclerItems.adapter =adapter


        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ListItemUiState.Loading -> showLoading()
                is ListItemUiState.Success -> {
                    hideLoading()
                    if (state.items.isEmpty()){
                        adapter.updateList(emptyList())


                    }else{
                        adapter.updateList(state.items)

                    }

                }
                is ListItemUiState.Empty -> {
                    hideLoading()
                    binding.recylerItemsLocal.visibility = View.GONE
                    binding.txtNoItems.visibility = View.VISIBLE
                }
                is ListItemUiState.Error -> hideLoading()
            }
        }
        switchSync()

    }


    private fun switchSync(){
        var internalChange = false

        viewModel.syncEnabled.observe(viewLifecycleOwner){isEnabled->
            internalChange = true
            binding.switchSync.isChecked = isEnabled
            internalChange = false

            if (isEnabled){
                observeStateWork()
            }else{
                binding.txtStatusSync.text = getString(R.string.sync_disabled)
            }
        }


        binding.switchSync.setOnCheckedChangeListener { _, isChecked->
            if (!internalChange) {
                viewModel.toggleSync(isChecked)
            }
        }
    }

    private fun showLoading() {
        binding.progressLoading.visibility = View.VISIBLE

    }

    private fun hideLoading() {
        binding.progressLoading.visibility = View.GONE

    }

    private fun observeStateWork(){
        viewModel.observeSyncWork(viewLifecycleOwner)
        viewModel.workState.observe(viewLifecycleOwner){workState->
            when(workState){
                WorkInfo.State.ENQUEUED ->showStatus("Agendado")
                WorkInfo.State.RUNNING -> showStatus("Sincronizando")
                WorkInfo.State.SUCCEEDED -> showStatus("Concluido")
                WorkInfo.State.FAILED -> showStatus("Falhou")
                WorkInfo.State.CANCELLED -> showStatus("Cancelado")
                else -> {}
            }

        }

    }

    private fun showStatus(msg: String){
        binding.txtStatusSync.text = msg
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}