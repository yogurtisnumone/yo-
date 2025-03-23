package com.kombat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinionPlacementRequest {
    private String playerName;
    private String minionType;
    private int row;
    private int col;
}
