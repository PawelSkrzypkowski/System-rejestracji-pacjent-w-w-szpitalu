package pl.edu.wat.model.enums;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
public enum JobEnum {
    DOCTOR("Lekarz"),NURSE("Pielęgniarka");

    String value;

    JobEnum(String val) {
        value = val;
    }

    public static String getValue(JobEnum jobEnum) {
        return jobEnum.value;
    }
}
