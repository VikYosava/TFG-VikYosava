import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

public class MyChartMultiline extends JComponent {
        private Map <Integer, ChartLine> chartMatrix;
        //Clave: identificado de la especie
        // Chartline guarda el color de la linea de esa especie y los puntos
        
        private int steps; 
        private int maxValue;
        
        private Color lineColor;
        
        MyChartMultiline(int steps){
        	chartMatrix = new HashMap<>();
        	this.steps = steps;
        	this.maxValue = 0;
        	
        }
        public void updateList(int id, List<Integer> line){
            System.out.println("updateList()");
            ChartLine aux = chartMatrix.get(id);
            if(aux == null) {
            	float r = (float) Math.random();
                float g = (float) Math.random();
                float b = (float) Math.random();
            	
            	Color col = new Color(r, g, b);
                aux = new ChartLine(col, line);
            }else {
            	aux.setPoints(line);
            	
            }
            chartMatrix.put(id, aux);
            
            int temp = Collections.max(line);
            if(maxValue< temp)
        		maxValue = temp;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            System.out.println("paint()");                 
            for(ChartLine l : chartMatrix.values()) {             	
            	paintMe(g, l);            	
            }
        }
        
        private void paintMe(Graphics g, ChartLine line){
            Graphics2D graphics2d = (Graphics2D)g;
            graphics2d.setColor(line.getColor());
            
            int width = getWidth();
            int height = getHeight();
            
            float hDiv = (float)width/(float)(line.size()-1);
            float vDiv = (float)height/(float)maxValue;
            
            for(int i=0; i<line.size()-1; i++){
                
                int value1, value2;
                if(line.get(i)==null){
                    value1 = 0;
                }else{
                    value1 = line.get(i);
                }
                if(line.get(i+1)==null){
                    value2 = 0;
                }else{
                    value2 = line.get(i+1);
                }
                
                graphics2d.drawLine(
                        (int)(i*hDiv), 
                        height - ((int)(value1*vDiv)),
                        (int)((i+1)*hDiv), 
                        height - ((int)(value2*vDiv)));
            }
            
            graphics2d.drawRect(0, 0, width, height);
        }
        
    }