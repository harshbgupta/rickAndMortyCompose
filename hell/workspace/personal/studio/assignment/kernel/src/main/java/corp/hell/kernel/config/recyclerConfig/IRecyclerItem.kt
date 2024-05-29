package corp.hell.kernel.config.recyclerConfig

interface IRecyclerItem {
    fun isSameItemAs(otherItem: IRecyclerItem): Boolean = this == otherItem
    fun isSameContentAs(otherItem: IRecyclerItem): Boolean = this == otherItem

    fun viewType(): Int = this::class.qualifiedName.hashCode()

    interface ClickListener {
        fun onClickRecyclerItem(
            item: IRecyclerItem,
            clickType: ClickType = ClickType.ListItemClicked,
            position: Int = 0
        )
    }
}

open class ClickType {
    object ListItemClicked : ClickType()
}
