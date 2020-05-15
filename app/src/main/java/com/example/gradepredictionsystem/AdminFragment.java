package com.example.gradepredictionsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import interfaces.JsonApiHolder;
import interfaces.TestClas;
import model.Functions;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminFragment extends AppCompatActivity {
private String TAG = "";
private Context context;
    private List<Functions> functions;
  private   ArrayList<String> course = new ArrayList<>();
   private ArrayList<String> teacher = new ArrayList<>();
   private ArrayList<String> cmsid = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fragment);
//        Log.d(TAG, "onCreate: ");
        gotcha();
        getteacher();
        getstudent();
        Button addStudent = (Button) findViewById(R.id.addstudent);
        Button addTeacher = (Button) findViewById(R.id.addteacher);
        Button addCourse = (Button) findViewById(R.id.Courses);
        final Button assigncourse = (Button) findViewById(R.id.teacherc);
        final Button Senroll = (Button) findViewById(R.id.enroll);
        Senroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollStudent();
            }
        });
        assigncourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assigncourseteacher();
            }
        });
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addnewcourse();
            }
        });
        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddnewTeacher();
            }
        });
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddnewStudent();

            }
        });

//        sendDataToServer("ali","34924","Computer science","2015");


    }

    private void enrollStudent() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.assigncourse);
        final Spinner spin1=(Spinner) dialog.findViewById(R.id.cspin);
        final Spinner spin2=(Spinner) dialog.findViewById(R.id.tspin);
        TextView t1 = (TextView) dialog.findViewById(R.id.id1);
        TextView t2 = (TextView) dialog.findViewById(R.id.id2);
        t2.setText("Select CMSID");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, course);
        ArrayAdapter adapterb = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cmsid);
        spin1.setAdapter(adapter);
        spin2.setAdapter(adapterb);
        Button Submit = (Button) dialog.findViewById(R.id.submit);
        dialog.setTitle("Add new enrollment");
        dialog.show();
