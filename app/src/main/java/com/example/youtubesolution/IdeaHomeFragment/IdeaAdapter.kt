import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubesolution.databinding.ItemIdeaBinding
import com.example.youtubesolution.dataclass.Idea

class IdeaAdapter(var items:MutableList<Idea>) : RecyclerView.Adapter<IdeaAdapter.Holder>(){
    inner class Holder(val binding : ItemIdeaBinding) : RecyclerView.ViewHolder (binding.root) {
        val description = binding.tvItemIdeaDescription
        val keyword = binding.tvItemIdeaKeyword
        val viewCount = binding.tvItemIdeaView
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.description.text = items[position].description
        holder.keyword.text = items[position].keyword
        holder.viewCount.text = "100만뷰"
    }

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