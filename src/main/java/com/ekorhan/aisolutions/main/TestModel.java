package com.ekorhan.aisolutions.main;

import lombok.Data;

@Data
public class TestModel {
    private int age = 7777;

    {
        System.out.println("age: ");
        System.out.println("normal curly");
        System.out.println("age: "+ "\n");
    }

    static {
        System.out.println("age: ");
        System.out.println("normal curly");
        System.out.println("age: "+ "\n");
    }

    {
        System.out.println("age: " + age);
        System.out.println("normal curly");
        System.out.println("age: " + age + "\n");
    }

    public TestModel() {
        System.out.println("age: " + age);
        System.out.println("empty constructor");
        System.out.println("age: " + age + "\n");
    }

    public TestModel(int age) {
        System.out.println("full constructor::before prop set");
        this.age = age;
        System.out.println("full constructor::after prop set");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println("clone");
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
        super.finalize();
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("equals");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestModel testModel = (TestModel) o;

        return age == testModel.age;
    }

    @Override
    public int hashCode() {
        System.out.println("hashCode");
        return age;
    }

    public int getAge() {
        System.out.println("getter");
        return age;
    }

    public void setAge(int age) {
        System.out.println("setter");
        this.age = age;
    }
}
