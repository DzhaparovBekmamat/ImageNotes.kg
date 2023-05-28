package com.example.myapplication.userInterface.fragment.contacts

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.Contacts
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentContactsBinding

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {
    private val adapter: ContactsAdapter by lazy { ContactsAdapter(::call, ::chat) }
    private lateinit var phoneNumber: String

    companion object {
        private const val CALL_PHONE_PERMISSION_REQUEST_CODE = 111
    }

    override fun setUpUI() {
        Toast.makeText(requireContext(), "Мои контакты", Toast.LENGTH_SHORT).show()
        binding.recyclerViewContacts.adapter = adapter
        if (ActivityCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(android.Manifest.permission.READ_CONTACTS), 111
            )
        } else {
            getContacts()
        }
    }

    @SuppressLint("Range", "Recycle")
    private fun getContacts() {
        val list = arrayListOf<ContactsModel>()
        val contentResolver = requireActivity().contentResolver
        val cursor = contentResolver.query(
            Contacts.CONTENT_URI, null, null, null, Phone.DISPLAY_NAME
        )
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val id = cursor.getString(cursor.getColumnIndex(Contacts._ID))
                    val name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME))
                    val numberCursor = contentResolver.query(
                        Phone.CONTENT_URI, null, Phone.CONTACT_ID + " =?", arrayOf(id), null
                    )
                    if (numberCursor != null && numberCursor.moveToFirst()) {
                        phoneNumber =
                            numberCursor.getString(numberCursor.getColumnIndex(Phone.NUMBER))
                    }
                    list.add(
                        ContactsModel(
                            name = name, phoneNumber = phoneNumber
                        )
                    )
                    numberCursor?.close()
                }
            }
            cursor.close()
            requireActivity().runOnUiThread {
                adapter.submitList(list)
            }
        }
    }

    private fun chat(number: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$number")
        startActivity(intent)
    }

    private fun call(number: String) {
        val permission = android.Manifest.permission.CALL_PHONE
        if (ActivityCompat.checkSelfPermission(
                requireContext(), permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            makeCall(number)
        } else {
            requestPermission(permission, CALL_PHONE_PERMISSION_REQUEST_CODE)
        }
    }

    private fun makeCall(number: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    private fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall(phoneNumber)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Невозможно совершить звонок. Разрешение не предоставлено.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
