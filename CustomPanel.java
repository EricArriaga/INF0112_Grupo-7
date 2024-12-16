import javax.swing.*;
import java.awt.*;


class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
    
            // Enable anti-aliasing for smooth lines
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
            // Set line color
            g2d.setColor(new Color(0x566246));
    
            // Set line thickness (4 times thicker, e.g., 8 pixels if the default is ~2)
            g2d.setStroke(new BasicStroke(8));
    
            // Outer square (perfect as requested)
            int outerMargin = 40; // Margin for the outer square
            g2d.drawRect(outerMargin, outerMargin, getWidth() - 2 * outerMargin, getHeight() - 2 * outerMargin);
    
            // Frame dimensions
            int frameWidth = getWidth();
            int frameHeight = getHeight();
    
            // Calculate even spacing
            int totalMargin = 180; // Total space between the outer and innermost square
            int spacing = totalMargin / 3; // Equal spacing between squares
    
            // First inner square (spaced evenly inside the outer square)
            int margin1 = outerMargin + spacing + 7;
            int innerSquare1Size = Math.min(frameWidth, frameHeight) - 2 * margin1;
            int innerSquare1X = (frameWidth - innerSquare1Size) / 2;
            int innerSquare1Y = (frameHeight - innerSquare1Size) / 2;
            g2d.drawRect(innerSquare1X, innerSquare1Y, innerSquare1Size, innerSquare1Size);
    
            // Second inner square (spaced evenly inside the first inner square)
            int margin2 = outerMargin + 2 * spacing + 20;
            int innerSquare2Size = Math.min(frameWidth, frameHeight) - 2 * margin2;
            int innerSquare2X = (frameWidth - innerSquare2Size) / 2;
            int innerSquare2Y = (frameHeight - innerSquare2Size) / 2;
            g2d.drawRect(innerSquare2X, innerSquare2Y, innerSquare2Size, innerSquare2Size);
    
            // Additional 4 lines
            int gap = 10; // Small gap between lines and the inner square
    
            // Vertical lines
            int verticalX = frameWidth / 2; // Centered in the frame
            g2d.drawLine(verticalX, outerMargin, verticalX, innerSquare2Y - gap); // Top segment
            g2d.drawLine(verticalX, innerSquare2Y + innerSquare2Size + gap, verticalX, frameHeight - outerMargin); // Bottom segment
    
            // Horizontal lines
            int horizontalY = frameHeight / 2; // Centered in the frame
            g2d.drawLine(outerMargin, horizontalY, innerSquare2X - gap, horizontalY); // Left segment
            g2d.drawLine(innerSquare2X + innerSquare2Size + gap, horizontalY, frameWidth - outerMargin, horizontalY); // Right segment
        }
    }