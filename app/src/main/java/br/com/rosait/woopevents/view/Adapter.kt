package br.com.rosait.woopevents.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.rosait.woopevents.R
import br.com.rosait.woopevents.common.base.CustomCallback
import br.com.rosait.woopevents.databinding.ItemEventBinding
import br.com.rosait.woopevents.common.extension.formatForBrazilianCurrency
import br.com.rosait.woopevents.common.extension.getDate
import br.com.rosait.woopevents.common.model.EventItem
import br.com.rosait.woopevents.view.ItemDetailActivity.Companion.EVENT_ID
import com.squareup.picasso.Picasso

class Adapter(var eventList: List<EventItem>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ItemDetailActivity::class.java)
            intent.putExtra(EVENT_ID, event.id)
            it.context.startActivity(intent)
        }
    }

    fun updateList(newList: List<EventItem>) {
        this.eventList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemEventBinding? = DataBindingUtil.bind(itemView)

        fun bind(event: EventItem) {
            event.title?.let { binding?.txtTitle?.text = it }
            event.date?.let { binding?.txtDate?.text = it.getDate() }
            event.price?.let { binding?.txtPrice?.text = it.formatForBrazilianCurrency() }

            event.image?.let {
                Picasso.get()
                    .load(it)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding?.imvPicture, object : CustomCallback() {
                        override fun onFinish() {
                            binding?.pbItemEvent?.visibility = View.GONE
                        }
                    })
            }

            if(event.latitude != null && event.longitude != null) {
                binding?.txtLocation?.setOnClickListener {
                    val uri = "google.navigation:q=${event.latitude},${event.longitude}"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setPackage("com.google.android.apps.maps")
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}