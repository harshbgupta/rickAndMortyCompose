package corp.hell.kernel.config.recyclerConfig

import android.view.ViewGroup
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber
import javax.inject.Inject


open class RecyclerPagingAdapter @Inject constructor(
    private val viewHolderFactoryMap: Map<Int, RecyclerViewHolder.ViewHolderFactory>
) : PagingDataAdapter<IRecyclerItem, RecyclerViewHolder>(DiffCallBack) {
    constructor(viewHolderFactoryList: List<RecyclerViewHolder.ViewHolderFactory>) : this(
        viewHolderFactoryList.associateBy { it.viewType }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return viewHolderFactoryMap[viewType]?.createViewHolder(parent)
            ?: throw UnsupportedOperationException("ViewHolder Factory missing, check the viewType value:$viewType")
                .also { Timber.e(it) }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position, this) }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.viewType()
            ?: throw UnsupportedOperationException("Viewtype missing for position:$position")
                .also { Timber.e(it) }
    }

    private object DiffCallBack : DiffUtil.ItemCallback<IRecyclerItem>() {
        override fun areItemsTheSame(oldItem: IRecyclerItem, newItem: IRecyclerItem): Boolean =
            oldItem.isSameItemAs(newItem)

        override fun areContentsTheSame(oldItem: IRecyclerItem, newItem: IRecyclerItem): Boolean =
            oldItem.isSameContentAs(newItem)
    }

    interface RecyclerLoadStateListener {
        fun onLoadStatusUpdated(loadState: CombinedLoadStates, itemCount: Int)
    }

    interface IPagingAdaptorCreator {
        fun createAdaptor(): RecyclerPagingAdapter
    }
}