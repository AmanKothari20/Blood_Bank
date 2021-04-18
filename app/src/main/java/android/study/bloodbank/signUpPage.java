package android.study.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                Toast.makeText(signUpPage.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

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
}