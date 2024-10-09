package api.kgu.nufarm.application.Item.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemCategory {

    SEEDS("모종"),
    NUTRITION("영양제"),
    SOIL("토양"),
    SET("세트");

    private final String displayName;
}
