package org.dario;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {


        Movie movie= new Movie("Duune",2021,16.20,true,Category.ACTION);

        printDeclaredFieldsInfo(Movie.class,movie);
    }


    public static <T> void printDeclaredFieldsInfo(Class<? extends T> clazz, T instance) throws IllegalAccessException {
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(String.format("Field name: %s type : %s", field.getName(), field.getType().getName()));


            System.out.println(String.format("Is synthetic : %s",field.isSynthetic()));

            System.out.println(String.format("Field value is : %s",field.get(instance)));

            System.out.println();

        }
    }

    public static class Movie extends Product {


        public static final double MINUMUM_PRICE = 10.99;

        private boolean isReleased;
        private Category category;
        private double actualPrice;

        public Movie(String name, int year, double price, boolean isReleased, Category category) {
            super(name, year);

            this.isReleased = isReleased;
            this.category = category;
            this.actualPrice = Math.max(price, MINUMUM_PRICE);
        }

        public class MovieStats{
            private double timesWatched;

            public MovieStats(double timesWatched) {
                this.timesWatched = timesWatched;
            }

            public double getRevenue(){
                return timesWatched*actualPrice;
            }
        }
    }

    public static class Product {

        protected String name;
        protected int year;
        protected double actualPrice;

        public Product(String name, int year) {
            this.name = name;
            this.year = year;
        }
    }

    public enum Category {
        ADVENTURE,
        ACTION,
        COMEDY
    }


}