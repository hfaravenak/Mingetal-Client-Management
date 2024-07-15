package mingetal.MCM.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/user/register",
            "/user/token",
            "/eureka",
            "/proveedor/nombre",
            "/proveedor/rubro",
            "/proveedor/empresa",
            "/cliente/nombre",
            "/cliente/empresa",
            "/productos",
            "/ordenes_de_compra/proveedor",
            "/ordenes_de_compra/cliente/productos",
            "/ordenes_de_compra/proveedor/productos",
            "/cliente/cotizacion/productos",
            "/cliente/cotizacion/productos",
            "/cliente"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
