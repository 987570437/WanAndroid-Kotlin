package com.xing.wanandroid.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPFragment
import com.xing.wanandroid.search.adapter.SearchResultAdapter
import com.xing.wanandroid.search.bean.SearchResultResponse
import com.xing.wanandroid.search.contract.SearchResultContract
import com.xing.wanandroid.search.presenter.SearchResultPresenter

private const val KEY_WORD = "key_word"

/**
 * 搜索结果
 */
class SearchResultFragment : BaseMVPFragment<SearchResultContract.View, SearchResultPresenter>(),
    SearchResultContract.View {


    private var recyclerView: RecyclerView? = null
    private var page: Int = 0
    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun createPresenter(): SearchResultPresenter {
        return SearchResultPresenter()
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        recyclerView = rootView?.findViewById(R.id.rv_search_result)
    }

    override fun initData() {
        super.initData()
        recyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        searchResultAdapter = SearchResultAdapter(R.layout.item_search_result)
        recyclerView?.adapter = searchResultAdapter
        presenter.getSearchResult(0, keyword ?: "")
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_search_result
    }

    override fun onSearchResult(response: SearchResultResponse) {
        searchResultAdapter.setNewData(response.datas)
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    private var keyword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            keyword = it.getString(KEY_WORD)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(keyword: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_WORD, keyword)
                }
            }
    }
}
