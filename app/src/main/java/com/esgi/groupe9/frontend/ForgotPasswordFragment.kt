package com.esgi.groupe9.frontend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.esgi.groupe9.frontend.utils.Constants

class ForgotPasswordFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        resetPassword(view)

        // Set on click on return button to LoginFragment
        goOnLoginFragment(view)
        return view
    }

    private fun resetPassword(view: View) {
        val resetPasswordButton = view?.findViewById<Button>(R.id.forgot_password_button)

        resetPasswordButton?.setOnClickListener {
            val email = view?.findViewById<EditText>(R.id.forgot_password_email_input)?.text.toString()

            if (!checkInputForgot(email)) return@setOnClickListener

            Log.d(TAG, "Try to send an email for reset password to the User")
            Constants.FIREBASE_AUTH.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d(
                        TAG,
                        "Successfully send email to the user with the email : $email"
                    )
                    Toast
                        .makeText(
                            activity,
                            "Successfully send email to the user with the email : $email",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    redirectOnLogin()
                }
                .addOnFailureListener {
                    Log.d(
                        TAG,
                        "Error while sending the email due to : ${it.message}"
                    )
                    Toast
                        .makeText(
                            activity,
                            "Error while sending the email due to : ${it.message}",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        }
    }

    private fun checkInputForgot(email: String): Boolean {
        when {
            email.isEmpty() -> {
                Toast
                    .makeText(activity, "Please fill the email input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        return true
    }

    // Set button to navigate to LoginFragment
    private fun goOnLoginFragment(view: View) {
        val returnButton = view.findViewById<Button>(R.id.return_login_page_forgot)
        returnButton.setOnClickListener {
            // Navigate to LoginFragment
            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
        }
    }

    // Navigate (or Return) to LoginFragment
    private fun redirectOnLogin() {
        // Navigate to LoginFragment
        findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
    }

    companion object {
        private const val TAG: String = "ForgotPasswordFragment"
    }
}