//        String a = spin1.getSelectedItem().toString();
//        String b = spin2.getSelectedItem().toString();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = spin1.getSelectedItem().toString();
                String b = spin2.getSelectedItem().toString();
                sendEnrollDatatoServer(a,b);
                dialog.cancel();
            }
        });
    }

    private void sendEnrollDatatoServer(String a, String b){
        JsonApiHolder service = RestApi.getApi();
//        File image = new File(getRealPathFromURI(imageurl));
//        if(/*frontImage == null || backImage == null ||*/ imageurl == null){
//            Toast.makeText(this, "Kindly Attach the image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        RequestBody imageRqst = RequestBody.create(MediaType.parse("image/jpg"), image);
//        MultipartBody.Part imageMultiPartRqst = MultipartBody.Part.createFormData("image", image.getName(), imageRqst);
        RequestBody namerqst = RequestBody.create(MediaType.parse("text/plain"), a);
        RequestBody cmsreq = RequestBody.create(MediaType.parse("text/plain"), b);
        RequestBody marks = RequestBody.create(MediaType.parse("text/plain"), "1");
//        RequestBody batchrqst = RequestBody.create(MediaType.parse("text/plain"), batch);
//        RequestBody psdpRqst = RequestBody.create(MediaType.parse("text/plain"), psdp);
//        RequestBody Detailrqst = RequestBody.create(MediaType.parse("text/plain"), detail);


        Call<TestClas> call = service.createEnrollData( namerqst, cmsreq,marks);
        call.enqueue(new Callback<TestClas>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<TestClas> call, Response<TestClas> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AdminFragment.this, response.body().getSuccess() , Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }else {
                    Toast.makeText(AdminFragment.this, "Response Failed!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+" fail ");

                }
            }

            @Override
            public void onFailure(Call<TestClas> call, Throwable t) {
                Toast.makeText(AdminFragment.this, "Response Failed!: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }


    private void getstudent() {
        Log.d(TAG, "gotcha: ");
        System.out.print("gotchat" );
        JsonApiHolder service = RestApi.getApi();
//        System.out.print("gotchat1" );

        Call <List<Functions>> call = service.getStudent();
        System.out.print("gotchat2" );

        call.enqueue(new Callback<List<Functions>>() {
            @Override
            public void onResponse(Call<List<Functions>> call, Response<List<Functions>> response) {
                System.out.print("gotchat3" );
                if(response.isSuccessful()){
                    System.out.print("gotchat4" );
                    List<Functions> list = response.body();
                    functions = response.body();
                    System.out.print("success");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.print("number"+i);
//                        Log.d(TAG, "onResponse: "+ list.get(i).getCourse_id().toString());
                        System.out.println("hello"+list.get(i).getCMSID());
                        System.out.print("hello"+list.get(i).getCMSID());


                        cmsid.add(list.get(i).getCMSID());
//                            teacher.add(list.get(i).getName());

//if(list.get(i)){


//}

//                       final MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(projects);
//                        markerOptions.icon(BitmapDescriptorFactory.fromAsset(list.get(i).getImage_url()));
//

                    }


                }
                if (!response.isSuccessful()){
                    System.out.print("fail");
                }
            }

            @Override
            public void onFailure(Call<List<Functions>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                System.out.print("gotchat1f" +t );
            }
        });

    }

    private void getteacher() {
        Log.d(TAG, "gotcha: ");
        System.out.print("gotchat" );
        JsonApiHolder service = RestApi.getApi();
//        System.out.print("gotchat1" );

        Call <List<Functions>> call = service.getTeacher();
        System.out.print("gotchat2" );

        call.enqueue(new Callback<List<Functions>>() {
            @Override
            public void onResponse(Call<List<Functions>> call, Response<List<Functions>> response) {
                System.out.print("gotchat3" );
                if(response.isSuccessful()){
                    System.out.print("gotchat4" );
                    List<Functions> list = response.body();
                    functions = response.body();
                    System.out.print("success");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.print("number"+i);
//                        Log.d(TAG, "onResponse: "+ list.get(i).getCourse_id().toString());
                        System.out.println("hello"+list.get(i).getName());
                        System.out.print("hello"+list.get(i).getName());


                        teacher.add(list.get(i).getName());
//                            teacher.add(list.get(i).getName());

//if(list.get(i)){


//}

//                       final MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(projects);
//                        markerOptions.icon(BitmapDescriptorFactory.fromAsset(list.get(i).getImage_url()));
//

                    }


                }
                if (!response.isSuccessful()){
                    System.out.print("fail");
                }
            }

            @Override
            public void onFailure(Call<List<Functions>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                System.out.print("gotchat1f" +t );
            }
        });

    }


    public void gotcha() {
            Log.d(TAG, "gotcha: ");
System.out.print("gotchat" );
            JsonApiHolder service = RestApi.getApi();
            System.out.print("gotchat1" );

            Call <List<Functions>> call = service.getCourses();
            System.out.print("gotchat2" );

            call.enqueue(new Callback<List<Functions>>() {
                @Override
                public void onResponse(Call<List<Functions>> call, Response<List<Functions>> response) {
                    System.out.print("gotchat3" );
                    if(response.isSuccessful()){
                        System.out.print("gotchat4" );
                        List<Functions> list = response.body();
                        functions = response.body();
                        System.out.print("success");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.print("number"+i);
                            Log.d(TAG, "onResponse: "+ list.get(i).getCourse_id().toString());
                            System.out.println("hello"+list.get(i).getCourse_id());
                            System.out.print("hello"+list.get(i).getCourse_id());


                            course.add(list.get(i).getCourse_id());
//                            teacher.add(list.get(i).getName());

//if(list.get(i)){


//}

//                       final MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(projects);
//                        markerOptions.icon(BitmapDescriptorFactory.fromAsset(list.get(i).getImage_url()));
//

                        }


                    }
                    if (!response.isSuccessful()){
                        System.out.print("fail");
                    }
                }

                @Override
                public void onFailure(Call<List<Functions>> call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t);
                    System.out.print("gotchat1f" +t );
                }
            });

        }



    private void assigncourseteacher() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.assigncourse);
        final Spinner spin1=(Spinner) dialog.findViewById(R.id.cspin);
        final Spinner spin2=(Spinner) dialog.findViewById(R.id.tspin);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, course);
        ArrayAdapter adapterb = new ArrayAdapter(this, android.R.layout.simple_list_item_1, teacher);
        spin1.setAdapter(adapter);
        spin2.setAdapter(adapterb);
        Button Submit = (Button) dialog.findViewById(R.id.submit);
        dialog.setTitle("Add new class");
        dialog.show();
        String a = spin1.getSelectedItem().toString();
        String b = spin2.getSelectedItem().toString();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = spin1.getSelectedItem().toString();
                String b = spin2.getSelectedItem().toString();
            sendClassDatatoServer(a,b);
            dialog.cancel();
            }
        });
    }

    private void sendClassDatatoServer(String a, String b) {
        JsonApiHolder service = RestApi.getApi();
//        File image = new File(getRealPathFromURI(imageurl));
//        if(/*frontImage == null || backImage == null ||*/ imageurl == null){
//            Toast.makeText(this, "Kindly Attach the image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        RequestBody imageRqst = RequestBody.create(MediaType.parse("image/jpg"), image);
//        MultipartBody.Part imageMultiPartRqst = MultipartBody.Part.createFormData("image", image.getName(), imageRqst);
        RequestBody namerqst = RequestBody.create(MediaType.parse("text/plain"), a);
        RequestBody cmsreq = RequestBody.create(MediaType.parse("text/plain"), b);
//        RequestBody batchrqst = RequestBody.create(MediaType.parse("text/plain"), batch);
//        RequestBody psdpRqst = RequestBody.create(MediaType.parse("text/plain"), psdp);
//        RequestBody Detailrqst = RequestBody.create(MediaType.parse("text/plain"), detail);


        Call<TestClas> call = service.createClassData( namerqst, cmsreq);
        call.enqueue(new Callback<TestClas>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<TestClas> call, Response<TestClas> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AdminFragment.this, response.body().getSuccess() , Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }else {
                    Toast.makeText(AdminFragment.this, "Response Failed!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+" fail ");

                }
            }

            @Override
            public void onFailure(Call<TestClas> call, Throwable t) {
                Toast.makeText(AdminFragment.this, "Response Failed!: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });

    }

    private void Addnewcourse() {

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog3tab);
            final EditText Stname = (EditText) dialog.findViewById(R.id.stname);
            final EditText Stcms = (EditText) dialog.findViewById(R.id.stcms);
            final EditText Stbatch = (EditText) dialog.findViewById(R.id.stbatch);
//        final EditText Stdept = (EditText) dialog.findViewById(R.id.stDept);
            Button Submit = (Button) dialog.findViewById(R.id.submit);
            dialog.setTitle("Add new entry");
            dialog.show();
            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a= Stname.getText().toString();
                    String b= Stcms.getText().toString();
                    String c= Stbatch.getText().toString();
                    SendCourseDataTotServer(a,b,c);
                }
            });



    }

    private void SendCourseDataTotServer(String a, String b, String c) {
        JsonApiHolder service = RestApi.getApi();
//        File image = new File(getRealPathFromURI(imageurl));
//        if(/*frontImage == null || backImage == null ||*/ imageurl == null){
//            Toast.makeText(this, "Kindly Attach the image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        RequestBody imageRqst = RequestBody.create(MediaType.parse("image/jpg"), image);
//        MultipartBody.Part imageMultiPartRqst = MultipartBody.Part.createFormData("image", image.getName(), imageRqst);
        RequestBody namerqst = RequestBody.create(MediaType.parse("text/plain"), a);
        RequestBody cmsreq = RequestBody.create(MediaType.parse("text/plain"), b);
        RequestBody deptrqst = RequestBody.create(MediaType.parse("Integer/int"), c);
//        RequestBody batchrqst = RequestBody.create(MediaType.parse("text/plain"), batch);
//        RequestBody psdpRqst = RequestBody.create(MediaType.parse("text/plain"), psdp);
//        RequestBody Detailrqst = RequestBody.create(MediaType.parse("text/plain"), detail);


        Call<TestClas> call = service.createCourseData( namerqst, cmsreq, deptrqst);
        call.enqueue(new Callback<TestClas>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<TestClas> call, Response<TestClas> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AdminFragment.this, response.body().getSuccess() , Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }else {
                    Toast.makeText(AdminFragment.this, "Response Failed!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+" fail ");

                }
            }

            @Override
            public void onFailure(Call<TestClas> call, Throwable t) {
                Toast.makeText(AdminFragment.this, "Response Failed!: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    private void AddnewTeacher() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog3tab);
        final EditText Stname = (EditText) dialog.findViewById(R.id.stname);
        final EditText Stcms = (EditText) dialog.findViewById(R.id.stcms);
        final EditText Stbatch = (EditText) dialog.findViewById(R.id.stbatch);
//        final EditText Stdept = (EditText) dialog.findViewById(R.id.stDept);
        Button Submit = (Button) dialog.findViewById(R.id.submit);
        dialog.setTitle("Add new entry");
        dialog.show();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a= Stname.getText().toString();
                String b= Stcms.getText().toString();
                String c= Stbatch.getText().toString();
SendTeacherDataTotServer(a,b,c);
            }
        });

    }

    private void SendTeacherDataTotServer(String Name,String Password,String Designation) {
        //        projectId = UUID.randomUUID().toString();
        JsonApiHolder service = RestApi.getApi();
//        File image = new File(getRealPathFromURI(imageurl));
//        if(/*frontImage == null || backImage == null ||*/ imageurl == null){
//            Toast.makeText(this, "Kindly Attach the image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        RequestBody imageRqst = RequestBody.create(MediaType.parse("image/jpg"), image);
//        MultipartBody.Part imageMultiPartRqst = MultipartBody.Part.createFormData("image", image.getName(), imageRqst);
        RequestBody namerqst = RequestBody.create(MediaType.parse("text/plain"), Name);
        RequestBody cmsreq = RequestBody.create(MediaType.parse("text/plain"), Password);
        RequestBody deptrqst = RequestBody.create(MediaType.parse("text/plain"), Designation);
//        RequestBody batchrqst = RequestBody.create(MediaType.parse("text/plain"), batch);
//        RequestBody psdpRqst = RequestBody.create(MediaType.parse("text/plain"), psdp);
//        RequestBody Detailrqst = RequestBody.create(MediaType.parse("text/plain"), detail);


        Call<TestClas> call = service.createTeacherData( namerqst, cmsreq, deptrqst);
        call.enqueue(new Callback<TestClas>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<TestClas> call, Response<TestClas> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AdminFragment.this, response.body().getSuccess() , Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }else {
                    Toast.makeText(AdminFragment.this, "Response Failed!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+" fail ");

                }
            }

            @Override
            public void onFailure(Call<TestClas> call, Throwable t) {
                Toast.makeText(AdminFragment.this, "Response Failed!: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }



    private void AddnewStudent() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog3);
        final EditText Stname = (EditText) dialog.findViewById(R.id.stname);
        final EditText Stcms = (EditText) dialog.findViewById(R.id.stcms);
        final EditText Stbatch = (EditText) dialog.findViewById(R.id.stbatch);
        final EditText Stdept = (EditText) dialog.findViewById(R.id.stDept);
        Button Submit = (Button) dialog.findViewById(R.id.submit);
        dialog.setTitle("Add new entry");
        dialog.show();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namest = Stname.getText().toString();
                String cmsst = Stcms.getText().toString();
                String batchst = Stbatch.getText().toString();
                String deptst= Stdept.getText().toString();
                sendDataToServer(namest,cmsst,deptst,batchst);
                dialog.cancel();
            }
        });
    }

    private void sendDataToServer(String name, String cmsid, String department,  String batch) {
//        projectId = UUID.randomUUID().toString();
        JsonApiHolder service = RestApi.getApi();
//        File image = new File(getRealPathFromURI(imageurl));
//        if(/*frontImage == null || backImage == null ||*/ imageurl == null){
//            Toast.makeText(this, "Kindly Attach the image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        RequestBody imageRqst = RequestBody.create(MediaType.parse("image/jpg"), image);
//        MultipartBody.Part imageMultiPartRqst = MultipartBody.Part.createFormData("image", image.getName(), imageRqst);
        RequestBody namerqst = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody cmsreq = RequestBody.create(MediaType.parse("text/plain"), cmsid);
        RequestBody deptrqst = RequestBody.create(MediaType.parse("text/plain"), department);
        RequestBody batchrqst = RequestBody.create(MediaType.parse("text/plain"), batch);
//        RequestBody psdpRqst = RequestBody.create(MediaType.parse("text/plain"), psdp);
//        RequestBody Detailrqst = RequestBody.create(MediaType.parse("text/plain"), detail);


        Call<TestClas> call = service.createProjectData( namerqst, cmsreq, deptrqst, batchrqst);
        call.enqueue(new Callback<TestClas>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<TestClas> call, Response<TestClas> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AdminFragment.this, response.body().getSuccess() , Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }else {
                    Toast.makeText(AdminFragment.this, "Response Failed!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+" fail ");

                }
            }

            @Override
            public void onFailure(Call<TestClas> call, Throwable t) {
                Toast.makeText(AdminFragment.this, "Response Failed!: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

}
