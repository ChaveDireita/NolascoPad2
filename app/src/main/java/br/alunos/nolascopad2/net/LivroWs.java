package br.alunos.nolascopad2.net;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.alunos.nolascopad2.net.WsConnector.*;

import br.alunos.nolascopad2.net.model.LivroNet;

public class LivroWs
{
    public static List<LivroNet> getLivrosUser (String user)
    {
        List<LivroNet> livros = new ArrayList<>();
        try
        {
            String res = getResponseData(get("/nolascopad/livro/" + user));
            JSONArray jsonArray = new JSONArray(res);

            for (int i = 0; i < jsonArray.length(); i++)
                livros.add(JsonConverter.livroFromJson(jsonArray.getJSONObject(i)));
        }catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return livros;
    }



}
