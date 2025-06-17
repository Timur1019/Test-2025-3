package org.com.model;

import java.util.*;

public class Tube {
    private final Deque<String> drops;
    private final int capacity;

    public Tube(int capacity, List<String> initial) {
        this.capacity = capacity;
        this.drops = new ArrayDeque<>(initial);
    }

    public boolean isEmpty() {
        return drops.isEmpty();
    }

    public boolean isFull() {
        return drops.size() == capacity;
    }

    public String peekTop() {
        return drops.peekLast();
    }

    public int countTopSameColor() {
        if (isEmpty()) return 0;
        String top = peekTop();
        return (int) drops.stream()
                .skip(drops.size() - Collections.frequency(new ArrayList<>(drops), top))
                .filter(c -> c.equals(top)).count();
    }


    public void pourIn(List<String> liquids) {
        for (String l : liquids) {
            if (drops.size() < capacity) {
                drops.addLast(l);
            }
        }
    }

    @Override
    public String toString() {
        return new ArrayList<>(drops).toString();
    }

    public int getFreeSpace() {
        return capacity - drops.size();
    }

    public List<String> getDrops() {
        return new ArrayList<>(drops);
    }

    public boolean isSingleColorFilled() {
        if (isEmpty()) return true;
        if (drops.size() != capacity) return false;
        String first = drops.peekFirst();
        return drops.stream().allMatch(d -> d.equals(first));
    }

    public Tube cloneTube() {
        return new Tube(capacity, new ArrayList<>(drops));
    }

    public List<String> removeTop(String color, int count) {
        List<String> result = new ArrayList<>();
        while (!drops.isEmpty() && drops.peekLast().equals(color) && result.size() < count) {
            result.add(drops.removeLast());
        }
        return result;
    }

}
