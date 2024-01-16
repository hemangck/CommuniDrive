package com.example.communidrive;

public class Users {
        String name;
        int age;
        String gender;
        String city;
        String phone;
        String email;
        String pwd;
        String cPwd;

        public Users(String name, int age, String gender, String city, String phone, String email, String cPwd) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.city = city;
            this.phone = phone;
            this.email = email;
            this.cPwd = cPwd;
        }

    public Users() {
    }

    public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public String getCity() {
            return city;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getPwd() {
            return pwd;
        }

        public String getcPwd() {
            return cPwd;
        }
    }
