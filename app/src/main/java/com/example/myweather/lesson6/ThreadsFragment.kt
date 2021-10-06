package com.example.myweather.lesson6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.fragment_threads.*
import ru.geekbrains.lesson_1423_2_2_main.databinding.FragmentThreadsBinding
import java.util.*
import java.util.concurrent.TimeUnit

const val TEST_BROADCAST_INTENT_FILTER = "TEST BROADCAST INTENT FILTER"
const val THREADS_FRAGMENT_BROADCAST_EXTRA = "THREADS_FRAGMENT_EXTRA"

class ThreadsFragment : Fragment() {

    private var _binding: FragmentThreadsBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        fun newInstance() = ThreadsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val timer = binding.editText.text.toString().toInt()
            startCalculations(timer)
        }

        val myHandler1991 = Handler(Looper.myLooper()!!) // текущий поток

        Thread {

            binding.calcThreadBtn.setOnClickListener {
                val timer = binding.editText.text.toString().toInt()

                startCalculations(timer)
                //val myHandler1992 = Handler(Looper.myLooper()!!) // текущий поток
                val myHandler1993 = Handler(Looper.getMainLooper()) // главный поток
                // помещаем в хандлер действие
                myHandler1991.post(Runnable {
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "TextSize"
                        textSize = 30f
                    })
                })
                /*myHandler1992.post(Runnable {
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "Thread myHandler1992"
                        textSize = 30f
                    })
                })*/
                myHandler1993.post(Runnable {
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "Thread myHandler1993"
                        textSize = 30f
                    })
                })

            }
        }.start()

        val handlerThread = MyThread()
        handlerThread.start()
        binding.calcThreadHandlerBtn.setOnClickListener {
            // проверка на null
            val handler = handlerThread.mHandler
            handler?.post{
                val timer = binding.editText.text.toString().toInt()
                startCalculations(timer)
                myHandler1991.post(Runnable {
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "handler Thread myHandler1992"
                        textSize = 30f
                    })
                })
            }
        }
        handlerThread.mHandler?.looper?.quitSafely() // ожидает завершения всех задач
        handlerThread.mHandler?.looper?.quit() // выход без завершения задач

        binding.btnService.setOnClickListener {
            context?.let {
                val intent = Intent(it,MainService::class.java)
                intent.putExtra(MAIN_SERVICE_STRING_EXTRA, "Привет сервис, я фрагмент")
                it.startService(intent)
            }
        }

        binding.btnServiceBroadcast.setOnClickListener {
            context?.let {
                val intent = Intent(it,MainService::class.java)
                intent.putExtra(MAIN_SERVICE_STRING_EXTRA, "Привет сервис, я фрагмент")
                it.startService(intent)
            }
        }

        // регистрация ресивера - как метод Оповестить()
        //requireActivity().registerReceiver(receiver, IntentFilter(TEST_BROADCAST_INTENT_FILTER))
        // регистрация локального ресивера (только в пределах своего приложения)
        //LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(receiver,IntentFilter(TEST_BROADCAST_INTENT_FILTER))

    }

    private fun startCalculations(timer: Int) {
        val currentTime = Date().time
        while (currentTime + timer * 1000 > Date().time) {
        }
    }

}

class MyThread:Thread(){

    var mHandler:Handler?=null
    override fun run() {
        Looper.prepare()
        mHandler = Handler(Looper.myLooper()!!)
        Looper.loop()
        super.run()
    }
}