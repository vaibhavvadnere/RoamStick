package com.iSay1.roamstick.careTaker

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
import com.iSay1.roamstick.careTaker.viewModel.AddCaretakerViewModel
import com.iSay1.roamstick.data.model.request.AddCareTakerRequest
import com.iSay1.roamstick.data.model.request.AddPropertyRequest
import com.iSay1.roamstick.data.model.response.AddPropertyResponse
import com.iSay1.roamstick.data.model.response.AmenitiesData
import com.iSay1.roamstick.data.model.response.BaseResponse
import com.iSay1.roamstick.data.model.response.CaretakerData
import com.iSay1.roamstick.data.model.response.ComplimentaryData
import com.iSay1.roamstick.data.model.response.PropertyData
import com.iSay1.roamstick.data.model.response.PropertyTypeData
import com.iSay1.roamstick.data.model.response.StatesData
import com.iSay1.roamstick.data.model.response.StatesResponse
import com.iSay1.roamstick.data.model.response.UnitChargesTypeData
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.databinding.AddCaretakerFragmentBinding
import com.iSay1.roamstick.property.AddPropertyFragment
import com.iSay1.roamstick.property.adapters.AmenitiesListAdapter
import com.iSay1.roamstick.property.adapters.ComplimentariesListAdapter
import com.iSay1.roamstick.property.adapters.PropertyTypesListAdapter
import com.iSay1.roamstick.property.adapters.StatesListAdapter
import com.iSay1.roamstick.property.adapters.UnitChargesTypesListAdapter
import com.iSay1.roamstick.util.DTU
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AddCaretakerFragment : HomeBaseFragment(), MainActivity.onBackPressListener {

    private lateinit var addCaretakerFragmentBinding: AddCaretakerFragmentBinding

    private val addCaretakerViewModel: AddCaretakerViewModel by activityViewModels()

    private var statesResponse: StatesResponse? = null
    private var selectedStates: StatesData? = null

    private val statesListAdapter by lazy {
        StatesListAdapter(requireContext(), mutableListOf())
    }

    //enum Class to Handle all the button click
    enum class ViewOnClick {
        ADD_CARETAKER, GET_STATES
    }

    //enum Class to Handle all the button click
    enum class UpdateEvent {
        HIDE_DIALOG, STATES_DATA, ADD_CARETAKER_FAILED
    }

    private var caretakerData: CaretakerData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addCaretakerFragmentBinding = AddCaretakerFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { addCaretakerViewModel.init(it) }

        addCaretakerFragmentBinding.viewModel = addCaretakerViewModel

        mActivity?.registerOnBackPress(this)

        return addCaretakerFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        if (bundle != null) {
            caretakerData = bundle.getParcelable(Constants.CARETAKER_DATA)!!
            Log.e("propertyDataLogs", " : prop :" + Gson().toJson(caretakerData))

            addCaretakerFragmentBinding.btnAddCaretaker.text = getString(R.string.update)

            addCaretakerFragmentBinding.edtFirstName.setText(caretakerData!!.FirstName)
            addCaretakerFragmentBinding.edtLastName.setText(caretakerData!!.LastName)
            addCaretakerFragmentBinding.edtEmail.setText(caretakerData!!.Email)
            addCaretakerFragmentBinding.edtPhoneNumber.setText(caretakerData!!.MobileNumber)

            addCaretakerFragmentBinding.edtAddressAreaStreet.setText(caretakerData!!.Address)
            addCaretakerFragmentBinding.edtCityDistrictTown.setText(caretakerData!!.CityDistrictTown)
            addCaretakerFragmentBinding.edtLandmark.setText(caretakerData!!.Landmark)
            addCaretakerFragmentBinding.edtPincode.setText(caretakerData!!.PinCode)
            addCaretakerFragmentBinding.edtLocality.setText(caretakerData!!.Locality)

//            addCaretakerFragmentBinding.tvSelectState.text = caretakerData!!.StateId

            showDialog()

            addCaretakerViewModel.getStates()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }


    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private fun showStatesDialog() {
        Log.e("deviceListLogsRem", ":" + Gson().toJson(addCaretakerViewModel.statesResponse))

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

            addCaretakerFragmentBinding.tvSelectState.text = selectedStates?.StateName
        }

        statesDialog.show()
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {

            ViewOnClick.ADD_CARETAKER -> {

                addCaretaker()
            }

            ViewOnClick.GET_STATES -> {

                showDialog()

                addCaretakerViewModel.getStates()
            }

            else -> {

            }
        }
    }

    private fun addCaretaker() {
        if (validateForm()) {

            val addCareTakerRequest = AddCareTakerRequest()

            addCareTakerRequest.FirstName = addCaretakerFragmentBinding.edtFirstName.text.toString()
            addCareTakerRequest.LastName = addCaretakerFragmentBinding.edtLastName.text.toString()
            addCareTakerRequest.Email = addCaretakerFragmentBinding.edtEmail.text.toString()
            addCareTakerRequest.MobileNumber = addCaretakerFragmentBinding.edtPhoneNumber.text.toString()
            addCareTakerRequest.CityDistrictTown = addCaretakerFragmentBinding.edtCityDistrictTown.text.toString()
            addCareTakerRequest.Address = addCaretakerFragmentBinding.edtAddressAreaStreet.text.toString()
            addCareTakerRequest.Landmark = addCaretakerFragmentBinding.edtLandmark.text.toString()
            addCareTakerRequest.Locality = addCaretakerFragmentBinding.edtLocality.text.toString()
            addCareTakerRequest.PinCode = addCaretakerFragmentBinding.edtPincode.text.toString()
            addCareTakerRequest.StateId = selectedStates?.Id
            addCareTakerRequest.CountryId = "1"

            showDialog()

            Log.e("addCTkrReqLog", ":" + Gson().toJson(addCareTakerRequest))

            if (caretakerData == null) {
                Log.e("submitProp", " : add :")


                addCareTakerRequest.CreatedBy = SharePrefRepo.getInstance().getString(Constants.USER_ID)
                addCareTakerRequest.CreatedAt = DTU().getCurrentDateTime(DTU().YYYY_MM_DD_HMS)

                addCaretakerViewModel.addCareTaker(addCareTakerRequest)
            } else {
                Log.e("submitProp", " : update :")

                addCareTakerRequest.UpdatedBy = SharePrefRepo.getInstance().getString(Constants.USER_ID)
                addCareTakerRequest.UpdatedAt = DTU().getCurrentDateTime(DTU().YYYY_MM_DD_HMS)

                addCaretakerViewModel.updateCaretaker(caretakerData!!.Id, addCareTakerRequest)
            }


        }
    }

    private fun validateForm(): Boolean {

        if (addCaretakerFragmentBinding.edtFirstName.text.toString() == "") {

            addCaretakerFragmentBinding.edtFirstName.requestFocus()
            Toast.makeText(
                mActivity, "Please enter first name", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addCaretakerFragmentBinding.edtFirstName.text.toString() == "") {

            addCaretakerFragmentBinding.edtLastName.requestFocus()
            Toast.makeText(
                mActivity, "Please enter last name", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addCaretakerFragmentBinding.edtEmail.text.toString() == "") {

            addCaretakerFragmentBinding.edtEmail.requestFocus()
            Toast.makeText(
                mActivity, "Please enter email", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addCaretakerFragmentBinding.edtPhoneNumber.text.toString() == "") {

            addCaretakerFragmentBinding.edtPhoneNumber.requestFocus()
            Toast.makeText(
                mActivity, "Please enter phone number", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addCaretakerFragmentBinding.edtAddressAreaStreet.text.toString() == "") {
            addCaretakerFragmentBinding.edtAddressAreaStreet.requestFocus()
            Toast.makeText(
                mActivity, "Please enter the area or street address", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addCaretakerFragmentBinding.edtCityDistrictTown.text.toString() == "") {

            addCaretakerFragmentBinding.edtCityDistrictTown.requestFocus()

            Toast.makeText(
                mActivity, "Please enter the city/district/town", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (selectedStates == null) {
            Toast.makeText(
                mActivity, "Please select the state", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (addCaretakerFragmentBinding.edtPincode.text.toString() == "") {

            addCaretakerFragmentBinding.edtPincode.requestFocus()

            Toast.makeText(
                mActivity, "Please enter the pincode", Toast.LENGTH_SHORT
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
                    "getComplimentaryLogs", ":clicked  aminitiesResponse:" + Gson().toJson(addCaretakerViewModel.statesResponse)
                )

                hideDialog()

                addCaretakerViewModel.statesResponse?.value.let { it ->
                    statesResponse = it!!
                }


                if (caretakerData == null) {
                    showStatesDialog()
                } else {
                    statesResponse?.data!!.forEachIndexed { position, stateData ->

                        if (caretakerData!!.StateId == stateData.Id) {
                            statesResponse?.data!![position].selected = true
                            selectedStates = stateData
                        } else {
                            statesResponse?.data!![position].selected = false
                        }
                    }
                    addCaretakerFragmentBinding.tvSelectState.text = selectedStates?.StateName
                }
            }


            UpdateEvent.ADD_CARETAKER_FAILED -> {
                hideDialog()

            }

            else -> {

            }
        }
    }


    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(baseResponse: BaseResponse) {

        hideDialog()

        Log.e("addCareResponse", ":" + Gson().toJson(baseResponse))
        mActivity?.navController!!.navigate(R.id.back_to_caretaker_action)
    }

    override fun onBackPress() {
        Log.e("AddCaretakerFragment", ":Backpressed:")
        mActivity?.navController!!.navigate(R.id.back_to_caretaker_action)
    }
}

