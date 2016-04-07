package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String [] args){
        Schema schema  = new Schema(1,"com.lanou.mirror.greendao");
        Entity cache = schema.addEntity("Cache");
        cache.addIdProperty().primaryKey().autoincrement();
        cache.addStringProperty("city");
        cache.addStringProperty("price");
        cache.addStringProperty("url");
        cache.addStringProperty("description");
        cache.addStringProperty("brand");
        cache.addStringProperty("title");


        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
