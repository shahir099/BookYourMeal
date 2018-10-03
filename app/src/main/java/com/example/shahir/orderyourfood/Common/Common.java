package com.example.shahir.orderyourfood.Common;

import com.example.shahir.orderyourfood.Model.User;

/**
 * Created by ShahiR on 2/24/2018.
 */

public class Common {

    public static User currentUser;

    public  static  final  String UPDATE = "Update";
    public  static  final  String DELETE = "Delete";

    public static String convertCodeToStatus(String status) {

        if(status.equals("0")) return "Order Placed";

        else if(status.equals("1")) return "Looking For Your Table";

        else return "Table Booked";

    }
}
