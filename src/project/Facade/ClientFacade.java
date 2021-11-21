package project.Facade;

import jdk.jshell.spi.ExecutionControl;
import project.DAO.CompaniesDAO;
import project.DAO.CouponsDAO;
import project.DAO.CustomerDAO;
import project.SQL.ConnectionPool;
import project.exceptions.CompanyException;
import project.exceptions.CustomerException;
import project.exceptions.UserException;
import project.util.ClientType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class ClientFacade {
    protected CompaniesDAO companiesDAO;
    protected CustomerDAO customerDAO;
    protected CouponsDAO couponsDAO;

    public abstract boolean login(String email,String password) throws UserException, CustomerException, CompanyException;


}

