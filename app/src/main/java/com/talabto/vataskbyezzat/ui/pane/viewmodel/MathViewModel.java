package com.talabto.vataskbyezzat.ui.pane.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.talabto.vataskbyezzat.data.dao.MathDao;

import com.talabto.vataskbyezzat.data.local.BaseDataBase;
import com.talabto.vataskbyezzat.data.local.BaseDataBase_Impl;
import com.talabto.vataskbyezzat.data.model.MathModel;

import java.util.List;

public class MathViewModel extends AndroidViewModel {
    private MathDao mathDao;
    private LiveData<List<MathModel>> allMathModel;


    public MathViewModel(@NonNull Application application) {
        super(application);
        BaseDataBase baseDataBase=BaseDataBase.getDatabase(application);
        mathDao=baseDataBase.getMathDao();

    }

    public void addEq(MathModel mathModel)
    {
        mathDao.addMathOp(mathModel);
    }
    public int updateEq(int id,String res)
    {
        return mathDao.updateMathOP(id,res);
    }

   public LiveData<List<MathModel>> getAllMathModel()
    {
        return mathDao.getAllOp();
    }
}
