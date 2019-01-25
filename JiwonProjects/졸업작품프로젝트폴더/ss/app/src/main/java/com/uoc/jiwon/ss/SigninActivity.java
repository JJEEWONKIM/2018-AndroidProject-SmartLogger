package com.uoc.jiwon.ss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class SigninActivity extends Activity /*implements GoogleApiClient.OnConnectionFailedListener*/{
    private DatabaseReference databaseReference;
    protected Button registerBtn;
    protected SignInButton googleAccountBtn;
    private EditText getReg_id;
    private EditText getReg_password;
    private EditText getReg_name;
    private EditText getReg_teamcord;
    private EditText getReg_phoneNum;
    private String accountLevel;



    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        databaseReference = FirebaseDatabase.getInstance().getReference("DEVICEID");
        getReg_id = (EditText)findViewById(R.id.reg_id);
        getReg_password = (EditText)findViewById(R.id.reg_password);
        getReg_name = (EditText)findViewById(R.id.reg_name);
        getReg_teamcord = (EditText)findViewById(R.id.reg_teamCord) ;
        getReg_phoneNum = (EditText)findViewById(R.id.reg_phone);


        // If  you touch login button...
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        loginScreen.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){ // From SigninAcitivity to LoginActivity....
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });



        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // checkcing 1. id 입력여부, 2. password 입력여부, 3. id 중복여부 4. 구글 계정 인증 여부 5. 팀코드 확인
                databaseReference.addValueEventListener(checkRegister);

            }
        });
    }

    private void writeNewUser(String userId, String userPassword, String userName, String userAccountLevel, String userPhoneNum) {
        User user = new User(userId, userPassword, userName, userAccountLevel, userPhoneNum);
        databaseReference.child("2FSQEO45").child(userId).setValue(user);
}

    private ValueEventListener checkRegister = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            if(getReg_id.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"ID를 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(getReg_password.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Password를 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(getReg_name.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Name을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            while (child.hasNext()) {//마찬가지로 중복 유무 확인
                if (getReg_id.getText().toString().equals(child.next().getKey())) {
                    dataSnapshot.child("userId").getValue(String.class);
                    Toast.makeText(getApplicationContext(), "이미 존재하는 아이디 입니다.", Toast.LENGTH_LONG).show();
                    databaseReference.removeEventListener(this);
                    return;
                }
            }
            if(getReg_teamcord.getText().toString().equals("01080057675")){
                accountLevel = "administrator";
                Toast.makeText(SigninActivity.this, String.valueOf(accountLevel), Toast.LENGTH_SHORT).show();
            }
            else if(getReg_teamcord.getText().toString().equals("koreatech10")){
                accountLevel = "teamUser";
                Toast.makeText(SigninActivity.this, String.valueOf(accountLevel), Toast.LENGTH_SHORT).show();
            }
            else if(getReg_teamcord.getText().toString().equals("amar1004zzang")){
                accountLevel = "developer";
                Toast.makeText(SigninActivity.this, String.valueOf(accountLevel), Toast.LENGTH_SHORT).show();
            }
            else if(getReg_teamcord.getText().toString().equals("minmin")){
                accountLevel = "driver";
                Toast.makeText(SigninActivity.this, String.valueOf(accountLevel), Toast.LENGTH_SHORT).show();
            }
            else if(getReg_teamcord.getText().toString().equals("laplap")){
                accountLevel = "lapCounter";
                Toast.makeText(SigninActivity.this, String.valueOf(accountLevel), Toast.LENGTH_SHORT).show();
            }
            else{
                accountLevel = "commonUser";
                Toast.makeText(SigninActivity.this, String.valueOf(accountLevel), Toast.LENGTH_SHORT).show();
            }

            writeNewUser(getReg_id.getText().toString(),getReg_password.getText().toString(),getReg_name.getText().toString(),accountLevel,getReg_phoneNum.getText().toString());
            Toast.makeText(SigninActivity.this,"회원 가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();



        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };



/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }
    */
/*
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) { //파이어베이스로 값 넘겨주기

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential) //이 사람에 대한 정보를 받아서 인증한것을 파이어베이스에 넘겨줌
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                        }else{
                            isGoogleAccountExisted = true;
                            Toast.makeText(SigninActivity.this,"구글 계정 인증이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    */
}
