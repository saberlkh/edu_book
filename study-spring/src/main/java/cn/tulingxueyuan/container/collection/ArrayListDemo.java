package cn.tulingxueyuan.container.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayListDemo {

    public static void main(String[] args) {

        //ArrayList<String> list = new ArrayList();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList();
        list.add("fox");
        list.add("mark");
        list.add("周瑜");

        new Thread(()->list.add("诸葛")).start();

        for (String str : list) {
            System.out.println(str);
        }

    }
}
