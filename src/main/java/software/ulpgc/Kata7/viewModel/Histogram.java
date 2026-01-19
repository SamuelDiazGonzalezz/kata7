package software.ulpgc.Kata7.viewModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Histogram implements Iterable<Integer> {
    private final Map<Integer, Integer> map;
    private final Map<String, String> labels;
    public Histogram(Map<String, String> labels) {
        this.labels = labels;
        this.map = new HashMap<>();
    }

    public void put(int bin) {
        map.put(bin, count(bin)+1);
    }

    public int count(int bin) {
        return map.getOrDefault(bin, 0);
    }

    @Override
    public Iterator<Integer> iterator() {
        return map.keySet().iterator();
    }

    public String title(){
        return labels.getOrDefault("title", "Histogram");
    }
    public String x(){
        return labels.getOrDefault("x", "");
    }
    public String y(){
        return labels.getOrDefault("y", "");
    }
    public String legend(){
        return labels.getOrDefault("legend", "");
    }
}
