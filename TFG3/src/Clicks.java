import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class Clicks implements ViewerListener {
    protected boolean loop = true;

    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing");
        new Clicks();
    }

    public Clicks() {
        Graph graph = new SingleGraph("Clicks");
        Viewer viewer = graph.display();

        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);
    
        // Example graph nodes for demonstration
        Node nodeA = graph.addNode("A");
        nodeA.setAttribute("x", 0);
        nodeA.setAttribute("y", 0);
        
        Node nodeB = graph.addNode("B");
        nodeB.setAttribute("x", 100);
        nodeB.setAttribute("y", 100);

        while(loop) {
            fromViewer.pump();
        }
    }

    public void viewClosed(String id) {
        loop = false;
    }

    public void buttonPushed(String id) {
        System.out.println("Button pushed on node " + id);
    }

    public void buttonReleased(String id) {
        System.out.println("Button released on node " + id);
    }

    public void mouseOver(String id) {
        System.out.println("Mouse over node " + id);
    }

    public void mouseLeft(String id) {
        System.out.println("Mouse left node " + id);
    }
}
