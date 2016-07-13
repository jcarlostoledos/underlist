package underdog.underlist.tasks.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import underdog.underlist.models.lists.ListModel;
import underdog.underlist.models.recycler_items.ListRecyclerItem;
import underdog.underlist.tasks.interfaces.TasksRowRecyclerViewOnClickListener;
import underdog.underlist.tasks.viewholders.tasks_rv_row_viewholder;

/**
 * Created by Eric on 13/07/16.
 *
 */
public class RecyclerViewTasksAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private TasksRowRecyclerViewOnClickListener listener;
    private final List<ListRecyclerItem> list;
    private final Context context;


    private Typeface helvetica_neue_bold, helvetica_neue_regular, helvetica_neue_italic;

    public RecyclerViewTasksAdapter(Context context, TasksRowRecyclerViewOnClickListener listener,
                                    Typeface helvetica_neue_bold, Typeface helvetica_neue_regular,
                                    Typeface helvetica_neue_italic){
        this.context = context;
        this.listener = listener;
        list = new ArrayList<>();
        this.helvetica_neue_bold = helvetica_neue_bold;
        this.helvetica_neue_regular = helvetica_neue_regular;
        this.helvetica_neue_italic = helvetica_neue_italic;
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
                return tasks_rv_row_viewholder.create(context, viewGroup);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)){
            case ListRecyclerItem.ROW:
                tasks_rv_row_viewholder.bind(context,(tasks_rv_row_viewholder) viewHolder,
                        list.get(position).listModel, listener,
                        helvetica_neue_bold, helvetica_neue_regular, helvetica_neue_italic);

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

    public void setListener(@Nullable TasksRowRecyclerViewOnClickListener listener) {
        this.listener = listener;
    }


}
