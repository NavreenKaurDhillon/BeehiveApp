package com.bhive.beehiveapp.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.bumptech.glide.Glide
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.MessageManagerAdapter
import com.bhive.beehiveapp.databinding.BottomTabSelectionBinding
import com.bhive.beehiveapp.databinding.FragmentReportanissueBinding
import com.bhive.beehiveapp.models.MessagesToManager
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.utils.constants.Constant.Companion.finalImagesData
import com.bhive.beehiveapp.utils.constants.Constant.Companion.imagesData
import com.bhive.beehiveapp.utils.fonts.CustomEditTextRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.viewmodel.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ReportAnIssueFragment() : BaseFragment(),
    View.OnClickListener {

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                currentImageView?.setImageURI(result.data?.data)
                if (photoFile?.let { baseActivity.saveBitmapToFile(it) } != null) {
//                    Constant.imagesData.clear()
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    baseActivity.saveBitmapToFile(photoFile!!)?.let {
                            Constant.imagesData.add(
                                it)
                        imagesDataIndex++
                            Log.d(TAG, "/// image view = : "+ currentImageView)
//                        if (reportanissueBinding.reportBT.text.toString() == "UPDATE")
//                        {
//                            previousDeleteButton?.visibility  = View.GONE
//                        }
                        Log.d(TAG, "//// images object =  : "+imagesObject)
                            currentImageView?.setImageURI(Uri.fromFile(it))
                            currentDeleteButton?.visibility =View.VISIBLE
                             nextImageViewLayout?.visibility  = View.VISIBLE
                        uploadImages()
                    }
                }
            }
        }

    private fun uploadImages()
    {
        var title: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
            reportanissueBinding.issueTitle.text.toString())
        this.title = title
        var description: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
            reportanissueBinding.customEditText.text.toString())
        this.description = description

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        var ii = 0
        finalImagesData.clear()
//        while (ii < imagesData.size)
//        {
//            if (imagesData.get(ii).equals("ff") == false)
//            {
//                finalImagesData.add(imagesData.get(ii))
//            }
//            ii++
//        }

        Log.d(TAG, "uploadImages: ///// final images array = "+ finalImagesData)

//        if (imagesData.size != 0 )
//        {
//            if (imagesData[0].toString().equals("ff")==false)
//            {
//                finalImagesData.add(imagesData[0])
//            }
//
//            if (imagesData.size>1) {
//                if (imagesData[1].toString().equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[1])
//                }
//                }
//            if (imagesData.size>2){
//                if (imagesData[2].toString().equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[2])
//                }
//
//                }
//            if (imagesData.size>3){
//                if (imagesData[3].equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[3])
//                }
//                }
//            if (imagesData.size>4){
//                if (imagesData[4].equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[4])
//                }
//                }
//            if (imagesData.size>5){
//                if (imagesData[5].equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[5])
//                }
//                }
//            if (imagesData.size>6){
//                if (imagesData[6].equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[6])
//                }
//                }
//            if (imagesData.size>7){
//                if (imagesData[7].equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[7])
//                }
//                }
//            if (imagesData.size>8){
//                if (imagesData[8].equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[8])
//                }
//                }
//            if (imagesData.size>9){
//                if (imagesData[9].equals("ff")==false)
//                {
//                    finalImagesData.add(imagesData[9])
//                }
//                }
//        }

        if (imagesData.size!=0)
    {
        //1
        IMAGES_SENT = 1
        val file = imagesData[0]
        val surveyBody : RequestBody = file.asRequestBody("images/*".toMediaTypeOrNull())
        Log.d(TAG, "uploadImages: //// file name = "+file.name)
        builder.addFormDataPart("images", file.name, surveyBody)
        this.filePart = MultipartBody.Part.createFormData(
            "images",
            timeStamp+file.name,
            surveyBody)


        if (imagesData.size>1)
        {
            //2
            IMAGES_SENT = 2
            val file1 = imagesData[1]
            Log.d(TAG, "uploadImages: // file name 2 = " + file1.name)
            val surveyBody1: RequestBody = file1.asRequestBody("images/*".toMediaTypeOrNull())
            this.filePart1 = MultipartBody.Part.createFormData(
                "images",
                timeStamp + file1.name,
                surveyBody1
            )
            if (imagesData.size>2)
            {
                //3
                IMAGES_SENT = 3
                val file2 = imagesData[2]
                val surveyBody2: RequestBody =
                    file2.asRequestBody("images/*".toMediaTypeOrNull())
                this.filePart2 = MultipartBody.Part.createFormData(
                    "images",
                    timeStamp + file2.name,
                    surveyBody2
                )
                if (imagesData.size>3)
                {
                    //4
                    IMAGES_SENT = 4
                    val file3 = imagesData[3]
                    val surveyBody3: RequestBody =
                        file3.asRequestBody("images/*".toMediaTypeOrNull())
                    this.filePart3 = MultipartBody.Part.createFormData(
                        "images",
                        file3.name,
                        surveyBody3
                    )
                    if (imagesData.size>4)
                    {
                        //5
                        IMAGES_SENT = 5
                        val file4 = imagesData[4]
                        val surveyBody4: RequestBody =
                            file4.asRequestBody("images/*".toMediaTypeOrNull())
                        this.filePart4 = MultipartBody.Part.createFormData(
                            "images",
                            timeStamp + file4.name,
                            surveyBody4
                        )
                    }
                }
            }
        }
    }

        Log.d(TAG, "uploadImages: //// removed final array = "+ finalImagesData)

    }


    lateinit var confirmationDialog: Dialog
    private var photoFile: File? = null
    private lateinit var currentPhotoPath: String
    private var requestBody: MultipartBody.Part ? = null
    private lateinit var addIssueViewModel: AddIssueViewModel
    private lateinit var societyDetailsViewModel: SocietyDetailsViewModel
    private lateinit var messageStatusViewModel: MessageStatusViewModel
    private lateinit var getProfileViewModel: GetProfileViewModel
    private lateinit var updateIssueStatusViewModel: UpdateIssueStatusViewModel
    lateinit var reportanissueBinding: FragmentReportanissueBinding
    val REQUEST_CODE = 101
    private var filePart : MultipartBody.Part ? = null
    private var filePart1 : MultipartBody.Part ? = null
    private var filePart2 : MultipartBody.Part ? = null
    private var filePart3 : MultipartBody.Part ? = null
    private var filePart4 : MultipartBody.Part ? = null
    private var filePart5 : MultipartBody.Part ? = null
    private var filePart6 : MultipartBody.Part ? = null
    private var filePart7 : MultipartBody.Part ? = null
    private var filePart8 : MultipartBody.Part ? = null
    private var filePart9 : MultipartBody.Part ? = null
    lateinit var getIssueViewModel: GetIssueViewModel
    lateinit var imagePath: String
    lateinit var imagesPath: ArrayList<String>
    lateinit var image: MultipartBody.Part
    lateinit var imageUri: ArrayList<Uri>
    var bundle: Bundle = Bundle()
    lateinit var jsonObject: JsonObject
    lateinit var jsonObjectM: JsonObject
    private var bottomSheetDialogImage : BottomSheetDialog?= null
    private lateinit var messageManagerViewModel: MessageManagerViewModel
    var ColorPrimary : String ? = null
    var title : RequestBody? = null
    var description : RequestBody? = null
    var uploadedImagesArray : RequestBody? = null
    var images : MultipartBody.Part? = null
    var IMAGES_SENT : Int ? = null
    var UPLOADED_IMAGES : Int?=null
    var imagesDataIndex : Int = 0
    var handler : Handler ? = null
    var mainActivity = MainActivity()
    var uploadedImages = kotlin.collections.ArrayList<String>()
    var replyMessages = ArrayList<MessagesToManager>()
    var adapter  = MessageManagerAdapter()
    var imagesObject = JsonObject()

    companion object{
        var CLOSE_ISSUE = 0
        var FLOOR_NO  :String? = null
        var BUILDING_NAME : String ? = null
        var currentImageView : ImageView ? =null
        var currentDeleteButton : ImageView ? = null
        var previousDeleteButton : ImageView ? = null
        var nextImageViewLayout : RelativeLayout ? =null
        var currentImageNO : Int ? =null
        var MessageStatusViewModelOb : MessageStatusViewModel?=null
        var bitmapFile : File ? = null
        var UPDATED_SUCCESS : Int = 0
        var ADDED_SUCCESS : Int = 0

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportanissueBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_reportanissue,
            container,
            false)
        return reportanissueBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseRequireInsteadOfGet", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Constant.imagesData.clear()
        Constant.reportAnIssueBinding = reportanissueBinding
