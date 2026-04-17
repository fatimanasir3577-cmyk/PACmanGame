import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class food
{
    private int x,y;
    private boolean eaten = false;

    public food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEaten() {
        return eaten;
    }
    public void consume(){
        this.eaten = true;
    }
}
class pacman
{
    int x , y ;
    private int Score = 0;

    public pacman(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
    public boolean move(int dx, int dy, Gameboard maze){
        int nextX = x + dx;
        int nextY = y + dy;

        System.out.println("Trying to move to: (" + nextX + "," + nextY + ")");

        if (!maze.wall(nextX, nextY)) {
            x = nextX;
            y = nextY;
            System.out.println("Moved!");
            return true;
        } else {
            System.out.println("!!! Caution !!!");
            System.out.println("Wall hit!");
            return false;
        }
    }
    public void addScore(int points){
        this.Score += points;
    }

    public int getScore() {
        return Score;
    }
}
class Ghost1
{
    int x,y ;
    private Random Rand = new Random();

    public Ghost1(int Startx, int Starty) {
        this.x = Startx;
        this.y = Starty;
    }
    public void update (Gameboard maze ) {
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        int[] move = directions[Rand.nextInt(4)];
        if (!maze.wall(x+move[0],y+move[1])){
            x += move[0];
            y += move[1];
        }
    }
}
class Ghost2
{
    int x,y ;
    private Random random = new Random();

    public Ghost2(int Startx, int Starty) {
        this.x = Startx;
        this.y = Starty;
    }
    public void update (Gameboard maze ) {
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        int[] move = directions[random.nextInt(4)];
        if (!maze.wall(x+move[0],y+move[1])){
            x += move[0];
            y += move[1];
        }
    }
}
class Gameboard
{
    private int[][] maze ={               // layout of maze where 1s are the walls of the maze
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1},
            {1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    ArrayList<food> pellets = new ArrayList<>();
    public Gameboard(){

        pellets.add(new food (1,1 ));
        pellets.add(new food (3,3));
        pellets.add(new food (5,5 ));
        pellets.add(new food (1,8 ));
        pellets.add(new food (5,10 ));
    }
    public boolean wall(int x, int y) {

        if (x < 0 || y < 0 || y >= maze.length || x >= maze[0].length) {
            return true;
        }

        return maze[y][x] == 1;
    }
    public void check_food(pacman p){
        for (food f : pellets ){
            if ( f.getX()== p.x && f.getY()== p.y ){
                if (!f.isEaten()){
                    f.consume();
                    p.addScore(10);
                }
            }
        }
    }
}
public class PACmenGAME
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Gameboard Board = new Gameboard();
        pacman Pacman = new pacman(3,3);
        Ghost1 G1 = new Ghost1(1,5);
        Ghost2 G2 = new Ghost2(10,1);
        boolean run = true;
        System.out.println("... Game instructions ...");
        System.out.println("USE --> \n ^ to move up \n v to move down \n > to move right \n < to move left ");
        System.out.println("\n YOU HAVE TO REACH THE END POINT OF THE MAZE TO EXIT THE GAME ");
        while (run){
            System.out.println("Enter your move ");
            String move = sc.nextLine();
            if (move.equals("^")){
                Pacman.move(0,-1,Board);
            } else if (move.equals("v")) {
                Pacman.move(0,1,Board);
            } else if (move.equals("<")) {
                Pacman.move(-1,0,Board);
            } else if (move.equals(">")) {
                Pacman.move(1,0,Board);
            }else{
                System.out.println("You Have pressed invalid key ");
                System.out.println("Please press the correct key ");
            }
            if (Pacman.x == 12 && Pacman.y == 5){
                System.out.println("CONGRATULATIONS *_*");
                System.out.println("You have passed the game ");
                break;
            }
            G1.update(Board);
            G2.update(Board);
            Board.check_food(Pacman);
            System.out.println("Pacman("+Pacman.x+":"+Pacman.y+")  your score is "+Pacman.getScore());
            System.out.println("Ghost 1 ("+ G1.x+":"+G1.y+")");
            System.out.println("Ghost 2 ("+ G2.x+":"+G2.y+")");

            if (Pacman.x == G1.x && Pacman.y == G1.y ){
                System.out.println(" GAME OVER v_v");
                System.out.println("Ghost caught you  ");
                run = false;
            }
            if (Pacman.x == G2.x && Pacman.y == G2.y ){
                System.out.println(" GAME OVER v_v");
                System.out.println("Ghost caught you  ");
                run = false ;
            }
        }
        System.out.println("Your total Score is "+ Pacman.getScore());

    }
}
