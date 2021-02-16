package com.talabto.vataskbyezzat.ui.pane;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.talabto.vataskbyezzat.R;
import com.talabto.vataskbyezzat.data.model.MathModel;
import com.talabto.vataskbyezzat.services.MathOPService;
import com.talabto.vataskbyezzat.services.RunAllTimeService;
import com.talabto.vataskbyezzat.ui.pane.adapter.MathAdapter;
import com.talabto.vataskbyezzat.ui.pane.viewmodel.MathViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MathActivity extends AppCompatActivity {

    boolean isOne = true;
    private RecyclerView recyclerView;
    private MathAdapter adapter;
    private List<MathModel> list = new ArrayList<>();
    private MathViewModel mathViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        if (getIntent().hasExtra("new")) {
            init();
            MathModel mathModel = getIntent().getParcelableExtra("new");
            Log.d("ffffffffffffffffffff", "onStartCommand: " + mathModel.toString());
            mathViewModel.updateEq(mathModel.getId(), mathModel.getResult());
            observeData();
            stopService(new Intent(getApplicationContext(), RunAllTimeService.class));

        } else if (!getIntent().hasExtra("model")) {
            Toast.makeText(this, "error no data try again", Toast.LENGTH_LONG).show();
            onBackPressed();

        } else {
            init();

            observeData1();

        }
    }

    private void addItem() {
        MathModel mathModel = getIntent().getParcelableExtra("model");
        mathModel.setId(list.size());
        mathViewModel.addEq(mathModel);
        makeMathQuestion(mathModel);

    }

    void makeMathQuestion(MathModel mathModel) {
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, MathOPService.class);
        i.putExtra("id", mathModel.getId());
        i.putExtra("n1", mathModel.getNum1());
        i.putExtra("n2", mathModel.getNum2());
        i.putExtra("date", mathModel.getDate());
        i.putExtra("time", mathModel.getTime());
        i.putExtra("result", mathModel.getResult());
        i.putExtra("is", mathModel.isIs_pending());
        i.putExtra("op", mathModel.getOp());


        i.setAction("com.talabto.vataskbyezzat.OP_type");


        Calendar cur_cal = Calendar.getInstance();
        cur_cal.setTimeInMillis(System.currentTimeMillis());
        cur_cal.add(Calendar.MILLISECOND, Integer.parseInt(mathModel.getTime()) * 1000);
        PendingIntent pi = PendingIntent.getService(this, 2, i, 0);

        mgr.set(AlarmManager.RTC_WAKEUP, cur_cal.getTimeInMillis(), pi);
    }

    private void observeData1() {

        mathViewModel.getAllMathModel().observe(this, new Observer<List<MathModel>>() {
            @Override
            public void onChanged(List<MathModel> mathModels) {

                list = mathModels;
                adapter.setList(list);
                if (isOne) {
                    addItem();
                    if (!list.isEmpty()) {
                        recyclerView.smoothScrollToPosition(list.size() - 1);
                    }

                    isOne = false;
                }

            }
        });

    }

    private void observeData() {

        mathViewModel.getAllMathModel().observe(this, new Observer<List<MathModel>>() {
            @Override
            public void onChanged(List<MathModel> mathModels) {

                list = mathModels;
                adapter.setList(list);

                if (!list.isEmpty()) {
                    recyclerView.smoothScrollToPosition(list.size() - 1);
                }
            }
        });

    }


    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MathAdapter();
        recyclerView.setAdapter(adapter);

        mathViewModel = ViewModelProviders.of(this).get(MathViewModel.class);

    }
}