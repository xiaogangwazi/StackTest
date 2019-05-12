package stack;

public class StackTest {
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push("ni");
        stack.push("hao");
        stack.push("a");
        stack.show();
        experssion("11+2*3-    6");
       System.out.println( bracketMatch("1+   [2+({4-8)}]"));
    }

    /**
     * 计算表达式，只有整数的加减乘除的简单计算，不包括有括号,
     * @param experssion
     */
    public  static void experssion(String experssion){
        Stack number = new Stack();
        Stack symbol = new Stack();
        char[] chars = experssion.toCharArray();
      int i=0;
      while(i<chars.length){
          if(isnumber(chars[i])){//是数字，压入数字栈中
              int temp=(chars[i++]-48);
              while(i<chars.length&&isnumber(chars[i])){
                  temp=temp*10+chars[i++]-48;
              }
              number.push(Integer.toString(temp));
          }else if(chars[i]==' '){
              i++;
          }
          else{//是符号
              if(symbol.point==0||level(chars[i])>level(symbol.gethead().charAt(0))){//首次压入符号或者亚茹的符号比栈顶符号的优先级高
                  symbol.push(Character.toString(chars[i++]));
              }else{//压入的不是第一个符号并且亚茹的符号优先级比栈顶符号的优先级低或者相等
                  while(symbol.point>0) {

                      int calculate = calculate(number,symbol);
                      number.push(Integer.toString(calculate));
                  }
                  symbol.push(Character.toString(chars[i++]));
              }
          }

      }

        int calculate = calculate(number,symbol);
        System.out.println(calculate);

    }

    public  static int calculate(Stack number,Stack symbol){
        int num1 = Integer.parseInt(number.gethead());
        number.pop();
        int num2 = Integer.parseInt(number.gethead());

        number.pop();
        char  sy= symbol.gethead().charAt(0);
        symbol.pop();
       if(sy=='+'){
           return num1+num2;
       }else if (sy=='-'){
           return num2-num1;
       }else if(sy=='*'){
           return num1*num2;

       }else{
           return num2/num1;
       }

    }

    /**
     * 判断是否是数字
     * @param c
     * @return
     */
    public static boolean isnumber(char c){
             return c>'0'&&c<'9'?true:false;
    }
    public  static int level(char a){

        switch (a){
            case ('+'): return 1;
            case ('-'): return 1;
            case ('*'): return 2;
            case ('/'): return 2;
            default:return 0;
        }

    }




    //---------------------------------------------------------------------------------------------------

    /**
     * 括号匹配
     * 遍历遇到左边符号进栈，遇到右边符号的时候和栈顶元素匹配，匹配成功栈顶元素出栈
     * 当遍历结束的时候栈中没有元素了说明括号匹配正确
     */

    public static  boolean bracketMatch(String experssion){
        char[] chars = experssion.toCharArray();
        Stack stack = new Stack();
        int i=0;
        while(i<chars.length){
            if(leftBracket(chars[i])==0&&rightBracket(chars[i])==0){
                i++;
            }
            else
                if(leftBracket(chars[i])>0){
                    if(stack.point>0&&leftBracket(stack.gethead().charAt(0))<leftBracket(chars[i])){//如果插入的左符号比栈中的做符号等级高，匹配失败
                       return false;
                    }
                    stack.push(Character.toString(chars[i++]));
                }else if (rightBracket(chars[i])>0){
                    char top = stack.gethead().charAt(0);
                        if(match(top,chars[i++])){
                            stack.pop();
                        }else {
                            return false;
                        }
                }
        }
        if(stack.point==0){
            return true;
        }
        else {
            return false;
        }
    }
    /*
    判断是否是左边符号,并返回等级
     */
    public static int leftBracket(char l){
        switch (l){
            case ('('):return 1;
            case ('{'):return  2;
            case('['):return  3;
            default:return 0;
        }
    }
    /*
    判断是否是右边符号，并返回等级
     */
    public static int rightBracket(char l){
        switch (l){
            case (')'):return 1;
            case ('}'):return  2;
            case(']'):return  3;
            default:return 0;
        }
    }
    /*
    判断符号是否匹配
     */
    public static boolean match(char l,char r){
        if(l=='('&&r==')'){
            return true;
        }else if(l=='{'&&r=='}'){
            return true;

        }else if(l=='['&&r==']'){
            return true;
        }
        return false;

    }



    static class Stack{
        private String[] stack;//栈数组
        private int count;//栈的元素个数
        private int bounds;//栈的内存大小
        private int point;//栈顶指针
        private final int  DEFAULT_SIZE=16;//默认的栈内存大小
         public  Stack(){
                stack=new String[DEFAULT_SIZE];
                count=0;
                point=0;
                bounds=DEFAULT_SIZE;
        }
        public void Stack(int bounds){
            stack=new String[bounds];
            count=0;
            point=0;
            this.bounds=bounds;
        }

        /**
         * 出栈操作
         */
        public String pop(){
               count--;
               return stack[--point];

        }

        /**
         * 进栈操作
         * @param value
         * @return
         */
        public int push(String value){

            if(count>=bounds){
                throw  new IndexOutOfBoundsException("栈内存已满");
            }
            else{
                stack[point++]=value;
                count++;
                return 1;
            }
        }

        /**
         * 展示栈元素
         */
        public void show(){
            for(int i=point-1;i>=0;i--){
                System.out.println(stack[i]);
            }
        }
        public String gethead(){
            return  stack[point-1];
        }
    }



}
