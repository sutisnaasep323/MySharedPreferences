package com.asep.mysharedpreferences.preferencessettingscreen

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.Toast
import androidx.preference.*
import com.asep.mysharedpreferences.R

class MyPreferenceFragment: PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var NAME: String
    private lateinit var EMAIL: String
    private lateinit var AGE: String
    private lateinit var PHONE: String
    private lateinit var LOVE: String
    private lateinit var NIGHT: String
    private lateinit var FONT: String
    private lateinit var namePreference: EditTextPreference
    private lateinit var emailPreference: EditTextPreference
    private lateinit var agePreference: EditTextPreference
    private lateinit var phonePreference: EditTextPreference
    private lateinit var isLoveMuPreference: CheckBoxPreference
    private lateinit var isNightPreference: SwitchPreference
    private lateinit var fontPreference: ListPreference

    companion object {
        private const val DEFAULT_VALUE = "Tidak Ada"
        private const val DEFAULT_FONT = "Sedang"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //connect ui dengan preferences
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()

    }

    //inisialisasi
    private fun init() {
        NAME = resources.getString(R.string.key_name)
        EMAIL = resources.getString(R.string.key_email)
        AGE = resources.getString(R.string.key_age)
        PHONE = resources.getString(R.string.key_phone)
        LOVE = resources.getString(R.string.key_love)
        NIGHT = resources.getString(R.string.key_night)
        FONT = resources.getString(R.string.key_font)
        namePreference = findPreference<EditTextPreference> (NAME) as EditTextPreference
        emailPreference = findPreference<EditTextPreference>(EMAIL) as EditTextPreference
        agePreference = findPreference<EditTextPreference>(AGE) as EditTextPreference
        phonePreference = findPreference<EditTextPreference>(PHONE) as EditTextPreference
        isLoveMuPreference = findPreference<CheckBoxPreference>(LOVE) as CheckBoxPreference
        isNightPreference = findPreference<SwitchPreference>(NIGHT) as SwitchPreference
        fontPreference = findPreference<ListPreference>(FONT) as ListPreference
    }

    //untuk merubah summary/value nya dengan mengambil nilai dari sh
    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        fontPreference = findPreference(getString(R.string.key_font))!!
        fontPreference?.setOnPreferenceChangeListener { _, newValue ->
            val selectedValue = newValue as String
            Toast.makeText(requireContext(), "Ukuran font diubah menjadi $selectedValue", Toast.LENGTH_SHORT).show()
            true
        }

        phonePreference.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_PHONE
            editText.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                val validChars = "0123456789"
                if (source.any { it !in validChars }) {
                    ""
                } else {
                    source
                }
            })
        }

        if (sh != null){
            namePreference.summary = sh.getString(NAME, DEFAULT_VALUE)
            emailPreference.summary = sh.getString(EMAIL, DEFAULT_VALUE)
            agePreference.summary = sh.getString(AGE, DEFAULT_VALUE)
            phonePreference.summary = sh.getString(PHONE, DEFAULT_VALUE)
            isLoveMuPreference.isChecked = sh.getBoolean(LOVE, false)
            isNightPreference.isChecked = sh.getBoolean(NIGHT, false)
            fontPreference?.summary = sh.getString(FONT, DEFAULT_FONT)
        }
    }

    /*
    untuk memantau apakah terjadi perubahan data? jika ya, panggil onSharedPreferenceVhanged
    agar data langsung diperbarui. untuk mendapatkan nilai yang berubah, dicocokkan key-nya
     */
    override fun onSharedPreferenceChanged(sh: SharedPreferences, key: String) {
        if (key == NAME) {
            namePreference.summary = sh.getString(NAME, DEFAULT_VALUE)
        }
        if (key == EMAIL) {
            emailPreference.summary = sh.getString(EMAIL, DEFAULT_VALUE)
        }
        if (key == AGE) {
            agePreference.summary = sh.getString(AGE, DEFAULT_VALUE)
        }
        if (key == PHONE) {
            phonePreference.summary = sh.getString(PHONE, DEFAULT_VALUE)
        }
        if (key == LOVE) {
            isLoveMuPreference.isChecked = sh.getBoolean(LOVE, false)
        }
        if (key == NIGHT) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val isNight = sh.getBoolean(NIGHT, false)
            sharedPreferences.edit().putBoolean(NIGHT, isNight).apply()
        }
        if (key == FONT){
            fontPreference.summary = sh.getString(FONT, DEFAULT_VALUE)
        }
    }

    /*
    Kode berikut ini digunakan untuk me-register ketika aplikasi dibuka dan me-unregister ketika
    aplikasi ditutup. Hal ini supaya listener tidak berjalan terus menerus dan menyebabkan memory leak.
     */
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

}