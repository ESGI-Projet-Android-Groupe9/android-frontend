package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.esgi.groupe9.frontend.utils.DummyData
import com.esgi.groupe9.frontend.viewers.VPAdapter
import com.google.android.material.tabs.TabLayout

class GameDetailFragment : Fragment(R.layout.fragment_game_details) {
    private val args: GameDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)

        // Set the GameDetailFragment toolbar
        setGameDetailToolbar(view)

        // Set the tabLayout of gameDetailFragment
        setTabLayout(view)

        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        // Set header Game in GameDetailFragment
        setHeaderGame(itemView)
        val gameDetailToolbar = view?.findViewById<Toolbar>(R.id.game_detail_toolbar)
        gameDetailToolbar?.setNavigationOnClickListener {
            findNavController().navigate(GameDetailFragmentDirections.actionGameDetailFragmentToHomeFragment())
        }
    }

    // Create the different options in the toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_game_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Set on Action of onSelect an option
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.game_detail_like -> {
                // TODO Add action onClick to add this item game in the likes list of the user
                //  and modify the view to have a filled like icon
                return true
            }
            R.id.game_detail_favorite -> {
                // TODO Add action onClick to add this item game in the wishlist of the user
                //  and modify the view to have a filled wishlist icon
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Set the game_detail_toolbar to our current activity
    private fun setGameDetailToolbar(view: View){
        val gameDetailToolbar = view.findViewById<Toolbar>(R.id.game_detail_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(gameDetailToolbar)
    }

    // Set header Game in GameDetailFragment
    private fun setHeaderGame(itemView:View){
        val game = args.gameItem

        view?.findViewById<TextView>(R.id.game_name_item_detail)?.text = game.name

        view?.findViewById<TextView>(R.id.game_editor_item_detail)?.text = game.editor.toString()

        // TODO to fix
        //view?.findViewById<TextView>(R.id.game_description_detail)?.text = game.detailedDescription

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

    // Set the tabLayout of gameDetailFragment
    private fun setTabLayout(view: View){
        val tabLayout = view.findViewById<TabLayout>(R.id.game_detail_tablayout)
        val viewPager = view.findViewById<ViewPager>(R.id.game_detail_view_pager)

        tabLayout?.setupWithViewPager(viewPager)

        val vpAdapter =
            activity?.let { VPAdapter(it.supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) };

        val gameDescriptionVarFrag: String = args.gameItem.detailedDescription

        vpAdapter?.addFragment(GameDescriptionFragment().apply {
            Log.d("GameDetailFragment", gameDescriptionVarFrag)
            gameDescription = gameDescriptionVarFrag
        }, "DESCRIPTION");


        vpAdapter?.addFragment(GameReviewsFragment(), "AVIS");
        if (viewPager != null) {
            viewPager.adapter = vpAdapter
        }
    }

    companion object {
        private const val TAG: String = "GameDetailFragment"
    }
}