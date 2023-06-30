package com.example.radius.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.radius.R
import com.example.radius.adapter.HomeAdapter
import com.example.radius.data.models.ExclusionModel
import com.example.radius.data.models.FacilitiesModel
import com.example.radius.data.models.FacilityModel
import com.example.radius.data.models.OptionModel
import com.example.radius.databinding.FragmentHomeBinding
import com.example.radius.helpers.Constants
import com.example.radius.helpers.ResultState
import com.example.radius.helpers.hideViewToBottom
import com.example.radius.helpers.makeGone
import com.example.radius.helpers.makeViewAppearFromBottom
import com.example.radius.helpers.makeVisible
import com.example.radius.viewModel.RadiusViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener, HomeAdapter.Listener {
    private var binding: FragmentHomeBinding? = null
    private var adapter: HomeAdapter? = null
    private val viewModel: RadiusViewModel by activityViewModels()
    private var model: FacilityModel? = null
    private var list: List<FacilityModel> = arrayListOf()
    private var excludedList: MutableList<String> = arrayListOf()
    private var exclusionList: List<List<ExclusionModel>>? = arrayListOf()
    private var eList: ArrayList<ExclusionModel> = arrayListOf()
    private var isValid = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
        adapter = HomeAdapter(this)
        binding?.apply {
            addReason.ivDismiss.setOnClickListener(this@HomeFragment)
            addReason.btnAddOption.setOnClickListener(this@HomeFragment)
            rvHome.adapter = adapter
            neHome.btnRetry.setOnClickListener(this@HomeFragment)
            addReason.etOptions.setOnItemClickListener { _, _, pos, _ ->
                val option = binding?.addReason?.etOptions?.text?.toString()?.trim()
                val arrayList = model?.options?.filter { it.name == option } ?: arrayListOf()
                val oModel = if (arrayList.isNotEmpty()) arrayList[0] else null
                if (excludedList.contains(oModel?.id)) {
                    Toast.makeText(
                        requireContext(),
                        "${oModel?.name} is not allowed in this combination",
                        Toast.LENGTH_SHORT
                    ).show()
                    isValid = false
                } else setExclusionModel(option)
            }
        }
        setObservers()
        getFacilities()
    }


    private fun getFacilities() {
        binding?.pfHome?.root?.makeVisible()
        viewModel.getFacilities()
    }

    private fun setObservers() {
        viewModel.getFacilityLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Success -> {
                    onDataFetchSuccess(it.data)
                }

                is ResultState.Loading -> {}
                is ResultState.Error -> {
                    onFetchFailure()
                }
            }
        }
    }

    private fun onFetchFailure() {
        binding?.pfHome?.root?.makeGone()
        showErrorFrame()
    }


    private fun onDataFetchSuccess(data: FacilitiesModel?) {
        data?.let {
            println(it.exclusions)
            exclusionList = it.exclusions
            updateFacilities(it.facilities)
            this.list = it.facilities ?: arrayListOf()
            bind(it.facilities)
        }
        binding?.pfHome?.root?.makeGone()
    }

    private fun updateFacilities(facilities: List<FacilityModel>?) {
        for (facilityModel: FacilityModel in facilities ?: emptyList()) {
            facilityModel.options?.forEach { it.facility_id = facilityModel.facility_id }
        }
    }

    private fun bind(list: List<FacilityModel>?) {
        adapter?.bindList(list)
    }

    private fun showSelectReasonView() {
        binding?.apply {
            addReason.etOptions.setText("")
            addReason.root.makeViewAppearFromBottom(requireView(), view)
            addReason.etOptions.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    getList()
                )
            )
        }
    }

    private fun getList(): List<String> {
        return mutableListOf<String>().apply {
            for (options: OptionModel in model?.options ?: emptyList()) {
                add(options.name ?: "")
            }
        }
    }

    private fun hideSelectReasonView() {
        binding?.apply {
            addReason.root.hideViewToBottom(requireView(), view)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivDismiss -> hideSelectReasonView()
            R.id.btnRetry -> retry()
            R.id.btnAddOption -> addOption()
        }
    }

    private fun retry() {
        hideErrorFrame()
        getFacilities()
    }

    private fun showErrorFrame() {
        binding?.neHome?.root.makeVisible()
    }

    private fun hideErrorFrame() {
        binding?.neHome?.root.makeGone()
    }

    private fun addOption() {
        if (!isValid) {
            Toast.makeText(
                requireContext(),
                "Selected combination is not allowed",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val option = binding?.addReason?.etOptions?.text?.toString()?.trim()
        model?.selectedOption = option
        adapter?.bindList(list)
        model?.selectedDraw = getDrawResource(option)
        hideSelectReasonView()
    }

    private fun getDrawResource(option: String?): Int {
        val list = model?.options?.filter { it.name == option } ?: arrayListOf()
        val model = if (list.isNotEmpty()) list[0] else null
        return when (model?.id) {
            Constants.ONE -> R.drawable.apartment
            Constants.TWO -> R.drawable.condo
            Constants.THREE -> R.drawable.boat
            Constants.FOUR -> R.drawable.land
            Constants.SIX -> R.drawable.rooms
            Constants.SEVEN -> R.drawable.no_room
            Constants.TEN -> R.drawable.swimming
            Constants.ELEVEN -> R.drawable.garden
            Constants.TWELVE -> R.drawable.garage
            else -> R.drawable.apartment
        }
    }

    private fun setExclusionModel(option: String?) {
        val list = model?.options?.filter { it.name == option } ?: arrayListOf()
        val model = if (list.isNotEmpty()) list[0] else null
        for (exList: List<ExclusionModel> in exclusionList ?: emptyList()) {
            for (exclusionModel: ExclusionModel in exList) {
                if (exclusionModel.options_id == model?.id) {
                    eList.addAll(exList)
                }
            }
        }
        val temporaryList =
            eList.filter { it.options_id != model?.id } as MutableList<ExclusionModel>
        modify(temporaryList)
        isValid = true
    }

    private fun modify(temporaryList: MutableList<ExclusionModel>) {
        for (model: ExclusionModel in temporaryList) {
            if (!excludedList.contains(model.options_id)) excludedList.add(model.options_id ?: "")
        }
    }

    override fun onOptionsClicked(model: FacilityModel) {
        this.model = model
        showSelectReasonView()
    }


}