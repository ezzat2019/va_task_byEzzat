package com.talabto.vataskbyezzat.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.talabto.vataskbyezzat.data.model.MathModel;
import com.talabto.vataskbyezzat.ui.pane.MathActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MathOPService extends IntentService {


    public MathOPService() {
        super("MathOPService");
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        if (intent.getAction().equals("com.talabto.vataskbyezzat.OP_type")) {

            MathModel mathModel = new MathModel(intent.getIntExtra("id", 0),
                    intent.getStringExtra("n1"),
                    intent.getStringExtra("n2"),
                    intent.getStringExtra("result")
                    , intent.getStringExtra("time"),
                    intent.getStringExtra("op"),
                    intent.getStringExtra("date"),
                    intent.getBooleanExtra("is", true)
            );



            double num1 = Double.parseDouble(mathModel.getNum1());
            double num2 = Double.parseDouble(mathModel.getNum2());
            String op = mathModel.getOp();
            Double res = 0.0;

            Log.d("eeeeeeeeeeee", "on op "+op);
            Log.d("eeeeeeeeeeee", "on op2 "+op.equals("add"));
            Log.d("eeeeeeeeeeee", "on op2 "+op.equals("sub"));
            Log.d("eeeeeeeeeeee", "on op2 "+op.equals("mul"));
            Log.d("eeeeeeeeeeee", "on op2 "+op.equals("div"));



            if (op.equals("add")) {

                res = num1 + num2;

            } else if (op.equals("sub")) {
                res = num1 - num2;
            } else if (op.equals("mul")) {
                res = num1 * num2;

            } else if (op.equals("div")) {
                if (num2 > 0) {
                    res = num1 / num2;
                } else {
                    res = -1.0;
                }


            }

            mathModel.setIs_pending(false);
            mathModel.setResult(res.toString());
            Intent intent2 = new Intent(getApplicationContext(), MathActivity.class);
            intent2.putExtra("new", mathModel);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);


            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


}