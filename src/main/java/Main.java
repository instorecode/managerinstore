
public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.a().b();
    }

    public Main a() {
        System.out.println("metodo a foi chamado");
        return this;
    }
    
    public Main b() {
        System.out.println("metodo b foi chamado");
        return this;
    }
}
