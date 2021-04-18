package android.study.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetailForm extends AppCompatActivity {


    Button SubmitDetails;

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
                if (var == 1)
                    startActivity(new Intent(getApplicationContext(), Donor.class));
                else if (var == 2)
                    startActivity(new Intent(getApplicationContext(), Reciever.class));
                else
                    Toast.makeText(UserDetailForm.this, "No value", Toast.LENGTH_SHORT).show();
            }
        });
    }
}