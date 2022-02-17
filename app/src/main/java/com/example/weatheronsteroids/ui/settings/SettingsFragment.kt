package com.example.weatheronsteroids.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.di.App
import com.example.weatheronsteroids.utils.secrettextview.SecretTextView
import com.google.android.material.textfield.TextInputEditText
import moxy.MvpAppCompatFragment

class SettingsFragment : MvpAppCompatFragment(R.layout.fragment_settings), SettingsView {

    private val TAG = "SettingsFragment"

    private lateinit var inputName: TextInputEditText
    private lateinit var becomeIncognito: AppCompatButton
    private lateinit var incognitoCongrats: SecretTextView
    private lateinit var launchCount: AppCompatTextView
    private lateinit var timeCount: AppCompatTextView

    lateinit var presenter: SettingsPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = (activity?.application as App).appComponent.getSettingsPresenter()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
    }

    private fun init() {
        presenter.attachView(this)
        incognitoCongrats = requireActivity().findViewById(R.id.incognito_congrats)
        inputName = requireActivity().findViewById(R.id.edit_name)
        inputName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val name = inputName.text.toString()
                presenter.setName(name)
            }
            return@setOnEditorActionListener false
        }

        becomeIncognito = requireActivity().findViewById(R.id.become_incognito)
        becomeIncognito.setOnClickListener {
            presenter.onBecomeIncognitoClicked()
        }

        launchCount = requireActivity().findViewById(R.id.launch_count)
        launchCount.text =
            "${resources.getString(R.string.app_launch_count)} ${presenter.getLaunchCount()}"

        timeCount = requireActivity().findViewById(R.id.time_count)
        timeCount.text =
            "${resources.getString(R.string.app_time_count)} ${presenter.humanTime()}"
    }

    override fun hideMessage() {
        incognitoCongrats.hide()
    }

    override fun showMessage() {
        incognitoCongrats.show()
    }
}