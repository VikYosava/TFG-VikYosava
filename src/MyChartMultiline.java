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
            //System.out.println("updateList()");
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
            //System.out.println("paint()"); 
        	Graphics2D graphics2d = (Graphics2D)g;
        	
        	graphics2d.setColor(Color.DARK_GRAY);
        	graphics2d.fillRect(0, 0, getWidth(), getHeight());
        	
        	graphics2d.setColor(Color.LIGHT_GRAY);
        	int width = getWidth();
            int height = getHeight();
        	
        	graphics2d.drawLine(0, height-30, width, height-30);
            graphics2d.drawLine(50,0,50,height);
            
            graphics2d.drawString("Generaciones", width / 2, height - 5);
            graphics2d.drawString("Cantidad de Individuos", 10, 10);
            
            drawGrid(graphics2d);
            
            for(ChartLine l : chartMatrix.values()) {             	
            	paintMe(graphics2d, l);            	
            }
            
            graphics2d.setColor(Color.LIGHT_GRAY);
            float hDiv = (float) (width - 50) / (float) (steps - 1);
            for (int i = 0; i < steps; i++) {
                graphics2d.drawString(String.valueOf(i), (int) (i * hDiv) + 50, height - 15);
            }
            
            graphics2d.setColor(Color.LIGHT_GRAY);
            float vDiv = (float) (height - 50) / (float) maxValue;
            for (int i = 0; i <= maxValue; i += 10) {
                int yPosition = height - 30 - (int) (i * vDiv);
                graphics2d.drawString(String.valueOf(i), 10, yPosition);
            }
            
        }
        
        private void drawGrid(Graphics2D graphics2d) {
            graphics2d.setColor(Color.LIGHT_GRAY);
            int width = getWidth();
            int height = getHeight();
            
            // Dibuja líneas verticales
            float hDiv = (float) (width - 50) / (float) (steps - 1);
            for (int i = 0; i < steps; i++) {
                int xPosition = (int) (i * hDiv) + 50;
                graphics2d.drawLine(xPosition, 0, xPosition, height - 30);
            }

            // Dibuja líneas horizontales cada 50 unidades
            float vDiv = (float) (height - 50) / (float) maxValue;
            for (int i = 0; i <= maxValue; i += 10) {
                int yPosition = height - 30 - (int) (i * vDiv);
                graphics2d.drawLine(50, yPosition, width, yPosition);
            }
        }
        
        private void paintMe(Graphics g, ChartLine line){
            g.setColor(line.getColor());
            
            int width = getWidth()-50;
            int height = getHeight()-50;
            
            float hDiv = (float)width/(float)(line.size()-1);
            float vDiv = (float)height/(float)maxValue;
            
            for(int i=0; i<line.size()-1; i++){
            	
            	int value1 = line.get(i) != null ? line.get(i) : 0;
            	int value2 = line.get(i + 1) != null ? line.get(i + 1) : 0;
            	
            	g.drawLine(
            			(int) (i*hDiv)+50,
            			getHeight()-30-(int)(value1*vDiv),
            			(int)((i+1)*hDiv)+50,
            			getHeight()-30-(int)(value2*vDiv)
            		);
            	
                /*int value1, value2;
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
                
                g.drawLine(
                        (int)(i*hDiv), 
                        height - ((int)(value1*vDiv)),
                        (int)((i+1)*hDiv), 
                        height - ((int)(value2*vDiv)));
            }*/
            }
            g.drawRect(50, 0, getWidth() - 50, getHeight() - 30);
        }
        
    }