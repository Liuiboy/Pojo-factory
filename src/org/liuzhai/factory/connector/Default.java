package org.liuzhai.factory.connector;

public class Default implements BooleanModel, IntModel, StringModel {
    @Override
    public boolean createBoolean() {
        return false;
    }

    @Override
    public int createInt() {
        return 0;
    }


    @Override
    public String createString() {
        return null;
    }
}
