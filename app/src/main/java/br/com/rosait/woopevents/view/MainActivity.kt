package br.com.rosait.woopevents.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.rosait.woopevents.R
import br.com.rosait.woopevents.common.base.BaseViewModelFactory
import br.com.rosait.woopevents.databinding.ActivityMainBinding
import br.com.rosait.woopevents.common.model.EventItem
import br.com.rosait.woopevents.viewmodel.EventViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: EventViewModel by lazy { ViewModelProviders.of( this, BaseViewModelFactory {
        EventViewModel.getInstance()
    }).get(EventViewModel::class.java) }
    val eventList = listOf<EventItem>()
    val mAdapter by lazy { Adapter(eventList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.lifecycleOwner = this
        mBinding.executePendingBindings()

        setupToolbar()

        initRecyclerView()

        mViewModel.getEvents().observe(this, Observer {
            it?.let {
                setEvent(it)
            }
        })
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.lbl_event_list)
    }

    private fun initRecyclerView() {
        mBinding.rvEvents.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mBinding.rvEvents.adapter = mAdapter
    }

    private fun setEvent(event: List<EventItem>) {
        mAdapter.updateList(event)
    }
}