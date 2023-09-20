package online.chat.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;
import online.chat.R;
import online.chat.databinding.ActivityLoginBinding;
import online.chat.network.request.device.ActiveDeviceReq;
import online.chat.network.request.device.LoginReq;
import online.chat.network.response.device.LoginRes;
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

        if (!Objects.equals(this.getSharedPreferences("token", Context.MODE_PRIVATE).getString("accessToken", ""), ""))
            startActivity(new Intent(this, MainActivity.class));
        else initView();
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
            } else validate();
        });
        mBinding.txtSignUp.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));
        mBinding.txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validate();
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
                validate();
            }
        });
    }

    @SuppressLint("InflateParams")
    private void observe() {
        LoginRes loginRes = new LoginRes();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_active_device, null);
        EditText code = view.findViewById(R.id.txtCode);
        TextView validCode = view.findViewById(R.id.txtValidCode);
        Button send = view.findViewById(R.id.btnSend);
        send.setEnabled(false);
        ProgressBar load = view.findViewById(R.id.loading);
        load.setVisibility(View.INVISIBLE);
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Kích hoạt thiết bị")
                .setCancelable(false)
                .setView(view)
                .create();

        viewModel.loginFail().observe(this, errorMessage -> {
            Log.d(TAG, "observe: errorMessage = " + errorMessage);
            mBinding.loading.setVisibility(View.INVISIBLE);
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        viewModel.loginSuccess().observe(this, response -> {
            Log.d(TAG, "observe: LoginRes = " + response);
            mBinding.loading.setVisibility(View.INVISIBLE);
            loginRes.setAccessToken(response.getAccessToken());
            loginRes.setRefreshToken(response.getRefreshToken());
            code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!Pattern.compile("^\\d{6}\\-\\d+$").matcher(code.getText().toString()).find()) {
                        validCode.setText("^\\d{6}\\-\\d+$");
                        send.setEnabled(false);
                    } else {
                        validCode.setText("");
                        send.setEnabled(true);
                    }
                }
            });
            send.setOnClickListener(view1 -> {
                load.setVisibility(View.VISIBLE);
                viewModel.activeDevice(new WebView(this).getSettings().getUserAgentString(),
                        new ActiveDeviceReq(code.getText().toString()));
            });
            alert.show();
        });
        viewModel.activeDeviceFail().observe(this, errorMessage -> {
            Log.d(TAG, "observe: errorMessage = " + errorMessage);
            load.setVisibility(View.INVISIBLE);
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            alert.dismiss();
        });

        viewModel.activeDeviceSuccess().observe(this, response -> {
            Log.d(TAG, "observe: OK = " + response);
            load.setVisibility(View.INVISIBLE);
            this.getSharedPreferences("token", Context.MODE_PRIVATE)
                    .edit()
                    .putString("accessToken", loginRes.getAccessToken())
                    .putString("refreshToken", loginRes.getRefreshToken())
                    .apply();
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void validate() {
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Timber.d(intent.getAction());
    }
}