public class Assign03_02 {
    static boolean balencedq(String text) {
	//
	// There are only '(', ')', '[', ']', '{', and '}'
	// appearing in [text]. This method should return
	// true if and only if the parentheses/brackets/braces
	// in [text] are balenced.
	// Your solution must make proper use of MyStack!
	//
	MyStackList<Character> stack = new MyStackList<Character>();
        
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            if (c == '(' || c == '[' || c == '{')
                stack.push$exn(c);
            else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                
                Character top = stack.pop$exn();
              
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{')) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        // Please write some testing code for your 'balencedq'
        
        System.out.println("\"()[]{}\" -> " + balencedq("()[]{}"));   // true
        System.out.println("\"([{}])\" -> " + balencedq("([{}])"));   // true
        System.out.println("\"([)]\" -> " + balencedq("([)]"));       // false
        System.out.println("\"((()\" -> " + balencedq("((()"));       // false
    }
}
