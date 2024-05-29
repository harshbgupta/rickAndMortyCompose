package corp.hell.kernel.config.recyclerConfig

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import timber.log.Timber

class RecyclerListAdapter(
    private val viewHolderFactoryMap: Map<Int, RecyclerViewHolder.ViewHolderFactory>
) : ListAdapter<IRecyclerItem, RecyclerViewHolder>(DiffCallBack) {
    constructor(viewHolderFactoryList: List<RecyclerViewHolder.ViewHolderFactory>) : this(

        viewHolderFactoryList.associateBy { it.viewType }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return viewHolderFactoryMap[viewType]?.createViewHolder(parent)
            ?: throw UnsupportedOperationException("Trying to get missing ViewHolderFactory, viewType:$viewType")
                .also { Timber.e(it) }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList.getOrNull(position)?.viewType()
            ?: throw UnsupportedOperationException("Trying to get missing view type, position:$position")
                .also { Timber.e(it) }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        currentList.getOrNull(position)?.let { holder.bind(it, position, this) }
            ?: throw UnsupportedOperationException(
                "Trying to bind missing IRecyclerItem, " +
                        "position:${position}, " +
                        "holder:${holder::class.qualifiedName}"
            )
                .also { Timber.e(it) }
    }

    private object DiffCallBack : DiffUtil.ItemCallback<IRecyclerItem>() {
        override fun areItemsTheSame(oldItem: IRecyclerItem, newItem: IRecyclerItem): Boolean =
            oldItem.isSameItemAs(newItem)

        override fun areContentsTheSame(oldItem: IRecyclerItem, newItem: IRecyclerItem): Boolean =
            oldItem.isSameContentAs(newItem)
    }
}