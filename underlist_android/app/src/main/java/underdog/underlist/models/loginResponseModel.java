package underdog.underlist.models;

import java.util.List;

import underdog.underlist.models.user.UserEntity;

/**
 * Created by Eric on 12/07/16.
 *
 */
public class loginResponseModel {

    private boolean error;
    private String message;
    private List<UserEntity> user;

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserEntity> getUser() {
        return user;
    }

    public void setUser(List<UserEntity> user) {
        this.user = user;
    }


}
