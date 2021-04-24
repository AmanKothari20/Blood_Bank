package android.study.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reciever extends AppCompatActivity {

    TextView DonorList,FinalResult;
    String str = "";
    String quantity = "";
    String BloodGrp = "";
    int temp =0,temp2=0;
    int Case = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever);

        DonorList = findViewById(R.id.DonorList);
        FinalResult = findViewById(R.id.FinalText);

        Intent intent = getIntent();
        str = intent.getStringExtra("seeker");
        Log.e("bloodgrp",str);

        String query = "select * from bloodstock where BloodGroup = '"+str+"'";
        new JsonTask().execute(login_page.url+"/bloodstock.php?query="+query);
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
            Case++;
            Log.e("Case",Integer.toString(Case));
//            Toast.makeText(login_page.this, result, Toast.LENGTH_SHORT).show();

//            query_result.setText(result);



            try {
                //JSONObject obj = new JSONObject(result);

                //String pageName = obj.getJSONObject("pageInfo").getString("pageName");
                String queryResult = "";

                JSONArray arr = new JSONArray(result); // notice that `"posts": [...]`
//                arr = obj.getJSONArray("results");
                if(Case == 1){
                    queryResult="Blood Group\tStock\tBest Before\n\n";
                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject jo = arr.getJSONObject(i);
                        queryResult = queryResult +""+jo.getString("BloodGroup")+"        "+jo.getString("Quantity")+"        "+jo.getString("BestBefore")+"\n";
                        quantity = jo.getString("Quantity");
                        BloodGrp = jo.getString("BloodGroup");
                        temp++;
                    }
                }
                else if(Case == 2){
                    queryResult="Name Age City BloodGroup PhoneNo gender\n\n";
                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject jo = arr.getJSONObject(i);
                        queryResult = queryResult +""+jo.getString("name")+" "+jo.getString("age")+" "+jo.getString("city")+" "+jo.getString("bloodGroup")+" "+jo.getString("phoneNo")+" "+jo.getString("gender")+"\n";
                        temp2++;
                    }
                }


//               Toast.makeText(login_page.this, Integer.toString(temp), Toast.LENGTH_SHORT).show();
                DonorList.setText(queryResult);
                Log.e("temp",Integer.toString(temp));




                if(Integer.valueOf(quantity) >0 ){
                    String text = " You can come and collect blood from bloodbank";
                    FinalResult.setText(text);
                    String UpdateStockValue = Integer.toString(Integer.valueOf(quantity)-1);
                    String query = "UPDATE bloodstock SET Quantity = '"+UpdateStockValue+"' WHERE (BloodGroup = '"+BloodGrp+"');\n";
                    new JsonTask().execute(login_page.url+"/bloodstock.php?query="+query);

                }
                if(Case == 1){
                    if(Integer.valueOf(quantity) == 0 ){
                        String text = "List of potential donors for blood type "+str;
                        FinalResult.setText(text);

                        String query = "select * from donor where bloodGroup = '"+str+"';\n";
                        Log.e("donor list",query);
                        new JsonTask().execute(login_page.url+"/donor.php?query="+query);
                        temp++;
                    }
                    if(temp2 == 0){
                        String text = "We dont have any donor or blood stock of blood type :"+str;
                        FinalResult.setText(text);
                    }
                }




//                Log.e("temp2",Integer.toString(temp2));
//                if(temp == 0 || Integer.valueOf(quantity) <= 0 ){
//                        String text = "Sorry we don't have this type of blood presently";
//                        FinalResult.setText(text);
//                }


            } catch (Exception e) {
                e.printStackTrace();
            }
//
        }
    }
}