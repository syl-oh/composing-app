package com.example.composingapp.utils.interfaces.componentdrawer;

public interface CompositeDrawer extends ComponentDrawer {
    /**
     * Adds a ComponentDrawer as one of the drawers to this CompositeDrawer
     * @param drawerComponent ComponentDrawer to add
     */
    void add(ComponentDrawer drawerComponent);

    /**
     * Removes a ComponentDrawer from one of the drawers of this CompositeDrawer
     * @param drawerComponent ComponentDrawer to remove
     */
    void remove(ComponentDrawer drawerComponent);
}
