package net.mullvad.mullvadvpn.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.mullvad.mullvadvpn.R
import net.mullvad.mullvadvpn.applist.AppListAdapter
import net.mullvad.mullvadvpn.ui.widget.CustomRecyclerView
import net.mullvad.mullvadvpn.util.AdapterWithHeader

class SplitTunnellingFragment : ServiceDependentFragment(OnNoService.GoToLaunchScreen) {
    private lateinit var appListAdapter: AppListAdapter
    private lateinit var titleController: CollapsibleTitleController

    override fun onAttach(context: Context) {
        super.onAttach(context)

        appListAdapter = AppListAdapter(context)
    }

    override fun onSafelyCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.split_tunnelling, container, false)

        view.findViewById<View>(R.id.back).setOnClickListener {
            activity?.onBackPressed()
        }

        titleController = CollapsibleTitleController(view, R.id.app_list)

        view.findViewById<CustomRecyclerView>(R.id.app_list).apply {
            layoutManager = LinearLayoutManager(parentActivity)

            adapter = AdapterWithHeader(appListAdapter, R.layout.split_tunnelling_header).apply {
                onHeaderAvailable = { headerView ->
                    titleController.expandedTitleView = headerView.findViewById(R.id.expanded_title)
                }
            }

            addItemDecoration(ListItemDividerDecoration(parentActivity))
        }

        return view
    }

    override fun onSafelyDestroyView() {
        titleController.onDestroy()
    }
}