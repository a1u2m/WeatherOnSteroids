package com.example.weatheronsteroids.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.ui.main.MainActivity
import com.example.weatheronsteroids.utils.secrettextview.SecretTextView
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"

    private lateinit var inputName: TextInputEditText
    private lateinit var becomeIncognito: AppCompatButton
    private lateinit var incognitoCongrats: SecretTextView
    private lateinit var launchCount: AppCompatTextView
    private lateinit var timeCount: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun humanTime(time: Int): String {
        val hours: Int = time / 3600
        val minutes: Int = (time - (hours * 3600)) / 60
        val seconds: Int = time - hours * 3600 - minutes * 60

        val sb = StringBuilder()
        if (hours == 0) {
            sb.append("00:")
        } else {
            sb.append("$hours:")
        }
        if (minutes == 0) {
            sb.append("00:")
        } else {
            sb.append("$minutes:")
        }
        if (seconds == 0) {
            sb.append("00")
        } else {
            sb.append("$seconds")
        }
        return sb.toString()
    }

    private fun initViews() {
        incognitoCongrats = requireActivity().findViewById(R.id.incognito_congrats)

        inputName = requireActivity().findViewById(R.id.edit_name)
        inputName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val name = inputName.text.toString()
                (activity as MainActivity).mainPresenter.sharedPreferencesHelper.setName(name)
            }
            return@setOnEditorActionListener false
        }

        becomeIncognito = requireActivity().findViewById(R.id.become_incognito)
        becomeIncognito.setOnClickListener {
            (activity as MainActivity).mainPresenter.sharedPreferencesHelper.clearName()
            incognitoCongrats.show()
            hideGreetings()
        }

        launchCount = requireActivity().findViewById(R.id.launch_count)
        launchCount.text =
            "${resources.getString(R.string.app_launch_count)} ${(activity as MainActivity).mainPresenter.sharedPreferencesHelper.getLaunch()}"

        timeCount = requireActivity().findViewById(R.id.time_count)
        timeCount.text =
            "${resources.getString(R.string.app_time_count)} ${humanTime((activity as MainActivity).mainPresenter.sharedPreferencesHelper.getTime())}"
    }

    private fun hideGreetings() {
        Flowable
            .interval(1, TimeUnit.SECONDS)
            .take(3)
            .takeLast(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Long>() {

                override fun onNext(t: Long?) {
                    //do nothing
                }

                override fun onComplete() {
                    incognitoCongrats.hide()
                }

                override fun onError(t: Throwable?) {
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    } else {
                        Log.d(TAG, "onError: t == null")
                    }
                }
            })
    }
}