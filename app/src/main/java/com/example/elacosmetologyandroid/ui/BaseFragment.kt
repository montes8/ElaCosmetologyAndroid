package com.example.elacosmetologyandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData

abstract class BaseFragment() : Fragment() {
    private var transactionHistory: ArrayList<Int> = ArrayList()
    private var currentChildFragment: BaseFragment? = null

    abstract fun getMainView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View

    abstract fun setUpView()
    abstract fun observeLiveData()
    abstract fun getViewModel(): BaseViewModel?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = getMainView(inflater, container)
        setUpView()
        observeMainViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLiveData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeMainViewModel() {
        if (activity is BaseActivity) {
            val context = activity as BaseActivity
            getViewModel()?.let { viewModel ->
                viewModel.loadingLiveData.observe(context, { isLoading ->
                    isLoading?.let {
                        (activity as BaseActivity).showLoading(it)
                    }
                })

                viewModel.errorLiveData.observe(context,{
                    (activity as BaseActivity).showDialogError(it)
                })
            }
        }
    }

    private fun validateOnlyOneItemOnNavigation(): Boolean =
        (this.childFragmentManager.backStackEntryCount <= 1)

    private fun validateCurrentFragment() {
        if (!this.childFragmentManager.fragments.isNullOrEmpty()) {
            for (i in this.childFragmentManager.fragments) {
                if (i.isVisible) {
                    this.currentChildFragment = i as BaseFragment
                }
            }
        }
    }

    fun backToFirstFragmentOfNavigation() {
        this.childFragmentManager.let {
            if (!validateOnlyOneItemOnNavigation()) {
                if (this.transactionHistory.size > 1) {
                    it.popBackStackImmediate(
                        this.transactionHistory[1],
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    val historySize: Int = this.transactionHistory.lastIndex
                    for (i in historySize downTo 1) {
                        this.transactionHistory.removeAt(i)
                    }
                    this.validateCurrentFragment()
                }
            }
        }
    }

    fun backStackFragmentFromNavigation(): Boolean {
        this.childFragmentManager.let {
            return if (!validateOnlyOneItemOnNavigation()) {
                it.popBackStackImmediate()
                this.validateCurrentFragment()
                true
            } else false
        }
    }

}