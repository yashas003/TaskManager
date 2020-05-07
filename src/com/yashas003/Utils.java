package com.yashas003;

class Utils {

    static boolean validateName(String name) {
        if (name == null) return false;
        if (name.trim().equals("")) return false;
        if (name.split(" ").length > 1) return false;
        if (!Character.isLetter(name.charAt(0))) return false;
        for (int i = 0; i < name.length(); i++) {
            if (!(Character.isDigit(name.charAt(i)) || Character.isLetter(name.charAt(i)))) return false;
        }
        return true;
    }
}
