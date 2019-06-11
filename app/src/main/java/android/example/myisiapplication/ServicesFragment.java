package android.example.myisiapplication;


import android.content.Context;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.view.animation.AnimationUtils;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {

    String access_token;
    String refresh_token;
    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        JSONArray  services;
        access_token = this.getArguments().getString("access_token");
        refresh_token = this.getArguments().getString("refresh_token");
        try {
            services= new JSONArray(this.getArguments().getString("services"));
            ArrayList<ServiceItem> titels = new ArrayList<ServiceItem>();
            for(int i=0;i<services.length();i++){
                JSONObject item =services.getJSONObject(i);
                titels.add(new ServiceItem(item.getString("pro_title"),item.getString("pro_uid"),item.getString("tas_uid")));
            }

            RecyclerView servicesList = (RecyclerView) view.findViewById(R.id.recycler_Service);
            RecyclerView.LayoutManager serviceManager = new LinearLayoutManager(getActivity());
            servicesList.setLayoutManager(serviceManager);
            runAnimation(servicesList,titels);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;

    }
    private void runAnimation(RecyclerView view,ArrayList titels){
        Context context= view.getContext();
        LayoutAnimationController controller = null;
        controller= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_from_right);
        ListAdapter listAdapter = new ListAdapter(titels,access_token,refresh_token,context);
        view.setAdapter(listAdapter);
        view.setLayoutAnimation(controller);
        view.getAdapter().notifyDataSetChanged();
        view.scheduleLayoutAnimation();
    }


}
