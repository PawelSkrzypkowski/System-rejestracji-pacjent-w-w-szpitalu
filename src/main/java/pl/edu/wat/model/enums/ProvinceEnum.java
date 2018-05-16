package pl.edu.wat.model.enums;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
public enum  ProvinceEnum {
    dolnoslaskie("dolnośląskie"), kujawsko_pomorskie("kujawsko-pomorskie"), lubelskie("lubelskie"),
    lubuskie("lubuskie"), lodzkie("łódzkie"), malopolskie("małopolskie"), mazowieckie("mazowieckie"),
    opolskie("opolskie"), podkarpackie("podkarpackie"), podlaskie("podlaskie"), pomorskie("pomorskie"),
    slaskie("śląskie"), swietokrzyskie("świętokrzyskie"), warminsko_mazurskie("warmińsko-mazurskie"),
    wielkopolskie("wielkopolskie"), zachodniopomorskie("zachodniopomorskie");

    String value;

    ProvinceEnum(String val) {
        value = val;
    }

    public static String getValue(ProvinceEnum provinceEnum) {
        return provinceEnum.value;
    }
}
