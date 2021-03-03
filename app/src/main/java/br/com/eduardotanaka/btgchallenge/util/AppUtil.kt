package br.com.eduardotanaka.btgchallenge.util

import android.os.Looper

fun onMainThread() = Looper.myLooper() == Looper.getMainLooper()
