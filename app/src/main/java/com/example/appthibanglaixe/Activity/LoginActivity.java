package com.example.appthibanglaixe.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appthibanglaixe.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appthibanglaixe.entity.modify;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        anhXa();

        modify.insertTestUser(LoginActivity.this);

        // Thiết lập sự kiện cho nút đăng nhập
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy username và password từ EditText
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Kiểm tra username và password
                if (modify.isValidLogin(LoginActivity.this ,username, password)) {
                    //Lưu trạng thái đăng nhập
                    SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", username);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, Tab_Test_Fragment.class);
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo lỗi nếu username hoặc password không hợp lệ
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Hàm gọi màn hình đăng kí
    public void openRegisterScreen(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //Hàm gọi chuyển sang màn hình đổi mật khẩu
    public void openForgotPasswordScreen(View view) {
        Intent intent = new Intent(this, ForgetActivity.class);
        startActivity(intent);
    }

    private void anhXa() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    // Phương thức để kiểm tra tính hợp lệ của username và password
    private boolean isValid(String username, String password) {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }
}
