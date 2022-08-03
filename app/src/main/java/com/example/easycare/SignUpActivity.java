package com.example.easycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaCodec;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.regex.Pattern;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import dagger.Lazy;

public class SignUpActivity extends AppCompatActivity {


    public static final String CHANEL_ID = "Chanel_id";
    public static final String CHANEL_NAME = "Chanel_name";
    public static final String CHANEL_DESC = "Chanel_description";

    CircularProgressButton SignUp;
    EditText email,pass,confirm;
    private FirebaseAuth mAuth;
    Drawable d;
    TextView signIn;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
         d = getResources().getDrawable(R.drawable.ic_baseline_done_24);
         signIn=findViewById(R.id.txt_sign_In);


        mAuth = FirebaseAuth.getInstance();

        SignUp = findViewById(R.id.buttonSignUp);
        email = findViewById(R.id.editTextEmail);
        pass = findViewById(R.id.editTextPassword);
        confirm=findViewById(R.id.editTextConfirm);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(n);
                finish();
            }
        });


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.startAnimation();
                createUser();
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANEL_ID,CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }




    }


    private void createUser() {

        final String mail = email.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        final String confirmPass=confirm.getText().toString().trim();
        if(mail.isEmpty()){
            email.setError("Email is needed");
            email.requestFocus();
            SignUp.revertAnimation();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Please enter a valid email.");
            email.requestFocus();
            SignUp.revertAnimation();
            return;
        }

        if(password.isEmpty()){
            pass.setError("password is needed");
            pass.requestFocus();
            SignUp.revertAnimation();
            return;
        }

        if(password.length()<6){
            pass.setError("password should be at least 6 char long");
            pass.requestFocus();
            SignUp.revertAnimation();
            return;
        }
        if(!password.equals(confirmPass))
        {
            pass.setError("password and confirm pass not match");
            pass.requestFocus();
            SignUp.revertAnimation();
            return;
        }


        mAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            startMainActivity();
                            SignUp.doneLoadingAnimation(Color.parseColor("#0F2DCA"),drawableToBitmap(d));
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                userLogin(mail,password);
                            }else{
                                SignUp.revertAnimation();
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });

    }

    private void userLogin(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            SignUp.doneLoadingAnimation(Color.parseColor("#0F2DCA"),drawableToBitmap(d));
                            startMainActivity();
                        }else{
                            SignUp.revertAnimation();
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            startMainActivity();
        }

    }


    private void startMainActivity(){
        Intent n = new Intent(SignUpActivity.this,MainActivity.class);
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