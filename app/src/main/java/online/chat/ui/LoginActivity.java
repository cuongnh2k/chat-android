package online.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import online.chat.databinding.ActivityLoginBinding;
import online.chat.network.request.device.LoginReq;
import online.chat.viewmodel.LoginViewModel;
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
        viewModel.login(new WebView(this).getSettings().getUserAgentString(),
                new LoginReq("cuongnh2k@gmail.com", "12345aA@"));
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