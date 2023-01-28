package com.esgi.groupe9.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.esgi.groupe9.frontend.utils.Constants

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        signInUser(view)
        goOnRegisterPage(view)
        goOnForgotPasswordPage(view)
        return view
    }

    private fun signInUser(view: View?) {
        val loginButton = view?.findViewById<Button>(R.id.login_button)
        loginButton?.setOnClickListener {
            val email = view?.findViewById<EditText>(R.id.email_input_login)?.text.toString()
            val password = view?.findViewById<EditText>(R.id.password_input_login)?.text.toString()

            // check the input of the user in the LoginActivity
            if (!checkInput(email, password)) return@setOnClickListener

            Log.d("LoginActivity", "Attempt login with Email : $email")
            Constants.FIREBASE_AUTH.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d(
                        TAG,
                        "Successfully login user with the UserID : ${it.result.user?.uid}"
                    )
                    goOnHomePage()
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to login user due to : ${it.message}")
                    Toast.makeText(
                        activity,
                        "Failed to login user due to : ${it.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
        }
    }

    private fun checkInput(email: String, password: String): Boolean {
        when {
            email.isEmpty() && password.isNotEmpty() -> {
                Toast
                    .makeText(activity, "Please fill the email input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            password.isEmpty() && email.isNotEmpty() -> {
                Toast
                    .makeText(activity, "Please fill the password input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            email.isEmpty() && password.isEmpty() -> {
                Toast
                    .makeText(activity, "Please fill both input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        return true
    }

    private fun goOnHomePage() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun goOnRegisterPage(view: View) {
        val registerButton = view.findViewById<Button>(R.id.register_button)
        registerButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun goOnForgotPasswordPage(view: View) {
        val forgotLinkPassword = view.findViewById<TextView>(R.id.forgot_password_link)
        forgotLinkPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
    }

    companion object {
        private const val TAG: String = "LoginFragment"
    }
}