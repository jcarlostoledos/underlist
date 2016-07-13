package underdog.underlist.models;

import java.util.List;

import underdog.underlist.models.task.taskEntity;

/**
 * Created by Eric on 13/07/16.
 */
public class getTasksResponseModel {

    private boolean error;
    private List<taskEntity> tasks;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<taskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<taskEntity> tasks) {
        this.tasks = tasks;
    }


}
