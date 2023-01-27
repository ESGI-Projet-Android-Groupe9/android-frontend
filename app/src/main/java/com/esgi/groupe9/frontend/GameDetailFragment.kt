package com.esgi.groupe9.frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.esgi.groupe9.frontend.utils.DummyData
import com.esgi.groupe9.frontend.viewers.VPAdapter
import com.google.android.material.tabs.TabLayout

class GameDetailFragment : Fragment() {
    //private val args: GameDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)

        val tabLayout = view?.findViewById<TabLayout>(R.id.game_detail_tablayout)
        val viewPager = view?.findViewById<ViewPager>(R.id.game_detail_view_pager)

        tabLayout?.setupWithViewPager(viewPager)

        val vpAdapter =
            activity?.let { VPAdapter(it.supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) };
        vpAdapter?.addFragment(GameDescriptionFragment(), "DESCRIPTION");
        vpAdapter?.addFragment(GameReviewsFragment(), "AVIS");
        if (viewPager != null) {
            viewPager.adapter = vpAdapter
        }
        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        //val game = args.gameItem
        val game = DummyData.DUMMY_GAME

//        val aboutTheGame = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
//        val shortDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
//        val price = 10.00,

        view?.findViewById<TextView>(R.id.game_name_item_detail)?.text = game.name

        view?.findViewById<TextView>(R.id.game_editor_item_detail)?.text = game.editor.toString()

        // TODO to fix
        view?.findViewById<TextView>(R.id.game_description_detail)?.text = game.detailedDescription

        val gameItemImageDetail = view?.findViewById<ImageView>(R.id.game_image_item_detail)
        if (gameItemImageDetail != null) {
            Glide.with(itemView).load(game.image).into(gameItemImageDetail)
        }
        val gameItemBackgroundDetail = view?.findViewById<ImageView>(R.id.game_background_item_detail)
        if (gameItemBackgroundDetail != null) {
            Glide.with(itemView).load(game.background).into(gameItemBackgroundDetail)
        }
        val gameItemBackgroundHeadDetail = view?.findViewById<ImageView>(R.id.game_background_detail)
        if (gameItemBackgroundHeadDetail != null) {
            Glide.with(itemView).load(game.background).into(gameItemBackgroundHeadDetail)
        }

    }
}