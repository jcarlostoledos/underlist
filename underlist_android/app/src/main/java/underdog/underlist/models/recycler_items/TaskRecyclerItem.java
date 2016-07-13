package underdog.underlist.models.recycler_items;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import underdog.underlist.models.task.taskEntity;

/**
 * Created by Eric on 13/07/16.
 *
 */
public class TaskRecyclerItem<T> {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ROW, SECTION})
    public @interface ViewType {
    }

    public static final int SECTION = 0;
    public static final int ROW = 1;

    @ViewType public int viewType;
    public taskEntity taskEntity;

    public TaskRecyclerItem(int viewType, taskEntity taskEntity) {
        this.viewType = viewType;
        this.taskEntity = taskEntity;
    }


}