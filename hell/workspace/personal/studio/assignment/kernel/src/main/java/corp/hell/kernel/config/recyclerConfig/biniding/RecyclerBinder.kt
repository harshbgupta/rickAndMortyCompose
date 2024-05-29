package corp.hell.kernel.config.recyclerConfig.biniding

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import corp.hell.kernel.config.recyclerConfig.IRecyclerItem
import corp.hell.kernel.config.recyclerConfig.RecyclerListAdapter
import corp.hell.kernel.config.recyclerConfig.RecyclerPagingAdapter
import corp.hell.kernel.config.recyclerConfig.RecyclerViewHolder

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter(value = ["recyclerData", "recyclerViewHolderFactories"], requireAll = true)
fun recyclerListBindingAdapter(
    recyclerView: RecyclerView,
    items: List<IRecyclerItem>?,
    viewHolderFactories: List<RecyclerViewHolder.ViewHolderFactory>?
) {
    viewHolderFactories ?: return
    val adapter: RecyclerListAdapter = when (val existingAdapter = recyclerView.adapter) {

        is RecyclerListAdapter -> existingAdapter
        else -> {
            RecyclerListAdapter(viewHolderFactories).also {
                it.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                recyclerView.adapter = it
            }
        }
    }
    val recyclerListItems: List<IRecyclerItem> = items ?: emptyList()
    adapter.submitList(recyclerListItems)
    adapter.notifyDataSetChanged()
}

@BindingAdapter(value = ["adaptorCreator", "loadStateListener"], requireAll = true)
fun recyclerPagingDataBindingAdapter(
    recyclerView: RecyclerView,
    adaptorCreator: RecyclerPagingAdapter.IPagingAdaptorCreator?,
    loadStateListener: RecyclerPagingAdapter.RecyclerLoadStateListener
) {
    adaptorCreator ?: return
    if (recyclerView.adapter == null) {
        recyclerView.adapter = adaptorCreator.createAdaptor()
        val adapter = recyclerView.adapter as PagingDataAdapter<IRecyclerItem, RecyclerViewHolder>
        adapter.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            addLoadStateListener { state ->
                loadStateListener.onLoadStatusUpdated(state, itemCount)
            }
        }
    }
}
