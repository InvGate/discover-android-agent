package com.invgate.discover.androidagent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.invgate.discover.androidagent.services.ServiceScheduler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ServiceScheduler.schedule(25L, this);
    }

}
