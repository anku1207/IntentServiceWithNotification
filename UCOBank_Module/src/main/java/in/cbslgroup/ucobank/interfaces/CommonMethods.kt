package `in`.cbslgroup.ucobank.interfaces

import android.view.View

interface CommonMethods {

    fun initViewModelsAndDataBinding(view : View)
    fun initObservers()
    fun initViews(view : View)
    fun initRecyclerViews(view : View)
    fun initListeners()
    fun getIntentData()

    // intializing all the common methods
    fun init(view :View){

        initViewModelsAndDataBinding(view)
        getIntentData()
        initViews(view)
        initObservers()
        initRecyclerViews(view)
        initListeners()

    }


}