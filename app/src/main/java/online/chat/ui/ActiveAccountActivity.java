package online.chat.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import online.chat.databinding.ActivityActiveAccountBinding;

public class ActiveAccountActivity extends AppCompatActivity {
    ActivityActiveAccountBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityActiveAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
//        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//        String accessToken = this.getSharedPreferences("token", Context.MODE_PRIVATE).getString("accessToken", "");
//        if (accessToken != null && !accessToken.equals("")) {
//            startActivity(new Intent(this, MainActivity.class));
//        } else {
//            initView();
//        }
    }
}