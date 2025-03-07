package dev.homework.restclientapp.service;

import org.springframework.stereotype.Service;

@Service
public class ProvinceService {

    public String getProvinceKey(String provinceName) {
        return switch (provinceName) {
            case "DOLNOŚLĄSKIE" -> "02";
            case "KUJAWSKO-POMORSKIE" -> "04";
            case "LUBELSKIE" -> "06";
            case "LUBUSKIE" -> "08";
            case "ŁÓDZKIE" -> "10";
            case "MAŁOPOLSKIE" -> "12";
            case "MAZOWIECKIE" -> "14";
            case "OPOLSKIE" -> "16";
            case "PODKARPACKIE" -> "18";
            case "PODLASKIE" -> "20";
            case "POMORSKIE" -> "22";
            case "ŚLĄSKIE" -> "24";
            case "ŚWIĘTOKRZYSKIE" -> "26";
            case "WARMIŃSKO-MAZURSKIE" -> "28";
            case "WIELKOPOLSKIE" -> "30";
            case "ZACHODNIOPOMORSKIE" -> "32";
            default -> "XX";
        };
    }
}
