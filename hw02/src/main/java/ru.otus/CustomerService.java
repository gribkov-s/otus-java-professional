package ru.otus;


import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private final TreeMap<Customer, String> customers = new TreeMap<Customer, String>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> first = customers.firstEntry();
        Customer keyCopy = new Customer(first.getKey());
        return Map.entry(keyCopy, first.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higher = customers.higherEntry(customer);
        if (higher != null) {
            Customer keyCopy = new Customer(higher.getKey());
            return Map.entry(keyCopy, higher.getValue());
        } else {
            return null;
        }
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
