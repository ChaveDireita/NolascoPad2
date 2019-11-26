package br.alunos.nolascopad2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.alunos.nolascopad2.database.ConnectionFactory;
import br.alunos.nolascopad2.models.User;

public class UserDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;
    public UserDAO(Context contexto) {
        ConnectionFactory connectionFactory = new ConnectionFactory(contexto);
        write = connectionFactory.getWritableDatabase();
        read = connectionFactory.getReadableDatabase();
    }

    public boolean saveUser(User usuario){
        ContentValues values = new ContentValues();
        values.put("nome",usuario.nome);
        values.put("email",usuario.email);
        values.put("senha",usuario.senha);
        values.put("nome",usuario.nome);
        write.insert(ConnectionFactory.TABELA_USER,null,values);
        return true;
    }
    public User getUserFromDB(int userid){
        User user = new User();
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_USER+" WHERE id = "+userid+";",null);
        cursor.moveToNext();
        if(cursor.getCount()>0){
            user.nome = cursor.getString(cursor.getColumnIndex("nome"));
            user.email = cursor.getString(cursor.getColumnIndex("email"));
            user.senha = cursor.getString(cursor.getColumnIndex("senha"));
            user.id = cursor.getInt(cursor.getColumnIndex("id"));
            return user;
        }
        return null;
    }
    public int getUserIDFromDBbyEmail(String email){
        User user = new User();
        Cursor cursor = read.rawQuery("SELECT id FROM "+ConnectionFactory.TABELA_USER+" WHERE email = '"+email+"';",null);
        cursor.moveToNext();
        if(cursor.getCount()>0){
            user.id = cursor.getInt(cursor.getColumnIndex("id"));
            return user.id;
        }
        return -1;
    }
    public ArrayList<User> getAllUsersExceptLogged(int loggeduser){
        ArrayList<User>list = new ArrayList<User>();
        Cursor cursor = read.rawQuery("SELECT * FROM "+ConnectionFactory.TABELA_USER+" WHERE id!="+loggeduser+";",null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                User user = new User();
                user.nome = cursor.getString(cursor.getColumnIndex("nome"));
                user.email = cursor.getString(cursor.getColumnIndex("email"));
                user.senha = cursor.getString(cursor.getColumnIndex("senha"));
                user.id = cursor.getInt(cursor.getColumnIndex("id"));
                list.add(user);
            }
            return list;
        }
        return null;
    }
    public int editUser(User usuario){
        //write.execSQL("INSERT INTO "+ConnectionFactory.TABELA_USER+" VALUES('"+usuario.nome+"','"+usuario.email+"','"+usuario.senha+"') WHERE ID="+usuario.id+"");
        ContentValues values = new ContentValues();
        values.put("nome",usuario.nome);
        values.put("email",usuario.email);
        values.put("senha",usuario.senha);
        String[] args = {String.valueOf(usuario.id)};
        write.update(ConnectionFactory.TABELA_LIVRO,values,"id = ?",args);
        return usuario.id;
    }
    public boolean searchUserByEmail(String email){
        Cursor cursor = read.rawQuery("SELECT EMAIL FROM "+ConnectionFactory.TABELA_USER+" WHERE EMAIL='"+email+"';",null);
        if(cursor.getCount()<=0){
            return false;
        }else {
            return true;
        }
    }
    public boolean searchUserByEmailAndPassword(String email,String password){
        Cursor cursor = read.rawQuery("SELECT EMAIL FROM "+ConnectionFactory.TABELA_USER+" WHERE EMAIL='"+email+"' AND SENHA='"+password+"';",null);
        if(cursor.getCount()<=0){
            return false;
        }else {
            return true;
        }
    }
}
