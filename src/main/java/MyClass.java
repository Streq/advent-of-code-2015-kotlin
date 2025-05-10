
public class MyClass {

    public static void main(String[] args){
        String myString = "hola";


        while (!myString.isEmpty()){
            myString = myString.substring(1);
            System.out.println(myString);
        }
    }

}
