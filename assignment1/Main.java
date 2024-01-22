package assignment1;

import java.io.*;

class Main {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Run program as java -cp classes Main {testcases_path}");
            System.exit(0);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            if ((line = br.readLine()) != null) {
                int T = Integer.parseInt(line);

                for (int i = 0; i < T; i++) {
                    line = br.readLine();
                    String[] params = line.split(" ");
                    int t = Integer.parseInt(params[1]);

                    line = br.readLine();
                    String[] numbers = line.split(" ");
                    int[] A = new int[numbers.length];
                    for (int j = 0; j < numbers.length; j++) {
                        A[j] = Integer.parseInt(numbers[j]);
                        System.out.print(A[j] + " ");
                    }

                    BinarySearchVariant bsv = new BinarySearchVariant();
                    int idx = bsv.bsearchv(A, t);
                    System.out.println("t= " + t);
                    System.out.println(idx + " " + (idx > -1 ? A[idx] : ""));
                    System.out.println("----------------------------------------------------");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
