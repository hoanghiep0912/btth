package com.example.btandoird;


class Contact {
    private String name, phone, email, address, position, unit;

    public Contact(String name, String phone, String email, String address, String position, String unit) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.position = position;
        this.unit = unit;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPosition() { return position; }
    public String getUnit() { return unit; }
}
