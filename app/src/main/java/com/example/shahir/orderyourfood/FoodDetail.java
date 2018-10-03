package com.example.shahir.orderyourfood;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shahir.orderyourfood.Common.Common;
import com.example.shahir.orderyourfood.Database.Database;
import com.example.shahir.orderyourfood.Model.Food;
import com.example.shahir.orderyourfood.Model.Order;
import com.example.shahir.orderyourfood.Model.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Queue;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FoodDetail extends AppCompatActivity implements RatingDialogListener {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart,btnRating;
    ElegantNumberButton numberButton;
    RatingBar ratingBar;



    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;

    DatabaseReference ratingTbl;

    Food currentFood;
    Button bookTable;

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

        setContentView(R.layout.activity_food_detail);



        //dateAbsent=findViewById(R.id.dateAbsent);
        /// FireBase

        database= FirebaseDatabase.getInstance();
        foods = database.getReference("Restaurants");
        ratingTbl = database.getReference("Rating");


        ///Init view
        bookTable = (Button) findViewById(R.id.bookTable);

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);

        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);
        btnRating=(FloatingActionButton) findViewById(R.id.btn_rating);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRatingDialog();
            }
        });

        bookTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetail.this,TestFoodDetail.class);
                intent.putExtra("MenuId",foodId);
                startActivity(intent);
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount(),
                        currentFood.getImage()
                ));
                Toast.makeText(FoodDetail.this,"Added to Cart",Toast.LENGTH_SHORT).show();
                //Intent calender=new Intent(FoodDetail.this,CalendarActivity.class);
                //startActivity(calender);
            }
        });

        food_description=(TextView) findViewById(R.id.food_description);
        food_name=(TextView) findViewById(R.id.food_name);
        food_price=(TextView) findViewById(R.id.food_price);
        food_image=(ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        /// Get Food id From Intent

        if(getIntent() != null){
            foodId=getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty()){
            getDetailFood(foodId);
            getRatingFood(foodId);
        }

    }

    private void getRatingFood(String foodId) {
        com.google.firebase.database.Query foodRating=ratingTbl.orderByChild("foodId").equalTo(foodId);
        foodRating.addValueEventListener(new ValueEventListener() {
            int count=0,sum=0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Rating item=postSnapshot.getValue(Rating.class);
                    sum+=Integer.parseInt(item.getRateValue());
                    count++;
                }
                if(count!=0){
                    float average=sum/count;
                    ratingBar.setRating(average);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad","Not Good","Quite Ok","Very Good","Excellent"))
                .setDefaultRating(5)
                .setTitle("Rate This Restaurant")
                .setDescription("Please Give Your Feedback To Make Our Service Better")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please Write Your Comment here...")
                .setHintTextColor(R.color.colorPrimaryDarkSplash)
                .setCommentTextColor(android.R.color.black)
                .setCommentBackgroundColor(R.color.lightGray)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(FoodDetail.this)
                .show();
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood=dataSnapshot.getValue(Food.class);

                /// Setting Image

                Picasso.with(getBaseContext()).load(currentFood.getImage())
                        .into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());

                //food_price.setText(currentFood.getPrice());

                food_name.setText(currentFood.getName());

                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        ///Get the rating and upload it to firebase

        final Rating rating = new Rating(Common.currentUser.getPhone(),
                foodId,
                String.valueOf(value),
                comments
                );
        ratingTbl.child(Common.currentUser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Common.currentUser.getPhone()).exists())
                {
                    /// Remove old Value
                    ratingTbl.child(Common.currentUser.getPhone()).removeValue();
                    /// Update new Value
                    ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);

                     }
                else
                {
                    // Update new Value
                    ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);
                    Toast.makeText(FoodDetail.this,"Thanks For Your Collaboration !",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
