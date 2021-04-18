package android.study.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout BloodDonor;
    RelativeLayout Recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BloodDonor = findViewById(R.id.blood_donor_card);
        BloodDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UserDetailForm.class);
                String str = "1";
                intent.putExtra("donor",str);
                startActivity(intent);
            }
        });

        Recipient = findViewById(R.id.blood_reciepient_card);
        Recipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UserDetailForm.class);
                String str = "2";
                intent.putExtra("donor",str);
                startActivity(intent);
            }
        });

    }


}