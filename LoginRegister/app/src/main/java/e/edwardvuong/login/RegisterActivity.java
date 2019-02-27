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

public class RegisterActivity extends AppCompatActivity {

    AuthenticationService auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView login = findViewById(R.id.register_text_link);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        auth = new AuthenticationService(this);

        //Text fields
        final EditText firstName = (EditText) findViewById(R.id.FirstName);
        final EditText lastName = (EditText) findViewById(R.id.LastName);
        final EditText age = (EditText) findViewById(R.id.Age);
        final EditText username = (EditText) findViewById(R.id.Username);
        final EditText email = (EditText) findViewById(R.id.Email);
        final EditText password = (EditText) findViewById(R.id.Password);
        final EditText passwordConfirm = (EditText) findViewById(R.id.ConfirmPassword);

        Button signUp = (Button) findViewById(R.id.signout_button);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Get text from fields
                String firstNameInput = firstName.getText().toString();
                String lastNameInput = lastName.getText().toString();
                String ageInput = age.getText().toString();
                String emailInput = email.getText().toString();
                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();
                String passwordConfirmInput = passwordConfirm.getText().toString();

                //Error handling
                TextInputLayout firstNameError = findViewById(R.id.first_name_input_error);
                firstNameError.setError(null);
                TextInputLayout lastNameError = findViewById(R.id.last_name_input_error);
                lastNameError.setError(null);
                TextInputLayout ageInputError = findViewById(R.id.age_input_error);
                ageInputError.setError(null);
                TextInputLayout usernameInputError = findViewById(R.id.username_input_error);
                usernameInputError.setError(null);
                TextInputLayout emailInputError = findViewById(R.id.email_input_error);
                emailInputError.setError(null);
                TextInputLayout passwordInputError = findViewById(R.id.password_input_error);
                passwordInputError.setError(null);
                TextInputLayout passwordConfirmInputError = findViewById(R.id.password_confirm_input_error);
                passwordConfirmInputError.setError(null);

                int fieldErrors = 0;

                if (firstNameInput.isEmpty()) {
                    fieldErrors++;
                    firstNameError.setError("This is a required field.");
                }
                if (lastNameInput.isEmpty()) {
                    fieldErrors++;
                    lastNameError.setError("This is a required field.");
                }
                if (ageInput.isEmpty()) {
                    fieldErrors++;
                    ageInputError.setError("This is a required field.");
                }
                else if(Integer.parseInt(ageInput) > 99 || Integer.parseInt(ageInput) < 1) {
                    fieldErrors++;
                    ageInputError.setError("Must be 0 to 99");
                    age.getText().clear();
                }
                if (emailInput.isEmpty()) {
                    fieldErrors++;
                    emailInputError.setError("This is a required field.");
                }
                else if(!emailInput.contains("@")){
                    fieldErrors++;
                    emailInputError.setError("Invalid email.");
                    email.getText().clear();
                }
                if (usernameInput.isEmpty()) {
                    fieldErrors++;
                    usernameInputError.setError("This is a required field.");
                }
                else if(!auth.isUsernameAvaliable(usernameInput)){
                    fieldErrors++;
                    usernameInputError.setError("This username is not available.");
                }
                if (passwordInput.isEmpty()) {
                    fieldErrors++;
                    passwordInputError.setError("This is a required field.");
                }
                if (passwordConfirmInput.isEmpty()) {
                    fieldErrors++;
                    passwordConfirmInputError.setError("This is a required field.");
                }
                if(!passwordInput.equals(passwordConfirmInput)) {
                    fieldErrors++;
                    passwordInputError.setError("Password mismatch.");
                    password.getText().clear();
                    passwordConfirm.getText().clear();
                }
                if(fieldErrors == 0) {
                    Toast.makeText(RegisterActivity.this, "Account has been created.",
                            Toast.LENGTH_LONG).show();
                    auth.registerUser(usernameInput, passwordInput);
                    auth.userData(usernameInput, firstNameInput, lastNameInput, ageInput, emailInput);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });
    }
}
