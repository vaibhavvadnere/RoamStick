package com.iSay1.roamstick.onBoarding

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.databinding.LetsYouInFragmentBinding
import com.iSay1.roamstick.onBoarding.viewModel.LetsYouInFragmentViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LetsYouInFragment : HomeBaseFragment(), MainActivity.onBackPressListener {

    private lateinit var letsYouInFragmentBinding: LetsYouInFragmentBinding

    private val letsYouInFragmentViewModel: LetsYouInFragmentViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        SIGN_IN, SIGN_UP, SCAN_QR_BARCODE,
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        letsYouInFragmentBinding = LetsYouInFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { letsYouInFragmentViewModel.init(it) }

        letsYouInFragmentBinding.viewModel = letsYouInFragmentViewModel

        mActivity?.registerOnBackPress(this)

        val signInMsg = String.format(getString(R.string.don_t_have_an_account_sign_up))

        val i1 = signInMsg.indexOf("? ")
        val i2 = signInMsg.length

        val signInSpanString = SpannableString(signInMsg)

        val clickSpan: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)

                ds.isUnderlineText = false
                ds.typeface = Typeface.DEFAULT_BOLD

            }

            override fun onClick(view: View) {
                mActivity?.navController?.navigate(R.id.action_sign_up)
            }
        }

        signInSpanString.setSpan(clickSpan, i1 + 1, i2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        letsYouInFragmentBinding.tvSignUp.text = signInSpanString
        letsYouInFragmentBinding.tvSignUp.movementMethod = LinkMovementMethod.getInstance()

        return letsYouInFragmentBinding.root
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

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {
            ViewOnClick.SIGN_IN -> {
                Log.e("onSignInClick", ":clicked  SIGN_IN:")
                mActivity?.navController?.navigate(R.id.action_sign_in)
            }

            ViewOnClick.SIGN_UP -> {
                mActivity?.navController?.navigate(R.id.action_sign_up)
            }

            else -> {

            }
        }
    }

    override fun onBackPress() {

        Log.e("onBackPress", " Lets You In ")

        requireActivity().finish()

        mActivity?.finishAffinity()
    }
}

