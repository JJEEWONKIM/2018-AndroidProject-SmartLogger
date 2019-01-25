package com.uoc.jiwon.ss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MypageActivity extends Activity {
    TextView nameView;
    TextView pwView;
    TextView userIdView;
    TextView phoneNumView;
    View layout;

    String name;
    String pw;
    String userId;
    String phoneNum;

    SharedPreferences namePreferences;
    private DatabaseReference databaseReference;

    ImageView returnBtn;
    ImageView pwChangeBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        nameView = (TextView)findViewById(R.id.name);
        pwView = (TextView)findViewById(R.id.pw);
        userIdView = (TextView)findViewById(R.id.id);
        phoneNumView = (TextView)findViewById(R.id.phoneNum);

        namePreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        name = namePreferences.getString("name", "null");
        pw = namePreferences.getString("pw", "null");
        userId = namePreferences.getString("id", "null");
        phoneNum = namePreferences.getString("phoneNum", "null");

        nameView.setText(name);
        pwView.setText(pw);
        userIdView.setText(userId);
        phoneNumView.setText(phoneNum);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        returnBtn = (ImageView)findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        pwChangeBtn = (ImageView)findViewById(R.id.pwChangeBtn);
        pwChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPWDialog();
            }
        });
    }

    private void setPWDialog(){
        final Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.pw_dialog,(ViewGroup) findViewById(R.id.layout_root));
        TextView currentPW = (TextView)layout.findViewById(R.id.currentPW);
        currentPW.setText("  "+pw);
        AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
        aDialog.setTitle("비밀번호 변경");
        aDialog.setView(layout);
        aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                EditText getNewPW = (EditText)layout.findViewById(R.id.newPW);
                EditText getNewPWCheck = (EditText)layout.findViewById(R.id.newPWCheck);
                if(getNewPW.getText().toString().equals(getNewPWCheck.getText().toString())){
                    SharedPreferences.Editor editor = namePreferences.edit();
                    pw = getNewPW.getText().toString();
                    editor.putString("pw",pw); //name이라는 key값으로 로그인한 이름 데이터를 저장한다.
                    writeNewUser(userId,pw,name,"teamUser",phoneNum);
                    pwView.setText(pw);
                }
                else{
                    Toast.makeText(getApplicationContext(),"새 비밀번호를 제대로 입력해주세요.",Toast.LENGTH_LONG);
                }

            }
        });
        aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog ad = aDialog.create();
        ad.show();
    }
    private void writeNewUser(String userId, String userPassword, String userName, String userAccountLevel, String userPhoneNum) {
        User user = new User(userId, userPassword, userName, userAccountLevel, userPhoneNum);
        databaseReference.child(userId).setValue(user);
    }
}
