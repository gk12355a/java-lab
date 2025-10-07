package OOP;

class Animal {
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
    public void makeSound(String sound){
        System.out.println("Animal makes a sound: " + sound);
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}
class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}
public class PolymorphismDemo {
    public static void main(String[] args) {
        // nạp chồng
        Animal myAnimal = new Animal();
        myAnimal.makeSound();
        myAnimal.makeSound("Roar");

        // ghi đè

        Animal myDog = new Dog();
        Animal myCat = new Cat();
        myDog.makeSound(); // Outputs: Dog barks
        myCat.makeSound(); // Outputs: Cat meows

    }
}
