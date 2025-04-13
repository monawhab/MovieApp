package com.movieapp.utils.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.movieapp.utils.showSnackBar


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T


abstract class BaseFragment<BB : ViewBinding>(
    private val inflate: Inflate<BB>,
) : Fragment() {

    private var _binding: BB? = null
    val binding get() = _binding!!

    abstract val viewModel: BaseViewModel

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(
            inflater,
            container,
            false
        )

        val frameLayout = FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            addView(_binding?.root)
        }

        addProgressBar(frameLayout)

        return frameLayout
    }


    private fun addProgressBar(viewGroup: ViewGroup?) {
        progressBar = ProgressBar(requireContext())

        progressBar.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { gravity = android.view.Gravity.CENTER }

        progressBar.visibility = View.GONE
        viewGroup?.addView(progressBar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            val error = it.asString(requireContext())
            requireActivity().showSnackBar(requireView(), error)
        }

        //        viewModel.isAuthError.observe(viewLifecycleOwner) {
        //            if (it) {
        //                // TODO Goto login
        //                // TODO Clear Shared Preference
        //            }
        //        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun navigateTo(navigationAction: Int) {
        findNavController().navigate(navigationAction)
    }

    fun navigateToWithBundle(navigationAction: Int, bundle: Bundle) {
        findNavController().navigate(navigationAction, bundle)
    }
}