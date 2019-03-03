package com.example.listviewlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private Places places;
    private EditText eName;
    private Button btnUpdate, btnDelete;
    private TextView eLat, eLng;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        //
        Intent intent = getIntent();
        places = (Places) intent.getSerializableExtra("places");

        db = new DatabaseHelper(this);

        eName = (EditText)findViewById(R.id.updateeditText);
        btnUpdate = (Button)findViewById(R.id.updatebutton1);
        btnDelete = (Button)findViewById(R.id.updatebutton2);
        eLat = (TextView)findViewById(R.id.updatetextLatitude);
        eLng = (TextView)findViewById(R.id.updatetextLongitude);

        eName.setText(places.getName());
        eLat.setText(places.getLatitude());
        eLng.setText(places.getLongitude());

        //when UPDATE button is clicked
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //declaring variables
                String updateName = eName.getText().toString();
                String updatelat = eLat.getText().toString();
                String updatelng = eLng.getText().toString();

                //check if lcoation field is empty
                if(!updateName.equals("")){
                    //call updateLocation method from DatabaseHelper
                    db.updateLocation(places.getId(),updateName,updatelat,updatelng);
                    Toast.makeText(getApplicationContext(),"Location has been updated!",Toast.LENGTH_SHORT).show();

                    //direct the user to main activity after update
                    Intent backMain = new Intent(EditDataActivity.this,MainActivity.class);
                    startActivity(backMain);
                }else{
                    Toast.makeText(getApplicationContext(),"Location field can not be empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //when delete button is clicked
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call deleteLocation method
                db.deleteLocation(places.getId());
                Toast.makeText(getApplicationContext(),"Location has been deleted!",Toast.LENGTH_SHORT).show();
                Intent delBack = new Intent(EditDataActivity.this,MainActivity.class);
                startActivity(delBack);
            }
        });
    }
}
