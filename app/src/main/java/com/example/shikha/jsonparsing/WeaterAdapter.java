package com.example.shikha.jsonparsing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by shikha on 3/3/16.
 */
public class WeaterAdapter extends ArrayAdapter<weather> {


    int resource;
    Context context;
    List<weather> objects;
    LayoutInflater li;
    public WeaterAdapter(Context context, int resource, List<weather> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
        this.resource=resource;
        li=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position,View convertView,ViewGroup parent)
    {
            ViewHolder viewHolder;
        if(convertView==null)
        {

            convertView=li.inflate(resource,null);
            viewHolder=new ViewHolder();
            viewHolder.micon=(ImageView)convertView.findViewById(R.id.imgv);
            viewHolder.main=(TextView)convertView.findViewById(R.id.t1);
            viewHolder.mdescription=(TextView)convertView.findViewById(R.id.t2);
            viewHolder.mid=(TextView)convertView.findViewById(R.id.t3);
            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        new ImageASynctask(viewHolder.micon).execute(objects.get(position).getIcon());
        viewHolder.main.setText(objects.get(position).getMain());
        viewHolder.main.setText(objects.get(position).getDescription());
        viewHolder.main.setText(objects.get(position).getId());


        return convertView;

    }

    static class ViewHolder{

       public ImageView micon;
        //public TextView mlat;
        //public TextView mlon;
        public TextView mdescription;
        public TextView main;
        //public TextView mpressure;
        //public TextView mhumidity;
        public TextView mid;



    }
    private class ImageASynctask extends AsyncTask<String,Void,Bitmap>
    {
        ImageView bview;
        public ImageASynctask(ImageView bview)
        {
            this.bview=bview;

        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisp= urls[0];
            Bitmap icons=null;
            try{
                InputStream in=new  java.net.URL(urldisp).openStream();
                icons= BitmapFactory.decodeStream(in);
            }
            catch(Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return icons;
        }
        protected void onPostExecute(Bitmap result)
        {
            bview.setImageBitmap(result);
        }

    }
}
