package android.example.myisiapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter {
    ArrayList<ServiceItem> titels = new ArrayList<ServiceItem>();
    Context context;
    public HistoryListAdapter(JSONArray list, Context context){
        this.context=context;
        for(int i=0;i<list.length();i++){
            JSONObject item = null;
            try {
                item = list.getJSONObject(i);
                titels.add(new ServiceItem(item.getString("app_pro_title")+" ("+item.getString("del_init_date")+")"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.isi_service_item,viewGroup,false);

        return new ListViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((ListViewHolder )viewHolder).bindView(i);

    }

    @Override
    public int getItemCount() {
        return titels.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView serviceTitle;
        ImageView serviceImg;
        public ListViewHolder(View serviceItem){
            super(serviceItem);
            serviceTitle=(TextView)serviceItem.findViewById(R.id.itemView);
            serviceImg = (ImageView) serviceItem.findViewById(R.id.serviceImage);
            serviceItem.setOnClickListener(this);
        }
        public void bindView(int position){
            serviceTitle.setText(titels.get(position).title);
            serviceImg.setImageResource(R.drawable.telechargement);
        }
        public void onClick(View view){

        }

    }
}
