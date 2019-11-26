package br.alunos.nolascopad2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.models.Pagina;

public class LivroDAO {
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Context contexto;
    public LivroDAO(Context contexto) {
        ConnectionFactory connectionFactory = new ConnectionFactory(contexto);
        this.contexto = contexto;
        write = connectionFactory.getWritableDatabase();
        read = connectionFactory.getReadableDatabase();
    }
    public boolean saveLivro(Livro livro){
        ContentValues values = new ContentValues();
        values.put("descricao",livro.desc);
        values.put("titulo",livro.titulo);
        values.put("userid",livro.userid);
        values.put("datamodificacao",livro.lastedit);
        values.put("ncaps",0);
        values.put("npages",0);
        values.put("isprivate",livro.isprivate);
        write.insert(ConnectionFactory.TABELA_LIVRO,null,values);
        return true;
    }

    public int saveLivroAndReturnId(Livro livro){
        ContentValues values = new ContentValues();
        values.put("descricao",livro.desc);
        values.put("titulo",livro.titulo);
        values.put("userid",livro.userid);
        values.put("datamodificacao",livro.lastedit);
        values.put("ncaps",0);
        values.put("npages",0);
        values.put("isprivate",livro.isprivate);
        return (int) write.insert(ConnectionFactory.TABELA_LIVRO,null,values);
    }

