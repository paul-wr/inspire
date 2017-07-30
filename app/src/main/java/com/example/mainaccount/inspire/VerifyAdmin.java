package com.example.mainaccount.inspire;

/**
 *  Classname: VerifyAdmin.java
 *  Version 1
 *  Date: 22 Jul 2017
 *  @author Paul Wrenn, x15020029
 */

public class VerifyAdmin {
    private final String ADMIN_ID = "imsc7scSfgVDjV1w4ufvlC37C6h2";

    public boolean isAdmin(String userID){
        boolean isAdmin = false;
        if(userID.equals(ADMIN_ID)){
            isAdmin = true;
        }
        return isAdmin;
    }
}
