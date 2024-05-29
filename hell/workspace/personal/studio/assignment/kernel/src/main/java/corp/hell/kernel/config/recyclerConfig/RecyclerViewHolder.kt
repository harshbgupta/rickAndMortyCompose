package corp.hell.kernel.config.recyclerConfig

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    //    abstract fun bind(data: IRecyclerItem, position: Int = 0)
    abstract fun bind(
        data: IRecyclerItem,
        position: Int = 0,
        adapter: RecyclerView.Adapter<RecyclerViewHolder>
    )

    abstract fun unbind()

    abstract class ViewHolderFactory(
        val viewType: Int
    ) {
        abstract fun createViewHolder(parent: ViewGroup): RecyclerViewHolder
    }
}
