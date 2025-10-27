package com.example.Indrugs.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ArchivosService {

    private final String BASE_UPLOAD_DIR = "uploads";

    public String guardarArchivo(MultipartFile archivo, String carpeta) throws IOException {
        if (archivo.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        // Validar tipo de archivo
        validarTipoArchivo(archivo, carpeta);

        // Obtener ruta absoluta del directorio de trabajo actual
        String directorioTrabajo = System.getProperty("user.dir");
        String rutaCompleta = directorioTrabajo + File.separator + BASE_UPLOAD_DIR + File.separator + carpeta;

        // Crear directorio específico usando Files.createDirectories()
        Path directorio = Paths.get(rutaCompleta);
        Files.createDirectories(directorio); // Crea toda la estructura de carpetas si no existe

        // Generar nombre único
        String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
        Path archivoDestino = directorio.resolve(nombreArchivo);

        // Guardar archivo usando Path
        Files.copy(archivo.getInputStream(), archivoDestino);

        // Retornar URL relativa para acceso web
        return "/uploads/" + carpeta + "/" + nombreArchivo;
    }

    public String guardarImagenMedicamento(MultipartFile imagen) throws IOException {
        return guardarArchivo(imagen, "medicamentos");
    }

    public String guardarFormulaMedica(MultipartFile pdf) throws IOException {
        return guardarArchivo(pdf, "formulas");
    }

    private void validarTipoArchivo(MultipartFile archivo, String carpeta) {
        String contentType = archivo.getContentType();

        switch (carpeta) {
            case "medicamentos":
                if (!contentType.startsWith("image/")) {
                    throw new RuntimeException("Solo se permiten imágenes para medicamentos");
                }
                break;
            case "formulas":
                if (!contentType.equals("application/pdf")) {
                    throw new RuntimeException("Solo se permiten archivos PDF para fórmulas");
                }
                break;
            default:
                throw new RuntimeException("Tipo de archivo no soportado");
        }
    }
}