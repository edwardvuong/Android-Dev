package e.edwardvuong.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    int loginAttempts;
    AuthenticationService auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView register = findViewById(R.id.register_text_link);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        auth = new AuthenticationService(this);

        //Text fields
        final EditText username = findViewById(R.id.Username);
        final EditText password = findViewById(R.id.Password);

        Button login = findViewById(R.id.signout_button);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Get text from fields
                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();

                //Error handling
                TextInputLayout usernameInputError = findViewById(R.id.username_input_error);
                usernameInputError.setError(null);
                TextInputLayout passwordInputError = findViewById(R.id.password_input_error);
                passwordInputError.setError(null);

                int fieldErrors = 0;
                if (usernameInput.isEmpty()) {
                    fieldErrors++;
                    usernameInputError.setError("This is a required field.");
                }
                if (passwordInput.isEmpty()) {
                    fieldErrors++;
                    passwordInputError.setError("This is a required field.");
                }

                if(loginAttempts >= 2) {
                    Toast.makeText(LoginActivity.this, "Maximum amount of login attempts. ",
                            Toast.LENGTH_LONG).show();
                    System.exit(0);
                }

                if(fieldErrors == 0) {
                    if(auth.authenticateLogin(usernameInput, passwordInput)) {
                        Toast.makeText(LoginActivity.this, "Login is successful.",
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                    }
                    else {
                        loginAttempts++;
                        Toast.makeText(LoginActivity.this, "Username or password is incorrect. ",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
