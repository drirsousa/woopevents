package br.com.rosait.woopevents.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.rosait.woopevents.R
import br.com.rosait.woopevents.common.base.BaseViewModelFactory
import br.com.rosait.woopevents.common.base.CustomCallback
import br.com.rosait.woopevents.common.extension.formatForBrazilianCurrency
import br.com.rosait.woopevents.common.extension.getDate
import br.com.rosait.woopevents.common.extension.isValidInput
import br.com.rosait.woopevents.common.model.EventItem
import br.com.rosait.woopevents.databinding.ActivityEventDetailBinding
import br.com.rosait.woopevents.viewmodel.EventViewModel
import com.squareup.picasso.Picasso

class ItemDetailActivity : AppCompatActivity() {

    companion object {
        const val EVENT_ID = "eventId"
    }

    private val mViewModel: EventViewModel by lazy { ViewModelProviders.of(this, BaseViewModelFactory { EventViewModel.getInstance() }).get(EventViewModel::class.java) }
    private lateinit var mBinding: ActivityEventDetailBinding
    lateinit var dialog: android.app.AlertDialog
    var mEventId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail)
        mBinding.lifecycleOwner = this
        mBinding.executePendingBindings()

        setupToolbar()

        mEventId = intent?.extras?.getInt(EVENT_ID)

        mEventId?.let {
            mViewModel.getEvent(it).observe(this, Observer { eventItem ->
                setEventViews(eventItem)
            })
        }
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.lbl_event_detail)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setEventViews(eventItem: EventItem) {

        eventItem.title?.let {
            mBinding.txtTitle.text = it
        }
        eventItem.description?.let {
            mBinding.txtDescription.text = it
        }
        eventItem.date?.let {
            mBinding.txtDate.text = it.getDate()
        }
        eventItem.price?.let {
            mBinding.txtPrice.text = it.formatForBrazilianCurrency()
        }

        eventItem.image?.let {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.ic_placeholder)
                .into(mBinding?.imvPicture, object : CustomCallback() {
                    override fun onFinish() {
                        mBinding?.pbItemEvent?.visibility = View.GONE
                    }
                })
        }

        if(eventItem.latitude != null && eventItem.longitude != null) {
            mBinding?.txtLocation?.setOnClickListener {
                val uri = "google.navigation:q=${eventItem.latitude},${eventItem.longitude}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                intent.setPackage("com.google.android.apps.maps")
                it.context.startActivity(intent)
            }
        }

        mBinding.btnCheckin.setOnClickListener {
            if(validateFields()) {
                mViewModel.doCheckin(mEventId ?: 0
                    , mBinding.edtName.text.toString()
                    , mBinding.edtEmail.text.toString()) { isSuccessfull: Boolean, errorMessage: String? ->
                    var message = errorMessage
                    if(isSuccessfull) {
                        message = getString(R.string.message_checkin)
                    }
                    showPopup(message ?: getString(R.string.message_error))
                }
            }
        }
    }

    private fun validateFields() : Boolean {
        return mBinding.edtName.isValidInput() && mBinding.edtEmail.isValidInput()
    }

    private fun showPopup(message: String) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.lbl_title_message))
        builder.setMessage(message)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            dialog.dismiss()
            finish()
        }

        dialog = builder.create()
        dialog.show()
    }
}