import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;

/**
 * cocaro
 */
public class cocaro extends JFrame implements MouseListener {
    public static void main(String[] args) {
        new cocaro();
    }

    int s = 30;
    int n = 20;
    int ox = 50;
    int oy = 50;
    boolean who = true;
    boolean owin = false;
    boolean xwin = false;
    List<Point> dadanh = new ArrayList<Point>();
    int[][] danho = new int[n][n];
    int[][] danhx = new int[n][n];
    int[][] matrano = new int[5][5];
    int[][] matranx = new int[5][5];

    public cocaro() {
        this.setTitle("Co Caro");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        this.addMouseListener(this);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                danho[i][j] = 0;
                danhx[i][j] = 0;
            }
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                matrano[i][j] = 0;
                matranx[i][j] = 0;
            }
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        for (int i = 0; i < n + 1; i++) {
            g.drawLine(ox, oy + i * s, ox + s * n, oy + i * s);
            g.drawLine(ox + i * s, oy, ox + i * s, oy + s * n);

        }
        for (int i = 0; i < dadanh.size(); i++) {
            String st = "o";
            Color c = Color.RED;
            if (i % 2 == 1) {
                st = "x";
                c = Color.BLUE;
            }
            int x = dadanh.get(i).x * s + ox + s / 4;
            int y = dadanh.get(i).y * s + oy + s / 2 + s / 4 + s / 20;
            g.setColor(c);
            g.setFont(new Font("Arial", Font.BOLD, s));
            g.drawString(st, x, y);
            if (owin == true) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 150));
                g.drawString("O win!!!", ox + s, (int) (n * s) / 2 + oy + s);
            } else if (xwin == true) {
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.BOLD, 150));
                g.drawString("X win!!!", ox + s, (int) (n * s) / 2 + oy + s);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (owin || xwin) {
            who = true;
            owin = false;
            xwin = false;
            dadanh.clear();
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    danho[i][j] = 0;
                    danhx[i][j] = 0;
                }
        }
        int x = e.getX();
        int y = e.getY();
        if (x < ox || x >= ox + s * n)
            return;
        if (y < oy || y >= oy + s * n)
            return;
        int ix = (int) (x - ox) / s;
        int iy = (int) (y - oy) / s;
        // them vao list da danh va 2 mang danh o va danh x
        for (Point p : dadanh) {
            if (p.x == ix && p.y == iy)
                return;
        }
        dadanh.add(new Point(ix, iy));
        if (who == true) {
            who = false;
            danho[iy][ix] = 1;
        } else {
            who = true;
            danhx[iy][ix] = 1;
        }
        thoatvonglap: for (int i = 0; i < n - 4; i++)
            for (int j = 0; j < n - 4; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        matrano[k][l] = danho[i + k][j + l];
                        matranx[k][l] = danhx[i + k][j + l];
                    }
                }
                int cheoo1 = 0;
                int cheoo2 = 0;
                int cheox1 = 0;
                int cheox2 = 0;
                for (int k = 0; k < 5; k++) {
                    // duyet ngang
                    if (matrano[k][0] == 1 && matrano[k][1] == 1 && matrano[k][2] == 1 && matrano[k][3] == 1
                            && matrano[k][4] == 1) {
                        owin = true;
                        break thoatvonglap;
                    }
                    if (matranx[k][0] == 1 && matranx[k][1] == 1 && matranx[k][2] == 1 && matranx[k][3] == 1
                            && matranx[k][4] == 1) {
                        xwin = true;
                        break thoatvonglap;
                    }
                    // duyet doc
                    if (matrano[0][k] == 1 && matrano[1][k] == 1 && matrano[2][k] == 1 && matrano[3][k] == 1
                            && matrano[4][k] == 1) {
                        owin = true;
                        break thoatvonglap;
                    }
                    if (matranx[0][k] == 1 && matranx[1][k] == 1 && matranx[2][k] == 1 && matranx[3][k] == 1
                            && matranx[4][k] == 1) {
                        xwin = true;
                        break thoatvonglap;
                    }
                    // tinh 2 duong cheo
                    cheoo1 += matrano[k][k];
                    cheox1 += matranx[k][k];
                    cheoo2 += matrano[4 - k][k];
                    cheox2 += matranx[4 - k][k];

                }
                // kiem tra 2 duong cheo
                if (cheoo1 == 5 || cheoo2 == 5) {
                    owin = true;
                    break thoatvonglap;
                }
                if (cheox1 == 5 || cheox2 == 5) {
                    xwin = true;
                    break thoatvonglap;
                }
            }

        repaint();

    };

    /**
     * Invoked when a mouse button has been pressed on a component.
     * 
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {

    };

    /**
     * Invoked when a mouse button has been released on a component.
     * 
     * @param e the event to be processed
     */
    public void mouseReleased(MouseEvent e) {

    };

    /**
     * Invoked when the mouse enters a component.
     * 
     * @param e the event to be processed
     */
    public void mouseEntered(MouseEvent e) {

    };

    /**
     * Invoked when the mouse exits a component.
     * 
     * @param e the event to be processed
     */
    public void mouseExited(MouseEvent e) {

    };
}