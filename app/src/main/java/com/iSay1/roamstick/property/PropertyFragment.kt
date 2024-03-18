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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.data.model.request.DeletePropertyRequest
import com.iSay1.roamstick.data.model.response.PropertyData
import com.iSay1.roamstick.data.model.response.PropertyDetailsResponse
import com.iSay1.roamstick.databinding.PropertyFragmentBinding
import com.iSay1.roamstick.property.adapters.PropertyListAdapter
import com.iSay1.roamstick.property.bottomSheets.PropertyBottomSheetFragment
import com.iSay1.roamstick.property.viewModel.PropertyViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PropertyFragment : HomeBaseFragment() {

    private lateinit var propertyFragmentBinding: PropertyFragmentBinding

    private val propertyViewModel: PropertyViewModel by activityViewModels()

    private val propertyListAdapter by lazy {
        PropertyListAdapter(requireContext(), mutableListOf())
    }

    //Class to Handle all the button click
    enum class ViewOnClick {
        ADD_PROPERTY
    }

    //Class to Handle all update events
    enum class UpdateEvent {
        PROPERTY_DATA, HIDE_DIALOG, DELETE_PROPERTY_SUCCESS
    }

    var propertyDetailsResponse: PropertyDetailsResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        propertyFragmentBinding = PropertyFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { propertyViewModel.init(it) }

        propertyFragmentBinding.viewModel = propertyViewModel


//        loadData();
        propertyFragmentBinding.pullToRefresh.setOnRefreshListener(OnRefreshListener { // cancel the Visual indication of a refresh
            propertyFragmentBinding.pullToRefresh.setRefreshing(false)
            propertyFragmentBinding.pullToRefresh.setEnabled(false)

            showDialog()
            propertyViewModel.getProperties()
        })

        return propertyFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        showDialog()

        propertyViewModel.getProperties()
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

            ViewOnClick.ADD_PROPERTY -> {
                Log.e("onInClick", ":clicked  ADD_PROPERTY:")
                mActivity?.navController?.navigate(R.id.action_property_frag_add_property)
            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.PROPERTY_DATA -> {

                hideDialog()

                Log.e("onInClick", ":clicked  ADD_PROPERTY:")
                propertyViewModel.propertyDetailsResponse?.value.let { it ->
                    propertyDetailsResponse = it!!
                }

                Log.e("onPropertyRecLogs", "  ::  " + propertyDetailsResponse)

                propertyFragmentBinding.pullToRefresh.setEnabled(true)

                if (propertyDetailsResponse!!.data.isNotEmpty()) showPropertyList()
            }

            UpdateEvent.DELETE_PROPERTY_SUCCESS -> {
                Log.e("deleted_successfully", "  :  :  ")

                propertyViewModel.getProperties()

            }

            UpdateEvent.HIDE_DIALOG -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

    private fun showPropertyList() {
        propertyFragmentBinding.rvPropertiesList.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        propertyFragmentBinding.rvPropertiesList.adapter = propertyListAdapter

        propertyListAdapter.setData(propertyDetailsResponse?.data!!)

        propertyListAdapter.setClickListener(object : PropertyListAdapter.PropertyListListener {
            override fun onItemClick(mPropertyData: PropertyData?) {

                showOptionsBottomSheet(mPropertyData)
            }
        })
    }

    private fun showOptionsBottomSheet(mPropertyData: PropertyData?) {

        val bottomSheet: PropertyBottomSheetFragment
        bottomSheet = PropertyBottomSheetFragment()
        bottomSheet.setPropertyData(mPropertyData)
        bottomSheet.isCancelable = true
        bottomSheet.show(getChildFragmentManager(), Constants.MODAL_BOTTOM_SHEET)
        bottomSheet.registerListener(object : PropertyBottomSheetFragment.PropertyBottomSheetListener {
            override fun onPropertyType(propertyData: PropertyData) {

                val bundle = Bundle()
                bundle.putParcelable(Constants.PROPERTY_DATA, propertyData)
                mActivity?.navController?.navigate(R.id.action_property_types_frag_property, bundle)
            }

            override fun onDeleteClicked(propertyData: PropertyData) {
                showDeletePopUp(propertyData)
            }

            override fun onEditProperty(propertyData: PropertyData) {
                val bundle = Bundle()
                bundle.putParcelable(Constants.PROPERTY_DATA, propertyData)
                mActivity?.navController?.navigate(R.id.action_property_frag_add_property, bundle)
            }

            override fun onMapLocation(propertyData: PropertyData) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showDeletePopUp(propertyData: PropertyData?) {
        val dialog = Dialog(mActivity!!, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)

        dialog.setContentView(R.layout.lay_delete_device_popup)

        val tvDeleteHeader = dialog.findViewById<TextView>(R.id.tv_delete_header)
        val tvDeleteMessage = dialog.findViewById<TextView>(R.id.tv_delete_message)
        val btnCancel = dialog.findViewById<TextView>(R.id.btn_cancel)
        val btnDelete = dialog.findViewById<TextView>(R.id.btn_delete)


        val popupHeaderText: String = String.format(getString(R.string.delete_header), getString(R.string.property))
        tvDeleteHeader.text = popupHeaderText

        val popupMessageText: String = String.format(getString(R.string.are_you_sure_you_want_to_delete), propertyData!!.Name)
        tvDeleteMessage.text = popupMessageText

        btnDelete.setOnClickListener { v: View? ->
            dialog.dismiss()

            showDialog()

            val deletePropertyRequest = DeletePropertyRequest()
            deletePropertyRequest.Id = propertyData.Id!!.toInt()

            propertyViewModel.deleteProperty(deletePropertyRequest)
        }

        btnCancel.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }


}

