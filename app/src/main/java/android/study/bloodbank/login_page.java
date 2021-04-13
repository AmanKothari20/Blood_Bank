package android.study.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class login_page extends AppCompatActivity {

    EditText userNameEdt,userPassEdt;
    Button loginBtn;
    ImageView logoImgView;
    private static final String user = "root";
    private static final String pass = "Mangalam06-";
    String user_phone="",user_pass="";

    //JSONParser jParser = new JSONParser();

    //JSONObject json;
    private static String url_login = "jdbc:mysql://127.0.0.1:3306/Blood_Bank";
    //JSONArray incoming_msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userNameEdt = findViewById(R.id.email);
        userPassEdt = findViewById(R.id.userP);

        loginBtn = findViewById(R.id.LoginUser);
        logoImgView = findViewById(R.id.app_logo_img);

        //Picasso.get().load("http://192.168.1.2:8080/sql_img.png").into(logoImgView);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");

            }
        });




    }

    @SuppressLint("StaticFieldLeak")
    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(login_page.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url_login, user, pass);
                System.out.println("Databaseection success");

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select distinct phoneno from user");
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result += rs.getString(1).toString() + "\n";
                    user_phone = ""+rs.getString(1);
                }
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(login_page.this, result, Toast.LENGTH_SHORT).show();
            if(user_phone.equalsIgnoreCase(userNameEdt.getText().toString())){
                startActivity(new Intent(login_page.this,MainActivity.class));
            }
        }
    }

}