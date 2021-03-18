package utery_17_25_c04.renderer;

import transforms.Mat4;
import utery_17_25_c04.model.Element;
import utery_17_25_c04.model.Vertex;

import java.util.List;

public interface GPURenderer {

    void draw(List<Element> elements, List<Integer> ib, List<Vertex> vb);

    void clear();

    void setModel(Mat4 model);

    void setView(Mat4 view);

    void setProjection(Mat4 projection);

}
