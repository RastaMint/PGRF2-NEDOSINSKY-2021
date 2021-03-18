package utery_17_25_c04.model;

public class Element {

    private final TopologyType topologyType;
    private final int start; // kde v index bufferu začít indexovat
    private final int count; // kolik indexů od startu se má použít

    public Element(TopologyType topologyType, int start, int count) {
        this.topologyType = topologyType;
        this.start = start;
        this.count = count;
    }

    public TopologyType getTopologyType() {
        return topologyType;
    }

    public int getStart() {
        return start;
    }

    public int getCount() {
        return count;
    }
}
