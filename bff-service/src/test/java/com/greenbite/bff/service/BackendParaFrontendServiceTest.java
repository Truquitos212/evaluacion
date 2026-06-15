package com.greenbite.bff.service;

import com.greenbite.bff.dto.CompraDTO;
import com.greenbite.bff.dto.ProductoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BackendParaFrontendServiceTest {

    @Mock
    private WebClient clienteProductos;

    @Mock
    private WebClient clienteCompras;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private BackendParaFrontendService servicio;

    private ProductoDTO producto;
    private CompraDTO compra;

    @BeforeEach
    void setUp() {

        producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Caja Familiar");

        compra = new CompraDTO();
        compra.setId(1L);
        compra.setEstado("PENDIENTE");

        servicio = new BackendParaFrontendService(clienteProductos, clienteCompras);
    }

    @Test
    void deberiaObtenerProductos() {

        ProductoDTO[] productos = { producto };

        // Configurar la cadena de mocks
        when(clienteProductos.get()).thenAnswer(invocation -> requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenAnswer(invocation -> responseSpec);
        when(responseSpec.bodyToMono(ProductoDTO[].class)).thenReturn(Mono.just(productos));

        List<ProductoDTO> resultado = servicio.obtenerProductos();

        assertEquals(1, resultado.size());
        assertEquals("Caja Familiar", resultado.get(0).getNombre());
    }

    @Test
    void deberiaRetornarListaVaciaSiNoHayProductos() {

        when(clienteProductos.get()).thenAnswer(invocation -> requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenAnswer(invocation -> responseSpec);
        when(responseSpec.bodyToMono(ProductoDTO[].class)).thenReturn(Mono.empty());

        List<ProductoDTO> resultado = servicio.obtenerProductos();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deberiaObtenerProductoPorId() {

        when(clienteProductos.get()).thenAnswer(invocation -> requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenAnswer(invocation -> requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenAnswer(invocation -> responseSpec);
        when(responseSpec.bodyToMono(ProductoDTO.class)).thenReturn(Mono.just(producto));

        ProductoDTO resultado = servicio.obtenerProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Caja Familiar", resultado.getNombre());
    }

    @Test
    void deberiaProcesarCompra() {

        CompraDTO respuesta = new CompraDTO();
        respuesta.setId(1L);
        respuesta.setEstado("COMPLETADA");

        when(clienteCompras.post()).thenAnswer(invocation -> requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenAnswer(invocation -> requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any())).thenAnswer(invocation -> requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenAnswer(invocation -> responseSpec);
        when(responseSpec.bodyToMono(CompraDTO.class)).thenReturn(Mono.just(respuesta));

        CompraDTO resultado = servicio.procesarCompra(compra);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("COMPLETADA", resultado.getEstado());
    }
}