package pe.smartpass.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.smartpass.dao.CatalogoDAO;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {

    private final CatalogoDAO catalogoDAO;

    public CatalogoController(CatalogoDAO catalogoDAO) {
        this.catalogoDAO = catalogoDAO;
    }

    @GetMapping("/unidades")
    public List<Map<String, Object>> unidades() {
        return catalogoDAO.listarUnidades();
    }

    @GetMapping("/rutas")
    public List<Map<String, Object>> rutas() {
        return catalogoDAO.listarRutas();
    }
}
