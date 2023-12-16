package com.example.carservice.feature.home.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.carservice.R
import com.example.carservice.core.constant.carImg.CarGenerate
import com.example.carservice.databinding.RowTiketNewBinding
import com.example.carservice.feature.home.domain.model.TicketResponse

class TicketAdapter(
    private val items: ArrayList<TicketResponse>,
    private val onClick: (TicketResponse) -> Unit
) : RecyclerView.Adapter<TicketAdapter.PostViewHolder>() {
    var phaseTime: String = ""
    private var filteredList: List<TicketResponse> = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = RowTiketNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = items[position]
        setStatus(item, holder)

        val index: Int = item.carModel.toInt()
        setCarImg(index, holder)
        setPhaseColor(item, holder)
        calculateTime(item, holder)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    private fun calculateTime(
        item: TicketResponse,
        holder: PostViewHolder
    ) {

        if (item.status.equals("0")) {
            holder.binding.tvTime.text = ""
        } else if (item.status.equals("2")) {
            holder.binding.tvTime.text = ""

        } else if (item.status.equals("1")) {
            val totalTime: Int = item.time.toInt()
            val hours = totalTime / 60
            val remainingMinutes = totalTime % 60
            if (hours > 0 || remainingMinutes > 40) {
                holder.binding.tvTime.text = "${hours}:${remainingMinutes}"
                holder.binding.tvTime.setTextColor(Color.rgb(255, 49, 49))
            } else {
                holder.binding.tvTime.text = "${hours}:${remainingMinutes}"

            }
        }

    }

    private fun timeCount(item: TicketResponse, holder: PostViewHolder): String {
        val time = item.time.toInt()
        val hours = time / 60
        val remainingMinutes = time % 60
        if (remainingMinutes > 40 || hours > 0) {
            holder.binding.tvTime.setTextColor(Color.rgb(255, 0, 0))
        }
        return "${hours}:${remainingMinutes}"

    }

    private fun setPhaseColor(
        item: TicketResponse,
        holder: PostViewHolder
    ) {
        if (item.status.equals("1")) {
            if (item.phase.equals("0")) {
                holder.binding.phase1.visibility = View.GONE
                holder.binding.phase2.visibility = View.GONE
                holder.binding.phase3.visibility = View.GONE
            } else if (item.phase.equals("1")) {
                holder.binding.phase2.visibility = View.GONE
                holder.binding.phase3.visibility = View.GONE
                holder.binding.phase1.visibility = View.VISIBLE
                holder.binding.phase1.setCardBackgroundColor(Color.rgb(64, 108, 184))

            } else if (item.phase.equals("2")) {
                holder.binding.phase1.visibility = View.VISIBLE
                holder.binding.phase2.visibility = View.VISIBLE
                holder.binding.phase3.visibility = View.GONE
                holder.binding.phase1.setCardBackgroundColor(Color.rgb(179, 239, 106))
                holder.binding.phase2.setCardBackgroundColor(Color.rgb(64, 108, 184))

            } else if (item.phase.equals("3")) {
                holder.binding.phase1.visibility = View.VISIBLE
                holder.binding.phase2.visibility = View.VISIBLE
                holder.binding.phase3.visibility = View.VISIBLE
                holder.binding.phase1.setCardBackgroundColor(Color.rgb(179, 239, 106))
                holder.binding.phase2.setCardBackgroundColor(Color.rgb(179, 239, 106))
                holder.binding.phase3.setCardBackgroundColor(Color.rgb(64, 108, 184))

            }
        } else if (item.status.equals("2")) {
            holder.binding.phase1.visibility = View.VISIBLE
            holder.binding.phase2.visibility = View.VISIBLE
            holder.binding.phase3.visibility = View.VISIBLE
            holder.binding.phase1.setCardBackgroundColor(Color.rgb(179, 239, 106))
            holder.binding.phase2.setCardBackgroundColor(Color.rgb(179, 239, 106))
            holder.binding.phase3.setCardBackgroundColor(Color.rgb(179, 239, 106))

        } else if (item.status.equals("0")) {
            holder.binding.phase1.visibility = View.GONE
            holder.binding.phase2.visibility = View.GONE
            holder.binding.phase3.visibility = View.GONE
        }

    }

    private fun setStatus(
        item: TicketResponse,
        holder: PostViewHolder
    ) {
        if (item.status.equals("1")) {
            holder.binding.ivOn.visibility = View.GONE
            holder.binding.ivOff.visibility = View.GONE
            holder.binding.ivInProsess.visibility = View.VISIBLE
        } else if (item.status.equals("2")) {
            holder.binding.ivOn.visibility = View.VISIBLE
            holder.binding.ivOff.visibility = View.GONE
            holder.binding.ivInProsess.visibility = View.GONE

        } else if (item.status.equals("0")) {
            holder.binding.ivInProsess.visibility = View.GONE
            holder.binding.ivOn.visibility = View.GONE
            holder.binding.ivOff.visibility = View.VISIBLE
        }
    }

    private fun setCarImg(
        index: Int,
        holder: PostViewHolder
    ) {
        if (index == -1) {
            holder.binding.ivCarType.setImageResource(R.drawable.img_car)

        } else {

            holder.binding.ivCarType.setImageResource(CarGenerate.carList().get(index).img)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setFilteredList(list: List<TicketResponse>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    class PostViewHolder(val binding: RowTiketNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var cvTicket: CardView = binding.cvData

        fun bind(item: TicketResponse) {
            binding.model = item
            binding.executePendingBindings()
        }
    }

    public fun clearList() {
        items.clear()
        notifyDataSetChanged()
    }

}