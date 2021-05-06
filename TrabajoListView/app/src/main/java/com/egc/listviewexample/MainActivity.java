package com.egc.listviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.egc.listviewexample.models.AdapterCar;
import com.egc.listviewexample.models.Car;
import com.ib.custom.toast.CustomToastView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listViewNames;
    private ArrayList<Car> listCars = new ArrayList<>();
    DbHelper db = new DbHelper(this);
    private Button btnReturn;
    private Button btnDelete;


    //private String[] names= {"Edwin","Pepito","Toño"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewNames = findViewById(R.id.listViewNames);
        //Car c = new Car("FORD MUSTAD","2000","2021","140000000",null);
        //listCars.add(new Car("FORD MUSTAD","2000","2021","140000000",null));
        //listCars.add(new Car("CAMARO","2000","2021","140000000",null));
        //listCars.add(new Car("COBRA","2000","1996","140000000",null));
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        //ArrayList<Car> cars =;

        AdapterCar adapter = new AdapterCar(this,db.selectCar(db.getWritableDatabase()));
        listViewNames.setAdapter(adapter);
        listViewNames.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,"Ha pulsado el elemento numero "+position,Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Ha pulsado el elemento numero "+ listCars.get(position).getName(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnReturn){
            Intent intentReturn = new Intent(this,carRegister.class);
            startActivity(intentReturn);
        }else if (v.getId() == R.id.btnDelete){
            db.deleteAll();
            CustomToastView.makeSuccessToast(this, "Registros eliminados", R.layout.custom_toast).show();

        }
    }
}