package underdog.underlist.models.recycler_items;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import underdog.underlist.models.lists.ListModel;

/**
 * Created by Eric on 13/07/16.
 *
 */
public class ListRecyclerItem <T> {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ROW, SECTION})
    public @interface ViewType {
    }

    public static final int SECTION = 0;
    public static final int ROW = 1;

    @ViewType public int viewType;
    public  ListModel listModel;

    public ListRecyclerItem(int viewType, ListModel listModel) {
        this.viewType = viewType;
        this.listModel = listModel;
    }


}