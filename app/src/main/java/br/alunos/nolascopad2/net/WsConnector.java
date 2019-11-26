package br.alunos.nolascopad2.net;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WsConnector
{
    public static final String BASE_SERVER = "http://192.168.1.69:55555";

    public static HttpURLConnection connectTo (@NonNull String route) throws IOException
    {
        HttpURLConnection con = (HttpURLConnection) new URL(BASE_SERVER + route).openConnection();
        con.setConnectTimeout(10000);
        con.setReadTimeout(5000);
        con.setDoInput(true);
        con.addRequestProperty("Accept", "application/json");
        con.addRequestProperty("Accept", "text/plain");
        con.addRequestProperty("Content-Type", "application/json");
        return con;
    }

    public static HttpURLConnection getFrom (@NonNull String route) throws IOException
    {
        HttpURLConnection con = connectTo(route);
        con.setRequestMethod("GET");
        con.connect();
        Log.d("net", con.getRequestMethod());
        return con;
    }

    public static HttpURLConnection getFrom (@NonNull String route, JSONObject data) throws IOException
    {
        HttpURLConnection con = connectTo(route);
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.connect();
        DataOutputStream writer = new DataOutputStream(con.getOutputStream());
        writer.writeBytes(data.toString());
        writer.flush();
        writer.close();
        return con;
    }

    public static String getResponseData (@NonNull HttpURLConnection con) throws IOException
    {
        byte[] buffer = new byte[1024];

        Log.d("net", "" + con.getResponseCode());
        Log.d("net", "" + con.getRequestMethod());
        InputStream in = con.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int read;

        while ((read = in.read(buffer)) != -1)
            out.write(buffer, 0, read);
        con.disconnect();
        return new String(out.toByteArray(), "UTF-8");
    }

    public static HttpURLConnection postTo (String route, JSONObject data) throws IOException
    {
        HttpURLConnection con = connectTo(route);
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.connect();

        DataOutputStream writer = new DataOutputStream(con.getOutputStream());
        Log.d("post", "postTo: " + data);
        writer.writeBytes(data.toString());
        writer.flush();
        writer.close();

        Scanner s = new Scanner(con.getInputStream());
        s.next();
        s.close();
        return con;
    }

    public static HttpURLConnection deleteFrom (@NonNull String route) throws IOException
    {
        HttpURLConnection con = connectTo(route);
        con.setRequestMethod("DELETE");
        con.connect();

        return con;
    }

}
