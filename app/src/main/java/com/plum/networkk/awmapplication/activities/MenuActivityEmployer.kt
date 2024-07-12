package com.plum.networkk.awmapplication.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.NavDrawerDataModel


class MenuActivityEmployer : AppCompatActivity(), RecyclerItemClick {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var drawerLayout: DrawerLayout? = null
    var navController: NavController? = null
    var menuList = ArrayList<NavDrawerDataModel>()
    var navDrawerAdapter: EmployeeNavDrawerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_employer)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /*****************************************
         * Setup Drawer Layout
         */
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashBoard_employer,
                R.id.nav_all_employees,
//                R.id.nav_settings,
                R.id.nav_profile,
                R.id.nav_help
            ), drawerLayout
        )


        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController!!)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*****************************************
         * Combin Drawer Items with Fragments
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

                } else if (destination.label!!.equals("EMPLOYEES")) {

                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarIconLayout).visibility =
                        View.GONE
                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarTextBtnLayout).visibility =
                        View.VISIBLE

                    toolbar!!.findViewById<TextView>(R.id.toolbarTitleTv).text =
                        resources.getString(R.string.menu_employees)
                    toolbar!!.findViewById<TextView>(R.id.toolbarEditBtnTv).visibility = View.GONE
                    toolbar!!.findViewById<ImageView>(R.id.iconAddEmployee).visibility =
                        View.VISIBLE

                } else if (destination.label!!.equals("PROFILE")) {

                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarIconLayout).visibility =
                        View.GONE
                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarTextBtnLayout).visibility =
                        View.VISIBLE

                    toolbar!!.findViewById<TextView>(R.id.toolbarTitleTv).text =
                        resources.getString(R.string.menu_profile)
                    toolbar!!.findViewById<TextView>(R.id.toolbarEditBtnTv).visibility =
                        View.VISIBLE

//                } else if (destination.label!!.equals("SETTINGS")) {
//
//                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarIconLayout).visibility =
//                        View.GONE
//                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarTextBtnLayout).visibility =
//                        View.VISIBLE
//
//                    toolbar!!.findViewById<TextView>(R.id.toolbarTitleTv).text =
//                        resources.getString(R.string.menu_setting)
//                    toolbar!!.findViewById<TextView>(R.id.toolbarEditBtnTv).visibility = View.GONE
//                    toolbar!!.findViewById<ImageView>(R.id.iconAddEmployee).visibility = View.GONE

                } else if (destination.label!!.equals("HELP")) {

                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarIconLayout).visibility =
                        View.GONE
                    toolbar!!.findViewById<RelativeLayout>(R.id.toolbarTextBtnLayout).visibility =
                        View.VISIBLE

                    toolbar!!.findViewById<TextView>(R.id.toolbarTitleTv).text =
                        resources.getString(R.string.menu_help)
                    toolbar!!.findViewById<TextView>(R.id.toolbarEditBtnTv).visibility = View.GONE
                    toolbar!!.findViewById<ImageView>(R.id.iconAddEmployee).visibility = View.GONE


                }
                Log.e("MenuActivityEmployee", "value=" + destination.label)
            }

        })

        /************************************************
         * Add Employee Icon Button Click Event Handler
         */
        toolbar.findViewById<ImageView>(R.id.iconAddEmployee).setOnClickListener {

//            var addEmployeeIntent =
//                Intent(this@MenuActivityEmployer, AddEmployeeActivity::class.java)
//            startActivity(addEmployeeIntent)
            if(AppController.companies != null && AppController.companies!!.isNotEmpty()) {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, AppController.companies!!.first().inviteCode);
                startActivity(Intent.createChooser(shareIntent, "Share"))
            }
        }

        /*****************************
         * Setup Drawer Navbar Items
         */
        var navgationDrawerRecyclerView =
            findViewById<RecyclerView>(R.id.navgationDrawerRecyclerView)

        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_dashboard),
                resources.getColor(R.color.app_green_color)
            )
        )

//        menuList.add(
//            NavDrawerDataModel(
//                resources.getString(R.string.menu_statistics),
//                resources.getColor(R.color.black)
//            )
//        )

        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_employees),
                resources.getColor(R.color.black)
            )
        )
        menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_profile),
                resources.getColor(R.color.black)
            )
        )

