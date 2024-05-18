package com.example.appthibanglaixe.Activity;
import com.bumptech.glide.Glide;
import com.example.appthibanglaixe.R;
import com.example.appthibanglaixe.entity.modify;
import com.example.appthibanglaixe.model.CusDes;

import android.app.Activity;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReadActivity extends AppCompatActivity {
    private int idBo;

    private ImageView imageViewComic;
    private TextView textViewTitle, textViewAuthor, textViewPublicationDate, textViewDescription, textViewContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sat_hoach);

        idBo = getIntent().getIntExtra("idBo", -1);
        anhXa();
        CusDes cus = modify.getDesId(ReadActivity.this, idBo);



        textViewTitle.setText(cus.getTitle());
        textViewAuthor.setText(cus.getAuthor());
        textViewPublicationDate.setText(cus.getPublicationDate());
        textViewDescription.setText(cus.getDescription());
        textViewContent.setText(cus.getContent());
        String imageUrl = cus.getImage();
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.icon)
                .into(imageViewComic);
    }

    private void anhXa() {
        imageViewComic = findViewById(R.id.imageView_comic);
        textViewTitle = findViewById(R.id.textView_title);
        textViewAuthor = findViewById(R.id.textView_author);
        textViewPublicationDate = findViewById(R.id.textView_publication_date);
        textViewDescription = findViewById(R.id.textView_description);
        textViewContent = findViewById(R.id.textView_content);
    }

}
