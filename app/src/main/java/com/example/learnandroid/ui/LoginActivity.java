package com.example.learnandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.learnandroid.data.network.request.user.LoginReq;
import com.example.learnandroid.databinding.ActivityLoginBinding;
import com.example.learnandroid.viewmodel.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * @author hieutt (tora262)
 */
@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();
    ActivityLoginBinding mBinding;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        initView();
    }

    private void initView() {
        observe();
        mBinding.btnLogin.setOnClickListener(view ->
                viewModel.login(new LoginReq("cuongnh2k@gmail.com", "12345aA@")));
    }

    private void observe() {
        viewModel.getErrorMessageLiveData().observe(this, errorMessage -> {
            Log.d(TAG, "observe: errorMessage = " + errorMessage);
            Toast.makeText((Context) this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        viewModel.getLiveData().observe(this, response -> {
            Log.d(TAG, "observe: userResponse = " + response);
            Toast.makeText((Context) this, response.toString(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Timber.d(intent.getAction());
    }
}