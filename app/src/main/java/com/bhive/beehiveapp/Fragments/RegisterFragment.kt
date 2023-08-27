package com.bhive.beehiveapp.Fragments

import   android.content.ContentValues.TAG
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.DropDownSpinnerAdapter
import com.bhive.beehiveapp.databinding.FragmentRegisterationPageBinding
import com.bhive.beehiveapp.models.SpinnerItemsModelClass
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.utils.fonts.CustomTvLight
import com.bhive.beehiveapp.viewmodel.RegisterationViewModel
import com.bhive.beehiveapp.viewmodel.SocietiesViewModel
import com.google.gson.JsonObject


class RegisterFragment  : BaseFragment() {

    private lateinit var registerationPageBinding: FragmentRegisterationPageBinding
    lateinit var registerationViewModel: RegisterationViewModel
    lateinit var societiesViewModel: SocietiesViewModel
//    lateinit var societyDetailsViewModel: SocietyDetailsViewModel
    lateinit var companion: Constant.Companion
    lateinit var jsonObject: JsonObject
    var spinner_items = ArrayList<SpinnerItemsModelClass> ()
    var ColorPrimary : String ? = null

    companion object{
        var BUILDING_ID : Int ?=0
        var FLOORS : Int ?=0
        var DATA_LOADED :Int = 0
        var REGISTERED_SUCCESS :Int = 0
        var binding : FragmentRegisterationPageBinding?= null
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerationPageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_registeration_page,
            container,
            false
        )
        return registerationPageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var aa=0
        binding = registerationPageBinding
        val spinner = registerationPageBinding.buildingSpinner
//        societyDetailsViewModel = ViewModelProvider(this).get(SocietyDetailsViewModel::class.java)
        societiesViewModel = ViewModelProvider(this).get(SocietiesViewModel::class.java)
        registerationViewModel = ViewModelProvider(this).get(RegisterationViewModel::class.java)
        registerationViewModel.responseInterface = this
        if (FLOORS!=0){
//            Toast.makeText(requireContext() , " floors ="+ FLOORS,Toast.LENGTH_LONG).show()
        }
        societiesViewModel.getSocieties(spinner,requireContext())

        Handler().postDelayed({

//            registerationPageBinding.progressBar.visibility = View. GONE
        }, 2000)

        societiesViewModel.responseInterface=this
//        val p: Pattern = Pattern.compile("[6-9][0-9]{9}")


