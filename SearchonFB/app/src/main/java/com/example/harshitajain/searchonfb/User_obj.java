package com.example.harshitajain.searchonfb;

/**
 * Created by harshitajain on 4/18/17.
 */

public class User_obj {
        
        private String id;
        private String name;
        private String pic_url;
        //private double price;

        public User_obj() {
            super();
        }

        public User_obj(String id, String name, String pic_url) {
            super();
            this.id = id;
            this.name = name;
            this.pic_url = pic_url;
            //this.price = price;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getpic_url() {
            return pic_url;
        }

        public void setpic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        //@Override
//        public int hashCode() {
//            final int prime = 31;
//            int result = 1;
//            result = prime * result + id;
//            return result;
//        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            User_obj other = (User_obj) obj;
            if (this.id.equals(other.id))
                return true;
            return false;
        }

        @Override
        public String toString() {
            return "User_obj [id=" + id + ", name=" + name + ", pic_url="
                    + pic_url  + "]";
        }
    }

