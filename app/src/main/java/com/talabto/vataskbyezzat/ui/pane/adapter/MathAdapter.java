package com.talabto.vataskbyezzat.ui.pane.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talabto.vataskbyezzat.R;
import com.talabto.vataskbyezzat.data.model.MathModel;

import java.util.ArrayList;
import java.util.List;

public class MathAdapter extends RecyclerView.Adapter<MathAdapter.MathVH> {
    private List<MathModel> list = new ArrayList<>();

    public void setList(List<MathModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MathVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.math_item, parent, false);
        return new MathVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MathVH holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MathVH extends RecyclerView.ViewHolder {
        private TextView txtn1, txtn2, txtRes, txtDate, txtPending;

        public MathVH(@NonNull View itemView) {
            super(itemView);

            init(itemView);
        }

        void bindData(MathModel mathModel) {
            txtn1.setText("num1 is " + mathModel.getNum1());
            txtn2.setText("num2  is " + mathModel.getNum2());
            txtDate.setText("Date is " + mathModel.getDate());
            txtPending.setText("Pending time : " + mathModel.getTime() + " s");
            if (mathModel.getResult().equals("")) {
                txtRes.setText("result is pending");

            } else {
                txtRes.setText("result is " + mathModel.getResult());
            }

        }

        private void init(View v) {
            txtn1 = v.findViewById(R.id.txt_n1);
            txtn2 = v.findViewById(R.id.txt2);
            txtRes = v.findViewById(R.id.txt_res);
            txtDate = v.findViewById(R.id.txt_date);
            txtPending = v.findViewById(R.id.txt_pending_time);

        }
    }
}
