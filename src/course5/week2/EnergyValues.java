import java.io.IOException;
import java.util.Scanner;

class Equation {
    Equation(double a[][], double b[]) {
        this.a = a;
        this.b = b;
    }

    double a[][];
    double b[];
}

class Position {
    Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    int column;
    int row;
}

class EnergyValues {
    static Equation ReadEquation() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        double a[][] = new double[size][size];
        double b[] = new double[size];
        for (int row = 0; row < size; ++row) {
            for (int column = 0; column < size; ++column)
                a[row][column] = scanner.nextInt();
            b[row] = scanner.nextInt();
        }
        return new Equation(a, b);
    }

    static Position SelectPivotElement(double a[][], boolean used_rows[], boolean used_columns[]) {
        for (int row = 0; row < a.length; row++) {
            if (used_rows[row]) {
                continue;
            }

            for (int column = 0; column < a.length; column++) {
                if (used_columns[column]) {
                    continue;
                }
                if (a[row][column] == 0) {
                    continue;
                }
                return new Position(column, row);
            }
        }
        return null;
    }

    static void SwapLines(double a[][], double b[], boolean used_rows[], Position pivot_element) {
        int size = a.length;

        for (int column = 0; column < size; ++column) {
            double tmpa = a[pivot_element.column][column];
            a[pivot_element.column][column] = a[pivot_element.row][column];
            a[pivot_element.row][column] = tmpa;
        }

        double tmpb = b[pivot_element.column];
        b[pivot_element.column] = b[pivot_element.row];
        b[pivot_element.row] = tmpb;

        boolean tmpu = used_rows[pivot_element.column];
        used_rows[pivot_element.column] = used_rows[pivot_element.row];
        used_rows[pivot_element.row] = tmpu;

        pivot_element.row = pivot_element.column;
    }

    static void ProcessPivotElement(double a[][], double b[], Position pivot_element) {
        double pivot = a[pivot_element.row][pivot_element.column];
        //make pivot == 1
        if (pivot != 1) {
            for (int i = 0; i < a.length; i++) {
                a[pivot_element.row][i] = a[pivot_element.row][i] / pivot;
            }
            b[pivot_element.row] = b[pivot_element.row] / pivot;
        }

        for (int row = 0; row < a.length; row++) {
            if (row == pivot_element.row) {
                continue;
            }
            if (a[row][pivot_element.column] == 0) {
                continue;
            }
            double rowPivot = a[row][pivot_element.column];
            for (int column = 0; column < a.length; column++) {
                double zxc = rowPivot * a[pivot_element.row][column];
                a[row][column] = a[row][column] - zxc;
            }
            b[row] = b[row] - (rowPivot * b[pivot_element.row]);
        }
    }

    static void MarkPivotElementUsed(Position pivot_element, boolean used_rows[], boolean used_columns[]) {
        used_rows[pivot_element.row] = true;
        used_columns[pivot_element.column] = true;
    }

    static double[] SolveEquation(Equation equation) {
        double a[][] = equation.a;
        double b[] = equation.b;
        int size = a.length;

        boolean[] used_columns = new boolean[size];
        boolean[] used_rows = new boolean[size];

        for (int step = 0; step < size; ++step) {
            Position pivot_element = SelectPivotElement(a, used_rows, used_columns);
            SwapLines(a, b, used_rows, pivot_element);
            ProcessPivotElement(a, b, pivot_element);
            MarkPivotElementUsed(pivot_element, used_rows, used_columns);
        }

//        for (int i = 0; i < b.length; i++) {
//            for (int j = 0; j < a.length; j++) {
//                if (a[i][j] == 0) {
//                    continue;
//                }
//                b[i] = b[i] / a[i][j];
//            }
//        }
        return b;
    }

    static void PrintColumn(double column[]) {
        int size = column.length;
        for (int row = 0; row < size; ++row)
            System.out.printf("%.20f\n", column[row]);
    }

    public static void main(String[] args) throws IOException {
        Equation equation = ReadEquation();
        double[] solution = SolveEquation(equation);
        PrintColumn(solution);
    }
}