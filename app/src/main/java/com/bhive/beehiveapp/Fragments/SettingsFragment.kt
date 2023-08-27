package com.bhive.beehiveapp.Fragments

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentSettingsBinding
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import com.bhive.beehiveapp.viewmodel.DeleteAccountViewModel
import com.bhive.beehiveapp.viewmodel.SettingsProfileViewModel
import java.util.*

class SettingsFragment : BaseFragment() {
    lateinit var dialog: Dialog
    private lateinit var settingsBinding: FragmentSettingsBinding
    private lateinit var settingsProfileViewModel : SettingsProfileViewModel
    private lateinit var deleteAccountViewModel: DeleteAccountViewModel
    var ColorPrimary : String ? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )
        return settingsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsProfileViewModel = ViewModelProvider(this).get(SettingsProfileViewModel::class.java)
        deleteAccountViewModel = ViewModelProvider(this).get(DeleteAccountViewModel::class.java)
        settingsProfileViewModel.userProfile(settingsBinding)

        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        setColor()
        settingsBinding.changePasswordIcon.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_changePasswordFragment)
        }
        settingsBinding.changePasswordText.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_changePasswordFragment)
        }
        settingsBinding.editProfileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_profilePageFragment)
        }
        settingsBinding.editProfileText.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_profilePageFragment)
        }
        settingsBinding.deleteAccount.setOnClickListener{

            showAlertDialogDeleteAccount()


        }
//        settingsBinding.signoutIcon.setOnClickListener {
//            showAlertDialogLeave()
//
//        }
//        settingsBinding.signoutText.setOnClickListener {
//            showAlertDialogLeave()
//        }

    }

    private fun showAlertDialogDeleteAccount () {

        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.alert_dialog_delete_account)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window?.attributes?.windowAnimations = R.style.dialogAnimation2
//        dialog.getWindow()?.setFlags(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        val width = (dm.widthPixels * 0.9).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()

        val titleTV = dialog.findViewById<CustomTvSemiBold>(R.id.titleTV)
        val leaveTV = dialog.findViewById<CustomTvRegular>(R.id.leaveTV)
        val cancelTV = dialog.findViewById<CustomTvRegular>(R.id.cancelTV)
        leaveTV.setOnClickListener {

            dialog.dismiss()
            SharedPreferencesClass.cleanPref(requireContext())
            deleteAccountViewModel.deleteUserAccount(requireContext())
            findNavController().navigate(R.id.action_settingsFragment_to_login_fragment)
            Handler().postDelayed({
//                Toast.makeText(requireContext() , "Account deleted successfully", Toast.LENGTH_SHORT).show()
            }, 1500)
        }
        cancelTV.setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun setColor() {
        settingsBinding.changePasswordIcon.imageTintList = (ColorStateList.valueOf(Color.parseColor(ColorPrimary)))
        settingsBinding.editProfileIcon.imageTintList = (ColorStateList.valueOf(Color.parseColor(ColorPrimary)))

    }

    private fun showAlertDialogLeave() {

        Log.d(ContentValues.TAG, "openDialog: //////")
        val customDialog = Dialog(requireActivity())
        customDialog.setContentView(R.layout.alert_dialog_custom_view)
        customDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val leaveButton = customDialog.findViewById(R.id.leaveTV) as CustomTvRegular
        val cancelButton = customDialog.findViewById(R.id.cancelTV) as CustomTvRegular
        leaveButton.setOnClickListener {
            //Do something here
            customDialog.dismiss()
            SharedPreferencesClass.cleanPref(requireContext())
            Handler().postDelayed({
                findNavController().navigate(R.id.splashFragment2)
            }, 1500)
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        customDialog.show()
    }

}