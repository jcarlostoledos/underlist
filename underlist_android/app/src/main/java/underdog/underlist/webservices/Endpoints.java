package underdog.underlist.webservices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import underdog.underlist.models.addListResponseModel;
import underdog.underlist.models.addTaskResponseModel;
import underdog.underlist.models.getListResponseModel;
import underdog.underlist.models.getTasksResponseModel;
import underdog.underlist.models.loginResponseModel;
import underdog.underlist.models.registerResponseModel;
import underdog.underlist.models.successResponse;

/**
 * Created by Eric on 12/07/16.
 *
 */
public interface Endpoints {

    /**
     * LOGIN USER
     * @param username username to login
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Call<loginResponseModel> loginUser(
            @Field("username") String username);

    /**
     * USER REGISTRATION
     * @param username username chosen by the user
     * @param name name of the user
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Call<registerResponseModel> registerUser(
            @Field("name") String name,
            @Field("username") String username);

    @GET("user/{userId}/list")
    Call<getListResponseModel> getAllLists(
            @Path("userId") String userId);


    /**
     *Add a to do list
     * @param name name for the list
     * @param description description of the list
     * @param createdDate list date creation
     * @param userId userId of the user making the list
     * @return
     */
    @FormUrlEncoded
    @POST("list")
    Call<addListResponseModel> addList(
            @Field("title") String name,
            @Field("description") String description,
            @Field("createdDate") String createdDate,
            @Field("userId") String userId);

    /**
     *Add task to a list
     * @param name name for the new task
     * @param description description of the new task
     * @param createdDate task date creation
     * @param dueDate task due date
     * @param userId userId from the user adding a task
     * @param listId listId that will store the new task
     * @return
     */
    @FormUrlEncoded
    @POST("task")
    Call<addTaskResponseModel> addTask(
            @Field("title") String name,
            @Field("description") String description,
            @Field("createdDate") String createdDate,
            @Field("dueDate") String dueDate,
            @Field("userId") String userId,
            @Field("listId") String listId);

    /**
     * All tasks from a user
     * @param userId userId from the user querying tasks
     * @return
     */
    @GET("user/{userId}/list/{listId}/task")
    Call<getTasksResponseModel> getAllTasks(
            @Path("userId") String userId,
            @Path("listId") String listId);
    /**
     *
     * @param listId listId from the list that will be deleted
     * @return success model
     */
    @DELETE("list/{listId}")
    Call<ArrayList<successResponse>> deleteList(
            @Path("listId") int listId);

    /**
     * Update task
     * @param taskId task id to update
     * @param status status for the task done/undone
     * @return
     */
    @FormUrlEncoded
    @PUT("task/{taskId}/{status}")
    Call<ArrayList<successResponse>> updateTaskStatus(
            @Path("taskId") int taskId,
            @Path("status") int status);


    /**
     * Edit List
     * @param listId id of the edited list
     * @param title title for the list
     * @param description description for the list
     * @return response
     */
    @FormUrlEncoded
    @PUT("list/{listId}")
    Call<ArrayList<successResponse>> editList(
            @Path("listId") int listId,
            @Field("title") String title,
            @Field("description") String description);

}
