package corp.hell.kernel.config.recyclerConfig

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class DataViewHolder<V : ViewDataBinding>(
    private val binding: V
) : RecyclerViewHolder(binding.root) {
    var adapter: RecyclerView.Adapter<RecyclerViewHolder>? = null
    override fun bind(
        data: IRecyclerItem,
        position: Int,
        adapter: RecyclerView.Adapter<RecyclerViewHolder>
    ) {
        setData(binding, data, position)
        getAdapterInstance(adapter)
        binding.executePendingBindings()
    }

    override fun unbind() {
        setData(binding, null)
        binding.unbind()
        binding.executePendingBindings()
    }


    abstract fun setData(binding: V, data: IRecyclerItem?, position: Int = 0)
    private fun getAdapterInstance(adapter: RecyclerView.Adapter<RecyclerViewHolder>?) {
        this.adapter = adapter
    }
}
