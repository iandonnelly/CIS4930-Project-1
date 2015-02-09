package com.example.ianjavier.project1.presentation.views.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.views.dialogs.CreateServerDialogFragment;
import com.example.ianjavier.project1.presentation.views.dialogs.JoinServerDialogFragment;


public class MainActivity extends ActionBarActivity implements
        JoinServerDialogFragment.JoinServerDialogListener,
        CreateServerDialogFragment.CreateServerDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createServerButton = (Button) findViewById(R.id.createServerButton);
        Button joinServerButton = (Button) findViewById(R.id.joinServerButton);

        createServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new CreateServerDialogFragment();
                dialog.show(getFragmentManager(), "CreateServerDialogFragment");
            }
        });

        joinServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new JoinServerDialogFragment();
                dialog.show(getFragmentManager(), "JoinServerDialogFragment");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onJoinServerDialogPositiveClicked(String address, int port, String nickname) {
        Intent intent = new Intent(this, ClientActivity.class);
        intent.putExtra(getString(R.string.address), address);
        intent.putExtra(getString(R.string.port), port);
        intent.putExtra(getString(R.string.nickname), nickname);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCreateServerDialogPositiveClicked(String name, int port) {
        Intent intent = new Intent(this, ServerActivity.class);
        intent.putExtra(getString(R.string.name), name);
        intent.putExtra(getString(R.string.port), port);
        startActivity(intent);
        finish();
    }
}
