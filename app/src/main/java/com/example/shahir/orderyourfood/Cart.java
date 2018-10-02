package com.example.shahir.orderyourfood;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shahir.orderyourfood.Common.Common;
import com.example.shahir.orderyourfood.Database.Database;
import com.example.shahir.orderyourfood.Model.Order;
import com.example.shahir.orderyourfood.Model.Request;
import com.example.shahir.orderyourfood.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;


    ///ctrl+o

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /// Font we will add before setContentView

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_cart);


        ///Firebase

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        ///Init

        recyclerView=(RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice=(TextView) findViewById(R.id.total);
        btnPlace=(FButton) findViewById(R.id.btnPlaceOrder);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog();

            }
        });

        loadListFood();

    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more Step !");
        alertDialog.setMessage("Enter Your Address: ");

        final EditText edtAddress=new EditText(Cart.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress); /// edt txt to alert Box
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );

                ///Submit to Firebase
                /// We will use System.CurrentMilli to key

                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                /// Delete cart

                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Thank You , Order Placed",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadListFood() {
        cart=new Database(this).getCarts();
        adapter=new CartAdapter(cart,this,Cart.this);
        recyclerView.setAdapter(adapter);

        //calculate total
        int total=0;
        for(Order order:cart)
        {
            //total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            total+=(Integer.parseInt(order.getQuantity()));

        }
        Locale locale=new Locale("en","BD");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);

        //txtTotalPrice.setText(fmt.format(total));
    }
}
