package com.example.ashis.agricultureexpertsservice.Model;

public class Weather {

    String weatherCityName;
    String weatherStreetName;
    String weatherDate;
    String weatherTemperature;
    String weatherClouds;
    String weatherCloudType;
    String date;

    public Weather() {
    }

    public Weather(String weatherCityName, String weatherStreetName, String weatherDate, String weatherTemperature, String weatherClouds, String weatherCloudType, String date) {
        this.weatherCityName = weatherCityName;
        this.weatherStreetName = weatherStreetName;
        this.weatherDate = weatherDate;
        this.weatherTemperature = weatherTemperature;
        this.weatherClouds = weatherClouds;
        this.weatherCloudType = weatherCloudType;
        this.date = date;
    }

    public String getWeatherCityName() {
        return weatherCityName;
    }

    public void setWeatherCityName(String weatherCityName) {
        this.weatherCityName = weatherCityName;
    }

    public String getWeatherStreetName() {
        return weatherStreetName;
    }

    public void setWeatherStreetName(String weatherStreetName) {
        this.weatherStreetName = weatherStreetName;
    }

    public String getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    public String getWeatherTemperature() {
        return weatherTemperature;
    }

    public void setWeatherTemperature(String weatherTemperature) {
        this.weatherTemperature = weatherTemperature;
    }

    public String getWeatherClouds() {
        return weatherClouds;
    }

    public void setWeatherClouds(String weatherClouds) {
        this.weatherClouds = weatherClouds;
    }

    public String getWeatherCloudType() {
        return weatherCloudType;
    }

    public void setWeatherCloudType(String weatherCloudType) {
        this.weatherCloudType = weatherCloudType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
