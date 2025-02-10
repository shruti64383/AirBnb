package com.codingshuttle.projects.airBnbApp.controller;

import com.codingshuttle.projects.airBnbApp.dto.RoomDto;
import com.codingshuttle.projects.airBnbApp.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/room")
@Slf4j
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/{hotelId}")
    public ResponseEntity<RoomDto> createNewRoom(@RequestBody RoomDto roomDto, @PathVariable Long hotelId){
        RoomDto roomDto1 = roomService.createNewRoom(roomDto, hotelId);
        return new ResponseEntity<>(roomDto1, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<List<RoomDto>> getAllRoomInHotel(@PathVariable Long hotelId){
        List<RoomDto> rooms = roomService.getAllRoomInHotel(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.FOUND);
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId){
        RoomDto roomDto = roomService.getRoomById(roomId);
        return new ResponseEntity<>(roomDto, HttpStatus.FOUND);
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
