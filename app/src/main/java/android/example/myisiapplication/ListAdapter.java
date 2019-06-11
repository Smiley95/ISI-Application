package android.example.myisiapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter {
    private ArrayList<ServiceItem> list;
    private String access_token,refresh_token;
    private Context context;
    public ListAdapter(ArrayList<ServiceItem> list,String access_token,String refresh_token,Context context){
        this.list=list;
        this.access_token=access_token;
        this.refresh_token= refresh_token;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.isi_service_item,viewGroup,false);
        return new ViewHolder(layout);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final String prod_uid= list.get(i).pro_uid;
        final String tas_uid= list.get(i).tas_uid;
        ((ViewHolder)viewHolder).bindView(i);
        ((ViewHolder)viewHolder).mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("access_token",access_token);
                bundle.putString("refresh_token",refresh_token);
                bundle.putString("prod_uid",prod_uid);
                bundle.putString("tas_uid",tas_uid);
                Intent intent = new Intent(context,FormulaireActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
            TextView serviceTitle;
            ImageView serviceImg;
            View mview;
            RelativeLayout relativeLayout;
        public ViewHolder(View serviceItem){
            super(serviceItem);
            mview = serviceItem;
            serviceTitle=(TextView)serviceItem.findViewById(R.id.itemView);
            serviceImg = (ImageView) serviceItem.findViewById(R.id.serviceImage);

        }
        public void bindView(int position){
            serviceTitle.setText(list.get(position).title);
            serviceImg.setImageResource(R.drawable.administration);
        }

    }
}
