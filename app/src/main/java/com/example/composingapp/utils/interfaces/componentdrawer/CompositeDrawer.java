package com.example.composingapp.utils.interfaces.componentdrawer;

public interface CompositeDrawer extends ComponentDrawer {
    void add(ComponentDrawer drawerComponent);
    void remove(ComponentDrawer drawerComponent);
}
