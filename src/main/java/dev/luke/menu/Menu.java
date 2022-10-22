package dev.luke.menu;

import java.util.ArrayList;

public class Menu {
    private String menuCode;
    private String initialPrompt;
    private ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();

    public Menu(String menuCode, String initialPrompt) {
        this.menuCode = menuCode;
        this.initialPrompt = initialPrompt;
    }

    public void add(int number, String optionText, String action){
        MenuItem menuItem = new MenuItem(number, optionText, action);
        menuItemList.add(menuItem);
    }

    @Override
    public String toString() {
        String returnString = initialPrompt + "\n";
        for (MenuItem menuitem : menuItemList) {
            returnString += menuitem + "\n";
        }
        returnString += "Please enter the number: ";
        return returnString;
    }

    public String getMenuCode() {
        return menuCode;
    }
    public String getMenuCode(int item) {
        return menuItemList.get(item).getAction();
    }

    public String getInitialPrompt() {
        return initialPrompt;
    }

    public ArrayList<MenuItem> getMenuItemList() {
        return menuItemList;
    }
}
