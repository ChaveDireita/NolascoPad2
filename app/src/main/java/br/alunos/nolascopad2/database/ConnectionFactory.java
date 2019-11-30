package br.alunos.nolascopad2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class ConnectionFactory extends SQLiteOpenHelper {

    public static int VERSION = 3;
    public static String NAME = "nolascopad2";
    public static String TABELA_USER = "usuario";
    public static String TABELA_LIVRO = "livro";
    public static String TABELA_CAPITULO = "capitulo";
    public static String TABELA_PAGINA = "pagina";

    public ConnectionFactory(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("create table if not exists "+TABELA_USER+"(\n" +
                    "\tid integer primary key autoincrement,\n" +
                    "    nome varchar(100),\n" +
                    "    email varchar(100),\n" +
                    "    senha varchar(100));");
            db.execSQL(
                    "create table if not exists "+TABELA_LIVRO+"(\n" +
                            "\tid integer primary key autoincrement,\n" +
                            "    descricao varchar (500),\n" +
                            "    titulo varchar(100),\n" +
                            "    userid int,\n" +
                            "    isprivate bool,\n" +
                            "    datamodificacao varchar(10),\n" +
                            "    ncaps int,\n" +
                            "    npages int,\n" +
                            "    foreign key (userid) references "+TABELA_USER+"(id));");
            db.execSQL(
                    "create table if not exists "+TABELA_CAPITULO+"(\n" +
                            "\tid integer primary key autoincrement,\n" +
                            "    titulo varchar(100),\n" +
                            "    descricao varchar(500),\n" +
                            "    datamodificacao varchar(10),\n" +
                            "    livroid int,\n" +
                            "    npages int,\n" +
                            "    foreign key (livroid) references "+TABELA_LIVRO+"(id));");
            db.execSQL(
                    "create table if not exists "+TABELA_PAGINA+"(\n" +
                            "\tcapituloid integer primary key,\n" +
                            "    titulo text(4000),\n" +
                            "    foreign key (capituloid) references "+TABELA_CAPITULO+"(id));");
            Log.w("Teste","Todas as tabelas foram criadas com sucesso");
        }catch(Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try
        {
            Log.w("Teste","Todas as tabelas foram criadas com sucesso");
        }catch(Exception e)
        {

        }
    }
/*
    @Override
    public void onOpen(SQLiteDatabase db) {
        //db.execSQL("delete from "+TABELA_POST+";");
        //db.execSQL("delete from "+TABELA_CAPITULO+";");
        db.execSQL("drop table if exists "+TABELA_LIVRO+";");
        db.execSQL("create table if not exists "+TABELA_USER+"(\n" +
                "\tid integer primary key autoincrement,\n" +
                "    nome varchar(100),\n" +
                "    email varchar(100),\n" +
                "    senha varchar(100));");
        db.execSQL("drop table if exists "+TABELA_LIVRO+";");
        db.execSQL(
                "create table if not exists "+TABELA_LIVRO+"(\n" +
                        "\tid integer primary key autoincrement,\n" +
                        "    descricao varchar (500),\n" +
                        "    titulo varchar(100),\n" +
                        "    userid int,\n" +
                        "    isprivate bool,\n" +
                        "    datamodificacao varchar(10),\n" +
                        "    ncaps int,\n" +
                        "    npages int,\n" +
                        "    foreign key (userid) references "+TABELA_USER+"(id));");
        db.execSQL("drop table if exists "+TABELA_CAPITULO+";");
        db.execSQL(
                "create table if not exists "+TABELA_CAPITULO+"(\n" +
                        "\tid integer primary key autoincrement,\n" +
                        "    titulo varchar(100),\n" +
                        "    descricao varchar(500),\n" +
                        "    datamodificacao varchar(10),\n" +
                        "    livroid int,\n" +
                        "    npages int,\n" +
                        "    foreign key (livroid) references "+TABELA_LIVRO+"(id));");

        db.execSQL("drop table if exists "+TABELA_PAGINA+";");
        db.execSQL(
                "create table if not exists "+TABELA_PAGINA+"(\n" +
                        "\tcapituloid integer primary key,\n" +
                        "    titulo text(4000),\n" +
                        "    foreign key (capituloid) references "+TABELA_CAPITULO+"(id));");
    }*/
}
