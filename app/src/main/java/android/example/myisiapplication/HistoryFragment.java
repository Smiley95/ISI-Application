package android.example.myisiapplication;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    String access_token;
    String refresh_token;
    JSONArray JA=null;
    RecyclerView historyList;
    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_history, container, false);
        JSONArray  services;
        access_token= this.getArguments().getString("access_token");
        refresh_token = this.getArguments().getString("refresh_token");
        HistoryAccess casesAccess =new HistoryAccess();
        casesAccess.execute();
        historyList = (RecyclerView) view.findViewById(R.id.recycler_history);

        RecyclerView.LayoutManager serviceManager = new LinearLayoutManager(getActivity());
        historyList.setLayoutManager(serviceManager);

        return view;
    }

    public class HistoryAccess extends AsyncTask<String, Void, String> {
        String token;


        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            try {
                URL urlHistory= new URL("http://process.isiforge.tn/api/1.0/isi/cases/participated");

                HttpURLConnection httpURLConnection=(HttpURLConnection) urlHistory.openConnection();
                //httpURLConnection.setRequestMethod("GET");

                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("authorization","Bearer "+ access_token);
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(inputStreamReader);
                String inputStreamData;
                String assembledOutput="";

                while ((inputStreamData = reader.readLine()) != null) {
                    assembledOutput = assembledOutput + inputStreamData;
                }
                JA=new JSONArray(assembledOutput);

                return assembledOutput;

            } catch (Exception e) {
                return "disconnected";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            runAnimationHistory(historyList,JA);

        }
    }
    private void runAnimationHistory(RecyclerView view,JSONArray ja){
        Context context= view.getContext();
        LayoutAnimationController controller = null;
        controller= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_from_right);
        HistoryListAdapter listAdapter = new HistoryListAdapter(ja,getContext());
        view.setAdapter(listAdapter);
        view.setLayoutAnimation(controller);
        view.getAdapter().notifyDataSetChanged();
        view.scheduleLayoutAnimation();
    }

}
