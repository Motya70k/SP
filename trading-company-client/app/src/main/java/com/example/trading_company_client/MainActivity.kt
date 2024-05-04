package com.example.trading_company_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trading_company_client.data.EmployeeRecycleViewAdapter
import com.example.trading_company_client.data.ItemRecyclerViewAdapter
import com.example.trading_company_client.data.model.requests.AddItemRequest
import com.example.trading_company_client.data.model.requests.RegisterRequest
import com.example.trading_company_client.data.repository.EmployeeRepositoryImpl
import com.example.trading_company_client.data.repository.ItemRepositoryImpl
import com.example.trading_company_client.databinding.ActivityMainBinding
import com.example.trading_company_client.databinding.CreateItemDialogLayoutBinding
import com.example.trading_company_client.databinding.RegisterEmployeeDialogLayoutBinding
import com.example.trading_company_client.domain.usecase.EmployeeUseCase
import com.example.trading_company_client.domain.usecase.ItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var employeeAdapter = EmployeeRecycleViewAdapter()
    var itemAdapter = ItemRecyclerViewAdapter()
    private lateinit var activityBinding: ActivityMainBinding
    private var selectedMenuItem: Int = R.id.employeeItem
    private val employeeUseCase = EmployeeUseCase(EmployeeRepositoryImpl())
    private val itemUseCase = ItemUseCase(ItemRepositoryImpl())

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        selectedMenuItem = item.itemId
        when (selectedMenuItem) {
            R.id.employeeItem -> loadEmployees()
            R.id.itemItem -> loadItems()
            // Добавьте обработку для остальных пунктов меню
        }
        return true
    }

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        job = Job()

        employeeAdapter = EmployeeRecycleViewAdapter()
        itemAdapter = ItemRecyclerViewAdapter()

        initRecyclerView(employeeAdapter) // Устанавливаем адаптер для сотрудников по умолчанию

        activityBinding.button1.setOnClickListener {
            when (selectedMenuItem) {
                R.id.employeeItem -> showDialogForRegisterEmployee()
                R.id.itemItem -> showDialogForCreateItem()
                // Добавьте обработку для остальных пунктов меню
            }
        }
        activityBinding.button2.setOnClickListener {
            when (selectedMenuItem) {
                R.id.employeeItem -> showDialogForUpdateEmployee()
                R.id.itemItem -> showDialogForUpdateEmployee()
                // Добавьте обработку для остальных пунктов меню
            }
        }
    }

    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>) {
        activityBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        activityBinding.recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun showDialogForRegisterEmployee() {
        val dialogBinding = RegisterEmployeeDialogLayoutBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle("Добавление сотрудника")
            .setPositiveButton("Добавить") { dialog, _ ->

                val login = dialogBinding.loginEditText.text.toString()
                val password = dialogBinding.passwordEditText.text.toString()
                val name = dialogBinding.nameEditText.text.toString()
                val lastname = dialogBinding.lastnameEditText.text.toString()
                val phone = dialogBinding.phoneEditText.text.toString()
                val role = dialogBinding.roleEditText.text.toString()

                val registerRequest = RegisterRequest(login, password, name, lastname, phone, role)

                launch {
                    try {
                        employeeUseCase.registerEmployee(registerRequest)

                        val employees = employeeUseCase.getAllEmployees()

                        employeeAdapter.submitList(employees)
                        Toast.makeText(
                            this@MainActivity,
                            "Пользователь успешно зарегистрирован",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            "Ошибка при регистрации пользователя: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun showDialogForUpdateEmployee() {
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.update_employee_dialog_layout, null)
        val editText1 = dialogView.findViewById<EditText>(R.id.editText11)
        val editText2 = dialogView.findViewById<EditText>(R.id.editText22)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Dialog for Menu Item 2")
            .setPositiveButton("OK") { dialog, _ ->
                val text1 = editText1.text.toString()
                val text2 = editText2.text.toString()
                // Здесь можно использовать введенные данные
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun showDialogForCreateItem() {
        val dialogBinding = CreateItemDialogLayoutBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle("Добавление товара")
            .setPositiveButton("Добавить") { dialog, _ ->

                val name = dialogBinding.itemNameEditText.text.toString()
                val quantity = dialogBinding.quantityEditText.text.toString().toIntOrNull()

                if (name.isNotBlank() && quantity != null) {
                    val addItemRequest = AddItemRequest(0, name, quantity)

                    launch {
                        try {
                            itemUseCase.addItem(addItemRequest)

                            val items = itemUseCase.getAllItems()

                            itemAdapter.submitList(items)

                            Toast.makeText(
                                this@MainActivity,
                                "Товар успешно добавлен",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@MainActivity,
                                "Ошибка при добавлении товара: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Пожалуйста, заполните все поля корректно",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    @OptIn(UnstableApi::class)
    private fun loadEmployees() {
        launch {
            try {
                val employees = employeeUseCase.getAllEmployees()
                Log.d("MainActivity", "Загружено сотрудников: ${employees.size}")
                employeeAdapter.submitList(employees)
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Ошибка при загрузке сотрудников: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @OptIn(UnstableApi::class)
    private fun loadItems() {
        launch {
            try {
                val items = itemUseCase.getAllItems()
                Log.d("MainActivity", "Загружено товаров: ${items.size}")
                itemAdapter.submitList(items)
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Ошибка при загрузке товаров: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

/*
private fun showDialogForSelectedMenuItem(menuItem: Int) {
        // Показываем диалоговое окно в зависимости от выбранного пункта меню
        when (selectedMenuItem) {
            R.id.action_item1 -> showDialogForRegisterEmployee()
            R.id.action_item2 -> showDialogForUpdateEmployee()
            // Добавьте обработку для остальных пунктов меню
        }
    }
 */