/**
 * 
 */
package com.pagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pagination.bean.Pagination;
import com.pagination.bean.User;
import com.pagination.dao.UserDao;

/**
 * @author Prathap Puppala
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{

    private static final long serialVersionUID = 1L;
    private User user = new User();

    //Initializing Pagination with page size 10, and current page 1 
    private Pagination pagination = new Pagination(10, 1);
    
    private List<User> users = null;
    private Connection con = null;
    
    public String userPagination(){
        UserDao aDao = new UserDao();
        try{
            //Database connection
            con = aDao.getConnection();
            
            //Getting Total records count from database...
            pagination.setPreperties(aDao.getUserCount(con));
            
            //Getting users list from database
            users = aDao.getUsers(con,pagination);
            
            //Setting number of records in the particular page
            pagination.setPage_records(users.size());
            
        }catch (Exception e) {
            System.out.println("ERROR "+e.getMessage());
        }finally{
            try {if(con != null)con.close();} catch (SQLException e) {
                System.out.println("ERROR "+e.getMessage());
            }
        }
        return SUCCESS;
    }
    
    @Override
    public User getModel() {
        return user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

} 
