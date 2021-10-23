package com.example.myweather.lesson9

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myweather.databinding.FragmentContentProviderBinding

class ContentProviderFragment : Fragment() {

    // представление xml файла в виде кода
    private var _binding: FragmentContentProviderBinding? = null // промежуточный
    private val binding: FragmentContentProviderBinding
        get() {
            return _binding!!
        }


    // резервация для статических методов
    companion object {

        // метод, который используется для создания экземпляра класса
        fun newInstance() = ContentProviderFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkPermission() {
        context?.let {
            // обращение к Permission из манифеста (проверка на доступ)
            if (ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getContacts()
                // запрос на рационализацию
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder(it)
                    .setTitle("Нужен доступ к контактам")
                    .setMessage("Иначе ничего не получится")
                    .setPositiveButton("Выдать разрешение") { dialog, which ->
                        myRequestPermission()
                    }
                    .setNegativeButton("Не выдать") { dialog, which ->
                        dialog.dismiss()
                    }
                    .create().show()
            } else {
                myRequestPermission()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Что-то новое")
                            .setMessage("Что-то новое")
                            .setPositiveButton("Выдать разрешение") { dialog, which ->
                                myRequestPermission()
                            }
                            .setNegativeButton("Не выдать") { dialog, which ->
                                dialog.dismiss()
                            }
                            .create().show()
                    }
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    val REQUEST_CODE = 999;

    private fun myRequestPermission() {
        // типовая функция получения разрешения - получаем массив - deprecated
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 999)
        // работаем с каждым результатом отдельно - более предпочительная версия
        // разовый запрос к контактам
        /*val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                getContacts()
            } else {
                // плакать
            }
        }
        launcher.launch(Manifest.permission.READ_CONTACTS)

        // несколько запросов  к контактам
        val launcherMulti =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                result.entries.forEach {
                    if (it.value) {
                        getContacts()
                    } else {
                        // плакать
                    }
                }
            }

        launcherMulti.launch(arrayOf(Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA))

         */
    }

    private fun getContacts() {

        context?.let {

            val contentResolver:ContentResolver = it.contentResolver;
            val cursor:Cursor? = contentResolver.query(ContactsContract.Contacts.CONTENT_URI
                ,null,null,null,ContactsContract.Contacts.DISPLAY_NAME + " ASC")

            cursor?.let {
                for(i in 0..it.count){
                    // аналог "Следующий"
                    if (cursor.moveToPosition(i)){
                        val name  = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        binding.containerForContacts.addView(TextView(context).apply {
                            text = name
                            textSize = 30f
                        })
                    }

                }
            }
        }
    }

}