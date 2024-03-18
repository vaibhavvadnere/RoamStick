package com.iSay1.roamstick.careTaker

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
import com.iSay1.roamstick.careTaker.viewModel.CareTakerViewModel
import com.iSay1.roamstick.data.model.request.DeleteCaretakerRequest
import com.iSay1.roamstick.data.model.response.CaretakerData
import com.iSay1.roamstick.data.model.response.CaretakerDetailsResponse
import com.iSay1.roamstick.databinding.CaretakerFragmentBinding
import com.iSay1.roamstick.property.adapters.CaretakerListAdapter
import com.iSay1.roamstick.property.bottomSheets.CaretakerBottomSheetFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CareTakerFragment : HomeBaseFragment() {

    private lateinit var caretakerFragmentBinding: CaretakerFragmentBinding

    private val careTakerViewModel: CareTakerViewModel by activityViewModels()

    private val caretakerListAdapter by lazy {
        CaretakerListAdapter(requireContext(), mutableListOf())
    }

    //Class to Handle all the button click
    enum class ViewOnClick {
        ADD_CARETAKER
    }

    //Class to Handle all update events
    enum class UpdateEvent {
        DELETE_CARETAKER_SUCCESSFUL, HIDE_DIALOG, CARETAKER_DATA
    }

    var caretakerDetailsResponse: CaretakerDetailsResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        caretakerFragmentBinding = CaretakerFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { careTakerViewModel.init(it) }

        caretakerFragmentBinding.viewModel = careTakerViewModel


//        loadData();
        caretakerFragmentBinding.pullToRefresh.setOnRefreshListener(OnRefreshListener { // cancel the Visual indication of a refresh
            caretakerFragmentBinding.pullToRefresh.setRefreshing(false)
            caretakerFragmentBinding.pullToRefresh.setEnabled(false)

            showDialog()
            careTakerViewModel.getCaretakers()
        })

        return caretakerFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        showDialog()

        careTakerViewModel.getCaretakers()
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

            ViewOnClick.ADD_CARETAKER -> {
                Log.e("onInClick", ":clicked  ADD_CARETAKER:")
                mActivity?.navController?.navigate(R.id.action_add_caretakers)
            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.CARETAKER_DATA -> {

                hideDialog()

                Log.e("onInClick", ":clicked  ADD_PROPERTY:")
                careTakerViewModel.caretakerDetailsResponse?.value.let { it ->
                    caretakerDetailsResponse = it!!
                }

                caretakerFragmentBinding.pullToRefresh.setEnabled(true)

                showCaretakersList()
            }

            UpdateEvent.DELETE_CARETAKER_SUCCESSFUL -> {

                careTakerViewModel.getCaretakers()

            }

            UpdateEvent.HIDE_DIALOG -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

    private fun showCaretakersList() {
        caretakerFragmentBinding.rvCaretakerList.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        caretakerFragmentBinding.rvCaretakerList.adapter = caretakerListAdapter

        caretakerListAdapter.setData(caretakerDetailsResponse?.data!!)
        caretakerListAdapter.setViewType(Constants.DETAILED)

        caretakerListAdapter.setClickListener(object : CaretakerListAdapter.CaretakerListener {
            override fun onItemClick(mCaretakerData: CaretakerData?, isChecked: Boolean) {
                showOptionsBottomSheet(mCaretakerData)
            }
        })
    }

    private fun showOptionsBottomSheet(mCaretakerData: CaretakerData?) {

        val bottomSheet: CaretakerBottomSheetFragment
        bottomSheet = CaretakerBottomSheetFragment()
        bottomSheet.setCaretakerData(mCaretakerData)
        bottomSheet.isCancelable = true
        bottomSheet.show(getChildFragmentManager(), Constants.MODAL_BOTTOM_SHEET)
        bottomSheet.registerListener(object : CaretakerBottomSheetFragment.CaretakerBottomSheetListener {

            override fun onDeleteClicked(caretakerData: CaretakerData) {
                showDeletePopUp(caretakerData)
            }

            override fun onEditProperty(caretakerData: CaretakerData) {

                val bundle = Bundle()
                bundle.putParcelable(Constants.CARETAKER_DATA, caretakerData)
                mActivity?.navController?.navigate(R.id.action_add_caretakers, bundle)
            }

        })
    }


    private fun showDeletePopUp(caretakerData: CaretakerData?) {
        val dialog = Dialog(mActivity!!, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)

        dialog.setContentView(R.layout.lay_delete_device_popup)

        val tvDeleteHeader = dialog.findViewById<TextView>(R.id.tv_delete_header)
        val tvDeleteMessage = dialog.findViewById<TextView>(R.id.tv_delete_message)
        val btnCancel = dialog.findViewById<TextView>(R.id.btn_cancel)
        val btnDelete = dialog.findViewById<TextView>(R.id.btn_delete)


        val popupHeaderText: String = String.format(getString(R.string.delete_header), getString(R.string.caretaker))
        tvDeleteHeader.text = popupHeaderText

        val popupMessageText: String =
            String.format(getString(R.string.are_you_sure_you_want_to_delete_caretaker), caretakerData!!.FirstName + " " + caretakerData.LastName)
        tvDeleteMessage.text = popupMessageText

        btnDelete.setOnClickListener { v: View? ->
            dialog.dismiss()

            showDialog()

            val deleteCaretakerRequest = DeleteCaretakerRequest()
            deleteCaretakerRequest.CareTakerId = caretakerData.Id!!.toInt()

            careTakerViewModel.deleteCaretaker(deleteCaretakerRequest)
        }

        btnCancel.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

}

