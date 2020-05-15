package com.example.gradepredictionsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapters.ExampleItem;
import Adapters.TeacherAdapter;
import interfaces.JsonApiHolder;
import interfaces.TestClas;
import model.Functions;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherFragment extends AppCompatActivity {
    private TeacherAdapter adapter;
    private List<Functions> exampleList = new ArrayList<>();
    private SearchView searchView;
    String TAG = "";
    TextView StudentName;
    TextView cms;
    TextView tname;
    TextView tdesig;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_fragment);
        searchView = (SearchView) findViewById(R.id.search1);
        StudentName= (TextView) findViewById(R.id.text_view11);
        cms= (TextView) findViewById(R.id.text_view22);
        tname = (TextView) findViewById(R.id.teachername);
        tdesig = (TextView) findViewById(R.id.teacherdeisg);
        Button marks = (Button) findViewById(R.id.markssubmit);
        Button gmail1 = (Button) findViewById(R.id.gmail1);
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



//        fillExampleList();
        getstudent();
        setUpRecyclerView();
        setSearchView();
gmail1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submitmarks();
            }
        });
//        setStudentTab(txt1,txt2);

    }
    public void setName(String name, String desig){
        tname.setText(name);
        tdesig.setText(desig);


    }

    private void Submitmarks() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.marksdialog);
        final EditText edit1 = (EditText) dialog.findViewById(R.id.marksi);
        Button Submit = (Button) dialog.findViewById(R.id.submit);
        dialog.setTitle("Add marks");
        dialog.show();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edit1.getText().toString();
                String b = cms.getText().toString();
                sendMarksDatatoServer(a,b);
                dialog.cancel();
            }
        });

    }

    private void sendMarksDatatoServer(String a, String b) {
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
//        RequestBody marks = RequestBody.create(MediaType.parse("text/plain"), "1");
//        RequestBody batchrqst = RequestBody.create(MediaType.parse("text/plain"), batch);
//        RequestBody psdpRqst = RequestBody.create(MediaType.parse("text/plain"), psdp);
//        RequestBody Detailrqst = RequestBody.create(MediaType.parse("text/plain"), detail);


        Call<TestClas> call = service.updateMarks(namerqst, cmsreq);
        call.enqueue(new Callback<TestClas>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<TestClas> call, Response<TestClas> response) {
                if(response.isSuccessful()){
                    Toast.makeText(TeacherFragment.this, response.body().getSuccess() , Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }else {
                    Toast.makeText(TeacherFragment.this, "Response Failed!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+" fail ");

                }
            }

            @Override
            public void onFailure(Call<TestClas> call, Throwable t) {
                Toast.makeText(TeacherFragment.this, "Response Failed!: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    private void setupAdapter(List<Functions> contacts) {
        adapter = new TeacherAdapter(contacts, TeacherFragment.this);
        recyclerView.setAdapter(adapter);
    }


    private void getstudent() {
        Log.d(TAG, "gotcha: ");
        System.out.print("gotchat" );
        JsonApiHolder service = RestApi.getApi();
//        System.out.print("gotchat1" );

        Call<List<Functions>> call = service.getStudent();
        System.out.print("gotchat2" );

        call.enqueue(new Callback<List<Functions>>() {
            @Override
            public void onResponse(Call<List<Functions>> call, Response<List<Functions>> response) {
                System.out.print("gotchat3" );
                if(response.isSuccessful()){
                    System.out.print("gotchat4" );
                    List<Functions> list = response.body();
                    exampleList = response.body();
                    adapter = new TeacherAdapter(exampleList, TeacherFragment.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    System.out.print("success");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.print("number"+i);
//                        Log.d(TAG, "onResponse: "+ list.get(i).getCourse_id().toString());
                        System.out.println("hello"+list.get(i).getCMSID());
                        System.out.print("hello"+list.get(i).getCMSID());
//                        exampleList.add(R.drawable.ic_person_pin_circle_b_24dp,list.get(i).getName(),list.get(i).getCMSID());



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
    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager
        adapter = new TeacherAdapter(exampleList,TeacherFragment.this);

//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
private void setSearchView(){
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    searchView.setSearchableInfo(
            searchManager.getSearchableInfo(getComponentName()));
    searchView.setIconifiedByDefault(false);
    Log.d(TAG, "onCreateOptionsMenu: ");
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Log.d(TAG, "onQueryTextChange: "+"text change");
            adapter.getFilter().filter(newText);
            return false;
        }
    });
}
    public void setStudentTab(String txt1,String txt2) {
        Log.d(TAG, "setStudentTab: "+txt1);
        StudentName.setText(txt1);
        cms.setText(txt2);

    }
}
