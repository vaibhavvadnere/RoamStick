package com.iSay1.roamstick.property

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.data.model.request.AddPropertyRequest
import com.iSay1.roamstick.data.model.request.AddPropertyTypesRequest
import com.iSay1.roamstick.data.model.response.AddPropertyResponse
import com.iSay1.roamstick.data.model.response.AmenitiesData
import com.iSay1.roamstick.data.model.response.AmenitiesResponse
import com.iSay1.roamstick.data.model.response.BaseResponse
import com.iSay1.roamstick.data.model.response.CaretakerData
import com.iSay1.roamstick.data.model.response.CaretakerDetailsResponse
import com.iSay1.roamstick.data.model.response.ComplimentariesResponse
import com.iSay1.roamstick.data.model.response.ComplimentaryData
import com.iSay1.roamstick.data.model.response.PropertyData
import com.iSay1.roamstick.data.model.response.PropertyTypeData
import com.iSay1.roamstick.data.model.response.PropertyTypesList
import com.iSay1.roamstick.data.model.response.PropertyTypesResponse
import com.iSay1.roamstick.data.model.response.StatesData
import com.iSay1.roamstick.data.model.response.StatesResponse
import com.iSay1.roamstick.data.model.response.UnitChargesTypeData
import com.iSay1.roamstick.data.model.response.UnitChargesTypesResponse
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.databinding.AddPropertyFragmentBinding
import com.iSay1.roamstick.databinding.AddPropertyTypeFragmentBinding
import com.iSay1.roamstick.property.adapters.AmenitiesListAdapter
import com.iSay1.roamstick.property.adapters.CaretakerListAdapter
import com.iSay1.roamstick.property.adapters.ComplimentariesListAdapter
import com.iSay1.roamstick.property.adapters.PropertyTypesListAdapter
import com.iSay1.roamstick.property.adapters.StatesListAdapter
import com.iSay1.roamstick.property.adapters.UnitChargesTypesListAdapter
import com.iSay1.roamstick.property.viewModel.AddPropertyTypeViewModel
import com.iSay1.roamstick.property.viewModel.AddPropertyViewModel
import com.iSay1.roamstick.util.DTU
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class AddPropertyTypeFragment : HomeBaseFragment(), MainActivity.onBackPressListener {

    private lateinit var addPropertyTypeFragmentBinding: AddPropertyTypeFragmentBinding

    private val addPropertyTypeViewModel: AddPropertyTypeViewModel by activityViewModels()

    private var amenitiesResponse: AmenitiesResponse? = null
    private var propertyTypesResponse: PropertyTypesResponse? = null
    private var unitChargesTypesResponse: UnitChargesTypesResponse? = null
    private var complimentariesResponse: ComplimentariesResponse? = null

    private var selectedPropertyType: PropertyTypeData? = null
    private var selectedUnitChargesTypeData: UnitChargesTypeData? = null
    private var selectedAmenitiesList: ArrayList<AmenitiesData> = arrayListOf()
    private var selectedComplimentariesList: ArrayList<ComplimentaryData> = arrayListOf()


    private var isShowAmenitiesDialog = true
    private var isShowComplimentriesDialog = true
    private var isShowPropertiesTypeDialog = true
    private var isShowUniteChargesTypeDialog = true


    private val amenitiesListAdapter by lazy {
        AmenitiesListAdapter(requireContext(), mutableListOf())
    }

    private val propertyTypesListAdapter by lazy {
        PropertyTypesListAdapter(requireContext(), mutableListOf())
    }

    private val unitChargesTypesListAdapter by lazy {
        UnitChargesTypesListAdapter(requireContext(), mutableListOf())
    }

    private val complimentariesListAdapter by lazy {
        ComplimentariesListAdapter(requireContext(), mutableListOf())
    }

    private var propertyType: PropertyTypesList? = null
    private var propertyId: Int? = null


    //enum Class to Handle all the button click
    enum class ViewOnClick {
        GET_PROPERTY_TYPES, GET_UNIT_CHARGES_TYPES, GET_AMENITIES, GET_COMPLIMENTARY, VALID_FROM_DATE, VALID_TO_DATE, ADD_PROPERTY_TYPE
    }

    //enum Class to Handle all the button click
    enum class UpdateEvent {
        PROPERTY_TYPE_DATA, UNIT_CHARGES_TYPES_DATA, HIDE_DIALOG, AMINITIES_DATA, COMPLIMENTARIES_DATA, ADD_PROPERTY_FAILED, ADD_PROPERTY_TYPES_FAILED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addPropertyTypeFragmentBinding = AddPropertyTypeFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { addPropertyTypeViewModel.init(it) }

        addPropertyTypeFragmentBinding.viewModel = addPropertyTypeViewModel

        mActivity?.registerOnBackPress(this)

        return addPropertyTypeFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {

            if (bundle.containsKey(Constants.PROPERTY_ID)) {
                propertyId = bundle.getInt(Constants.PROPERTY_ID)
                Log.e("propertyIdLogs", " : prop :" + propertyId)
            }

            if (bundle.containsKey(Constants.PROPERTY_TYPE)) {
                propertyType = bundle.getParcelable(Constants.PROPERTY_TYPE)
                Log.e("propertyIdLogs", " : prop :" + propertyId)


                addPropertyTypeFragmentBinding.tvSelectPropertyType.text = propertyType!!.TypeName
                addPropertyTypeFragmentBinding.tvSelectUnitCharges.text = propertyType!!.UnitChargeType

                addPropertyTypeFragmentBinding.edtNumberOfUnits.setText(propertyType!!.NumberOfUnits)
                addPropertyTypeFragmentBinding.edtRatePerUnit.setText(propertyType!!.ChargesPerUnit)
                addPropertyTypeFragmentBinding.edtValidFrom.setText(propertyType!!.ValidFrom)
                addPropertyTypeFragmentBinding.edtValidTo.setText(propertyType!!.ValidTo)

                if (propertyType!!.AmenitiesMappingData != null && propertyType!!.AmenitiesMappingData!!.isNotEmpty()) {
                    selectedAmenitiesList = propertyType!!.AmenitiesMappingData as ArrayList<AmenitiesData>
                    showSelectedAmenities()
                    isShowAmenitiesDialog = false
                }

                if (propertyType!!.ComplimentoryMappingData != null && propertyType!!.ComplimentoryMappingData!!.isNotEmpty()) {
                    selectedComplimentariesList = propertyType!!.ComplimentoryMappingData as ArrayList<ComplimentaryData>
                    showSelectedComplimentaries()
                    isShowComplimentriesDialog = false

                }

                showDialog()

                isShowPropertiesTypeDialog = false
                isShowUniteChargesTypeDialog = false

                addPropertyTypeViewModel.getPropertyTypes()
                addPropertyTypeViewModel.getUnitChargesTypes()
                addPropertyTypeViewModel.getAmenities()
                addPropertyTypeViewModel.getComplimentary()

//                selectedAmenitiesList = propertyType!!.AmenitiesMappingData
//                addPropertyTypeFragmentBinding.tvSelectAmenities.setText(propertyType!!.Ame)


            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    private fun showPropertyTypesDialog() {
        Log.e("deviceListLogsRem", ":" + Gson().toJson(addPropertyTypeViewModel.propertyTypesResponse))

        val dialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.lay_select_option_dialog)
        val dialogButton = dialog.findViewById<View>(R.id.tv_done) as TextView
        val cancel = dialog.findViewById<TextView>(R.id.tv_cancel)
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.rl_list)
        val tvHeader = dialog.findViewById<TextView>(R.id.tv_header)

        tvHeader.text = mActivity?.resources?.getString(R.string.select_property_type)

        var tempPropertyTypeData: PropertyTypeData? = null

        if (selectedPropertyType != null) {
            propertyTypesResponse?.data?.forEachIndexed { position, propertyType ->

                propertyTypesResponse?.data!![position].selected = selectedPropertyType?.Id == propertyType.Id
            }
        }



        recyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = propertyTypesListAdapter

        propertyTypesListAdapter.setData(propertyTypesResponse?.data!!)

        propertyTypesListAdapter.setClickListener(object : PropertyTypesListAdapter.CareAlertListener {

            override fun onItemClick(propertyTypeData: PropertyTypeData?, isChecked: Boolean) {

                Log.e("SelectedPropertyLog", ":" + Gson().toJson(propertyTypeData))

                tempPropertyTypeData = propertyTypeData

                Log.e(
                    "SelectedCareAlertLog", "  : 1  :  " + Gson().toJson(tempPropertyTypeData)
                )

                propertyTypesResponse?.data!!.forEachIndexed { position, propertyType ->


                    propertyTypesResponse?.data!![position].selected = tempPropertyTypeData!!.Id == propertyType.Id

                }

                propertyTypesListAdapter.setData(propertyTypesResponse?.data!!)

//                propertyTypesListAdapter.notifyDataSetChanged()
//                propertyTypesListAdapter.setData(propertyTypesResponse?.data)


            }


        })

        cancel.setOnClickListener {

            dialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {
            dialog.dismiss()

            selectedPropertyType = tempPropertyTypeData

            Log.e("selectedPropOnDone", ":" + Gson().toJson(selectedPropertyType))

            addPropertyTypeFragmentBinding.tvSelectPropertyType.text = selectedPropertyType?.TypeName

        }

        dialog.show()
    }

    private fun showUnitChargesTypesDialog() {
        Log.e(
            "deviceListLogsRem", ":" + Gson().toJson(addPropertyTypeViewModel.unitChargesTypesResponse)
        )

        val unitChargesTypesDialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        unitChargesTypesDialog.setContentView(R.layout.lay_select_option_dialog)
        val dialogButton = unitChargesTypesDialog.findViewById<View>(R.id.tv_done) as TextView
        val cancel = unitChargesTypesDialog.findViewById<TextView>(R.id.tv_cancel)
        val recyclerView = unitChargesTypesDialog.findViewById<RecyclerView>(R.id.rl_list)
        val tvHeader = unitChargesTypesDialog.findViewById<TextView>(R.id.tv_header)

        tvHeader.text = mActivity?.resources?.getString(R.string.select_unit_charges_type)

        var tempUnitChargesTypeData: UnitChargesTypeData? = null

        if (selectedUnitChargesTypeData != null) {
            unitChargesTypesResponse?.data?.forEachIndexed { position, unitChargesType ->

                unitChargesTypesResponse?.data!![position].selected = selectedUnitChargesTypeData?.Id == unitChargesType.Id
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = unitChargesTypesListAdapter

        unitChargesTypesListAdapter.setData(unitChargesTypesResponse?.data!!)

        unitChargesTypesListAdapter.setClickListener(object : UnitChargesTypesListAdapter.CareAlertListener {

            override fun onItemClick(
                unitChargesTypeData: UnitChargesTypeData?, isChecked: Boolean
            ) {

                Log.e("SelectedPropertyLog", ":" + Gson().toJson(unitChargesTypeData))

                tempUnitChargesTypeData = unitChargesTypeData

                Log.e(
                    "SelectedCareAlertLog", "  : 1  :  " + Gson().toJson(tempUnitChargesTypeData)
                )

                unitChargesTypesResponse?.data!!.forEachIndexed { position, propertyType ->

                    unitChargesTypesResponse?.data!![position].selected = tempUnitChargesTypeData!!.Id == propertyType.Id
                }

                unitChargesTypesListAdapter.setData(unitChargesTypesResponse?.data!!)
            }
        })

        cancel.setOnClickListener {

            unitChargesTypesDialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {
            unitChargesTypesDialog.dismiss()

            selectedUnitChargesTypeData = tempUnitChargesTypeData

            Log.e("selectedPropOnDone", ":" + Gson().toJson(selectedUnitChargesTypeData))

            addPropertyTypeFragmentBinding.tvSelectUnitCharges.text = selectedUnitChargesTypeData?.Type

        }

        unitChargesTypesDialog.show()
    }

    private fun showAmenitiesDialog() {
        Log.e("deviceListLogsRem", ":" + Gson().toJson(addPropertyTypeViewModel.propertyTypesResponse))

        val amenitiesDialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        amenitiesDialog.setContentView(R.layout.lay_select_option_dialog)

        val cancel = amenitiesDialog.findViewById<TextView>(R.id.tv_cancel)
        val tvHeader = amenitiesDialog.findViewById<TextView>(R.id.tv_header)
        val dialogButton = amenitiesDialog.findViewById<View>(R.id.tv_done) as TextView
        val amenitiesRecyclerView = amenitiesDialog.findViewById<RecyclerView>(R.id.rl_list)

        tvHeader.text = mActivity?.resources?.getString(R.string.select_amenities)

        amenitiesRecyclerView.layoutManager = GridLayoutManager(mActivity, 1, GridLayoutManager.VERTICAL, false);
        amenitiesListAdapter.setViewType(Constants.VERTICAL)
        amenitiesRecyclerView.adapter = amenitiesListAdapter

        amenitiesListAdapter.setData(amenitiesResponse?.data!!)

        setAmenitiesListOnClick()

        cancel.setOnClickListener {

            amenitiesDialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {

            Log.e("SelectedCareAlertLogOnYes", ":")

            amenitiesDialog.dismiss()

            showSelectedAmenities()

        }

        amenitiesDialog.show()
    }

    private fun setAmenitiesListOnClick() {

        amenitiesListAdapter.setClickListener(object : AmenitiesListAdapter.CareAlertListener {

            override fun onItemClick(amenitiesData: AmenitiesData?, isChecked: Boolean) {

                Log.e("SelectedPropertyLog", ":" + Gson().toJson(amenitiesData))

                if (isChecked) {
                    if (!selectedAmenitiesList.contains(amenitiesData)) amenitiesData?.let {
                        selectedAmenitiesList.add(
                            it
                        )
                    }
                } else {
                    if (selectedAmenitiesList.contains(amenitiesData)) amenitiesData?.let {
                        selectedAmenitiesList.remove(
                            it
                        )
                    }
                }

                amenitiesListAdapter.notifyDataSetChanged()

                Log.e("selectedPropertyListLogs", ":" + Gson().toJson(selectedAmenitiesList))

            }

            override fun onItemRemove(amenitiesData: AmenitiesData?) {

                Log.e(
                    "selectedPropertyListLogs", " : on Remove :" + Gson().toJson(selectedAmenitiesList)
                )

                selectedAmenitiesList.forEachIndexed { position, amenity ->

                    Log.e(
                        "selePropListLogs", " : on Remove :" + Gson().toJson(amenity)
                    )

                    if (amenitiesData == amenity) {
                        selectedAmenitiesList.removeAt(position)
                    }
                }

                amenitiesListAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun showSelectedAmenities() {

        if (selectedAmenitiesList.isNotEmpty()) {

            addPropertyTypeFragmentBinding.tvSelectAmenities.visibility = View.GONE
            addPropertyTypeFragmentBinding.rvAmenitiesList.visibility = View.VISIBLE

            addPropertyTypeFragmentBinding.rvAmenitiesList.layoutManager = GridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
            addPropertyTypeFragmentBinding.rvAmenitiesList.adapter = amenitiesListAdapter

            amenitiesListAdapter.setViewType(Constants.HORIZONTAL)

            amenitiesListAdapter.setData(selectedAmenitiesList)

            setAmenitiesListOnClick()
        } else {
            addPropertyTypeFragmentBinding.tvSelectAmenities.visibility = View.VISIBLE
            addPropertyTypeFragmentBinding.rvAmenitiesList.visibility = View.GONE
        }
    }

    private fun showComplimentariesDialog() {
        Log.e("deviceListLogsRem", ":" + Gson().toJson(addPropertyTypeViewModel.propertyTypesResponse))

        val complimentariesDialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        complimentariesDialog.setContentView(R.layout.lay_select_option_dialog)
        val dialogButton = complimentariesDialog.findViewById<View>(R.id.tv_done) as TextView
        val cancel = complimentariesDialog.findViewById<TextView>(R.id.tv_cancel)
        val complimentariesRecyclerView = complimentariesDialog.findViewById<RecyclerView>(R.id.rl_list)
        val tvHeader = complimentariesDialog.findViewById<TextView>(R.id.tv_header)

        tvHeader.text = mActivity?.resources?.getString(R.string.select_complimentary)


        complimentariesRecyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        complimentariesRecyclerView.adapter = complimentariesListAdapter

        complimentariesListAdapter.setViewType(Constants.VERTICAL)
        complimentariesListAdapter.setData(complimentariesResponse?.data!!)

        setComplimentariesListOnClick()

        cancel.setOnClickListener {

            complimentariesDialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {

            Log.e("SelectedcomplOnYes", ":" + Gson().toJson(selectedComplimentariesList))

            complimentariesDialog.dismiss()

            showSelectedComplimentaries()
        }

        complimentariesDialog.show()
    }

    private fun setComplimentariesListOnClick() {

        complimentariesListAdapter.setClickListener(object : ComplimentariesListAdapter.CareAlertListener {

            override fun onItemClick(complimentaryData: ComplimentaryData?, isChecked: Boolean) {

                Log.e("SelectedComplimentaryLog", ":" + Gson().toJson(complimentaryData))

                if (isChecked) {
                    if (!selectedComplimentariesList.contains(complimentaryData)) complimentaryData?.let {
                        selectedComplimentariesList.add(
                            it
                        )
                    }
                } else {
                    if (selectedComplimentariesList.contains(complimentaryData)) complimentaryData?.let {
                        selectedComplimentariesList.remove(
                            it
                        )
                    }
                }

                complimentariesListAdapter.notifyDataSetChanged()

                Log.e("selectedcompListLogs", ":" + Gson().toJson(selectedComplimentariesList))

            }

            override fun onItemRemove(complimentaryData: ComplimentaryData?) {

                Log.e(
                    "selectedcompListLogs", " : on Remove :" + Gson().toJson(selectedComplimentariesList)
                )

                selectedComplimentariesList.forEachIndexed { position, complimentary ->

                    Log.e(
                        "seleCompListLogs", " : on Remove :" + Gson().toJson(complimentary)
                    )

                    if (complimentaryData == complimentary) {
                        selectedComplimentariesList.removeAt(position)
                    }
                }

                complimentariesListAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun showSelectedComplimentaries() {

        if (selectedComplimentariesList.isNotEmpty()) {

            addPropertyTypeFragmentBinding.tvSelectComplimentary.visibility = View.GONE
            addPropertyTypeFragmentBinding.rvComplimentaryList.visibility = View.VISIBLE

            addPropertyTypeFragmentBinding.rvComplimentaryList.layoutManager = GridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
            addPropertyTypeFragmentBinding.rvComplimentaryList.adapter = complimentariesListAdapter

            complimentariesListAdapter.setViewType(Constants.HORIZONTAL)

            complimentariesListAdapter.setData(selectedComplimentariesList)

            setComplimentariesListOnClick()
        } else {
            addPropertyTypeFragmentBinding.tvSelectComplimentary.visibility = View.VISIBLE
            addPropertyTypeFragmentBinding.rvComplimentaryList.visibility = View.GONE
        }
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {

            ViewOnClick.GET_PROPERTY_TYPES -> {
                Log.e("addPropertyFragmentClick", ":clicked  ADD_PROPERTY:")
//                mActivity?.navController?.navigate(R.id.action_property_frag_add_property)

                if (propertyTypesResponse == null) {
                    isShowPropertiesTypeDialog = true
                    showDialog()

                    addPropertyTypeViewModel.getPropertyTypes()
                } else {
                    showPropertyTypesDialog()
                }
            }

            ViewOnClick.GET_UNIT_CHARGES_TYPES -> {
                Log.e("addPropertyFragmentClick", ":clicked  GET_UNIT_CHARGES_TYPES:")
//                mActivity?.navController?.navigate(R.id.action_property_frag_add_property)

                isShowUniteChargesTypeDialog = true

                if (unitChargesTypesResponse == null) {
                    showDialog()

                    addPropertyTypeViewModel.getUnitChargesTypes()
                } else {
                    showUnitChargesTypesDialog()
                }
            }

            ViewOnClick.GET_AMENITIES -> {
                Log.e("addPropertyFragmentClick", ":clicked  GET_AMENITIES:")
//                mActivity?.navController?.navigate(R.id.action_property_frag_add_property)

                isShowAmenitiesDialog = true

                if (amenitiesResponse == null) {
                    showDialog()

                    addPropertyTypeViewModel.getAmenities()
                } else {
                    showAmenitiesDialog()
                }
            }

            ViewOnClick.GET_COMPLIMENTARY -> {
                Log.e("addPropertyFragmentClick", ":clicked  GET_COMPLIMENTARY:")
//                mActivity?.navController?.navigate(R.id.action_property_frag_add_property)
                isShowComplimentriesDialog = true

                if (complimentariesResponse == null) {
                    showDialog()

                    addPropertyTypeViewModel.getComplimentary()
                } else {
                    showComplimentariesDialog()
                }
            }

            ViewOnClick.VALID_FROM_DATE -> {

                DTU().showDatePickerDialog(
                    context, DTU().FLAG_OLD_AND_NEW, addPropertyTypeFragmentBinding.edtValidFrom
                )
            }

            ViewOnClick.VALID_TO_DATE -> {

                DTU().showDatePickerDialog(
                    context, DTU().FLAG_OLD_AND_NEW, addPropertyTypeFragmentBinding.edtValidTo
                )
            }


            ViewOnClick.ADD_PROPERTY_TYPE -> {

                if (validateForm()) {
                    showDialog()
                    addPropertyType(propertyId = propertyId.toString())
                }
            }


            else -> {

            }
        }
    }

    private fun validateForm(): Boolean {
        if (selectedPropertyType == null) {
            Toast.makeText(
                mActivity, getString(R.string.please_select_property_type), Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addPropertyTypeFragmentBinding.edtNumberOfUnits.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please enter number of units", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addPropertyTypeFragmentBinding.edtRatePerUnit.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please enter rate per unit", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addPropertyTypeFragmentBinding.edtValidFrom.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please select valid from date", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addPropertyTypeFragmentBinding.edtValidTo.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please select valid to date", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (selectedAmenitiesList.isEmpty()) {

            Toast.makeText(
                mActivity, "Please select at least one amenities", Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.HIDE_DIALOG -> {
                hideDialog()

            }

            UpdateEvent.PROPERTY_TYPE_DATA -> {
                Log.e(
                    "getPropertyTypesLogs", ":clicked  PROPERTY_TYPE_DATA:" + Gson().toJson(addPropertyTypeViewModel.propertyTypesResponse)
                )

                hideDialog()

                if (isShowPropertiesTypeDialog) {
                    addPropertyTypeViewModel.propertyTypesResponse?.value.let { it ->
                        propertyTypesResponse = it!!
                    }

                    showPropertyTypesDialog()
                }
            }

            UpdateEvent.UNIT_CHARGES_TYPES_DATA -> {
                Log.e(
                    "getUnitChargesTypesLogs", ":clicked  UNIT_CHARGES_TYPES_DATA:" + Gson().toJson(addPropertyTypeViewModel.unitChargesTypesResponse)
                )

                hideDialog()
                if (isShowUniteChargesTypeDialog) {

                    addPropertyTypeViewModel.unitChargesTypesResponse?.value.let { it ->
                        unitChargesTypesResponse = it!!
                    }

                    showUnitChargesTypesDialog()
                }
            }

            UpdateEvent.AMINITIES_DATA -> {
                Log.e(
                    "getAminitiesLogs", ":clicked  aminitiesResponse:" + Gson().toJson(addPropertyTypeViewModel.amenitiesResponse)
                )
                hideDialog()
                if (isShowAmenitiesDialog) {

                    addPropertyTypeViewModel.amenitiesResponse?.value.let { it ->
                        amenitiesResponse = it!!
                    }

                    showAmenitiesDialog()
                }
            }

            UpdateEvent.COMPLIMENTARIES_DATA -> {
                Log.e(
                    "getComplimentaryLogs", ":clicked  aminitiesResponse:" + Gson().toJson(addPropertyTypeViewModel.complimentariesResponse)
                )
                hideDialog()
                if (isShowComplimentriesDialog) {

                    addPropertyTypeViewModel.complimentariesResponse?.value.let { it ->
                        complimentariesResponse = it!!
                    }

                    showComplimentariesDialog()
                }
            }

            UpdateEvent.ADD_PROPERTY_FAILED -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

    /*@SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(addPropertyResponse: AddPropertyResponse) {

//        hideDialog()
        Log.e("addPropResponse", ":" + Gson().toJson(addPropertyResponse))

        addPropertyType(propertyId = addPropertyResponse.data.toString())
    }*/

    @SuppressLint("NewApi")
    private fun addPropertyType(propertyId: String?) {
        val addPropertyTypesRequest = AddPropertyTypesRequest()
        addPropertyTypesRequest.ValidTo = DTU().get_yyyy_mm_dd(addPropertyTypeFragmentBinding.edtValidTo.text.toString())
        addPropertyTypesRequest.CreatedBy = SharePrefRepo.getInstance().getString(Constants.USER_ID).toInt()
        addPropertyTypesRequest.CreatedAt = DTU().getCurrentDateTime(DTU().YYYY_MM_DD_HMS)
        addPropertyTypesRequest.PropertyId = propertyId?.toInt()
        addPropertyTypesRequest.PropertyTypeId = selectedPropertyType?.Id
        addPropertyTypesRequest.ValidFrom = DTU().get_yyyy_mm_dd(addPropertyTypeFragmentBinding.edtValidFrom.text.toString())
        addPropertyTypesRequest.NumberOfUnits = addPropertyTypeFragmentBinding.edtNumberOfUnits.text.toString().toInt()
        addPropertyTypesRequest.ChargesPerUnit = addPropertyTypeFragmentBinding.edtRatePerUnit.text.toString()
        addPropertyTypesRequest.ChargesUnitTypeId = selectedUnitChargesTypeData?.Id!!.toInt()

        addPropertyTypesRequest.aminities = selectedAmenitiesList
        addPropertyTypesRequest.complimentaries = selectedComplimentariesList

        addPropertyTypeViewModel.addPropertyType(addPropertyTypesRequest)

    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(baseResponse: BaseResponse) {
        Log.e("addPropTypeResponse", ":" + Gson().toJson(baseResponse))

        hideDialog()
        mActivity?.navController!!.navigate(R.id.back_to_property_types_action)
    }


    override fun onBackPress() {
        Log.e("AddPropertyFragment", ":Backpressed:")
        mActivity?.navController!!.navigate(R.id.back_to_property_types_action)
    }
}

