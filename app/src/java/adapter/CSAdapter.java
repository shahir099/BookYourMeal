package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CSAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context context;
    private ArrayList<String> asr;

    public CSAdapter(Context context, ArrayList<String> asr) {
        this.asr=asr;
        this.context = context;
    }

    public int getCount()
    {
        return asr.size();
    }

    public Object getItem(int i)
    {
        return asr.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setGravity(Gravity.CENTER);
        txt.setText(asr.get(position));
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(20);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0,android.R.drawable.arrow_down_float, 0);
        txt.setText(asr.get(i));
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

}
