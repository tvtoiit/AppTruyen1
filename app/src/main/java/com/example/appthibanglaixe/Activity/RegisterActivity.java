package com.example.appthibanglaixe.Activity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername, editTextNewPassword;
    Button buttonRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhXa();

        // Thiết lập sự kiện click cho button Register
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ EditText
                String username = editTextUsername.getText().toString();
                String password = editTextNewPassword.getText().toString();

                //validate form
                // Kiểm tra tính hợp lệ của dữ liệu
                if (TextUtils.isEmpty(username)) {
                    editTextUsername.setError("Username không được để trống");
                    return;
                }

                // Kiểm tra mật khẩu cũ có đúng không
//                if (!isUserNameCorrect(username)) {
//                    editTextNewPassword.setError("Tồn tại mật tài khoản này, xin chọn tài khoản khác");
//                    return;
//                }

                if (TextUtils.isEmpty(password)) {
                    editTextNewPassword.setError("Password không được để trống");
                    return;
                }

                // Nếu dữ liệu hợp lệ, tiến hành đăng ký
                performRegister(username, password);
            }
        });


    }
    private boolean isUserNameCorrect(String userName) {
        if (modify.isUsernameExist(RegisterActivity.this, userName)) {
            return true;
        }
        return false;
    }


    /**
     * Hàm đăng kí thông tin người dùng
     *
     * @param username Tên đăng nhập của người dùng
     * @param password Mật khẩu của người dùng
     */
    private void performRegister(String username, String password) {
        modify.registerUser(RegisterActivity.this, username, password);
        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

        //Sau khi đăng kí thành công thì chuyển sang màn hình đăng nhập
         Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
         startActivity(intent);
        // Đóng màn hình đăng ký sau khi chuyển sang màn hình đăng nhập
         finish();
    }

    /**
     * Hàm thực hiện ánh xạ các thành phần giao diện
     */
    private void anhXa() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
    }
}
