package android.example.myisiapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormulaireActivity extends AppCompatActivity {
    String prod_uid,tas_uid,access_token,refresh_token;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);
        linearLayout = (LinearLayout) findViewById(R.id.forms);
        Bundle bundle = getIntent().getExtras();
        prod_uid = bundle.getString("prod_uid");
        tas_uid = bundle.getString("tas_uid");
        access_token = bundle.getString("access_token");
        refresh_token = bundle.getString("refresh_token");
        String url = "http://process.isiforge.tn/api/1.0/isi/project/" + prod_uid + "/activity/" + tas_uid + "/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IService service = retrofit.create(IService.class);


        Call<List<Dynaform>> callString = service.getStepUidObj("Bearer " + access_token);
        Log.i("return ",callString.toString());


        callString.enqueue(new Callback<List<Dynaform>>() {
            @Override
            public void onResponse(Call<List<Dynaform>> call, Response<List<Dynaform>> response) {


                if (response.isSuccessful()) {

                    List<Dynaform> Dyn;
                    Dyn = response.body();
                    for (Dynaform i : Dyn) {
                        Log.e("For", "IN boucle!!! + "+i.getStep_uid_obj());
                            String step_uid_obj = i.getStep_uid_obj();
                            getDynContent(prod_uid,step_uid_obj);


                    }

                } else {

                   Log.i("hjgjg","jfhdf");
                }

            }

            @Override
            public void onFailure(Call<List<Dynaform>> call, Throwable t) {
                Log.i("error","hello there");
            }
        });

    }

    public void getDynContent(String prod_uid,String stepUidObject){
        String url1 = "http://isiforge.tn/api/1.0/isi/project/"+prod_uid+"/dynaform/"+stepUidObject+"/";
        String tok = access_token;
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(url1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IService client = retrofit1.create(IService.class);

        Call<DYNContent> callContent = client.getContent("Bearer " + tok);

        callContent.enqueue(new Callback<DYNContent>() {
            @Override
            public void onResponse(Call<DYNContent> call, Response<DYNContent> response) {


                if (response.isSuccessful()) {
                    Log.d("ContentRaw", "response getContent Success!!!!!");
                    String content = response.body().getDyn_content();
                    Log.d("ContentRaw", content);

                    JSONObject js1 = null;
                    try {
                        js1 = (JSONObject)new JSONTokener(content.toString()).nextValue();
                        JSONArray jsAr = js1.getJSONArray("items");
                        for(int i=0;i<jsAr.length();i++)
                        {
                            JSONObject joFr =(JSONObject)new JSONTokener(jsAr.get(i).toString()).nextValue();

                            JSONArray jaSe=joFr.getJSONArray("items");

                            for(int j=0;j<jaSe.length();j++)
                            {
                                JSONArray jaTh =(JSONArray)new JSONTokener(jaSe.get(j).toString()).nextValue();

                                for(int k=0;k<jaTh.length();k++)
                                {
                                    JSONObject joLst =(JSONObject)new JSONTokener(jaTh.get(k).toString()).nextValue();
                                    Log.d("ForM", joLst.toString());
                                    if(joLst.has("type"))
                                    {
                                        //LinearLayout Layout = findViewById(R.id.forms);
                                        String Type=joLst.getString("type");

                                        switch (Type)
                                        {
                                            case "title" :
                                                Toast.makeText(FormulaireActivity.this, " "+joLst.getString("label"), Toast.LENGTH_LONG).show();

                                                TextView title = new TextView(getApplicationContext());
                                                title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                title.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL|Gravity.CENTER);
                                                title.setTypeface(null, Typeface.BOLD);
                                                title.setTextSize(24);
                                                title.setTextColor(Color.rgb(78,79,173));
                                                title.setPadding(2, 2, 2, 20);

                                                title.setText(joLst.getString("label"));
                                                //if (Layout != null) {

                                                linearLayout.addView(title);
                                                //}
                                                Log.d("ITEM", "title item!!!");
                                                break;
                                            case "text":
                                                TextView textView = new TextView(getApplicationContext());
                                                textView.setTextColor(getResources().getColor(R.color.design_default_color_primary_dark));
                                                textView.setText(joLst.getString("label"));
                                                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                textView.setGravity(Gravity.CENTER);
                                                linearLayout.addView(textView);

                                                textView.setTypeface(null, Typeface.BOLD);

                                                // Create EditText
                                                Log.d("Debugging..", joLst.getString("placeholder"));
                                                final EditText editText = new EditText(getApplication());
                                                editText.setHint(joLst.getString("placeholder"));
                                                editText.setHintTextColor(getResources().getColor(R.color.lightBlue));
                                                editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                                editText.setPadding(20, 20, 20, 20);
                                                //editText.setTextColor(Color.BLACK);
                                                //editText.setSingleLine(true);
                                                
                                                editText.setMaxLines(2);
                                                linearLayout.addView(editText);
                                                break;
                                            case "submit" :
                                                TextView view=new TextView(getApplicationContext());
                                                view.setPadding(10,8,8,8);
                                                linearLayout.addView(view);
                                                Button bouton = new Button(getApplicationContext());

                                                bouton.setTextColor(Color.rgb(255,255,255));
                                                bouton.setText(joLst.getString("label"));
                                                bouton.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                                              //  bouton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                bouton.setGravity(Gravity.CENTER_HORIZONTAL);
                                                bouton.setPadding(10,20,8,8);
                                                linearLayout.addView(bouton);




                                                break;
                                        }


                                    }
                                }
                            }
                        }
                        TextView view=new TextView(getApplicationContext());
                        view.setPadding(0,0,0,20);
                        linearLayout.addView(view);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Log.e("error ","form error");
                }

            }
            @Override
            public void onFailure(Call<DYNContent> call, Throwable t) {
                Toast.makeText(FormulaireActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();

            }
        });
    }

}
