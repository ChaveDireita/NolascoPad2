package br.alunos.nolascopad2.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.net.model.CapituloNet;
import br.alunos.nolascopad2.net.model.LivroNet;
import br.alunos.nolascopad2.models.Pagina;
import br.alunos.nolascopad2.models.User;

public class JsonConverter
{
    public static JSONObject toJson (User user) throws JSONException
    {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("nome", user.nome);
        jsonObject.put("email", user.email);
        jsonObject.put("senha", user.senha);

        return  jsonObject;
    }

    public static User userFromJson(JSONObject json) throws JSONException
    {
        User user = new User();
        user.nome = json.getString("nome");
        user.email = json.getString("email");
        user.senha = json.getString("senha");

        return user;
    }


    public static LivroNet livroFromJson(JSONObject json) throws JSONException
    {
        LivroNet livro = new LivroNet();

        livro.titulo = json.getString("titulo");
        livro.npages = json.getInt("npages");
        livro.desc = json.getString("desc");
        livro.lastedit = json.getString("lastedit");
        livro.ncaps = json.getInt("ncaps");
        livro.isprivate = json.getBoolean("isprivate");
        livro.capitulos = new ArrayList<>();

        JSONArray jsonCapitulos = json.getJSONArray("capitulos");

        for (int i = 0; i < jsonCapitulos.length(); i++)
            livro.capitulos.add(capituloFromJson(jsonCapitulos.getJSONObject(i)));

        return livro;
    }

    public static CapituloNet capituloFromJson (JSONObject json) throws JSONException
    {
        CapituloNet capitulo = new CapituloNet();

        capitulo.titulo = json.getString("titulo");
        capitulo.desc = json.getString("desc");
        capitulo.lastedit = json.getString("lastedit");
        capitulo.npages = json.getInt("npages");
        capitulo.pagina = json.getString("pagina");

        return capitulo;
    }
}