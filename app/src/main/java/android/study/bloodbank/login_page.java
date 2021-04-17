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

public class login_page extends AppCompatActivity {

    EditText userNameEdt,userPassEdt;
    Button loginBtn;
    ImageView logoImgView;
    TextView query_result;
    private static final String user = "root";
    private static final String pass = "";
    ProgressDialog pd;
    String user_phone="",user_pass="";

    //JSONParser jParser = new JSONParser();

    //JSONObject json;
    private static String url_login = "jdbc:mysql://198.168.1.1:3306/blood_bank_management";
    //JSONArray incoming_msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userNameEdt = findViewById(R.id.email);
        userPassEdt = findViewById(R.id.userP);
        query_result = findViewById(R.id.pagehead);

        loginBtn = findViewById(R.id.LoginUser);
        logoImgView = findViewById(R.id.app_logo_img);

        //Picasso.get().load("http://192.168.1.2:8080/sql_img.png").into(logoImgView);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "select * from users where name = 'mangalam'";
                new JsonTask().execute("https://4ba53609fd45.ngrok.io/testing.php?query="+query);

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

            query_result.setText(result);
            String queryResult="Name   Password\n\n";

           try {
                //JSONObject obj = new JSONObject(result);

                //String pageName = obj.getJSONObject("pageInfo").getString("pageName");


                JSONArray arr = new JSONArray(result); // notice that `"posts": [...]`
                //arr = obj.getJSONArray("results");
                for (int i = 0; i < arr.length(); i++)
                {
                    JSONObject jo = arr.getJSONObject(i);
                    queryResult = queryResult +""+ jo.getString("name")+"    "+jo.getString("password")+"\n";

                }
                query_result.setText(queryResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}