package android.study.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class login_page extends AppCompatActivity {

    public int temp = 0;

    EditText userNameEdt,userPassEdt;
    Button loginBtn;
    ImageView logoImgView;
    TextView query_result;
    TextView signNewUser;
    private static final String user = "root";
    private static final String pass = "";
    ProgressDialog pd;
    String user_phone="",user_pass="";

    //JSONParser jParser = new JSONParser();

    //JSONObject json;

    //JSONArray incoming_msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userNameEdt = findViewById(R.id.PhoneEditLogin);
        userPassEdt = findViewById(R.id.userPassEdit);

        query_result = findViewById(R.id.pagehead);

        loginBtn = findViewById(R.id.LoginUser);
        logoImgView = findViewById(R.id.app_logo_img);
        signNewUser = findViewById(R.id.signInToUp);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserPhone = userNameEdt.getText().toString();
                String Password = userPassEdt.getText().toString();
                String query = "select * from user where phoneNo = '"+UserPhone+"' and Password = '"+Password+"';";
                Log.e("query",query);
                new JsonTask().execute("https://69097247416c.ngrok.io/testing.php?query="+query);

                Log.e("temp1",Integer.toString(temp));
            }
        });

        signNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this, signUpPage.class);
                startActivity(intent);
            }
        });




    }

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }


                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//            Toast.makeText(login_page.this, result, Toast.LENGTH_SHORT).show();

//            query_result.setText(result);
            String queryResult="Name   Password\n\n";


           try {
                //JSONObject obj = new JSONObject(result);

                //String pageName = obj.getJSONObject("pageInfo").getString("pageName");


                JSONArray arr = new JSONArray(result); // notice that `"posts": [...]`
//                arr = obj.getJSONArray("results");
                for (int i = 0; i < arr.length(); i++)
                {
                    JSONObject jo = arr.getJSONObject(i);
//                    queryResult = queryResult +""+jo.getString("user_id")+"    "+jo.getString("name")+"    "+jo.getString("phoneNo")+"    "+jo.getString("Password")+"\n";
                    temp++;
                }

//               Toast.makeText(login_page.this, Integer.toString(temp), Toast.LENGTH_SHORT).show();
//               query_result.setText(queryResult);
               Log.e("tempValues",Integer.toString(temp));
               if(temp == 1){
                   startActivity(new Intent(login_page.this,MainActivity.class));
                   temp=0;
               }
               else
                   Toast.makeText(login_page.this, "Wrong Phone number or password", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
//
        }
    }

}