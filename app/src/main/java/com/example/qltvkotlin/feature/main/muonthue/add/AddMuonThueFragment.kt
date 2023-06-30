package com.example.qltvkotlin.feature.main.muonthue.add

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCaller
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.feature.main.help.dialogcustom.DocGiaSelecDialog


class AddMuonThueFragment : BaseFragmentNavigation(R.layout.fragment_add_muon_thue) {
    private val binding by viewBinding { FragmentAddMuonThueBinding.bind(this) }
    private val viewmodel by viewModel<VM>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogCustom = DocGiaSelecDialog(requireActivity() as AppCompatActivity)
        binding.themdocgia.setOnClickListener{
            dialogCustom.showDialog{
                binding.themdocgia.text = it.nameKey
            }
        }
    }

    override fun clickEditAndSave(it: View) {
        TODO("Not yet implemented")
    }

    override fun getRun(): () -> Unit {
        return {}
    }

    override fun getCheck(): () -> Boolean {
        return {true}
    }

    class VM:ViewModel(){
        var newMuonThue = MutableLiveData<IMuonSach>()
    }
}