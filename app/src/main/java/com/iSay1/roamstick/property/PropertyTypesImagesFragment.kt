package com.iSay1.roamstick.property

import android.Manifest
import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.databinding.PropertyTypesImagesFragmentBinding
import com.iSay1.roamstick.property.adapters.AllPropertyTypesListAdapter
import com.iSay1.roamstick.property.viewModel.PropertyTypesImagesViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class PropertyTypesImagesFragment : HomeBaseFragment(), MainActivity.IActivityListener {

    private lateinit var propertyTypesImagesFragmentBinding: PropertyTypesImagesFragmentBinding

    private val propertyTypesImagesViewModel: PropertyTypesImagesViewModel by activityViewModels()

    private val allPropertyTypesListAdapter by lazy {
        AllPropertyTypesListAdapter(requireContext(), mutableListOf())
    }


    //Class to Handle all the button click
    enum class ViewOnClick {
        ADD_IMAGE, SIGN_UP, SCAN_QR_BARCODE,
    }

    //Class to Handle all update events
    enum class UpdateEvent {
        PROPERTY_TYPES_DATA, HIDE_DIALOG, DELETE_PROPERTY_TYPES_SUCCESS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val permissionAll = arrayOf(
        permission.CAMERA, permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION, permission.READ_EXTERNAL_STORAGE
    )


    private val density = 70
    private val quality = 50

    private var pictureImagePath0 = ""
    private var pictureImagePath1 = ""

    private var fileUri: Uri? = null
    private val bitmap: Bitmap? = null
    private val isCamera = false

    private val signatureFileName = ""
    private val strSignatureBase64 = ""

    private val GALLERY_REQUEST_CODE = 111
    private val CAMERA_REQUEST_CODE = 112
    private val REQUEST_CODE_IMAGE_PICK = 113

    private val REQUEST_CODE_PERMISSIONS = 100

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity!!.registerListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        propertyTypesImagesFragmentBinding = PropertyTypesImagesFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { propertyTypesImagesViewModel.init(it) }

        propertyTypesImagesFragmentBinding.viewModel = propertyTypesImagesViewModel

// Added for solving camera issue

        // Added for solving camera issue
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure()
        }

        checkAndRequestPermissions()


        return propertyTypesImagesFragmentBinding.root
    }

    private fun checkAndRequestPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)

        val permissionsNeeded = mutableListOf<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionsNeeded.isNotEmpty()) {
            requestPermissions(permissionsNeeded.toTypedArray(), REQUEST_CODE_PERMISSIONS)
        } else {
            // Permissions already granted, proceed
            openImageSelection()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                openImageSelection()
            } else {
                // Permission request denied, handle user rejection
            }
        }
    }

    private fun openImageSelection() {
        val chooseImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        chooseImageIntent.type = "image/*"

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val chooserIntent = Intent.createChooser(chooseImageIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        startActivityForResult(chooserIntent, REQUEST_CODE_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data ?: return
            // Load the image from the URI (handle exceptions)
            // You can use Glide, Picasso, or other libraries for image loading
//            Glide.with(this).load(selectedImageUri).into(yourImageView)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {

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

            ViewOnClick.ADD_IMAGE -> {
                Log.e("onInClick", ":clicked  ADD_PROPERTY:")
                getimagefromgallery()
            }

            else -> {

            }
        }
    }

    private fun showPropertyTypesList() {/* propertyTypesFragmentBinding.rvPropertiesTypesList.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
         propertyTypesFragmentBinding.rvPropertiesTypesList.adapter = allPropertyTypesListAdapter

         allPropertyTypesListAdapter.setData(propertyTypesList!!)

         allPropertyTypesListAdapter.setClickListener(object : AllPropertyTypesListAdapter.PropertyTypesListener {
             override fun onItemClick(mPropertyType: PropertyTypesList?) {

                 showOptionsBottomSheet(mPropertyType)
             }
         })*/
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.PROPERTY_TYPES_DATA -> {

                hideDialog()/*
                                Log.e("onInClick", ":clicked  ADD_PROPERTY:")
                                propertyTypesViewModel.propertyTypesDetailsResponse?.value.let { it ->
                                    propertyTypesDetailsResponse = it!!
                                }*/

//                Log.e("onPropertyRecLogs", "  ::  " + propertyTypesDetailsResponse)

//                propertyTypesFragmentBinding.pullToRefresh.setEnabled(true)

                /*if (propertyTypesDetailsResponse!!.data.isNotEmpty()) {
                    propertyTypesList = propertyTypesDetailsResponse!!.data
                    showPropertyTypesList()
                }*/
            }

            UpdateEvent.DELETE_PROPERTY_TYPES_SUCCESS -> {

//                propertyTypesViewModel.getPropertyTypesDetails(propertyData!!.Id.toString())
            }


            UpdateEvent.HIDE_DIALOG -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

    fun getimagefromgallery() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext()!!)
        builder.setTitle("Add Photo of Loved Ones")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                pickFromCamera()
            } else if (options[item] == "Choose from Gallery") {
                pickImageFromGallery()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
//                isUploadImg = false
            }
        }
        builder.show()
    }

    private fun pickFromCamera() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "$timeStamp.JPEG"
            val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            Log.e("storageDirLog", ":$storageDir")
            var file: File? = null
            pictureImagePath0 = storageDir.absolutePath + "/" + imageFileName
            file = File(pictureImagePath0)
            fileUri = Uri.fromFile(file)
            Log.e("fileUriLog", ":$fileUri")
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(permission.READ_EXTERNAL_STORAGE), 50
            )
        }
    }

    private fun pickImageFromGallery() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.setType("image/*")
            //            intent.putExtra("crop", "true");
//            intent.putExtra("scale", true);
//            intent.putExtra("aspectX", 16);
//            intent.putExtra("aspectY", 9);
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(permission.READ_EXTERNAL_STORAGE), 50
            )
        }
    }

    override fun onActivityResultData(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data ?: return

            Log.e("onActiRes", ":")
            // Load the image from the URI (handle exceptions)
            // You can use Glide, Picasso, or other libraries for image loading
//            Glide.with(this).load(selectedImageUri).into(yourImageView)
        }

    }

}