    public Livro getLivroFromDB(int livroid){
        Livro livro = new Livro();
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_LIVRO+" WHERE id = "+livroid+";",null);
        cursor.moveToNext();
        livro.titulo = cursor.getString(cursor.getColumnIndex("titulo"));
        livro.desc = cursor.getString(cursor.getColumnIndex("descricao"));
        livro.lastedit = cursor.getString(cursor.getColumnIndex("datamodificacao"));
        livro.id = cursor.getInt(cursor.getColumnIndex("id"));
        livro.userid = cursor.getInt(cursor.getColumnIndex("userid"));
        livro.ncaps = cursor.getInt(cursor.getColumnIndex("ncaps"));
        livro.npages = cursor.getInt(cursor.getColumnIndex("npages"));
        livro.isprivate = cursor.getInt(cursor.getColumnIndex("isprivate"))>0;
        return livro;
    }
    public Capitulo getCapituloFromDB(int capid){
        Capitulo capitulo = new Capitulo();
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_CAPITULO+" WHERE id = "+capid+";",null);
        cursor.moveToNext();
        capitulo.titulo = cursor.getString(cursor.getColumnIndex("titulo"));
        capitulo.desc = cursor.getString(cursor.getColumnIndex("descricao"));
        capitulo.lastedit = cursor.getString(cursor.getColumnIndex("datamodificacao"));
        capitulo.id = cursor.getInt(cursor.getColumnIndex("id"));
        capitulo.livroid = cursor.getInt(cursor.getColumnIndex("livroid"));
        capitulo.npages = cursor.getInt(cursor.getColumnIndex("npages"));
        return capitulo;
    }
    public ArrayList<Livro> getAllPublicBooks(){
        ArrayList<Livro>list = new ArrayList<>();
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_LIVRO+" WHERE isprivate = 0;",null);
        int qt = cursor.getCount();
        while (cursor.moveToNext()){
            Livro livro = new Livro();
            livro.titulo = cursor.getString(cursor.getColumnIndex("titulo"));
            livro.desc = cursor.getString(cursor.getColumnIndex("descricao"));
            livro.lastedit = cursor.getString(cursor.getColumnIndex("datamodificacao"));
            livro.id = cursor.getInt(cursor.getColumnIndex("id"));
            livro.userid = cursor.getInt(cursor.getColumnIndex("userid"));
            livro.ncaps = cursor.getInt(cursor.getColumnIndex("ncaps"));
            livro.npages = cursor.getInt(cursor.getColumnIndex("npages"));
            livro.isprivate = cursor.getInt(cursor.getColumnIndex("isprivate"))>0;
            list.add(livro);
            Log.w("Teste","subindo tela "+livro.isprivate);
        }
        Log.w("Teste","voltando tela tela ");
        return list;
    }
    public ArrayList<Livro> getUserLivro(int userid){
        ArrayList<Livro>list = new ArrayList<>();
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_LIVRO+" WHERE USERID="+userid+";",null);
        int userlogado = new UserDAO(contexto).getUserFromDB(userid).id;
        while (cursor.moveToNext()){
            Livro livro = new Livro();
            livro.titulo = cursor.getString(cursor.getColumnIndex("titulo"));
            livro.desc = cursor.getString(cursor.getColumnIndex("descricao"));
            livro.lastedit = cursor.getString(cursor.getColumnIndex("datamodificacao"));
            livro.id = cursor.getInt(cursor.getColumnIndex("id"));
            livro.userid = cursor.getInt(cursor.getColumnIndex("userid"));
            livro.ncaps = cursor.getInt(cursor.getColumnIndex("ncaps"));
            livro.npages = cursor.getInt(cursor.getColumnIndex("npages"));
            livro.isprivate = cursor.getInt(cursor.getColumnIndex("isprivate"))>0;
            int useridbook = livro.userid;
            list.add(livro);
            Log.w("Teste","subindo tela "+livro.id);
        }
        Log.w("Teste","voltando tela tela ");
        return list;
    }
    public boolean saveCapitulo(Capitulo cap, Pagina pagina){
            ContentValues values = new ContentValues();
            values.put("descricao",cap.desc);
            values.put("livroid",cap.livroid);
            values.put("titulo",cap.titulo);
            values.put("datamodificacao",cap.lastedit);
            values.put("npages",0);
            int idcap = (int) write.insert(ConnectionFactory.TABELA_CAPITULO,null,values);
            pagina.capid = idcap;
            int idpage = (int) savePagina(pagina);
            return true;
    }
    public int savePagina(Pagina pag){
            ContentValues values = new ContentValues();
            values.put("titulo",pag.text);
            values.put("capituloid",pag.capid);
           return (int) write.insert(ConnectionFactory.TABELA_PAGINA,null,values);
    }
    public ArrayList<Capitulo> getLivroCaps(int livroid){
        ArrayList<Capitulo>list = new ArrayList<>();
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_CAPITULO+" WHERE LIVROID = "+livroid+";",null);
        while (cursor.moveToNext()){
            Capitulo capitulo = new Capitulo();
            capitulo.titulo = cursor.getString(cursor.getColumnIndex("titulo"));
            capitulo.desc = cursor.getString(cursor.getColumnIndex("descricao"));
            capitulo.id = cursor.getInt(cursor.getColumnIndex("id"));
            capitulo.livroid = cursor.getInt(cursor.getColumnIndex("livroid"));
            capitulo.npages = cursor.getInt(cursor.getColumnIndex("npages"));
            capitulo.lastedit = cursor.getString(cursor.getColumnIndex("datamodificacao"));
            list.add(capitulo);
            Log.w("Teste","subindo tela "+capitulo.id);
        }
        Log.w("Teste","voltando tela tela ");
        return list;
    }
    public Pagina getCapituloPag(int capid){
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_PAGINA+" WHERE CAPITULOID = "+capid+";",null);
        cursor.moveToNext();
        Pagina pagina = new Pagina();
        pagina.text = cursor.getString(cursor.getColumnIndex("titulo"));
        pagina.capid = cursor.getInt(cursor.getColumnIndex("capituloid"));
        //Log.w("Teste","subindo tela "+pagina.id);
        Log.w("Teste","voltando tela tela ");
        return pagina;
    }
    public int getNLivrosperuser(int userid){
        int totalbooks=0;
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_LIVRO+" WHERE USERID="+userid+";",null);
        while (cursor.moveToNext()){
            totalbooks+=cursor.getCount();
        }
        return totalbooks;
    }
    public int getNCapsperuser(int userid){
        int totalcaps=0;
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_LIVRO+" WHERE USERID="+userid+";",null);
        while (cursor.moveToNext()){
            totalcaps+=getNCaps(cursor.getInt(cursor.getColumnIndex("id")));
        }
        return totalcaps;
    }
    public int getNCaps(int livroid){
        Cursor cursor;
        cursor = read.rawQuery("SELECT id FROM "+ConnectionFactory.TABELA_CAPITULO+" WHERE LIVROID = "+livroid+";",null);
        int totalcaps = cursor.getCount();
        return totalcaps;
    }
    public int getNPagesPerCap(int capid){
        Cursor cursor;
        cursor = read.rawQuery("SELECT id FROM "+ConnectionFactory.TABELA_PAGINA+" WHERE CAPITULOID = "+capid+";",null);
        int totalpages = cursor.getCount();
        return totalpages;
    }
    public int editLivro(Livro livro){
        ContentValues values = new ContentValues();
        values.put("descricao",livro.desc);
        values.put("titulo",livro.titulo);
        values.put("datamodificacao",livro.lastedit);
        values.put("isprivate",livro.isprivate);
        String[] args = {String.valueOf(livro.id)};
        write.update(ConnectionFactory.TABELA_LIVRO,values,"id = ?",args);
        return livro.id;
    }
    public int editCap(Capitulo capitulo){
        ContentValues values = new ContentValues();
        values.put("descricao",capitulo.desc);
        values.put("titulo",capitulo.titulo);
        values.put("datamodificacao",capitulo.lastedit);
        String[] args = {String.valueOf(capitulo.id)};
        write.update(ConnectionFactory.TABELA_CAPITULO,values,"id = ?",args);
        return capitulo.id;
    }
    public boolean editPage(Pagina pagina){
        ContentValues values = new ContentValues();
        values.put("titulo",pagina.text);
        String[] args = {String.valueOf(pagina.capid)};
        write.update(ConnectionFactory.TABELA_PAGINA,values,"capituloid = ?",args);
        return true;
    }
}
