package store.devshcherbinavv.cinemasearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import store.devshcherbinavv.cinemasearch.databinding.FragmentWatchLaterBinding
import store.devshcherbinavv.cinemasearch.utils.AnimationHelper


class WatchLaterFragment : Fragment() {
    private lateinit var binding: FragmentWatchLaterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWatchLaterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.watchLaterFragmentRoot, requireActivity(), 3)
    }

}