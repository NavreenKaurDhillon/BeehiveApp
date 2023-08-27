package com.bhive.beehiveapp.Fragments

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.NotificationsAdapter
import com.bhive.beehiveapp.databinding.FragmentNotificationsBinding
import com.bhive.beehiveapp.interfaces.NotificationsInterface
import com.bhive.beehiveapp.models.Notifications
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import com.bhive.beehiveapp.viewmodel.NotificationsViewModel
import java.util.*
import kotlin.collections.ArrayList

class NotificationsFragment : BaseFragment() , NotificationsInterface{
    lateinit var dialog: Dialog
    var notificationsInterface : NotificationsInterface?= null
    val notificationsAdapter = NotificationsAdapter()
    open var arrayList = ArrayList<Notifications>()
    lateinit var notificationsViewModel: NotificationsViewModel
     lateinit var notificationsBinding: FragmentNotificationsBinding
     var ColorPrimary : String ? = null
    var mainActivity = MainActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notifications,
            container,
            false
        )
        return notificationsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        Constant.notificationsBinding = notificationsBinding
       notificationsViewModel= ViewModelProvider(this).get(NotificationsViewModel::class.java)

        notificationsViewModel.markNotificationsRead(requireContext())
        notificationsViewModel.getNotificationsCount()
        mainActivity.setBadges()

        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        Constant.notificationsViewModel = notificationsViewModel
        this.arrayList = notificationsViewModel.getNotificationsList(notificationsBinding)
        Log.d(TAG, "onViewCreated: /// aaaray "+this.arrayList)

        notificationsAdapter.notificationsInterface=this
    }

    private fun submitList(
        arrayList: ArrayList<Notifications>,
        notificationsBinding: FragmentNotificationsBinding
    ) {
        this.arrayList = arrayList
        var notificationsAdapter = NotificationsAdapter()
        notificationsBinding.recyclerView.adapter=notificationsAdapter
        notificationsAdapter.submitList(arrayList)
    }

      override fun deleteNotification(position: Int, id: String)
    {
        Log.d(TAG, "deleteNotification: ///"+arrayList)
//        arrayList.removeAt(position)
        Constant.updatedNotificationsList?.removeAt(position)
        Constant.notificationsViewModel!!.deleteANotification(id)
        Log.d(TAG, "deleteNotification: /// updated list"+ Constant.updatedNotificationsList)

        submitList(Constant.updatedNotificationsList!!, Constant.notificationsBinding!!)
//        notificationsViewModel.deleteANotification(position.toString(), requireContext())
//        submitList(arrayList, notificationsBinding)
    }

    fun setAdapter(
        notificationsList: ArrayList<Notifications>,
        notificationsBinding: FragmentNotificationsBinding)
    {
        submitList(notificationsList,notificationsBinding)
    }

    fun showDeleteNotificationDialog(position: Int, id: String, context: Context) {

            dialog = Dialog(context)
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

                notificationsInterface?.deleteNotification(position, id)
            }
            cancelTV.setOnClickListener {
                dialog.dismiss()
            }
        }

    }