        var socities = resources.getStringArray(R.array.Socities)
        registerationPageBinding.loginTV.setOnClickListener {

            findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }
        registerationPageBinding.signupBT.setOnClickListener {

            userSignup(view)

            Handler().postDelayed({
                if (DATA_LOADED==1){
                    registerationPageBinding.progressbar1.visibility = View.GONE
                }
            }, 2000)
        }
        registerationPageBinding.backIV.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }
        fun setUpFloor(){
        }
    }

    fun setData(context: Context,spinner_items : ArrayList<SpinnerItemsModelClass>,spinner: Spinner) {
        Log.d(TAG, "setData: "+spinner_items)
        this.spinner_items = spinner_items
        setUpSpinner(context,spinner,spinner_items)
    }

    private fun userSignup(view: View)
    {
        val aa =0
        if (registerationPageBinding.firstNameET.text?.length ?: String == 0){
            registerationPageBinding.firstNameET.error = "First name can't be empty"
            registerationPageBinding.firstNameET.requestFocus()
        }
        else if (registerationPageBinding.lastNameET.text?.length ?: String == 0) {
            registerationPageBinding.lastNameET.error = "Last name can't be empty"
            registerationPageBinding.lastNameET.requestFocus()
        }
        else if (BUILDING_ID == 0)
        {
            Toast.makeText(context,"Please select your building",Toast.LENGTH_SHORT).show()
        }
        else if (registerationPageBinding.emailET.text?.length ?: String==0) {
            registerationPageBinding.emailET.error = "Email can't be empty"
            registerationPageBinding.emailET.requestFocus()
        }
        else if (!isEmailValid(registerationPageBinding.emailET.text.toString())) {
            registerationPageBinding.emailET.error = "Please enter valid email"
            registerationPageBinding.emailET.requestFocus()
        }
        else if (registerationPageBinding.floorET.text?.length ?: String ==0){
            registerationPageBinding.floorET.error = "Please enter floor number"
            registerationPageBinding.floorET.requestFocus()
        }

        else if (registerationPageBinding.floorET.text.toString().toInt() == 0 || registerationPageBinding.floorET.text.toString().toInt() > FLOORS!!){
            registerationPageBinding.floorET.error = "Please enter valid floor number"
            Log.d(TAG, "userSignup: //// entered floor = "+registerationPageBinding.floorET.text.toString().toInt()
            +"  max floors = "+ FLOORS)
            Log.d(TAG, "userSignup: entered > Floor = "+(registerationPageBinding.floorET.text.toString().toInt() > FLOORS!!))
            Log.d(TAG, "userSignup: entered < Floor = "+(registerationPageBinding.floorET.text.toString().toInt() < FLOORS!!))
            Log.d(TAG, "userSignup: entered == Floor = "+(registerationPageBinding.floorET.text.toString().toInt() == FLOORS!!))
            Log.d(TAG, "userSignup: entered ==0 = "+(registerationPageBinding.floorET.text.toString().toInt() == 0))
            registerationPageBinding.floorET.requestFocus()
        }
        else if (registerationPageBinding.phoneNumberET.text?.length ?: String==0) {
            registerationPageBinding.phoneNumberET.error = "Phone number can't be empty"
            registerationPageBinding.phoneNumberET.requestFocus()
        }
        else if (registerationPageBinding.phoneNumberET.text.toString().length!=10 )
        {
            registerationPageBinding.phoneNumberET.error = "Please enter valid phone number"
            registerationPageBinding.phoneNumberET.requestFocus()
        }
        else if (registerationPageBinding.passwordET.text?.length ?: String==0) {
            registerationPageBinding.passwordET.error = "Password can't be empty"
            registerationPageBinding.passwordET.requestFocus()
        }
        else if (registerationPageBinding.confirmPasswordET.text?.length ?: String==0) {
            registerationPageBinding.confirmPasswordET.error = "Confirm password can't be empty"
            registerationPageBinding.confirmPasswordET.requestFocus()
        }
        else if ( registerationPageBinding.passwordET.text.toString() < 8.toString()) {
            registerationPageBinding.passwordET.error = "Password must contain at least 7 characters including Upper/Lower case, 1 numeric digit and 1 special character"
            registerationPageBinding.passwordET.requestFocus()
        }
        else if ( registerationPageBinding.confirmPasswordET.text.toString() < 8.toString()) {
            registerationPageBinding.passwordET.error = "Confirm password must contain at least 7 characters including Upper/Lower case, 1 numeric digit and 1 special character"
            registerationPageBinding.passwordET.requestFocus()
        }
        else if (!isValidPassword(registerationPageBinding.passwordET.text.toString()) ) {
            registerationPageBinding.passwordET.error = "Password must contain at least 7 characters including Upper/Lower case, 1 numeric digit and 1 special character"
            registerationPageBinding.passwordET.requestFocus()
        }
        else if (registerationPageBinding.passwordET.text.toString() != registerationPageBinding.confirmPasswordET.text.toString()){
            registerationPageBinding.confirmPasswordET.error = "Passwords don't match"
            registerationPageBinding.confirmPasswordET.requestFocus()
        }
        else {
            registerationPageBinding.progressbar1.visibility = View.VISIBLE
            Constant.FLOOR_NO = registerationPageBinding.floorET.text.toString()
            jsonObject = JsonObject()
            jsonObject.addProperty("first_name", registerationPageBinding.firstNameET.text.toString())
            jsonObject.addProperty("last_name", registerationPageBinding.lastNameET.text.toString())
            jsonObject.addProperty("password", registerationPageBinding.passwordET.text.toString())
            jsonObject.addProperty("email", registerationPageBinding.emailET.text.toString())
            jsonObject.addProperty("phone", registerationPageBinding.phoneNumberET.text.toString())
            jsonObject.addProperty("society_id", BUILDING_ID)
            jsonObject.addProperty("floor_number", registerationPageBinding.floorET.text.toString().toInt())
            jsonObject.addProperty("device_token", MainActivity.firebaseToken)
            Log.d(TAG, "userSignup: /// json paassws  = "+jsonObject)
            registerationViewModel.register(jsonObject,view,baseActivity)
            if (REGISTERED_SUCCESS ==1)
            {
                registerationPageBinding.progressbar1.visibility = View.GONE
            }
        }
    }


    fun setUpSpinner(context : Context,spinner: Spinner,spinner_items : ArrayList<SpinnerItemsModelClass>) {
        Log.d(TAG, "setUpSpinner: "+spinner_items)
        Log.d(TAG, "setUpSpinner: spinner is "+spinner)

        val adapter = DropDownSpinnerAdapter(
            context,
            spinner_items
        )

        var itemsSize = spinner_items.size
        spinner.adapter = adapter
        var i =1

        if (spinner != null) {
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {

                    Log.d(TAG, "onItemSelected:  pos //"+pos)

                    Log.d(TAG, "onItemSelected: vale at pos in model //"+ (parent?.getItemAtPosition(pos).toString()))

                    Log.d(TAG, "onItemSelected:  itemsize //"+itemsSize)

                    val selectedStatus: SpinnerItemsModelClass = parent!!.getSelectedItem() as SpinnerItemsModelClass
                    val selectedBuildingId: Int = selectedStatus.id!!
                    val selectedBuildingName: String = selectedStatus.society_name
                    val selectedBuildingFloors : String = selectedStatus.floors.toString()
                    Log.d(TAG, "onItemSelected: /// buidling = "+selectedBuildingName+"// has floors ="+ RegisterFragment.FLOORS)

                    Log.d(TAG, "onItemSelected: item id ///"+selectedBuildingId)
                    Log.d(TAG, "onItemSelected: item name ///"+selectedBuildingName)
                    Log.d(TAG, "onItemSelected: floors count =  ///"+selectedBuildingFloors)
                    FLOORS = selectedBuildingFloors.toInt()
                    binding?.floorsCount?.setText("Max count is "+FLOORS.toString())

                    binding?.floorsCount?.visibility  = View.VISIBLE
                    Constant.SOCIETY_ID = selectedBuildingId.toString()
                    BUILDING_ID = selectedBuildingId
//                 Toast.makeText(,"id = "+ BUILDING_ID,Toast.LENGTH_LONG).show()

//                    societyDetailsViewModel.getBuildingDetails(
//                        BUILDING_ID.toString())
//
//                 Toast.makeText(requireContext(),"floors = "+ FLOORS
//                     ,Toast.LENGTH_LONG).show()

//                    var regBinding : FragmentRegisterationPageBinding?= null
//                    fragmentManager
//                    val view : View = layoutInflater.inflate(R.layout.fragment_registeration_page,null)
//                    regBinding = FragmentRegisterationPageBinding.bind(view)
//                    regBinding.floorET.append(FLOORS.toString())
//
//                    val binding = FragmentRegisterationPageBinding.inflate(LayoutInflater.from(context), this)

//                    if (FLOORS!=0){
//                        binding?.floorsCount?.setText("Max count is "+FLOORS.toString())
//                        binding?.floorsCount?.visibility  = View.VISIBLE
//                    }

                    parent?.selectedView?.findViewById<CustomTvLight>(R.id.text)
                        ?.setTextColor(ContextCompat.getColor(context,R.color.black))
                    Log.d(TAG, "onItemSelected: "+parent?.getItemAtPosition(pos))
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Log.d(TAG, "onNothingSelected: ")
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpFloor() {
        Log.d(TAG, "setUpFloor: /// id is"+registerationPageBinding.buildingSpinner)
    }
}


