package com.example.appthibanglaixe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.appthibanglaixe.entity.modify;

import com.example.appthibanglaixe.R;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextOldPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private Button buttonChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget);

        // Ánh xạ các thành phần trong layout
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonChangePassword = findViewById(R.id.btnForgetPassword);

        buttonChangePassword.setOnClickListener(view -> handleChangePassword());
    }

    // Phương thức xử lý sự kiện khi nhấn vào buttonChangePassword
    private void handleChangePassword() {
        // Lấy dữ liệu từ các trường nhập
        String userName = editTextUsername.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Kiểm tra mật khẩu cũ có đúng không
        if (!isUserNameCorrect(userName)) {
            Toast.makeText(this, "Tài khoản đã tồn tại, vui lòng điền khác tài khoản đã có này!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu mới và mật khẩu nhập lại có khớp nhau không
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu mới và nhập lại mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nếu tất cả điều kiện đều đúng, tiến hành thay đổi mật khẩu
        changePassword(userName, newPassword);
    }

    // Phương thức kiểm tra tài khoản có tồn tại
    private boolean isUserNameCorrect(String userName) {
        if (modify.isUsernameExist(ForgetActivity.this, userName)) {
            return true;
        }
        return false;
    }

    /**
     * Function đổi mật khẩu
     *
     * @param userName  Tên tài khoản người dùng
     * @param newPassword Mật khẩu của người dùng
     */
    private void changePassword(String userName, String newPassword) {
        if (modify.changePassword(ForgetActivity.this, userName, newPassword)) {
            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
        }
    }
}
