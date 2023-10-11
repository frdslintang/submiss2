package com.dicoding.submiss2.settings

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submiss2.R
import com.dicoding.submiss2.databinding.ActivityThemeSettingsBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class ThemeSettingsActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivityThemeSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        viewModel = ViewModelProvider(this@ThemeSettingsActivity).get(SettingViewModel::class.java)

        viewModel.getThemeSettings().observe(this){ isDarkMode ->
            if (isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }
}