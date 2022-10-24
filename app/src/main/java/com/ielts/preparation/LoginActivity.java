package com.ielts.preparation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ielts.preparation.databinding.ActivityLoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameTIL, passwordTIL;
    private TextInputEditText passwordET, usernameEt;
    private String usernameStr, passwordStr;
    private TextView signInTv;

    private Dialog dialog;


    // digit + lowercase char + uppercase char + punctuation + symbol
    protected static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    /**
     *Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
     * Username allowed of the dot (.), underscore (_), and hyphen (-).
     * The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
     * The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
     * The number of characters must be between 5 to 20.
     */
    /**
     * ^[a-zA-Z0-9]      # start with an alphanumeric character
     * (                 # start of (group 1)
     * [._-](?![._-])  # follow by a dot, hyphen, or underscore, negative lookahead to
     * # ensures dot, hyphen, and underscore does not appear consecutively
     * |               # or
     * [a-zA-Z0-9]     # an alphanumeric character
     * )                 # end of (group 1)
     * {3,18}            # ensures the length of (group 1) between 3 and 18
     * [a-zA-Z0-9]$      # end with an alphanumeric character
     * <p>
     * # {3,18} plus the first and last alphanumeric characters,
     * # total length became {5,20}
     */
    protected static final String USERNAME_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials");


    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        initView();
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        initFunctionality();
        setupListeners();
    }

    private void setupListeners() {
        usernameEt.addTextChangedListener(new TextFieldValidation(usernameEt));
        passwordET.addTextChangedListener(new TextFieldValidation(passwordET));
        signInTv.setOnClickListener(this::SignIn);
    }

    private void SignIn(View view) {
        if (isValidate()) {
            usernameStr = String.valueOf(usernameEt.getText());
            passwordStr = String.valueOf(passwordET.getText());
            dialog.show();
            loginUser();
        }
    }

    private void loginUser() {
        ref.child("student").child(usernameStr)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String pwd = snapshot.child("password").getValue(String.class);
                            String gmail = snapshot.child("gmail").getValue(String.class);
                            if (pwd.equals(passwordStr)) {
                                auth.signInWithEmailAndPassword(gmail, pwd)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Get Instance of the current user
                                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                                    if (firebaseUser.isEmailVerified()) {
                                                        Toast.makeText(getApplicationContext(), "You are logged in now", Toast.LENGTH_SHORT).show();
                                                        //open dashboard
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        intent.putExtra("username",usernameStr);
                                                        startActivity(intent);
                                                        overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
                                                        finish();
                                                    } else {
                                                        firebaseUser.sendEmailVerification();
                                                        auth.signOut(); // sign out user
                                                        showAlertDialog();
                                                    }
                                                } else {
                                                    try {
                                                        throw task.getException();
                                                    } catch (FirebaseAuthInvalidUserException e) {
                                                        usernameTIL.setError("User does not exists or is no longer valid. Please register again");
                                                        usernameEt.requestFocus();
                                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                                        usernameTIL.setError("Invalid credentials. Kindly, check and re-enter.");
                                                        usernameEt.requestFocus();
                                                    } catch (Exception e) {
                                                        Log.e("MyTag", "onComplete: " + e.getMessage());
                                                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid credentials. Kindly, check and re-enter.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "User does not exists or is no longer valid. Please register again.", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showAlertDialog() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification.");

        // open email apps if user clicks/taps continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //To email app in new window and not within our app
                startActivity(intent);
            }
        });

        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        //Show the AlertDialog
        alertDialog.show();
    }

    private void initView() {
        usernameTIL = findViewById(R.id.usernameTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
        usernameEt = findViewById(R.id.usernameEt);
        passwordET = findViewById(R.id.passwordET);
        signInTv = findViewById(R.id.signInTv);
    }


    private void initFunctionality() {
        usernameTIL.setBoxStrokeColorStateList(setStrokeColorState());
        passwordTIL.setBoxStrokeColorStateList(setStrokeColorState());
    }

    private ColorStateList setStrokeColorState() {
        //Color from hex string
        int color = Color.parseColor("#1A6EFC");

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_focused}, // focused
                new int[]{android.R.attr.state_hovered}, // hovered
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{}  //
        };

        int[] colors = new int[]{
                color,
                color,
                color,
                color
        };

        ColorStateList mColorList = new ColorStateList(states, colors);
        return mColorList;
    }

    private boolean isValidate() {
        return (validateUsername() | validatePassword());
    }


    public static boolean isValidPattern(final String input, final String CHECK_PATTERN) {
        Pattern pattern = Pattern.compile(CHECK_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private boolean validateUsername() {
        if (String.valueOf(usernameEt.getText()).isEmpty()) {
            usernameTIL.setError("Required Field!");
            usernameEt.requestFocus();
            return false;
        } else if (String.valueOf(usernameEt.getText()).length() < 5) {
            usernameTIL.setError("username must more then 5");
            usernameEt.requestFocus();
            return false;
        } else if (!isValidPattern(String.valueOf(usernameEt.getText()), USERNAME_PATTERN)) {
            usernameTIL.setError("username is not valid");
            usernameEt.requestFocus();
            return false;
        } else {
            usernameTIL.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {

        if (String.valueOf(passwordET.getText()).isEmpty()) {
            passwordTIL.setError("Required Field!");
            passwordET.requestFocus();
            return false;
        } else if (String.valueOf(passwordET.getText()).length() < 8) {
            passwordTIL.setError("Password must more then 8");
            passwordET.requestFocus();
            return false;
        } else if (!isValidPattern(String.valueOf(passwordET.getText()), PASSWORD_PATTERN)) {
            passwordTIL.setError("Please enter strong password");
            passwordET.requestFocus();
            return false;
        } else {
            passwordTIL.setErrorEnabled(false);
        }

        return true;
    }


    /**
     * applying text watcher on each text field
     */
    class TextFieldValidation implements TextWatcher {

        /*       TextInputEditText v;

               public TextFieldValidation(TextInputEditText view) {
                   this.v = view;
               }*/
        View v;

        public TextFieldValidation(View view) {
            this.v = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // checking ids of each text field and applying functions accordingly.
            switch (v.getId()) {
                case R.id.usernameEt:
                    validateUsername();
                    break;
                case R.id.passwordET:
                    validatePassword();
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}