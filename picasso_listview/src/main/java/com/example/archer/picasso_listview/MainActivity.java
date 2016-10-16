package com.example.archer.picasso_listview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archer.picasso_listview.entity.Item;
import com.example.archer.picasso_listview.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= (ListView) findViewById(R.id.list_view);

        listView.setOnScrollListener(new Listscroller());//添加滚动事件
        dialog=new ProgressDialog(this);
        dialog.setTitle("loading.....");
        new Mytask().execute("http://litchiapi.jstv.com/api/GetFeeds?column=17&PageSize=20&pageIndex=1&val=AD908EDAB9C3ED111A58AF86542CCF50");

    }

    private static class ViewHolder{
        TextView  subject;
        TextView summary;
        ImageView img;

    }


    public class Listscroller implements AbsListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            final Picasso picasso = Picasso.with(MainActivity.this);
            if (scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_TOUCH_SCROLL){
                picasso.resumeTag(MainActivity.this);
            }else{
                picasso.pauseTag(MainActivity.this);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    class mAdapter extends BaseAdapter{

        private List<Item> data;

        public mAdapter(List<Item> data){

            this.data=data;

        }


        @Override
        public int getCount() {
            return  data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder hoder=null;
            if (convertView==null){
                hoder=new ViewHolder();
                convertView= View.inflate(MainActivity.this,R.layout.layout,null);
//                convertView = getLayoutInflater().inflate(R.layout.layout, parent, false);

                hoder.subject=(TextView)convertView.findViewById(R.id.subject);
                hoder.summary= (TextView)convertView.findViewById(R.id.summary);
                hoder.img=(ImageView)convertView.findViewById(R.id.imageView);
                convertView.setTag(hoder);
            }else {
                hoder= (ViewHolder) convertView.getTag();
            }
            hoder.subject.setText(data.get(position).getSubject());
            hoder.summary.setText(data.get(position).getSummary());
            PicassoUtils.loadImageWithSize(MainActivity.this, "http://litchiapi.jstv.com" + data.get(position).getCover(), 400, 300, hoder.img);


            return convertView;
        }
    }






    class Mytask extends AsyncTask<String,Void,List<Item>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Item> doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
            HttpResponse response = null;
            List<Item> list = new ArrayList<>();
            try {
                response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    String json_value = EntityUtils.toString(entity, "utf-8");
                    JSONArray jsonArray = new JSONObject(json_value).getJSONObject("paramz").getJSONArray("feeds");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject element = jsonArray.getJSONObject(i).getJSONObject("data");
                        Item item = new Item();
                        item.setCover(element.getString("cover"));
                        item.setSubject(element.getString("subject"));
                        item.setSummary(element.getString("summary"));
                        list.add(item);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);

            mAdapter madapter = new mAdapter(items);
            listView.setAdapter(madapter);
            dialog.dismiss();
        }
    }

}
