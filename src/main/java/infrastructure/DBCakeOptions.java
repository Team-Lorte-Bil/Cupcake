package infrastructure;

import domain.items.Cake;
import domain.items.CakeOptions;
import domain.items.Option;

import java.sql.*;
import java.util.HashMap;

public class DBCakeOptions {
    
    private final Database db;
    
    public DBCakeOptions(Database db) {
        this.db = db;
    }


    public int getToppingIdFromName(String topping) {

        return 0;
    }

    public int getBottomIdFromName(String bottom) {
        return 0;

    }

    }

