package com.example.appthibanglaixe.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.example.appthibanglaixe.Adapter.ComicAdapter;
import com.example.appthibanglaixe.R;
import com.example.appthibanglaixe.data.sqDuLieu;
import com.example.appthibanglaixe.model.Comic;
import java.util.ArrayList;
import com.example.appthibanglaixe.entity.modify;

public class Tab_Test_Fragment extends Fragment {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Comic> comicList;
    ComicAdapter comicAdapter;
    sqDuLieu data;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Tab_Test_Fragment() {
        // Required empty public constructor
    }

    public static Tab_Test_Fragment newInstance(String param1, String param2) {
        Tab_Test_Fragment fragment = new Tab_Test_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab__test_, container, false);
        toolbar = view.findViewById(R.id.ftt_toobar_thisathoach);
        recyclerView = view.findViewById(R.id.ftt_resview);

        data = new sqDuLieu(getActivity());

        setupToolbar();

        comicList = modify.getAllLoaiTruyen(getActivity());
        comicAdapter = new ComicAdapter(getActivity(), comicList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(comicAdapter);

        // Thiết lập sự kiện click cho adapter
        comicAdapter.setOnItemClickListener(new ComicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Comic comic) {
                int index = comicList.indexOf(comic) + 1;
                Intent intent = new Intent(getActivity(), ReadActivity.class);
                intent.putExtra("idBo", index);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setupToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_luyentap, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ml_huongdan:
                showGuideDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showGuideDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_huongdanthi_dialog, null, false);

        TextView txt1 = view.findViewById(R.id.lhd_txt1);
        TextView txt2 = view.findViewById(R.id.lhd_txt2);
        TextView txt3 = view.findViewById(R.id.lhd_txt3);

        builder.setView(view);
        builder.setTitle("Hướng dẫn làm bài thi")
                .setNegativeButton("Đã hiểu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void senData(int index, int position) {
        Comic comic = comicList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(comic.getTitle());
        builder.setMessage(comic.getDescription());
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
