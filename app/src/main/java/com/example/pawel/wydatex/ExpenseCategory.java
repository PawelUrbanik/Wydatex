package com.example.pawel.wydatex;

public enum ExpenseCategory {
    FOOD("Jedzenie"), ENTERTAINMENT("Rozrywka"), HYGIENE("Higienia"), OTHER("Inne");

    private String name;

    private ExpenseCategory(String name)
    {
        this.name=name;
    }

    public String getName()
    {
        return name;
    }

    public static int getId(String categoryName)
    {
        int counter = 0;
        for (ExpenseCategory category: values()) {
            if (category.name().equals(categoryName))
                return counter;

            counter++;
        }
        return  -1;
    }
}
