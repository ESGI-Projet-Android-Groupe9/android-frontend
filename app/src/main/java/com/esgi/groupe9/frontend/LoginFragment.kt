package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.esgi.groupe9.frontend.utils.Constants
import com.esgi.groupe9.frontend.utils.Constants.FIREBASE_AUTH

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

        // Check if the user is connected
        checkIfUserConnected()
        // Set button to signIn user
        signInUser(view)
        // Set button to Navigate to RegisterFragment
        goOnRegisterPage(view)
        // Set button to Navigate to ForgotPasswordFragment
        goOnForgotPasswordPage(view)

        return view
    }

    // Set button to signIn user
    private fun signInUser(view: View?) {
        val loginButton = view?.findViewById<Button>(R.id.login_button)
        loginButton?.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.email_input_login)?.text.toString()
            val password = view.findViewById<EditText>(R.id.password_input_login)?.text.toString()

            // check the input of the user in the LoginFragment
            if (!checkInput(email, password)) return@setOnClickListener

            // Login using Firebase
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
                    ).show()
                }
        }
    }

    // Check if the input for login are valid or correct
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

    // Navigate to HomeFragment
    private fun goOnHomePage() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    // Set button to Navigate to RegisterFragment
    private fun goOnRegisterPage(view: View) {
        val registerButton = view.findViewById<Button>(R.id.register_button)
        registerButton.setOnClickListener {
            // Navigate to RegisterFragment
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    // Set button to Navigate to ForgotPasswordFragment
    private fun goOnForgotPasswordPage(view: View) {
        val forgotLinkPassword = view.findViewById<TextView>(R.id.forgot_password_link)
        forgotLinkPassword.setOnClickListener {
            // Navigate to ForgotPasswordFragment
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
    }

    private fun checkIfUserConnected() {
        val user = FIREBASE_AUTH.currentUser
        if (user != null) {
            goOnHomePage()
        } else {
            return
        }
    }

    companion object {
        private const val TAG: String = "LoginFragment"
    }
}