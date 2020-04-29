import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    final int position;

    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;

    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> st = new Stack<>();
        int pos = 0;
        boolean success = true;
        for (int i = 0; i < text.length(); ++i) {
            char next = text.charAt(i);
            pos = i + 1;
            if (next == '(' || next == '[' || next == '{') {
                st.push(new Bracket(next, pos));
                continue;
            }

            if (next == ')' || next == ']' || next == '}') {
                if (st.empty()) {
                    success = false;
                    break;
                }
                Bracket top = st.pop();
                if (!top.match(next)) {
                    success = false;
                    break;
                }
            }
        }

        if (st.empty() && success) {
            System.out.println("Success");
        } else {
            if (success) {
                System.out.println(st.pop().position);
            } else {
                System.out.println(pos);
            }
        }

        // Printing answer, write your code here
    }
}
