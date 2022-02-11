package com.troy.mysqlapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv;
    ListView lv;
    ItemAdapter itemAdapter;
    Context thisContext;
    Fruit obj;
    ArrayList<Fruit> fruits ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnGetData);
        tv = findViewById(R.id.displayTextView);
        lv = findViewById(R.id.myListView);

        thisContext = this;

        tv.setText("");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataAccess data = new DataAccess();
                data.execute("");

            }
        });

    }

    private class DataAccess extends AsyncTask<String,String,String>{

        String msg = "";
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://" + DBStrings.DATABASE_URL +"/"+DBStrings.DATABASE_NAME;

        @Override
        protected void onPreExecute(){
            tv.setText("Connecting to Database");
        }

        @Override
        protected String doInBackground(String... strings) {
            fruits = new ArrayList<>();
            Connection con = null;
            Statement stmt = null;

            try {
                Class.forName(JDBC_DRIVER);

                con = DriverManager.getConnection(DB_URL,DBStrings.USERNAME,DBStrings.PASSWORD );
                stmt = con.createStatement();

                String qry = "SELECT * FROM Fruits";

                ResultSet rs = stmt.executeQuery(qry);

                while (rs.next()){
                    String name = rs.getString("Name");
                    double price = rs.getDouble("Price");

                    obj = new Fruit(name,price);

                    fruits.add(obj);
                }
                msg = "Process Complete";

                rs.close();
                stmt.close();
                con.close();

            } catch (ClassNotFoundException e) {
                msg = "An Exception was thrown for JDBC DRIVER: \n"+ e.getMessage();
                e.printStackTrace();
            } catch (SQLException e) {
                msg = "A SQL Exception was thrown: \n"+ e.getMessage();
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null){
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (con != null){
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String msg){
            tv.setText(this.msg);

            if (fruits.size() > 0){
                lv.setAdapter(itemAdapter);
                itemAdapter = new ItemAdapter(thisContext,fruits);
                lv.setAdapter(itemAdapter);
            }

        }
    }

}
