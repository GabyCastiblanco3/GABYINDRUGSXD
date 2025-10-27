//package com.example.Indrugs.controllers;
////
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
////import com.example.Indrugs.DAO.IUsuario;
////import com.example.Indrugs.entities.Usuarios_registro;
////import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.GetMapping;
////
//@Control
//public class usuariosController
//
//
//
//////    private IUsuario usuarioRepos;
//////
//////    public usuariosController(IUsuario usuarioRepos){
//////        this.usuarioRepos = usuarioRepos;
//////    }
//////
//////    public Long guardar(@RequestParam String NOMBRE_USUARIOS){
//////        Usuarios_registro usuario = new Usuarios_registro();
//////        usuario.setNOMBRE_USUARIOS("Pepe");
//////        usuario.setCORREO_USUARIOS("pepe@gmail.com");
//////
//////        usuarioRepos.save(usuario);
//////
//////        return usuario.getID_USUARIOS();
//////    }
//////    public String buscarPorId(@PathVariable(name = "ID_USUARIOS") Long ID_USUARIOS){
//////        return usuarioRepos.findById(ID_USUARIOS).getNOMBRE_USUARIOS();
//////}

//    @GetMapping({"/", "/index"})
//    public String mostrarInicio() {
//        return "index";
//    }
//
//    @GetMapping("/2.pagina_inicio_sesion")
//    public String mostrarLogin() {
//        return "pacientes/5.pagina_pqrs"; // sin .html, Thymeleaf lo asume
//    }
//
//    @GetMapping("/6.pagina_registrar_usuario")
//    public String mostrarRegistro() {
//        return "6.pagina_registrar_usuario"; // sin .html, Thymeleaf lo asume
//    }
//
//    @GetMapping("/7.pagina_sobre_nosotros")
//    public String mostrarNosotros() {
//        return "7.pagina_sobre_nosotros";
//    }
//
//    @GetMapping("/10.pagina_mapa_nav")
//    public String mostrarMapa() {
//        return "10.pagina_mapa_nav";
//    }
//    @GetMapping("/500")
//    public String mostrar500() {
//        return "500";
//    }@GetMapping("/404")
//    public String mostrar400() {
//        return "404";
//    }
//
//}
