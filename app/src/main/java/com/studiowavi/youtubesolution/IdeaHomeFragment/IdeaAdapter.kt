import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.studiowavi.youtubesolution.R
import com.studiowavi.youtubesolution.checkIsRequested
import com.studiowavi.youtubesolution.databinding.ItemIdeaBinding
import com.studiowavi.youtubesolution.dataclass.Idea
import com.studiowavi.youtubesolution.dataclass.IsRequested
import com.studiowavi.youtubesolution.dataclass.SharedViewModel
import com.studiowavi.youtubesolution.formatViews

class IdeaAdapter(var items:MutableList<Idea>) : RecyclerView.Adapter<IdeaAdapter.Holder>(){
    inner class Holder(val binding : ItemIdeaBinding) : RecyclerView.ViewHolder (binding.root) {
        val description = binding.tvItemIdeaDescription
        val keyword = binding.tvItemIdeaKeyword
        val viewCount = binding.tvItemIdeaView
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val idea = items[position]
        val ideaAnalysis = viewModel?.getIdeaAnalysisById(idea.ideaId)

        holder.description.text = idea.description
        holder.keyword.text = idea.keyword
        checkIsRequested(holder.viewCount, idea, ideaAnalysis = ideaAnalysis!!)

        holder.itemView.setOnClickListener {
            itemClick?.onItemClick(idea)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(idea: Idea)
    }
    var itemClick : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = ItemIdeaBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return Holder(binding)
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    private var viewModel: SharedViewModel? = null
    fun setViewModel(viewModel: SharedViewModel) {
        this.viewModel = viewModel
    }

    fun updateItems(newItems: List<Idea>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun submitList(newItems: List<Idea>) {
        items = newItems.toMutableList()
        notifyDataSetChanged() // 데이터가 변경되었음을 Adapter에 알림
    }


}