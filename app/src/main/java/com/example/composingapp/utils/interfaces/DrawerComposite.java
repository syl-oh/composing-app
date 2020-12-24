package com.example.composingapp.utils.interfaces;

import java.util.ArrayList;

public interface DrawerComposite extends DrawerComponent {
    void add(DrawerComponent drawerComponent);
    void remove(DrawerComponent drawerComponent);
    ArrayList<DrawerComponent> getDrawerComponents();
}
