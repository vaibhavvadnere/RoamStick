package com.falcon.evCharger.signUp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.signUp.viewModel.SignUpFragmentViewModel
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.data.model.request.CreateUserRequest
import com.iSay1.roamstick.data.model.response.CreateUserResponse
import com.iSay1.roamstick.databinding.SignUpFragmentBinding
import com.iSay1.roamstick.util.DTU
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Calendar

class SignUpFragment : HomeBaseFragment() {

    private lateinit var signUpFragmentBinding: SignUpFragmentBinding

    private val signUpFragmentViewModel: SignUpFragmentViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        CONTINUE, SIGN_UP, SELECT_DATE_OF_BIRTH,
    }

    enum class UpdateEvent {
        CREATE_USER_FAILED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        signUpFragmentBinding = SignUpFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { signUpFragmentViewModel.init(it) }

        signUpFragmentBinding.viewModel = signUpFragmentViewModel

        return signUpFragmentBinding.root
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


    private fun validateForm(): Boolean {
        if (signUpFragmentBinding.edtFirstName.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please enter first name", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (signUpFragmentBinding.edtLastName.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please enter last name", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (signUpFragmentBinding.edtEmail.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please enter email", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (signUpFragmentBinding.edtDateOfBirth.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please select date of birth", Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (signUpFragmentBinding.edtPasswordInput.text.toString() == "") {

            Toast.makeText(
                mActivity, "Please enter password", Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {
            UpdateEvent.CREATE_USER_FAILED -> {
                hideDialog()
            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {

            ViewOnClick.CONTINUE -> {
                Log.e("onInClick", ":clicked  CONTINUE:")

                if (validateForm()) {
                    showDialog()


                    var createUserRequest: CreateUserRequest = CreateUserRequest()

                    createUserRequest.email = signUpFragmentBinding.edtEmail.text.toString()
                    createUserRequest.firstName = signUpFragmentBinding.edtFirstName.text.toString()
                    createUserRequest.lastName = signUpFragmentBinding.edtLastName.text.toString()
                    createUserRequest.password = signUpFragmentBinding.edtPasswordInput.text.toString()
                    createUserRequest.dateOfBirth = signUpFragmentBinding.edtDateOfBirth.text.toString()

                    signUpFragmentViewModel.createUsers(createUserRequest)
                }
            }

            ViewOnClick.SELECT_DATE_OF_BIRTH -> {
                Log.e("onInClick", ":clicked  SELECT_DATE_OF_BIRTH:")

                DTU().showDatePickerDialog(
                    context, DTU().FLAG_OLD_AND_NEW, signUpFragmentBinding.edtDateOfBirth
                )
            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(createUserResponse: CreateUserResponse) {

        hideDialog()

        val bundle = Bundle()
        bundle.putParcelable(Constants.USER_DATA, createUserResponse.data)
        mActivity?.navController!!.navigate(R.id.action_verify_otp, bundle)
    }
}

