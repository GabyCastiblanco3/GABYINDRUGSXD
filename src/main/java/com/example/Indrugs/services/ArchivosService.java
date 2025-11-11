package com.example.Indrugs.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ArchivosService {

    private final String BASE_UPLOAD_DIR = "uploads";

    /**
     * Guarda cualquier archivo en la carpeta específica.
     * @param archivo Archivo recibido desde el formulario
     * @param carpeta Carpeta donde se guardará el archivo
     * @return URL relativa del archivo guardado
     * @throws IOException
     */
    public String guardarArchivo(MultipartFile archivo, String carpeta) throws IOException {
        if (archivo.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        // Validar tipo de archivo según carpeta
        validarTipoArchivo(archivo, carpeta);

        // Ruta absoluta del directorio de trabajo
        String directorioTrabajo = System.getProperty("user.dir");
        String rutaCompleta = directorioTrabajo + File.separator + BASE_UPLOAD_DIR + File.separator + carpeta;

        // Crear directorio si no existe
        Path directorio = Paths.get(rutaCompleta);
        Files.createDirectories(directorio);

        // Generar nombre único y seguro
        String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
        Path archivoDestino = directorio.resolve(nombreArchivo);

        // Guardar archivo en el sistema
        Files.copy(archivo.getInputStream(), archivoDestino, StandardCopyOption.REPLACE_EXISTING);

        // Retornar URL relativa para acceder desde navegador
        return "/uploads/" + carpeta + "/" + nombreArchivo;
    }

    /**
     * Guarda imagen de medicamento en la carpeta "medicamentos"
     */
    public String guardarImagenMedicamento(MultipartFile imagen) throws IOException {
        return guardarArchivo(imagen, "medicamentos");
    }

    /**
     * Guarda archivo PDF de fórmula médica en la carpeta "formulas"
     */
    public String guardarFormulaMedica(MultipartFile pdf) throws IOException {
        return guardarArchivo(pdf, "formulas");
    }

    /**
     * Valida el tipo de archivo según la carpeta de destino
     */
    private void validarTipoArchivo(MultipartFile archivo, String carpeta) {
        String contentType = archivo.getContentType();

        switch (carpeta) {
            case "medicamentos":
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new RuntimeException("Solo se permiten imágenes para medicamentos");
                }
                break;
            case "formulas":
                if (contentType == null || !contentType.equals("application/pdf")) {
                    throw new RuntimeException("Solo se permiten archivos PDF para fórmulas");
                }
                break;
            default:
                throw new RuntimeException("Tipo de archivo no soportado");
        }
    }
    public void eliminarArchivo(String rutaArchivo) {
        try {
            Path path = Paths.get(rutaArchivo);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
