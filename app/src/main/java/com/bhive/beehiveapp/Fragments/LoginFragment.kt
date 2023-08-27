package com.bhive.beehiveapp.Fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri.encode
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.Fragments.ABC
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentLoginPageBinding
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.viewmodel.SignInViewModel
import com.google.gson.JsonObject
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64
import java.io.UnsupportedEncodingException
import java.net.URLEncoder.encode
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.Security
import java.util.*
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec


class LoginFragment : BaseFragment() {

    lateinit var loginPageBinding: FragmentLoginPageBinding
    lateinit var signInViewModel: SignInViewModel
    lateinit var companion: Constant.Companion
    lateinit var jsonObject: JsonObject
    var mainContext: Context? = null
    var abc :ABC?=null
    var ColorPrimary : String ? = null

    private var flag : Int = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginPageBinding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_login_page, container, false)
        return loginPageBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        signInViewModel.responseInterface = this
        loginPageBinding.progressBar.visibility = View. GONE

        val tokk = SharedPreferencesClass.getString(requireContext(),"token")
        if(tokk != null)
        {
            Constant.ACCESS_TOKEN = tokk
            val email = SharedPreferencesClass.getString(requireContext(),"email")
            val password = SharedPreferencesClass.getString(requireContext(),"password")
            val role = SharedPreferencesClass.getString(requireContext(),"role")
            jsonObject = JsonObject()
            jsonObject.addProperty("email",email)
            jsonObject.addProperty("password", password)
            jsonObject.addProperty("role",role)
            signInViewModel.signIn( jsonObject , view  , baseActivity)
        }
        loginPageBinding.forgotTV.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment2_to_registerFragment)
        }

        loginPageBinding.signinBT.setOnClickListener {

            if (loginPageBinding.emailET.text?.length ?: String == 0)
            {
                loginPageBinding.emailET.error = "Email can't be empty"
                loginPageBinding.emailET.requestFocus()
            }
            else if (!isEmailValid(loginPageBinding.emailET.text.toString()))
            {
                loginPageBinding.emailET.error = "Please enter valid email"
                loginPageBinding.emailET.requestFocus()
            }else if (loginPageBinding.passwordET.text?.length ?: String == 0)
            {
                loginPageBinding.passwordET.error = "Password can't be empty"
                loginPageBinding.passwordET.requestFocus()
            }else if (!isValidPassword(loginPageBinding.passwordET.text.toString()) )
            {
                loginPageBinding.passwordET.error = "Password must contain at least 6 characters including Upper/Lower case, 1 numeric digit and 1 special character"
                loginPageBinding.passwordET.requestFocus()
            }
            else if (loginPageBinding.passwordET.text.toString() < 8.toString())
            {
                loginPageBinding.passwordET.error = "Password must contain at least 6 characters including Upper/Lower case, 1 numeric digit and 1 special character"
                loginPageBinding.passwordET.requestFocus()
            }
            else
            {
                jsonObject = JsonObject()
                jsonObject.addProperty("email", loginPageBinding.emailET.text.toString())
                jsonObject.addProperty("password", loginPageBinding.passwordET.text.toString())
                jsonObject.addProperty("role", 3)
                jsonObject.addProperty("device_token", MainActivity.firebaseToken)

                SharedPreferencesClass.saveString(requireContext(),"email", loginPageBinding.emailET.text.toString())
                SharedPreferencesClass.saveString(requireContext(),"password", loginPageBinding.passwordET.text.toString())
                SharedPreferencesClass.saveString(requireContext(),"role", 3.toString())
                var encryptedText : String =
                    encrypt(loginPageBinding.passwordET.text.toString(), Constant.ENCRYPT_KEY.toString()).toString()
                Log.d(TAG, "onViewCreated: ////text encrypted" +encryptedText)
                signInViewModel.signIn(jsonObject, view,baseActivity)
            }
        }
        loginPageBinding.forgotTV.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment2_to_forgotPasswordFragment)
        }
        loginPageBinding.createaccountTV.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_registerFragment)
        }
    }

    fun encrypt(strToEncrypt: String, secret_key: String): String? {
        Security.addProvider(BouncyCastleProvider())
        var keyBytes: ByteArray

        try {
            keyBytes = secret_key.toByteArray(charset("UTF8"))
            val skey = SecretKeySpec(keyBytes, "AES")
            val input = strToEncrypt.toByteArray(charset("UTF8"))

            synchronized(Cipher::class.java) {
                val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, skey)

                val cipherText = ByteArray(cipher.getOutputSize(input.size))
                var ctLength = cipher.update(
                    input, 0, input.size,
                    cipherText, 0
                )
                ctLength += cipher.doFinal(cipherText, ctLength)
                Log.d(TAG, "encrypt: ///"+ String(Base64.encode(cipherText)))
                return String(
                    Base64.encode(cipherText)
                )
            }
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        } catch (ibse: IllegalBlockSizeException) {
            ibse.printStackTrace()
        } catch (bpe: BadPaddingException) {
            bpe.printStackTrace()
        } catch (ike: InvalidKeyException) {
            ike.printStackTrace()
        } catch (nspe: NoSuchPaddingException) {
            nspe.printStackTrace()
        } catch (nsae: NoSuchAlgorithmException) {
            nsae.printStackTrace()
        } catch (e: ShortBufferException) {
            e.printStackTrace()
        }
        return null
    }

}