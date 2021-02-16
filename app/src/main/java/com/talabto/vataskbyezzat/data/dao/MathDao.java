package com.talabto.vataskbyezzat.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.talabto.vataskbyezzat.data.model.MathModel;

import java.util.List;

@Dao
public interface MathDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMathOp(MathModel mathModel);

    @Query("select * from math")
    LiveData<List<MathModel>> getAllOp();

    @Query("UPDATE math SET  result=:res WHERE id = :id1")
    int updateMathOP(int id1, String res);

}