//        val bitmap = BitmapFactory.decodeStream(URL("http://staging.qualhon.net:3088/uploads/issues/images-1675321115066.jpeg").openConnection().getInputStream())
//        var bitmap : Bitmap= getBitmapFromLink("http://staging.qualhon.net:3088/uploads/issues/images-1675321115066.jpeg")
//        Log.d(TAG, "onViewCreated: //// bitmp created = "+bitmap)

        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        addIssueViewModel = ViewModelProvider(this).get(AddIssueViewModel::class.java)

        Constant.addIssueViewModel = addIssueViewModel
        bottomSheetDialogImage = BottomSheetDialog(baseActivity, R.style.bottomPopup)
        societyDetailsViewModel = ViewModelProvider(this).get(SocietyDetailsViewModel::class.java)
        messageStatusViewModel = ViewModelProvider(this).get(MessageStatusViewModel::class.java)
        messageManagerViewModel = ViewModelProvider(this).get(MessageManagerViewModel::class.java)
        getProfileViewModel = ViewModelProvider(this).get(GetProfileViewModel::class.java)
        updateIssueStatusViewModel = ViewModelProvider(this).get(UpdateIssueStatusViewModel::class.java)
        Constant.updateIssueViewModel = updateIssueStatusViewModel
        MessageStatusViewModelOb = messageStatusViewModel
        setColor()
        setSocietyDetails()
        reportanissueBinding.myMessage.visibility = View.GONE

        reportanissueBinding.sendIcon.setOnClickListener {

            if (Constant.NAME.isNullOrEmpty() == true) {
                Constant.NAME =
                    SharedPreferencesClass.getString(requireContext(), "firstName").toString() +
                            SharedPreferencesClass.getString(requireContext(), "lastName")
                                .toString()
            }
            if(reportanissueBinding.messageET.text.toString().isNullOrEmpty() == false)
            {
            replyMessages.add(
                MessagesToManager(
                    reportanissueBinding.messageET.text.toString(),
                    Constant.NAME
                )
            )

//            reportanissueBinding.userName.text = Constant.NAME
//            reportanissueBinding.senderImage.text= Constant.NAME?.get(0)?.toString()
//            reportanissueBinding.messageLayout.setBackgroundColor(Color.parseColor(ColorPrimary))
//            reportanissueBinding.messageBubble.text = reportanissueBinding.messageET.text.toString()
//            reportanissueBinding.time.text ="Just now"
//            reportanissueBinding.myMessage.visibility = View.VISIBLE

            reportanissueBinding.messageManagerRecycler.adapter = adapter

            //set recycler

            Log.d(TAG, "onViewCreated: /// messages list = " + replyMessages)

            jsonObjectM = JsonObject()

            jsonObjectM.addProperty("message", reportanissueBinding.messageET.text.toString())
            messageManagerViewModel!!.messageToManager(Constant.issueId.toString(), jsonObjectM)
            adapter.submitList(replyMessages)
                reportanissueBinding.noMessages.visibility = View.GONE
            adapter.notifyItemRangeChanged(0, replyMessages.size)
//            getIssueViewModel.getIssue(Constant.SOCIETY_ID.toString(),
//                Constant.issueId.toString(),
//                reportanissueBinding,
//                requireContext()
//            )
            reportanissueBinding.messageET.text = null
        }
        }
        confirmationDialog = Dialog(baseActivity)

        jsonObject = JsonObject()
        getIssueViewModel = ViewModelProvider(this).get(GetIssueViewModel::class.java)

        reportanissueBinding.back.setOnClickListener {
            findNavController().navigate(R.id.action_reportAnIssueFragment_to_reportAnIssueMainFragment)
        }

        if (Constant.issueId != 0) {
            if (ReportAnIssueMainFragment.STATUS == 3){
                showDetails(reportanissueBinding)
            }
            updateFun(reportanissueBinding)

            getIssueViewModel.getIssue(Constant.SOCIETY_ID.toString(),
                Constant.issueId.toString(),
                reportanissueBinding,
                requireContext()
            )
        } else {
            addFun(reportanissueBinding)
        }

        imagePath = ""

        imagesPath = ArrayList<String>()

