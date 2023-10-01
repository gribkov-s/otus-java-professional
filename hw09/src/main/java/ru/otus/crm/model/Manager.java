package ru.otus.crm.model;

import ru.otus.annotations.Column;
import ru.otus.annotations.Id;

public class Manager {


    @Column(order = 1) @Id
    private Long no;

    @Column(order = 2)
    private String label;

    @Column(order = 3)
    private String param1;

    public Manager() {}

    public Manager(String label) {
        this.no = null;
        this.label = label;
    }

    public Manager(String label, String param1) {
        this.no = null;
        this.label = label;
        this.param1 = param1;
    }

    public Manager(Long no, String label, String param1) {
        this.no = no;
        this.label = label;
        this.param1 = param1;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    @Override
    public String toString() {
        return "Manager{" + "no=" + no + ", label='" + label + '\'' + '}';
    }
}
