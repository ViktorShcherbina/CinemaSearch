package store.devshcherbinavv.cinemasearch.view.fragments

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import store.devshcherbinavv.cinemasearch.databinding.FragmentSelectionsBinding
import store.devshcherbinavv.cinemasearch.utils.AnimationHelper

class SelectionsFragment : Fragment() {
    private lateinit var binding: FragmentSelectionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSelectionsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.selectionsFragmentRoot, requireActivity(), 4)
    }

}