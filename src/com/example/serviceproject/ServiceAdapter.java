package com.example.serviceproject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServiceAdapter extends BaseAdapter implements OnClickListener {

    private Activity activity;
    private ArrayList<Service> services;
    private static LayoutInflater inflater = null;
    public Resources res;
    Service temp = null;
    int i = 0;

    public ServiceAdapter(Activity activity, ArrayList<Service> services) {
        this.activity = activity;
        this.services = services;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public class ViewHolder {
        public TextView text;
        public TextView text1;
    }


    

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*@Override
    public void onClick(View v) {
        Log.e("Button Clicked", "Click");

    }*/

    
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ViewHolder holder;

        if ( convertView == null ) {

            holder = new ViewHolder();
            //LayoutInflater vi;
            v = inflater.inflate(R.layout.rows, null);
            holder.text = (TextView) v.findViewById(R.id.name);
            holder.text1 = (TextView) v.findViewById(R.id.description);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();

        }

        if( services.size() <= 0 ) {
            holder.text.setText("No data");
        } else {

             
            temp = null;
            temp = ( Service ) services.get( position );

            /************  Set Model values in Holder elements ***********/

             holder.text.setText( temp.getName() );
             holder.text1.setText( temp.getDesciption());
 
        }

        return v;

    }
    
    
    
    
    
    private class OnItemClickListener implements OnClickListener
    {
         private int mPosition;
        OnItemClickListener(int position){
             mPosition = position;
        }

        public void onClick(View arg0) {


          MainActivity sct = (MainActivity) activity;

         /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            sct.onItemClick(mPosition);
        }

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}

    }


	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}

