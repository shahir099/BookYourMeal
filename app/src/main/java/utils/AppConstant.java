package utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by me on 2/15/2018.
 */

public class AppConstant {

    public static void showAlertMessage(Context context, String message){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }

}
