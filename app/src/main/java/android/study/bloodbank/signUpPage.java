package android.study.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class signUpPage extends AppCompatActivity {

    Button Register_btn;
    TextView BackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_new_user);

        Register_btn = findViewById(R.id.RegisterUser);

        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "insert into user values('U3','anirudh','1234567891','anirudh');";
                new JsonTask().execute("https://9db616c901ac.ngrok.io/testing.php?query="+query);


                startActivity(new Intent(signUpPage.this,login_page.class));
            }
        });

        BackToLogin = findViewById(R.id.already_user_text);

        BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signUpPage.this,login_page.class));
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
            Toast.makeText(signUpPage.this, result, Toast.LENGTH_SHORT).show();

//            query_result.setText(result);
            String queryResult="Name   Password\n\n";

            try {
                //JSONObject obj = new JSONObject(result);

                //String pageName = obj.getJSONObject("pageInfo").getString("pageName");


                JSONArray arr = new JSONArray(result); // notice that `"posts": [...]`
                //arr = obj.getJSONArray("results");
//                for (int i = 0; i < arr.length(); i++)
//                {
//                    JSONObject jo = arr.getJSONObject(i);
//                    queryResult = queryResult +""+jo.getString("user_id")+"    "+jo.getString("name")+"    "+jo.getString("phoneNo")+"\n";
//
//                }
//                query_result.setText(queryResult);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}