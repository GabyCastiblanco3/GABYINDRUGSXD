package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.DomicilioDTO;
import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.DTO.PedidoDTO; // nuevo DTO para los pedidos
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class DomiciliarioController {

    private final UsuarioService usuarioService;
    private final DomicilioService domicilioService;
    private final VehiculoService vehiculoService;
    private final OrdenService ordenService;
    private final PedidoServicelmpl pedidoService; // Nuevo servicio

    public DomiciliarioController(UsuarioService usuarioService,
                                  DomicilioService domicilioService,
                                  VehiculoService vehiculoService,
                                  OrdenService ordenService,
                                  PedidoServicelmpl pedidoService) {
        this.usuarioService = usuarioService;
        this.domicilioService = domicilioService;
        this.vehiculoService = vehiculoService;
        this.ordenService = ordenService;
        this.pedidoService = pedidoService;
    }

    // 📍 Página principal del domiciliario
    @GetMapping("/11.pagina_principal_domiciliario")
    public String mostrarPaginaDomiciliario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        Map<String, Object> dashboard = domicilioService.ObtenerResumen();
        model.addAttribute("totalDomiciliosActivos", dashboard.get("totalDomiciliosActivos"));
        model.addAttribute("domiciliosRecientes", dashboard.get("domiciliosRecientes"));

        List<OrdenDTO> ordenesRecientes = ordenService.ObtenerOrdenesRecientes();
        model.addAttribute("ordenesRecientes", ordenesRecientes);

        Map<String,Object> dashboardO = ordenService.ObtenerResumenOrden();
        model.addAttribute("cantidadOrdenes",dashboardO.get("totalOrdenesActivos"));

        return "domiciliario/11.pagina_principal_domiciliario";
    }

    // 📍 Tabla principal de Domicilios
    @GetMapping("/15.pagina_domicilio_domi")
    public String Mostrartabla(HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        List<DomicilioDTO> domicilio = domicilioService.read();
        model.addAttribute("domicilios", domicilio);
        return "domiciliario/15.pagina_domicilio_domi";
    }

    // 📍 Actualizar estado de un domicilio
    @GetMapping("/actualizar/domicilio/{idDomicilio}")
    public String cambiarEstado(@PathVariable Long idDomicilio) {
        domicilioService.actualizar(idDomicilio);
        return "redirect:/15.pagina_domicilio_domi";
    }

    @GetMapping("/24.pagina_pedidos_domiciliario")
    public String mostrarPedidos(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<PedidoDTO> pedidos = pedidoService.listarPedidos(); // ✅ llamada correcta
        model.addAttribute("pedidos", pedidos);
        return "domiciliario/24.pagina_pedidos_domiciliario";
    }


    // 🆕 📍 Cambiar estado del pedido
    @PostMapping("/actualizar/pedido/{idPedido}")
    public String actualizarEstadoPedido(@PathVariable Long idPedido, @RequestParam String estado) {
        pedidoService.actualizarEstado(idPedido, estado);
        return "redirect:/24.pagina_pedidos_domiciliario";
    }
}
