import auction.Auction;
import auction.Bid;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class Main {

    public static void main(String[] args) throws ClassNotFoundException {


        printFieldsModifiers(Bid.class.getDeclaredFields());
        System.out.println("--------------------------------");
        printFieldsModifiers(Auction.class.getDeclaredFields());
        System.out.println("--------------------------------");
        printFieldsModifiers(Bid.Builder.class.getDeclaredFields());

//        printClassModifiers(Bid.class);
//        printMethodModifiers(Bid.class.getDeclaredMethods());
//
//        System.out.println("--------------------------------");
//
//        printClassModifiers(Bid.Builder.class);
//        printMethodModifiers(Bid.Builder.class.getDeclaredMethods());
//
//        System.out.println("--------------------------------");
//
//
//        printClassModifiers(Auction.class);
//        printMethodModifiers(Auction.class.getDeclaredMethods());
//
//        System.out.println("--------------------------------");
//
//
//        printClassModifiers(Class.forName("auction.Bid$Builder$BidImpl"));
    }

    public static void printMethodModifiers(Method[] methods){

        for (Method method:methods){
            int modifiers = method.getModifiers();

            System.out.printf("%s() access modifier is %s%n",method.getName(), getAccessModifierName(modifiers));

            if (Modifier.isSynchronized(modifiers)){
                System.out.println("The method is synchronized");
            }else{
                System.out.println("The method is not synchronized");
            }
            System.out.println();
        }

    }


    public static void printFieldsModifiers(Field[] fields){
        for (Field field: fields){

            int modifier= field.getModifiers();

            System.out.println(String.format("Field \"%s\" access modifier is %s",field.getName(), getAccessModifierName(modifier)));

            if(Modifier.isVolatile(modifier)){
                System.out.println("The field is volatile");

            } else if (Modifier.isFinal(modifier)) {
                System.out.println("The field is final");
            }

            if(Modifier.isTransient(modifier)){
                System.out.println("The field is transient");
            }



        }
    }


    public static void printClassModifiers(Class<?> clazz) {

        int modifier = clazz.getModifiers();

        System.out.println(clazz.getSimpleName() + " is " + getAccessModifierName(modifier));

        if (Modifier.isAbstract(modifier)) {
            System.out.println("The class is abstract");
        }

        if (Modifier.isInterface(modifier)) {
            System.out.println("The class is an interface");
        }

        if (Modifier.isStatic(modifier)) {
            System.out.println("The class is static");
        }
    }


    private static String getAccessModifierName(int modifiers) {

        if (Modifier.isPublic(modifiers)) {
            return "public";
        } else if (Modifier.isPrivate(modifiers)) {
            return "private";
        } else if (Modifier.isProtected(modifiers)) {
            return "protected";
        } else {
            return "package-private";
        }
    }


    public static void runAuction() {
        Auction auction = new Auction();
        auction.startAuction();

        Bid bid1 = Bid.builder().setBidderName("Company1").setPrice(10).build();
        auction.addBid(bid1);

        Bid bid2 = Bid.builder().setBidderName("Company2").setPrice(12).build();
        auction.addBid(bid2);

        auction.stopAuction();

        System.out.println(auction.getAllBids());
        System.out.println("Highest bid :" + auction.getHighestBid().get());
    }
}