//        menuList.add(
//            NavDrawerDataModel(
//                resources.getString(R.string.menu_setting),
//                resources.getColor(R.color.black)
//            )
//        )

       /* menuList.add(
            NavDrawerDataModel(
                resources.getString(R.string.menu_profile),
                resources.getColor(R.color.black)
            )
        )*/

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

        navDrawerAdapter = EmployeeNavDrawerAdapter(this@MenuActivityEmployer, menuList)
        navgationDrawerRecyclerView.adapter = navDrawerAdapter


        /**********************************
         * Setup Other Components
         */
        var tvAwayModeStatusOrAddEmployee =
            findViewById(R.id.tvAwayModeStatusOrAddEmployee) as TextView
        var logout =
            findViewById(R.id.logout) as TextView
        var userImage =
            findViewById(R.id.userImage) as ImageView
        var userTitleName =
            findViewById(R.id.userTitleName) as TextView


        tvAwayModeStatusOrAddEmployee.text = ""
        tvAwayModeStatusOrAddEmployee.setBackgroundColor(Color.WHITE)
        logout.text = "Log out"
//        userTitleName.text = "Jasica Newberg"
//        userImage.setBackgroundResource(R.drawable.employer_drawer_image)

        userTitleName.text = "${AppController.user!!.user.firstName} ${AppController.user!!.user.lastName}".takeIf { AppController.user != null } ?: ""
        userImage.setBackgroundResource(R.drawable.blank_person)

        /**********************************
         * LogOut button Click Event Handler
         */
        logout.setOnClickListener {
            MySharedPreferences.editBooleanPreferences(
                MySharedPreferences.SIGNED_IN_KEY,
                false,
                this@MenuActivityEmployer
            )
            MySharedPreferences.editStringPreferences(
                MySharedPreferences.TOKEN_KEY,
                "",
                this@MenuActivityEmployer
            )

            var singinIntent = Intent(this@MenuActivityEmployer, SplashActivity::class.java)
            startActivity(singinIntent)
            finish()

        }
    }

    fun changeFragment(fragmentNo: Int) {
        if (fragmentNo == 1)
            navController!!.navigate(R.id.nav_all_employees)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**************************************
     * Drawer NavItem Click Event Handler
     */
    override fun OnRecyclerViewItemClick(position: Int) {

        if (position != 4) {
            for (i in menuList) {
                i.itemTextColor = resources.getColor(R.color.black)
            }
        }

        if (position == 0)
            navController!!.navigate(R.id.nav_dashBoard_employer)



//        if (position == 1) {
////            navController!!.navigate(R.id.nav_settings)
//
//            var viewMoreIntent =
//                Intent(this@MenuActivityEmployer, StatisticsActivity_Employer::class.java)
//            startActivity(viewMoreIntent)
//        }

        if (position == 1)
            navController!!.navigate(R.id.nav_all_employees)
        if (position == 2)
            navController!!.navigate(R.id.nav_profile)

//        if (position == 4)
//            navController!!.navigate(R.id.nav_settings)

        if (position == 3){
            AppController.userRole = 1
            var FaqIntent = Intent(this@MenuActivityEmployer, FAQActivity::class.java)
            startActivity(FaqIntent)
            finish()
        }
     if (position == 4) {
            MySharedPreferences.editBooleanPreferences(
                MySharedPreferences.SIGNED_IN_KEY,
                false,
                this@MenuActivityEmployer
            )
            MySharedPreferences.editStringPreferences(
                MySharedPreferences.TOKEN_KEY,
                "",
                this@MenuActivityEmployer
            )

            var singinIntent = Intent(this@MenuActivityEmployer, SplashActivity::class.java)
            startActivity(singinIntent)
            finish()
        }

        if (position != 4) {
            menuList.get(position).itemTextColor = resources.getColor(R.color.app_green_color)
            navDrawerAdapter!!.notifyDataSetChanged()
        }

        drawerLayout!!.closeDrawer(GravityCompat.START)


    }

    /**************************************
     * Back Icon Click Event Handler
     */
    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}