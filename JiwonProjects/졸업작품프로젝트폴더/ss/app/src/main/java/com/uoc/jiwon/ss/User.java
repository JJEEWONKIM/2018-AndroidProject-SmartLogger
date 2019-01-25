package com.uoc.jiwon.ss;

class User {

    public String userId;
    public String userPassword;
    public String userName;
    public String userAccountLevel;
    public String userPhoneNum;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId, String userPassword, String userName, String userAccountLevel, String userPhoneNum ) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userAccountLevel = userAccountLevel;
        this.userPhoneNum = userPhoneNum;
    }

}
