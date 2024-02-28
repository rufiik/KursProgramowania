public class test {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            try {
                int n = Integer.parseInt(args[i]);
                System.out.println(n + ": " + div(n));
            } catch (NumberFormatException ex) {
                System.out.println(args[i] + " nie jest liczbą całkowitą");
            }
        }
    }

    public static int div(int n) {
        int maxDivisor = 1;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) {
                maxDivisor = i;
            }
        }
        return maxDivisor;
    }
}