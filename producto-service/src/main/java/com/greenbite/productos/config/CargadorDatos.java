package com.greenbite.productos.config;

import com.greenbite.productos.modelo.Producto;
import com.greenbite.productos.repositorio.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Carga datos de prueba al iniciar la aplicacion.
 * Representa el catalogo real de GreenBite con cajas de alimentos organicos.
 */
@Configuration
public class CargadorDatos {

    @Bean
    CommandLineRunner cargarDatosIniciales(ProductoRepository repositorio) {
        return args -> {
            if (repositorio.count() == 0) {
                repositorio.save(new Producto(
                    "Caja Verduras de Temporada",
                    "Seleccion semanal de verduras organicas certificadas de agricultores locales de la Region Metropolitana.",
                    12990.0, 20, "Verduras"
                ));
                repositorio.save(new Producto(
                    "Caja Frutas Organicas",
                    "Mix de frutas de estacion sin pesticidas. Incluye manzanas, peras, citricos y frutos del bosque segun disponibilidad.",
                    10990.0, 15, "Frutas"
                ));
                repositorio.save(new Producto(
                    "Caja Mixta Familiar",
                    "La opcion mas completa. Verduras, frutas y legumbres para una familia de 4 personas durante toda la semana.",
                    19990.0, 10, "Mixta"
                ));
                repositorio.save(new Producto(
                    "Caja Legumbres y Granos",
                    "Lentejas, garbanzos, quinoa y arroz integral de produccion agroecologica. Ideal para complementar la dieta.",
                    8990.0, 0, "Despensa"
                ));
                repositorio.save(new Producto(
                    "Caja Hierbas y Aromáticas",
                    "Perejil, albahaca, cilantro, menta y oregano frescos. Cultivados sin agroquimicos en huertos urbanos certificados.",
                    6990.0, 8, "Hierbas"
                ));
            }
        };
    }
}
