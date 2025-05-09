package grepp.NBE5_6_2_Team03.api.controller.llm.dto;

public class PlaceDto {
    private String name;
    private String recommend;
    private String address;

    public PlaceDto() {}

    public PlaceDto(String name, String recommend, String address) {
        this.name = name;
        this.recommend = recommend;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public String getRecommend() {
        return recommend;
    }
    public String getAddress() {
        return address;
    }
}