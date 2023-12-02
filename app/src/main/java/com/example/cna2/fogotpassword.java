package com.example.cna2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class fogotpassword extends AppCompatActivity {
    ImageView reset;
    EditText txtemail;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogotpassword);
        reset = findViewById(R.id.resetpassword);
        txtemail = findViewById(R.id.emailid);
        auth = FirebaseAuth.getInstance();



        reset.setOnClickListener(new View.OnClickListener() {
            //            String emailAddress = String.valueOf(email.getText()).trim();

            String emailAddress = String.valueOf(txtemail.getText()).trim();


            @Override
            public void onClick(View v) {
//                textiew.setText(txtemail.getText());

                if (TextUtils.isEmpty(txtemail.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(txtemail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        showNotification();
                                        Toast.makeText(getApplicationContext(), "Email send", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), login.class));
                                        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.emailapp");

                                        if (intent != null) {
                                            startActivity(intent);
                                        } else {

                                        }


                                    }
                                }
                            });
                }


            }
        });
    }

    private void showNotification() {
        // Create an explicit intent to launch an activity when the notification is clicked
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Create the notification
        int notificationId = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.cna)
                .setContentTitle("Password Reset")
                .setContentText("Password Reset Email send to the Your email please check that Thank You!!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }

}