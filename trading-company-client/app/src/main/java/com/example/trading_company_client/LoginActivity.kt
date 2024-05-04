package com.example.trading_company_client

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trading_company_client.data.model.requests.LoginRequest
import com.example.trading_company_client.data.repository.LoginRequestRepositoryImpl
import com.example.trading_company_client.domain.usecase.LoginRequestUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var loginInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private val loginRequestUseCase = LoginRequestUseCase(LoginRequestRepositoryImpl())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, MainActivity::class.java)

        job = Job()

        loginInput = findViewById(R.id.editLogin)
        passwordInput = findViewById(R.id.editPassword)
        loginButton = findViewById(R.id.buttonAuth)

        loginButton.setOnClickListener {

            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()
            val loginRequest = LoginRequest(login, password)

            launch {
                try {
                    val response = loginRequestUseCase.employeeLogin(loginRequest)

                    if (response.success) {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}