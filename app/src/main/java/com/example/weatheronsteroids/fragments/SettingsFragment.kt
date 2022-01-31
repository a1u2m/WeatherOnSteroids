package com.example.weatheronsteroids.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.secrettextview.SecretTextView
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"

    lateinit var inputName: TextInputEditText
    lateinit var becomeIncognito: AppCompatButton
    lateinit var incognitoCongrats: SecretTextView
    lateinit var launchCount: AppCompatTextView
    lateinit var timeCount: AppCompatTextView

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

    private fun countLaunch(): Int {
        val sp = requireActivity().getPreferences(AppCompatActivity.MODE_PRIVATE)
        return sp.getInt(getString(R.string.launch_count_key), 0)
    }

    private fun initViews() {
        incognitoCongrats = requireActivity().findViewById(R.id.incognito_congrats)

        inputName = requireActivity().findViewById(R.id.edit_name)
        inputName.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val name = inputName.text.toString()
                val sp = activity?.getPreferences(Context.MODE_PRIVATE)
                with(sp?.edit()) {
                    this?.putString(getString(R.string.user_name_key), name)
                    this?.apply()
                }
            }
            return@setOnEditorActionListener false
        }

        becomeIncognito = requireActivity().findViewById(R.id.become_incognito)
        becomeIncognito.setOnClickListener {
            val sp = activity?.getPreferences(Context.MODE_PRIVATE)
            with(sp?.edit()) {
                this?.remove(getString(R.string.user_name_key))
                this?.apply()
            }
            incognitoCongrats.show()
            hideGreetings()
        }

        launchCount = requireActivity().findViewById(R.id.launch_count)
        launchCount.text = "${resources.getString(R.string.app_launch_count)} ${countLaunch()}"

        timeCount = requireActivity().findViewById(R.id.time_count)
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
                    }
                }
            })
    }
}