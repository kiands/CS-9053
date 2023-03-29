package PartIII;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortFrequency {

    public static void sortByFrequency(ArrayList<Integer> ar) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (Integer num : ar) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        Collections.sort(ar, new Comparator<Integer>() {
            @Override
            public int compare(Integer num1, Integer num2) {
                int freq1 = frequencyMap.get(num1);
                int freq2 = frequencyMap.get(num2);

                if (freq1 != freq2) {
                    return freq1 - freq2;
                } else {
                    return num1 - num2;
                }
            }
        });
    }

    public static void main(String[] args) {
        ArrayList<Integer> ar = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            ar.add((int) (Math.random() * 10));
        }
        System.out.println(ar.toString());
        sortByFrequency(ar);
        System.out.println(ar.toString());
    }
}
