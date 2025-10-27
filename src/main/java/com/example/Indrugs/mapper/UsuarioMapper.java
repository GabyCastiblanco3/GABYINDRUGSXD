package com.example.Indrugs.mapper;

import com.example.Indrugs.DTO.Usuario.UsuarioCreateDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioUpdateDTO;
import com.example.Indrugs.entities.Usuario;

public class UsuarioMapper {

    // Mapear a dto
    public static UsuarioDTO mapToDto(Usuario usuario){
        //Validacion
        if (usuario == null){
            return null;
        }

        //proceso de mapeo
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setTipoDoc(usuario.getTipoDoc());
        dto.setNumDoc(usuario.getNumDoc());
        dto.setCorreo(usuario.getCorreo());
        dto.setDireccion(usuario.getDireccion());
        dto.setNombreRol(usuario.getRol().getNombreRol());
        dto.setEstado(usuario.getEstado());
        dto.setTelefono(usuario.getTelefono());
         //devuelve el dto creado
        return dto;
    }
    //Mapear crear dto a entitie
    public static Usuario mapNewToEntitie(UsuarioCreateDTO usuarioCreateDTO){
        //validacion
        if (usuarioCreateDTO == null){
            return null;
        }

        //proceso
        Usuario u = new Usuario();
        u.setNombre(usuarioCreateDTO.getNombre());
        u.setTipoDoc(usuarioCreateDTO.getTipoDoc());
        u.setNumDoc(usuarioCreateDTO.getNumDoc());
        u.setDireccion(usuarioCreateDTO.getDireccion());
        u.setTelefono(usuarioCreateDTO.getTelefono());
        u.setCorreo(usuarioCreateDTO.getCorreo());
        u.setPassword(usuarioCreateDTO.getPassword());

        //return new
        return u;
    }

    //mapear update

    public static void mapUpdateTo(Usuario u, UsuarioUpdateDTO usuarioUpdateDTO){

        //Validacion
        if (usuarioUpdateDTO == null)return;

        //proceso
        u.setNombre(usuarioUpdateDTO.getNombre());
        u.setDireccion(usuarioUpdateDTO.getDireccion());
        u.setTelefono(usuarioUpdateDTO.getTelefono());
        u.setCorreo(usuarioUpdateDTO.getCorreo());
        u.setEstado(usuarioUpdateDTO.getEstado());

    }

    public static UsuarioUpdateDTO toUpdateDTO(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) return null;

        UsuarioUpdateDTO updateDTO = new UsuarioUpdateDTO();
        updateDTO.setIdUsuario(usuarioDTO.getIdUsuario());
        updateDTO.setNombre(usuarioDTO.getNombre());
        updateDTO.setDireccion(usuarioDTO.getDireccion());
        updateDTO.setTelefono(usuarioDTO.getTelefono());
        updateDTO.setCorreo(usuarioDTO.getCorreo());
        updateDTO.setEstado(usuarioDTO.getEstado());

        return updateDTO;
    }
}
