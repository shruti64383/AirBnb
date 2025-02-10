package com.codingshuttle.projects.airBnbApp.service;

import com.codingshuttle.projects.airBnbApp.dto.HotelDto;

import java.util.List;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long hotelId);

    HotelDto updateHotelById(Long hotelId, HotelDto hotelDto);

    Void deleteHotelById(Long hotelId);

    Void activateHotel(Long hotelId);

    List<HotelDto> getAllHotel();
}
