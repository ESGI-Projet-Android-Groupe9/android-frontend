package com.esgi.groupe9.frontend

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.entity.User
import com.esgi.groupe9.frontend.utils.Constants
import com.esgi.groupe9.frontend.utils.Constants.FIREBASE_FIRESTORE
import com.esgi.groupe9.frontend.utils.DummyData


class RegisterFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Set button to signUn user
        signUpUser(view)
        // Set button to navigate to LoginFragment
        goOnLoginFragment(view)
        return view
    }

    // Check if the password and confirmPassword are equivalent
    private fun checkPasswordEquality(view: View) {
        val confirmPassword = view.findViewById<EditText>(R.id.confirm_password_input_register)
        val password = view.findViewById<EditText>(R.id.password_input_register)
        confirmPassword.addTextChangedListener {
            val registerButton = view.findViewById<Button>(R.id.create_account_button)
            val password = view.findViewById<EditText>(R.id.password_input_register)
            if (confirmPassword.text.toString() == password.text.toString()) {
                registerButton.isEnabled = true
                registerButton.background.alpha = 255
                registerButton.setTextColor(Color.WHITE)
            } else {
                registerButton.isEnabled = false
                registerButton.background.alpha = 100
                registerButton.setTextColor(Color.GRAY)
            }
        }
        password.addTextChangedListener {
            val registerButton = view.findViewById<Button>(R.id.create_account_button)
            val password = view.findViewById<EditText>(R.id.password_input_register)
            if (confirmPassword.text.toString() == password.text.toString()) {
                registerButton.isEnabled = true
                registerButton.background.alpha = 255
                registerButton.setTextColor(Color.WHITE)
            } else {
                registerButton.isEnabled = false
                registerButton.background.alpha = 100
                registerButton.setTextColor(Color.GRAY)
            }
        }
    }

    // Check if the inputs are validit and correct of the RegisterFragment
    private fun checkInputRegister(email: String, password: String, username: String): Boolean {
        when {
            email.isEmpty() && password.isNotEmpty() && username.isNotEmpty() -> {
                Toast
                    .makeText(activity, "Please fill the email input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            password.isEmpty() && email.isNotEmpty() && username.isNotEmpty() -> {
                Toast
                    .makeText(activity, "Please fill the password input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            username.isEmpty() && email.isNotEmpty() && password.isNotEmpty() -> {
                Toast
                    .makeText(activity, "Please fill the username input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            email.isEmpty() && password.isEmpty() && username.isEmpty() -> {
                Toast
                    .makeText(activity, "Please fill all input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            email.isEmpty() && password.isEmpty() -> {
                Toast
                    .makeText(
                        activity,
                        "Email & Password are required to create account",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                return false
            }
        }
        return true
    }

    // Set button to signUn user
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun signUpUser(view: View) {
        val registerButton = view.findViewById<Button>(R.id.create_account_button)

        // Check the password and confirmPassword
        checkPasswordEquality(view)

        // Set onClick on registerButton
        registerButton?.setOnClickListener {
            val username =
                view.findViewById<EditText>(R.id.username_input_register)?.text.toString()
            val email = view.findViewById<EditText>(R.id.email_input_register)?.text.toString()
            val passwordEditText = view.findViewById<EditText>(R.id.password_input_register)
            val password = passwordEditText?.text.toString()
            // Check the validity of the parameters
            if (!checkInputRegister(email, password, username)) return@setOnClickListener
            // Display gray blue bordure when password is editing
            passwordEditText.setOnClickListener {
                passwordEditText.setBackgroundResource(R.color.gray_blue)
            }
            // Create and save a new user using Firebase
            Log.d(TAG, "Try to register a User to the Firebase Authentication")
            Constants.FIREBASE_AUTH.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    val userId = it.result.user?.uid.toString()
                    Log.d(
                        TAG,
                        "Successfully created user with the UserID : ${it.result.user?.uid}"
                    )
                    // Save the User in Firebase Database
                    saveUserToDatabase(view, userId, username, email)
                    // Navigate (or Return) to Login Page
                    redirectOnLogin()
                }
                .addOnFailureListener {
                    var passwordMessageError = "${it.message}"
                    Log.d(TAG, "Failed to create user due to : ${it.message}")
                    Toast
                        .makeText(
                            activity,
                            "Failed to create user due to : ${it.message}",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    if(passwordMessageError=="The given password is invalid. [ Password should be at least 6 characters ]"){
                       passwordEditText.setBackgroundResource(R.drawable.button_border_red)
                        val icon = resources.getDrawable(R.drawable.warning)
                        icon?.setBounds(
                            0, 0,
                            icon.intrinsicWidth,
                            icon.intrinsicHeight
                        )
                        passwordEditText.setError("The given password is invalid.", icon);
                    }
                }
        }
    }

    // Save a User with the indicated parameters from Firebase
    private fun saveUserToDatabase(view: View, userId: String, username: String, email: String) {
        val likesList = listOf<Game>()
        val wishlist = listOf<Game>()
        val user = User(userId, username, email, likesList, wishlist)

        Log.d(TAG, "Try to add a User to the FireStore Database")
        FIREBASE_FIRESTORE.collection("users")
            .document(userId)
            .set(user)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d(
                    TAG,
                    "User has been inserted in database with the id : $userId"
                )
            }
            .addOnFailureListener {
                Log.w(
                    TAG,
                    "User has not been inserted in db because of ${it.message}"
                )
            }
    }

    // Set button to navigate to LoginFragment
    private fun goOnLoginFragment(view: View) {
        val backButton = view.findViewById<Button>(R.id.return_login_page)
        backButton.setOnClickListener {
            // Navigate to LoginFragment
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }

    // Navigate (or Return) to LoginFragment
    private fun redirectOnLogin() {
        // Navigate to LoginFragment
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    companion object {
        private const val TAG: String = "RegisterFragment"
    }
}
