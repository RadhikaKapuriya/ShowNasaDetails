package com.example.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.Model.Post;
import com.example.viewmodel.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public MutableLiveData<List<Post>> mutableLiveDataPost = new MutableLiveData<>();
    private Application application;
    public UserRepository(Application application) {
        this.application = application;
    }



    public MutableLiveData<List<Post>> getPost() {


        if(new File(MyApplication.getAppComponent().getFilesDir() + "/data.json").exists())
        {

            readFromFile();

            Gson gson = new Gson();
            Type type = new TypeToken<List<Post>>(){}.getType();
            List<Post> contactList = gson.fromJson(readFromFile(), type);
            mutableLiveDataPost.setValue(contactList);
            for (Post contact : contactList){
                Log.i("Contact Details", contact.getUrl() );
            }

        }
        else {
            DownloadVideoAsyncTask task = new DownloadVideoAsyncTask(MyApplication.getAppComponent());
            task.execute();
        }
        return mutableLiveDataPost;
    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = MyApplication.getAppComponent().openFileInput("data.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public class DownloadVideoAsyncTask extends AsyncTask<String, Integer, String> {

        private Context mContext;

        public DownloadVideoAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL("https://drive.google.com/u/0/uc?id=18t-LzVG7bxu-oPxJQZg8P49I9UHcA552&export=download");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();

//            output = new FileOutputStream("/data/data/com.example.vadym.test1/textfile.txt");
                output = new FileOutputStream(mContext.getFilesDir() + "/data.json");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("ptg", "onProgressUpdate: " + values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(new File(MyApplication.getAppComponent().getFilesDir() + "/data.json").exists())
            {

                readFromFile();

                Gson gson = new Gson();
                Type type = new TypeToken<List<Post>>(){}.getType();
                List<Post> contactList = gson.fromJson(readFromFile(), type);
                mutableLiveDataPost.setValue(contactList);
                for (Post contact : contactList){
                    Log.i("Contact Details", contact.getUrl() );
                }

            }
        }
    }




//    public MutableLiveData<List<User>> getUsers() {
//        apiInterface = RestClient.getClient().create(GithubService.class);
//        Call<List<User>> call = apiInterface.getUsers();
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.body() != null) {
//                    userArrayList = (ArrayList<User>) response.body();
//
//                    mutableLiveData.setValue(userArrayList);
//
//                }
//            }
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//            }
//        });
//        return mutableLiveData;
//    }

}
