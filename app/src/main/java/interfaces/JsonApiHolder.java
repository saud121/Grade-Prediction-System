package interfaces;

import java.util.List;

import model.Functions;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface JsonApiHolder {
    @Multipart
    @POST("create_student_data")
    Call<TestClas> createProjectData(
//            @Part MultipartBody.Part image,
            @Part("Name") RequestBody Name,
            @Part("CMSID") RequestBody CMSID,
            @Part("Department") RequestBody Department,
            @Part("Batch") RequestBody Batch
    );
    @Multipart
    @POST("create_teacher_data")
    Call<TestClas> createTeacherData(
//            @Part MultipartBody.Part image,
            @Part("Name") RequestBody Name,
            @Part("Password") RequestBody CMSID,
            @Part("Designation") RequestBody Department
    );
    @Multipart
    @POST("create_course_data")
    Call<TestClas> createCourseData(
//            @Part MultipartBody.Part image,
            @Part("Course_id") RequestBody Name,
            @Part("Course_name") RequestBody CMSID,
            @Part("Credit_hours") RequestBody Department
    );
    @Multipart
    @POST("create_enroll_data")
    Call<TestClas> createEnrollData(
//            @Part MultipartBody.Part image,
            @Part("Course_id") RequestBody Name,
            @Part("CMSID") RequestBody CMSID,
            @Part("marks") RequestBody marks
    );
    @Multipart
    @POST("create_class_data")
    Call<TestClas> createClassData(
//            @Part MultipartBody.Part image,
            @Part("Course_id") RequestBody Name,
            @Part("Teacher_name") RequestBody CMSID
    );

    @Multipart
    @POST("update_studnet_marks")
    Call<TestClas> updateMarks(
//            @Part MultipartBody.Part image,
            @Part("marks") RequestBody marks,
            @Part("CMSID") RequestBody CMSID
    );
    @GET("getcourse")
    Call<List<Functions>> getCourses();
    @GET("getteacher")
    Call<List<Functions>> getTeacher();
    @GET("getstudent")
    Call<List<Functions>> getStudent();
}
