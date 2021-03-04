package br.com.eduardotanaka.btgchallenge.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FilmeViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    private val fragmentList = ArrayList<Fragment>()

    override fun getItemCount(): Int = fragmentList.size

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun createFragment(position: Int): Fragment =
        fragmentList[position]
}
