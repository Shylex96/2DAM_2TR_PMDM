package com.example.myrecyclerview;

class Color
{
    private final String name;
    private final String hex;
    public Color(String name, String hex)
    {
        this.hex = hex;
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public String getHex()
    {
        return hex;
    }
}