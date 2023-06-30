package com.example.radius.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.radius.R
import com.example.radius.data.models.FacilityModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class HomeAdapter(private var listener: Listener) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    interface Listener {
        fun onOptionsClicked(model: FacilityModel)
    }

    private var list: List<FacilityModel> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun bindList(list: List<FacilityModel>?) {
        this.list = list ?: emptyList()
        notifyDataSetChanged()
    }

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFacilityName: MaterialTextView = view.findViewById(R.id.tvFacilityName)
        val iv: AppCompatImageView = view.findViewById(R.id.ivFacility)
        val cv: MaterialCardView = view.findViewById(R.id.cvOptions)
        val tvOptions: MaterialTextView = view.findViewById(R.id.tvOptions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        list[position].apply {
            holder.apply {
                itemView.apply {
                    selectedDraw?.let {
                        iv.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                it,
                                null
                            )
                        )
                    }
                    tvOptions.text = selectedOption ?: "Select options..."
                    tvFacilityName.text = name
                    cv.setOnClickListener {
                        listener.onOptionsClicked(list[position])
                    }
                }
            }
        }
    }
}