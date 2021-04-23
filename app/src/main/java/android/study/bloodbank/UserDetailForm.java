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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDetailForm extends AppCompatActivity {


    Button SubmitDetails;
    EditText Name,Age,City,BloodGrp,PhoneNo,Gender;
    TextView AlreadyRegistered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_form);


        Intent intent = getIntent();
        String str = intent.getStringExtra("donor");
        int var = Integer.parseInt(str);


        SubmitDetails = findViewById(R.id.SubmitUserDetails);
        SubmitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = findViewById(R.id.nameEdit);
                Age = findViewById(R.id.ageEdit);
                City = findViewById(R.id.cityEdit);
                BloodGrp = findViewById(R.id.bloodGrpEdit);
                PhoneNo = findViewById(R.id.phoneNoEdit);
                Gender = findViewById(R.id.genderEdit);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());
                Log.e("date",date);

                if (var == 1){
                    String query = "insert into donor values('"+DonorIdGen(PhoneNo.getText().toString())+"','"+Name.getText().toString()+"','"+Age.getText().toString()+"','"+City.getText().toString()+"','"+BloodGrp.getText().toString()+"','"+PhoneNo.getText().toString()+"','"+Gender.getText().toString()+"','"+date+"');";

                    Log.e("query",query);
                    new JsonTask().execute("https://f756f396f58f.ngrok.io/Donor.php?query="+query);
                    Intent intent = new Intent(getApplicationContext(), Donor.class);
                    String str = BloodGrp.getText().toString();
                    intent.putExtra("donor",str);
                    startActivity(intent);
                }
                else if (var == 2) {
                    String query = "insert into seeker values('"+DonorIdGen(PhoneNo.getText().toString())+"','"+Name.getText().toString()+"','"+Age.getText().toString()+"','"+City.getText().toString()+"','"+BloodGrp.getText().toString()+"','"+PhoneNo.getText().toString()+"','"+Gender.getText().toString()+"','"+date+"');";

                    Log.e("query",query);
                    new JsonTask().execute("https://f756f396f58f.ngrok.io/seeker.php?query="+query);

                    Intent intent = new Intent(getApplicationContext(), Reciever.class);
                    String str = BloodGrp.getText().toString();
                    intent.putExtra("seeker",str);
                    startActivity(intent);
                }
                else
                    Toast.makeText(UserDetailForm.this, "No value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String DonorIdGen(String Phone){
        return "D"+Phone;
    }

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() { super.onPreExecute(); }

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
//            try {
//                JSONArray arr = new JSONArray(result);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }
}