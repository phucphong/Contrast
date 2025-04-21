package com.contrast.Contrast.presentation.features.affiliate.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.contrast.Contrast.R
import com.itechpro.domain.enumApp.ProductSectionType
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.model.product.ProductSection

class HomeAffiliateAdapter(
    private val promoUiDataMap: Map<String, PromoUiData> = emptyMap(),
    private val onItemClick: (Product) -> Unit,
    private val onClickCart: (Product) -> Unit,
    private val onClickAddServiceRequest: (Product) -> Unit,
    private val onCategorySelected: (Int, List<Category>) -> Unit,
) : ListAdapter<ProductSection, RecyclerView.ViewHolder>(SectionDiffCallback()) {

    companion object {
        private const val TYPE_SLIDE = 0
        private const val TYPE_CATEGORY = 1
        private const val TYPE_TAB_HEADER = 2
        private const val TYPE_PRODUCT_GRID = 3
        private const val TYPE_DIVIDER = 4
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            ProductSectionType.SLIDE -> TYPE_SLIDE
            ProductSectionType.CATEGORY -> TYPE_CATEGORY
            ProductSectionType.TAB -> TYPE_TAB_HEADER
            ProductSectionType.PRODUCT_ALL -> TYPE_PRODUCT_GRID
            else -> TYPE_DIVIDER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_SLIDE -> SlideViewHolder(inflater.inflate(R.layout.item_slider, parent, false))
            TYPE_CATEGORY -> CategoryViewHolder(inflater.inflate(R.layout.item_category_grid, parent, false))
            TYPE_TAB_HEADER -> TabHeaderViewHolder(inflater.inflate(R.layout.item_tab_header, parent, false))
            TYPE_PRODUCT_GRID -> ProductGridViewHolder(inflater.inflate(R.layout.item_product_grid, parent, false))
            TYPE_DIVIDER -> DividerViewHolder(inflater.inflate(R.layout.item_divider, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = getItem(position)
        when (holder) {
            is SlideViewHolder -> holder.bind(section.slides)
            is CategoryViewHolder -> holder.bind(section.categories)
            is TabHeaderViewHolder -> holder.bind(section.categories, onCategorySelected)
            is ProductGridViewHolder -> holder.bind(
                section.products,
                section.domain,
                promoUiDataMap,
                onItemClick,
                onClickCart,
                onClickAddServiceRequest
            )
            is DividerViewHolder -> {} // no-op
        }
    }

    class SlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(slides: List<SliderHome>) {
            // TODO: bind ViewPager2 or Slider
        }
    }

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(categories: List<Category>) {
            // TODO: bind horizontal category
        }
    }

    class TabHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tabs: List<Category>, onTabSelected: (Int, List<Category>) -> Unit) {
            // TODO: bind tab row with click listener
        }
    }

    class ProductGridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val recyclerView: RecyclerView = view.findViewById(R.id.rcProductGrid)

        fun bind(
            products: List<Product>,
            domain: String,
            promoUiDataMap: Map<String, PromoUiData>,
            onItemClick: (Product) -> Unit,
            onClickCart: (Product) -> Unit,
            onClickAddServiceRequest: (Product) -> Unit,
        ) {
            recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 2)
            recyclerView.adapter = ProductGridAdapter(
                domain = domain,
                promoUiDataMap = promoUiDataMap,
                onItemClick = onItemClick,
                onClickCart = onClickCart,
                onClickAddServiceRequest = onClickAddServiceRequest
            ).apply {
                submitList(products)
            }
        }
    }

    class DividerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class SectionDiffCallback : DiffUtil.ItemCallback<ProductSection>() {
        override fun areItemsTheSame(oldItem: ProductSection, newItem: ProductSection): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductSection, newItem: ProductSection): Boolean =
            oldItem == newItem
    }
}

class ProductGridAdapter(
    private val domain: String,
    private val promoUiDataMap: Map<String, PromoUiData> = emptyMap(),
    private val onItemClick: (Product) -> Unit,
    private val onClickCart: (Product) -> Unit,
    private val onClickAddServiceRequest: (Product) -> Unit,
) : ListAdapter<Product, ProductGridAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            val promo = promoUiDataMap[product.id.orEmpty()]

            // Bind views here
            val tvName = view.findViewById<TextView>(R.id.tvProductName)
//            val tvPrice = view.findViewById<TextView>(R.id.tvProductPrice)
//            val tvPromoTime = view.findViewById<TextView>(R.id.tvPromoCountdown)
//            val btnCart = view.findViewById<ImageView>(R.id.btnAddToCart)
            val imgProduct = view.findViewById<ImageView>(R.id.imgProduct)
//            val layoutRoot = view.findViewById<View>(R.id.productRoot)

            tvName.text = product.ten ?: ""
//            tvPrice.text = (product.sotien ?: 0.0).toString()
//            tvPromoTime.text = promo?.th ?: ""

            // Optional: Load image
            Glide.with(view.context)
                .load("${domain}/${product.filetxt}")
                .placeholder(R.drawable.nodata)
                .into(imgProduct)

//            layoutRoot.setOnClickListener { onItemClick(product) }
//            btnCart.setOnClickListener { onClickCart(product) }
//            tvPromoTime.setOnClickListener { onClickAddServiceRequest(product) }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem
    }
}