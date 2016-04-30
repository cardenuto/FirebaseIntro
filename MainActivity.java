package info.anth.firebaseinto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Firebase mRef;

    Firebase mRefRoot;
    ValueEventListener eventListener;
    TextView resultsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://hudsonvalleygdg.firebaseio.com");
        mRefRoot = mRef;
        resultsText = (TextView) findViewById(R.id.text_results);
    }

    public void clickChild(View view){
        // This method moves along the children of the database
        // Each call will add a new child to the path
        //
        // In file systems terms: creating a sub-directory

        // Get the Edit Text
        EditText childEntry = (EditText) findViewById(R.id.edit_child);
        // Get reference display text field
        TextView displayRef = (TextView) findViewById(R.id.text_reference);

        // update the Firebase reference (if child has a value)
        mRef = mRef.child(childEntry.getText().toString());
        // set reference display text
        displayRef.setText(mRef.getRef().toString());

        // clear Child edit box
        childEntry.setText("");
    }

    public void clickParent(View view){
        // This method moves to the parent of the current reference

        // Get reference display text field
        TextView displayRef = (TextView) findViewById(R.id.text_reference);

        // Need to check if already at root.
        if (!mRef.getRef().toString().equals(mRef.getRoot().toString())) {
            // NOT at root
            // update the Firebase reference (if child has a value)
            mRef = mRef.getParent();
        }
        // set reference display text
        displayRef.setText(mRef.getRef().toString());
    }

    public void clickSetName(View view){
        // This method saves the data for the name using the set method

        // Get the Name entry
        EditText nameEntry =  (EditText) findViewById(R.id.edit_name);

        // Define hash map and set the values
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("name", nameEntry.getText().toString());
        mRef.setValue(updateMap);
    }

    public void clickSet3(View view){
        // This method saves the data for name, food, and color using the set method

        // Get the Name entry
        EditText nameEntry =  (EditText) findViewById(R.id.edit_name);
        // Get the Food entry
        EditText foodEntry =  (EditText) findViewById(R.id.edit_food);
        // Get the Name entry
        EditText colorEntry =  (EditText) findViewById(R.id.edit_color);

        // Define hash map and set the values
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("name", nameEntry.getText().toString());
        updateMap.put("color", colorEntry.getText().toString());
        updateMap.put("food", foodEntry.getText().toString());
        mRef.setValue(updateMap);
    }

    public void clickUpdateFood(View view){
        // This method saves the data for food using the updateChildren method

        // Get the Food entry
        EditText foodEntry =  (EditText) findViewById(R.id.edit_food);

        // Define hash map and set the values
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("food", foodEntry.getText().toString());
        mRef.updateChildren(updateMap);
    }

    public void clickUpdate3(View view){
        // This method saves the data for name, food, and color
        // using the updateChildren method

        // Get the Name entry
        EditText nameEntry =  (EditText) findViewById(R.id.edit_name);
        // Get the Food entry
        EditText foodEntry =  (EditText) findViewById(R.id.edit_food);
        // Get the Name entry
        EditText colorEntry =  (EditText) findViewById(R.id.edit_color);

        // Define hash map and set the values
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("name", nameEntry.getText().toString());
        updateMap.put("color", colorEntry.getText().toString());
        updateMap.put("food", foodEntry.getText().toString());
        mRef.updateChildren(updateMap);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FirebaseInto", String.valueOf(dataSnapshot.getValue()));
                resultsText.setText(String.valueOf(dataSnapshot.getValue()));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("FirebaseInto", firebaseError.toString());
            }
        };
        mRefRoot.addValueEventListener(eventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRefRoot.removeEventListener(eventListener);
    }
}
