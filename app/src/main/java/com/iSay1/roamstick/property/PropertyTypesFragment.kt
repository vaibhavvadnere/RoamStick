package com.iSay1.roamstick.property

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.data.model.request.DeletePropertyTypesRequest
import com.iSay1.roamstick.data.model.response.PropertyData
import com.iSay1.roamstick.data.model.response.PropertyTypesDetailsResponse
import com.iSay1.roamstick.data.model.response.PropertyTypesList
import com.iSay1.roamstick.databinding.PropertyTypesFragmentBinding
import com.iSay1.roamstick.property.adapters.AllPropertyTypesListAdapter
import com.iSay1.roamstick.property.bottomSheets.PropertyTypesBottomSheetFragment
import com.iSay1.roamstick.property.bottomSheets.PropertyTypesBottomSheetFragment.PropertyTypesBottomSheetListener
import com.iSay1.roamstick.property.viewModel.PropertyTypesViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PropertyTypesFragment : HomeBaseFragment() {

    private lateinit var propertyTypesFragmentBinding: PropertyTypesFragmentBinding

    private val propertyTypesViewModel: PropertyTypesViewModel by activityViewModels()

    private val allPropertyTypesListAdapter by lazy {
        AllPropertyTypesListAdapter(requireContext(), mutableListOf())
    }

    private var propertyData: PropertyData? = null

    private var propertyTypesDetailsResponse: PropertyTypesDetailsResponse? = null

    private var propertyTypesList: List<PropertyTypesList>? = null

    //Class to Handle all the button click
    enum class ViewOnClick {
        ADD_PROPERTY_TYPE, SIGN_UP, SCAN_QR_BARCODE,
    }

    //Class to Handle all update events
    enum class UpdateEvent {
        PROPERTY_TYPES_DATA, HIDE_DIALOG, DELETE_PROPERTY_TYPES_SUCCESS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        propertyTypesFragmentBinding = PropertyTypesFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { propertyTypesViewModel.init(it) }

        propertyTypesFragmentBinding.viewModel = propertyTypesViewModel

        return propertyTypesFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            propertyData = bundle.getParcelable(Constants.PROPERTY_DATA)!!

            Log.e("propertyDataTypesLog", ":" + Gson().toJson(propertyData))
            if (propertyData!!.PropertyType != null && propertyData!!.PropertyType!!.isNotEmpty()) {

                propertyTypesList = propertyData!!.PropertyType
                showPropertyTypesList()
            }
        }
    }

    override fun onResume() {
        super.onResume()
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

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {

            ViewOnClick.ADD_PROPERTY_TYPE -> {
                Log.e("onInClick", ":clicked  ADD_PROPERTY:")
//                mActivity?.navController?.navigate(R.id.action_property_frag_add_property)

                val bundle = Bundle()
                bundle.putInt(Constants.PROPERTY_ID, propertyData!!.Id!!.toInt())
                mActivity?.navController!!.navigate(R.id.add_property_type_property_frag_action, bundle)
            }

            else -> {

            }
        }
    }

    private fun showPropertyTypesList() {
        propertyTypesFragmentBinding.rvPropertiesTypesList.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        propertyTypesFragmentBinding.rvPropertiesTypesList.adapter = allPropertyTypesListAdapter

//        allPropertyTypesListAdapter.setData(propertyData?.PropertyType!!)
        allPropertyTypesListAdapter.setData(propertyTypesList!!)

        allPropertyTypesListAdapter.setClickListener(object : AllPropertyTypesListAdapter.PropertyTypesListener {
            override fun onItemClick(mPropertyType: PropertyTypesList?) {

                showOptionsBottomSheet(mPropertyType)
            }
        })
    }

    private fun showOptionsBottomSheet(mPropertyData: PropertyTypesList?) {

        val bottomSheet: PropertyTypesBottomSheetFragment
        bottomSheet = PropertyTypesBottomSheetFragment()
        bottomSheet.setPropertyData(mPropertyData)
        bottomSheet.isCancelable = true
        bottomSheet.show(getChildFragmentManager(), Constants.MODAL_BOTTOM_SHEET)

        bottomSheet.registerListener(object : PropertyTypesBottomSheetListener {

            override fun onDeleteClicked(propertyType: PropertyTypesList?) {
                bottomSheet.dismiss()

                showDeletePopUp(propertyType)
            }

            override fun onEditPropertyType(propertyType: PropertyTypesList?) {
                bottomSheet.dismiss()

                val bundle = Bundle()
                bundle.putParcelable(Constants.PROPERTY_TYPE, propertyType)
                bundle.putInt(Constants.PROPERTY_ID, propertyData!!.Id!!.toInt())
                mActivity?.navController?.navigate(R.id.add_property_type_property_frag_action, bundle)
            }

            override fun onPropertyImages(propertyType: PropertyTypesList?) {
                bottomSheet.dismiss()

                val bundle = Bundle()
                bundle.putParcelable(Constants.PROPERTY_TYPE, propertyType)
                bundle.putInt(Constants.PROPERTY_ID, propertyData!!.Id!!.toInt())
                mActivity?.navController?.navigate(R.id.add_property_type_images_frag_action, bundle)
            }
        })
    }

    private fun showDeletePopUp(propertyType: PropertyTypesList?) {
        val dialog = Dialog(mActivity!!, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)

        dialog.setContentView(R.layout.lay_delete_device_popup)

        val tvDeleteHeader = dialog.findViewById<TextView>(R.id.tv_delete_header)
        val tvDeleteMessage = dialog.findViewById<TextView>(R.id.tv_delete_message)
        val btnCancel = dialog.findViewById<TextView>(R.id.btn_cancel)
        val btnDelete = dialog.findViewById<TextView>(R.id.btn_delete)


        val popupHeaderText: String = String.format(getString(R.string.delete_header), getString(R.string.property_type))
        tvDeleteHeader.text = popupHeaderText

        val popupMessageText: String = String.format(getString(R.string.are_you_sure_you_want_to_delete), propertyType!!.TypeName)
        tvDeleteMessage.text = popupMessageText

        btnDelete.setOnClickListener { v: View? ->
            dialog.dismiss()

            showDialog()

            val deletePropertyTypesRequest = DeletePropertyTypesRequest()
            deletePropertyTypesRequest.PropertyId = propertyData!!.Id!!.toInt()
            deletePropertyTypesRequest.Id = propertyType.Id

            propertyTypesViewModel.deletePropertyType(deletePropertyTypesRequest)
        }

        btnCancel.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.PROPERTY_TYPES_DATA -> {

                hideDialog()

                Log.e("onInClick", ":clicked  ADD_PROPERTY:")
                propertyTypesViewModel.propertyTypesDetailsResponse?.value.let { it ->
                    propertyTypesDetailsResponse = it!!
                }

                Log.e("onPropertyRecLogs", "  ::  " + propertyTypesDetailsResponse)

//                propertyTypesFragmentBinding.pullToRefresh.setEnabled(true)

                if (propertyTypesDetailsResponse!!.data.isNotEmpty()) {
                    propertyTypesList = propertyTypesDetailsResponse!!.data
                    showPropertyTypesList()
                }
            }


            UpdateEvent.DELETE_PROPERTY_TYPES_SUCCESS -> {

                propertyTypesViewModel.getPropertyTypesDetails(propertyData!!.Id.toString())
            }


            UpdateEvent.HIDE_DIALOG -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

}