//        reportanissueBinding.reportBT.setOnClickListener(this)

        imageUri = ArrayList<Uri>()

        val pattern = "hh:mm a"
        val fin: LocalDateTime = LocalDateTime.now()
        val nf: NumberFormat = NumberFormat.getInstance()
        nf.setMinimumIntegerDigits(2)
        reportanissueBinding.dateTV.setText(nf.format(fin.dayOfMonth).toString()+"-"+nf.format(fin.monthValue).toString()+"-"+fin.year
             +", "+fin.format(DateTimeFormatter.ofPattern(pattern)))
        Log.d(TAG, "onViewCreated: /// title 1 = "+reportanissueBinding.issueTitle.text.toString())
        Log.d(TAG, "onViewCreated: /// des 1 = "+reportanissueBinding.customEditText.text.toString())

        reportanissueBinding.reportBT.setOnClickListener {

            var title: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                reportanissueBinding.issueTitle.text.toString())
            this.title = title
            var description: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                reportanissueBinding.customEditText.text.toString())
            this.description = description
            if (reportanissueBinding.reportBT.text.equals("SUBMIT"))
            {
                Log.d(TAG, "onViewCreated: /// title = "+reportanissueBinding.issueTitle.text.toString())
                Log.d(TAG, "onViewCreated: /// description = "+reportanissueBinding.customEditText.text.toString())
                Log.d(TAG, "onViewCreated: //// add issue")
                if (reportanissueBinding.issueTitle.text?.length == 0) {
                    reportanissueBinding.issueTitle.setError("Issue title can't be empty")
                    reportanissueBinding.issueTitle.requestFocus()
                }
                else if (reportanissueBinding.customEditText.text?.length == 0) {

                    reportanissueBinding.customEditText.setError("Issue description can't be empty")
                    reportanissueBinding.customEditText.requestFocus()
                }
                else
                {
                    if (IMAGES_SENT ==null )
                    {
                        Log.d(TAG, "onViewCreated: /// no images ")
                        addIssueViewModel.addIssue(
                            0, this.title , this.description, null,
                        null, null, null, null, it , confirmationDialog , requireContext())
                    }
                if (IMAGES_SENT==1)
                {
                    addIssueViewModel.addIssue(
                        IMAGES_SENT!!, this.title, this.description,
                        this.filePart, null, null, null, null,
                        requireView(), confirmationDialog, requireContext()
                    )
                }
                if (IMAGES_SENT==2)
                {
                    addIssueViewModel.addIssue(
                        IMAGES_SENT!!, this.title, this.description,
                        this.filePart, this.filePart1, null, null, null,
                        it, confirmationDialog, requireContext()
                    )
                }
                if (IMAGES_SENT==3)
                {
                    addIssueViewModel.addIssue(
                        IMAGES_SENT!!, this.title, this.description,
                        this.filePart, this.filePart1, this.filePart2, null, null,
                        it, confirmationDialog, requireContext()
                    )
                }
                if (IMAGES_SENT==4)
                {
                    addIssueViewModel.addIssue(
                        IMAGES_SENT!!, this.title, this.description,
                        this.filePart, this.filePart1, this.filePart2, this.filePart3, null,
                        it, confirmationDialog, requireContext()
                    )
                }
                if (IMAGES_SENT==5)
                {
                    addIssueViewModel.addIssue(
                        IMAGES_SENT!!,
                        this.title,
                        this.description,
                        this.filePart,
                        this.filePart1,
                        this.filePart2,
                        this.filePart3,
                        this.filePart4,
                        it,
                        confirmationDialog,
                        requireContext()
                    )
                }}
            }
        }

            reportanissueBinding.Image1.setOnClickListener {
                currentImageNO = 0
            currentImageView = reportanissueBinding.Image1
                currentDeleteButton = reportanissueBinding.delete1
            nextImageViewLayout = reportanissueBinding.rl2
                showBottomSheetImages()
//                reportanissueBinding.delete1.visibility = View.VISIBLE
//                reportanissueBinding.rl2.visibility = View.VISIBLE

            }
        reportanissueBinding.Image2.setOnClickListener {
            currentImageNO = 1
            currentImageView = reportanissueBinding.Image2
            nextImageViewLayout = reportanissueBinding.rl3
            currentDeleteButton = reportanissueBinding.delete2
            previousDeleteButton = reportanissueBinding.delete1
            showBottomSheetImages()
//            reportanissueBinding.delete2.visibility = View.VISIBLE
//            reportanissueBinding.rl3.visibility = View.VISIBLE
//            doDelete(reportanissueBinding.Image2)
//            if (COMPLAINANT_ID == Constant.USER_ID){
//                showBottomSheetImages(reportanissueBinding.Image2)
//            }

        }
        reportanissueBinding.Image3.setOnClickListener {
            previousDeleteButton = reportanissueBinding.delete2
            currentImageNO = 2
            currentImageView = reportanissueBinding.Image3
            nextImageViewLayout = reportanissueBinding.rl4
            currentDeleteButton = reportanissueBinding.delete3
            showBottomSheetImages()
//            reportanissueBinding.delete3.visibility = View.VISIBLE
//            reportanissueBinding.rl4.visibility = View.VISIBLE
//            doDelete(reportanissueBinding.Image3)
//            if (COMPLAINANT_ID == Constant.USER_ID){
//                showBottomSheetImages(reportanissueBinding.Image3)
//            }
        }
        reportanissueBinding.Image4.setOnClickListener {
            previousDeleteButton =  reportanissueBinding.delete3
            currentImageNO = 3
            currentImageView = reportanissueBinding.Image4
            nextImageViewLayout = reportanissueBinding.rl5
            currentDeleteButton = reportanissueBinding.delete4
            showBottomSheetImages()
//            reportanissueBinding.delete4.visibility = View.VISIBLE
//            doDelete(reportanissueBinding.Image4)
//            if (COMPLAINANT_ID == Constant.USER_ID){
//                showBottomSheetImages(reportanissueBinding.Image4)
//            }
        }
        reportanissueBinding.Image5.setOnClickListener {
            previousDeleteButton = reportanissueBinding.delete4
            currentImageNO = 4
            currentImageView = reportanissueBinding.Image5
            currentDeleteButton = reportanissueBinding.delete5
//            nextImageViewLayout = reportanissueBinding.rl6
            showBottomSheetImages()
//            reportanissueBinding.delete5.visibility = View.VISIBLE
//            doDelete(reportanissueBinding.Image5)
//            if (COMPLAINANT_ID == Constant.USER_ID){
//                showBottomSheetImages(reportanissueBinding.Image5)
//            }
        }
