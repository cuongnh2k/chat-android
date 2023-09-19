package online.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.regex.Pattern;

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
        mBinding.btnLogin.setEnabled(false);
        mBinding.loading.setVisibility(View.INVISIBLE);
        mBinding.btnLogin.setOnClickListener(view -> {
            if (Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find()
                    && Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find()) {
                viewModel.login(new WebView(this).getSettings().getUserAgentString(),
                        new LoginReq(mBinding.txtEmail.getText().toString(), mBinding.txtPassword.getText().toString()));
                mBinding.loading.setVisibility(View.VISIBLE);
            } else {
                if (mBinding.txtPassword.getText().toString().length() > 16)
                    mBinding.txtValidPassword.setText("Mật khẩu tối đa 16 ký tự");
                else if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find())
                    mBinding.txtValidPassword.setText("Mật khẩu yếu");
                else mBinding.txtValidPassword.setText("");
                if (!Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find())
                    mBinding.txtValidEmail.setText("Vui lòng nhập đúng định dạng email");
                else mBinding.txtValidEmail.setText("");
            }
        });
        mBinding.txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mBinding.txtPassword.getText().toString().length() > 16)
                    mBinding.txtValidPassword.setText("Mật khẩu tối đa 16 ký tự");
                else if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find())
                    mBinding.txtValidPassword.setText("Mật khẩu yếu");
                else mBinding.txtValidPassword.setText("");
                if (!Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find())
                    mBinding.txtValidEmail.setText("Vui lòng nhập đúng định dạng email");
                else mBinding.txtValidEmail.setText("");
                mBinding.btnLogin.setEnabled(Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find()
                        && Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find());
            }
        });
        mBinding.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mBinding.txtPassword.getText().toString().length() > 16)
                    mBinding.txtValidPassword.setText("Mật khẩu tối đa 16 ký tự");
                else if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find())
                    mBinding.txtValidPassword.setText("Mật khẩu yếu");
                else mBinding.txtValidPassword.setText("");
                if (!Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find())
                    mBinding.txtValidEmail.setText("Vui lòng nhập đúng định dạng email");
                else mBinding.txtValidEmail.setText("");
                mBinding.btnLogin.setEnabled(Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find()
                        && Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find());
            }
        });
    }

    private void observe() {
        viewModel.getErrorMessageLiveData().observe(this, errorMessage -> {
            Log.d(TAG, "observe: errorMessage = " + errorMessage);
            mBinding.loading.setVisibility(View.INVISIBLE);
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        viewModel.getLiveData().observe(this, response -> {
            Log.d(TAG, "observe: userResponse = " + response);
            mBinding.loading.setVisibility(View.INVISIBLE);
            this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                    .edit()
                    .putString("accessToken", response.getAccessToken())
                    .putString("refreshToken", response.getRefreshToken())
                    .apply();

            Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Timber.d(intent.getAction());
    }
}