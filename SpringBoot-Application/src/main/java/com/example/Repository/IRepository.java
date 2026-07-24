package com.example.Repository;
import java.util.List;
import com.example.Model.User;

public interface IRepository<T> {

    public void add(T t);
    public List<T> getAll();
}