//        reportanissueBinding.Image6.setOnClickListener {
//            previousDeleteButton = reportanissueBinding.delete5
//            currentImageNO = 5
//            currentImageView = reportanissueBinding.Image6
//            currentDeleteButton = reportanissueBinding.delete6
//            nextImageViewLayout = reportanissueBinding.rl7
//            showBottomSheetImages()
//        }
//        reportanissueBinding.Image7.setOnClickListener {
//            previousDeleteButton = reportanissueBinding.delete7
//            currentImageNO = 6
//            currentImageView = reportanissueBinding.Image7
//            currentDeleteButton = reportanissueBinding.delete7
//            nextImageViewLayout = reportanissueBinding.rl8
//            showBottomSheetImages()
////            reportanissueBinding.delete5.visibi[lity = View.VISIBLE
////            doDelete(reportanissueBinding.Image5)
////            if (COMPLAINANT_ID == Constant.USER_ID){
////                showBottomSheetImages(reportanissueBinding.Image5)
////            }
//        }
//        reportanissueBinding.Image8.setOnClickListener {
//            previousDeleteButton = reportanissueBinding.delete8
//            currentImageNO = 7
//            currentImageView = reportanissueBinding.Image8
//            currentDeleteButton = reportanissueBinding.delete8
//            nextImageViewLayout = reportanissueBinding.rl9
//            showBottomSheetImages()
////            reportanissueBinding.delete5.visibility = View.VISIBLE
////            doDelete(reportanissueBinding.Image5)
////            if (COMPLAINANT_ID == Constant.USER_ID){
////                showBottomSheetImages(reportanissueBinding.Image5)
////            }
//        }
//        reportanissueBinding.Image9.setOnClickListener {
//            previousDeleteButton = reportanissueBinding.delete9
//            currentImageNO = 8
//            currentImageView = reportanissueBinding.Image9
//            currentDeleteButton = reportanissueBinding.delete9
//            nextImageViewLayout = reportanissueBinding.rl10
//            showBottomSheetImages()
////            reportanissueBinding.delete5.visibility = View.VISIBLE
////            doDelete(reportanissueBinding.Image5)
////            if (COMPLAINANT_ID == Constant.USER_ID){
////                showBottomSheetImages(reportanissueBinding.Image5)
////            }
//        }
//        reportanissueBinding.Image10.setOnClickListener {
//            previousDeleteButton = reportanissueBinding.delete10
//            currentImageNO = 9//',.
//            currentImageView = reportanissueBinding.Image10
//            currentDeleteButton = reportanissueBinding.delete10
//            showBottomSheetImages()
////            reportanissueBinding.delete5.visibility = View.VISIBLE
////            doDelete(reportanissueBinding.Image5)
////            if (COMPLAINANT_ID == Constant.USER_ID){
////                showBottomSheetImages(reportanissueBinding.Image5)
////            }
//        }

        reportanissueBinding.delete1.setOnClickListener {
            var ff = File("ff")
//            Constant.imagesData.set(0 , ff)
            if (reportanissueBinding.reportBT.text.toString().equals("UPDATE"))
            {
                Constant.imagesData.removeAt(Constant.imagesData.size-1)
            }else{
                Constant.imagesData.removeAt(0)
            }

            reportanissueBinding.delete1.visibility = View.GONE
            reportanissueBinding.Image1.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
//            reportanissueBinding.rl1?.visibility  = View.GONE
            currentDeleteButton?.visibility  = View.GONE
            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)
        }
        reportanissueBinding.delete2.setOnClickListener {
            var ff = File("ff")
            if (reportanissueBinding.reportBT.text.toString().equals("UPDATE"))
            {
                Constant.imagesData.removeAt(Constant.imagesData.size-1)
            }
            else{
                Constant.imagesData.removeAt(1)
            }
//            Constant.imagesData.set(1 , ff)
            reportanissueBinding.delete2.visibility = View.GONE
            reportanissueBinding.Image2.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
//            reportanissueBinding.rl2?.visibility  = View.GONE
            currentDeleteButton?.visibility  = View.GONE
            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)

        }
        reportanissueBinding.delete3.setOnClickListener {
            var ff = File("ff")
//            Constant.imagesData.set(2 , ff)
            if (reportanissueBinding.reportBT.text.toString().equals("UPDATE"))
            {
                Constant.imagesData.removeAt(Constant.imagesData.size-1)
            }
            else{
                Constant.imagesData.removeAt(2)
            }

            reportanissueBinding.Image3.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
          reportanissueBinding.delete3.visibility = View.GONE
//            reportanissueBinding.rl3?.visibility  = View.GONE
            currentDeleteButton?.visibility  = View.GONE
            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)


        }
        reportanissueBinding.delete4.setOnClickListener {
            var ff = File("ff")
//            Constant.imagesData.set(3 , ff)
            if (reportanissueBinding.reportBT.text.toString().equals("UPDATE"))
            {
                Constant.imagesData.removeAt(Constant.imagesData.size-1)
            }
            else{
                Constant.imagesData.removeAt(3)
            }
            reportanissueBinding.Image4.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
            reportanissueBinding.delete4.visibility =View.GONE
//            reportanissueBinding.rl4?.visibility  = View.GONE
            currentDeleteButton?.visibility  = View.GONE
            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)

        }
        reportanissueBinding.delete5.setOnClickListener {
            var ff = File("ff")
            if (reportanissueBinding.reportBT.text.toString().equals("UPDATE"))
            {
                Constant.imagesData.removeAt(Constant.imagesData.size-1)
            }
            else{
                Constant.imagesData.removeAt(4)
            }

//            Constant.imagesData.set(4 , ff)
            reportanissueBinding.Image5.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
            reportanissueBinding.delete5.visibility = View.GONE
//            reportanissueBinding.rl5?.visibility  = View.GONE
            currentDeleteButton?.visibility  = View.GONE
            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)

        }
