package underdog.underlist.lists.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import underdog.underlist.lists.interfaces.ListRowRecyclerViewOnClickListener;
import underdog.underlist.lists.interfaces.OnListEdited;
import underdog.underlist.lists.viewholders.lists_rv_row_viewholder;
import underdog.underlist.models.lists.ListModel;
import underdog.underlist.models.recycler_items.ListRecyclerItem;

/**
 * Created by Eric on 13/07/16.
 *
 */
public class RecyclerViewListsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ListRowRecyclerViewOnClickListener listener;
    private OnListEdited onListEdited;
    private final List<ListRecyclerItem> list;
    private final Context context;


    public RecyclerViewListsAdapter(Context context, ListRowRecyclerViewOnClickListener listener,
                                    OnListEdited onListEdited){
        this.context = context;
        this.listener = listener;
        this.onListEdited = onListEdited;
        list = new ArrayList<>();
    }

    public void clearAdapter (){
        list.clear();
    }

    public void removeRow (int position){
        list.remove(position);
    }


    public void addRow(ListModel listModel){
        list.add(new ListRecyclerItem<>(ListRecyclerItem.ROW, listModel));
        notifyItemInserted(list.size());
    }

    public void addSection(ListModel listModel){
        list.add(new ListRecyclerItem(ListRecyclerItem.SECTION, listModel));
        notifyItemInserted(list.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case ListRecyclerItem.ROW:
                return lists_rv_row_viewholder.create(context, viewGroup);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)){
            case ListRecyclerItem.ROW:
                lists_rv_row_viewholder.bind(context,(lists_rv_row_viewholder) viewHolder,
                        list.get(position).listModel, listener, onListEdited);

                break;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).viewType;
    }

    public void setListener(@Nullable ListRowRecyclerViewOnClickListener listener) {
        this.listener = listener;
    }


}
