package android.study.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class Donor extends AppCompatActivity {

    TextView ResultTable,OutputText;
    String Stock = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        //String minBlood[][] = {{"A+","10"},{"A-","10"},{"B+","10"},{"B-","10"},{"O+","10"},{"O-","10"},{"AB+","10"},{"AB-","10"}};

        ResultTable = findViewById(R.id.resultText);
        OutputText = findViewById(R.id.outputText);
        String query = "select * from bloodstock";
        new JsonTask().execute("https://f756f396f58f.ngrok.io/bloodstock.php?query="+query);


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
            String queryResult="Blood Group\tStock\tBest Before\n\n";


            try {
                //JSONObject obj = new JSONObject(result);

                //String pageName = obj.getJSONObject("pageInfo").getString("pageName");


                JSONArray arr = new JSONArray(result); // notice that `"posts": [...]`
//                arr = obj.getJSONArray("results");
                for (int i = 0; i < arr.length(); i++)
                {
                    JSONObject jo = arr.getJSONObject(i);
                     queryResult = queryResult +""+jo.getString("BloodGroup")+"        "+jo.getString("Quantity")+"        "+jo.getString("BestBefore")+"\n";
                    Stock = jo.getString("Quantity");
                }

//               Toast.makeText(login_page.this, Integer.toString(temp), Toast.LENGTH_SHORT).show();
               ResultTable.setText(queryResult);
                if(Integer.valueOf(Stock) < 10){
                    String text = " You can come tomorrow and donate blood";
                    OutputText.setText(text);
                }
                else{
                    String text = "Your type of blood is enough in the blood bank. we will contact you in case of emergency";
                    OutputText.setText(text);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
//
        }
    }
}