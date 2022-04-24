package com.company.Commands;

import java.io.Serializable;

public class clear implements Serializable {
    public String nameOfCommand;

    public clear(String nameOfCommand) {
        this.nameOfCommand = nameOfCommand;
    }
}
