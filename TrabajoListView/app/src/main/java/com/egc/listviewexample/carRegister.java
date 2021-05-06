package com.egc.listviewexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.egc.listviewexample.models.Car;
import com.ib.custom.toast.CustomToastView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class carRegister extends AppCompatActivity implements View.OnClickListener {

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 10 ;
    String img;
    private Button btnSend;
    private Button btnList;
    private TextView txtName;
    private TextView txtCylinderCapacity;
    private TextView txtModel;
    private TextView txtValue;
    private TextView txtUrl;
    ImageView mImageView;
    Button btnChooseImg;
    DbHelper db = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_register);

        btnSend = findViewById(R.id.btnSend);
        btnList = findViewById(R.id.btnList);
        txtName = findViewById(R.id.txtName);
        txtCylinderCapacity = findViewById(R.id.txtCylinderCapacity);
        txtModel = findViewById(R.id.txtModel);
        txtValue = findViewById(R.id.txtValue);
        txtUrl = findViewById(R.id.txtUrl);
        btnSend.setOnClickListener(this);
        btnList.setOnClickListener(this);

        ///
        mImageView = findViewById(R.id.imageView);
        btnChooseImg = findViewById(R.id.btnChooseImg);
        btnChooseImg.setOnClickListener(this);
        ///



        ArrayList<Car> cities =db.selectCar(db.getWritableDatabase());
        int i =1;
        for (Car ciudadSelected: cities){
            System.out.println("el auto "+ ciudadSelected.getName() + " tiene un modelo"+ ciudadSelected.getModel());
            i++;
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSend){
            if (txtName.getText().toString().isEmpty()|| txtCylinderCapacity.getText().toString().isEmpty()||
                    txtModel.getText().toString().isEmpty()||txtValue.getText().toString().isEmpty()){
                CustomToastView.makeErrorToast(this, "Llene todos los campos", R.layout.custom_toast).show();

            }else{
                addCar();
                CustomToastView.makeSuccessToast(this, "Auto Guardado", R.layout.custom_toast).show();

            }
        }else if (v.getId()== R.id.btnList){
            Intent intentList = new Intent(this, MainActivity.class);
            startActivity(intentList);
        }else if (v.getId() == R.id.btnChooseImg){
            mostrarDialogOpciones();
        }
    }

    private void mostrarDialogOpciones(){
        final CharSequence[] opciones = {"Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliger una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_FOTO);
                    } else {
                        dialogInterface.dismiss();
                    }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                img = miPath.toString();
                mImageView.setImageURI(miPath);
                break;
        }
    }

    private void addCar() {
        /*imageViewToByte(mImageView)*/
        Car car = new Car(txtName.getText().toString(),txtCylinderCapacity.getText().toString(),txtModel.getText().toString(),
                txtValue.getText().toString(),txtUrl.getText().toString());
        db.insertCity(db.getWritableDatabase(),car);
        clearFields();
    }

    public void clearFields(){
        txtName.setText("");
        txtCylinderCapacity.setText("");
        txtModel.setText("");
        txtValue.setText("");
        txtUrl.setText("");
    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}