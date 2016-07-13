package underdog.underlist.models;

import java.util.List;

import underdog.underlist.models.lists.ListModel;

/**
 * Created by Eric on 13/07/16.
 */
public class getListResponseModel {

    private boolean error;
    private List<ListModel> lists;
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ListModel> getLists() {
        return lists;
    }

    public void setLists(List<ListModel> lists) {
        this.lists = lists;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
