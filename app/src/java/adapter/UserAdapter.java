package adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shahir.personalattendence.Courses;
import com.example.shahir.personalattendence.R;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import model.Person;

/**
 * Created by sarour on 2/16/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.PersonView>{
    protected Activity context;
    private List<Person> list = new ArrayList<Person>();
    private DBHelper db;

    public UserAdapter(Activity context,List<Person> list){
        this.context =context;
        this.list =list;
        db= new DBHelper(context);
    }

    @Override
    public PersonView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one,parent,false);
        return new PersonView(view);
    }

    @Override
    public void onBindViewHolder(PersonView holder, final int position) {

        final Person holdObject = list.get(position);

        holder.firName.setText("Course Title  : "+holdObject.getFname());
        holder.lasName.setText("Course Code : "+holdObject.getLname());
        holder.mobile.setText("Credit Hour    : "+holdObject.getMobile());
        holder.country.setText("Level&Term    : "+holdObject.getCountry());

        holder.mRodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context,"OnClicked at pos "+position,Toast.LENGTH_SHORT).show();
                createAndShowAlertDialog(holdObject);


            }
        });

        holder.mRodView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(context,"OnClicked at pos "+position,Toast.LENGTH_SHORT).show();
                createAndShowAlertDialog(holdObject);
                return false;

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PersonView extends RecyclerView.ViewHolder {
        protected TextView firName,lasName,mobile,country;
        private View mRodView;
        public PersonView(View itemView) {
            super(itemView);

            firName = (TextView) itemView.findViewById(R.id.f);
            lasName = (TextView) itemView.findViewById(R.id.l);
            mobile = (TextView) itemView.findViewById(R.id.m);
            country = (TextView) itemView.findViewById(R.id.c);
            mRodView =itemView;
        }
    }

    public void createAndShowAlertDialog(final Person pp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you want delete this item");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!db.removePerson(pp)){

                    Intent in = new Intent(context,Courses.class);
                    context.startActivity(in);
                    context.finish();

                }else {
                    Toast.makeText(context,"Delete not success",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //No Button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
