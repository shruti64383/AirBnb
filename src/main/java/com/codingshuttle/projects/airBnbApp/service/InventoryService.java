package com.codingshuttle.projects.airBnbApp.service;

import com.codingshuttle.projects.airBnbApp.entity.Room;

public interface InventoryService {

    Void initializeRoomForAYear(Room room);

    Void deleteAllInventories(Room room);
}
