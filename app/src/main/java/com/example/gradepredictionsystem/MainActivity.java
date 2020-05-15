package com.example.gradepredictionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import java.util.ArrayList;
import java.util.List;

import interfaces.JsonApiHolder;
import model.Functions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "";
    private EditText Name;
    private ShowHidePasswordEditText Password;
    public Context context;
    private TextView Info;
    private ImageView Login;
    private LinearLayout register;
    private int counter = 5;
    private int i =0;
    String[] roles={"Teacher","Admin"};
    String role = "";
    private List<Functions> functions;
    private   ArrayList<String> username = new ArrayList<>();
    private   ArrayList<String> password = new ArrayList<>();
    private   ArrayList<String> designation = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spin =(Spinner) findViewById(R.id.spin1);

        Name = (EditText)findViewById(R.id.etName);
        Password = (ShowHidePasswordEditText) findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (ImageView) findViewById(R.id.btnLogin);
        register = (LinearLayout) findViewById(R.id.Register);
        getteacher();


        Info.setText("No of attempts remaining: 5");
        spin.setOnItemSelectedListener(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString(),role);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),SecondActivity.class);
                startActivity(intent);
            }
        });
        ArrayAdapter aa=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,roles);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }

    private void getteacher() {
        Log.d(TAG, "gotcha: ");
        System.out.print("gotchat" );
        JsonApiHolder service = RestApi.getApi();
//        System.out.print("gotchat1" );

        Call<List<Functions>> call = service.getTeacher();
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
                        System.out.print("hello"+list.get(i).getDesignation());


                        username.add(list.get(i).getName());
                        password.add(list.get(i).getPassword());
                        designation.add(list.get(i).getDesignation());
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
    private void validate(String userName, String userPassword, String role){
        for (i=0 ; i<username.size();i++){
        if((userName.contains(username.get(i).toString())) && (userPassword.equals(password.get(i).toString()))&&(role.equals("Teacher"))){
            String n = username.get(i);
            String d = designation.get(i);
            Intent intent = new Intent(MainActivity.this, TeacherFragment.class);
//            ((TeacherFragment) context).setName(n,d );
            startActivity(intent);
        }
        }
         if((userName.equals("Admin")) && (userPassword.equals("1234"))&&(role.equals("Admin"))){

            Intent intent = new Intent(MainActivity.this,AdminFragment.class);
            startActivity(intent);
        }

        else{
            counter--;
             Toast.makeText(this, "Username or Password is incorrect you have "+String.valueOf(counter) +": No of attempts remaining", Toast.LENGTH_SHORT).show();
            Info.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);

            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (roles[position].equals("Teacher")){
            role="Teacher";


        }
        else if (roles[position].equals("Admin")){

            role="Admin";


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
