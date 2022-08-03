package com.example.easycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class SignInActivity extends AppCompatActivity {

    CircularProgressButton SignIn;
    EditText email,pass;
    private FirebaseAuth mAuth;
    Drawable d;
    TextView signUp,forget;
    TextInputLayout email_ADDress;
    String emailAdd,mail,password;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SignIn =findViewById(R.id.signIn);
        email=findViewById(R.id.editTextTextEmail);
        pass=findViewById(R.id.editTextPass);
        signUp=findViewById(R.id.txt_signUp);
        forget=findViewById(R.id.txt_forget);
        d = getResources().getDrawable(R.drawable.ic_baseline_done_24);
        mAuth = FirebaseAuth.getInstance();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder= new AlertDialog.Builder(SignInActivity.this);
                View view= LayoutInflater.from(SignInActivity.this).inflate(R.layout.reset_password_layout,null,false);
                final CircularProgressButton reset=view.findViewById(R.id.btn_get_pass);
                email_ADDress=view.findViewById(R.id.edit_email_forget);
                alertBuilder.setView(view);
                final AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();

                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reset.startAnimation();

                        emailAdd =email_ADDress.getEditText().getText().toString().trim();
                        if(!isVaildEmailForget()){
                            reset.revertAnimation();
                            return;
                        }else {
                            mAuth.sendPasswordResetEmail(emailAdd)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                reset.doneLoadingAnimation(Color.parseColor("#F44336"),drawableToBitmap(d));
                                                Toast.makeText(getApplicationContext(),"Check your email for reset",Toast.LENGTH_LONG).show();
                                                alertDialog.dismiss();
                                            }
                                            else {
                                                reset.revertAnimation();
                                                Toast.makeText(getApplicationContext(),"There is error...!",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }

                    }
                });
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(n);
                finish();
            }
        });


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn.startAnimation();
                final String mail = email.getText().toString().trim();
                final String password = pass.getText().toString().trim();
                if(mail.isEmpty()){
                    email.setError("Email is needed");
                    email.requestFocus();
                    SignIn.revertAnimation();
                    return;

                }

                if(password.isEmpty()){
                    pass.setError("password is needed");
                    pass.requestFocus();
                    SignIn.revertAnimation();
                    return;
                }

                if(password.length()<6){
                    pass.setError("password should be at least 6 char long");
                    pass.requestFocus();
                    SignIn.revertAnimation();
                    return;
                }

                userLogin(mail,password);
            }
        });



    }

    private void userLogin(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            SignIn.doneLoadingAnimation(Color.parseColor("#0F2DCA"),drawableToBitmap(d));
                            startMainActivity();
                        }else{
                            SignIn.revertAnimation();
                            Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    boolean isVaildEmailForget(){
        if(emailAdd.isEmpty()){
            email_ADDress.getEditText().setError("Email can’t be empty..!");
            email_ADDress.getEditText().requestFocus();
            return false;
        }
        if(emailAdd.length()>50){
            email_ADDress.getEditText().setError("Email is too long..!");
            return false;
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            startMainActivity();
        }

    }


    private void startMainActivity(){
        Intent n = new Intent(SignInActivity.this,MainActivity.class);
        n.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(n);
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}