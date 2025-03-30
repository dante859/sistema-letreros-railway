package com.letreros.apirest.apirestletreros.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.letreros.apirest.apirestletreros.Entities.Letrero;
import com.letreros.apirest.apirestletreros.Repository.LetreroRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/letrero")
@CrossOrigin(origins = "*")
public class LetreroController {

    @Autowired
    LetreroRepository letreroRepository;
    @Autowired
    Cloudinary cloudinary;

    // CREATE
    @GetMapping("/get")
    public List<Letrero> getAllLetreros() {
        return letreroRepository.findAll();
    }

    // -----------POST
    @PostMapping("/post")
    public ResponseEntity<?> crearLetrero(@RequestParam("cliente") String cliente,
            @RequestParam(value = "telefono", required = false) Integer telefono,
            @RequestParam(value = "apellido", required = false) String apellido,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaCaducada") LocalDate fechaCaducada) throws IOException {

        Letrero nuevoLetrero = new Letrero();
        nuevoLetrero.setCliente(cliente);
        nuevoLetrero.setTelefono(telefono);
        nuevoLetrero.setApellido(apellido);
        nuevoLetrero.setFechaInicio(fechaInicio);
        nuevoLetrero.setFechaCaducada(fechaCaducada);

        if (imagen != null && !imagen.isEmpty()) {
            Map<String, Object> opciones = new HashMap<>();
            opciones.put("folder", "PEPE");

            opciones.put("resource_type", "image");

            opciones.put("type", "upload");

            Map uploadResult = cloudinary.uploader().upload(imagen.getBytes(), opciones);
            String imagenURL = (String) uploadResult.get("url");
            nuevoLetrero.setImagen(imagenURL);

        } else {
            nuevoLetrero.setImagen(null);

        }
        Letrero letreroGuardado = letreroRepository.save(nuevoLetrero);

        return ResponseEntity.status(200).body(letreroGuardado);

    }

    // -----------PUT
    @PutMapping("put/{idLetrero}")
    public ResponseEntity<?> actualizarLetrero(@PathVariable Long idLetrero,
            @RequestParam("cliente") String cliente,
            @RequestParam(value = "telefono", required = false) Integer telefono,
            @RequestParam(value = "apellido", required = false) String apellido,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaCaducada") LocalDate fechaCaducada) throws IOException {

        Letrero letrero = letreroRepository.findById(idLetrero)
                .orElseThrow(() -> new RuntimeException("Id no encontrado"));

        letrero.setCliente(cliente);
        letrero.setTelefono(telefono);
        letrero.setApellido(apellido);
        letrero.setFechaInicio(fechaInicio);
        letrero.setFechaCaducada(fechaCaducada);

        if (imagen != null && !imagen.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(imagen.getBytes(), ObjectUtils.asMap(
                    "folder", "PEPE"

            ));
            String imageURL = (String) uploadResult.get("url");
            letrero.setImagen(imageURL);
        } else {
            letrero.setImagen(null);
        }
        Letrero letreroActualizado = letreroRepository.save(letrero);

        return ResponseEntity.status(200).body(letreroActualizado);
    }

    // -----------DELETE
    @DeleteMapping("/delete/{idLetrero}")
    public ResponseEntity<?> crearLetrero(@PathVariable Long idLetrero) {
        Letrero letrero = letreroRepository.findById(idLetrero)
                .orElseThrow(() -> new RuntimeException("El " + idLetrero + " no encontrado."));

        String imagenUrl = letrero.getImagen();
        if (imagenUrl != null && !imagenUrl.isEmpty()) {
            try {
                // Extraer el public_id de la imagen en Cloudinary
                String publicId = imagenUrl.substring(imagenUrl.lastIndexOf("/") + 1, imagenUrl.lastIndexOf("."));

                // Eliminar la imagen de Cloudinary
                Map result = cloudinary.uploader().destroy("PEPE/" + publicId,
                        ObjectUtils.asMap("resource_type", "image"));
                System.out.println("Imagen eliminada: " + result);

            } catch (Exception e) {
                System.err.println("Error eliminando la imagen de Cloudinary: " + e.getMessage());
            }
        }

        letreroRepository.delete(letrero);
        return ResponseEntity.status(200).body(1);
    }
}
