package com.iSay1.roamstick.bottomNavigation

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.falcon.evCharger.setup.SetupFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.bottomNavigation.viewModel.BottomNavigationMenuViewModel
import com.iSay1.roamstick.dashboard.DashboardFragment
import com.iSay1.roamstick.databinding.FragmentMenuBinding
import com.iSay1.roamstick.property.PropertyFragment

class MenuFragment : HomeBaseFragment() {

    private lateinit var fragmentMenuBinding: FragmentMenuBinding
    private val bottomNavigationMenuViewModel: BottomNavigationMenuViewModel by activityViewModels()

    var navController: NavController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity!!.registerOnBackPress(null)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMenuBinding = FragmentMenuBinding.inflate(inflater, container, false)

        mActivity?.let { bottomNavigationMenuViewModel.init(it) }

        fragmentMenuBinding.viewModel = bottomNavigationMenuViewModel

        //        fragment = new CallFragment();

        mActivity!!.unRegisterOnBackPress()

        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())

        fragmentMenuBinding.pager.adapter = pagerAdapter
        fragmentMenuBinding.pager.isUserInputEnabled = false
        fragmentMenuBinding.pager.offscreenPageLimit = 1

        bottomNavigation()


        fragmentMenuBinding.propertyFab.setOnClickListener {

            val menu: Menu = fragmentMenuBinding.bottomNavigation.menu
            val menuitem = menu.getItem(1)
            menuitem.isChecked = false

            fragmentMenuBinding.propertyFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_900))
            fragmentMenuBinding.pager.setCurrentItem(1, false)

        }

        /*fragmentMenuBinding.propertyFab.setOnClickListener(v -> {

            Menu menu = fragmentMenuBinding . bottomNavigation . getMenu ();
            MenuItem menuitem = menu . getItem (2);
            menuitem.setChecked(false);

        });*/

        return fragmentMenuBinding!!.root
    }

    private fun bottomNavigation() {
        val menu = fragmentMenuBinding!!.bottomNavigation.menu
        val menuitem = menu.getItem(0)
        menuitem.isChecked = true

        fragmentMenuBinding.bottomNavigation.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.navigation_dashboard -> {

                            fragmentMenuBinding.propertyFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.greyscale_900))
                            fragmentMenuBinding.pager.setCurrentItem(0, false)
                        }

                        R.id.navigation_property -> {
                            fragmentMenuBinding.propertyFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_900))
                            fragmentMenuBinding.pager.setCurrentItem(1, false)
                        }

                        R.id.navigation_setup -> {
                            fragmentMenuBinding.propertyFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.greyscale_900))
                            fragmentMenuBinding.pager.setCurrentItem(2, false)
                        }

                    }
                    true
                })


        /*fragmentMenuBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navigation_dashboard:
                        Log.e("dashboardCli", ":" + 1);

                        break;

                    case R.id.navigation_vehicals:
                        Log.e("dashboardCli", ":" + 1);

                        break;

                    case R.id.navigation_setup:
                        Log.e("dashboardCli", ":" + 1);
                        break;
                }
                return true;
            }
        });*/
    }

    override fun connectionAvailable() {}
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity?) : FragmentStateAdapter(fa!!) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return DashboardFragment()
                1 -> return PropertyFragment()
                2 -> return SetupFragment()
            }
            return DashboardFragment()
        }

        override fun getItemCount(): Int {
            return Constants.TOTAL_SCREEN
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}