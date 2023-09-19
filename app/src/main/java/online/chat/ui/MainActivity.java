package online.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import online.chat.databinding.ActivityLoginBinding;
import online.chat.databinding.ActivityMainBinding;
import online.chat.viewmodel.LoginViewModel;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

//    private static final String TAG = LoginActivity.class.getName();
    ActivityMainBinding mBinding;
//    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
//        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initView();
    }

    private void initView() {
        observe();

    }

    private void observe() {
//        viewModel.getErrorMessageLiveData().observe(this, errorMessage -> {
//            Log.d(TAG, "observe: errorMessage = " + errorMessage);
//
//        });
//
//        viewModel.getLiveData().observe(this, response -> {
//            Log.d(TAG, "observe: userResponse = " + response);
//        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Timber.d(intent.getAction());
    }
}