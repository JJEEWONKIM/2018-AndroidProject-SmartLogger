/*
 * Copyright (C) 2017 Samsung Electronics Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uoc.jiwon.ss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {
    static final String TAG = "LoginActivity";


    private Query zonesQuery;
    private  ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;
    private EditText loginId;
    private EditText loginPassword;
    private View layout;
    private TextView pwChangeBtn;
    private String newPW;
    private boolean confirm;
    private boolean isGrantStorage;
    private String userName;
    private String userAccountLevel;
    private String userPhoneNum;
    private String name;
    private Button button;
    private String Id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "::onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText loginId = (EditText)findViewById(R.id.loginId);
        final EditText loginPassword = (EditText)findViewById(R.id.loginPassword);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        button = (Button)findViewById(R.id.btn);
        pwChangeBtn = (TextView)findViewById(R.id.pwChangeBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference("DEVICEID").child("2FSQEO45");

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }

        });

        // LoginButton
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (loginId.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "ID를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (loginPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Password를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

               zonesQuery = databaseReference.orderByChild("userId").equalTo(loginId.getText().toString());
                valueEventListener = new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("real", "loginActivitydataChanged");
                        for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) { //password까지 맞으면 로그인 완료
                            Toast.makeText(getApplicationContext(), "일단 들어옴", Toast.LENGTH_LONG).show();
                            Log.d("real", "전pwcomein");
                            if (loginPassword.getText().toString().equals(zoneSnapshot.child("userPassword").getValue(String.class))) {
                                Toast.makeText(getApplicationContext(), "로그인 완료", Toast.LENGTH_LONG).show();
                                Log.d("real", "pwcomein");

                                name = zoneSnapshot.child("userName").getValue(String.class);
                                String id = zoneSnapshot.child("userId").getValue(String.class);
                                String pw = zoneSnapshot.child("userPassword").getValue(String.class);
                                String phoneNum = zoneSnapshot.child("userPhoneNum").getValue(String.class);

                                SharedPreferences namePreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = namePreferences.edit();
                                editor.putString("name", name); //name이라는 key값으로 로그인한 이름 데이터를 저장한다.
                                editor.putString("id", id); //name이라는 key값으로 로그인한 이름 데이터를 저장한다.
                                editor.putString("pw", pw); //name이라는 key값으로 로그인한 이름 데이터를 저장한다.
                                editor.putString("phoneNum", phoneNum); //name이라는 key값으로 로그인한 이름 데이터를 저장한다.
                                editor.commit(); //완료한다.

                                startMessageActivity();

                                finish();
                                return;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "onCancelled", databaseError.toException());
                    }
                };
                zonesQuery.addValueEventListener(valueEventListener);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        zonesQuery.removeEventListener(valueEventListener);

    }

    private void writeNewUser(String userId, String userPassword, String userName, String userAccountLevel, String userPhoneNum) {
        User user = new User(userId, userPassword, userName, userAccountLevel, userPhoneNum);
        databaseReference.child(userId).setValue(user);
    }

    private void startMessageActivity() {
        Log.d("real","startMessageActivity");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("name",name);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
