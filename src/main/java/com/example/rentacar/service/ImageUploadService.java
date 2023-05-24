package com.example.rentacar.service;


import com.example.rentacar.model.Car;
import com.example.rentacar.model.Image;
import com.example.rentacar.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageUploadService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageUploadService.class);

    private final ImageRepository imageRepository;

    @Autowired
    public ImageUploadService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void uploadImageToCar(MultipartFile file, Car car) throws IOException {

        Image carProfileImage = imageRepository.findByCarId(car.getIdCar());
        if (!ObjectUtils.isEmpty(carProfileImage)) {
            imageRepository.delete(carProfileImage);
        }
        Image image = new Image();
        image.setCarId(car.getIdCar());
        image.setContent(compressBytes(file.getBytes()));
        image.setContentType(file.getContentType());
        image.setName(file.getOriginalFilename());
        imageRepository.save(image);
    }


    public Image getImageToCar(Car car) {
        Image image = imageRepository.findByCarId(car.getIdCar());
        if (!ObjectUtils.isEmpty(image)) {
            image.setContent(decompressBytes(image.getContent()));
        }
        return image;
    }


    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOG.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            LOG.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }
}
