package com.kuifir.nullp.returned;

public class UseCase {
    public static void main(String[] args) {
        FullName jack = new FullName("Jack", "", "Brown");
        FullName rose = new FullName("Rose", null, "Brown");
        FullName nova = new FullName("Nova", "Diane", "Brown");
        System.out.println(
                "Is Jack's middle name Diane? " +
                        hasMiddleName(jack, "Diane"));
        System.out.println(
                "Is Rose's middle name Diane? " +
                        hasMiddleName(rose, "Diane"));
        System.out.println(
                "Is Nova's middle name Diane? " +
                        hasMiddleName(nova, "Diane"));
    }

    private static boolean hasMiddleName(FullName fullName, String middleName) {
        return switch (fullName.middleName()) {
            case Returned.Undefined undefined -> false;
            case Returned.ReturnValue rv -> {
                String returnedMiddleName = (String) rv.returnValue();
                yield returnedMiddleName.equals(middleName);
            }
        };
    }
}
