package underdog.underlist.tasks.viewholders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import underdog.underlist.R;
import underdog.underlist.models.lists.ListModel;
import underdog.underlist.tasks.interfaces.TasksRowRecyclerViewOnClickListener;

/**
 * Created by Eric on 13/07/16.
 *
 */
public class tasks_rv_row_viewholder extends RecyclerView.ViewHolder{

    TextView listName_tv;
    ImageView finishedEdit_iv;

    public tasks_rv_row_viewholder(View itemView) {
        super(itemView);
        listName_tv = (TextView) itemView.findViewById(R.id.list_name_tv);
        finishedEdit_iv = (ImageView) itemView.findViewById(R.id.finishedEdit_iv);
    }

    public static tasks_rv_row_viewholder create(Context context, ViewGroup parent){
        return new tasks_rv_row_viewholder(LayoutInflater.from(context).inflate(R.layout.task_row_cell, parent, false));
    }

    public static void bind(final Context context, final tasks_rv_row_viewholder holder,
                            final ListModel listModel,
                            final TasksRowRecyclerViewOnClickListener listener,
                            Typeface helvetica_neue_bold, Typeface helvetica_neue_regular,
                            Typeface helvetica_neue_italic){

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRowClicked(3, holder.getAdapterPosition());
                }
            }
        });
    }
}