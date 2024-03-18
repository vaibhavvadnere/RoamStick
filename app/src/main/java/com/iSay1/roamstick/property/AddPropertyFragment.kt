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
import com.iSay1.roamstick.data.model.response.PropertyTypesResponse
import com.iSay1.roamstick.data.model.response.StatesData
import com.iSay1.roamstick.data.model.response.StatesResponse
import com.iSay1.roamstick.data.model.response.UnitChargesTypeData
import com.iSay1.roamstick.data.model.response.UnitChargesTypesResponse
import com.iSay1.roamstick.data.model.response.UpdatePropertyResponse
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.databinding.AddPropertyFragmentBinding
import com.iSay1.roamstick.property.adapters.AmenitiesListAdapter
import com.iSay1.roamstick.property.adapters.CaretakerListAdapter
import com.iSay1.roamstick.property.adapters.ComplimentariesListAdapter
import com.iSay1.roamstick.property.adapters.PropertyTypesListAdapter
import com.iSay1.roamstick.property.adapters.StatesListAdapter
import com.iSay1.roamstick.property.adapters.UnitChargesTypesListAdapter
import com.iSay1.roamstick.property.viewModel.AddPropertyViewModel
import com.iSay1.roamstick.util.DTU
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class AddPropertyFragment : HomeBaseFragment(), MainActivity.onBackPressListener {

    private lateinit var addPropertyFragmentBinding: AddPropertyFragmentBinding

    private val addPropertyViewModel: AddPropertyViewModel by activityViewModels()

    private var statesResponse: StatesResponse? = null
    private var caretakerDetailsResponse: CaretakerDetailsResponse? = null

    private var selectedStates: StatesData? = null
    private var selectedCaretakerData: CaretakerData? = null

    private val statesListAdapter by lazy {
        StatesListAdapter(requireContext(), mutableListOf())
    }

    private val caretakerListAdapter by lazy {
        CaretakerListAdapter(requireContext(), mutableListOf())
    }

    private var propertyData: PropertyData? = null

    //enum Class to Handle all the button click
    enum class ViewOnClick {
        CREATE_PROPERTY, GET_STATES, GET_CARETAKER
    }

    //enum Class to Handle all the button click
    enum class UpdateEvent {
        HIDE_DIALOG, STATES_DATA, ADD_PROPERTY_FAILED, UPDATE_PROPERTY_FAILED, CARETAKER_DATA
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addPropertyFragmentBinding = AddPropertyFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { addPropertyViewModel.init(it) }

        addPropertyFragmentBinding.viewModel = addPropertyViewModel

        mActivity?.registerOnBackPress(this)


        return addPropertyFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            propertyData = bundle.getParcelable(Constants.PROPERTY_DATA)!!
            Log.e("propertyDataLogs", " : prop :" + Gson().toJson(propertyData))

            addPropertyFragmentBinding.edtPropertyName.setText(propertyData!!.Name)
            addPropertyFragmentBinding.edtAddressAreaStreet.setText(propertyData!!.AddressLine1)
            addPropertyFragmentBinding.edtPincode.setText(propertyData!!.Pincode)
            addPropertyFragmentBinding.edtLandmark.setText(propertyData!!.Landmark)
            addPropertyFragmentBinding.edtLocality.setText(propertyData!!.AddressLine3)
            addPropertyFragmentBinding.tvSelectState.text = propertyData!!.StateId
            addPropertyFragmentBinding.edtCityDistrictTown.setText(propertyData!!.CityDistrictTown)

            showDialog()

            addPropertyViewModel.getStates()

            addPropertyViewModel.getCaretakers()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    private fun showStatesDialog() {
        Log.e("deviceListLogsRem", ":" + Gson().toJson(addPropertyViewModel.statesResponse))

        val statesDialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        statesDialog.setContentView(R.layout.lay_select_option_dialog)
        val dialogButton = statesDialog.findViewById<View>(R.id.tv_done) as TextView
        val cancel = statesDialog.findViewById<TextView>(R.id.tv_cancel)
        val statesRecyclerView = statesDialog.findViewById<RecyclerView>(R.id.rl_list)
        val tvHeader = statesDialog.findViewById<TextView>(R.id.tv_header)

        tvHeader.text = mActivity?.resources?.getString(R.string.select_state)

        var tempSelectedStatesData: StatesData? = null

        if (selectedStates != null) {
            statesResponse?.data!!.forEachIndexed { position, stateData ->

                if (selectedStates!!.Id == stateData.Id) {
                    statesResponse?.data!![position].selected = true
                } else {
                    statesResponse?.data!![position].selected = false
                }
            }
        }



        statesRecyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        statesRecyclerView.adapter = statesListAdapter

        statesListAdapter.setData(statesResponse?.data!!)

        statesListAdapter.setClickListener(object : StatesListAdapter.CareAlertListener {

            override fun onItemClick(statesData: StatesData?, isChecked: Boolean) {

                Log.e("SelectedPropertyLog", ":" + Gson().toJson(statesData))

                tempSelectedStatesData = statesData

                Log.e(
                    "SelectedPropertyLogLog", "  : 1  :  " + Gson().toJson(tempSelectedStatesData)
                )

                statesResponse?.data!!.forEachIndexed { position, stateData ->

                    if (tempSelectedStatesData!!.Id == stateData.Id) {
                        statesResponse?.data!![position].selected = true
                    } else {
                        statesResponse?.data!![position].selected = false
                    }
                }

                statesListAdapter.setData(statesResponse?.data!!)

                statesListAdapter.notifyDataSetChanged()

                Log.e("selectedStatesListLogs", ":" + Gson().toJson(selectedStates))

            }
        })

        cancel.setOnClickListener {

            statesDialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {
            selectedStates = tempSelectedStatesData

            Log.e("SelectedStatesLogOnYes", ":" + Gson().toJson(selectedStates))

            statesDialog.dismiss()

            addPropertyFragmentBinding.tvSelectState.text = selectedStates?.StateName
        }

        statesDialog.show()
    }

    private fun showCaretakerDialog() {
        Log.e("deviceListLogsRem", ":" + Gson().toJson(addPropertyViewModel.caretakerDetailsResponse))

        val careTakerDialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        careTakerDialog.setContentView(R.layout.lay_select_option_dialog)
        val dialogButton = careTakerDialog.findViewById<View>(R.id.tv_done) as TextView
        val cancel = careTakerDialog.findViewById<TextView>(R.id.tv_cancel)
        val statesRecyclerView = careTakerDialog.findViewById<RecyclerView>(R.id.rl_list)
        val tvHeader = careTakerDialog.findViewById<TextView>(R.id.tv_header)

        tvHeader.text = mActivity?.resources?.getString(R.string.select_state)

        var tempSelectedCaretakerData: CaretakerData? = null

        if (selectedCaretakerData != null) {
            caretakerDetailsResponse?.data!!.forEachIndexed { position, stateData ->

                if (selectedCaretakerData!!.Id == stateData.Id) {
                    caretakerDetailsResponse?.data!![position].Selected = true
                } else {
                    caretakerDetailsResponse?.data!![position].Selected = false
                }
            }
        }

        statesRecyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        statesRecyclerView.adapter = caretakerListAdapter

        caretakerListAdapter.setData(caretakerDetailsResponse?.data!!)
        caretakerListAdapter.setViewType(Constants.SELECTIVE)

        caretakerListAdapter.setClickListener(object : CaretakerListAdapter.CaretakerListener {

            override fun onItemClick(caretakerData: CaretakerData?, isChecked: Boolean) {

                Log.e("SelectedPropertyLog", ":" + Gson().toJson(caretakerData))

                tempSelectedCaretakerData = caretakerData

                Log.e(
                    "SelectedPropertyLogLog", "  : 1  :  " + Gson().toJson(tempSelectedCaretakerData)
                )

                caretakerDetailsResponse?.data!!.forEachIndexed { position, stateData ->

                    if (tempSelectedCaretakerData!!.Id == stateData.Id) {
                        caretakerDetailsResponse?.data!![position].Selected = true
                    } else {
                        caretakerDetailsResponse?.data!![position].Selected = false
                    }
                }

                caretakerListAdapter.setData(caretakerDetailsResponse?.data!!)

                caretakerListAdapter.notifyDataSetChanged()

                Log.e("selectedStatesListLogs", ":" + Gson().toJson(selectedStates))

            }
        })

        cancel.setOnClickListener {

            careTakerDialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {
            selectedCaretakerData = tempSelectedCaretakerData

            Log.e("SelectedStatesLogOnYes", ":" + Gson().toJson(selectedCaretakerData))

            careTakerDialog.dismiss()

            addPropertyFragmentBinding.tvSelectCaretaker.text = selectedCaretakerData?.FirstName + " " + selectedCaretakerData?.LastName
        }

        careTakerDialog.show()
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

            ViewOnClick.GET_STATES -> {
                Log.e("addPropertyFragmentClick", ":clicked  GET_STATES:")
//                mActivity?.navController?.navigate(R.id.action_property_frag_add_property)

                if (statesResponse == null) {
                    showDialog()

                    addPropertyViewModel.getStates()
                } else {
                    showStatesDialog()
                }
            }

            ViewOnClick.GET_CARETAKER -> {
                if (caretakerDetailsResponse == null) {
                    showDialog()

                    addPropertyViewModel.getCaretakers()
                } else {
                    showCaretakerDialog()
                }
            }

            ViewOnClick.CREATE_PROPERTY -> {
                Log.e("createProperty", "  :  New  :")
                submitProperty()

            }

            else -> {

            }
        }
    }

    private fun submitProperty() {
        if (validateForm()) {

            val addPropertyRequest = AddPropertyRequest()

            addPropertyRequest.UserId = SharePrefRepo.getInstance().getString(Constants.USER_ID)
            addPropertyRequest.Name = addPropertyFragmentBinding.edtPropertyName.text.toString()
            addPropertyRequest.AddressLine1 = addPropertyFragmentBinding.edtAddressAreaStreet.text.toString()
            addPropertyRequest.CityDistrictTown = addPropertyFragmentBinding.edtCityDistrictTown.text.toString()
            addPropertyRequest.AddressLine3 = addPropertyFragmentBinding.edtLocality.text.toString()
            addPropertyRequest.Landmark = addPropertyFragmentBinding.edtLandmark.text.toString()
            addPropertyRequest.IsActive = true
            addPropertyRequest.Latitude = "12.241552"
            addPropertyRequest.Longitude = "13.41021"
            addPropertyRequest.Pincode = addPropertyFragmentBinding.edtPincode.text.toString()
            addPropertyRequest.StateId = selectedStates?.Id.toString().toInt()
            addPropertyRequest.CareTakerId = selectedCaretakerData?.Id.toString().toInt()



            showDialog()

            if (propertyData == null) {
                Log.e("submitProp", " : add :")


                addPropertyRequest.CreatedBy = SharePrefRepo.getInstance().getString(Constants.USER_ID).toInt()
                addPropertyRequest.CreatedAt = DTU().getCurrentDateTime(DTU().YYYY_MM_DD_HMS)

                addPropertyViewModel.addProperty(addPropertyRequest)
            } else {
                Log.e("submitProp", " : update :")

                addPropertyRequest.UpdatedBy = SharePrefRepo.getInstance().getString(Constants.USER_ID).toInt()
                addPropertyRequest.UpdatedAt = DTU().getCurrentDateTime(DTU().YYYY_MM_DD_HMS)

                addPropertyViewModel.updateProperty(propertyData!!.Id!!, addPropertyRequest)
            }
        }
    }

    private fun validateForm(): Boolean {
        if (addPropertyFragmentBinding.edtPropertyName.text.toString() == "") {
            addPropertyFragmentBinding.edtPropertyName.requestFocus()
            Toast.makeText(
                mActivity, "Please enter the name of property", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addPropertyFragmentBinding.edtAddressAreaStreet.text.toString() == "") {
            addPropertyFragmentBinding.edtAddressAreaStreet.requestFocus()
            Toast.makeText(
                mActivity, "Please enter the area or street address", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addPropertyFragmentBinding.edtCityDistrictTown.text.toString() == "") {

            addPropertyFragmentBinding.edtCityDistrictTown.requestFocus()

            Toast.makeText(
                mActivity, "Please enter the city/district/town", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (selectedStates == null) {
            Toast.makeText(
                mActivity, "Please select the state", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addPropertyFragmentBinding.edtPincode.text.toString() == "") {

            addPropertyFragmentBinding.edtPincode.requestFocus()

            Toast.makeText(
                mActivity, "Please enter the pincode", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (selectedCaretakerData == null) {
            Toast.makeText(
                mActivity, "Please select caretaker", Toast.LENGTH_SHORT
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

            UpdateEvent.STATES_DATA -> {
                Log.e(
                    "getComplimentaryLogs", ":clicked  aminitiesResponse:" + Gson().toJson(addPropertyViewModel.statesResponse)
                )
                hideDialog()

                addPropertyViewModel.statesResponse?.value.let { it ->
                    statesResponse = it!!
                }

                if (propertyData == null) {
                    showStatesDialog()
                } else {
                    statesResponse?.data!!.forEachIndexed { position, stateData ->

                        if (propertyData!!.StateId == stateData.Id) {
                            statesResponse?.data!![position].selected = true
                            selectedStates = stateData
                        } else {
                            statesResponse?.data!![position].selected = false
                        }
                    }
                }

                addPropertyFragmentBinding.tvSelectState.text = selectedStates?.StateName
            }

            UpdateEvent.CARETAKER_DATA -> {
                Log.e(
                    "getCaretakerLogs", ":clicked  caretakerDetailsResponse:" + Gson().toJson(addPropertyViewModel.caretakerDetailsResponse)
                )
                hideDialog()

                addPropertyViewModel.caretakerDetailsResponse?.value.let { it ->
                    caretakerDetailsResponse = it!!
                }

                if (propertyData == null) {
                    showCaretakerDialog()
                } else {

                    caretakerDetailsResponse?.data!!.forEachIndexed { position, careTakerData ->

                        if (propertyData!!.CareTakerDetails!![0].Id == careTakerData.Id) {
                            caretakerDetailsResponse?.data!![position].Selected = true

                            selectedCaretakerData = careTakerData

                        } else {
                            caretakerDetailsResponse?.data!![position].Selected = false
                        }
                    }

                    addPropertyFragmentBinding.tvSelectCaretaker.text = selectedCaretakerData?.FirstName + " " + selectedCaretakerData?.LastName

                }
            }

            UpdateEvent.ADD_PROPERTY_FAILED, UpdateEvent.UPDATE_PROPERTY_FAILED -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(addPropertyResponse: AddPropertyResponse) {
        hideDialog()
        Log.e("addPropResponse", ":" + Gson().toJson(addPropertyResponse))


        val bundle = Bundle()
        bundle.putInt(Constants.PROPERTY_ID, addPropertyResponse.data)
        mActivity?.navController!!.navigate(R.id.add_property_type_action, bundle)
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updatePropertyResponse: UpdatePropertyResponse) {
        hideDialog()

        mActivity?.navController!!.navigate(R.id.back_to_property_action)
    }

    override fun onBackPress() {
        Log.e("AddPropertyFragment", ":Backpressed:")
        mActivity?.navController!!.navigate(R.id.back_to_property_action)
    }
}

