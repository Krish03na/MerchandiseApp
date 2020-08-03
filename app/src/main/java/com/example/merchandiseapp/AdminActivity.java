package com.example.merchandiseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.nio.BufferUnderflowException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {


    String categoryname,description,Pname,price,saveCurrentDate,saveCurrentTime,RandomProductKey,ImageUrl;
    ImageView input_image;
    EditText product_name,product_description,product_price;
    Button addnew;
    ProgressDialog progressDialog;

    StorageReference ProductImageRef;
    DatabaseReference Productref;
    private static  int GalleryPick = 1 ;
    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        categoryname = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        Productref = FirebaseDatabase.getInstance().getReference().child("Products");
        progressDialog = new ProgressDialog(this);

        input_image = (ImageView) findViewById(R.id.insert_image);
        addnew = (Button) findViewById(R.id.addnewprod);
        product_price = (EditText) findViewById(R.id.pprice);
        product_name = (EditText) findViewById(R.id.pname);
        product_description= (EditText) findViewById(R.id.pdesc);


        input_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Button 1 Pressed",Toast.LENGTH_SHORT).show();
                VaildateProductData();
            }
        });

    }
    private void openGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!= null){
            imageUri = data.getData();
            input_image.setImageURI(imageUri);
        }
    }

    private void VaildateProductData() {
        description = product_description.getText().toString();
        Pname = product_name.getText().toString();
        price = product_price.getText().toString();
        //Toast.makeText(getApplicationContext(),"Button Pressed",Toast.LENGTH_SHORT).show();
        if(imageUri == null){
            Toast.makeText(getApplicationContext(), "Image not selected" ,Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Pname)){
            Toast.makeText(getApplicationContext(),"Product name not given",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(description)){
            Toast.makeText(getApplicationContext(),"Description is Empty",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(price)){
            Toast.makeText(getApplicationContext(),"Price is not given",Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(getApplicationContext(),"Button Pressed",Toast.LENGTH_SHORT).show();
            StoreProductInfo();
        }

    }

    private void StoreProductInfo() {

        progressDialog.setTitle("Storing Product Information");
        progressDialog.setMessage("Please Wait while we store the information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat curdate = new SimpleDateFormat("MM DD,YYYY");
        saveCurrentDate = curdate.format(calendar.getTime());
        SimpleDateFormat curtime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = curtime.format(calendar.getTime());

        RandomProductKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filepath = ProductImageRef.child(imageUri.getLastPathSegment() + RandomProductKey+".jpg");

        final UploadTask uploadTask = filepath.putFile(imageUri);
        //Toast.makeText(getApplicationContext(),"Button Pressed",Toast.LENGTH_SHORT).show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                String message =e.toString();
                Toast.makeText(getApplicationContext(),"Error : " + message,Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Toast.makeText(getApplicationContext(),"Product Image uploaded successfully",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()){

                            throw  task.getException();

                        }
                        ImageUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            ImageUrl = task.getResult().toString();
                            //Toast.makeText(getApplicationContext(),"Image saved to database" ,Toast.LENGTH_SHORT).show();
                            SaveProductInfo();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfo() {
        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",RandomProductKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",description);
        productMap.put("image",ImageUrl);
        productMap.put("category",categoryname);
        productMap.put("name",Pname);
        productMap.put("price",price);



        Productref.child(RandomProductKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //Toast.makeText(AdminActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminActivity.this,AdminCategoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    String message = task.getException().toString();
                    Toast.makeText(AdminActivity.this, "Error :" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
