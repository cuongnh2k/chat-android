package online.chat.ui;

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

import online.chat.databinding.ActivityRegisterBinding;
import online.chat.network.request.user.RegisterReq;
import online.chat.viewmodel.LoginViewModel;
import online.chat.viewmodel.RegisterViewModel;
import timber.log.Timber;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getName();
    ActivityRegisterBinding mBinding;
    RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        initView();
    }

    private void initView() {
        observe();
        mBinding.btnRegister.setEnabled(false);
        mBinding.loading.setVisibility(View.INVISIBLE);
        mBinding.btnRegister.setOnClickListener(view -> {
            if (Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find()
                    && Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find()
                    && mBinding.txtRepassword.getText().toString().equals(mBinding.txtPassword.getText().toString())) {
                viewModel.register(new WebView(this).getSettings().getUserAgentString(),
                        new RegisterReq(mBinding.txtEmail.getText().toString(),
                                mBinding.txtPassword.getText().toString(),
                                mBinding.txtFirstName.getText().toString(),
                                mBinding.txtLastName.getText().toString()));
                mBinding.loading.setVisibility(View.VISIBLE);
            } else validate();
        });
        mBinding.txtSignin.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
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
        mBinding.txtRepassword.addTextChangedListener(new TextWatcher() {
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

    private void observe() {
        viewModel.getErrorMessageLiveData().observe(this, errorMessage -> {
            Log.d(TAG, "observe: errorMessage = " + errorMessage);
            mBinding.loading.setVisibility(View.INVISIBLE);
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        viewModel.getLiveData().observe(this, response -> {
            Log.d(TAG, "observe: userResponse = " + response);
            mBinding.loading.setVisibility(View.INVISIBLE);
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    private void validate() {
        if (mBinding.txtPassword.getText().toString().length() > 16)
            mBinding.txtValidPassword.setText("Mật khẩu tối đa 16 ký tự");
        else if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find())
            mBinding.txtValidPassword.setText("Mật khẩu yếu");
        else mBinding.txtValidPassword.setText("");
        if (!mBinding.txtRepassword.getText().toString().equals(mBinding.txtPassword.getText().toString())) {
            mBinding.txtValidRepassword.setText("Mật khẩu không khớp");
        } else mBinding.txtValidRepassword.setText("");
        if (!Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find())
            mBinding.txtValidEmail.setText("Vui lòng nhập đúng định dạng email");
        else mBinding.txtValidEmail.setText("");
        mBinding.btnRegister.setEnabled(Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[\\w\\W]{8,16}$").matcher(mBinding.txtPassword.getText().toString()).find()
                && Pattern.compile("^\\w+@\\w+\\.(\\w{2,3})*$").matcher(mBinding.txtEmail.getText().toString()).find()
                && mBinding.txtRepassword.getText().toString().equals(mBinding.txtPassword.getText().toString()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Timber.d(intent.getAction());
    }
}