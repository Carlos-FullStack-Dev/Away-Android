package com.plum.networkk.awmapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.EmployeeNavDrawerAdapter
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences.SIGNED_IN_KEY
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences.TOKEN_KEY
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.NavDrawerDataModel
import java.util.*
import kotlin.collections.ArrayList


class MenuActivityEmployee : AppCompatActivity(), RecyclerItemClick {
    var toolbar: Toolbar? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    var drawerLayout: DrawerLayout? = null
    var navController: NavController? = null
    var menuList = ArrayList<NavDrawerDataModel>()
    var navDrawerAdapter: EmployeeNavDrawerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_employee)



//        AppController.signIn(this@MenuActivityEmployee)
        /** Setup AppBar **/
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        /** Setup Drawer **/
        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()


        /*****************************************************
         * @Desc: Combine Drawer Navbar Item with AppBar ( Appbar title, action buttons)
         * Passing each menu ID as a set of Ids because each
         * menu should be considered as top level destinations.
         */
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashBoard, R.id.nav_statistics, R.id.nav_profile, R.id.nav_help
            ), drawerLayout
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController!!)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /***************************************************************************
         * @author: geniusdev0813@gmail.com
         * @desc: Whenever click drawer navitem, Change Appbar Title and action buttons
         */
        navController!!.addOnDestinationChangedListener(object :
            AppBarConfiguration.OnNavigateUpListener,
            NavController.OnDestinationChangedListener {
            override fun onNavigateUp(): Boolean {
                return false
            }

            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {

                if (destination.label!!.equals("DASHBOARD")) {

                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarIconLayout).visibility =
                        View.VISIBLE
                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarTextBtnLayout).visibility =
                        View.GONE

                } else if (destination.label!!.equals("STATISTICS")) {

                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarIconLayout).visibility =
                        View.GONE
                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarTextBtnLayout).visibility =
                        View.VISIBLE
                    toolbar!!.findViewById<TextView>(R.id.toolbarTitleTv).text =
                        resources.getString(R.string.menu_statistics)
                    toolbar!!.findViewById<TextView>(R.id.toolbarEditBtnTv).visibility = View.GONE


                } else if (destination.label!!.equals("PROFILE")) {

                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarIconLayout).visibility =
                        View.GONE
                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarTextBtnLayout).visibility =
                        View.VISIBLE

                    toolbar!!.findViewById<TextView>(R.id.toolbarTitleTv).text =
                        resources.getString(R.string.menu_profile)
                    toolbar!!.findViewById<TextView>(R.id.toolbarEditBtnTv).visibility =
                        View.VISIBLE


                } else if (destination.label!!.equals("HELP")) {

                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarIconLayout).visibility =
                        View.GONE
                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarTextBtnLayout).visibility =
                        View.VISIBLE

                    toolbar!!.findViewById<TextView>(R.id.toolbarTitleTv).text =
                        resources.getString(R.string.menu_help)
                    toolbar!!.findViewById<TextView>(R.id.toolbarEditBtnTv).visibility = View.GONE

                }
                Log.e("MenuActivityEmployee", "value=" + destination.label)
            }

        })

        /**********************************************
         * @author: geniusdev0813@gmail.com
         * @desc:   Add Drawer NavItems
         */
        var navgationDrawerRecyclerView =
            findViewById<RecyclerView>(R.id.navgationDrawerRecyclerView)

        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_dashboard),
                resources.getColor(R.color.app_green_color)
            )
        )

        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_statistics),
                resources.getColor(R.color.black)
            )
        )

        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_companies),
                resources.getColor(R.color.black)
            )
        )

        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_profile),
                resources.getColor(R.color.black)
            )
        )

        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_help),
                resources.getColor(R.color.black)
            )
        )

        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.txt_logout),
                resources.getColor(R.color.black)
            )
        )
        navDrawerAdapter = EmployeeNavDrawerAdapter(this@MenuActivityEmployee, menuList)
        navgationDrawerRecyclerView.adapter = navDrawerAdapter


        /************************************************
         * @desc: Setup Other Components
         */
        var tvAwayModeStatusOrAddEmployee =
            findViewById(R.id.tvAwayModeStatusOrAddEmployee) as TextView
        var logout =
            findViewById(R.id.logout) as TextView
        var userImage =
            findViewById(R.id.userImage) as ImageView
        var userTitleName =
            findViewById(R.id.userTitleName) as TextView
        var isLocked = MySharedPreferences.getBooleanPreferences(
            MySharedPreferences.LockMode,
            this
        )

        /******************************
         * Set Status Text of Bottom
         */
        if (!isLocked){
            tvAwayModeStatusOrAddEmployee!!.text =
                resources.getString(R.string.txt_away_mode_turned_off)
        } else {
            tvAwayModeStatusOrAddEmployee!!.text =
                resources.getString(R.string.txt_away_mode_turned_on)
        }

        /******************************************
         * Set Image & User Name of top of DrawBar
         */
        userTitleName.text = if(AppController.user != null ) "" else "${AppController.user!!.user.firstName} ${AppController.user!!.user.lastName}"
        userImage.setBackgroundResource(R.drawable.blank_person)

        /******************************
         * Log Out Button Click Event
         */
        logout.setOnClickListener {
            MySharedPreferences.editBooleanPreferences(SIGNED_IN_KEY, false, this@MenuActivityEmployee)
            MySharedPreferences.editStringPreferences(TOKEN_KEY, "", this@MenuActivityEmployee)

            var singinIntent = Intent(this@MenuActivityEmployee, SplashActivity::class.java)
            startActivity(singinIntent)
            finish()
        }
    }




    fun changeToolbarItem(fragmentName: String) {
        /*if (fragmentName == "profile") {

            toolbar!!.findViewById<ImageView>(R.id.toolbarIcon).visibility = View.GONE

        } else if (fragmentName == "dashboard") {

            toolbar!!.findViewById<ImageView>(R.id.toolbarIcon).visibility = View.VISIBLE

        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    /*****************************************
     * @author: geniusdev0813@gmail.com
     * @desc:   Drawer NavItem Click Event Handler
     */
    override fun OnRecyclerViewItemClick(position: Int) {

        if (position != 1 && position != 2) {
            for (i in menuList) {
                i.itemTextColor = resources.getColor(R.color.black)
            }
        }

        if (position == 0)
            navController!!.navigate(R.id.nav_dashBoard)
        if (position == 1) {
//            navController!!.navigate(R.id.nav_statistics)
            var viewMoreIntent =
                Intent(this@MenuActivityEmployee, StatisticsActivity::class.java)
            startActivity(viewMoreIntent)
        }

        if (position == 2) {
//            navController!!.navigate(R.id.nav_statistics)
            var viewMoreIntent =
                Intent(this@MenuActivityEmployee, EmployeeCompanyActivity::class.java)
            startActivity(viewMoreIntent)
            finish()

        }

        if (position == 3)
            navController!!.navigate(R.id.nav_profile)
        if (position == 4) {
//            navController!!.navigate(R.id.nav_help)
            AppController.userRole = 2
            var FaqIntent = Intent(this@MenuActivityEmployee, FAQActivity::class.java)
            startActivity(FaqIntent)
            finish()

        }
        if (position == 5) {
            MySharedPreferences.editBooleanPreferences(SIGNED_IN_KEY, false, this@MenuActivityEmployee)
            MySharedPreferences.editStringPreferences(TOKEN_KEY, "", this@MenuActivityEmployee)
            AppController.selectedCompany = null
            AppController.companies = null
            var singinIntent = Intent(this@MenuActivityEmployee, SplashActivity::class.java)
            startActivity(singinIntent)
            finish()
        }
        if (position != 1 && position != 2) {
            menuList.get(position).itemTextColor = resources.getColor(R.color.app_green_color)
            navDrawerAdapter!!.notifyDataSetChanged()
        }

        drawerLayout!!.closeDrawer(GravityCompat.START)
    }
}