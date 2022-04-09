package dao;

import entities.Admin;

public interface AdminDAO {
    public Admin getAdmin(String email, String password);
}
