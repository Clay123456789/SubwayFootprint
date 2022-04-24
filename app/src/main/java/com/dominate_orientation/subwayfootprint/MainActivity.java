package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "lol wtf";
    public TextView num1 ;
    public TextView num2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num1 = (TextView) findViewById(R.id.textView1);
        num2 = (TextView) findViewById(R.id.textView2);
    }

    public void aMethod(View view){
        Navigation.findNavController(view).navigate(R.id.action_frag_1_to_frag_2);
    }

    public void aMethod2(View view){
        Navigation.findNavController(view).navigate(R.id.action_frag_2_to_frag_1);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}