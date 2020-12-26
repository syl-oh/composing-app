package com.example.composingapp.utils.interfaces;

import java.util.ArrayList;

public interface CompositeDrawer extends ComponentDrawer {
    void add(ComponentDrawer drawerComponent);
    void remove(ComponentDrawer drawerComponent);
}
