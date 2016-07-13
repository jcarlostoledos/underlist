package underdog.underlist.lists.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import underdog.underlist.R;
import underdog.underlist.lists.interfaces.ListRowRecyclerViewOnClickListener;
import underdog.underlist.lists.interfaces.OnListEdited;
import underdog.underlist.models.lists.ListModel;

/**
 * Created by Eric on 13/07/16.
 *
 */
public class lists_rv_row_viewholder extends RecyclerView.ViewHolder{

    TextView listName_tv;
    EditText listName_et;
    ImageView finishedEdit_iv;

    public lists_rv_row_viewholder(View itemView) {
        super(itemView);
        listName_tv = (TextView) itemView.findViewById(R.id.list_name_tv);
        listName_et = (EditText) itemView.findViewById(R.id.list_name_et);
        finishedEdit_iv = (ImageView) itemView.findViewById(R.id.finishedEdit_iv);
    }

    public static lists_rv_row_viewholder create(Context context, ViewGroup parent){
        return new lists_rv_row_viewholder(LayoutInflater.from(context).inflate(R.layout.list_row_cell, parent, false));
    }

    public static void bind(final Context context, final lists_rv_row_viewholder holder,
                            final ListModel listModel,
                            final ListRowRecyclerViewOnClickListener listener,
                            final OnListEdited onListEdited){

        holder.listName_tv.setText(listModel.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRowClicked(listModel.getId(), holder.getAdapterPosition());
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.listName_tv.setVisibility(View.GONE);
                holder.listName_et.setVisibility(View.VISIBLE);
                holder.finishedEdit_iv.setVisibility(View.VISIBLE);
                holder.listName_et.setText("Editable");
                holder.listName_et.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.listName_et, InputMethodManager.SHOW_IMPLICIT);
                return true;
            }
        });

        holder.listName_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    holder.listName_tv.setText("Editado");
                    holder.listName_tv.setVisibility(View.VISIBLE);
                    holder.listName_et.setVisibility(View.GONE);
                    holder.finishedEdit_iv.setVisibility(View.GONE);
                }
            }
        });

        holder.finishedEdit_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onListEdited!=null){
                    onListEdited.OnListEdited(3, holder.getAdapterPosition(),
                            holder.listName_et.getText().toString());
                }
            }
        });


    }
}