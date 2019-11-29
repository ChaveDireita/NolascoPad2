package br.alunos.nolascopad2.net;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.alunos.nolascopad2.net.WsConnector.*;

import br.alunos.nolascopad2.models.User;

public class UserWs
{
    @NonNull
    public static String post (User user)
    {
        try
        {
            return getResponseData(WsConnector.postTo("/nolascopad/user", JsonConverter.toJson(user)));
        } catch (JSONException | IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public static List<User> getAll ()
    {
        List<User> users = new ArrayList<>();
        try
        {
            String s = getResponseData(getFrom("/nolascopad/user"));
            Log.d("net", s);
            JSONArray array = new JSONArray(s);

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonUser = array.getJSONObject(i);
                users.add(JsonConverter.userFromJson(jsonUser));
            }

        } catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return users;
    }

    public static User get (String user)
    {
        User u = null;
        try
        {
            u = JsonConverter.userFromJson(new JSONObject
                    (
                            getResponseData(getFrom("nolascopad/user/email"))
                    ));
        } catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

        return u;
    }

    public static boolean auth(String email, String senha)
    {
        try
        {
            getResponseData(getFrom("/nolascopad/auth"));
        } catch (IOException ignored)
        {
            return false;
        }
        return true;
    }
}
