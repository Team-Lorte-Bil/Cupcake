package web.widgets;

import domain.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class Navbar {
    private final HttpServletRequest request;
    
    public Navbar(HttpServletRequest request) {
        this.request = request;
    }
    
    private final List<Item> items = List.of(
            new Item("Opret konto", "/Register", true),
            new Item("Log ind", "/Login", true),
            new Item("Log ud", "/Logout", false)
    );
    
    public List<Item> getItems() {
        User curUser = (User) request.getSession().getAttribute("currentUser");
        
        if(curUser == null){
            List<Item> list = new ArrayList<>();
            for (Item x : items) {
                if (x.guestOnly) {
                    list.add(x);
                }
            }
            return list;
        } else if (curUser.isAdmin()){
            List<Item> list = new ArrayList<>(List.copyOf(items));
            list.add(new Item("Admin", "/Admin", false));
            return list;
        } else {
            List<Item> list = new ArrayList<>();
            list.add(new Item(curUser.getEmail(), "nolink", false));
            for (Item x : items) {
                if (!x.guestOnly) {
                    list.add(x);
                }
            }
            return list;
        }
    }
    
    public class Item {
        private final String name;
        private final String url;
        private final boolean guestOnly;
        
        public Item(String name, String url, boolean guestOnly) {
            this.name = name;
            this.url = url;
            this.guestOnly = guestOnly;
        }
        
        public String getUrl() {
            return url;
        }
        
        public String getName() {
            return name;
        }
        
        public boolean isActive() {
            return request.getRequestURI().endsWith(url);
        }
    }
}