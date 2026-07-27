package com.example.repository;
import java.util.List;

public interface IRepository<T> {

    public void add(T t);
    public List<T> getAll();
}
