package com.kuifir.normal.annotations.database;

/**
 * @author kuifir
 * @date 2023/6/13 0:21
 * @see DBTable
 * @see Constraints
 * @see SQLInteger
 * @see SQLString
 * @see Uniqueness
 */
@DBTable(name = "MEMBER")
public class Member {
    @SQLString(30)
    String firstName;
    @SQLString(50)
    String lastName;
    @SQLInteger
    Integer age;
    @SQLString(value = 30, constrains = @Constraints(primaryKey = true))
    String reference;
    static int memberCount;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return reference;
    }
}
