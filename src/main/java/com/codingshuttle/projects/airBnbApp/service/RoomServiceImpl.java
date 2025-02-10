package com.codingshuttle.projects.airBnbApp.service;

import com.codingshuttle.projects.airBnbApp.dto.RoomDto;
import com.codingshuttle.projects.airBnbApp.entity.Hotel;
import com.codingshuttle.projects.airBnbApp.entity.Room;
import com.codingshuttle.projects.airBnbApp.exception.ResourceNotFoundException;
import com.codingshuttle.projects.airBnbApp.repository.HotelRepository;
import com.codingshuttle.projects.airBnbApp.repository.RoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(RoomDto roomDto, Long hotelId) {
        log.info("Creating Room with in Hotel with ID:{}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: "+hotelId));
        Room newRoom = modelMapper.map(roomDto, Room.class);
        newRoom.setHotel(hotel);
        Room createdRoom = roomRepository.save(newRoom);

        if(hotel.getActive()){
            inventoryService.initializeRoomForAYear(createdRoom);
        }

        return modelMapper.map(createdRoom, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomInHotel(Long hotelId) {
        log.info("Getting All Rooms in Hotel with ID:{}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        return hotel.getRoom()
                .stream()
                .map(element -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting Room with ID:{}", roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with ID: "+roomId));

        return modelMapper.map(room, RoomDto.class);
    }

    @Transactional
    @Override
    public Void deleteRoomById(Long roomId) {
        log.info("Deleting Room with ID:{}", roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: "+roomId));
        inventoryService.deleteFutureInventories(room);
        roomRepository.delete(room);
        return null;
    }
}