//        reportanissueBinding.delete6.setOnClickListener {
//            var ff = File("ff")
//            Constant.imagesData.set(5 , ff)
////            reportanissueBinding.Image5.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
//            reportanissueBinding.delete6.visibility = View.GONE
//            reportanissueBinding.rl6.visibility  = View.GONE
//            currentDeleteButton?.visibility  = View.GONE
//            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)
//
//        }
//        reportanissueBinding.delete7.setOnClickListener {
//            var ff = File("ff")
//            Constant.imagesData.set(6 , ff)
////            reportanissueBinding.Image5.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
//            reportanissueBinding.delete7.visibility = View.GONE
//            reportanissueBinding.rl7?.visibility  = View.GONE
//            currentDeleteButton?.visibility  = View.GONE
//            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)
//
//        }
//        reportanissueBinding.delete8.setOnClickListener {
//            var ff = File("ff")
//            Constant.imagesData.set(7 , ff)
////            reportanissueBinding.Image5.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
//            reportanissueBinding.delete8.visibility = View.GONE
//            reportanissueBinding.rl8?.visibility  = View.GONE
//            currentDeleteButton?.visibility  = View.GONE
//            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)
//
//        }
//        reportanissueBinding.delete9.setOnClickListener {
//            var ff = File("ff")
//            Constant.imagesData.set(8 , ff)
////            reportanissueBinding.Image5.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
//            reportanissueBinding.delete9.visibility = View.GONE
//            reportanissueBinding.rl9?.visibility  = View.GONE
//            currentDeleteButton?.visibility  = View.GONE
//            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)
//
//        }
//        reportanissueBinding.delete10.setOnClickListener {
//
//            var ff = File("ff")
//            Constant.imagesData. set(9 , ff)
////            reportanissueBinding.Image5.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
//            reportanissueBinding.delete10.visibility = View.GONE
//            reportanissueBinding.rl10?.visibility  = View.GONE
//            currentDeleteButton?.visibility  = View.GONE
//            Log.d(TAG, "onViewCreated: //// final array = "+ imagesData)
//
//        }
    }

    private fun showBottomSheetImages() {
        val dialog = BottomSheetDialog(requireContext())
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.bottom_tab_selection, null)
        val binding :BottomTabSelectionBinding = BottomTabSelectionBinding.bind(view)
        dialog.setContentView(view)
        dialog.show()
        binding.closeIV.setOnClickListener {
            bottomSheetDialogImage?.hide()
        }
        binding.uploadFromGallery.setOnClickListener {
            dialog.dismiss()
            bottomSheetDialogImage?.dismiss()
            setGalleryPhoto()

        }
        binding.takePhoto.setOnClickListener {
            dialog.dismiss()
            bottomSheetDialogImage?.dismiss()
            setCameraPhoto()
        }
        dialog?.setCancelable(true)
        dialog?.show()

    }

    private fun setGalleryPhoto() {
        baseActivity.checkSelfPermission(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), object : MainActivity.PermissionCallback {
                override fun permGranted() {
                    UwMediaPicker
                        .with(baseActivity)                        // Activity or Fragment
                        .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery) // GalleryMode: ImageGallery/VideoGallery/ImageAndVideoGallery, default is ImageGallery
                        .setGridColumnCount(4)
                        .setMaxSelectableMediaCount(1)// Grid column count, default is 3
                        .setLightStatusBar(true)                                // Is llight status bar enable, default is true
                        .enableImageCompression(true)                // Is image compression enable, default is false
                        .setCompressionMaxWidth(1280F)                // Compressed image's max width px, default is 1280
                        .setCompressionMaxHeight(720F)                // Compressed image's max height px, default is 720
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)        // Compressed image's format, default is JPEG
                        .setCompressionQuality(85)                // Image compression quality, default is 85
                        .setCancelCallback { }                    // Will be called when user cancels media selection
                        .launch { selectedMediaList ->
                            if (selectedMediaList!!.isNotEmpty()) {
//                                Constant.imagesData.clear()
                                for (i in selectedMediaList) {
                                    Log.d(TAG, "permGranted: //// media = "+i.mediaPath)
                                    val photo = File(i.mediaPath)
                                    Constant.imagesData?.add(photo)
                                    imagesObject.addProperty(currentImageView.toString() , photo.toString())
                                    Log.d(TAG, "//// images object =  : "+imagesObject)
                                    imagesDataIndex++
                                    currentImageView?.let {
                                        Glide.with(requireContext())
                                            .load((i.mediaPath))
                                            .placeholder(resources.getDrawable(R.drawable.ic_baseline_add_24))
                                            .into(it)
                                    }
                                    if (reportanissueBinding.reportBT.text.toString() =="UPDATE")
                                    {
                                        previousDeleteButton?.visibility  =View.GONE
                                    }
                                    currentDeleteButton?.visibility  = View.VISIBLE
                                    nextImageViewLayout?.visibility = View.VISIBLE
                                    Log.d(TAG, "permGranted: /// images data = "+Constant.imagesData)
                                }
                                uploadImages()
                            }

                        }
                }
                override fun permDenied() {
                }

            })
    }

    private fun setCameraPhoto() {
        baseActivity.checkSelfPermission(
            arrayOf(
                Manifest.permission.CAMERA,
            ), object : MainActivity.PermissionCallback {
                override fun permGranted() {
                    dispatchTakePictureIntent()
                    // val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    // resultLauncher.launch(cameraIntent)

                }

                override fun permDenied() {
                }

            })
    }

    private fun dispatchTakePictureIntent() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePicture.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        try {
            photoFile = createImageFile()
            Log.d(TAG, "dispatchTakePictureIntent: //// photofie = "+photoFile)
        } catch (ex: IOException) {

        }
        if (photoFile != null) {
            var photoURI: Uri? = null
            photoURI = if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)) {
                FileProvider.getUriForFile(
                    baseActivity,
                    "com.bhive.beehiveapp",
                    photoFile!!
                )
                //FAApplication.setPhotoUri(photoURI);
            } else {
                Uri.fromFile(photoFile)
            }
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            resultLauncher.launch(takePicture)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = baseActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        Log.d(TAG, "createImageFile: /// timestamp = "+timeStamp)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.d(TAG, "createImageFile: /// current path is "+currentPhotoPath)
        }
    }

    private fun setColor() {
        reportanissueBinding.senderImage.setTextColor(Color.parseColor(ColorPrimary.toString()))
        reportanissueBinding.sendIcon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(ColorPrimary)))
        reportanissueBinding.reportBT.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(ColorPrimary)))

    }

    private fun setSocietyDetails() {
        reportanissueBinding.buildingName.setText(SharedPreferencesClass.getString(requireContext(),"societyName"))
        reportanissueBinding.floorNo.setText(SharedPreferencesClass.getString(requireContext(),"floorNo"))
        setImage(requireContext(), reportanissueBinding.buildingIcon , Constant.IMAGE_BASE_URL+SharedPreferencesClass.getString(requireContext() ,"societyLogo"))
    }

    fun showAlert() {
        confirmationDialog = Dialog(baseActivity)
        confirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        confirmationDialog.setContentView(R.layout.report_submit_confirmation_dialog)
        Objects.requireNonNull(confirmationDialog.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        confirmationDialog.window?.attributes?.windowAnimations = R.style.dialogAnimation
        val dm = DisplayMetrics()
        baseActivity.windowManager.defaultDisplay.getMetrics(dm)
        val width = (dm.widthPixels * 0.9).toInt()
        confirmationDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        confirmationDialog.show()
        val closeV = confirmationDialog.findViewById<ImageView>(R.id.closeV);
        val okCV = confirmationDialog.findViewById<CustomTvRegular>(R.id.okButton);
        okCV.setOnClickListener {
            confirmationDialog.dismiss()
            findNavController().navigate(R.id.reportAnIssueMainFragment)
        }
        closeV.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.reportBT -> {
                showAlert()
            }
            R.id.closeV -> {
                confirmationDialog.dismiss()
            }
            R.id.okButton -> {
                confirmationDialog.dismiss()
            }
        }
    }

        private fun doDelete(image1: ImageView) {
        image1.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))
    }

    private fun showDialog(requireContext: Context) {
        confirmationDialog = Dialog(requireContext)
        confirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        confirmationDialog.setContentView(R.layout.report_submit_confirmation_dialog)
        Objects.requireNonNull(confirmationDialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        confirmationDialog.getWindow()?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        confirmationDialog.window?.attributes?.windowAnimations = R.style.dialogAnimation2
//        dialog.getWindow()?.setFlags(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
        val dm = DisplayMetrics()
        var windowManager : WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        val width = (dm.widthPixels * 0.9).toInt()
        confirmationDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        val okButton = confirmationDialog.findViewById<CustomTvRegular>(R.id.okButton)
        okButton.setOnClickListener {
            confirmationDialog.dismiss()
            findNavController().navigate(R.id.action_reportAnIssueFragment_to_reportAnIssueMainFragment)
        }
    }

    private fun updateFun(reportanissueBinding: FragmentReportanissueBinding) {
        reportanissueBinding.buildingHeading?.text ="Update issue"
        reportanissueBinding.reportBT.setText("UPDATE")
        reportanissueBinding.orLayout.visibility = View.VISIBLE
        reportanissueBinding.closeIssue.visibility = View.VISIBLE
    }
    private fun addFun(reportanissueBinding: FragmentReportanissueBinding) {
        reportanissueBinding.issueTitle.isEnabled= true
        reportanissueBinding.customEditText.isEnabled= true
        reportanissueBinding.buildingHeading?.text ="Report an issue"
        reportanissueBinding.reportBT.setText("SUBMIT")
        reportanissueBinding.orLayout.visibility = View.GONE
        reportanissueBinding.closeIssue.visibility = View.GONE
        reportanissueBinding.openIssue.visibility = View.GONE
    }
    private fun showDetails(reportanissueBinding: FragmentReportanissueBinding){

        reportanissueBinding.buildingHeading?.text ="Issue Details"
        reportanissueBinding.reportBT.setText("CLOSE")
        reportanissueBinding.orLayout.visibility = View.GONE
        reportanissueBinding.closeIssue.visibility = View.GONE
        reportanissueBinding.openIssue.visibility = View.GONE
    }


    fun setData(
        jsonObject: JsonObject,
        reportanissueBinding: FragmentReportanissueBinding,
        context: Context
    )
     {
         val userId  = SharedPreferencesClass.getString(context,"userID")?.toInt()
         reportanissueBinding.floorNo.setText(
             (jsonObject.get("floor").asString)
         )
         var comId = jsonObject.get("complainant_id").asString

         if (userId ==comId.toInt())
         {
             reportanissueBinding.openIssue.visibility=View.VISIBLE
             reportanissueBinding.orLayout.visibility=View.VISIBLE
             reportanissueBinding.closeIssue.visibility=View.VISIBLE
            setIssueDate(jsonObject.get("time").asString, reportanissueBinding)
             reportanissueBinding.buildingHeading.setText("Update issue")
             if (jsonObject.get("messageSize").asInt ==0 )
             {
                 reportanissueBinding.noMessages.visibility = View.VISIBLE
             }
             else{
                 reportanissueBinding.noMessages.visibility = View.GONE
             }
             MessageStatusViewModelOb?.updateMessageStatus(jsonObject.get("issue_id").asString)
             IMAGES_SENT = jsonObject.getAsJsonArray("images").size().toInt()

             if (jsonObject.get("issueStatus").asInt ==1)
             {
                 Log.d(TAG, "setData: /// issue status is open")
                 reportanissueBinding.openIssue.setText("Open")
                 reportanissueBinding.openIssue.visibility = View.VISIBLE
                 reportanissueBinding.closeIssue.visibility = View.VISIBLE
                 reportanissueBinding.closeIssue.setText("Close this issue")
             }
             else{
                 Log.d(TAG, "setData: /// issue status is closed ")
                 reportanissueBinding.closeIssue.setText("Closed")
                 reportanissueBinding.openIssue.setText("Open this issue")
                 reportanissueBinding.openIssue.visibility = View.VISIBLE
             }

             reportanissueBinding.closeIssue.setOnClickListener {
                 var jsn = JsonObject()
                 jsn.addProperty("status", 2)
                 Log.d(TAG, "setData: /// close this issue")
                 Constant.updateIssueViewModel?.updateMessageStatus(jsonObject.get("issueId").toString(),
                     jsn
                 )

                 reportanissueBinding.closeIssue.setText("Closed")
                 reportanissueBinding.closeIssue.setTextColor(resources.getColor(R.color.Red))
                 reportanissueBinding.openIssue.setText("Open this issue")
                 reportanissueBinding.openIssue.visibility = View.VISIBLE


                 }
             reportanissueBinding.openIssue.setOnClickListener {
                 var jsn = JsonObject()
                 jsn.addProperty("status", 1)
                     Constant.updateIssueViewModel?.updateMessageStatus(jsonObject.get("issueId").asString ,
                         jsn)
                 reportanissueBinding.openIssue.setText("Open")
                 reportanissueBinding.openIssue.visibility = View.VISIBLE
                 reportanissueBinding.closeIssue.visibility = View.VISIBLE
                 reportanissueBinding.closeIssue.setText("Close this issue")
                 }

             var jsonImagesArray = jsonObject.getAsJsonArray("images")
             var s = 0
             val ImagesArray = jsonObject.getAsJsonArray("images")

             UPLOADED_IMAGES = ImagesArray.size()
             if (ImagesArray.size()>0)
             {
                 uploadedImages.add(ImagesArray.get(0).toString())


                 Log.d(TAG, "setData: //// uploaded pics array = "+uploadedImages)
                 //1
                 if (ImagesArray.size()>1)
                 { uploadedImages.add(ImagesArray.get(1).toString())
                     //2
                     if (ImagesArray.size()>2)
                     { uploadedImages.add(ImagesArray.get(2).toString())
                         //3
                         if (ImagesArray.size()>3)
                         {
                             uploadedImages.add(ImagesArray.get(3).toString())
                             //4
                             if (ImagesArray.size()>4)
                             {
                                 uploadedImages.add(ImagesArray.get(4).toString())
                                 //5
                             }
                         }
                     }
                 }
             }

             val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
             if (imagesData.size != 0 ){
                 IMAGES_SENT = 1
                 val file = imagesData[0]
                 val surveyBody : RequestBody = file.asRequestBody("images/*".toMediaTypeOrNull())
                 builder.addFormDataPart("images", file.name, surveyBody)
                 this.filePart = MultipartBody.Part.createFormData(
                     "images",
                     file.name,
                     surveyBody)

                 if (imagesData.size>1){
                     IMAGES_SENT=2
                     val file1 = imagesData[1]
                     val surveyBody1 : RequestBody = file1.asRequestBody("images/*".toMediaTypeOrNull())
                     this.filePart1 = MultipartBody.Part.createFormData(
                         "images",
                         file1.name,
                         surveyBody1)

                     if (imagesData.size>2){
                         IMAGES_SENT = 3
                         val file2 = imagesData[2]
                         val surveyBody2 : RequestBody = file2.asRequestBody("images/*".toMediaTypeOrNull())
                         this.filePart2 = MultipartBody.Part.createFormData(
                             "images",
                             file2.name,
                             surveyBody2)
                         if (imagesData.size>3){
                             IMAGES_SENT = 4
                             val file3 = imagesData[3]
                             val surveyBody3 : RequestBody = file3.asRequestBody("images/*".toMediaTypeOrNull())
                             this.filePart3 = MultipartBody.Part.createFormData(
                                 "images",
                                 file3.name,
                                 surveyBody3)
                             if (imagesData.size>4){
                                 IMAGES_SENT = 5
                                 val file4 = imagesData[4]
                                 val surveyBody4 : RequestBody = file4.asRequestBody("images/*".toMediaTypeOrNull())
                                 this.filePart4 = MultipartBody.Part.createFormData(
                                     "images",
                                     file4.name,
                                     surveyBody4)
                             }
                         }
                     }
                 }
             }

             reportanissueBinding.reportBT.setOnClickListener {
                 var title: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                     reportanissueBinding.issueTitle.text.toString())
                 this.title = title
                 var description: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                     reportanissueBinding.customEditText.text.toString())
                 this.description = description

                 //update issue
                 if (imagesData.size != 0 ){
                     IMAGES_SENT = 1
                     val file = imagesData[0]
                     val surveyBody : RequestBody = file.asRequestBody("images/*".toMediaTypeOrNull())
                     builder.addFormDataPart("images", file.name, surveyBody)
                     this.filePart = MultipartBody.Part.createFormData(
                         "images",
                         file.name,
                         surveyBody)

                     if (imagesData.size>1){
                         IMAGES_SENT=2
                         val file1 = imagesData[1]
                         val surveyBody1 : RequestBody = file1.asRequestBody("images/*".toMediaTypeOrNull())
                         this.filePart1 = MultipartBody.Part.createFormData(
                             "images",
                             file1.name,
                             surveyBody1)

                         if (imagesData.size>2){
                             IMAGES_SENT = 3
                             val file2 = imagesData[2]
                             val surveyBody2 : RequestBody = file2.asRequestBody("images/*".toMediaTypeOrNull())
                             this.filePart2 = MultipartBody.Part.createFormData(
                                 "images",
                                 file2.name,
                                 surveyBody2)
                             if (imagesData.size>3){
                                 IMAGES_SENT = 4
                                 val file3 = imagesData[3]
                                 val surveyBody3 : RequestBody = file3.asRequestBody("images/*".toMediaTypeOrNull())
                                 this.filePart3 = MultipartBody.Part.createFormData(
                                     "images",
                                     file3.name,
                                     surveyBody3)
                                 if (imagesData.size>4){
                                     IMAGES_SENT = 5
                                     val file4 = imagesData[4]
                                     val surveyBody4 : RequestBody = file4.asRequestBody("images/*".toMediaTypeOrNull())
                                     this.filePart4 = MultipartBody.Part.createFormData(
                                         "images",
                                         file4.name,
                                         surveyBody4)
                                 }
                             }
                         }
                     }
                 }

                 Log.d(TAG, "setData: //// file part 1 = "+this.filePart)
                 Log.d(TAG, "setData: //// file part 2 = "+this.filePart1)
                 Log.d(TAG, "setData: //// file part 3 = "+this.filePart2)
                 Log.d(TAG, "setData: //// file part 4 = "+this.filePart3)
                 Log.d(TAG, "setData: //// file part 5 = "+this.filePart4)
                 Log.d(TAG, "setData: ///// uploaded images array  = "+uploadedImages)
                 var uploadedImagesArray: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                     uploadedImages.toString())
                 this.uploadedImagesArray = uploadedImagesArray
                 Constant.addIssueViewModel?.updateIssue(jsonObject.get("issueId").asString ,
                     this.title , this.description,
                     this.filePart, this.filePart1 ,this.filePart2  , this.filePart3, this.filePart4 , this.uploadedImagesArray, it)

//
             }
             setAsDisabled(reportanissueBinding.issueTitle)
             setAsDisabled(reportanissueBinding.customEditText)
             setAsEnabled(reportanissueBinding.Image1 , reportanissueBinding.rl1)
             setAsEnabled(reportanissueBinding.Image2, reportanissueBinding.rl2)
             setAsEnabled(reportanissueBinding.Image3, reportanissueBinding.rl3)
             setAsEnabled(reportanissueBinding.Image4, reportanissueBinding.rl4)
             setAsEnabled(reportanissueBinding.Image5, reportanissueBinding.rl5)
             reportanissueBinding.replyMessageLayout.visibility = View.VISIBLE
             reportanissueBinding.closeIssue.visibility = View.VISIBLE
             reportanissueBinding.closeIssue.setOnClickListener {
                 var jsn = JsonObject()
                 jsn.addProperty("status", 2)
                 Log.d(TAG, "setData: // close this")
                 Constant.updateIssueViewModel?.updateMessageStatus(jsonObject.get("issueId").toString(),
                    jsn
                 )
                 reportanissueBinding.openIssue.setText("Open this issue")
                 reportanissueBinding.closeIssue.setText("Closed")
             }
             reportanissueBinding.openIssue.setOnClickListener {
                 var jsn = JsonObject()
                 jsn.addProperty("status", 1)
                 Log.d(TAG, "setData: //// open this")
                 Constant.updateIssueViewModel?.updateMessageStatus(jsonObject.get("issueId").toString(),
                     jsn
                 )
                 reportanissueBinding.openIssue.setText("Open")
                 reportanissueBinding.closeIssue.setText("Close this issue")
             }
         }
         else
         {
             if (jsonObject.getAsJsonArray("images").size() == 0)
             {
                 reportanissueBinding.AddImagesText.visibility = View.GONE
                 reportanissueBinding.uploadImagesButton.visibility = View.GONE
             }
             reportanissueBinding.openIssue.visibility=View.GONE
             reportanissueBinding.orLayout.visibility=View.GONE
             reportanissueBinding.closeIssue.visibility=View.GONE
             reportanissueBinding.buildingHeading.setText("Issue details")
             reportanissueBinding.reportBT.isEnabled = false
             setAsDisabled(reportanissueBinding.issueTitle)
             setAsDisabled(reportanissueBinding.customEditText)
             setAsDisabled(reportanissueBinding.Image1 , reportanissueBinding.rl1)
             setAsDisabled(reportanissueBinding.Image2 ,  reportanissueBinding.rl2)
             setAsDisabled(reportanissueBinding.Image3 , reportanissueBinding.rl3)
             setAsDisabled(reportanissueBinding.Image4 , reportanissueBinding.rl4)
             setAsDisabled(reportanissueBinding.Image5 , reportanissueBinding.rl5)
             reportanissueBinding.replyMessageLayout.visibility = View.GONE
             reportanissueBinding.closeIssue.visibility = View.GONE
         }

        reportanissueBinding.issueTitle.setText(jsonObject.get("title").asString)
        reportanissueBinding.customEditText.setText(jsonObject.get("description").asString)

        var a = 0
        var imageUriFile = jsonObject.getAsJsonArray("images")
         for (i in imageUriFile) {

            var imageUri =
               Constant.IMAGE_BASE_URL + jsonObject.getAsJsonArray("images").get(a).asString


             if (!imageUri.isNullOrEmpty()){
                 if (a==0){
                     setImage(context,reportanissueBinding.Image1,imageUri)
                     Log.d(TAG, "setData: /// image 1 data  ="+reportanissueBinding.Image1.drawable)
                 }
                 if (a==1){
                     setImage(context,reportanissueBinding.Image2,imageUri)
                 }
                 if (a==2){
                     setImage(context,reportanissueBinding.Image3,imageUri)
                 }
                 if (a==3){
                     setImage(context,reportanissueBinding.Image4,imageUri)
                 }
                  if (a==4){
                     setImage(context,reportanissueBinding.Image5,imageUri)
                 }
             }
            a++
        }
    }

    @SuppressLint("SuspiciousIndentation")
