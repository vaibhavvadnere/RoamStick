package com.iSay1.roamstick.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.iSay1.roamstick.util.NetConnection.checkConnection
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.R

abstract class BaseFragment : Fragment() {
    private var progressDialog: ProgressDialog? = null
    abstract fun checkConnectivity()
    abstract val isConnected: Unit
    var mActivity: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MainActivity
    }

    private fun makeApiCall() {
        if (checkConnection(requireActivity())) {
            Log.d("Checking_internet", "devic123")
            isConnected
        } else {
            showNoInternetConnectionDialog()
            //Toast.makeText(this, "Connect Your Network", Toast.LENGTH_SHORT).show();
        }
    }

    fun showDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(requireContext())
            progressDialog!!.show()
            if (progressDialog!!.window != null) {
                progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            progressDialog!!.setContentView(R.layout.progress_dialog)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
        } else {
            progressDialog!!.show()
        }
    }

    fun hideDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    fun showToastMessage(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    private fun showNoInternetConnectionDialog() {
        Log.e("Testing net Connection", "Entering showNoInternetConnectionDialog Method")
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Whoops! Its seems you don't have internet connection, please try again later!")
            .setTitle("No Internet Connection")
            .setCancelable(false)
            .setNeutralButton("Retry") { dialog, id -> makeApiCall() }
        val alert = builder.create()
        alert.show()
        //  Log.e("Testing net Connection", "Showed NoIntenetConnectionDialog");
    }
}