//    private fun getBitmapFromLink(url : String) {
//        var  cachedPath: File = File(Environment.getExternalStorageDirectory().path+"./bitmapImages")
//
//        Log.d(TAG, "getBitmapFromLink: ///path = "+cachedPath.mkdir())
//        if (cachedPath.exists()==true)
//        {
//            Log.d(TAG, "getBitmapFromLink: file created = "+cachedPath.path)
//        }
//        else{
//            Log.d(TAG, "getBitmapFromLink: // not created = ")
//        }
////         GetImageFromUrl(Constant.reportAnIssueBinding?.Image1 , url).execute(url);
//        try {
//            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//            val file2 = File(path.toString(),"/"+"image.jpg".toString())
//            val outputStream = FileOutputStream(file2)
//            ReportAnIssueFragment.bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//            outputStream.flush()
//            outputStream.close()
//            Log.d(TAG, "setData: //// images data  file 2 = "+file2)
//            Constant.imagesData.clear()
//            Constant.imagesData.add(file2)
//            fileCheck = file2
//        }
//        catch (e : java.lang.Exception)
//        {
//            Log.d(TAG, "getBitmapFromLink: /// exception occurred = "+e.toString())
//        }
////        ImageUtils.save(url, cachedPath, Bitmap.CompressFormat.PNG);
////        val uri = UriUtils.file2Uri(getFileByPath(cachedPath));
//        Log.d(TAG, "getBitmapFromLink: /// imagre url ="+url)
//
////        val bitmap = BitmapFactory.decodeStream(URL("http://staging.qualhon.net:3088/uploads/issues/images-1675321115066.jpeg").openConnection().getInputStream())
//    }

    private fun createMultiPart(uri : String): MultipartBody.Part? {
        val file = File(uri)
        val surveyBody1 : RequestBody = file.asRequestBody("images/*".toMediaTypeOrNull())
        val filepart = MultipartBody.Part.createFormData(
            "images",
            file.name,
            surveyBody1)
        return filePart
    }


    private fun setIssueDate(createdAt: String, reportanissueBinding: FragmentReportanissueBinding) {
        val pattern = "hh:mm a"
        val instant: Instant = Instant.parse(createdAt)
        val nf: NumberFormat = NumberFormat.getInstance()
        nf.setMinimumIntegerDigits(2)
        val fin: LocalDateTime = LocalDateTime.ofInstant(instant , ZoneId.of("Asia/Kolkata"))
        reportanissueBinding.dateTV.setText(nf.format(fin.dayOfMonth).toString()+"-"+nf.format(fin.monthValue).toString()+"-"+fin.year+"  "+
                fin.format(DateTimeFormatter.ofPattern(pattern)))
    }

    private fun setImage(context: Context, imageView: ImageView, imageUri: String) {
        Glide
            .with(context)
            .load(imageUri)
            .centerCrop()
            .into(imageView)
        imageView.isEnabled = true
    }

    private fun setAsEnabled(image: ImageView, rl1: RelativeLayout) {
        rl1.visibility = View.VISIBLE
        image.isEnabled = true
    }
     private fun setAsDisabled(text: CustomEditTextRegular) {
        text.isEnabled = false
    }
    private fun setAsDisabled(image: ImageView , rl: RelativeLayout) {
        rl.visibility = View.INVISIBLE
        image.isEnabled = false
    }
}

//class GetImageFromUrl(image1: ImageView?, url: String) : AsyncTask<String, Void, Bitmap>() {
//    var url = url
//    override fun doInBackground(vararg p0: String?): Bitmap? {
////        val stringUrl: String = url.get(0)
//         ReportAnIssueFragment.bitmap = null
//        val inputStream: InputStream
//        try {
//            inputStream = URL(url.toString()).openStream()
//            ReportAnIssueFragment.bitmap = BitmapFactory.decodeStream(inputStream)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//
//        return ReportAnIssueFragment.bitmap
//    }
//
//    override fun onPostExecute(result: Bitmap?) {
//        super.onPostExecute(result)
//        Log.d(TAG, "doInBackground: ////// bitmap becomes = "+ReportAnIssueFragment.bitmap)
//        var f = File(ReportAnIssueFragment.bitmap.toString())
//        Constant.imagesData.add(0, f)
//        return
//
//
//
//    }
//
//
//    }

